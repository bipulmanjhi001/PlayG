package com.PlayG.menuActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.PlayG.R;
import com.PlayG.activity.Profile;
import com.PlayG.settingmenu.Change_password;
import com.PlayG.settingmenu.Contact_us;
import com.PlayG.settingmenu.EditPlayer;
import com.PlayG.settingmenu.Privacy_policy;

public class Settings extends AppCompatActivity {

    ImageView back_setting;
    LinearLayout edit_profile,change_password,contact_us,privacy_policy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        back_setting=(ImageView)findViewById(R.id.back_setting);

        back_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent top_player=new Intent(Settings.this, Profile.class);
                startActivity(top_player);
                finish();
            }
        });
        edit_profile=(LinearLayout) findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent top_player=new Intent(Settings.this, EditPlayer.class);
                startActivity(top_player);
                finish();
            }
        });
        change_password=(LinearLayout) findViewById(R.id.change_password);

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent top_player=new Intent(Settings.this, Change_password.class);
                startActivity(top_player);
                finish();
            }
        });
        contact_us=(LinearLayout) findViewById(R.id.contact_us);
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent top_player=new Intent(Settings.this, Contact_us.class);
                startActivity(top_player);
                finish();
            }
        });
        privacy_policy=(LinearLayout) findViewById(R.id.privacy_policy);
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent top_player=new Intent(Settings.this, Privacy_policy.class);
                startActivity(top_player);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        Intent forgot=new Intent(Settings.this, Profile.class);
        startActivity(forgot);
        finish();
    }
}
