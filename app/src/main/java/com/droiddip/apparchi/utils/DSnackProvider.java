package com.droiddip.apparchi.utils;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.momenta.R;
import com.momenta.application.MomentaApplication;

/**
 * Created by Dipanjan Chakraborty on 21-02-2018.
 */

public class DSnackProvider {

    private DSnackProvider() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    public static void showLongSnack(View rootView, @StringRes int message) {
        new DSnackBar.Builder(rootView)
                .setBackgroundColor(MomentaApplication.getApplicationInstance().getResources().getColor(R.color.colorSnackBarNormal))
                .setDuration(Snackbar.LENGTH_LONG)
                .setText(MomentaApplication.getApplicationInstance().getResources().getString(message))
                .build()
                .show();
    }

    public static void showShortSnack(View rootView, @StringRes int message) {
        new DSnackBar.Builder(rootView)
                .setBackgroundColor(MomentaApplication.getApplicationInstance().getResources().getColor(R.color.colorSnackBarNormal))
                .setDuration(Snackbar.LENGTH_SHORT)
                .setText(MomentaApplication.getApplicationInstance().getResources().getString(message))
                .build()
                .show();
    }

    public static void showLongErrorSnack(View rootView, @StringRes int message) {
        new DSnackBar.Builder(rootView)
                .setBackgroundColor(MomentaApplication.getApplicationInstance().getResources().getColor(R.color.colorSnackBarError))
                .setDuration(Snackbar.LENGTH_LONG)
                .setText(MomentaApplication.getApplicationInstance().getResources().getString(message))
                .build()
                .show();
    }

    public static void showShortErrorSnack(View rootView, @StringRes int message) {
        new DSnackBar.Builder(rootView)
                .setBackgroundColor(MomentaApplication.getApplicationInstance().getResources().getColor(R.color.colorSnackBarError))
                .setDuration(Snackbar.LENGTH_SHORT)
                .setText(MomentaApplication.getApplicationInstance().getResources().getString(message))
                .build()
                .show();
    }
}
