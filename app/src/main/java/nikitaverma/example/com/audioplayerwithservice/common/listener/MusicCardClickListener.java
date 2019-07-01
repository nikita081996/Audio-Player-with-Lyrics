package nikitaverma.example.com.audioplayerwithservice.common.listener;

import android.databinding.BindingAdapter;
import android.view.View;

import nikitaverma.example.com.audioplayerwithservice.model.Music;
import nikitaverma.example.com.audioplayerwithservice.views.home.model.HomeMusicModel;

public interface MusicCardClickListener {
    void musicCardClickListener(View view, Music homeMusicModel);

    void sendMusicWithPosition(View view, Music music, int position);
}
