package com.example.fahad.assignment3.AsyncTasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Fahad on 23/05/2016.
 */
public class DownloadImage extends AsyncTask<String,Void,RequestCreator>{

    private Context context;
    private ImageView imageView;

    public DownloadImage(Context context,ImageView imageView)
    {
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected RequestCreator doInBackground(String... args) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");

        String[] stringURL = args[0].split("/");
        builder.authority(stringURL[0]);
        int count = stringURL.length;
        for(int i = 1; i < count; i++)
        {
            builder.appendPath(stringURL[i]);
        }

        builder.appendQueryParameter("lon",args[1]);
        builder.appendQueryParameter("lat",args[2]);
        builder.appendQueryParameter("date",args[3]);
        builder.appendQueryParameter("cloud_source", "true");
        builder.appendQueryParameter("api_key",args[4]);

        String urlQuery = builder.build().toString();
        InputStream is = null;
        try{
            URL url = new URL(urlQuery);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000); //10 seconds
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect(); //Kick it off
            int response = connection.getResponseCode();

            Log.v("Response Cocde: ", new Integer(response).toString());

            is = connection.getInputStream();
            String stringResult = parseStream(is, 200);
            Log.d("Here", stringResult);
            return fetchImage(stringResult);
        }
        catch (MalformedURLException e)
        {
            Log.e("MalformedURL Exception",e.toString());
        }
        catch (IOException e) {
            Log.e("IOException", e.toString());
        }
        finally { //Close the input stream when we're done:
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (IOException e) {
                Log.e("IOException", e.toString());
            }
        }
        return null;
    }

    public String parseStream(InputStream stream, int length) throws IOException, UnsupportedEncodingException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder result = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    private RequestCreator fetchImage(String imageURL)
    {
        try {
            JSONObject object = new JSONObject(imageURL);

                Log.d("Here2", object.getString("url"));
                return Picasso.with(this.context)
                        .load(object.getString("url"));


        }
        catch(JSONException e)
        {
            Log.e("JSONException", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(RequestCreator creator) {
       creator.into(this.imageView);
    }
}
