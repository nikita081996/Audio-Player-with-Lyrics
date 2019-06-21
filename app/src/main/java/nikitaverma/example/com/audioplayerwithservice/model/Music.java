package nikitaverma.example.com.audioplayerwithservice.model;

import android.support.v7.app.AppCompatActivity;

/**
 * Music Model
 */
public class Music extends AppCompatActivity {

    private int musicIndex;

    private String musicName;

    private String endTime;

    private String runningTime;

    private int lyricRawFile;

    public Music(int musicIndex, String musicName, String endTime, String runningTime, int lyricRawFile) {
        this.musicIndex = musicIndex;
        this.musicName = musicName;
        this.endTime = endTime;
        this.runningTime = runningTime;
        this.lyricRawFile = lyricRawFile;
    }

    public int getMusicIndex() {
        return musicIndex;
    }

    public void setMusicIndex(int musicIndex) {
        this.musicIndex = musicIndex;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {

        this.endTime = endTime;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
//        Toast.makeText(getApplicationContext(), runningTime, Toast.LENGTH_LONG).show();
        this.runningTime = runningTime;
    }

    public int getLyricFile() {
        return lyricRawFile;
    }

    public void setLyricFile(int lyricRawFile) {
        this.lyricRawFile = lyricRawFile;
    }


}

