package com.PlayG.pref;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "pubG-welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_FIRST_TIME_LAUNCH_NOTE = "IsFirstTimeLaunchNote";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunchNote(boolean isFirstTimeNote) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH_NOTE, isFirstTimeNote);
        editor.commit();
    }

    public boolean isFirstTimeLaunchNote() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH_NOTE, true);
    }
}