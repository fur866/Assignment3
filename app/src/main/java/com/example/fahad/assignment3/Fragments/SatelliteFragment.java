package com.example.fahad.assignment3.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fahad.assignment3.R;

/**
 * Created by Fahad on 23/05/2016.
 */
public class SatelliteFragment extends Fragment {

    private String latitude;
    private String longitude;
    private final String api_key = getResources().getString(R.string.api_key);
    private final String api_endPoint = getResources().getString(R.string.api_endPoint);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.satellite_fragment_view, container,false);
        return view;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude()
    {
        return this.latitude;
    }

    public String getLongitude()
    {
        return this.longitude;
    }

    public String getApiKey()
    {
        return this.api_key;
    }

    public  String getApiEndPoint()
    {
        return this.api_endPoint;
    }
}
