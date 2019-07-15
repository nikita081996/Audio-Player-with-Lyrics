package nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.browse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MusicCardClickListener;
import nikitaverma.example.com.audioplayerwithservice.common.utils.LoaderUtils;
import nikitaverma.example.com.audioplayerwithservice.common.utils.NetworkStateUtils;
import nikitaverma.example.com.audioplayerwithservice.common.utils.ToastUtils;
import nikitaverma.example.com.audioplayerwithservice.databinding.FragmentBrowseBinding;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.ApiClient;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.ApiInterface;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.MakeCalls;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model.CustomAlbum;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model.CustomSearchItems;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Items;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Search;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.tracks.Tracks;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model_controller.BrowseAdapter;
import nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.search.SearchResultActivity;
import retrofit2.Call;

import static nikitaverma.example.com.audioplayerwithservice.common.BaseActivity.TOKEN;

@SuppressLint("ValidFragment")
public class BrowseFragment extends Fragment implements MusicCardClickListener, MakeCalls.CallListener {

    private View mView;
    private Context mContext;
    private FragmentBrowseBinding mFragmentBrowseBinding;
    private RecyclerView mRecyclerView;
    private String mCategoryId;

    @SuppressLint("ValidFragment")
    public BrowseFragment(String categoryId) {
        if (categoryId != null) {
            this.mCategoryId = categoryId;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mFragmentBrowseBinding == null) {
            mFragmentBrowseBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_browse, container, false);
            mView = mFragmentBrowseBinding.getRoot();
            mFragmentBrowseBinding.executePendingBindings();
        }
        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mCategoryId != null) {
            callApi(Constants.BROWSE_API, mCategoryId);
        }
    }

    @Override
    public void musicCardClickListener(View view, Object object) {

    }

    @Override
    public void sendMusicWithPosition(View view, Object object, int position) {
        Items items = (Items) object;
        // ToastUtils.showLongToast(mContext, items.getId() + "");
        callApi(Constants.PLAYlISTS_TRACK_API, items.getId());
    }

    private void callApi(String apiName, String id) {
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class);

        if (TOKEN != null && NetworkStateUtils.checkNetworkConnection(mContext)) {
            Call call = null;
            switch (apiName) {
                case Constants.BROWSE_API:
                    call = apiInterface.browseCategoryPlaylists(TOKEN, id, Constants.COUNTRY);
                    MakeCalls.commonCall(call, (MakeCalls.CallListener) this, apiName);

                    LoaderUtils.showLoader(getActivity());
                    break;

                case Constants.PLAYlISTS_TRACK_API:
                    call = apiInterface.playListsTracks(TOKEN, id, Constants.COUNTRY);
                    MakeCalls.commonCall(call, (MakeCalls.CallListener) this, apiName);

                    LoaderUtils.showLoader(getActivity());
                    break;
                default:
            }
        } else {
            ToastUtils.showLongToast(mContext, getString(R.string.network_connection_error));
        }

    }

    @Override
    public void onCallSuccess(@NonNull Object result, String apiName) {
        LoaderUtils.hideLoader(getActivity());

        if (apiName.equals(Constants.BROWSE_API)) {

            Search search = (Search) result;
            mRecyclerView = mFragmentBrowseBinding.rvBrowseList; // In xml we have given id rv_movie_list to RecyclerView
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2);
            mRecyclerView.setLayoutManager(layoutManager);

            BrowseAdapter browseAllAdapter = new BrowseAdapter(Arrays.asList(search.getPlaylists().getItems()), mContext, this);
            mRecyclerView.setAdapter(browseAllAdapter);
           // ToastUtils.showLongToast(mContext, search.getPlaylists().getItems()[0].getName());
            /*if (mSpotifyAppRemote != null)
                mSpotifyAppRemote.getPlayerApi().play(response.getPlaylists().getItems()[0].getUri());*/

            //   ToastUtils.showLongToast(getApplicationContext(), Constants.SPOTIFY_CONNECTION_ERROR + " " + Constants.PLEASE_INSTALL_SPOTIFY_APP);
            //     mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:6iVecbNLLdHHmeVP1mPzVd");

        } else if (apiName.equals(Constants.PLAYlISTS_TRACK_API)) {
            Tracks tracks = (Tracks) result;

            List<CustomSearchItems> customSearchItemsList = new ArrayList<>();
            for (int i = 0; i < tracks.getItems().length; i++) {
                CustomAlbum customAlbum = new CustomAlbum();
                customAlbum.setArtists(tracks.getItems()[i].getTracks().getAlbum().getArtists());
                customAlbum.setAlbumName(tracks.getItems()[i].getTracks().getAlbum().getName());
                customAlbum.setImages(tracks.getItems()[i].getTracks().getAlbum().getImages());
                customAlbum.setImageUrl(tracks.getItems()[i].getTracks().getAlbum().getImages()[2].getUrl());
                customAlbum.setRelease_date(tracks.getItems()[i].getTracks().getAlbum().getRelease_date());
                customAlbum.setAlbumUri(tracks.getItems()[i].getTracks().getAlbum().getUri());

                String allAlbumArtists = "";
                for (int j = 0; j < tracks.getItems()[i].getTracks().getAlbum().getArtists().length; j++) {
                    if (j != 0 && j != tracks.getItems()[i].getTracks().getAlbum().getArtists().length - 1) {
                        allAlbumArtists = allAlbumArtists + ", ";
                    }
                    allAlbumArtists = allAlbumArtists + " " + tracks.getItems()[i].getTracks().getAlbum().getArtists()[j].getName();
                }
                customAlbum.setAllAlbumArtistName(allAlbumArtists);

                CustomSearchItems customSearchItems = new CustomSearchItems();
                customSearchItems.setArtists(tracks.getItems()[i].getTracks().getAlbum().getArtists());
                customSearchItems.setCustomAlbum(customAlbum);
                customSearchItems.setTrackName(tracks.getItems()[i].getTracks().getName());
                customSearchItems.setDurationMs(tracks.getItems()[i].getTracks().getDuration_ms());
                customSearchItems.setTrackId(tracks.getItems()[i].getTracks().getId());
                customSearchItems.setTrackUri(tracks.getItems()[i].getTracks().getUri());

                String allTractArtists = "";
                for (int j = 0; j < tracks.getItems()[i].getTracks().getArtists().length; j++) {
                    if (j != 0 && j != tracks.getItems()[i].getTracks().getAlbum().getArtists().length - 1) {
                        allTractArtists = allTractArtists + ", ";
                    }
                    allTractArtists = allTractArtists + " " + tracks.getItems()[i].getTracks().getArtists()[j].getName();
                }

                customSearchItems.setAllArtistName(allTractArtists);

                customSearchItemsList.add(customSearchItems);
            }

//            ToastUtils.showLongToast(mContext, customSearchItemsList.get(0).getAllArtistName());
            //          ToastUtils.showLongToast(mContext, customSearchItemsList.get(0).getCustomAlbum().getAllAlbumArtistName());
            if (customSearchItemsList.size() > 0) {
                Intent intent = new Intent(mView.getContext(), SearchResultActivity.class);
                intent.putExtra(Constants.CUSTOM_SEARCH_ITEMS, (Serializable) customSearchItemsList);
                mView.getContext().startActivity(intent);
            } else {
                ToastUtils.showLongToast(mContext, getString(R.string.no_result_found));
            }

        } else {
            ToastUtils.showLongToast(mContext, " fre");


        }


    }

    @Override
    public void onCallFailure(@NonNull Object result, String apiName) {
        ToastUtils.showLongToast(mContext, result.toString());
        LoaderUtils.hideLoader(getActivity());

    }
}