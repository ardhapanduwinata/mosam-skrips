package com.arwinata.am.skripsi.Retrofit.service;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String sp_app = "spApplication";

    public static final String sp_iduser = "spIDuser";
    public static final String sp_level = "spLevel";

    public static final String sp_sudahLogin = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(sp_app, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keysp, String value){
        spEditor.putString(keysp, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSP_iduser(){
        return sp.getString(sp_iduser, "");
    }

    public int getSP_level(){
        return sp.getInt(sp_level, 0);
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(sp_sudahLogin, false);
    }
}
