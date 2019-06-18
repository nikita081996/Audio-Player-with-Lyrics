package nikitaverma.example.com.audioplayerwithservice.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import nikitaverma.example.com.audioplayerwithservice.BR;
import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.model.Music;
import nikitaverma.example.com.audioplayerwithservice.views.MainActivity;

import static nikitaverma.example.com.audioplayerwithservice.service.MyMusicService.onClickNotificationButton;

public class MainActivityViewModel extends BaseObservable {

    static MainActivityViewModel mMainActivityViewModel;

    @Bindable
    int playButtonClicked = R.drawable.ic_play_arrow_black_24dp;

    @Bindable
    private Music music;

    @Bindable
    int progress;

    @Bindable
    int maxTime;

    /*@Bindable
    File lyricFile;

    @Bindable
    int lyricTime;*/

    public MainActivityViewModel() {

    }

    public static MainActivityViewModel getInstance() {
        if (mMainActivityViewModel == null) {
            mMainActivityViewModel = new MainActivityViewModel();
        }
        return mMainActivityViewModel;
    }

    public int getPlayButtonClicked() {
        return playButtonClicked;
    }

    public void setPlayButtonClicked(int playButtonClicked) {
        this.playButtonClicked = playButtonClicked;
        notifyPropertyChanged(BR.playButtonClicked);
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
        notifyPropertyChanged(BR.music);
    }

    @Bindable
    public int getMusicIndex() {
        if (music != null)
            return music.getMusicIndex();
        return 0;
    }

    public void setMusicIndex(int index) {
        music.setMusicIndex(index);
        notifyPropertyChanged(BR.musicIndex);
    }

    @Bindable
    public String getSongName() {
        if (music != null)
            return music.getMusicName();
        return null;
    }

    public void setSongName(String songName) {
        music.setMusicName(songName);
        notifyPropertyChanged(BR.songName);
    }

    @Bindable
    public String getRunnningTime() {
        if (music != null)
            return music.getRunningTime();
        return "0";
    }

    public void setRunnningTime(String runnningTime) {
        if (music != null) {
            music.setRunningTime(runnningTime);
            notifyPropertyChanged(BR.runnningTime);
        }

    }

    @Bindable
    public String getEndTime() {
        if (music != null)
            return music.getEndTime();
        return "0";
    }

    public void setEndTime(String endTime) {
        if (music != null) {
            music.setEndTime(endTime);
            notifyPropertyChanged(BR.endTime);
        }
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        notifyPropertyChanged(BR.progress);
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
        notifyPropertyChanged(BR.maxTime);
    }


    /*public File getLyricFile() {
        return lyricFile;
    }

    public void setLyricFile(File lyricFile) {
        this.lyricFile = lyricFile;
        notifyPropertyChanged(BR.lyricFile);
    }

    public int getLyricTime() {
        return lyricTime;
    }

    public void setLyricTime(int lyricTime) {
        this.lyricTime = lyricTime;
        notifyPropertyChanged(BR.lyricTime);

    }*/

    public void onNextClicked() {
        if (music == null)
            music = new Music(R.raw.kalimba, "kalimba", "0", "0");
        updateIndex(Constants.NEXT);

    }

    public void onPrevClicked() {
        if (music == null)
            music = new Music(R.raw.kalimba, "kalimba", "0", "0");
        updateIndex(Constants.PREV);

    }

    public void onPlayClicked() {
        if (music == null)
            music = new Music(R.raw.kalimba, "kalimba", "0", "0");
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
            case R.raw.kalimba:
                if (btnName.equals(Constants.PREV)) {
                    music.setMusicIndex(R.raw.sleepaway);
                    music.setMusicName("sleepway");

                } else {
                    music.setMusicIndex(R.raw.flaxenhair);
                    music.setMusicName("flaxenhair");
                }
                break;
            case R.raw.flaxenhair:
                if (btnName.equals(Constants.PREV)) {
                    music.setMusicIndex(R.raw.kalimba);
                    music.setMusicName("kalimba");
                } else {
                    music.setMusicIndex(R.raw.sleepaway);
                    music.setMusicName("sleepway");
                }
                break;
            case R.raw.sleepaway:
                if (btnName.equals(Constants.PREV)) {
                    music.setMusicIndex(R.raw.flaxenhair);
                    music.setMusicName("flaxenhair");
                } else {
                    music.setMusicIndex(R.raw.kalimba);
                    music.setMusicName("kalimba");
                }
                break;
            default:
                music.setMusicIndex(R.raw.kalimba);
                music.setMusicName("kalimba");
                break;
        }
        music.setEndTime("0");
        music.setRunningTime("0");
        notifyPropertyChanged(BR.songName);
        setMusic(music);
        setPlayButtonClicked(R.drawable.ic_pause_black_24dp);

    }




}
