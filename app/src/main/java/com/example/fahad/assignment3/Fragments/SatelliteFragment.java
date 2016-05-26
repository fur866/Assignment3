package com.example.fahad.assignment3.Fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fahad.assignment3.AsyncTasks.DownloadImage;
import com.example.fahad.assignment3.Interfaces.AsyncResponse;
import com.example.fahad.assignment3.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Fahad on 23/05/2016.
 */
public class SatelliteFragment extends Fragment implements AsyncResponse{

    private String latitude;
    private String longitude;
    private String api_key;
    private String api_endPoint;
    private HashMap<String,Bitmap> images;
    private ImageView imageView;
    private TextView textView;

    @Override
    public void onCreate(Bundle bundles)
    {
        super.onCreate(bundles);
        Resources resources = getContext().getResources();
        api_key = resources.getString(R.string.api_key);
        api_endPoint = resources.getString(R.string.api_endPoint);
        this.images = new HashMap<String, Bitmap>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.satellite_fragment_view, container,false);

        this.longitude = "150.8931239";
        this.latitude = "-34.424984";
        this.imageView = (ImageView) view.findViewById(R.id.satelliteImage);
        this.textView = (TextView) view.findViewById(R.id.satelliteText);
        performNASARequestSequence();

        return view;
    }

    @Override
    public void processFinish(Bitmap image, String date)
    {
            this.images.put(date,image);
            Log.d("Here",String.valueOf(images.size()));
            if(images.size() >=  5)
            {
                showImagesSequentially();
            }

    }

    public void performNASARequest(String date)
    {
        DownloadImage image =  new DownloadImage(getContext());
        image.delegate = this;
        image.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,api_endPoint, longitude, latitude, date, api_key);
    }

    public void performNASARequestSequence()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,16);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 0; i < 5; i++)
        {
            calendar.add(Calendar.DAY_OF_YEAR,-16);
           // Log.d("Here",formatter.format(calendar.getTime()));
            performNASARequest(formatter.format(calendar.getTime()));
            this.latitude = "51.507351";
            this.longitude = "-0.127758";
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

    private void showImagesSequentially()
    {
//        for (final Map.Entry<String, Bitmap> entry : images.entrySet()) {
//
//            final Bitmap value = entry.getValue();
//            final String key = entry.getKey();


//            handler.postDelayed(new Runnable(){
//                public void run(){
//                    imageView.setImageBitmap(value);
//                    textView.setText(key);
//                    Log.d("here", key);
//
//                    handler.postDelayed(this, 500);
//                }
//            }, 500);

            final Iterator<String> it = images.keySet().iterator();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                public void run(){
                    if (it.hasNext()){
                        String key = it.next();
                        Bitmap value = images.get(key);
                        imageView.setImageBitmap(value);
                        textView.setText(key);
                        handler.postDelayed(this, 2500);
                    }
                }
            }, 2500);
        //}
    }
}
