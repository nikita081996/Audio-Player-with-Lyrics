package nikitaverma.example.com.audioplayerwithservice.views.browse.model.lyrics;

public class Hits
{
    private Result result;

    private String[] highlights;

    private String index;

    private String type;

    public Result getResult ()
    {
        return result;
    }

    public void setResult (Result result)
    {
        this.result = result;
    }

    public String[] getHighlights ()
    {
        return highlights;
    }

    public void setHighlights (String[] highlights)
    {
        this.highlights = highlights;
    }

    public String getIndex ()
    {
        return index;
    }

    public void setIndex (String index)
    {
        this.index = index;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+result+", highlights = "+highlights+", index = "+index+", type = "+type+"]";
    }
}

