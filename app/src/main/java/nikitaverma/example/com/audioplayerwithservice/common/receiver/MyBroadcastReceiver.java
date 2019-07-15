package nikitaverma.example.com.audioplayerwithservice.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.ChangeOnlineSongListener;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MediaCompletionListener;
import nikitaverma.example.com.audioplayerwithservice.helpers.NotificationManager;
import nikitaverma.example.com.audioplayerwithservice.views.music.view_controller.MainActivity;

import static nikitaverma.example.com.audioplayerwithservice.service.MyMusicService.onClickNotificationButton;

/**
 * BroadcastReceiver for listeners
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    public MediaCompletionListener mMediaCompletionListener;
    private NotificationManager mNotificationManagerUtils;
    private NotificationCompat.Builder nb;
    private int mId = 101;
    private ChangeOnlineSongListener mChangeOnlineSongListener;

    public MyBroadcastReceiver(ChangeOnlineSongListener changeOnlineSongListener) {
        mChangeOnlineSongListener = changeOnlineSongListener;
    }

    public MyBroadcastReceiver() {
    }

    public MyBroadcastReceiver(MediaCompletionListener listener) {
        this.mMediaCompletionListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        long timeSentInMs = intent.getLongExtra("timeSent", 0L);
        if (mNotificationManagerUtils == null)
            mNotificationManagerUtils = new NotificationManager(context);

        String action = intent.getAction();
        String trackId = "";
        String artistName = "";
        String albumName = "";
        String trackName = "";
        if (action.equals(Constants.BROADCAST_ACTION_METADATACHANGED)) {
            trackId = intent.getStringExtra("id");
            artistName = intent.getStringExtra("artist");
            albumName = intent.getStringExtra("album");
            trackName = intent.getStringExtra("track");
            int trackLengthInSec = intent.getIntExtra("length", 0);

        } else if (action.equals(Constants.BROADCAST_ACTION_PLAYBACKSTATECHANGED)) {

            boolean playing = intent.getBooleanExtra("playing", false);
            if (playing) {
                if (MainActivity.mMediaPlayer != null && MainActivity.mMediaPlayer.isPlaying())
                    onClickNotificationButton.onClickPlayPauseButton();
            }
            int positionInMs = intent.getIntExtra("playbackPosition", 0);
        } else if (action.equals(Constants.BROADCAST_ACTION_QUEUECHANGED)) {

            // Sent only as a notification, your app may want to respond accordingly.
        } else if (intent.getAction().equals(Constants.ACTION.MEDIA_COMPLETION_LISTENER_ACTION))
            if (mMediaCompletionListener != null)
                mMediaCompletionListener.registerMediaCompletionListener();
        if (intent.getAction().equals(Constants.ACTION.DESTROY_ACTIVITY))
            if (mMediaCompletionListener != null) {
                mMediaCompletionListener.destroyApplication();
            }
    }

}
