package nikitaverma.example.com.audioplayerwithservice.common;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageButton;

import nikitaverma.example.com.audioplayerwithservice.model.Music;

public interface BindingAdapterListener {

    @BindingAdapter("music")
    void initlizeMediaPlayer(View view, Music music);

    @BindingAdapter({"android:src"})
    void playAndPauseButtonClicked(ImageButton view, int resId);
}
