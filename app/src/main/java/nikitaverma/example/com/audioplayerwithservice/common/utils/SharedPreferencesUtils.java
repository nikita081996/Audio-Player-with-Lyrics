package nikitaverma.example.com.audioplayerwithservice.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import com.google.gson.Gson;

import nikitaverma.example.com.audioplayerwithservice.model.Music;

/**
 * SharedPreference Utility Class
 */
public class SharedPreferencesUtils {
    private SharedPreferences sp;
    private Context context;

    public SharedPreferencesUtils(Context context) {
        sp = context.getSharedPreferences("MUSIC_PREFERENCE", Context.MODE_PRIVATE);
        this.context = context;
    }

    public Music getMediaPlayerFromSharedPreferences() {
        String musicName = sp.getString("Music Name", null);
        int musicIndex = sp.getInt("Music Index", 0);
        String runningTime = sp.getString("Running Name", "0");
        String endTime = sp.getString("End Name", "0");
        Music music = null;
//        if(musicName != null){
//            music = new Music(musicIndex,musicName, endTime, runningTime);
//        }
        return music;
    }

    public void setMediaPlayerToSharedPreferences(Music music) {
        SharedPreferences.Editor prefsEditor = sp.edit();

        prefsEditor.putString("Music Name", music.getMusicName());
        prefsEditor.putInt("Music Index", music.getMusicIndex());
        prefsEditor.putString("Running Time", music.getRunningTime());
        prefsEditor.putString("End Time", music.getEndTime());

        prefsEditor.commit();

    }


}
