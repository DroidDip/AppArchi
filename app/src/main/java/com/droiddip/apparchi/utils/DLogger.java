package com.droiddip.apparchi.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * DLogger
 */
public class DLogger {

    private static final String DEFAULT_LOG_TAG = "AppArchi";
    private static boolean SHOULD_PRINT_LOG = true;
    private static boolean SHOULD_PRINT_LOG_FILE = false;
    private static final String LOG_PREFIX = DEFAULT_LOG_TAG;
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    private static final String LOG_EXCEPTION_MSG = "Log message should not be empty or null";

    private DLogger() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    public static String makeLogTag(String str) {
        if (TextUtils.isEmpty(str)) {
            return DEFAULT_LOG_TAG;
        }
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX
                    + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH
                    - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    @SuppressWarnings("rawtypes")
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    /**
     * Used for enable or disable Log to be print. Default is True.
     *
     * @param isEnabled
     */
    public static void enableLogging(boolean isEnabled) {
        SHOULD_PRINT_LOG = isEnabled;
    }

    public static void d(String message) {
        if (SHOULD_PRINT_LOG) {
            if (!TextUtils.isEmpty(message))
                Log.i(DEFAULT_LOG_TAG, message);
            else
                throw new Error(LOG_EXCEPTION_MSG);
        }
    }

    public static void d(Exception exception) {
        if (SHOULD_PRINT_LOG) {
            if (!TextUtils.isEmpty(exception.toString()))
                Log.d(DEFAULT_LOG_TAG, exception.getLocalizedMessage());
            else
                throw new Error(LOG_EXCEPTION_MSG);
        }
    }

    public static void d(String tag, String message) {
        if (SHOULD_PRINT_LOG) {
            if (!TextUtils.isEmpty(message))
                Log.d(tag, message);
            else
                throw new Error(LOG_EXCEPTION_MSG);
        }
    }

    public static void d(String tag, Exception exception) {
        if (SHOULD_PRINT_LOG) {
            if (!TextUtils.isEmpty(exception.toString()))
                Log.d(tag, exception.getLocalizedMessage());
            else
                throw new Error(LOG_EXCEPTION_MSG);
        }
    }

    public static void i(String message) {
        if (SHOULD_PRINT_LOG) {
            if (!TextUtils.isEmpty(message))
                Log.i(DEFAULT_LOG_TAG, message);
            else
                throw new Error(LOG_EXCEPTION_MSG);
        }
    }

    public static void i(Exception exception) {
        if (SHOULD_PRINT_LOG) {
            if (!TextUtils.isEmpty(exception.toString()))
                Log.i(DEFAULT_LOG_TAG, exception.getLocalizedMessage());
            else
                throw new Error(LOG_EXCEPTION_MSG);
        }
    }

    public static void i(String tag, String message) {
        if (SHOULD_PRINT_LOG) {
            if (!TextUtils.isEmpty(message))
                Log.i(tag, message);
            else
                throw new Error(LOG_EXCEPTION_MSG);
        }
    }

    public static void i(String tag, Exception exception) {
        if (SHOULD_PRINT_LOG) {
            if (!TextUtils.isEmpty(exception.toString()))
                Log.i(tag, exception.getLocalizedMessage());
            else
                throw new Error(LOG_EXCEPTION_MSG);
        }
    }

    public static void e(String message) {
        if (SHOULD_PRINT_LOG) {
            if (!TextUtils.isEmpty(message))
                Log.e(DEFAULT_LOG_TAG, message);
            else
                throw new Error(LOG_EXCEPTION_MSG);
        }
    }

    public static void e(Exception exception) {
        if (SHOULD_PRINT_LOG) {
            if (!TextUtils.isEmpty(exception.toString()))
                Log.e(DEFAULT_LOG_TAG, exception.getLocalizedMessage());
            else
                throw new Error(LOG_EXCEPTION_MSG);
        }
    }

    public static void e(String tag, String message) {
        if (SHOULD_PRINT_LOG) {
            if (!TextUtils.isEmpty(message))
                Log.e(tag, message);
            else
                throw new Error(LOG_EXCEPTION_MSG);
        }
    }

    public static void e(String tag, Exception exception) {
        if (SHOULD_PRINT_LOG) {
            if (!TextUtils.isEmpty(exception.toString()))
                Log.e(tag, exception.toString());
            else
                throw new Error(LOG_EXCEPTION_MSG);
        }
    }

}
