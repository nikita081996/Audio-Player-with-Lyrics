package nikitaverma.example.com.audioplayerwithservice.common.listener;

import android.view.View;

import nikitaverma.example.com.audioplayerwithservice.views.home.model.Music;

public interface MusicCardClickListener {
    void musicCardClickListener(View view, Music homeMusicModel);

    void sendMusicWithPosition(View view, Music music, int position);
}
