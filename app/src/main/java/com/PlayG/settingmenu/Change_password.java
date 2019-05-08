package com.PlayG.settingmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.PlayG.R;
import com.PlayG.activity.Dashboard;
import com.PlayG.api.URLS;
import com.PlayG.menuActivities.Settings;
import com.PlayG.model.ConnectivityReceiver;
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

public class Change_password extends AppCompatActivity {

    ImageView back_settingss;
    EditText new_password,confirm_password;
    String new_passwords,confirm_passwords,tokens;
    CardView sign_in_button;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String tokenss = "tokens";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        back_settingss=(ImageView)findViewById(R.id.back_settingss);
        back_settingss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent top_player=new Intent(Change_password.this, Settings.class);
                startActivity(top_player);
                finish();
            }
        });

        new_password=(EditText)findViewById(R.id.new_password);
        confirm_password=(EditText)findViewById(R.id.confirm_password);
        sign_in_button=(CardView)findViewById(R.id.sign_in_button);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        if (tokens != null) {
            Log.d("tokens",tokens);
        }

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });
    }
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack ( boolean isConnected){
        String message;
        if (isConnected) {
            attemptLogin();
        } else {
            message = "connect your internet.";
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }
    private void attemptLogin() {

        new_passwords = new_password.getText().toString();
        confirm_passwords = confirm_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(new_passwords)  && !isPasswordValid(confirm_passwords)) {
            new_password.setError(getString(R.string.error_field_required));
            focusView = new_password;
            cancel = true;
        }
        if (!TextUtils.isEmpty(confirm_passwords) && !isPasswordValid(confirm_passwords)) {
            confirm_password.setError(getString(R.string.error_invalid_password));
            focusView = confirm_password;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            Authenticate();
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void Authenticate(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_UPDATE_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getBoolean("status")) {

                                Intent intent=new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(intent);
                                finish();

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
                params.put("password", confirm_passwords);
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
        Intent forgot=new Intent(Change_password.this, Settings.class);
        startActivity(forgot);
        finish();
    }
}
