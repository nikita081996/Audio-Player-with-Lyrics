package nikitaverma.example.com.audioplayerwithservice.common.utils;

import android.util.Log;

import nikitaverma.example.com.audioplayerwithservice.BuildConfig;


public class LoggerUtils {
    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG)
            Log.d(tag, message);
    }

    public static void e(String tag, String errorMsg, Throwable throwable) {
        if (throwable == null)
            Log.e(tag, errorMsg);
        else
            Log.e(tag, errorMsg, throwable);
    }
}
