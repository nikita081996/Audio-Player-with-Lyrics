package nikitaverma.example.com.audioplayerwithservice.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.SeekBar;

import nikitaverma.example.com.audioplayerwithservice.BR;
import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.utils.TimeFormatUtils;
import nikitaverma.example.com.audioplayerwithservice.model.Music;

import static nikitaverma.example.com.audioplayerwithservice.service.MyMusicService.onClickNotificationButton;

/**
 * ViewModel class for MainActivity
 */
public class MainActivityViewModel extends BaseObservable {

    static MainActivityViewModel mMainActivityViewModel;

    @Bindable
    int playButtonClicked = R.drawable.ic_play_arrow_black_24dp;

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
     * get current song id
     *
     * @return
     */
    public int getMusicIndex() {
        if (music != null)
            return music.getMusicIndex();
        return 0;
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
            music = new Music(R.raw.closer, Constants.CLOSER, Constants.RESET_TIME, Constants.RESET_TIME, R.raw.closer_lyrics);
        updateIndex(Constants.NEXT);

    }

    /**
     * listener
     * call when prev button clicked
     */
    public void onPrevClicked() {
        if (music == null)
            music = new Music(R.raw.closer, Constants.CLOSER, Constants.RESET_TIME, Constants.RESET_TIME, R.raw.closer_lyrics);
        updateIndex(Constants.PREV);
    }

    /**
     * listener
     * call when play button clicked
     */
    public void onPlayClicked() {
        if (music == null)
            music = new Music(R.raw.closer, Constants.CLOSER, Constants.RESET_TIME, Constants.RESET_TIME, R.raw.closer_lyrics);
        notifyPropertyChanged(BR.songName);
        if (onClickNotificationButton.checkMediaIsPlayingOrNot()) {
            setPlayButtonClicked(R.drawable.ic_play_arrow_black_24dp);
        } else {
            setPlayButtonClicked(R.drawable.ic_pause_black_24dp);
        }
    }

    /**
     * update song name
     *
     * @param btnName is which button is clicked
     */
    public void updateIndex(String btnName) {
        switch (music.getMusicIndex()) {
            case R.raw.closer:
                if (btnName.equals(Constants.PREV)) {
                    music.setMusicIndex(R.raw.sober);
                    music.setMusicName(Constants.SOBER);
                    music.setLyricFile(R.raw.sober_lyrics);

                } else {
                    music.setMusicIndex(R.raw.who_says);
                    music.setMusicName(Constants.WHO_SAYS);
                    music.setLyricFile(R.raw.who_says_lyrics);

                }
                break;
            case R.raw.who_says:
                if (btnName.equals(Constants.PREV)) {
                    music.setMusicIndex(R.raw.closer);
                    music.setMusicName(Constants.CLOSER);
                    music.setLyricFile(R.raw.closer_lyrics);
                } else {
                    music.setMusicIndex(R.raw.sober);
                    music.setMusicName(Constants.SOBER);
                    music.setLyricFile(R.raw.sober_lyrics);
                }
                break;
            case R.raw.sober:
                if (btnName.equals(Constants.PREV)) {
                    music.setMusicIndex(R.raw.who_says);
                    music.setMusicName(Constants.WHO_SAYS);
                    music.setLyricFile(R.raw.who_says_lyrics);
                } else {
                    music.setMusicIndex(R.raw.closer);
                    music.setMusicName(Constants.CLOSER);
                    music.setLyricFile(R.raw.closer_lyrics);
                }
                break;
            default:
                music.setMusicIndex(R.raw.closer);
                music.setMusicName(Constants.CLOSER);
                music.setLyricFile(R.raw.closer_lyrics);
                break;
        }
        music.setEndTime(Constants.RESET_TIME);
        music.setRunningTime(Constants.RESET_TIME);
        notifyPropertyChanged(BR.songName);
        setMusic(music);
        setPlayButtonClicked(R.drawable.ic_pause_black_24dp);

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
