package com.suba.weathervogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Sakthivale on 11/6/2015.
 */
public class LocationAdapter extends ArrayAdapter<Location> {

    private Context mContext;
    private Location[] mLocations;

    public LocationAdapter(Context context, int resource, Location[] locations) {
        super(context, resource, locations);

        mContext = context;
        mLocations = locations;
    }


    @Override
    public int getCount() {
        return mLocations.length;
    }

    @Override
    public Location getItem(int position) {
        return mLocations[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.location_list, null);
            holder = new ViewHolder();
            holder.locationListTextView = (TextView) convertView.findViewById(R.id.locationListTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Location location = mLocations[position];
        holder.locationListTextView.setText(location.getLocationName());

        return convertView;
    }


    private static class ViewHolder {
        TextView locationListTextView;
    }
}
