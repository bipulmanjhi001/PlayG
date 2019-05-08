package com.PlayG.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.PlayG.R;
import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<EventList> mylist = new ArrayList<>();

    public EventAdapter(ArrayList<EventList> itemArray, Context mContext) {
        super();
        this.mContext = mContext;
        mylist = itemArray;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public String getItem(int position) {
        return mylist.get(position).toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {

        private TextView event_date, event_time, event_name, event_id;
        private TextView event_address, event_price;
        private LinearLayout event_img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder view;
        LayoutInflater inflator = null;
        if (convertView == null) {
            view = new ViewHolder();
            try {
                inflator = ((Activity) mContext).getLayoutInflater();
                convertView = inflator.inflate(R.layout.event_list, null);

                view.event_id = (TextView) convertView.findViewById(R.id.event_id);
                view.event_date = (TextView) convertView.findViewById(R.id.event_date);
                view.event_name = (TextView) convertView.findViewById(R.id.event_name);
                view.event_time = (TextView) convertView.findViewById(R.id.event_time);
                view.event_price = (TextView) convertView.findViewById(R.id.event_price);
                view.event_img = (LinearLayout) convertView.findViewById(R.id.event_img);
                view.event_address = (TextView) convertView.findViewById(R.id.event_address);

                convertView.setTag(view);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            view = (ViewHolder) convertView.getTag();
        }
        try {

            view.event_id.setTag(position);
            view.event_id.setText(mylist.get(position).getId());
            view.event_date.setText("Date :"+mylist.get(position).getDate());
            view.event_name.setText(mylist.get(position).getTitle());
            view.event_time.setText("Time :"+mylist.get(position).getTime());
            view.event_price.setText("Fee :"+mylist.get(position).getFee());
            view.event_img.setBackgroundResource(R.drawable.download);
            view.event_address.setText("Address :"+mylist.get(position).getLocation());

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
