package com.PlayG.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.PlayG.R;
import com.PlayG.api.URLS;
import com.PlayG.model.VolleySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MatchRegisteration extends AppCompatActivity {

    CardView join_button;
    String id,team,ids;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String tokenss = "tokens";
    String tokens,names,registration_fee;

    TextView match_name,win_prize,per_kill,entry_fee;
    TextView joined_player,max_player;
    RadioButton joining_as;
    EditText p_name;
    ImageView back_wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_registeration);

        match_name=(TextView)findViewById(R.id.match_name);
        win_prize=(TextView)findViewById(R.id.win_prize);
        per_kill=(TextView)findViewById(R.id.per_kill);
        entry_fee=(TextView)findViewById(R.id.entry_fee);
        joined_player=(TextView)findViewById(R.id.joined_player);
        max_player=(TextView)findViewById(R.id.max_player);
        joining_as=(RadioButton)findViewById(R.id.joining_as);
        p_name=(EditText) findViewById(R.id.p_name);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        if (tokens != null) {
            names = prefs.getString(Name, null);
            MatchDisplay();
        }

        back_wallet=(ImageView)findViewById(R.id.back_wallet);
        back_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot=new Intent(MatchRegisteration.this, Dashboard.class);
                startActivity(forgot);
                finish();
            }
        });
        join_button=(CardView)findViewById(R.id.join_button);
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent forgot=new Intent(MatchRegisteration.this, PaymentList.class);
                forgot.putExtra("id",id);
                forgot.putExtra("team",team);
                forgot.putExtra("fee",registration_fee);
                startActivity(forgot);
                finish();

            }
        });
    }
    private void MatchDisplay(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_TOURNAMENTDETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray userJson = obj.getJSONArray("message");

                            for(int i=0; i<userJson.length(); i++) {

                                JSONObject itemslist = userJson.getJSONObject(i);
                                ids = itemslist.getString("id");
                                String name = itemslist.getString("name");
                                String toornament_date=itemslist.getString("toornament_date");
                                String toornament_time=itemslist.getString("toornament_time");
                                team = itemslist.getString("team");
                                registration_fee=itemslist.getString("registration_fee");
                                String mode = itemslist.getString("mode");
                                String type=itemslist.getString("type");
                                String map = itemslist.getString("map");
                                String prize_first=itemslist.getString("prize_first");
                                String prize_second=itemslist.getString("prize_second");
                                String prize_third=itemslist.getString("prize_third");
                                String per_kills=itemslist.getString("per_kill");

                                match_name.setText(name);
                                win_prize.setText(prize_first);
                                per_kill.setText(per_kills);
                                entry_fee.setText(registration_fee);
                                joined_player.setText("50");
                                max_player.setText("100");
                                joining_as.setText(team);
                                p_name.setText(names);
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
                params.put("id", id);
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
        Intent forgot=new Intent(MatchRegisteration.this, Dashboard.class);
        startActivity(forgot);
        finish();
    }
}
