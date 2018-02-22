package com.droiddip.apparchi.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.momenta.R;

/**
 * Created by Dipanjan Chakraborty on 21-02-2018.
 */

public class DToastMessage {

    private Context context;
    private static DToastMessage instance;

    /**
     * @para m context
     */
    private DToastMessage(Context context) {
        this.context = context;
    }

    /**
     * @param context
     * @return
     */
    public synchronized static DToastMessage getInstance(Context context) {
        if (instance == null) {
            instance = new DToastMessage(context);
        }
        return instance;
    }

    /**
     * @param message
     */
    public void showLongToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param message
     */
    public void showSmallToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * The Toast displayed via this method will display it for short period of time
     *
     * @param message
     */
    public void showLongCustomToast(String message) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_custom_toast, (ViewGroup) ((Activity) context).findViewById(R.id.ll_toast));
        TextView msgTv = (TextView) layout.findViewById(R.id.tv_toast_msg);
        msgTv.setText(message);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    /**
     * The toast displayed by this class will display it for long period of time
     *
     * @param message
     */
    public void showSmallCustomToast(String message) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_custom_toast, (ViewGroup) ((Activity) context).findViewById(R.id.ll_toast));
        TextView msgTv = (TextView) layout.findViewById(R.id.tv_toast_msg);
        msgTv.setText(message);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
