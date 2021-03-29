package com.example.aftersale.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;


import androidx.annotation.RequiresApi;

import com.example.aftersale.BuildConfig;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class Prefrancemanger {

    private static Context mContext = null;
    private static String tag = getDefaultSharedPreferencesTag();
    private static SharedPreferences sharedPreferences = null;

    public static boolean initialize(Context mContext) {
        getSharedPreferences(mContext);
        Prefrancemanger.mContext = mContext;
        return mContext == null ? false : true;
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public static boolean initialize(Context mContext, String tag) {
        getSharedPreferences(mContext);
        Prefrancemanger.tag = getTag(tag);
        Prefrancemanger.mContext = mContext;
        return mContext == null ? false : true;
    }
    public static boolean isInitialize() {
        return sharedPreferences == null ? false : true;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private static String getTag(String tag) {
        if (!tag.isEmpty()) return tag;
        return getDefaultSharedPreferencesTag();
    }
    private static String getDefaultSharedPreferencesTag() {
        return BuildConfig.APPLICATION_ID.substring(
                BuildConfig.APPLICATION_ID.lastIndexOf(".")+1);
    }
    //    private static String getDefaultSharedPreferencesTag() {
//        EncryptedSharedPreferences sharedPreferences = EncryptedSharedPreferences
//                .create(
//                        fileName,
//                        masterKeyAlias,
//                        context,
//                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//                );
//
//        SharedPreferences.Editor sharedPrefsEditor = sharedPreferences.edit();
//    }
    private static SharedPreferences getSharedPreferences(Context mContext)  throws NullPointerException {
        if (sharedPreferences == null && mContext != null)
            sharedPreferences =  mContext.getSharedPreferences(tag, Context.MODE_PRIVATE);
        return sharedPreferences;
    }
    private static SharedPreferences getSharedPreferences()  throws NullPointerException {
        if (sharedPreferences == null && mContext != null)
            sharedPreferences =  mContext.getSharedPreferences(tag, Context.MODE_PRIVATE);
        return sharedPreferences;
    }
    private static SharedPreferences.Editor getEditor() throws NullPointerException {

            return Prefrancemanger.getSharedPreferences().edit();
    }
       public static boolean put(String key, String value) { return getEditor().putString(key, value).commit(); }

       public static String getString(String key) { return getSharedPreferences().getString(key, null); }

    public static boolean remove(String key) { return getEditor().remove(key).commit(); }
    public static boolean clear() { return getEditor().clear().commit(); }
    public static void clearAppData() {
        try {
            if (mContext != null) {
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear "+ mContext.getPackageName());
            }
        } catch (Exception e) {
            Log.d("SharedPrefManager : ", "clearAppData() : "+e.getMessage());
        }
    }
    public static void clearAppCash() {
        try {
            if (mContext != null) {
                File cache = mContext.getCacheDir();
                File appDir = new File(cache.getParent());
                if (appDir.exists()) {
                    String[] children = appDir.list();
                    for (String s : children) {
                        if (!s.equals("lib")) {
                            deleteAppDir(new File(appDir, s));
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d("SharedPrefManager : ", "clearAppCash() : "+e.getMessage());
        }
    }
    private static boolean deleteAppDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                if (!deleteAppDir(new File(dir, children[i]))) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}


