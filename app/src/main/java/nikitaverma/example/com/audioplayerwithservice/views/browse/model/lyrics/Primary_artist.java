package nikitaverma.example.com.audioplayerwithservice.views.browse.model.lyrics;

public class Primary_artist
{
    private String is_meme_verified;

    private String image_url;

    private String name;

    private String iq;

    private String id;

    private String api_path;

    private String is_verified;

    private String header_image_url;

    private String url;

    public String getIs_meme_verified ()
    {
        return is_meme_verified;
    }

    public void setIs_meme_verified (String is_meme_verified)
    {
        this.is_meme_verified = is_meme_verified;
    }

    public String getImage_url ()
    {
        return image_url;
    }

    public void setImage_url (String image_url)
    {
        this.image_url = image_url;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getIq ()
    {
        return iq;
    }

    public void setIq (String iq)
    {
        this.iq = iq;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getApi_path ()
    {
        return api_path;
    }

    public void setApi_path (String api_path)
    {
        this.api_path = api_path;
    }

    public String getIs_verified ()
    {
        return is_verified;
    }

    public void setIs_verified (String is_verified)
    {
        this.is_verified = is_verified;
    }

    public String getHeader_image_url ()
    {
        return header_image_url;
    }

    public void setHeader_image_url (String header_image_url)
    {
        this.header_image_url = header_image_url;
    }

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
        return "ClassPojo [is_meme_verified = "+is_meme_verified+", image_url = "+image_url+", name = "+name+", iq = "+iq+", id = "+id+", api_path = "+api_path+", is_verified = "+is_verified+", header_image_url = "+header_image_url+", url = "+url+"]";
    }
}