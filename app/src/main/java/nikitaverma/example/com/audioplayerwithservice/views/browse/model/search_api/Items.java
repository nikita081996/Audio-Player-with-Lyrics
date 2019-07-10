package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api;


import java.io.Serializable;

import nikitaverma.example.com.audioplayerwithservice.views.browse.model.browse.Icons;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.album.Albums;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.artists.Artists;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.tracks.Tracks;


public class Items implements Serializable {

    private Icons[] icons;

    private String disc_number;

    private Albums album;

    private String type;

    private External_ids external_ids;

    private Owner owner;

    private int duration_ms;

    private String explicit;

    private String is_playable;

    private Images[] images;

    private String snapshot_id;

    private String collaborative;

    private String primary_color;

    private Followers followers;

    private String[] genres;

    private String popularity;

    private String name;

    private Tracks tracks;

    private String href;

    private String id;

    private Artists[] artists;

    private String preview_url;

    private String track_number;

    private String is_local;

    private External_urls external_urls;

    private String uri;

    public Images[] getImages ()
    {
        return images;
    }

    public void setImages (Images[] images)
    {
        this.images = images;
    }

    public Followers getFollowers ()
    {
        return followers;
    }

    public void setFollowers (Followers followers)
    {
        this.followers = followers;
    }

    public String[] getGenres ()
    {
        return genres;
    }

    public void setGenres (String[] genres)
    {
        this.genres = genres;
    }

    public String getPopularity ()
    {
        return popularity;
    }

    public void setPopularity (String popularity)
    {
        this.popularity = popularity;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
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

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public External_urls getExternal_urls ()
    {
        return external_urls;
    }

    public void setExternal_urls (External_urls external_urls)
    {
        this.external_urls = external_urls;
    }

    public String getUri ()
    {
        return uri;
    }

    public void setUri (String uri)
    {
        this.uri = uri;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getSnapshot_id() {
        return snapshot_id;
    }

    public void setSnapshot_id(String snapshot_id) {
        this.snapshot_id = snapshot_id;
    }

    public String getCollaborative() {
        return collaborative;
    }

    public void setCollaborative(String collaborative) {
        this.collaborative = collaborative;
    }

    public String getPrimary_color() {
        return primary_color;
    }

    public void setPrimary_color(String primary_color) {
        this.primary_color = primary_color;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    public String getDisc_number() {
        return disc_number;
    }

    public void setDisc_number(String disc_number) {
        this.disc_number = disc_number;
    }

    public Albums getAlbum() {
        return album;
    }

    public void setAlbum(Albums album) {
        this.album = album;
    }

    public External_ids getExternal_ids() {
        return external_ids;
    }

    public void setExternal_ids(External_ids external_ids) {
        this.external_ids = external_ids;
    }

    public int getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }

    public String getExplicit() {
        return explicit;
    }

    public void setExplicit(String explicit) {
        this.explicit = explicit;
    }

    public String getIs_playable() {
        return is_playable;
    }

    public void setIs_playable(String is_playable) {
        this.is_playable = is_playable;
    }

    public Artists[] getArtists() {
        return artists;
    }

    public void setArtists(Artists[] artists) {
        this.artists = artists;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public String getTrack_number() {
        return track_number;
    }

    public void setTrack_number(String track_number) {
        this.track_number = track_number;
    }

    public String getIs_local() {
        return is_local;
    }

    public void setIs_local(String is_local) {
        this.is_local = is_local;
    }

    public Icons[] getIcons() {
        return icons;
    }

    public void setIcons(Icons[] icons) {
        this.icons = icons;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [images = "+images+", followers = "+followers+", genres = "+genres+", popularity = "+popularity+", name = "+name+", href = "+href+", id = "+id+", type = "+type+", external_urls = "+external_urls+", uri = "+uri+"]";
    }
}
