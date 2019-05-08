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


public class JoinNow extends AppCompatActivity {
    ImageView imgBack;
    TextView txtBalance,txtFees,txtRemaining;
    CardView joinCard;
    String id,value,team,team_leader="";
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String tokenss = "tokens";

    public static final String MyPREFERENCES2 = "MyPrefs2" ;
    public static final String Fee = "fee";
    String tokens,names,fee;
    int n1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_now);

        value=getIntent().getStringExtra("fee");
        id=getIntent().getStringExtra("id");
        team=getIntent().getStringExtra("team");

        txtBalance=(TextView)findViewById(R.id.txtBalance);

        SharedPreferences prefs2 = getSharedPreferences(MyPREFERENCES2, MODE_PRIVATE);
        fee = prefs2.getString(Fee, null);

        txtFees=(TextView)findViewById(R.id.txtFees);
        txtFees.setText(value);
        try {
            if (fee != null) {

                txtBalance.setText(fee);
            }
            n1 = Integer.parseInt(value) - Integer.parseInt(fee);
            txtRemaining.setText(String.valueOf(n1));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        txtRemaining=(TextView)findViewById(R.id.txtRemaining);
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent top_player=new Intent(JoinNow.this, Dashboard.class);
                startActivity(top_player);
                finish();

            }
        });

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        if (tokens != null) {
            names = prefs.getString(Name, null);
        }

        joinCard=(CardView)findViewById(R.id.joinCard);
        joinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPayment(v);
            }
        });
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
                                    Intent top_player=new Intent(JoinNow.this, SuccessMessage.class);
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
    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        Intent forgot=new Intent(JoinNow.this,Dashboard.class);
        startActivity(forgot);
        finish();
    }
}
