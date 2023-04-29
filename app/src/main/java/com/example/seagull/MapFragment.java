package com.example.seagull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<String> places;
    private ArrayList<Bank> banks;
    private FusedLocationProviderClient mFusedLocationClient;
    Button findBankBtn;
    private LatLng currentLatLng;
    private static final String TAG = "LOGGING";

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.w(TAG, "Location permission granted");
                    getLocation();
                } else {
                    Log.w(TAG, "Location permission denied");
                    Toast.makeText(requireContext(), "Location permission is required for this feature.", Toast.LENGTH_LONG).show();
                }
            });



    public MapFragment() {

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        mFusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
            if (location != null) {
                currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                Log.d(TAG, "Current location: " + currentLatLng);

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Your location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));
            }
        });
    }
    private void checkAndRequestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        findBankBtn = rootView.findViewById(R.id.button);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        findBankBtn.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        checkAndRequestLocationPermission();

        mMap.setOnMarkerClickListener(marker -> {
            Toast.makeText(getContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();


            try{
                String bankID = marker.getSnippet();
                Bank b = getBank(bankID);
                Intent intent = new Intent(requireContext(), WebActivity.class);
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

            return false;
        });

    }


    public void findBank(View v) {

        if (currentLatLng == null) {
            Toast.makeText(requireContext(), "User location not available yet. Please try again in a moment.", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + currentLatLng.latitude + "," + currentLatLng.longitude);
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

    private Bank getBank(String ID) {
        for (Bank b : banks
        ) {
            if (b.getID().equals(ID)) {
                return b;
            }
        }
        return null;
    }


}



