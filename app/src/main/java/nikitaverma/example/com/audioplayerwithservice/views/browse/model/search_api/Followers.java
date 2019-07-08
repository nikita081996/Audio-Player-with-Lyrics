package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api;

public class Followers {

    private String total;

    private String href;

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getHref ()
    {
        return href;
    }

    public void setHref (String href)
    {
        this.href = href;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total = "+total+", href = "+href+"]";
    }
}
