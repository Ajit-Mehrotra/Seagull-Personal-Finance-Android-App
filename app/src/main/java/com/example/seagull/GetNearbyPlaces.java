package com.example.seagull;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetNearbyPlaces extends AsyncTask<Object, String, String> {
    //LOG TAG
    private static final String TAG = "GET_NEARBY";
    //STRING
    String url;
    String data;
    StringBuilder stringBuilder;
    //IO
    InputStream is;
    BufferedReader br;
    //MAP & JSON
    GoogleMap mMap;
    JSONObject jsonObject;
    //ARRAYLIST
    ArrayList<String> placeIds = new ArrayList<String>();
    ArrayList<Bank> banks = new ArrayList<>();

    @Override
    protected String doInBackground(Object... params) {
        mMap = (GoogleMap) params[0];
        url = (String) params[1];

        try {
            //HTTP URL CONNECTION
            URL myurl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) myurl.openConnection();
            httpURLConnection.connect();

            is = httpURLConnection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            String line = "";
            stringBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();

        }   catch (MalformedURLException e) {throw new RuntimeException(e);}
            catch (IOException e) {throw new RuntimeException(e);}

        return data;
    }

    @Override
    protected void onPostExecute(String s) {

        try {
            //JSON ARRAY
            JSONObject parentObject = new JSONObject(s);
            JSONArray resultsArray = parentObject.getJSONArray("results");
            Log.e(TAG, String.valueOf(resultsArray));

            for (int i = 0; i < resultsArray.length() - 1; i++) {
                //GET JSON OBJECT
                jsonObject = resultsArray.getJSONObject(i);
                Log.e(TAG, String.valueOf(jsonObject));
                JSONObject locationObject = jsonObject.getJSONObject("geometry").getJSONObject("location");

                //LATITUDE & LONGITUDE
                String lat = locationObject.getString("lat");
                String lon = locationObject.getString("lng");

                String ID = jsonObject.getString("reference");
                placeIds.add(ID);
                Log.e(TAG, String.valueOf(placeIds));

                String bank = jsonObject.getString("name");
                String vicinity = jsonObject.getString("vicinity");

                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));

                //MARKERS
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(vicinity);
                markerOptions.position(latLng);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bank));
                mMap.addMarker(markerOptions).setSnippet(ID);

            }

            for (String e : placeIds) Log.i(TAG, "Reference" + e);


        } catch (JSONException e) {throw new RuntimeException(e);}

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i(TAG, "Runnable started!");
                try {
                    for (String id : placeIds) {

                        //API REQUEST URL
                        String url =    "https://maps.googleapis.com/maps/api/place/details/json?" +
                                        "place_id=" + id +
                                        "&fields=website,name,formatted_phone_number,formatted_address" +
                                        "&key=AIzaSyATGMGc25BNLlAzllIQLZULVtFt59IQ10E";

                        //HTTP URL CONNECTION
                        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                        connection.setRequestMethod("GET");

                        //SERVER RESPONSE
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                        StringBuilder builder = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {builder.append(line);}
                        String response = builder.toString();

                        Log.i(TAG, "Response Running");

                        //PARSE JSON
                        JSONObject json = new JSONObject(response);
                        JSONObject result = json.getJSONObject("result");

                        //EXTRACT FIELD VALUES

                        Bank newBank = new Bank(id);

                        try {
                            String name = result.getString("name");
                            newBank.setName(name);
                        } catch (Exception e) {Log.e(TAG,"Name set exception caught");}

                        try {
                            String formattedAddress = result.getString("formatted_address");
                            newBank.setAddress(formattedAddress);
                        } catch (Exception e) {Log.e(TAG,"Address set exception caught");}

                        try {
                            String formattedPhoneNumber = result.getString("formatted_phone_number");
                            newBank.setPhone(formattedPhoneNumber);
                        } catch (Exception e) {Log.e(TAG,"Phone set exception caught");}

                        try {
                            String website = result.getString("website");
                            newBank.setWebsite(website);
                        } catch (Exception e) {Log.e(TAG,"Website set exception caught");}

                        banks.add(newBank);

                    }

                } catch (Exception e) {e.printStackTrace();}
            }

        }).start();

    }

    public ArrayList<String> getPlaceIds() {return placeIds;}

    public ArrayList<Bank> getBanks() {return banks;}

}
