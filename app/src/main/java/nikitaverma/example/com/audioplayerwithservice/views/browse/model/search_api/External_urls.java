package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api;

public class External_urls {

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
