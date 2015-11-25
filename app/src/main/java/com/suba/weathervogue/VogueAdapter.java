package com.suba.weathervogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Sakthivale on 11/16/2015.
 */
public class VogueAdapter extends BaseAdapter {

    private Context mContext;
    private Vogue[] mVogues;

    public VogueAdapter(Context context, Vogue[] vogues) {
        mContext = context;
        mVogues = vogues;
    }

    @Override
    public int getCount() {
        return mVogues.length;
    }

    @Override
    public Object getItem(int position) {
        return mVogues[position];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.vogue_list_item, null);
            holder = new ViewHolder();
            holder.dateTextView = (TextView) convertView.findViewById(R.id.timeTextView);
            holder.dressTextView = (TextView) convertView.findViewById(R.id.dressTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Vogue vogue = mVogues[position];


        String iconStr = vogue.getIconStr();
        int maxHumidity = vogue.getMaxHumidity();
        int maxTemperature = vogue.getMaxTemperature();
        int minTemperature = vogue.getMinTemperature();

        String dress = Forecast.getVogueStr(iconStr, maxHumidity, maxTemperature, minTemperature);

        holder.dateTextView.setText(vogue.getMonth() + "/" + vogue.getDayNum());
        holder.dressTextView.setText(dress);

        return convertView;
    }

    public static class ViewHolder{

        TextView dateTextView;
        TextView dressTextView;
    }
}
