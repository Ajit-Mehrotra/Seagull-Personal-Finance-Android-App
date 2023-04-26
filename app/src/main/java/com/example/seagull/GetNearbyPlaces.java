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

public class GetNearbyPlaces extends AsyncTask<Object,String,String> {

    GoogleMap mMap;
    String url;
    InputStream is;
    BufferedReader br;
StringBuilder stringBuilder;
String data;
    JSONObject jsonObject;
    @Override
    protected String doInBackground(Object... params) {
        mMap = (GoogleMap)params[0];
        url = (String)params[1];

        try {
            URL myurl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)myurl.openConnection();
            httpURLConnection.connect();
            is = httpURLConnection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line= "";
            stringBuilder = new StringBuilder();
            while ((line = br.readLine()) != null){
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return data;
    }
    @Override
    protected void onPostExecute(String s){

        try {
            JSONObject parentObject = new JSONObject(s);
            JSONArray resultsArray = parentObject.getJSONArray("results");
            Log.e("bruh", String.valueOf(resultsArray));
            int i;
            for (i = 0; i < resultsArray.length()-1; i++) {
                jsonObject = resultsArray.getJSONObject(i);
                Log.e("bruh", String.valueOf(jsonObject));
                JSONObject locationObject = jsonObject.getJSONObject("geometry").getJSONObject("location");

                String lat = locationObject.getString("lat");
                String lon = locationObject.getString("lng");

                JSONObject nameObject = resultsArray.getJSONObject(i);
                String bank = nameObject.getString("name");
                String vicinity = nameObject.getString("vicinity");

                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(vicinity);
                markerOptions.position(latLng);
                mMap.addMarker(markerOptions);
            }


        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }
}
