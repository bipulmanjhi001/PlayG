package com.PlayG.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.PlayG.R;
import com.PlayG.pref.PrefManager;

public class PrivacyPolicy extends AppCompatActivity {

    Typeface normalFont;
    TextView title_text,normail_text;
    FloatingActionButton floatingActionButton;
    WebView wb;
    CheckBox accept_terms;
    private PrefManager prefManager;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(false);
        }
        setContentView(R.layout.activity_privacy_policy);

        title_text=(TextView)findViewById(R.id.title_text);
        title_text.setText("Privacy Policy");
        title_text.setTextSize(18);
        title_text.setTextColor(Color.parseColor("#FFFFFF"));
        normalFont = Typeface.createFromAsset(getAssets(),"lato_regular.ttf");
        title_text.setTypeface(normalFont);

        normail_text=(TextView)findViewById(R.id.normail_text);
        wb = (WebView) findViewById(R.id.webView);
        wb.getSettings().setJavaScriptEnabled(true);

        wb.getSettings().setLoadWithOverviewMode(true);
        accept_terms=(CheckBox)findViewById(R.id.accept_terms);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.agree_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(floatingActionButton, "rotation", 0f, 360f).setDuration(800).start();
                if(accept_terms.isChecked()){
                    Intent openMain = new Intent(PrivacyPolicy.this, Login.class);
                    startActivity(openMain);
                    finish();
                }else {
                    Toast.makeText(PrivacyPolicy.this, "Please,Accept Terms and Conditions.", Toast.LENGTH_SHORT).show();
                }
            }
        });

         wb.getSettings().setUseWideViewPort(true);
         wb.getSettings().setBuiltInZoomControls(true);

         wb.getSettings().setPluginState(WebSettings.PluginState.ON);
         wb.loadUrl("http://gamersplayg.in/Privacy_policies");
         wb.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
               wb.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PrivacyPolicy.this);
        alertDialog.setTitle("Leave application?");
        alertDialog.setMessage("Are you sure you want to leave the application?");
        alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PrivacyPolicy.this.finish();
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
