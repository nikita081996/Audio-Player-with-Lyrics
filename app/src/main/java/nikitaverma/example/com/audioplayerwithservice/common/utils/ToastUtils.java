package nikitaverma.example.com.audioplayerwithservice.common.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    /**
     * Method shows long toast on an activity with proper message
     *
     * @param text : text to be displayed
     */
    public static void showLongToast(Context con, String text) {
        if (text != null && text.trim().length() > 0)
            Toast.makeText(con, text, Toast.LENGTH_LONG).show();
    }


    /**
     * Method shows short toast on an activity with message
     *
     * @param con  : Context of activity
     * @param text :  Text to be displayed
     */
    public static void showShortToast(Context con, String text) {
        if (text != null && text.trim().length() > 0)
            Toast.makeText(con, text, Toast.LENGTH_SHORT).show();
    }

}
