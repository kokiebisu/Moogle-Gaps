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

public class Adapter extends ArrayAdapter<String> {

    Context _context;

    public Adapter(@NonNull Context context, ArrayList<String> vendors) {
        super(context, 0, vendors);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;

        String vendor = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_vendor_list, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.name);
        tvName.setText(vendor);

        return convertView;
    }
}
