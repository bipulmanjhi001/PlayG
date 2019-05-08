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

public class TransactionAdpater extends BaseAdapter {
    private Context mContext;
    private ArrayList<TransactionList> mylist = new ArrayList<>();

    public TransactionAdpater(ArrayList<TransactionList> itemArray, Context mContext) {
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
        private TextView order_id, amount, date, id,time;
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
                convertView = inflator.inflate(R.layout.transaction_list, null);
                view.order_id = (TextView) convertView.findViewById(R.id.order_id);
                view.amount = (TextView) convertView.findViewById(R.id.amount);
                view.date = (TextView) convertView.findViewById(R.id.date);
                view.time = (TextView) convertView.findViewById(R.id.time);

                convertView.setTag(view);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            view = (ViewHolder) convertView.getTag();
        }
        try {
            view.order_id.setText(mylist.get(position).getOrder_id());
            view.amount.setText("Amount "+mylist.get(position).getAmount());
            view.date.setText("Date "+mylist.get(position).getDate());
            view.time.setText("Time "+mylist.get(position).getTime());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}