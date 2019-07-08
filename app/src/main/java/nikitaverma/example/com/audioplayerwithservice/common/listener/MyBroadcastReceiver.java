package nikitaverma.example.com.audioplayerwithservice.common.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.helpers.NotificationManager;

/**
 * BroadcastReceiver for listeners
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    private NotificationManager mNotificationManagerUtils;
    private NotificationCompat.Builder nb;
    private int mId = 101;
    private CallBrowseApiListener mCallBrowseApiListener;
    public MediaCompletionListener mMediaCompletionListener;

    public MyBroadcastReceiver(CallBrowseApiListener callBrowseApiListener){
        mCallBrowseApiListener = callBrowseApiListener;
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
            //  Toast.makeText(context, "Broadcast", Toast.LENGTH_LONG).show();
            //  sendNotification(trackName, artistName, albumName);
            int trackLengthInSec = intent.getIntExtra("length", 0);
            // Do something with extracted information...
        }
        else if (action.equals(Constants.BROADCAST_ACTION_PLAYBACKSTATECHANGED)) {
            boolean playing = intent.getBooleanExtra("playing", false);
            int positionInMs = intent.getIntExtra("playbackPosition", 0);
            // Do something with extracted information
        }
        else if (action.equals(Constants.BROADCAST_ACTION_QUEUECHANGED)) {
            // Sent only as a notification, your app may want to respond accordingly.
        }
        else if(action.equals(Constants.BROADCAST_ACTION_BROWSE)){
            mCallBrowseApiListener.callBrowseApi(intent.getStringExtra(Constants.API_NAME));
        } else if (intent.getAction().equals(Constants.ACTION.MEDIA_COMPLETION_LISTENER_ACTION))
            if (mMediaCompletionListener != null)
                mMediaCompletionListener.registerMediaCompletionListener();
        if (intent.getAction().equals(Constants.ACTION.DESTROY_ACTIVITY))
            if (mMediaCompletionListener != null)
                mMediaCompletionListener.destroyApplication();
    }
}
