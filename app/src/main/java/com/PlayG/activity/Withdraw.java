package com.PlayG.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

public class Withdraw extends AppCompatActivity {
    ImageView imgBack;
    EditText edtMoney,mobile,upiID;
    String edtMoneys,mobiles,upiIDs;
    LinearLayout withdrawCard;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String tokenss = "tokens";
    String tokens,mode;
    RadioButton radioPayTm,radioPhonePe,radioGooglePay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot=new Intent(Withdraw.this,Wallet.class);
                startActivity(forgot);
                finish();
            }
        });

        withdrawCard=(LinearLayout)findViewById(R.id.withdrawCard);
        withdrawCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        edtMoney=(EditText)findViewById(R.id.edtMoney);
        mobile=(EditText)findViewById(R.id.mobile);
        upiID=(EditText)findViewById(R.id.upiID);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        radioGooglePay=(RadioButton)findViewById(R.id.radioGooglePay);
        radioGooglePay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(radioGooglePay.isChecked()){
                    radioPayTm.setChecked(false);
                    radioPhonePe.setChecked(false);
                    mode="GooglePay";
                }
            }
        });
        radioPayTm=(RadioButton)findViewById(R.id.radioPayTm);
        radioPayTm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(radioPayTm.isChecked()){
                    radioGooglePay.setChecked(false);
                    radioPhonePe.setChecked(false);
                    mode="PayTm";
                }
            }
        });
        radioPhonePe=(RadioButton)findViewById(R.id.radioPhonePe);
        radioPhonePe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(radioPhonePe.isChecked()){
                    radioGooglePay.setChecked(false);
                    radioPayTm.setChecked(false);
                    mode="PhonePay";
                }
            }
        });

    }
    private void attemptLogin() {
        edtMoneys = edtMoney.getText().toString();
        mobiles = mobile.getText().toString();
        upiIDs = upiID.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(edtMoneys)) {
            edtMoney.setError("Enter Amount");
            focusView = edtMoney;
            cancel = true;
        }
        if (TextUtils.isEmpty(mobiles)) {
            mobile.setError("Invalid Mobile No.");
            focusView = mobile;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            Authenticate();
        }
    }
    public void Authenticate(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_WITHDRAWMONEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("status")) {

                                edtMoney.setText("");
                                mobile.setText("");
                                upiID.setText("");

                            } else if(!obj.getBoolean("status")) {

                                String error=obj.getString("error");
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getApplicationContext(), "Connection error..", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Check again..", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", tokens);
                params.put("amount", edtMoneys);
                params.put("mode", mode);
                params.put("mobile", mobiles);
                params.put("upi", upiIDs);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        Intent forgot=new Intent(Withdraw.this,Wallet.class);
        startActivity(forgot);
        finish();
    }
}
