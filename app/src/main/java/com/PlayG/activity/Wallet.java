package com.PlayG.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.PlayG.R;
import com.PlayG.adapter.TransactionAdpater;
import com.PlayG.adapter.TransactionList;
import com.PlayG.api.URLS;
import com.PlayG.model.VolleySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet extends AppCompatActivity {

    LinearLayout add_money,withdraw_money;
    ImageView back_wallet;
    ListView wallet_lsit;
    TextView money;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String tokenss = "tokens";
    String tokens,amount;

    ProgressBar live_list_pro;
    ArrayList<TransactionList> transactionLists;
    TransactionAdpater adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        live_list_pro=(ProgressBar)findViewById(R.id.live_list_pro);
        add_money=(LinearLayout)findViewById(R.id.add_money);
        add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot=new Intent(Wallet.this,AddFund.class);
                startActivity(forgot);
                finish();
            }
        });

        money=(TextView)findViewById(R.id.money);
        wallet_lsit=(ListView)findViewById(R.id.wallet_list);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        if (tokens != null) {
            MY_Money();
            Withdraw_Money();
        }

        transactionLists=new ArrayList<TransactionList>();
        back_wallet=(ImageView)findViewById(R.id.back_wallet);
        back_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot=new Intent(Wallet.this,Dashboard.class);
                startActivity(forgot);
                finish();
            }
        });

        withdraw_money=(LinearLayout)findViewById(R.id.withdraw_money);
        withdraw_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot=new Intent(Wallet.this,Withdraw.class);
                startActivity(forgot);
                finish();
            }
        });
    }
    private void MY_Money(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_MYMONEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         try {
                             JSONObject obj = new JSONObject(response);
                                amount = obj.getString("amount");
                             try {
                                 if(!amount.isEmpty()) {
                                     money.setText(amount);
                                 }else{
                                     money.setText("0.0");
                                 }
                             }catch (NullPointerException e){
                                 e.printStackTrace();
                             }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
            {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", tokens);
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void Withdraw_Money(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_MYWALLET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONArray userJson = obj.getJSONArray("wallet");

                                for (int i = 0; i < userJson.length(); i++) {

                                    JSONObject walletlist = userJson.getJSONObject(i);
                                    String order_id = walletlist.getString("id");
                                    String amount = walletlist.getString("amount");
                                    String date = walletlist.getString("date");
                                    String time = walletlist.getString("time");

                                    TransactionList transactionList = new TransactionList();
                                    transactionList.setOrder_id(order_id);
                                    transactionList.setAmount(amount);
                                    transactionList.setDate(date);
                                    transactionList.setTime(time);
                                    transactionLists.add(transactionList);
                                }

                                } catch(JSONException e){
                                    e.printStackTrace();
                                }
                                try {
                                    live_list_pro.setVisibility(View.GONE);
                                    adapter = new TransactionAdpater(transactionLists, Wallet.this);
                                    wallet_lsit.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", tokens);
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }
    public void backButtonHandler() {
        Intent forgot=new Intent(Wallet.this,Dashboard.class);
        startActivity(forgot);
        finish();
    }
}
