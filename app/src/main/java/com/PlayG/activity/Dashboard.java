package com.PlayG.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.TabLayout;
import com.PlayG.api.URLS;
import com.PlayG.model.VolleySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.PlayG.R;
import com.PlayG.match.Completed;
import com.PlayG.match.Live;
import com.PlayG.match.Upcoming;
import com.PlayG.menuActivities.Results;
import com.PlayG.menuActivities.Events;
import com.PlayG.model.TabAdapter;
import com.PlayG.notification.NotificationConfig;
import com.PlayG.notification.NotificationUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    TabAdapter adapters;
    TabLayout tabLayouts;
    ViewPager viewPagers;
    TextView wallet,player_name;
    Toolbar toolbar;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String tokenss = "tokens";
    String amount,tokens;

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    Intent dashboard=new Intent(Dashboard.this,Dashboard.class);
                    startActivity(dashboard);
                    finish();

                    return true;
                case R.id.navigation_dashboard:

                    Intent top_player=new Intent(Dashboard.this, Events.class);
                    startActivity(top_player);
                    finish();

                    return true;
                case R.id.navigation_notifications:

                    Intent history=new Intent(Dashboard.this, Results.class);
                    startActivity(history);
                    finish();

                    return true;
                case R.id.navigation_profile:

                    Intent profile=new Intent(Dashboard.this,Profile.class);
                    startActivity(profile);
                    finish();

                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        viewPagers = (ViewPager)findViewById(R.id.salaryview);
        tabLayouts = (TabLayout) findViewById(R.id.salarytab);
        adapters = new TabAdapter(this.getSupportFragmentManager());
        adapters.addFragment(new Upcoming(), "UPCOMING");
        adapters.addFragment(new Live(), "LIVE");
        adapters.addFragment(new Completed(), "COMPLETED");
        viewPagers.setAdapter(adapters);

        player_name=(TextView)findViewById(R.id.player_name);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        if (tokens != null) {
            String name = prefs.getString(Name, "");
            player_name.setText("Hello "+name);
            MY_Money();
        }

        tabLayouts.setupWithViewPager(viewPagers);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(NotificationConfig.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(NotificationConfig.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(NotificationConfig.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                }
            }
        };

        wallet=(TextView)findViewById(R.id.wallet);
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wallet=new Intent(Dashboard.this,Wallet.class);
                startActivity(wallet);
                finish();
            }
        });
        displayFirebaseRegId();
    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(NotificationConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        if (!TextUtils.isEmpty(regId))
            Log.e("Push notification: ", regId);
        else
        Toast.makeText(getApplicationContext(), "Firebase Reg Id is not received yet! " + regId, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NotificationConfig.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NotificationConfig.PUSH_NOTIFICATION));
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }
    private void MY_Money(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_MYMONEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            amount = obj.getString("amount");
                            try {
                                if(!amount.isEmpty()) {
                                    wallet.setText(amount);
                                }else{
                                    wallet.setText("0.0");
                                }
                            }catch (NullPointerException e){
                                e.printStackTrace();
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
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Dashboard.this);
        alertDialog.setTitle("Leave application?");
        alertDialog.setMessage("Are you sure you want to leave the application?");
        alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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