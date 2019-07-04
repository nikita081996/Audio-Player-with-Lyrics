package nikitaverma.example.com.audioplayerwithservice.views.home.model;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

/**
 * Music Model class
 */
public class Music implements Serializable {

    private String musicName;

    private String endTime;

    private String runningTime;

    private int lyricRawFile;

    private String title;

    private String album;

    private String data;

    private String artist;


    public Music(){

    }
    public Music(String musicName, String endTime, String runningTime, int lyricRawFile, String data) {
        this.musicName = musicName;
        this.endTime = endTime;
        this.runningTime = runningTime;
        this.lyricRawFile = lyricRawFile;
        this.data = data;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

}

