package nikitaverma.example.com.audioplayerwithservice.views.browse.model.browse;


import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Items;

public class Categories {
    private String next;

    private String total;

    private String offset;

    private String previous;

    private String limit;

    private String href;

    private Items[] items;

    public String getNext ()
    {
        return next;
    }

    public void setNext (String next)
    {
        this.next = next;
    }

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getOffset ()
    {
        return offset;
    }

    public void setOffset (String offset)
    {
        this.offset = offset;
    }

    public String getPrevious ()
    {
        return previous;
    }

    public void setPrevious (String previous)
    {
        this.previous = previous;
    }

    public String getLimit ()
    {
        return limit;
    }

    public void setLimit (String limit)
    {
        this.limit = limit;
    }

    public String getHref ()
    {
        return href;
    }

    public void setHref (String href)
    {
        this.href = href;
    }

    public Items[] getItems ()
    {
        return items;
    }

    public void setItems (Items[] items)
    {
        this.items = items;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [next = "+next+", total = "+total+", offset = "+offset+", previous = "+previous+", limit = "+limit+", href = "+href+", items = "+items+"]";
    }
}
