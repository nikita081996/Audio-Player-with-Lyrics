package nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model;



import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import android.widget.ImageButton;

import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.PlayerState;

import java.io.Serializable;

import androidx.annotation.NonNull;

import nikitaverma.example.com.audioplayerwithservice.BR;
import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.App;
import nikitaverma.example.com.audioplayerwithservice.common.BaseActivity;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.utils.TimeFormatUtils;
import nikitaverma.example.com.audioplayerwithservice.common.utils.ToastUtils;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.ApiClient;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.ApiInterface;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.MakeCalls;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.lyrics.Lyrics;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.artists.Artists;
import nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.play.PlayActivity;
import nikitaverma.example.com.audioplayerwithservice.views.music.view_controller.MainActivity;
import retrofit2.Call;

import static nikitaverma.example.com.audioplayerwithservice.common.BaseActivity.GENIUS_TOKEN;
import static nikitaverma.example.com.audioplayerwithservice.service.MyMusicService.onClickNotificationButton;
import static nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.SearchFragment.mCustomSearchItemsList;
import static nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.SearchFragment.mListviewposition;
import static nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.play.PlayActivity.mActivityPlayBinding;

public class CustomSearchItems extends BaseObservable implements Serializable, MakeCalls.CallListener {

    private CustomAlbum customAlbum;

    private Artists[] artists;

    private String allArtistName;

    private String trackName;

    private int durationMs;

    private String trackId;

    private String trackUri;

    @Bindable
    private String lyrics = "Fetching Lyrics . . .";

    @Bindable
    private int playClicked = R.drawable.ic_pause_blue_24dp;

    @BindingAdapter({"playClicked"})
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

    public int getPlayClicked() {
        return playClicked;
    }

    public void setPlayClicked(int playAndPauseClicked) {
        this.playClicked = playAndPauseClicked;
        notifyPropertyChanged(BR.playClicked);
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
        notifyPropertyChanged(BR.lyrics);
    }

    public void playButtonClicked(CustomSearchItems customSearchItems) {
        if (BaseActivity.mSpotifyAppRemote != null && BaseActivity.mSpotifyAppRemote.getPlayerApi() != null)
            pauseOnlineMusic();

    }


    void pauseOnlineMusic() {
        BaseActivity.mSpotifyAppRemote.getPlayerApi().getPlayerState().setResultCallback(new CallResult.ResultCallback<PlayerState>() {
            @Override
            public void onResult(PlayerState playerState) {
                if (!playerState.isPaused) {
                    BaseActivity.mSpotifyAppRemote.getPlayerApi().pause();
                    setPlayClicked(R.drawable.ic_play_arrow_blue_24dp);

                } else {
                    BaseActivity.mSpotifyAppRemote.getPlayerApi().resume();
                    setPlayClicked(R.drawable.ic_pause_blue_24dp);
                    if (MainActivity.mMediaPlayer != null && MainActivity.mMediaPlayer.isPlaying())
                        onClickNotificationButton.onClickPlayPauseButton();

                }
            }
        });
    }

    public void nextButtonClicked() {
        if (mCustomSearchItemsList.size() == mListviewposition + 1) {
            mListviewposition = 0;
        } else {
            mListviewposition = mListviewposition + 1;
        }
        playSongAndFetchLyrics();
    }

    public void prevButtonClicked() {
        if (mListviewposition == 0) {
            mListviewposition = mCustomSearchItemsList.size() - 1;
        } else {
            mListviewposition = mListviewposition - 1;
        }
        playSongAndFetchLyrics();
    }

    void playSongAndFetchLyrics() {
        CustomSearchItems customSearchItems = mCustomSearchItemsList.get(mListviewposition);
        mActivityPlayBinding.setCustomSearchItems(customSearchItems);
        PlayActivity.customSearchItems = customSearchItems;
        PlayActivity.customSearchItems.setLyrics(Constants.FETCHING_LYRICS);
        if (GENIUS_TOKEN != null)
            fetchLyricsApi(Constants.GENIUS_LYRICS_API, customSearchItems);
        else {
            PlayActivity.customSearchItems.setLyrics(Constants.UNABLE_TO_FETCH_LYRICS);
            ToastUtils.showLongToast(App.getContext(), Constants.UNABLE_TO_FETCH_LYRICS);
        }

        if (BaseActivity.mSpotifyAppRemote.isConnected())
            BaseActivity.mSpotifyAppRemote.getPlayerApi().play(customSearchItems.getTrackUri());
        else
            ToastUtils.showLongToast(App.getContext(), Constants.SPOTIFY_CONNECTION_ERROR);

    }

    public void fetchLyricsApi(String apiName, CustomSearchItems customSearchItems) {
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class, apiName);
        Call call = null;
        switch (apiName) {
            case Constants.GENIUS_LYRICS_API:
                call = apiInterface.geniusLyricsApi(GENIUS_TOKEN, customSearchItems.getTrackName());
                MakeCalls.commonCall(call, (MakeCalls.CallListener) this, apiName);

                break;
        }

    }

    @Override
    public void onCallSuccess(@NonNull Object result, String apiName) {

        if (apiName.equals(Constants.GENIUS_LYRICS_API)) {
            Lyrics lyrics = (Lyrics) result;
            String lyricsUrl = null;
            if (lyrics.getResponse().getHits().length > 0)
                lyricsUrl = lyrics.getResponse().getHits()[0].getResult().getUrl();

            if (lyricsUrl != null) {
                PlayActivity playActivity = new PlayActivity();
                PlayActivity.MyTask myTask = playActivity.new MyTask();
                myTask.execute(lyricsUrl);
            } else
                PlayActivity.customSearchItems.setLyrics(Constants.UNABLE_TO_FETCH_LYRICS);


        }


    }

    @Override
    public void onCallFailure(@NonNull Object result, String apiName) {
        ToastUtils.showLongToast(App.getContext(), Constants.UNABLE_TO_FETCH_LYRICS);
        PlayActivity.customSearchItems.setLyrics(Constants.UNABLE_TO_FETCH_LYRICS);
    }


}
