package com.droiddip.apparchi.mvp.presenter;

import android.support.annotation.NonNull;

import com.droiddip.apparchi.mvp.view.MvpBaseView;


public abstract class MvpBasePresenter<T extends MvpBaseView> {

    private T baseView;

    protected boolean isViewAttached() {
        return this.baseView != null;
    }

    public void attachView(@NonNull T view) {
        this.baseView = view;
    }

    public void detachView() {
        this.baseView = null;
    }

    public T getView() {
        return baseView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
