package nikitaverma.example.com.audioplayerwithservice.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.ChangeOnlineSongListener;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MediaCompletionListener;
import nikitaverma.example.com.audioplayerwithservice.common.listener.PlayActivityListener;
import nikitaverma.example.com.audioplayerwithservice.helpers.NotificationManager;
import nikitaverma.example.com.audioplayerwithservice.views.music.view_controller.MainActivity;

import static nikitaverma.example.com.audioplayerwithservice.service.MyMusicService.onClickNotificationButton;

/**
 * BroadcastReceiver for listeners
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    private static PlayActivityListener mPlayActivityListener = null;
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

    public MyBroadcastReceiver(PlayActivityListener playActivityListener) {
        mPlayActivityListener = playActivityListener;
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
                if (mPlayActivityListener != null) {
                    mPlayActivityListener.changePlayAndPauseIcon(R.drawable.ic_pause_blue_24dp);
                }
            } else if (mPlayActivityListener != null) {
                mPlayActivityListener.changePlayAndPauseIcon(R.drawable.ic_play_arrow_blue_24dp);
            }

            int positionInMs = intent.getIntExtra("playbackPosition", 0);
        } else if (action.equals(Constants.BROADCAST_ACTION_QUEUECHANGED)) {
            //  if(trackId != null)
            //  sendNotification(trackName, artistName, albumName);
            // Sent only as a notification, your app may want to respond accordingly.
        } else if (intent.getAction().equals(Constants.ACTION.MEDIA_COMPLETION_LISTENER_ACTION)) {
            if (mMediaCompletionListener != null)
                mMediaCompletionListener.registerMediaCompletionListener();
        } else if (intent.getAction().equals(Constants.ACTION.DESTROY_ACTIVITY)) {
            if (mMediaCompletionListener != null) {
                mMediaCompletionListener.destroyApplication();
            }
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTIVITY_PLAY_PAUSE_BUTTON_CHANGE)) {
            if (mPlayActivityListener != null) {
                mPlayActivityListener.changePlayAndPauseIcon(R.drawable.ic_play_arrow_blue_24dp);
            }
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_BUTTON_CLICKED)) {
            if (mChangeOnlineSongListener != null) {
                mChangeOnlineSongListener.changeOnlineSong(Constants.NEXT);
            }
        } else if (intent.getAction().equals(Constants.ACTION.PREV_BUTTON_CLICKED)) {
            if (mChangeOnlineSongListener != null) {
                mChangeOnlineSongListener.changeOnlineSong(Constants.PREV);
            }
        }
    }

    /**
     * Send Notification
     *
     * @param title
     * @param body
     */
    private void sendNotification(String title, String body, String url) {
        mId = 101;
        if (mNotificationManagerUtils != null) {
            nb = mNotificationManagerUtils.
                    getAndroidChannelNotification(title, body, url);
            mNotificationManagerUtils.getManager().notify(mId, nb.build());


        }

    }

}
