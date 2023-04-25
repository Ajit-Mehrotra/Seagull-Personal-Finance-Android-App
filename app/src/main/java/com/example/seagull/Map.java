package com.example.seagull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends MainActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private static final LatLng University_of_Pacific = new LatLng(37.7826801839483, -122.40561381000833);
    private GoogleApiClient mGoogleApiClient;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        MainActivity mainActivityInstance = MainActivity.getInstance();
        String tabId = getIntent().getStringExtra("tabId");
        fragmentTransaction.add(R.id.tab4, mapFragment).commit();
        Toast.makeText(Map.this, "Hello world", Toast.LENGTH_LONG).show();
        mapFragment.getMapAsync(this);
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("tabId", tabId);
                startActivity(intent);
                finish();

            }
        });
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        mGoogleApiClient.connect();

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int radius = 5000;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(Map.this, "not GPS", Toast.LENGTH_LONG).show();
            return;
        }

        googleMap.setMyLocationEnabled(true);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Move the map to the user's location
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                // Remove location updates after the first location is received
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.removeUpdates(this);
            }
        };

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);



        //display a Toast when the marker is clicked
        mMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {
                    TabHost tabs = (TabHost) findViewById(R.id.tabhost);

                    public boolean onMarkerClick(Marker m) {
                        //get title and snippet to display on Toast, get the snip and search that in the webview
                        String title = m.getTitle();
                        String snip = m.getSnippet();
                        Toast.makeText(Map.this, title,Toast.LENGTH_LONG).show();


                        return true;
                    }
                }
        );
    }
}