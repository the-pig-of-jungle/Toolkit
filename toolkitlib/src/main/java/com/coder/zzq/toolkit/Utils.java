package com.coder.zzq.toolkit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.coder.zzq.toolkit.lifecycle.ActivityStack;

public class Utils {

    public static int dpToPx(float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Toolkit.getContext().getResources().getDisplayMetrics()));
    }

    @ColorInt
    public static int getColorFromRes(@ColorRes int colorRes) {
        return ContextCompat.getColor(Toolkit.getContext(), colorRes);
    }

    public static LayoutInflater getInflater() {
        return LayoutInflater.from(Toolkit.getContext());
    }

    public static <T> T requireNonNull(T obj, String tip) {
        if (obj == null) {
            throw new NullPointerException(tip);
        }

        return obj;
    }


    public static boolean isEmpty(CharSequence c) {
        return TextUtils.isEmpty(c) || c.toString().trim().isEmpty();
    }

    public static int screenWidth() {
        return Toolkit.getContext().getResources().getDisplayMetrics().widthPixels;
    }


    public static int getStatusBarHeight() {
        int resourceId = Toolkit.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        int height = 0;
        try {
            height = Toolkit.getContext().getResources().getDimensionPixelSize(resourceId);
        } catch (Resources.NotFoundException e) {
            height = Utils.dpToPx(24);
        }

        return height;
    }

    public static int getToolbarHeight() {
        int resourceId = Toolkit.getContext().getResources().getIdentifier("abc_action_bar_default_height_material", "dimen", "android");
        int height = 0;
        try {
            height = Toolkit.getContext().getResources().getDimensionPixelSize(resourceId);
        } catch (Resources.NotFoundException e) {
            height = Utils.dpToPx(56);
        }

        return height;
    }


    public static boolean isActivityDestroyed(Activity activity) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ?
                activity.isDestroyed() : !ActivityStack.isInStack(activity);
    }

    public static void popKeyboardWhenDialogShow(Dialog dialog) {
        if (dialog != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) Toolkit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static View inflate(@LayoutRes int layoutRes, ViewGroup viewGroup, boolean attach) {
        return LayoutInflater.from(Toolkit.getContext()).inflate(layoutRes, viewGroup, attach);
    }

    public static View inflate(@LayoutRes int layoutRes, ViewGroup viewGroup) {
        return inflate(layoutRes, viewGroup, viewGroup != null);
    }

    public static Drawable getDrawableFromRes(int drawableRes) {
        return ContextCompat.getDrawable(Toolkit.getContext(), drawableRes);
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (view == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 判断 activity的状态是可以操作UI
     *
     * @param activity
     * @return
     */
    public static boolean isUpdateActivityUIPermitted(Activity activity) {
        return activity != null && !activity.isFinishing() && !Utils.isActivityDestroyed(activity);
    }

    public static boolean isUpdateFragmentUIPermitted(Fragment fragment) {
        return fragment != null && fragment.isVisible() && !fragment.isRemoving()
                && isUpdateActivityUIPermitted(fragment.getActivity());
    }

    public static boolean isCanShowDialogInFragment(Fragment fragment) {
        return isUpdateFragmentUIPermitted(fragment) && fragment.getUserVisibleHint();
    }

    public static String getStringFromRes(@StringRes int msg) {
        return Toolkit.getContext().getString(msg);
    }

    public static float pxToDp(int px) {
        float scale = Toolkit.getContext().getResources().getDisplayMetrics().density;
        return px / scale;
    }

    public static boolean isNotificationPermitted() {
        return NotificationManagerCompat.from(Toolkit.getContext()).areNotificationsEnabled();
    }

    public static int screenHeight() {
        return Toolkit.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static String getActivityInfo(Activity activity) {
        return activity == null ? "the activity == null" : activity.getClass().getSimpleName() + "(" + activity.getClass().hashCode() + ")";
    }

    public static String getObjectDesc(Object object) {
        return object == null ? "(null)" : "(class:" + object.getClass().getSimpleName() + ";hashcode:" + object.hashCode() + ")";
    }

    public static int screenOrientation() {
        return Toolkit.getContext().getResources().getConfiguration()
                .orientation;
    }

    public static boolean isScreenLandscape() {
        return screenOrientation() == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isScreenPortrait() {
        return screenOrientation() == Configuration.ORIENTATION_PORTRAIT;
    }
}
