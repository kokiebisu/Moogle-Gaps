package ca.bcit.foodtruckproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Vendor> {

    Context _context;

    public Adapter(@NonNull Context context, ArrayList<Vendor> vendors) {
        super(context, 0, vendors);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;

        Vendor vendor = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_vendor_list, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.name);
        TextView tvDescription = convertView.findViewById(R.id.description);
        TextView tvType = convertView.findViewById(R.id.timeStamp);
        TextView tvLocationDescription = convertView.findViewById(R.id.locationDescription);
        TextView tvTimeStamp = convertView.findViewById(R.id.timeStamp);
        tvName.setText(vendor.getName());
        tvDescription.setText(vendor.getDescription());
        tvType.setText(vendor.getType());
        tvLocationDescription.setText(vendor.getLocationDescription());
        tvTimeStamp.setText(vendor.getTimeStamp());

        String descriptionString = "Food type: " + tvDescription.getText().toString();
        tvDescription.setText(descriptionString);
        return convertView;
    }
}
