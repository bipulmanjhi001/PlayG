package com.PlayG.settingmenu;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.PlayG.R;
import com.PlayG.menuActivities.Settings;

public class Privacy_policy extends AppCompatActivity {

    TextView normail_text;
    WebView wb;
    ImageView back_historysp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy2);

        normail_text=(TextView)findViewById(R.id.normail_text);
        wb = (WebView) findViewById(R.id.webView);
        wb.getSettings().setJavaScriptEnabled(true);

        back_historysp=(ImageView)findViewById(R.id.back_historysp);
        back_historysp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent top_player=new Intent(Privacy_policy.this, Settings.class);
                startActivity(top_player);
                finish();

            }
        });

        wb.getSettings().setLoadWithOverviewMode(true);
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
        Intent forgot=new Intent(Privacy_policy.this, Settings.class);
        startActivity(forgot);
        finish();
    }
}
