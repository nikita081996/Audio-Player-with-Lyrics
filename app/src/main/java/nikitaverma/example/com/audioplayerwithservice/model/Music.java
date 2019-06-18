package nikitaverma.example.com.audioplayerwithservice.model;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Music extends AppCompatActivity {
    private int musicIndex;

    private String musicName;

    private String endTime;

    private String runningTime;

    public Music(int musicIndex, String musicName, String endTime, String runningTime){
        this.musicIndex = musicIndex;
        this.musicName = musicName;
        this.endTime = endTime;
        this.runningTime = runningTime;
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
}
