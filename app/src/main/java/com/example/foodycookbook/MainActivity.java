package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String randomFoodApi = "https://www.themealdb.com/api/json/v1/1/random.php";
    private static String apiData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Start","Process started");

        DownloadRandomFoodApi task = new DownloadRandomFoodApi();
        try {
            task.execute(randomFoodApi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void apiWriteback(String data) {
        apiData = data;
        if (!apiData.equals("")) {
            Log.i("Info",apiData);
        }
        else {
            Log.i("Info","Could not fetch api");
        }
    }

    private static class DownloadRandomFoodApi extends AsyncTask<String,Void,Void> {

        String api = "";

        @Override
        protected Void doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    api += current;
                    data = reader.read();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            apiWriteback(api);
        }
    }
}