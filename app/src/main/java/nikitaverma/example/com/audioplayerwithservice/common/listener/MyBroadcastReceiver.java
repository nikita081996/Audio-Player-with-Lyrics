package nikitaverma.example.com.audioplayerwithservice.common.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import nikitaverma.example.com.audioplayerwithservice.common.Constants;

public class MyBroadcastReceiver extends BroadcastReceiver {

    public MediaCompletionListener mMediaCompletionListener;

    public MyBroadcastReceiver() {
    }

    public MyBroadcastReceiver(MediaCompletionListener listener) {
        this.mMediaCompletionListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Constants.ACTION.MEDIA_COMPLETION_LISTENER_ACTION))
            mMediaCompletionListener.registerMediaCompletionListener();
    }
}
