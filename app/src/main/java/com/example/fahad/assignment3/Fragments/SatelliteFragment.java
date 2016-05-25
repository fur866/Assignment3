package com.example.fahad.assignment3.Fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fahad.assignment3.AsyncTasks.DownloadImage;
import com.example.fahad.assignment3.R;
import com.squareup.picasso.RequestCreator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Fahad on 23/05/2016.
 */
public class SatelliteFragment extends Fragment {

    private String latitude;
    private String longitude;
    private String date;
    private final String api_key;//getSystem().getString(R.string.api_key);
    private final String api_endPoint;//getResources().getString(R.string.api_endPoint);

    public SatelliteFragment(Context context)
    {
        Resources resources = context.getResources();
        api_key = resources.getString(R.string.api_key);
        api_endPoint = resources.getString(R.string.api_endPoint);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.satellite_fragment_view, container,false);

        this.longitude = "150.8931239";
        this.latitude = "-34.424984";
        ImageView imageView = (ImageView) view.findViewById(R.id.satelliteImage);
        performNASARequestSequence(imageView);

        return view;
    }


    public void performNASARequest(ImageView imageView, String date)
    {
        try {
            new DownloadImage(getContext(), imageView).execute(api_endPoint, longitude, latitude, date, api_key);
        }
        catch(Exception e){}
    }

    public void performNASARequestSequence(ImageView imageView)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,16);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 0; i < 5; i++)
        {
            calendar.add(Calendar.DAY_OF_YEAR,-16);
            Log.d("Here",formatter.format(calendar.getTime()));
            performNASARequest(imageView,formatter.format(calendar.getTime()));
        }
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public void setDate(String date)
    {
        this.date = date;
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

    public String getDate()
    {
        return this.date;
    }
}
