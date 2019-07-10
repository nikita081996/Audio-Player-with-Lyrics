package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.artists;


import java.io.Serializable;

import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.External_urls;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Items;

public class Artists implements Serializable {
    private String name;

    private String type;

    private String uri;

    private String id;

    private External_urls external_urls;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public External_urls getExternal_urls() {
        return external_urls;
    }

    public void setExternal_urls(External_urls external_urls) {
        this.external_urls = external_urls;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [next = "+next+", total = "+total+", offset = "+offset+", previous = "+previous+", limit = "+limit+", href = "+href+", items = "+items+"]";
    }

}
