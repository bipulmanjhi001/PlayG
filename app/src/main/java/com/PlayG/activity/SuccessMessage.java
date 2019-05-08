package com.PlayG.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import com.PlayG.R;

public class SuccessMessage extends AppCompatActivity {
    CardView return_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_message);

        return_button=(CardView)findViewById(R.id.return_button);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile=new Intent(SuccessMessage.this,Dashboard.class);
                startActivity(profile);
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
        Intent forgot=new Intent(SuccessMessage.this,Dashboard.class);
        startActivity(forgot);
        finish();
    }
}
