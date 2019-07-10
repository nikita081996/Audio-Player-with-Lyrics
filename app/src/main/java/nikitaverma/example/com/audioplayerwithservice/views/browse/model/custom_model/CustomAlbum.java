package nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model;

import java.io.Serializable;

import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Images;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.artists.Artists;

public class CustomAlbum implements Serializable {

    private Artists[] artists;

    private String allAlbumArtistName;

    private String albumName;

    private Images[] images;

    private String release_date;

    private String imageUrl;

    private String albumUri;

    public Artists[] getArtists() {
        return artists;
    }

    public void setArtists(Artists[] artists) {
        this.artists = artists;
    }

    public String getAllAlbumArtistName() {
        return allAlbumArtistName;
    }

    public void setAllAlbumArtistName(String allAlbumArtistName) {
        this.allAlbumArtistName = allAlbumArtistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Images[] getImages() {
        return images;
    }

    public void setImages(Images[] images) {
        this.images = images;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAlbumUri() {
        return albumUri;
    }

    public void setAlbumUri(String albumUri) {
        this.albumUri = albumUri;
    }
}
