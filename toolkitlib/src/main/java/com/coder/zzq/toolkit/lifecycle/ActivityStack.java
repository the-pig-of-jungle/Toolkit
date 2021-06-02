package com.coder.zzq.toolkit.lifecycle;

import android.app.Activity;

import com.coder.zzq.toolkit.Utils;
import com.coder.zzq.toolkit.log.EasyLogger;

import java.util.LinkedList;

/**
 * Created by 朱志强 on 2018/08/19.
 */
public final class ActivityStack {

    private ActivityStack() {

    }

    private static LinkedList<Activity> sActivitySet;

    public static void push(Activity activity) {
        if (activity != null) {
            EasyLogger.d("push activity : " + Utils.getActivityInfo(activity));
            getActivitySet().add(activity);
        }
    }


    public static void pop(Activity activity) {

        if (isEmpty()) {
            return;
        }

        int num = count();
        for (int index = (num - 1); index >= 0; index--) {
            if (getActivitySet().get(index) == activity) {
                getActivitySet().remove(index);
                EasyLogger.d("pop activity : " + Utils.getActivityInfo(activity));
                break;
            }
        }
    }


    public static Activity getTop() {
        return isEmpty() ? null : getActivitySet().peekLast();
    }


    public static Activity below(Activity activity) {
        if (activity == null || isEmpty()) {
            return null;
        }

        int num = count();
        for (int index = (num - 1); index >= 0; index--) {
            if (getActivitySet().get(index) == activity) {
                return index == 0 ? null : getActivitySet().get(index - 1);
            }
        }

        return null;
    }

    public static Activity above(Activity activity) {
        if (activity == null || isEmpty()) {
            return null;
        }

        int num = count();
        for (int index = (num - 1); index >= 0; index--) {
            if (getActivitySet().get(index) == activity) {
                return index == num - 1 ? null : getActivitySet().get(index + 1);
            }
        }

        return null;
    }


    private static LinkedList<Activity> getActivitySet() {
        if (sActivitySet == null) {
            sActivitySet = new LinkedList<>();
        }
        return sActivitySet;
    }

    public static boolean isEmpty() {
        return sActivitySet == null || sActivitySet.isEmpty();
    }


    public static int count() {
        return sActivitySet == null ? 0 : sActivitySet.size();
    }

    public static boolean isInStack(Activity activity) {
        return !isEmpty() && getActivitySet().contains(activity);
    }
}
