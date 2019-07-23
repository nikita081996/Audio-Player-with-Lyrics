package nikitaverma.example.com.audioplayerwithservice.views.browse.model.lyrics;

public class Response
{
    private Hits[] hits;

    public Hits[] getHits ()
    {
        return hits;
    }

    public void setHits (Hits[] hits)
    {
        this.hits = hits;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [hits = "+hits+"]";
    }
}

