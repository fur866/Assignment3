package com.example.fahad.assignment3.Interfaces;

import android.graphics.Bitmap;

import com.squareup.picasso.RequestCreator;

/**
 * Created by Fahad on 25/05/2016.
 */
public interface AsyncResponse {
    void processFinish(Bitmap image, String date);
}
