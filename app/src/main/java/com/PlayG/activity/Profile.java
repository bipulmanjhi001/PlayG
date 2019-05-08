package com.PlayG.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.PlayG.R;
import com.PlayG.menuActivities.Settings;

public class Profile extends AppCompatActivity {

    ImageView back_profile;
    TextView profile_settings,sign_out;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String Phone = "Phone";
    public static final String Email = "Email";
    public static final String Username = "Username";
    public static final String tokenss = "tokens";

    TextView names,user_name,mobileNumber,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back_profile=(ImageView)findViewById(R.id.back_profile);
        back_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile=new Intent(Profile.this,Dashboard.class);
                startActivity(profile);
                finish();
            }
        });

        names=(TextView)findViewById(R.id.name);
        user_name=(TextView)findViewById(R.id.user_name);
        mobileNumber=(TextView)findViewById(R.id.mobileNumber);
        email=(TextView)findViewById(R.id.email);

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String tokens = prefs.getString(tokenss, null);
        if (tokens != null) {
            String Usernames = prefs.getString(Username, "");
            names.setText(Usernames);
            String Phones = prefs.getString(Phone, "");
            mobileNumber.setText(Phones);
            String Emails = prefs.getString(Email, "");
            email.setText(Emails);
            String name = prefs.getString(Name, "");
            user_name.setText(name);

        }

        profile_settings=(TextView) findViewById(R.id.profile_settings);
        profile_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent setting=new Intent(Profile.this, Settings.class);
                startActivity(setting);
                finish();
            }
        });

        sign_out=(TextView) findViewById(R.id.sign_out);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sign_out=new Intent(Profile.this, Login.class);
                startActivity(sign_out);
                finish();
                SharedPreferences settings = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                settings.edit().clear().apply();

            }
        });
    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        Intent profile=new Intent(Profile.this,Dashboard.class);
        startActivity(profile);
        finish();
    }
}
