package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api;

public class Added_by {

    private String href;

    private String id;

    private String type;

    private External_urls external_urls;

    private String uri;

    public String getHref ()
    {
        return href;
    }

    public void setHref (String href)
    {
        this.href = href;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public External_urls getExternal_urls ()
    {
        return external_urls;
    }

    public void setExternal_urls (External_urls external_urls)
    {
        this.external_urls = external_urls;
    }

    public String getUri ()
    {
        return uri;
    }

    public void setUri (String uri)
    {
        this.uri = uri;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [href = "+href+", id = "+id+", type = "+type+", external_urls = "+external_urls+", uri = "+uri+"]";
    }


}