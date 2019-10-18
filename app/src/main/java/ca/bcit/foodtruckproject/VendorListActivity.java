package ca.bcit.foodtruckproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ca.bcit.foodtruckproject.R;

public class VendorListActivity extends AppCompatActivity {

    private static String SERVICE_URL =
            "https://opendata.vancouver.ca/api/records/1.0/search/?dataset=food-vendors&facet=vendor_type&facet=status&facet=geo_localarea";
    private ArrayList<String> businessNames;
    private ArrayList<String> vendorTypes;
    private ArrayList<double[]> coordinates;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list);

        lv = findViewById(R.id.list);
        businessNames = new ArrayList<>();
        vendorTypes = new ArrayList<>();
        coordinates = new ArrayList<>();

        new GetVendors().execute();
    }

    public void clickItem(View view) {
        TextView tv = (TextView) view;
        String key = tv.getText().toString();
        Intent intent = new Intent(this, MapsActivity.class);

        int idx = 0;
        for (int i = 0; i < businessNames.size(); i++) {
            if (businessNames.get(i).equals(key)) {
                idx = i;
                break;
            }
        }

        intent.putExtra("coordinates", coordinates.get(idx));

        startActivity(intent);
    }

    private class GetVendors extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground (Void... arg0){
        HttpURLConnection con;
        try {
            //Creating HTTP connection
            URL url = new URL(SERVICE_URL);
            con = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            //Creating JSON Object
            JSONObject dataSet = new JSONObject(sb.toString());
            JSONArray records = dataSet.getJSONArray("records");
            for(int i=0; i<records.length(); i++){
                String businessName;
                JSONObject record = records.getJSONObject(i);
                JSONObject vendorFields = record.getJSONObject("fields");
                JSONObject vendorGeom = vendorFields.getJSONObject("geom");
                JSONArray jsonCoords = vendorGeom.getJSONArray("coordinates");
                double[] vendorCoords = new double[]{jsonCoords.getDouble(0), jsonCoords.getDouble(1)};
                //not all records have business names; using food type instead if they don't.
                try {
                    businessName = vendorFields.getString("business_name");
                } catch (JSONException e) {
                    businessName = vendorFields.getString("description");
                }
                String vendorType = vendorFields.getString("vendor_type");
                //adds string into ArrayList of Business Names
                businessNames.add(businessName);
                //adds string into ArrayList of Vendor Types
                vendorTypes.add(vendorType);
                coordinates.add(vendorCoords);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            }
        return null;
        }

        @Override
        protected void onPostExecute(Void o) {
            super.onPostExecute(o);
            Adapter adapter = new Adapter(VendorListActivity.this, businessNames);
            lv.setAdapter(adapter);
        }
    }
}
