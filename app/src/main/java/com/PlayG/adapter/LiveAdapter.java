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

public class LiveAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ViewLiveList> mylist = new ArrayList<>();

    public LiveAdapter(ArrayList<ViewLiveList> itemArray, Context mContext) {
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

        private TextView txtChikenDinner, txtType, txtVersion, id,txtPerKill,txtEntryFees,txtJoinMatch;
        private TextView txtMap, txtDate, txtMatchNo,txtRoomID,txtPassword,txtRemainingTime;
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
                convertView = inflator.inflate(R.layout.live_list, null);

                view.id = (TextView) convertView.findViewById(R.id.id);
                view.txtChikenDinner = (TextView) convertView.findViewById(R.id.txtChikenDinner);

                view.txtType = (TextView) convertView.findViewById(R.id.txtType);
                view.txtVersion = (TextView) convertView.findViewById(R.id.txtVersion);

                view.txtMap = (TextView) convertView.findViewById(R.id.txtMap);
                view.txtDate = (TextView) convertView.findViewById(R.id.txtDate);

                view.txtMatchNo = (TextView) convertView.findViewById(R.id.txtMatchNo);
                view.txtPerKill = (TextView) convertView.findViewById(R.id.txtPerKill);

                view.txtEntryFees = (TextView) convertView.findViewById(R.id.txtEntryFees);
                view.txtRoomID = (TextView) convertView.findViewById(R.id.txtRoomID);

                view.txtPassword = (TextView) convertView.findViewById(R.id.txtPassword);
                view.txtRemainingTime = (TextView) convertView.findViewById(R.id.txtRemainingTime);
                view.txtJoinMatch = (TextView) convertView.findViewById(R.id.txtJoinMatch);

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
            view.txtChikenDinner.setText(mylist.get(position).getF_prize());
            view.txtType.setText(mylist.get(position).getType());
            view.txtVersion.setText(mylist.get(position).getMode());
            view.txtMap.setText(mylist.get(position).getMap());
            view.txtDate.setText(mylist.get(position).getToornament_date());
            view.txtMatchNo.setText(mylist.get(position).getName());

            view.txtPerKill.setText(mylist.get(position).getPer_kill());
            view.txtEntryFees.setText(mylist.get(position).getRegistration_fee());
            view.txtRoomID.setText(mylist.get(position).getRoom_id());
            view.txtPassword.setText(mylist.get(position).getPassword());
            view.txtRemainingTime.setText(mylist.get(position).getToornament_time());

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return convertView;
    }


}