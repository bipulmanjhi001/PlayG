package com.PlayG.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.PlayG.R;
import com.PlayG.api.URLS;
import com.PlayG.model.VolleySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.easebuzz.payment.kit.PWECouponsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import datamodels.StaticDataModel;


public class AddFund extends AppCompatActivity {

    EditText add_balance;
    LinearLayout fivehundred,fifty,hundred,twohundred;
    ImageView back_wallet;
    Button add_moneys;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES2 = "MyPrefs2" ;
    public static final String Fund = "fund";

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String tokenss = "tokens";
    String tokens,responses;

    int range = 9;
    int length = 4;
    int randomNumber,randomNumber2;
    int range2 = 9;
    int length2 = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fund);

        add_balance=(EditText)findViewById(R.id.add_balance);
        fivehundred=(LinearLayout) findViewById(R.id.fivehundred);
        fivehundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_balance.setText("1000");
            }
        });
        generateRandomNumber();
        generateRandomNumber2();

        sharedpreferences = getSharedPreferences(MyPREFERENCES2, Context.MODE_PRIVATE);
        fifty=(LinearLayout) findViewById(R.id.fifty);

        fifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_balance.setText("100");
            }
        });

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        hundred=(LinearLayout) findViewById(R.id.hundred);
        hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_balance.setText("200");
            }
        });

        twohundred=(LinearLayout) findViewById(R.id.twohundred);
        twohundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_balance.setText("500");
            }
        });

        back_wallet=(ImageView)findViewById(R.id.back_wallet);
        back_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wallet=new Intent(AddFund.this,Dashboard.class);
                startActivity(wallet);
                finish();
            }
        });

        add_moneys=(Button)findViewById(R.id.add_moneys);
        add_moneys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(add_balance.getText().toString())>= 50) {
                    doPayment(v);
                }else {
                    Toast.makeText(getApplicationContext(), "Add Fund should be greater then Rs.50", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void doPayment(View v){

        String email="gaurav.ranchi91@gmail.com";
        String phone="7004895809";
        String amount=add_balance.getText().toString();
        String buyername="gaurav";

        String tx_id=String.valueOf(randomNumber);
        Log.d("value",tx_id);

        Float value=Float.parseFloat(amount);
        Log.d("value",String.valueOf(value));

        String u_id=String.valueOf(randomNumber2);
        Log.d("value",u_id);

        Intent intentProceed = new Intent(AddFund.this, PWECouponsActivity.class);
        intentProceed.putExtra("trxn_id",tx_id);
        intentProceed.putExtra("trxn_amount",value);
        intentProceed.putExtra("trxn_prod_info","PLAYPUBG");
        intentProceed.putExtra("trxn_firstname",buyername);
        intentProceed.putExtra("trxn_email_id",email);
        intentProceed.putExtra("trxn_phone",phone);
        intentProceed.putExtra("trxn_key","DTVOU9PM99");
        intentProceed.putExtra("trxn_udf1","");
        intentProceed.putExtra("trxn_udf2","");
        intentProceed.putExtra("trxn_udf3","");
        intentProceed.putExtra("trxn_udf4","");
        intentProceed.putExtra("trxn_udf5","");
        intentProceed.putExtra("trxn_address1","Ranchi");
        intentProceed.putExtra("trxn_address2","Ranchi");
        intentProceed.putExtra("trxn_city","Ranchi");
        intentProceed.putExtra("trxn_state","Jharkhand");
        intentProceed.putExtra("trxn_country","India");
        intentProceed.putExtra("trxn_zipcode","834010");
        intentProceed.putExtra("trxn_salt","M9ZYVHFWFQ");
        intentProceed.putExtra("unique_id",u_id);
        intentProceed.putExtra("pay_mode","online");
       // intentProceed.putExtra("hash","ce2d0588f8648c62db86475d343d3433d00b87827502c676a093730f04cec5fea2eb0e8bb");
        startActivityForResult(intentProceed, StaticDataModel.PWE_REQUEST_CODE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String result = data.getStringExtra("result");
        String response = data.getStringExtra("payment_response");
    }
    public int generateRandomNumber() {
        SecureRandom secureRandom = new SecureRandom();
        String s = "";
        for (int i = 0; i < length; i++) {
            int number = secureRandom.nextInt(range);
            if (number == 0 && i == 0) {
                i = -1;
                continue;
            }
            s = s + number;
        }
        randomNumber = Integer.parseInt(s);
        return randomNumber;
    }

    public int generateRandomNumber2() {
        SecureRandom secureRandom = new SecureRandom();
        String s = "";
        for (int i = 0; i < length2; i++) {
            int number = secureRandom.nextInt(range2);
            if (number == 0 && i == 0) {
                i = -1;
                continue;
            }
            s = s + number;
        }
        randomNumber2 = Integer.parseInt(s);
        return randomNumber2;
    }

    private void Add_Money(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_ADDMONEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getBoolean("status")) {

                                String message = obj.getString("message");
                                Intent wallet = new Intent(AddFund.this, Dashboard.class);
                                startActivity(wallet);
                                finish();
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Fund, add_balance.getText().toString());
                                editor.apply();
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
                params.put("transaction_details", responses);
                params.put("amount", add_balance.getText().toString());
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
        Intent profile=new Intent(AddFund.this,Dashboard.class);
        startActivity(profile);
        finish();
    }


}
