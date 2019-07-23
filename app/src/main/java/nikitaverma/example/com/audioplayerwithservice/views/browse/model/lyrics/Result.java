package nikitaverma.example.com.audioplayerwithservice.views.browse.model.lyrics;

public class Result
{
    private String lyrics_owner_id;

    private String annotation_count;

    private String song_art_image_url;

    private Primary_artist primary_artist;

    private String title;

    private String api_path;

    private String url;

    private String path;

    private String song_art_image_thumbnail_url;

    private String full_title;

    private Stats stats;

    private String title_with_featured;

    private String id;

    private String header_image_thumbnail_url;

    private String pyongs_count;

    private String header_image_url;

    private String lyrics_state;

    public String getLyrics_owner_id ()
    {
        return lyrics_owner_id;
    }

    public void setLyrics_owner_id (String lyrics_owner_id)
    {
        this.lyrics_owner_id = lyrics_owner_id;
    }

    public String getAnnotation_count ()
    {
        return annotation_count;
    }

    public void setAnnotation_count (String annotation_count)
    {
        this.annotation_count = annotation_count;
    }

    public String getSong_art_image_url ()
    {
        return song_art_image_url;
    }

    public void setSong_art_image_url (String song_art_image_url)
    {
        this.song_art_image_url = song_art_image_url;
    }

    public Primary_artist getPrimary_artist ()
    {
        return primary_artist;
    }

    public void setPrimary_artist (Primary_artist primary_artist)
    {
        this.primary_artist = primary_artist;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getApi_path ()
    {
        return api_path;
    }

    public void setApi_path (String api_path)
    {
        this.api_path = api_path;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getPath ()
    {
        return path;
    }

    public void setPath (String path)
    {
        this.path = path;
    }

    public String getSong_art_image_thumbnail_url ()
    {
        return song_art_image_thumbnail_url;
    }

    public void setSong_art_image_thumbnail_url (String song_art_image_thumbnail_url)
    {
        this.song_art_image_thumbnail_url = song_art_image_thumbnail_url;
    }

    public String getFull_title ()
    {
        return full_title;
    }

    public void setFull_title (String full_title)
    {
        this.full_title = full_title;
    }

    public Stats getStats ()
    {
        return stats;
    }

    public void setStats (Stats stats)
    {
        this.stats = stats;
    }

    public String getTitle_with_featured ()
    {
        return title_with_featured;
    }

    public void setTitle_with_featured (String title_with_featured)
    {
        this.title_with_featured = title_with_featured;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getHeader_image_thumbnail_url ()
    {
        return header_image_thumbnail_url;
    }

    public void setHeader_image_thumbnail_url (String header_image_thumbnail_url)
    {
        this.header_image_thumbnail_url = header_image_thumbnail_url;
    }

    public String getPyongs_count ()
    {
        return pyongs_count;
    }

    public void setPyongs_count (String pyongs_count)
    {
        this.pyongs_count = pyongs_count;
    }

    public String getHeader_image_url ()
    {
        return header_image_url;
    }

    public void setHeader_image_url (String header_image_url)
    {
        this.header_image_url = header_image_url;
    }

    public String getLyrics_state ()
    {
        return lyrics_state;
    }

    public void setLyrics_state (String lyrics_state)
    {
        this.lyrics_state = lyrics_state;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lyrics_owner_id = "+lyrics_owner_id+", annotation_count = "+annotation_count+", song_art_image_url = "+song_art_image_url+", primary_artist = "+primary_artist+", title = "+title+", api_path = "+api_path+", url = "+url+", path = "+path+", song_art_image_thumbnail_url = "+song_art_image_thumbnail_url+", full_title = "+full_title+", stats = "+stats+", title_with_featured = "+title_with_featured+", id = "+id+", header_image_thumbnail_url = "+header_image_thumbnail_url+", pyongs_count = "+pyongs_count+", header_image_url = "+header_image_url+", lyrics_state = "+lyrics_state+"]";
    }
}

