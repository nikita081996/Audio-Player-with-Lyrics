package nikitaverma.example.com.audioplayerwithservice.views.browse.model.lyrics;

public class Stats
{
    private String concurrents;

    private String pageviews;

    private String unreviewed_annotations;

    private String hot;

    public String getConcurrents ()
    {
        return concurrents;
    }

    public void setConcurrents (String concurrents)
    {
        this.concurrents = concurrents;
    }

    public String getPageviews ()
    {
        return pageviews;
    }

    public void setPageviews (String pageviews)
    {
        this.pageviews = pageviews;
    }

    public String getUnreviewed_annotations ()
    {
        return unreviewed_annotations;
    }

    public void setUnreviewed_annotations (String unreviewed_annotations)
    {
        this.unreviewed_annotations = unreviewed_annotations;
    }

    public String getHot ()
    {
        return hot;
    }

    public void setHot (String hot)
    {
        this.hot = hot;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [concurrents = "+concurrents+", pageviews = "+pageviews+", unreviewed_annotations = "+unreviewed_annotations+", hot = "+hot+"]";
    }
}

