package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api;


import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.album.Albums;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.artists.Artists;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.playlist.Playlists;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.tracks.Tracks;

public class Search
{
    private Albums albums;

    private Artists artists;

    private Playlists playlists;

    private Tracks tracks;

    public Albums getAlbums ()
    {
        return albums;
    }

    public void setAlbums (Albums albums)
    {
        this.albums = albums;
    }

    public Artists getArtists ()
    {
        return artists;
    }

    public void setArtists (Artists artists)
    {
        this.artists = artists;
    }

    public Playlists getPlaylists ()
    {
        return playlists;
    }

    public void setPlaylists (Playlists playlists)
    {
        this.playlists = playlists;
    }

    public Tracks getTracks ()
    {
        return tracks;
    }

    public void setTracks (Tracks tracks)
    {
        this.tracks = tracks;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [albums = "+albums+", artists = "+artists+", playlists = "+playlists+", tracks = "+tracks+"]";
    }
}