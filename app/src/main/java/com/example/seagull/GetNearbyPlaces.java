package com.example.seagull;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
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

    GoogleMap mMap;
    String url;
    InputStream is;
    BufferedReader br;
    StringBuilder stringBuilder;
    String data;
    JSONObject jsonObject;
    ArrayList<String> placeIds = new ArrayList<String>();
    ArrayList<Bank> banks = new ArrayList<>();

    @Override
    protected String doInBackground(Object... params) {
        mMap = (GoogleMap) params[0];
        url = (String) params[1];

        try {
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
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    @Override
    protected void onPostExecute(String s) {

        try {
            JSONObject parentObject = new JSONObject(s);
            JSONArray resultsArray = parentObject.getJSONArray("results");
            Log.e("bruh", String.valueOf(resultsArray));


            int i;
            for (i = 0; i < resultsArray.length() - 1; i++) {
                jsonObject = resultsArray.getJSONObject(i);
                Log.e("bruh", String.valueOf(jsonObject));
                JSONObject locationObject = jsonObject.getJSONObject("geometry").getJSONObject("location");

                String lat = locationObject.getString("lat");
                String lon = locationObject.getString("lng");

                JSONObject nameObject = resultsArray.getJSONObject(i);
                String ID = nameObject.getString("reference");
                placeIds.add(ID);
                Log.e("bruhIDS", String.valueOf(placeIds));
                String bank = nameObject.getString("name");
                String vicinity = nameObject.getString("vicinity");

                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(vicinity);
                markerOptions.position(latLng);
                mMap.addMarker(markerOptions).setSnippet(ID);

            }
            for (String e : placeIds
            )
                Log.e("reference", e);
            {

            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.e("bruh", "RUNNING WOHOO");
                try {
                    for (String id : placeIds
                    ) {


                        // create the URL for the API request
                        String url = "https://maps.googleapis.com/maps/api/place/details/json?" +
                                "place_id=" + id +
                                "&fields=website,name,formatted_phone_number,formatted_address" +
                                "&key=AIzaSyATGMGc25BNLlAzllIQLZULVtFt59IQ10E";

                        // create a new HttpURLConnection object
                        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                        connection.setRequestMethod("GET");

                        // get the response from the server
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        String response = builder.toString();
                        Log.e("bruh", "RUNNING WOHOO");
                        // parse the JSON response
                        JSONObject json = new JSONObject(response);
                        JSONObject result = json.getJSONObject("result");

                        // extract the values of the fields

                        Bank newBank = new Bank(id);

                        try {
                            String name = result.getString("name");
                            newBank.setName(name);
                        } catch (Exception e) {

                        }

                        try {
                            String formattedAddress = result.getString("formatted_address");
                            newBank.setAddress(formattedAddress);
                        } catch (Exception e) {

                        }
                        try {
                            String formattedPhoneNumber = result.getString("formatted_phone_number");
                            newBank.setPhone(formattedPhoneNumber);
                        } catch (Exception e) {

                        }
                        try {
                            String website = result.getString("website");
                            newBank.setWebsite(website);
                        } catch (Exception e) {

                        }
                        banks.add(newBank);
                        // update the UI with the JSON data
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public ArrayList<String> getPlaceIds() {
        return placeIds;
    }
    public ArrayList<Bank> getBanks() {
        return banks;
    }
}
