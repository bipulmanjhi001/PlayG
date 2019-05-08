package com.PlayG.settingmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.PlayG.R;
import com.PlayG.activity.Dashboard;
import com.PlayG.api.URLS;
import com.PlayG.menuActivities.Settings;
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

public class EditPlayer extends AppCompatActivity {
    ImageView back_historyss;
    EditText mobile,email,namePUBG,name;
    String mobiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);

        back_historyss=(ImageView)findViewById(R.id.back_historyses);
        back_historyss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent top_player=new Intent(EditPlayer.this, Settings.class);
                startActivity(top_player);
                finish();
            }
        });
    }
    private void attemptOTP() {
        mobiles = mobile.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(mobiles)) {
            mobile.setError("Invalid OTP");
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

                                Intent intent=new Intent(getApplicationContext(), Dashboard.class);
                                intent.putExtra("mobile",mobile);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                intent.putExtra("pubg_username",pubg_username);
                                intent.putExtra("token",token);
                                startActivity(intent);
                                finish();


                               /* SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Name, name);
                                editor.putString(Phone, mobile);
                                editor.putString(Email, email);
                                editor.putString(Username, pubg_username);
                                editor.putString(tokens, token);
                                editor.apply();*/

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
              //  params.put("otp", otp_numbers);
              //  params.put("mobile", value);
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
        Intent forgot=new Intent(EditPlayer.this, Settings.class);
        startActivity(forgot);
        finish();
    }
}
