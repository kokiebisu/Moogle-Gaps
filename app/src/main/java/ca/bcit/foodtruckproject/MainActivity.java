package ca.bcit.foodtruckproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class DownloadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg) {
            HttpURLConnection connection;
            try {
                URL url = new URL("https://opendata.vancouver.ca/api/records/1.0/search/?dataset=food-vendors&rows=1&facet=vendor_type&facet=status&facet=geo_localarea");
                connection = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                InputStream in = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                JSONObject JOB = new JSONObject(sb.toString());

            }
            catch (Exception e) {
                Log.e(MainActivity.class.getSimpleName(), "Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(Void arg){

        }
    }
}
