package nikitaverma.example.com.audioplayerwithservice.views.music.view_controller;

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
import android.net.Uri;
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

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.BindingAdapterListener;
import nikitaverma.example.com.audioplayerwithservice.common.listener.OnClickNotificationButtonListener;
import nikitaverma.example.com.audioplayerwithservice.common.utils.TimeFormatUtils;
import nikitaverma.example.com.audioplayerwithservice.databinding.ActivityMainBinding;
import nikitaverma.example.com.audioplayerwithservice.views.home.model.Music;
import nikitaverma.example.com.audioplayerwithservice.service.MyMusicService;
import nikitaverma.example.com.audioplayerwithservice.views.home.view_controller.LocalFragment;
import nikitaverma.example.com.audioplayerwithservice.views.music.model.MainActivityViewModel;

/**
 * MainActivity class
 */
public class MainActivity extends AppCompatActivity implements OnClickNotificationButtonListener, MediaPlayer.OnCompletionListener {

    public static MediaPlayer mMediaPlayer;
    private File mFile;
    private ActivityMainBinding mActivityMainBinding;

    private Notification mStatus;
    private Intent mServiceIntent;
    private NotificationManager mManager;

    private RemoteViews mViews;
    private RemoteViews mBigViews;

    private Intent mNotificationIntent;
    private Intent mPreviousIntent;
    private Intent mPlayIntent;
    private Intent mNextIntent;
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
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.app_name);

        initilizeListener();
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.MUSIC)) {
            Music homeMusicModel = (Music) getIntent().getSerializableExtra(Constants.MUSIC);
            MainActivityViewModel.getInstance().setMusic(homeMusicModel);
            MainActivityViewModel.getInstance().updateIndex();

        }


        /*if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("Music"))
            Toast.makeText(getApplicationContext(), homeMusicModel.getArtist()+"", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "nulllll", Toast.LENGTH_LONG).show();
*/
        DataBindingUtil.setDefaultComponent(new android.databinding.DataBindingComponent() {
            @Override
            public BindingAdapterListener getBindingAdapterListener() {
                return new BindingAdapterListener() {
                    /**
                     * call when prev and next button is clicked
                     *
                     * @param view
                     * @param music
                     */
                    @Override
                    public void initlizeMediaPlayer(View view, Music music) {

                        if (mMediaPlayer != null) {
                            mMediaPlayer.stop();
                            mMediaPlayer = null;
                        }
                        if (mMediaPlayer == null) {
                            if (music != null) {
                                initilizeMediaPlayer(music.getData(), 0, music.getMusicName(), music.getLyricFile());
                                ///     Toast.makeText(getApplicationContext(), "init" + view.getId(), Toast.LENGTH_LONG).show();
                                new MyTask().execute();
                            }
                        }
                    }

                    /**
                     * call when play and pause button is clicked
                     *
                     * @param imageButton
                     * @param resId
                     */
                    @Override
                    public void playAndPauseButtonClicked(ImageButton imageButton, int resId) {

                        if (mMediaPlayer == null) {
                            if (MainActivityViewModel.getInstance().getMusic().getData() != null)
                                initilizeMediaPlayer(MainActivityViewModel.getInstance().getMusic().getData(), 0, MainActivityViewModel.getInstance().getSongName(), MainActivityViewModel.getInstance().getMusic().getLyricFile());
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
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mActivityMainBinding.setViewModel(MainActivityViewModel.getInstance());
        mActivityMainBinding.executePendingBindings();

    }

    /**
     * initilize Listener
     */
    private void initilizeListener() {
        MyMusicService.onClickNotificationButton = this;
    }

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
    private void initilizeMediaPlayer(String data, int seek, String songName, int lyricFile) {
        if (lyricFile != 0)
            setLyrics(lyricFile, songName);
        else
            mActivityMainBinding.customLyricView.reset();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer = null;
        }
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(data));
        mMediaPlayer.seekTo(seek);
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(this);

        if (mMediaPlayer != null && mMediaPlayer.isPlaying() && mServiceIntent == null) {
            startMusicService();
        } else
            showNotificationStatus();
        serviceClassMediaCompletionListener();
    }

    /**
     * register media completion listener in service class
     */
    private void serviceClassMediaCompletionListener() {
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setAction(Constants.ACTION.MEDIA_COMPLETION_LISTENER_ACTION);
        sendBroadcast(intent);
    }

    /**
     * set lyric file to lyricview
     *
     * @param lyricsFile
     * @param songName
     */
    void setLyrics(int lyricsFile, String songName) {
        mActivityMainBinding.customLyricView.setLyricFile(getFile(lyricsFile, songName));
    }

    /**
     * create Notification
     */
    private void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mStatus == null)
                mStatus = new Notification.Builder(this, Constants.ACTION.CHANNEL_ID).setAutoCancel(true).build();
            mStatus.contentView = mViews;
            mStatus.bigContentView = mBigViews;
            mStatus.flags = Notification.FLAG_ONGOING_EVENT;
            mStatus.icon = R.drawable.ic_launcher_background;
            mStatus.contentIntent = pendingIntent;
            mStatus.vibrate = new long[]{4, 4, 4, 4};


        } else {
            if (mStatus == null)
                mStatus = new Notification.Builder(this).setAutoCancel(true).build();
            mStatus.contentView = mViews;
            mStatus.bigContentView = mBigViews;
            mStatus.flags = Notification.FLAG_ONGOING_EVENT;
            mStatus.icon = R.drawable.ic_launcher_background;
            mStatus.vibrate = new long[]{4, 4, 4, 4};
            mStatus.contentIntent = pendingIntent;

        }
        getManager(getApplicationContext()).notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, mStatus);

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

        mNotificationIntent = new Intent(this, MainActivity.class);
        mNotificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        mNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        if (mMediaPlayer != null)
//            mNotificationIntent.putExtra("Resume", mMediaPlayer.getCurrentPosition());
//        else
//            mNotificationIntent.putExtra("Resume", 0);

      //  mNotificationIntent.putExtra("Resume", "Amit");
        pendingIntent = PendingIntent.getActivity(this, 0,
                mNotificationIntent, 0);

        mPreviousIntent = new Intent(this, MyMusicService.class);
        mPreviousIntent.setAction(Constants.ACTION.PREV_ACTION);
        ppreviousIntent = PendingIntent.getService(this, 0,
                mPreviousIntent, 0);

        mPlayIntent = new Intent(this, MyMusicService.class);
        mPlayIntent.setAction(Constants.ACTION.PLAY_ACTION);
        pplayIntent = PendingIntent.getService(this, 0,
                mPlayIntent, 0);

        mNextIntent = new Intent(this, MyMusicService.class);
        mNextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        pnextIntent = PendingIntent.getService(this, 0,
                mNextIntent, 0);

        closeIntent = new Intent(this, MyMusicService.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);

        if (mViews != null && mBigViews != null) {
            mViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
            mBigViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);

            mViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
            mBigViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);

            mViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);
            mBigViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);

            mViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
            mBigViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
        }

    }

    /**
     * show notification when media is playing
     */
    @Override
    public void showNotificationStatus() {
        mViews = new RemoteViews(getPackageName(),
                R.layout.status_bar);
        mBigViews = new RemoteViews(getPackageName(),
                R.layout.status_bar_expanded);

        if (mNotificationIntent == null || mPreviousIntent == null || mPlayIntent == null || mNextIntent == null || closeIntent == null)
            setIntentForNotificationButton();

        if (mMediaPlayer != null && mViews != null && mBigViews != null) {
            if (mMediaPlayer.isPlaying()) {
                mViews.setImageViewResource(R.id.status_bar_play, R.drawable.ic_pause_black_24dp);
                mBigViews.setImageViewResource(R.id.status_bar_play, R.drawable.ic_pause_black_24dp);
            } else {
                mViews.setImageViewResource(R.id.status_bar_play, R.drawable.ic_play_arrow_black_24dp);
                mBigViews.setImageViewResource(R.id.status_bar_play, R.drawable.ic_play_arrow_black_24dp);
            }

            mViews.setTextViewText(R.id.status_bar_track_name, MainActivityViewModel.getInstance().getSongName());
            mBigViews.setTextViewText(R.id.status_bar_track_name, MainActivityViewModel.getInstance().getSongName());
        }
        createNotification();

    }

    /**
     * pass notification status to service class
     *
     * @return
     */
    @Override
    public Notification getNotificationStatus() {
        return mStatus;
    }

    /**
     * check media is playing or not for service class
     *
     * @return
     */
    @Override
    public boolean checkMediaIsPlayingOrNot() {
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
        switch (btnName) {
            case "prev":
                MainActivityViewModel.getInstance().setMusic(LocalFragment.getHomeActivity().prevButtonClicked());
                break;

            case "next":
                MainActivityViewModel.getInstance().setMusic(LocalFragment.getHomeActivity().nextButtonClicked());
                break;

            default:
                MainActivityViewModel.getInstance().setMusic(LocalFragment.getHomeActivity().nextButtonClicked());

        }
        MainActivityViewModel.getInstance().updateIndex();


    }

    /**
     * initlize Media Player when prev or next button is clicked on notification
     */
    @Override
    public void initlizeMediaPlayer() {
        initilizeMediaPlayer(MainActivityViewModel.getInstance().getMusic().getData(), 0, MainActivityViewModel.getInstance().getSongName(), MainActivityViewModel.getInstance().getMusicLyricFile());
    }

    /**
     * call when user click on Play or Pause button in notification
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
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setAction(Constants.ACTION.DESTROY_ACTIVITY);
        sendBroadcast(intent);
    }

    /**
     * convert raw lyrics to file type
     *
     * @param lyricFile is raw id
     * @param name      is lyric file name
     * @return is ltyric file
     */
    private File getFile(int lyricFile, String name) {
        mFile = new File(this.getFilesDir() + File.separator + name + ".lrc");

        try {
            InputStream inputStream = getResources().openRawResource(lyricFile);
            FileOutputStream fileOutputStream = new FileOutputStream(mFile);

            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                fileOutputStream.write(buf, 0, len);
            }

            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e1) {
        }
        return mFile;
    }

    /**
     * seekbar change listner
     *
     * @param progress is seekbar progress
     */
    @Override
    public void onSeekBarChange(int progress) {
        mMediaPlayer.seekTo(progress);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        MainActivityViewModel.getInstance().setMusic(LocalFragment.getHomeActivity().nextButtonClicked());
        MainActivityViewModel.getInstance().updateIndex();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setAction(Constants.ACTION.MEDIA_COMPLETION_LISTENER_ACTION);
        sendBroadcast(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    class MyTask extends AsyncTask<Void, Integer, Void> {         //doInBackground return type input(URL), publishProgress input, doInBackground return type

        @Override
        protected void onPreExecute() {
            if (mMediaPlayer != null) {
                MainActivityViewModel.getInstance().setSeekbarMaxTime(mMediaPlayer.getDuration());
                String time = TimeFormatUtils.convertToTimeFormat(mMediaPlayer.getDuration());
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
            String time = TimeFormatUtils.convertToTimeFormat(values[0]);
            MainActivityViewModel.getInstance().setRunnningTime(time);
            MainActivityViewModel.getInstance().setSeekbarProgress(values[0]);
            mActivityMainBinding.customLyricView.setCurrentTimeMillis(values[0]);


        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
