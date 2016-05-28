package com.example.fahad.assignment3;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.fahad.assignment3.Fragments.SatelliteFragment;
import com.example.fahad.assignment3.Fragments.WorldMapFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       setHomePage();
    }

    public void setHomePage()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.satelliteFragment, new WorldMapFragment());
        fragmentTransaction.commit();
    }

    public void downloadImages(double latitude, double longitude)
    {
        SatelliteFragment fragment = new SatelliteFragment();
        fragment.setLatitude(String.valueOf(latitude));
        fragment.setLongitude(String.valueOf(longitude));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.satelliteFragment, fragment);
        fragmentTransaction.commit();
    }
}
