package com.example.horosco.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import com.example.horosco.R;

public class SignActivity extends AppCompatActivity {

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
                String signInfo = jsonObject.getString("horoscope");
                Log.i("About the zodiac sign: ", signInfo);

                TextView infoView = (TextView) findViewById(R.id.infoView);
                infoView.setText(signInfo);

                JSONArray array = new JSONArray(signInfo);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject1 = array.getJSONObject(i);
                    Log.i("Description", jsonObject1.getString("horoscope"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = sdf.format(c.getTime());
        //Log.d("Date","DATE : " + strDate);

        Intent intent = getIntent();
        String day = (intent.getStringExtra(HomeActivity.EXTRA_TEXT2));
        TextView infoView = (TextView) findViewById(R.id.text_date_display);
        infoView.setText(day + ": " + strDate);


        String sign = (intent.getStringExtra(HomeActivity.EXTRA_TEXT)).toLowerCase();
        String signName = (intent.getStringExtra(HomeActivity.EXTRA_TEXT)).toLowerCase();
        //String day = intent.getStringExtra(HomeActivity.EXTRA_TEXT2);

        TextView infoView2 = (TextView) findViewById(R.id.text_sign_display);
        infoView2.setText("Hello " + signName.toUpperCase());

        String uri = "@drawable/" + sign;
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        ImageView imageSignView = (ImageView)findViewById(R.id.image_sign_view);
        Drawable res = getResources().getDrawable(imageResource);
        imageSignView.setImageDrawable(res);

        //Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_SHORT).show();

        SignActivity.DownloadTask task = new SignActivity.DownloadTask();
        task.execute("https://ohmanda.com/api/horoscope/"+sign);
    }
}
