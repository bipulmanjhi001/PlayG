package com.PlayG.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.PlayG.R;
import com.PlayG.model.ConnectivityReceiver;
import com.PlayG.model.PubgApplication;
import com.PlayG.pref.PrefManager;

public class Splashscreen extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    private PrefManager prefManager;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String tokenss = "tokens";
    String tokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        if (tokens != null) {
            String name = prefs.getString(Name, "");
        }
        checkConnection();
    }
        private void checkConnection() {
            boolean isConnected = ConnectivityReceiver.isConnected();
            showSnack(isConnected);
        }

        private void showSnack(boolean isConnected) {
            String message;
            int color;
            if (isConnected) {
                Thread timer = new Thread() {
                    public void run() {
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        } finally {
                            prefManager = new PrefManager(Splashscreen.this);
                            if (!prefManager.isFirstTimeLaunch() && !TextUtils.isEmpty(tokens)) {
                                Intent openMain = new Intent(Splashscreen.this, Dashboard.class);
                                startActivity(openMain);
                                finish();
                            }
                            else if(!prefManager.isFirstTimeLaunch() && TextUtils.isEmpty(tokens)){
                                Intent openMain = new Intent(Splashscreen.this, Login.class);
                                startActivity(openMain);
                                finish();
                            }
                            else {
                                Intent openMain = new Intent(Splashscreen.this, Introscreen.class);
                                startActivity(openMain);
                                finish();
                            }
                        }
                    }
                };
                timer.start();

            } else  {
                message = "connect your internet.";
                color = Color.RED;
                Toast toast=Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT);
                toast.show();

                Intent openMain = new Intent(Splashscreen.this, Splashscreen.class);
                startActivity(openMain);
                finish();
            }
        }

        @Override
        protected void onResume() {
            super.onResume();
            PubgApplication.getInstance().setConnectivityListener(this);
        }
        @Override
        public void onNetworkConnectionChanged(boolean isConnected) {
            showSnack(isConnected);
        }
        @Override
        protected void onPause() {
            super.onPause();
            finish();
        }
        @Override
        public void onBackPressed() {
            backButtonHandler();
            return;
        }

        public void backButtonHandler() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Splashscreen.this);
            alertDialog.setTitle("Leave application?");
            alertDialog.setMessage("Are you sure you want to leave the application?");
            alertDialog.setIcon(R.mipmap.ic_launcher);
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Splashscreen.this.finish();
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