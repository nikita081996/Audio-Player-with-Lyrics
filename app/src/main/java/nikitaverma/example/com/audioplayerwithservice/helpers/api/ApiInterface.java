package nikitaverma.example.com.audioplayerwithservice.helpers.api;


import nikitaverma.example.com.audioplayerwithservice.views.browse.model.browse.BrowseCategory;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Search;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("search")
    Call<Search> accessToken(@Header("Authorization") String token, @Query("q") String q, @Query("type") String type);

    @GET("browse/categories")
    Call<BrowseCategory> browseCategory(@Header("Authorization") String token, @Query("country") String country, @Query("limit") int limit);

}
