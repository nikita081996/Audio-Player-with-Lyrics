package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api;

public class Video_thumbnail {

    private String  url;

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [url = "+url+"]";
    }
}
