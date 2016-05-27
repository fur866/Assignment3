package com.example.fahad.assignment3.Fragments;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fahad.assignment3.AsyncTasks.DownloadImageData;
import com.example.fahad.assignment3.Interfaces.AsyncResponse;
import com.example.fahad.assignment3.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

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
    private ProgressDialog progress;
    private final int total = 5;

    @Override
    public void onCreate(Bundle bundles)
    {
        super.onCreate(bundles);
        Resources resources = getContext().getResources();
        api_key = resources.getString(R.string.api_key);
        api_endPoint = resources.getString(R.string.api_endPoint);
        this.images = new HashMap<String, Bitmap>();
        progress =new ProgressDialog(getContext());
        progress.setMessage("Downloading Images");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setCancelable(false);
        progress.setMax(100);
        progress.setProgress(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.satellite_fragment_view, container,false);

        progress.show();

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
            this.progress.setProgress((int) (((float) this.images.size() / (float) total) * 100));
            if(images.size() >=  this.total)
            {
                showImagesSequentially();
            }

    }

    public void performNASARequest(String date)
    {
        DownloadImageData image =  new DownloadImageData(getContext());
        image.delegate = this;
        image.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,api_endPoint, longitude, latitude, date, api_key);
    }

    public void performNASARequestSequence()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,16);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 0; i < this.total; i++)
        {
            calendar.add(Calendar.DAY_OF_YEAR,-16);
           // Log.d("Here",formatter.format(calendar.getTime()));
            performNASARequest(formatter.format(calendar.getTime()));
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
        final Iterator<String> it = images.keySet().iterator();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                if (it.hasNext()){
                    String key = it.next();
                    Bitmap value = images.get(key);
                    imageView.setImageBitmap(value);
                    textView.setText(key);
                    progress.dismiss();
                    handler.postDelayed(this, 2500);
                }
            }
        }, 2500);
    }
    
}
