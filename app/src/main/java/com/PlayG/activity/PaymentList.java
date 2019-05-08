package com.PlayG.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.PlayG.R;
import com.PlayG.api.URLS;
import com.PlayG.model.VolleySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class PaymentList extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String tokenss = "tokens";
    String tokens,value,id,team,team_leader="",amount;
    CardView instamojo_button;
    TextView wallet_balance;
    ImageView back_historyss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);

        instamojo_button=(CardView)findViewById(R.id. instamojo_button);
        instamojo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPayment(v);
            }
        });
        back_historyss=(ImageView)findViewById(R.id.back_historyss);
        back_historyss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent top_player=new Intent(PaymentList.this, Dashboard.class);
                startActivity(top_player);
                finish();
            }
        });

        value=getIntent().getStringExtra("fee");
        id=getIntent().getStringExtra("id");
        team=getIntent().getStringExtra("team");

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        if (tokens != null) {
            MY_Money();
        }
        wallet_balance=(TextView)findViewById(R.id.wallet_balance);

    }

    public void doPayment(View v){

        String email="gaurav.ranchi91@gmail.com";
        String phone="7004895809";
        String amount=value;
        String purpose="donate";
        String buyername="gaurav";

    }
    private void Join_tournament(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_JOIN_TOURNAMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getBoolean("status")) {

                                String message = obj.getString("message");
                                Intent top_player=new Intent(PaymentList.this, SuccessMessage.class);
                                startActivity(top_player);
                                finish();
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
                params.put("t_id", id);
                params.put("team", team);
                params.put("team_leader", team_leader);
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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
                                    wallet_balance.setText(amount);
                                }else{
                                    wallet_balance.setText("0.0");
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
}

