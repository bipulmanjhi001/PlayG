package com.PlayG.settingmenu;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.PlayG.R;
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

public class Contact_us extends AppCompatActivity {

    ImageView back_historyss;
    EditText message_contact,title_contact;
    String message_contacts,title_contacts,tokens;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String tokenss = "tokens";
    CardView submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        title_contact=(EditText)findViewById(R.id.title_contact);
        message_contact=(EditText)findViewById(R.id.message_contact);
        back_historyss=(ImageView)findViewById(R.id.back_historyss);

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        submit_button=(CardView)findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        back_historyss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent top_player=new Intent(Contact_us.this, Settings.class);
                startActivity(top_player);
                finish();

            }
        });
    }
    private void attemptLogin() {
        title_contacts = title_contact.getText().toString();
        message_contacts = message_contact.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(title_contacts)) {
            title_contact.setError("Enter your Title");
            focusView = title_contact;
            cancel = true;
        }
        if (!TextUtils.isEmpty(message_contacts)) {
            message_contact.setError("Enter your Message");
            focusView = message_contact;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            Authenticate();
        }
    }

    public void Authenticate(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_CONTACTUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("status")) {

                                title_contact.setText("");
                                message_contact.setText("");

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
                params.put("title", title_contacts);
                params.put("message", message_contacts);
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
        Intent forgot=new Intent(Contact_us.this, Settings.class);
        startActivity(forgot);
        finish();
    }
}
