package nikitaverma.example.com.audioplayerwithservice.views;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.BindingAdapterListener;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.OnClickNotificationButton;
import nikitaverma.example.com.audioplayerwithservice.databinding.ActivityMainBinding;
import nikitaverma.example.com.audioplayerwithservice.model.Music;
import nikitaverma.example.com.audioplayerwithservice.service.MyMusicService;
import nikitaverma.example.com.audioplayerwithservice.viewModels.MainActivityViewModel;


public class MainActivity extends AppCompatActivity implements OnClickNotificationButton {

    private static MediaPlayer mMediaPlayer;
    private File file;
    private ActivityMainBinding activityMainBinding;

    private Notification status;
    private Intent mServiceIntent;
    private NotificationManager mManager;

    private RemoteViews views;
    private RemoteViews bigViews;

    private Intent notificationIntent;
    private Intent previousIntent;
    private Intent playIntent;
    private Intent nextIntent;
    private Intent closeIntent;

    private PendingIntent pendingIntent;


    /**
     * get Notification manager
     *
     * @param context
     * @return
     */
    private NotificationManager getManager(Context context) {
        if (mManager == null) {
            mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    /**
     * create Notification Channel
     */
    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels() {
        NotificationChannel androidChannel = new NotificationChannel(Constants.ACTION.CHANNEL_ID,
                Constants.ACTION.CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
        androidChannel.enableLights(true);
        androidChannel.enableVibration(false);
        androidChannel.setLightColor(Color.GREEN);
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        androidChannel.setVibrationPattern(new long[]{2, 2, 2, 2});
        androidChannel.enableVibration(false);
        getManager(getApplicationContext()).createNotificationChannel(androidChannel);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
        initilizeListener();

        DataBindingUtil.setDefaultComponent(new android.databinding.DataBindingComponent() {
            @Override
            public BindingAdapterListener getBindingAdapterListener() {
                return new BindingAdapterListener() {
                    @Override
                    public void initlizeMediaPlayer(View view, Music music) {
                        if (mMediaPlayer != null) {
                            mMediaPlayer.stop();
                            mMediaPlayer = null;
                        }
                        if (mMediaPlayer == null) {
                            if (music != null) {
                                Toast.makeText(getApplicationContext(), "init", Toast.LENGTH_LONG).show();

                                initilizeMediaPlayer(music.getMusicIndex(), 0, music.getMusicName(), music.getLyricFile());
                                new MyTask().execute();
                            }
                        }

                    }

                    @Override
                    public void playAndPauseButtonClicked(ImageButton imageButton, int resId) {
                        if (mMediaPlayer == null) {
                            Toast.makeText(getApplicationContext(), "kj", Toast.LENGTH_LONG).show();

                            if (MainActivityViewModel.getInstance().getMusicIndex() != 0)
                                initilizeMediaPlayer(MainActivityViewModel.getInstance().getMusicIndex(), 0, MainActivityViewModel.getInstance().getSongName(), MainActivityViewModel.getInstance().getMusic().getLyricFile());
                            if (imageButton != null)
                                imageButton.setImageResource(resId);
                            new MyTask().execute();
                        } else {
                            if (mMediaPlayer.isPlaying() && resId == R.drawable.ic_play_arrow_black_24dp) {
                                mMediaPlayer.pause();
                                if (imageButton != null)
                                    imageButton.setImageResource(resId);
                            } else {
                                if (resId != R.drawable.ic_play_arrow_black_24dp)
                                    mMediaPlayer.start();
                                if (imageButton != null)
                                    imageButton.setImageResource(resId);
                            }
                            showNotificationStatus();
                            new MyTask().execute();
                        }

                    }
                };

            }
        });
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.setViewModel(MainActivityViewModel.getInstance());
        //   activityMainBinding.customLyricView.setLyricFile(getFile());
        // activityMainBinding.seekBar.setOnSeekBarChangeListener(this);
        activityMainBinding.executePendingBindings();

    }

    /**
     * initilize Listener
     */
    private void initilizeListener() {
        MyMusicService.onClickNotificationButton = this;

    }

//step 3


    /**
     * start Service for music
     */
    private void startMusicService() {
        mServiceIntent = new Intent(getApplicationContext(), MyMusicService.class);
        mServiceIntent.setAction(Constants.ACTION.NOTIFY);
        startService(mServiceIntent);
    }

    /**
     * intilize Media Player
     */
    private void initilizeMediaPlayer(int mMusicIndex, int seek, String songName, int lyricFile) {
        if (lyricFile != 0)
            setLyrics(lyricFile, songName);
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), mMusicIndex);
        mMediaPlayer.seekTo(seek);
        mMediaPlayer.start();
        if (mMediaPlayer != null && mMediaPlayer.isPlaying() && mServiceIntent == null) {
            startMusicService();
        } else
            showNotificationStatus();
    }

    void setLyrics(int lyricsFile, String songNmae) {
        activityMainBinding.customLyricView.setLyricFile(getFile(lyricsFile, songNmae));
    }

    /**
     * create Notification
     */
    private void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (status == null)
                status = new Notification.Builder(this, Constants.ACTION.CHANNEL_ID).setAutoCancel(true).build();
            status.contentView = views;
            status.bigContentView = bigViews;
            status.flags = Notification.FLAG_ONGOING_EVENT;
            status.icon = R.drawable.ic_launcher_background;
            status.contentIntent = pendingIntent;
            status.vibrate = new long[]{4, 4, 4, 4};


        } else {
            if (status == null)
                status = new Notification.Builder(this).setAutoCancel(true).build();
            status.contentView = views;
            status.bigContentView = bigViews;
            status.flags = Notification.FLAG_ONGOING_EVENT;
            status.icon = R.drawable.ic_launcher_background;
            status.vibrate = new long[]{4, 4, 4, 4};
            status.contentIntent = pendingIntent;

        }
        getManager(getApplicationContext()).notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);

    }

    /*private int getMusicIndex() {
        return mMusicIndex;
    }*/

    /**
     * set Intent for notification button
     */
    private void setIntentForNotificationButton() {

        PendingIntent ppreviousIntent;
        PendingIntent pplayIntent;
        PendingIntent pnextIntent;
        PendingIntent pcloseIntent;

        notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra("Resume", mMediaPlayer.getCurrentPosition());
        notificationIntent.putExtra("Current Song Name", MainActivityViewModel.getInstance().getSongName());
        notificationIntent.putExtra("Media Player Index", MainActivityViewModel.getInstance().getMusicIndex());
        pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        previousIntent = new Intent(this, MyMusicService.class);
        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
        ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        playIntent = new Intent(this, MyMusicService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        nextIntent = new Intent(this, MyMusicService.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        closeIntent = new Intent(this, MyMusicService.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);

        if (views != null && bigViews != null) {
            views.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
            bigViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);

            views.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
            bigViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);

            views.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);
            bigViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);

            views.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
            bigViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
        }

    }

    /**
     * show notification when media is playing
     */
    @Override
    public void showNotificationStatus() {
        views = new RemoteViews(getPackageName(),
                R.layout.status_bar);
        bigViews = new RemoteViews(getPackageName(),
                R.layout.status_bar_expanded);

        if (notificationIntent == null || previousIntent == null || playIntent == null || nextIntent == null || closeIntent == null)
            setIntentForNotificationButton();

        if (mMediaPlayer != null && views != null && bigViews != null) {
            if (mMediaPlayer.isPlaying()) {
                views.setImageViewResource(R.id.status_bar_play, R.drawable.ic_pause_black_24dp);
                bigViews.setImageViewResource(R.id.status_bar_play, R.drawable.ic_pause_black_24dp);
            } else {
                views.setImageViewResource(R.id.status_bar_play, R.drawable.ic_play_arrow_black_24dp);
                bigViews.setImageViewResource(R.id.status_bar_play, R.drawable.ic_play_arrow_black_24dp);
            }

            views.setTextViewText(R.id.status_bar_track_name, MainActivityViewModel.getInstance().getSongName());
            bigViews.setTextViewText(R.id.status_bar_track_name, MainActivityViewModel.getInstance().getSongName());
        }
        createNotification();

    }

    @Override
    public Notification getNotificationStatus() {
        return status;
    }

    @Override
    public boolean checkMediaIsPlayingOrNot() {
        //     Toast.makeText(getApplicationContext(), "hgjh", Toast.LENGTH_LONG).show();
        if (mMediaPlayer != null)
            if (mMediaPlayer.isPlaying())
                return true;
        return false;
    }

    /**
     * call when user click on next or prev button in notification
     *
     * @param btnName is next or prev button
     */
    @Override
    public void udpateMusicIndex(String btnName) {
        MainActivityViewModel.getInstance().updateIndex(btnName);
        //initilizeMediaPlayer();
    }

    /**
     * initlize Media Player
     */
    @Override
    public void initlizeMediaPlayer() {
        initilizeMediaPlayer(MainActivityViewModel.getInstance().getMusicIndex(), 0, MainActivityViewModel.getInstance().getSongName(), MainActivityViewModel.getInstance().getMusicLyricFile());
    }

    /**
     * call when user click on Play or Pause button
     */
    @Override
    public void onClickPlayPauseButton() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                MainActivityViewModel.getInstance().setPlayButtonClicked(R.drawable.ic_play_arrow_black_24dp);
            } else {
                mMediaPlayer.start();
                MainActivityViewModel.getInstance().setPlayButtonClicked(R.drawable.ic_pause_black_24dp);
            }
        }

    }

    /**
     * call when user click on close button in notification
     */
    @Override
    public void onCloseNotificationListener() {
        if (mMediaPlayer != null)
            mMediaPlayer.stop();
        mMediaPlayer = null;
        if (mManager != null)
            mManager.cancel(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE);
        mManager = null;
        MainActivityViewModel.clearMainActivityViewModelInstance();
        finishAffinity();
    }

//    @Override
//    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//        changeSeekBarProgress = i;
//    }
//
//    @Override
//    public void onStartTrackingTouch(SeekBar seekBar) {
//
//    }
//
//    @Override
//    public void onStopTrackingTouch(SeekBar seekBar) {
//        mMediaPlayer.seekTo(changeSeekBarProgress);
//    }

    private File getFile(int lyricFile, String name) {
        file = new File(this.getFilesDir() + File.separator + name + ".lrc");

        try {
            InputStream inputStream = getResources().openRawResource(lyricFile);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                fileOutputStream.write(buf, 0, len);
            }

            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e1) {
        }
        return file;
    }

    @Override
    public void onSeekBarChange(int progress) {
        mMediaPlayer.seekTo(progress);
    }

    class MyTask extends AsyncTask<Void, Integer, Void> {         //doInBackground return type input(URL), publishProgress input, doInBackground return type

        @Override
        protected void onPreExecute() {
            if (mMediaPlayer != null) {
                MainActivityViewModel.getInstance().setMaxTime(mMediaPlayer.getDuration());
                Toast.makeText(getApplicationContext(), "vvhg", Toast.LENGTH_LONG).show();
                String time = String.format("%02d : %02d ",
                        TimeUnit.MILLISECONDS.toMinutes(mMediaPlayer.getDuration()),
                        TimeUnit.MILLISECONDS.toSeconds(mMediaPlayer.getDuration()) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mMediaPlayer.getDuration())));
                MainActivityViewModel.getInstance().setEndTime(time);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (mMediaPlayer != null) {
                while (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    publishProgress(mMediaPlayer.getCurrentPosition());
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            String time = String.format("%02d : %02d ",
                    TimeUnit.MILLISECONDS.toMinutes(values[0]),
                    TimeUnit.MILLISECONDS.toSeconds(values[0]) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(values[0])));
            MainActivityViewModel.getInstance().setRunnningTime(time);
            MainActivityViewModel.getInstance().setProgress(values[0]);
            activityMainBinding.customLyricView.setCurrentTimeMillis(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }


}
