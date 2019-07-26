package nikitaverma.example.com.audioplayerwithservice.views.music.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.SeekBar;

import nikitaverma.example.com.audioplayerwithservice.BR;
import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.utils.TimeFormatUtils;
import nikitaverma.example.com.audioplayerwithservice.views.home.model.Music;
import nikitaverma.example.com.audioplayerwithservice.views.home.view_controller.LocalFragment;

import static nikitaverma.example.com.audioplayerwithservice.service.MyMusicService.onClickNotificationButton;

/**
 * ViewModel class for MainActivity
 */
public class MainActivityViewModel extends BaseObservable {

    static MainActivityViewModel mMainActivityViewModel;

    @Bindable
    int playButtonClicked = R.drawable.ic_play_arrow_white_24dp;

    @Bindable
    int seekbarProgress;

    @Bindable
    int seekbarMaxTime;
    int myProgress = 0;
    @Bindable
    private Music music = null;

    public MainActivityViewModel() {
    }

    public static MainActivityViewModel getInstance() {
        if (mMainActivityViewModel == null) {
            mMainActivityViewModel = new MainActivityViewModel();
        }
        return mMainActivityViewModel;
    }

    /**
     * listener
     * call when Notification is cancelled
     */
    public static void clearMainActivityViewModelInstance() {
        mMainActivityViewModel = null;
    }

    /**
     * get current song name
     *
     * @return
     */
    @Bindable
    public String getSongName() {
        if (music != null)
            return music.getMusicName();
        return null;
    }

    /**
     * set current song name
     *
     * @param songName
     */
    public void setSongName(String songName) {
        music.setMusicName(songName);
        notifyPropertyChanged(BR.songName);
    }

    /**
     * get current song running time
     *
     * @return
     */
    @Bindable
    public String getRunnningTime() {
        if (music != null)
            return music.getRunningTime();
        return Constants.RESET_TIME;
    }

    /**
     * set current song running time
     *
     * @param runnningTime
     */
    public void setRunnningTime(String runnningTime) {
        if (music != null) {
            music.setRunningTime(runnningTime);
            notifyPropertyChanged(BR.runnningTime);
        }

    }

    /**
     * get current song end time
     *
     * @return
     */
    @Bindable
    public String getEndTime() {
        if (music != null)
            return music.getEndTime();
        return Constants.RESET_TIME;
    }

    /**
     * set current song end time
     *
     * @param endTime
     */
    public void setEndTime(String endTime) {
        if (music != null) {
            music.setEndTime(endTime);
            notifyPropertyChanged(BR.endTime);
        }
    }

    /**
     * get current song seekbar progress
     *
     * @return
     */
    public int getSeekbarProgress() {
        return seekbarProgress;
    }

    /**
     * set current song seekbar progress
     *
     * @param progress
     */
    public void setSeekbarProgress(int progress) {
        this.seekbarProgress = progress;
        notifyPropertyChanged(BR.seekbarProgress);
    }

    /**
     * get play button icon
     *
     * @return
     */
    public int getPlayButtonClicked() {
        return playButtonClicked;
    }

    /**
     * set play button icon
     *
     * @param playButtonClicked
     */
    public void setPlayButtonClicked(int playButtonClicked) {
        this.playButtonClicked = playButtonClicked;
        notifyPropertyChanged(BR.playButtonClicked);
    }

    /**
     * get current song music model
     *
     * @return
     */
    public Music getMusic() {
        return music;
    }

    /**
     * set current song music model
     *
     * @param music
     */
    public void setMusic(Music music) {
        this.music = music;
        notifyPropertyChanged(BR.music);
    }

    /**
     * get current song max time
     *
     * @return
     */
    public int getSeekbarMaxTime() {
        return seekbarMaxTime;
    }

    /**
     * set current song  max time
     *
     * @param maxTime
     */
    public void setSeekbarMaxTime(int maxTime) {
        this.seekbarMaxTime = maxTime;
        notifyPropertyChanged(BR.seekbarMaxTime);
    }

    /**
     * get current song lyrics id
     *
     * @return
     */
    public int getMusicLyricFile() {
        if (music != null)
            return music.getLyricFile();
        return 0;
    }

    /**
     * listener
     * call when next button clicked
     */
    public void onNextClicked() {
        if (music == null)
            music = new Music(Constants.CLOSER, Constants.RESET_TIME, Constants.RESET_TIME, R.raw.closer_lyrics,"");
        setMusic(LocalFragment.getHomeActivity().nextButtonClicked());
        updateIndex();

    }

    /**
     * listener
     * call when prev button clicked
     */
    public void onPrevClicked() {
        if (music == null)
            music = new Music(Constants.CLOSER, Constants.RESET_TIME, Constants.RESET_TIME, R.raw.closer_lyrics,"");
        setMusic(LocalFragment.getHomeActivity().prevButtonClicked());
        updateIndex();
    }

    /**
     * listener
     * call when play button clicked
     */
    public void onPlayClicked() {
        if (music == null)
            music = new Music(Constants.CLOSER, Constants.RESET_TIME, Constants.RESET_TIME, R.raw.closer_lyrics,"");
        notifyPropertyChanged(BR.songName);
        if (onClickNotificationButton != null && onClickNotificationButton.checkMediaIsPlayingOrNot()) {
            setPlayButtonClicked(R.drawable.ic_play_arrow_white_24dp);
        } else {
            setPlayButtonClicked(R.drawable.ic_pause_white_24dp);
        }
    }

    /**
     * update song name
     *
     */
    public void updateIndex() {
        music.setMusicName(getMusic().getTitle());
      //  music.setLyricFile(R.raw.who_says_lyrics);
        music.setEndTime(Constants.RESET_TIME);
        music.setRunningTime(Constants.RESET_TIME);
        notifyPropertyChanged(BR.songName);
        setMusic(music);
        setPlayButtonClicked(R.drawable.ic_pause_white_24dp);

    }

    /**
     * listener
     * call when seekbar changes
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.myProgress = 0;
        String time = TimeFormatUtils.convertToTimeFormat(progress);
        setRunnningTime(time);
        this.myProgress = progress;
    }

    /**
     * listener
     * call when seebar is releases
     *
     * @param seekBar
     */
    public void onStopTrackingTouch(SeekBar seekBar) {
        onClickNotificationButton.onSeekBarChange(myProgress);
    }

}
