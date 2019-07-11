package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.tracks;


import java.io.Serializable;

import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Items;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.album.Albums;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.artists.Artists;

public class Tracks implements Serializable {

    private int duration_ms;

    private String next;

    private String total;

    private String offset;

    private String previous;

    private String limit;

    private String href;

    private Items[] items;

    private Artists[] artists;

    private Albums album;

    private String name;

    private String track_number;

    private String id;

    private String is_local;

    private String track;

    private String uri;


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

    public Artists[] getArtists() {
        return artists;
    }

    public void setArtists(Artists[] artists) {
        this.artists = artists;
    }

    public Albums getAlbum() {
        return album;
    }

    public void setAlbum(Albums album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrack_number() {
        return track_number;
    }

    public void setTrack_number(String track_number) {
        this.track_number = track_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_local() {
        return is_local;
    }

    public void setIs_local(String is_local) {
        this.is_local = is_local;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [next = "+next+", total = "+total+", offset = "+offset+", previous = "+previous+", limit = "+limit+", href = "+href+", items = "+items+"]";
    }
}
