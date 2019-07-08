package nikitaverma.example.com.audioplayerwithservice.common.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;


public class LoaderUtils {


    /**
     * Method to show loader on screen
     *
     * @param activity : present activity
     */
    public static void showLoader(Activity activity) {
        if (activity != null) {
            ViewGroup view = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View loader = activity.findViewById(R.id.progress_bar_layout);
            if (loader == null) {
                assert inflater != null;
                inflater.inflate(R.layout.custom_progress_loader, view, true);
                loader = activity.findViewById(R.id.progress_bar_layout);
            }
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            loader.setVisibility(View.VISIBLE);

        }
    }


    /**
     * Method to hide loader once functionality is done
     *
     * @param activity : current activity
     */
    public static void hideLoader(Activity activity) {
        try {
            if (activity != null) {
                View loader = activity.findViewById(R.id.progress_bar_layout);
                if (loader != null) {
                    loader.setVisibility(View.GONE);
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            } else
                logData(activity.getString(R.string.progress_bar_hode_log_error));
        } catch (Exception e) {
            logExceptionData(e);
        }
    }

    private static void logData(Object obj) {
        LoggerUtils.d(Constants.MESSAGE_EXCEPTION_TAG, Constants.MESSAGE_EXCEPTION_TAG + obj.toString());
    }


    /**
     * Method to log exceptions
     */
    private static void logExceptionData(Exception e) {
        LoggerUtils.d(Constants.EXCEPTION_LOG_TAG, Constants.EXCEPTION_TAG );
    }

}

