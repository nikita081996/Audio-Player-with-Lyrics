package nikitaverma.example.com.audioplayerwithservice.helpers.api;


import nikitaverma.example.com.audioplayerwithservice.views.browse.model.browse.BrowseCategory;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Search;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.tracks.Tracks;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search")
    Call<Search> search(@Header("Authorization") String token, @Query("q") String q, @Query("type") String type, @Query("market") String market);

    @GET("browse/categories")
    Call<BrowseCategory> browseAllCategory(@Header("Authorization") String token, @Query("country") String country, @Query("limit") int limit);

    @GET("browse/categories/{category_id}/playlists")
    Call<Search> browseCategoryPlaylists(@Header("Authorization") String token, @Path("category_id") String category_id, @Query("country") String country);

    @GET("playlists/{playlist_id}/tracks")
    Call<Tracks> playListsTracks(@Header("Authorization") String token, @Path("playlist_id") String playlist_id, @Query("market") String market);

}
