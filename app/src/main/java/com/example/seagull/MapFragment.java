package com.example.seagull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, EasyPermissions.PermissionCallbacks {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    //private TextView webViewText;

    public MapFragment() {
    }

    GoogleApiClient client;
    private MainActivity ma;
    private Context thiscontext;

    private LatLng userLocation;
    private ArrayList<String> places;
    private ArrayList<Bank> banks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ma = (MainActivity) getActivity();
        thiscontext = ma.getApplicationContext();
    }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        Button button = rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                findBank(v);
            }

        });
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


        return rootView;


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        checkLocationPermission();
        mMap.setMyLocationEnabled(true);
        client = new GoogleApiClient.Builder(thiscontext).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        client.connect();
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Move the map to the user's location
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 9));
                // Remove location updates after the first location is received

                LocationManager locationManager = ma.getLocationManager();
                locationManager.removeUpdates(this);
            }
        };

        mMap.setOnMarkerClickListener(marker -> {
            Toast.makeText(getContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();


          try{
              String bankID = marker.getSnippet();
              Bank b = getBank(bankID);
              Intent intent = new Intent(thiscontext, WebActivity.class);
              try {
                  String website = b.getWebsite();
                  intent.putExtra("website", website);
              } catch (Exception e) {

              }
              try {
                  String name = b.getName();
                  intent.putExtra("name", name);

              } catch (Exception e) {

              }
              try {
                  String phone = b.getPhone();
                  intent.putExtra("phone", phone);
              } catch (Exception e) {

              }
              try {
                  String address = b.getWebsite();
                  intent.putExtra("address", address);
              } catch (Exception e) {

              }
              startActivity(intent);
          }catch (Exception e){
              Toast.makeText(getContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
          }

//            Log.d("MapFragment", "Banks: " + banks.size());


            return false;
        });
    }
    private void checkLocationPermission() {
        String[] perms = {android.Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(requireActivity(), perms)) {
            enableMyLocation();
        } else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission to use the app",
                    LOCATION_PERMISSION_REQUEST_CODE, perms);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = new LocationRequest().create();
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(thiscontext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(thiscontext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
//               public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                                      int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
    }

    public void findBank(View v) {
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + userLocation.latitude + "," + userLocation.longitude);
        sb.append("&radius=" + 10000);
        sb.append("&keyword=" + "Bank");
        sb.append("&key=" + getResources().getString(R.string.google_maps_key));
        String url = sb.toString();
        Object dataTransfer[] = new Object[2];
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;

        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
        getNearbyPlaces.execute(dataTransfer);
        places = getNearbyPlaces.getPlaceIds();
        banks = getNearbyPlaces.getBanks();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        userLocation = new LatLng(location.getLatitude(), location.getLongitude());

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(userLocation, 12);
        mMap.animateCamera(update);
        MarkerOptions options = new MarkerOptions();
        options.position(userLocation);
        options.title("Locations");
        mMap.addMarker(options);
    }

    private Bank getBank(String ID) {
        for (Bank b : banks
        ) {
            if (b.getID().equals(ID)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            enableMyLocation();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this).build().show();
            } else {
                checkLocationPermission();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            checkLocationPermission();
        }
    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



}



