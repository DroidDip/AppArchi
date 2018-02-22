package com.droiddip.apparchi.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.droiddip.apparchi.mvp.presenter.MvpBasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment<T extends MvpBasePresenter> extends Fragment {

    private Unbinder unbinder;
    @Nullable
    protected T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) getActivity();
            getFragmentActivityReference(baseActivity);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = addPresenter();
        onPresenterCreated(presenter);
        View view = inflater.inflate(getFragmentLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @NonNull
    public abstract T addPresenter();

    public abstract void onPresenterCreated(@NonNull T presenter);

    public T getPresenter() {
        return presenter;
    }

    protected abstract int getFragmentLayout();

    protected abstract void getFragmentActivityReference(Activity activity);
}
