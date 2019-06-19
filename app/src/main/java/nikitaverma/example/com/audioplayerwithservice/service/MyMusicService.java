package nikitaverma.example.com.audioplayerwithservice.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.OnClickNotificationButton;

public class MyMusicService extends Service {

    public static OnClickNotificationButton onClickNotificationButton;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);
        if(intent != null){
            if (intent.getAction().equals(Constants.ACTION.NOTIFY)) {
                //  onClickNotificationButton.initlizeMediaPlayer();
                showNotification();

            } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
                onClickNotificationButton.udpateMusicIndex(Constants.PREV);
                //    onClickNotificationButton.initlizeMediaPlayer();
                showNotification();

            } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
                onClickNotificationButton.onClickPlayPauseButton();
                showNotification();

            } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
                onClickNotificationButton.udpateMusicIndex(Constants.NEXT);
                // onClickNotificationButton.initlizeMediaPlayer();
                showNotification();

            } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
                stopForeground(true);
                stopSelf();
                onClickNotificationButton.onCloseNotificationListener();
            }
        }


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void showNotification() {
        onClickNotificationButton.showNotificationStatus();
        if (onClickNotificationButton.getNotificationStatus() != null)
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, onClickNotificationButton.getNotificationStatus());
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
        Toast.makeText(getApplicationContext(), "Killed", Toast.LENGTH_LONG).show();
        super.onTaskRemoved(rootIntent);
    }
}
