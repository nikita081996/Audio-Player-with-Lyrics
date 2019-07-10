package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.album;


import java.io.Serializable;

import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.External_urls;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Images;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Items;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.artists.Artists;

public class Albums implements Serializable {
    private Images[] images;

    private String[] available_markets;

    private String release_date_precision;

    private String type;

    private String uri;

    private String total_tracks;

    private Artists[] artists;

    private String release_date;

    private String name;

    private String album_type;

    private String href;

    private String id;

    private External_urls external_urls;

    private Items[] items;

    public Images[] getImages ()
    {
        return images;
    }

    public void setImages (Images[] images)
    {
        this.images = images;
    }

    public String[] getAvailable_markets ()
    {
        return available_markets;
    }

    public void setAvailable_markets (String[] available_markets)
    {
        this.available_markets = available_markets;
    }

    public String getRelease_date_precision ()
    {
        return release_date_precision;
    }

    public void setRelease_date_precision (String release_date_precision)
    {
        this.release_date_precision = release_date_precision;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getUri ()
    {
        return uri;
    }

    public void setUri (String uri)
    {
        this.uri = uri;
    }

    public String getTotal_tracks ()
    {
        return total_tracks;
    }

    public void setTotal_tracks (String total_tracks)
    {
        this.total_tracks = total_tracks;
    }

    public Artists[] getArtists ()
    {
        return artists;
    }

    public void setArtists (Artists[] artists)
    {
        this.artists = artists;
    }

    public String getRelease_date ()
    {
        return release_date;
    }

    public void setRelease_date (String release_date)
    {
        this.release_date = release_date;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getAlbum_type ()
    {
        return album_type;
    }

    public void setAlbum_type (String album_type)
    {
        this.album_type = album_type;
    }

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

    public External_urls getExternal_urls ()
    {
        return external_urls;
    }

    public void setExternal_urls (External_urls external_urls)
    {
        this.external_urls = external_urls;
    }

    public Items[] getItems() {
        return items;
    }

    public void setItems(Items[] items) {
        this.items = items;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [images = "+images+", available_markets = "+available_markets+", release_date_precision = "+release_date_precision+", type = "+type+", uri = "+uri+", total_tracks = "+total_tracks+", artists = "+artists+", release_date = "+release_date+", name = "+name+", album_type = "+album_type+", href = "+href+", id = "+id+", external_urls = "+external_urls+"]";
    }
}
