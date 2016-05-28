package com.example.fahad.assignment3.Fragments;

import android.app.Service;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fahad.assignment3.MainActivity;
import com.example.fahad.assignment3.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Fahad on 28/05/2016.
 */
public class WorldMapFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap mMap;
    private double currentLatitue;
    private double currentLongitude;

    @Override
    public void onCreate(Bundle bundles)
    {
        super.onCreate(bundles);
        //default set to Sydney
        this.currentLatitue = -34;
        this.currentLongitude = 151;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.map_fragment_view, container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btn = (Button) view.findViewById(R.id.downloadImagesButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).downloadImages(currentLatitue,currentLongitude);
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                double latval = latLng.latitude;
                double longval = latLng.longitude;
                String addressStr = "";
                try {
                    Geocoder myLocation = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> myList = myLocation.getFromLocation(latval, longval, 1);
                    Address address = (Address) myList.get(0);
                    addressStr += address.getAddressLine(0) + ", ";
                    addressStr += address.getAddressLine(1) + ", ";
                    addressStr += address.getAddressLine(2);
                }
                catch (IOException e)
                {
                    Log.e("IOException", e.toString());
                }

                mMap.addMarker(new MarkerOptions().position(new LatLng( latval,  longval))
                        .title(addressStr)
                        .rotation((float) -15.0)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                );
                currentLatitue = latval;
                currentLongitude = longval;
            }
        });

        LatLng currPos;

        try {
            LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            currPos = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (SecurityException e)
        {
            //if something goes wrong, go to the default one
            currPos = new LatLng(this.currentLatitue, this.currentLongitude);
        }
        mMap.addMarker(new MarkerOptions().position(currPos).title("Current Position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currPos));
    }

}
