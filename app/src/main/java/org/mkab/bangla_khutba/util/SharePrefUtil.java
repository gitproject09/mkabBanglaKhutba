package org.mkab.bangla_khutba.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharePrefUtil {

    private static String SHARE_PREF_FILE_NAME = "MKAB_Banlga_Khutba";

    public static SharedPreferences getSharePref(Context context) {
        return context.getSharedPreferences(SHARE_PREF_FILE_NAME, 0);
    }

    public static SharedPreferences.Editor getSharePrefEditor(Context context) {
        return getSharePref(context).edit();
    }

    public static boolean setSharePrefData(Context context, String key, String value) {
        try {
            getSharePrefEditor(context).putString(key, value).commit();
        } catch (Exception e) {
            Log.d(SharePrefUtil.class.getSimpleName(), "Share_Pref_util  Set String error"+ e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean setSharePrefData(Context context, String key, int value) {
        try {
            getSharePrefEditor(context).putInt(key, value).commit();
        } catch (Exception e) {
            Log.d(SharePrefUtil.class.getSimpleName(), "Share_Pref_util  Set int error"+ e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean setSharePrefData(Context context, String key, float value) {
        try {
            getSharePrefEditor(context).putFloat(key, value).commit();
        } catch (Exception e) {
            Log.d(SharePrefUtil.class.getSimpleName(), "Share_Pref_util  Set float error"+ e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean setSharePrefData(Context context, String key, long value) {
        try {
            getSharePrefEditor(context).putLong(key, value).commit();
        } catch (Exception e) {
            Log.d(SharePrefUtil.class.getSimpleName(), "Share_Pref_util  Set float error"+ e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean setSharePrefData(Context context, String key, boolean value) {
        try {
            getSharePrefEditor(context).putBoolean(key, value).commit();
        } catch (Exception e) {
            Log.d(SharePrefUtil.class.getSimpleName(), "Share_Pref_util  Set boolean error"+ e.getMessage());
            return false;
        }
        return true;
    }

    public static String getStringValue(Context context, String key) {
        return getSharePref(context).getString(key, null);
    }

    public static boolean getBooleanValue(Context context, String key) {
        return getSharePref(context).getBoolean(key, false);
    }

    public static int getIntValue(Context context, String key){
        return getSharePref(context).getInt(key, 0);
    }

    public static long getLongValue(Context context, String key){
        return getSharePref(context).getLong(key, 0);
    }


    public static void clearSharePref(Context context) {
        getSharePrefEditor(context).clear().commit();
    }
}
