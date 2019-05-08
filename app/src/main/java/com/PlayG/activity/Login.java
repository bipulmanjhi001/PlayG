package com.PlayG.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Login extends AppCompatActivity  {

    TextView txtRegister,forgot_password;
    CardView sign_in_button;
    EditText mobile_number,login_password;
    String mobile_numbers,login_passwords;

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
        setContentView(R.layout.activity_login);

        forgot_password=(TextView)findViewById(R.id.forgot_password);
        txtRegister=(TextView)findViewById(R.id.txtRegister);
        sign_in_button=(CardView) findViewById(R.id.sign_in_button);
        mobile_number=(EditText) findViewById(R.id.mobile_number);
        login_password=(EditText) findViewById(R.id.login_password);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Click();

    }
    private void Click(){
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign_up=new Intent(Login.this,Sign_up.class);
                startActivity(sign_up);
                finish();
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot=new Intent(Login.this,Forgot_Password.class);
                startActivity(forgot);
                finish();
            }
        });
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

        mobile_numbers = mobile_number.getText().toString();
        login_passwords = login_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(mobile_numbers)) {
            mobile_number.setError("Invalid Mobile No.");
            focusView = mobile_number;
            cancel = true;
        }
        if (!TextUtils.isEmpty(login_passwords) && !isPasswordValid(login_passwords)) {
            login_password.setError(getString(R.string.error_invalid_password));
            focusView = login_password;
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_LOGIN,
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
                params.put("mobile", mobile_numbers);
                params.put("password", login_passwords);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
        alertDialog.setTitle("Leave application?");
        alertDialog.setMessage("Are you sure you want to leave the application?");
        alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Login.this.finish();
                    }
                });
        alertDialog.setNegativeButton("NO",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}

