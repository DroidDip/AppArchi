package com.music.arts.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Dipanjan Chakraborty on 07-02-2017.
 */

public class ActivityAnim {

    public static final long PERFECT_MILLS = 600;
    public static final int MINI_RADIUS = 0;

    private static Long mMills;
    private static Long mActivityMills;
    private static Integer mColorOrImageRes;

    public static void init(long perfectMills, long fullActivityPerfectMills, int colorOrImageRes) {
        mMills = perfectMills;
        mActivityMills = fullActivityPerfectMills;
        mColorOrImageRes = colorOrImageRes;
    }

    public static class ActivityBuilder {
        private Activity mActivity;
        private View mTriggerView;
        private float mStartRadius = MINI_RADIUS;
        private int mColorOrImageRes = getColorOrImageRes();
        private Long mDurationMills;
        private OnAnimationEndListener mOnAnimationEndListener;
        private int mEnterAnim = android.R.anim.fade_in, mExitAnim = android.R.anim.fade_out;

        public ActivityBuilder(Activity activity, View triggerView) {
            mActivity = activity;
            mTriggerView = triggerView;
        }

        public ActivityBuilder startRadius(float startRadius) {
            mStartRadius = startRadius;
            return this;
        }

        public ActivityBuilder colorOrImageRes(int colorOrImageRes) {
            mColorOrImageRes = colorOrImageRes;
            return this;
        }

        public ActivityBuilder duration(long durationMills) {
            mDurationMills = durationMills;
            return this;
        }

        public ActivityBuilder overridePendingTransition(int enterAnim, int exitAnim) {
            mEnterAnim = enterAnim;
            mExitAnim = exitAnim;
            return this;
        }

        public void go(OnAnimationEndListener onAnimationEndListener) {
            mOnAnimationEndListener = onAnimationEndListener;

            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                doOnEnd();
                return;
            }

            int[] location = new int[2];
            mTriggerView.getLocationInWindow(location);
            final int cx = location[0] + mTriggerView.getWidth() / 2;
            final int cy = location[1] + mTriggerView.getHeight() / 2;
            final ImageView view = new ImageView(mActivity);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setImageResource(mColorOrImageRes);
            final ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
            int w = decorView.getWidth();
            int h = decorView.getHeight();
            decorView.addView(view, w, h);

            int maxW = Math.max(cx, w - cx);
            int maxH = Math.max(cy, h - cy);
            final int finalRadius = (int) Math.sqrt(maxW * maxW + maxH * maxH) + 1;

            try {
                Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, mStartRadius, finalRadius);

                int maxRadius = (int) Math.sqrt(w * w + h * h) + 1;
                if (mDurationMills == null) {
                    double rate = 1d * finalRadius / maxRadius;
                    mDurationMills = (long) (getActivityMills() * Math.sqrt(rate));
                }
                final long finalDuration = mDurationMills;
                anim.setDuration((long) (finalDuration * 0.9));
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        doOnEnd();

                        mActivity.overridePendingTransition(mEnterAnim, mExitAnim);

                        mTriggerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mActivity.isFinishing()) return;
                                try {
                                    Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                                            finalRadius, mStartRadius);
                                    anim.setDuration(finalDuration);
                                    anim.addListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            try {
                                                decorView.removeView(view);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    anim.start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    try {
                                        decorView.removeView(view);
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }, 1000);

                    }
                });
                anim.start();
            } catch (Exception e) {
                e.printStackTrace();
                doOnEnd();
            }
        }

        private void doOnEnd() {
            mOnAnimationEndListener.onAnimationEnd();
        }
    }

    public static ActivityBuilder fullActivity(Activity activity, View triggerView) {
        return new ActivityBuilder(activity, triggerView);
    }

    private static long getActivityMills() {
        if (mActivityMills != null)
            return mActivityMills;
        else
            return PERFECT_MILLS;
    }

    private static int getColorOrImageRes() {
        if (mColorOrImageRes != null)
            return mColorOrImageRes;
        else
            return android.R.color.white;
    }

    public interface OnAnimationEndListener {
        void onAnimationEnd();
    }
}
