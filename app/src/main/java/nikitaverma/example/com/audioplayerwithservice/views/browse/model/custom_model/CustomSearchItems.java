package nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageButton;

import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.PlayerState;

import java.io.Serializable;

import nikitaverma.example.com.audioplayerwithservice.BR;
import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.BaseActivity;
import nikitaverma.example.com.audioplayerwithservice.common.utils.TimeFormatUtils;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.artists.Artists;
import nikitaverma.example.com.audioplayerwithservice.views.music.view_controller.MainActivity;

import static nikitaverma.example.com.audioplayerwithservice.service.MyMusicService.onClickNotificationButton;

public class CustomSearchItems extends BaseObservable implements Serializable {

    private CustomAlbum customAlbum;

    private Artists[] artists;

    private String allArtistName;

    private String trackName;

    private int durationMs;

    private String trackId;

    private String trackUri;

    @Bindable
    private int playAndPauseClicked = R.drawable.ic_pause_blue_24dp;

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageButton imageView, int resource) {
        imageView.setImageResource(resource);
    }

    public CustomAlbum getCustomAlbum() {
        return customAlbum;
    }

    public void setCustomAlbum(CustomAlbum customAlbum) {
        this.customAlbum = customAlbum;
    }

    public Artists[] getArtists() {
        return artists;
    }

    public void setArtists(Artists[] artists) {
        this.artists = artists;
    }

    public String getAllArtistName() {
        return allArtistName;
    }

    public void setAllArtistName(String allArtistName) {
        this.allArtistName = allArtistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getDurationMs() {
        return TimeFormatUtils.convertToTimeFormat(durationMs);
    }

    public void setDurationMs(int durationMs) {
        this.durationMs = durationMs;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackUri() {
        return trackUri;
    }

    public void setTrackUri(String trackUri) {
        this.trackUri = trackUri;
    }

    public int getPlayAndPauseClicked() {
        return playAndPauseClicked;
    }

    public void setPlayAndPauseClicked(int playAndPauseClicked) {
        this.playAndPauseClicked = playAndPauseClicked;
        notifyPropertyChanged(BR.playAndPauseClicked);
    }

    public void playButtonClicked(CustomSearchItems customSearchItems) {
        if (customSearchItems.playAndPauseClicked == R.drawable.ic_pause_blue_24dp) {
            setPlayAndPauseClicked(R.drawable.ic_play_arrow_blue_24dp);
        } else {
            setPlayAndPauseClicked(R.drawable.ic_pause_blue_24dp);
            if (MainActivity.mMediaPlayer != null && MainActivity.mMediaPlayer.isPlaying())
                onClickNotificationButton.onClickPlayPauseButton();

        }
        pauseOnlineMusic();

    }

    void pauseOnlineMusic(){
        BaseActivity.mSpotifyAppRemote.getPlayerApi().getPlayerState().setResultCallback(new CallResult.ResultCallback<PlayerState>() {
            @Override
            public void onResult(PlayerState playerState) {
                if(!playerState.isPaused){
                    BaseActivity.mSpotifyAppRemote.getPlayerApi().pause();
                } else {
                    BaseActivity.mSpotifyAppRemote.getPlayerApi().resume();
                }
            }
        });
    }
}
