package com.PlayG.menuActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.PlayG.R;
import com.PlayG.activity.Dashboard;

public class Results extends AppCompatActivity {
    ImageView back_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        back_history=(ImageView)findViewById(R.id.back_history);
        back_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent top_player=new Intent(Results.this, Dashboard.class);
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
        Intent forgot=new Intent(Results.this, Dashboard.class);
        startActivity(forgot);
        finish();
    }
}
