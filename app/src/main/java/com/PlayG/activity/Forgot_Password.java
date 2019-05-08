package com.PlayG.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

public class Forgot_Password extends AppCompatActivity {

    CardView reset_button;
    EditText mobile;
    String mobile_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        reset_button=(CardView)findViewById(R.id.reset_button);
        mobile=(EditText)findViewById(R.id.mobile);
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptForgot();
            }
        });
    }
    private void attemptForgot() {
        mobile_no = mobile.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(mobile_no)) {
            mobile.setError(getString(R.string.error_incorrect_phone));
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_FORGOT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getBoolean("status")) {

                                Intent intent=new Intent(getApplicationContext(),OTPActivity.class);
                                intent.putExtra("mobile",mobile_no);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(),obj.getString("message"), Toast.LENGTH_SHORT).show();

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
                params.put("mobile", mobile_no);
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
        Intent forgot=new Intent(Forgot_Password.this,Login.class);
        startActivity(forgot);
        finish();
    }
}
