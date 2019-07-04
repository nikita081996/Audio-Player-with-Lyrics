package nikitaverma.example.com.audioplayerwithservice.common.listener;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageButton;

import nikitaverma.example.com.audioplayerwithservice.views.home.model.Music;

/**
 * BindingView Listener
 */
public interface BindingAdapterListener {

    @BindingAdapter("music")
    void initlizeMediaPlayer(View view, Music music);

    @BindingAdapter({"android:src"})
    void playAndPauseButtonClicked(ImageButton view, int resId);
}
