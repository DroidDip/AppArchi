package com.droiddip.apparchi.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.droiddip.apparchi.busevents.base.BusProvider;
import com.droiddip.apparchi.busevents.events.EventSnackBarMessage;
import com.droiddip.apparchi.busevents.events.EventToastMessage;
import com.droiddip.apparchi.mvp.presenter.MvpBasePresenter;
import com.droiddip.apparchi.utils.DSnackProvider;
import com.droiddip.apparchi.utils.DToastMessage;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity<T extends MvpBasePresenter> extends AppCompatActivity {

    private Unbinder unbinder;
    @Nullable
    protected T presenter;
    protected static long back_pressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
        unbinder = ButterKnife.bind(this);
        presenter = addPresenter();
        onPresenterCreated(presenter, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!BusProvider.getInstance().isRegistered(this)) {
            BusProvider.getInstance().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null)
            unbinder.unbind();
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finish();
            super.onBackPressed();
        } else {
            if (!isFinishing()) {
                showOnBackPressedMessage();
            }
        }

        back_pressed = System.currentTimeMillis();
    }


    protected abstract int getActivityLayout();

    protected abstract void showOnBackPressedMessage();

    @NonNull
    public abstract T addPresenter();

    public abstract void onPresenterCreated(@NonNull T presenter, Bundle savedInstanceState);

    public T getPresenter() {
        return presenter;
    }

    public void gotoNextScreen(Activity context, Class<? extends Activity> _class) {
        Intent intent = new Intent(context, _class);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        context.finish();
    }

    @Subscribe
    public void onToastMessageEvent(EventToastMessage eventToastMessage) {
        if (eventToastMessage.isLongToast())
            DToastMessage.getInstance(this).showLongCustomToast(eventToastMessage.getMessage());
        else
            DToastMessage.getInstance(this).showSmallCustomToast(eventToastMessage.getMessage());
    }

    @Subscribe
    public void onSnackBarEvent(EventSnackBarMessage eventSnackBarMessage) {
        if (eventSnackBarMessage.isError())
            DSnackProvider.showShortErrorSnack(eventSnackBarMessage.getRootView(), eventSnackBarMessage.getMessage());
        else
            DSnackProvider.showShortSnack(eventSnackBarMessage.getRootView(), eventSnackBarMessage.getMessage());
    }
}
