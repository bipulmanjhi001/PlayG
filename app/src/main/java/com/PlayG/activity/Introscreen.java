package com.PlayG.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.PlayG.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.PlayG.Introfragments.EigthFragment;
import com.PlayG.Introfragments.Fifthfragment;
import com.PlayG.Introfragments.FirstFragment;
import com.PlayG.Introfragments.FourthFragment;
import com.PlayG.Introfragments.SecondFragment;
import com.PlayG.Introfragments.SeventhFragment;
import com.PlayG.Introfragments.SixthFragment;
import com.PlayG.Introfragments.ThirdFragment;

public class Introscreen extends AppIntro {

    FirstFragment firstFragment=new FirstFragment();
    SecondFragment secondFragment=new SecondFragment();
    ThirdFragment thirdFragment=new ThirdFragment();
    FourthFragment fourthFragment=new FourthFragment();

    Fifthfragment fifthfragment=new Fifthfragment();
    SixthFragment sixthFragment=new SixthFragment();
    SeventhFragment seventhFragment=new SeventhFragment();
    EigthFragment eigthFragment=new EigthFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            addSlide(firstFragment);
            addSlide(secondFragment);
            addSlide(thirdFragment);
            addSlide(fourthFragment);
            addSlide(fifthfragment);
            addSlide(sixthFragment);
            addSlide(seventhFragment);
            addSlide(eigthFragment);

            setBarColor(Color.parseColor("#555346"));
            setSeparatorColor(Color.parseColor("#555346"));

            showSkipButton(true);
            setProgressButtonEnabled(true);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(Introscreen.this, PrivacyPolicy.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(Introscreen.this, PrivacyPolicy.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Introscreen.this);
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
