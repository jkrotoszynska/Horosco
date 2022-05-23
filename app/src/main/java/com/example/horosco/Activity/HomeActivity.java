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

    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.example.application.example.EXTRA_TEXT2";

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

                Intent intent = new Intent(HomeActivity.this, SignActivity.class);
                intent.putExtra(EXTRA_TEXT, sign);
                intent.putExtra(EXTRA_TEXT2, day);
                startActivity(intent);

                //startActivity(new Intent(HomeActivity.this,SignActivity.class));
                //Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_SHORT).show();
            }
        });

        ConstraintLayout yesterdayButton = findViewById(R.id.yesterdayButton);
        yesterdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign = spinnerSigns.getSelectedItem().toString();
                day = "yesterday";

                Intent intent = new Intent(HomeActivity.this, SignActivity.class);
                intent.putExtra(EXTRA_TEXT, sign);
                intent.putExtra(EXTRA_TEXT2, day);
                startActivity(intent);

                //startActivity(new Intent(HomeActivity.this,SignActivity.class));
                //Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_SHORT).show();
            }
        });

        ConstraintLayout tomorrowButton = findViewById(R.id.tomorrowButton);
        tomorrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign = spinnerSigns.getSelectedItem().toString();
                day = "tomorrow";

                Intent intent = new Intent(HomeActivity.this, SignActivity.class);
                intent.putExtra(EXTRA_TEXT, sign);
                intent.putExtra(EXTRA_TEXT2, day);
                startActivity(intent);

                //startActivity(new Intent(HomeActivity.this,SignActivity.class));
                //Toast.makeText(getApplicationContext(), sign + day, Toast.LENGTH_SHORT).show();
            }
        });

        String[] signs = getResources().getStringArray(R.array.signs);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, signs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSigns.setAdapter(adapter);

//        DownloadTask task = new DownloadTask();
//        task.execute("https://aztro.sameerkumar.website?sign="+sign+"&day="+day);

    }
}
