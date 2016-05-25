package com.example.fahad.assignment3.AsyncTasks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.fahad.assignment3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Fahad on 23/05/2016.
 */
public class DownloadImage extends AsyncTask<String,Void,String>{

    @Override
    protected String doInBackground(String... args) {
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
            return stringResult;

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

    @Override
    protected void onPostExecute(String result) {
        Log.d("Here1",result);
//        try {
//            JSONArray array = new JSONArray(result);
//            //  list.clear();
//            this.listQuestions = new ArrayList<QuestionResponseModel>();
//            for(int i = 0; i < array.length(); i++)
//            {
//                JSONObject object = array.getJSONObject(i);
//                Log.d("Here p: ",object.getString("question"));
//                this.listQuestions.add(new QuestionResponseModel(object.getString("question"),object.getString("answer"),object.getString("imageURL")));
//            }
//
//            Intent intent = new Intent(getApplicationContext(),HistoryActivity.class);
//            intent.putExtra("QuestionResponseArray",this.listQuestions);//downloadTask.getList());
//            startActivity(intent);
//        }
//        catch(JSONException e)
//        {
//
//        }
    }
}
