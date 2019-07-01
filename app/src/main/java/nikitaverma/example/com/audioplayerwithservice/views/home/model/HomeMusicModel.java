package nikitaverma.example.com.audioplayerwithservice.views.home.model;

import java.io.Serializable;

public class HomeMusicModel implements Serializable {
    private String title;

    private String album;

    private String data;

    private String artist;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
