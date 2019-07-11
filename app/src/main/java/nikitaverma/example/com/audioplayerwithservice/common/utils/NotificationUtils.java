package nikitaverma.example.com.audioplayerwithservice.common.utils;

import android.os.Build;
import android.support.v4.app.NotificationCompat;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.App;


public class NotificationUtils {

    /**
     * generate notification bulider
     *
     * @param title
     * @param body
     * @param CHANNEL_ID
     * @return
     */
    public static NotificationCompat.Builder getChannelNotification(String title, String body, String url, String CHANNEL_ID) {
       /* Intent intent = new Intent(App.getContext(), LoginActivity.class);
        intent.putExtra(Constants.ACTION_URL, url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(App.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new NotificationCompat.Builder(App.getContext(), CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setLights(0xff00ff00, 300, 100)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    //  .setContentIntent(pendingIntent)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

        } else {
            return new NotificationCompat.Builder(App.getContext())
                    .setContentTitle(title)
                    .setContentText(body)
                    .setLights(0xff00ff00, 300, 100)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    //  .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        }

    }


}
