package com.example.horosco.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.horosco.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeActivity extends AppCompatActivity{

    String sign;
    String day;

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();

                while(data != -1){
                    char letter = (char) data;
                    json += letter;
                    data = reader.read();
                }

                return json;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.i("json", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String signInfo = jsonObject.getString("Sign");
                Log.i("Informacje o znaku", signInfo);
                JSONArray array = new JSONArray(signInfo);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject1 = array.getJSONObject(i);
                    Log.i("Opis pogody", jsonObject1.getString("description"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private Spinner spinnerSigns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        spinnerSigns = findViewById(R.id.spinnerSigns);

        ConstraintLayout todayButton = findViewById(R.id.todayButton);
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign = spinnerSigns.getSelectedItem().toString();
                day = "today";
                startActivity(new Intent(HomeActivity.this,SignActivity.class));
                //Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_SHORT).show();
            }
        });

        ConstraintLayout yesterdayButton = findViewById(R.id.yesterdayButton);
        yesterdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign = spinnerSigns.getSelectedItem().toString();
                day = "yesterday";
                startActivity(new Intent(HomeActivity.this,SignActivity.class));
                //Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_SHORT).show();
            }
        });

        ConstraintLayout tomorrowButton = findViewById(R.id.tomorrowButton);
        tomorrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign = spinnerSigns.getSelectedItem().toString();
                day = "tomorrow";
                startActivity(new Intent(HomeActivity.this,SignActivity.class));
                //Toast.makeText(getApplicationContext(), sign + day, Toast.LENGTH_SHORT).show();
            }
        });

        String[] signs = getResources().getStringArray(R.array.signs);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, signs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSigns.setAdapter(adapter);

        DownloadTask task = new DownloadTask();
        task.execute("https://aztro.sameerkumar.website?sign="+sign+"&day="+day);

    }
}
