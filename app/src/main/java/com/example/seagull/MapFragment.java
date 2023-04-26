//package com.example.seagull;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import android.widget.Toast;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class MapFragment extends Fragment implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    //private TextView webViewText;
//
//    public MapFragment() {}
//
//
//    @Override
//    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
//                              Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment != null) {
//            mapFragment.getMapAsync(this);
//        }
//
//        return rootView;
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//
//        // Add college markers
//        LatLng c1 = new LatLng(37.721897, -122.478209);
//        mMap.addMarker(new MarkerOptions().position(c1).title("San Francisco State University").snippet("https://www.sfsu.edu"));
//
//        LatLng c2 = new LatLng(37.788674, -122.407808);
//        mMap.addMarker(new MarkerOptions().position(c2).title("Academy of Art University").snippet("https://www.academyart.edu"));
//
//        LatLng c3 = new LatLng(37.427474, -122.169719);
//        mMap.addMarker(new MarkerOptions().position(c3).title("Stanford University").snippet("https://www.stanford.edu"));
//
//        LatLng c4 = new LatLng(37.875349, -122.258850);
//        mMap.addMarker(new MarkerOptions().position(c4).title("University of California, Berkeley").snippet("https://www.berkeley.edu"));
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.7749, -122.4194), 9));
//
//        mMap.setOnMarkerClickListener(marker -> {
//
//            Toast.makeText(getContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
//
//
//            return false;
//        });
//    }
//
//}
//
//
//
