package com.droiddip.apparchi.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import com.droiddip.apparchi.R;
import com.droiddip.apparchi.mvp.view.dialog.DialogMvpView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dipanjan Chakraborty on 08-02-2018.
 */

public abstract class BaseDialog extends Dialog implements DialogMvpView, View.OnClickListener {

    private Unbinder unbinder;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.BaseDialogTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.windowAnimations = R.style.Theme_Normal_Dialog;
        setContentView(getDialogLayout());

//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        unbinder = ButterKnife.bind(this);
        int width = getContext().getResources().getDisplayMetrics().widthPixels;
        getWindow().setLayout((int) (width / 1.0), ViewGroup.LayoutParams.WRAP_CONTENT);

        setCancelable(false);
        setUpDialogView();
    }

    @Override
    public void onClick(View view) {
        setUpOnClickListener(view);
    }

    @Override
    public void dismissDialog() {
        if (isShowing())
            dismiss();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showInternetError() {

    }

    protected abstract int getDialogLayout();

    protected abstract void setUpDialogView();

    protected abstract void setUpOnClickListener(View view);
}
