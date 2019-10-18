package ca.bcit.foodtruckproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class VendorListActivity extends AppCompatActivity {

    private static String SERVICE_URL =
            "https://opendata.vancouver.ca/api/records/1.0/search/?dataset=food-vendors&facet=vendor_type&facet=status&facet=geo_localarea";
    private ArrayList<String> businessNames;
    private ArrayList<String> vendorTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list);
    }

    private class getVendors extends AsyncTask<Void, Void, Void> {
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
                //not all records have business names; using food type instead if they don't.
                if(vendorFields.getString("business_name") != null){
                    businessName = vendorFields.getString("vendor_type");
                } else{
                    businessName = vendorFields.getString("business_name");
                }
                String vendorType = vendorFields.getString("vendor_type");
                //adds string into ArrayList of Business Names
                businessNames.add(businessName);
                //adds string into ArrayList of Vendor Types
                vendorTypes.add(vendorType);
            }
        } catch (Exception e) {
            e.getMessage();
            }
        return null;
        }
    }
}
