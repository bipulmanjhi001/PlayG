package com.PlayG.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.PlayG.R;
import com.PlayG.api.URLS;
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

public class Sign_up extends AppCompatActivity {

    TextView txtSignIn;
    EditText mobile,email,namePUBG,name,password;
    String mobile_no,emails,namePUBGS,names,passwords;
    CardView login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtSignIn=(TextView)findViewById(R.id.txtSignIn);
        mobile=(EditText)findViewById(R.id.mobile);
        email=(EditText)findViewById(R.id.email);
        namePUBG=(EditText)findViewById(R.id.namePUBG);
        name=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.password);
        login_button=(CardView)findViewById(R.id.login_button);

        Click();
    }
    private void Click() {
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
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

            mobile_no = mobile.getText().toString();
            emails = email.getText().toString();
            namePUBGS = namePUBG.getText().toString();
            names = name.getText().toString();
            passwords = password.getText().toString();

            boolean cancel = false;
            View focusView = null;

            if (TextUtils.isEmpty(mobile_no)) {
                mobile.setError("Invalid Mobile No.");
                focusView = mobile;
                cancel = true;
            }
            if (TextUtils.isEmpty(emails)) {
                email.setError("Invalid Email");
                focusView = email;
                cancel = true;
            }
            if (TextUtils.isEmpty(namePUBGS)) {
                namePUBG.setError("Invalid PUBGName");
                focusView = namePUBG;
                cancel = true;
            }
            if (TextUtils.isEmpty(names)) {
                name.setError("Invalid Name");
                focusView = name;
                cancel = true;
            }
            if (TextUtils.isEmpty(passwords)) {
                password.setError("Invalid Password");
                focusView = password;
                cancel = true;
            }
            if (cancel) {
                focusView.requestFocus();
            } else {
                Authenticate();
            }
        }
        public void Authenticate(){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_PLAYERS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.getBoolean("status")) {

                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),OTPActivity.class);
                                    intent.putExtra("mobile",mobile_no);
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

                    params.put("name", names);
                    params.put("mobile", mobile_no);
                    params.put("email", emails);
                    params.put("pubg_username", namePUBGS);
                    params.put("password", passwords);

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
        Intent forgot=new Intent(Sign_up.this,Login.class);
        startActivity(forgot);
        finish();
    }

}
