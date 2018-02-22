package com.droiddip.apparchi.data.prefs;

import android.content.Context;

/**
 * Created by Dipanjan Chakraborty.
 */

public class AppPrefs extends DPrefs {

    private AppPrefs() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * Key
     */
    public static final String PREF_KEY_USER_ID = "KEY_USER_ID";
    private static final String PREF_KEY_DEVICE_UUID = "KEY_DEVICE_UUID";
    private static final String PREF_KEY_DEVICE_TOKEN = "KEY_DEVICE_TOKEN";
    private static final String PREF_KEY_WELCOME_SCREEN = "KEY_WELCOME_SCREEN";

    /**
     * Default Value
     */
    private static final String PREF_DEFAULT_USER_ID = "";
    private static final String PREF_DEFAULT_DEVICE_UUID = "";
    private static final String PREF_DEFAULT_DEVICE_TOKEN = "";
    private static final boolean PREF_DEFAULT_WELCOME_SCREEN = false;

    /**
     * USER ID
     */
    public static String getUserId(Context mContext) {
        return getString(mContext, PREF_KEY_USER_ID, PREF_DEFAULT_USER_ID);
    }

    public static void setUserId(Context mContext, String userId) {
        setString(mContext, PREF_KEY_USER_ID, userId);
    }

    /**
     * Device UUID
     */
    public static String getDeviceUuid(Context mContext) {
        return getString(mContext, PREF_KEY_DEVICE_UUID, PREF_DEFAULT_DEVICE_UUID);
    }

    public static void setDeviceUuid(Context mContext, String deviceUuid) {
        setString(mContext, PREF_KEY_DEVICE_UUID, deviceUuid);
    }

    /**
     * Device Token
     */
    public static void setDeviceToken(Context mContext, String devicetoken) {
        setString(mContext, PREF_KEY_DEVICE_TOKEN, devicetoken);
    }

    public static String getDeviceToken(Context mContext) {
        return getString(mContext, PREF_KEY_DEVICE_TOKEN, PREF_DEFAULT_DEVICE_TOKEN);
    }

    /**
     * Store completion of welcome screen
     */
    public static void storeWelcomeCompleted(Context mContext, boolean isCompleted) {
        setBoolean(mContext, PREF_KEY_WELCOME_SCREEN, isCompleted);
    }

    public static boolean isWelcomeCompleted(Context mContext) {
        return getBoolean(mContext, PREF_KEY_WELCOME_SCREEN, PREF_DEFAULT_WELCOME_SCREEN);
    }

}