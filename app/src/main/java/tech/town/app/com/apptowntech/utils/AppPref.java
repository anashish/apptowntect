package tech.town.app.com.apptowntech.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ${="Ashish"} on 13/9/16.
 */
public class AppPref {

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREF_NAME = "liveTv";

    public AppPref(Context context) {
        this.mContext = context;
        mPref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mPref.edit();
    }
    public void saveHomeCategoryJson(String homeJosn){

        mEditor.putString("homeJson", homeJosn);
        mEditor.commit();
    }
    public String getHomeCategoryJson(Context context){

        return mPref.getString("homeJson","");
    }

    public void saveAccessToke(String accessToken){

        mEditor.putString("accessToke", accessToken);
        mEditor.commit();
    }
    public String getAccessToken(Context context){

        return mPref.getString("accessToke","");
    }
    public void saveUserIcon(String url){

        mEditor.putString("imageUrl", url);
        mEditor.commit();
    }
    public void saveUserName(String name){

        mEditor.putString("name", name);
        mEditor.commit();
    }
    public String getImageIcon(Context context){

        return mPref.getString("imageUrl","");
    }
    public String getUserName(Context context){

        return mPref.getString("name","");
    }

    public void saveDataSavingMode(Boolean b){
        mEditor.putBoolean("data", b);
        mEditor.commit();
    }
    public boolean getDataSaveMode(Context context){
        return mPref.getBoolean("data",false);
    }
    public void saveVersionHomeAPi(String number){
        mEditor.putString("version", number);
        mEditor.commit();
    }
    public String getVersionNumber(Context context){
        return mPref.getString("version","");
    }

    public void saveOriention(boolean b){
        mEditor.putBoolean("o", b);
        mEditor.commit();
    }
    public boolean getBooleanOri(Context context){
        return mPref.getBoolean("o",false);
    }

}
