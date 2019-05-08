package com.PlayG.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.PlayG.R;
import java.util.ArrayList;

public class UpcomingAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ViewUpcomingList> mylist = new ArrayList<>();

    public UpcomingAdapter(ArrayList<ViewUpcomingList> itemArray, Context mContext) {
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

        private TextView txtChikenDinner, txtType, txtVersion, id,txtJoinMatch;
        private TextView txtMap, txtDate, txtMatchNo,txttypes,txttime;
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
                convertView = inflator.inflate(R.layout.upcoming_list, null);

                view.id = (TextView) convertView.findViewById(R.id.id_upcoming);
                view.txtChikenDinner = (TextView) convertView.findViewById(R.id.txtChikenDinner);
                view.txtType = (TextView) convertView.findViewById(R.id.txtType);
                view.txtVersion = (TextView) convertView.findViewById(R.id.txtVersion);
                view.txtMap = (TextView) convertView.findViewById(R.id.txtMap);
                view.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
                view.txtMatchNo = (TextView) convertView.findViewById(R.id.txtMatchNo);

                view.txttime = (TextView) convertView.findViewById(R.id.txttime);
                view.txttypes = (TextView) convertView.findViewById(R.id.txttypes);
                view.txtJoinMatch=(TextView) convertView.findViewById(R.id.txtJoinMatch);

                convertView.setTag(view);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            view = (ViewHolder) convertView.getTag();
        }
        try {

            view.id.setTag(position);
            view.id.setText(mylist.get(position).getId());
            view.txtChikenDinner.setText(mylist.get(position).getResgistration());
            view.txtType.setText(mylist.get(position).getTeam());
            view.txtVersion.setText(mylist.get(position).getMode());
            view.txtMap.setText(mylist.get(position).getMap());
            view.txtDate.setText(mylist.get(position).getTournament_date());
            view.txtMatchNo.setText(mylist.get(position).getName());
            view.txttime.setText(mylist.get(position).getToornament_time());
            view.txttypes.setText(mylist.get(position).getType());

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
