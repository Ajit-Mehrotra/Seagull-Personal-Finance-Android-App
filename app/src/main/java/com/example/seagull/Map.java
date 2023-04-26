package com.example.seagull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
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
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
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
import com.google.android.libraries.places.api.model.TypeFilter;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class Map extends MainActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    String placeId = "PLACE_ID_HERE";
    PlaceFilter filter = new PlaceFilter();
    GoogleApiClient mGoogleApiClient;
    double latitude;
    double longitude;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        MainActivity mainActivityInstance = MainActivity.getInstance();
        String tabId = getIntent().getStringExtra("tabId");
        fragmentTransaction.add(R.id.tab4, mapFragment).commit();
    //    PlacesClient placesClient = Places.createClient(this);

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
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .build();


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
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12));
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
        getNearbyATMs();
    }
    private void getNearbyATMs() {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        // Handle connection success
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        // Handle connection suspension
                    }
                })
                .addOnConnectionFailedListener(connectionResult -> {
                    // Handle connection failure
                })
                .build();

        mGoogleApiClient.connect();
        LatLng currentLocation = new LatLng(latitude, longitude);

        @SuppressLint("MissingPermission") PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer likelyPlaces) {
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    Place place = placeLikelihood.getPlace();
                    List<Integer> placeTypes = place.getPlaceTypes();
                    if (placeTypes.contains(Place.TYPE_BANK)) {
                        // This is an ATM, add marker on map
                        LatLng latLng = place.getLatLng();
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(latLng)
                                .title(place.getName().toString());
                        mMap.addMarker(markerOptions);
                    }
                }
                likelyPlaces.release();
            }
        });

    }
}
