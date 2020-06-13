package nikitaverma.example.com.audioplayerwithservice.helpers;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.utils.NotificationUtils;

public class NotificationManager extends ContextWrapper {

    private android.app.NotificationManager mManager;

    public NotificationManager(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannels() {

        NotificationChannel androidChannel = new NotificationChannel(Constants.ANDROID_CHANNEL_ID,
                Constants.ANDROID_CHANNEL_NAME, android.app.NotificationManager.IMPORTANCE_DEFAULT);
        androidChannel.enableLights(true);
        androidChannel.enableVibration(true);
        androidChannel.setLightColor(Color.GREEN);
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        androidChannel.setShowBadge(true);
        getManager().createNotificationChannel(androidChannel);


    }

    public android.app.NotificationManager getManager() {
        if (mManager == null) {
            mManager = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return mManager;
    }

    public NotificationCompat.Builder getAndroidChannelNotification(String title, String body, String url) {
        return NotificationUtils.getChannelNotification(title, body, url, Constants.ANDROID_CHANNEL_ID);
    }


}
