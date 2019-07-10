package nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model;

import java.io.Serializable;

import nikitaverma.example.com.audioplayerwithservice.common.utils.TimeFormatUtils;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.artists.Artists;

public class CustomSearchItems implements Serializable {

    private CustomAlbum customAlbum;

    private Artists[] artists;

    private String allArtistName;

    private String trackName;

    private int durationMs;

    private String trackId;

    private String trackUri;

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


}
