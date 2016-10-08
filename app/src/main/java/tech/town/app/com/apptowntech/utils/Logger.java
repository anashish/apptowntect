package tech.town.app.com.apptowntech.utils;

import android.util.Log;

/**
 * Created by ${="Ashish"} on 16/6/16.
 */
public class Logger {
    private static boolean DEBUG = true;

    public static void d(String tag, String arg) {
        if (isEnable()) {
            log(tag, arg);
        }
    }

    public static void d(String logMsg) {
        if (isEnable()) {
            log(getCurrentClassName(), getCurrentMethodName() + "(): " + logMsg);
        }
    }


    private static void log(String tag, String msg) {
        Log.d(tag, msg);
    }

    private static String getSplitter(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append("-");
        }
        return builder.toString();
    }

    private static void format(String tag, Object source) {
        tag = " " + tag + " ";
        log(" ", " ");
        log(" ", getSplitter(50) + tag + getSplitter(50));
        log(" ", "" + source);
        log(" ", getSplitter(100 + tag.length()));
        log(" ", " ");
    }

    private static String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }

    private static String getCurrentClassName() {
        String className = Thread.currentThread().getStackTrace()[4].getClassName();
        String[] temp = className.split("[\\.]");
        className = temp[temp.length - 1];
        return className;
    }

    public static boolean isEnable() {
        return DEBUG;
    }

    public static void setEnable(boolean flag) {
        Logger.DEBUG = flag;
    }
}
