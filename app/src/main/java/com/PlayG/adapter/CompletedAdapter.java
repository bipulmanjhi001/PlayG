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

public class CompletedAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ViewCompletedList> mylist = new ArrayList<>();

    public CompletedAdapter(ArrayList<ViewCompletedList> itemArray, Context mContext) {
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

        private TextView txtChikenDinner, txtType, txtVersion, id;
        private TextView txtMap, txtDate, txtMatchNo,txtPerKill,txtEntryFees;
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
                convertView = inflator.inflate(R.layout.completed_list, null);

                view.id = (TextView) convertView.findViewById(R.id.id);
                view.txtChikenDinner = (TextView) convertView.findViewById(R.id.txtChikenDinner);
                view.txtType = (TextView) convertView.findViewById(R.id.txtType);
                view.txtVersion = (TextView) convertView.findViewById(R.id.txtVersion);
                view.txtMap = (TextView) convertView.findViewById(R.id.txtMap);
                view.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
                view.txtMatchNo = (TextView) convertView.findViewById(R.id.txtMatchNo);
                view.txtPerKill = (TextView) convertView.findViewById(R.id.txtPerKill);
                view.txtEntryFees = (TextView) convertView.findViewById(R.id.txtEntryFees);

                convertView.setTag(view);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            view = (ViewHolder) convertView.getTag();
        }
        try {

            view.id.setTag(position);
            view.txtChikenDinner.setText(mylist.get(position).getPrize_first());
            view.txtType.setText(mylist.get(position).getTeam());
            view.txtVersion.setText(mylist.get(position).getMode());
            view.txtMap.setText(mylist.get(position).getMap());
            view.txtDate.setText(mylist.get(position).getTournament_date());
            view.txtMatchNo.setText(mylist.get(position).getName());
            view.txtPerKill.setText(mylist.get(position).getPer_kill());
            view.txtEntryFees.setText(mylist.get(position).getResgistration());

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
