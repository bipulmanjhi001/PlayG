package com.PlayG.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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

public class OTPActivity extends AppCompatActivity {

    CardView submit_button;
    EditText otp_number;
    String otp_numbers,value;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String Phone = "Phone";
    public static final String Email = "Email";
    public static final String Username = "Username";
    public static final String tokens = "tokens";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        value=getIntent().getStringExtra("mobile");
        otp_number=(EditText)findViewById(R.id.otp_number);

        submit_button=(CardView)findViewById(R.id.submit_button);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptOTP();
            }
        });
    }

    private void attemptOTP() {
        otp_numbers = otp_number.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(otp_numbers)) {
            otp_number.setError("Invalid OTP");
            focusView = otp_number;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            Authenticate();
        }
    }

    public void Authenticate(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_OTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getBoolean("status")) {

                                JSONObject userJson = obj.getJSONObject("user");
                                String mobile=userJson.getString("mobile");
                                String name=userJson.getString("name");
                                String email=userJson.getString("email");
                                String pubg_username=userJson.getString("pubg_username");
                                String token=userJson.getString("token");

                                Intent intent=new Intent(getApplicationContext(),Dashboard.class);
                                intent.putExtra("mobile",mobile);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                intent.putExtra("pubg_username",pubg_username);
                                intent.putExtra("token",token);
                                startActivity(intent);
                                finish();

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Name, name);
                                editor.putString(Phone, mobile);
                                editor.putString(Email, email);
                                editor.putString(Username, pubg_username);
                                editor.putString(tokens, token);

                                editor.apply();

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
                params.put("otp", otp_numbers);
                params.put("mobile", value);
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
        Intent forgot=new Intent(OTPActivity.this,Login.class);
        startActivity(forgot);
        finish();
    }

}
