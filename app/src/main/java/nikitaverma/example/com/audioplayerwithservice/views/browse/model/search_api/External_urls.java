package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api;

import java.io.Serializable;

public class External_urls implements Serializable {

    private String spotify;

    public String getSpotify ()
    {
        return spotify;
    }

    public void setSpotify (String spotify)
    {
        this.spotify = spotify;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [spotify = "+spotify+"]";
    }
}
