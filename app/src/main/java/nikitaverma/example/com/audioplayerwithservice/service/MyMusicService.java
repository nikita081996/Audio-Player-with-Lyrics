package nikitaverma.example.com.audioplayerwithservice.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MediaCompletionListener;
import nikitaverma.example.com.audioplayerwithservice.common.listener.OnClickNotificationButtonListener;
import nikitaverma.example.com.audioplayerwithservice.common.receiver.MyBroadcastReceiver;
import nikitaverma.example.com.audioplayerwithservice.views.home.view_controller.LocalFragment;
import nikitaverma.example.com.audioplayerwithservice.views.music.model.MainActivityViewModel;
import nikitaverma.example.com.audioplayerwithservice.views.music.view_controller.MainActivity;

/**
 * Service class for notification
 */
public class MyMusicService extends Service implements MediaPlayer.OnCompletionListener, MediaCompletionListener {

    public static OnClickNotificationButtonListener onClickNotificationButton;
    private MyBroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        broadcastReceiver = new MyBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION.MEDIA_COMPLETION_LISTENER_ACTION);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);
        if (intent != null) {
            switch (intent.getAction()) {
                case Constants.ACTION.NOTIFY:
                    showNotification();
                    break;
                case Constants.ACTION.PREV_ACTION:
                    if (onClickNotificationButton != null) {
                        onClickNotificationButton.udpateMusicIndex(Constants.PREV);
                        onClickNotificationButton.initlizeMediaPlayer();
                        showNotification();
                    }
                    break;
                case Constants.ACTION.PLAY_ACTION:
                    if (onClickNotificationButton != null) {
                        onClickNotificationButton.onClickPlayPauseButton();
                        showNotification();
                    }
                    break;
                case Constants.ACTION.NEXT_ACTION:
                    if (onClickNotificationButton != null) {
                        onClickNotificationButton.udpateMusicIndex(Constants.NEXT);
                        onClickNotificationButton.initlizeMediaPlayer();
                        showNotification();
                    }
                    break;
                case Constants.ACTION.STOPFOREGROUND_ACTION:
                    stopForeground(true);
                    stopSelf();
                    if (onClickNotificationButton != null) {
                        onClickNotificationButton.onCloseNotificationListener();
                    }
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Comp", Toast.LENGTH_LONG).show();

                    break;
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
        if (onClickNotificationButton != null) {
            onClickNotificationButton.showNotificationStatus();
            if (onClickNotificationButton.getNotificationStatus() != null)
                startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, onClickNotificationButton.getNotificationStatus());

        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        MainActivityViewModel.getInstance().setMusic(LocalFragment.getHomeActivity().nextButtonClicked());
        MainActivityViewModel.getInstance().updateIndex();
        if (onClickNotificationButton != null)
            onClickNotificationButton.initlizeMediaPlayer();
    }

    @Override
    public void registerMediaCompletionListener() {
        MainActivity.mMediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void destroyApplication() {

    }
}
