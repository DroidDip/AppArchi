/**
 * Copyright 2017 Dipanjan Chakraborty (chakraborty.dipanjan07@gmail.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.droiddip.apparchi.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * DPrefs
 *
 * @author Dipanjan Chakraborty
 */

public class DPrefs {

    private static final String FILE_NAME = "com.droiddip.apparchi.appdata";


    /**
     * Store some string in SharedPreferences using a key value and the data
     *
     * @param key
     * @param value
     */

    protected static void setString(Context mContext, String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    /**
     * Get string value from SharedPreferences using key value
     *
     * @param key
     * @param def
     * @return a string
     */

    protected static String getString(Context mContext, String key, String def) {
        SharedPreferences prefs = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key, def);
    }

    /**
     * Store some boolean value in SharedPreferences using a key value and the
     * data
     *
     * @param key
     * @param value
     */

    protected static void setBoolean(Context mContext, String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    /**
     * Get boolean value from SharedPreferences using key value
     *
     * @param key
     * @param def
     * @return a boolean value
     */

    protected static boolean getBoolean(Context mContext, String key, boolean def) {
        SharedPreferences prefs = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, def);
    }

    /**
     * Store some integer value in SharedPreferences using a key value and the
     * data
     *
     * @param key
     * @param value
     */

    protected static void setInt(Context mContext, String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }

    /**
     * Get integer value from SharedPreferences using key value
     *
     * @param key
     * @param def
     * @return a integer value
     */

    protected static int getInt(Context mContext, String key, int def) {
        SharedPreferences prefs = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(key, def);
    }

    /**
     * Store some Long value in SharedPreferences using a key value and the data
     *
     * @param key
     * @param value
     */

    protected static void setLong(Context mContext, String key, long value) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).apply();
    }

    /**
     * Get Long value from SharedPreferences using key value
     *
     * @param key
     * @param def
     * @return a Long value
     */

    protected static long getLong(Context mContext, String key, long def) {
        SharedPreferences prefs = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return prefs.getLong(key, def);
    }

    /**
     * Store some Double value in SharedPreferences using a key value and the
     * data
     *
     * @param key
     * @param value
     */

    protected static void setDouble(Context mContext, String key, double value) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, Double.toString(value)).apply();
    }

    /**
     * Get Double value from SharedPreferences using key value
     *
     * @param key
     * @param def
     * @return a Double value
     */

    protected static double getDouble(Context mContext, String key, double def) {
        SharedPreferences prefs = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return Double.parseDouble(prefs.getString(key, Double.toString(def)));
    }


    /**
     * Store some Float value in SharedPreferences using a key value and the
     * data
     *
     * @param key
     * @param value
     */

    protected static void setFloat(Context mContext, String key, float value) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putFloat(key, value).apply();
    }

    /**
     * Get Float value from SharedPreferences using key value
     *
     * @param key
     * @param def
     * @return a Float value
     */

    protected static float getFloat(Context mContext, String key, float def) {
        SharedPreferences prefs = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return prefs.getFloat(key, def);
    }

    public static void clearPreferences(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public static void removeKeyFromPrefs(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (sp.contains(key)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.apply();
        }
    }
}
