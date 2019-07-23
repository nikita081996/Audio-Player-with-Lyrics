package nikitaverma.example.com.audioplayerwithservice.views.home.view_controller;

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

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.PlayerState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MusicCardClickListener;
import nikitaverma.example.com.audioplayerwithservice.common.utils.KeyBoardUtils;
import nikitaverma.example.com.audioplayerwithservice.common.utils.LoaderUtils;
import nikitaverma.example.com.audioplayerwithservice.common.utils.NetworkStateUtils;
import nikitaverma.example.com.audioplayerwithservice.common.utils.ToastUtils;
import nikitaverma.example.com.audioplayerwithservice.databinding.FragmentBrowseAllBinding;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.ApiClient;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.ApiInterface;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.MakeCalls;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.browse.BrowseCategory;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model.CustomAlbum;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model.CustomSearchItems;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Items;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Search;
import nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.browse.BrowseActivity;
import nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.search.SearchResultActivity;
import nikitaverma.example.com.audioplayerwithservice.views.home.model_controller.BrowseAllAdapter;
import retrofit2.Call;

import static nikitaverma.example.com.audioplayerwithservice.common.BaseActivity.SPOTIFY_TOKEN;

public class BrowseAllFragment extends Fragment implements MakeCalls.CallListener, CallResult.ResultCallback, MusicCardClickListener, MaterialSearchBar.OnSearchActionListener {

    private View mView;
    private Context mContext;
    private FragmentBrowseAllBinding mFragmentBrowseAllBinding;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mFragmentBrowseAllBinding == null) {
            mFragmentBrowseAllBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_browse_all, container, false);
            mView = mFragmentBrowseAllBinding.getRoot();
            mFragmentBrowseAllBinding.executePendingBindings();
            registerListener();
        }
        return mView;
    }

    void registerListener() {
        mFragmentBrowseAllBinding.searchBar.setOnSearchActionListener((MaterialSearchBar.OnSearchActionListener) this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (SPOTIFY_TOKEN != null) {
            if (mRecyclerView == null)
                callApi(Constants.BROWSE_ALL_API, "");
        } else {
            ToastUtils.showLongToast(mContext, getString(R.string.network_connection_error));

        }
    }


    @Override
    public void onResult(Object o) {
        PlayerState playerState = (PlayerState) o;
        ToastUtils.showLongToast(mContext, playerState.playbackOptions + "");
    }

    @Override
    public void musicCardClickListener(View view, Object browsecategory) {

    }

    @Override
    public void sendMusicWithPosition(View view, Object object, int position) {
        Items items = (Items) object;
        //  ToastUtils.showLongToast(mContext, items.getId());
        Intent intent = new Intent(mView.getContext(), BrowseActivity.class);
        intent.putExtra(Constants.CATEGORY_ID, items.getId());
        mView.getContext().startActivity(intent);
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        callApi(Constants.SEARCH_API, text.toString());
        KeyBoardUtils.hideKeyboard(getActivity());
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        ToastUtils.showLongToast(mContext, "onButtonClicked");

    }

    private void callApi(String apiName, String query) {
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class, null);

        if (SPOTIFY_TOKEN != null && NetworkStateUtils.checkNetworkConnection(mContext)) {
            Call call = null;
            switch (apiName) {
                case Constants.BROWSE_ALL_API:
                    call = apiInterface.browseAllCategory(SPOTIFY_TOKEN, Constants.COUNTRY, 50);
                    MakeCalls.commonCall(call, (MakeCalls.CallListener) this, apiName);
                    LoaderUtils.showLoader(getActivity());
                    break;

                case Constants.SEARCH_API:
                    call = apiInterface.search(SPOTIFY_TOKEN, query, "track", Constants.COUNTRY);
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

        if (apiName.equals(Constants.BROWSE_ALL_API)) {
            BrowseCategory browseCategory = (BrowseCategory) result;
            if (browseCategory.getCategories().getItems().length > 0)
                mFragmentBrowseAllBinding.tvBrowseAll.setVisibility(View.VISIBLE);
            mRecyclerView = mFragmentBrowseAllBinding.rvBrowseAllList; // In xml we have given id rv_movie_list to RecyclerView
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2);
            mRecyclerView.setLayoutManager(layoutManager);

            BrowseAllAdapter browseAllAdapter = new BrowseAllAdapter(Arrays.asList(browseCategory.getCategories().getItems()), mContext, this);
            mRecyclerView.setAdapter(browseAllAdapter);
            /*if (mSpotifyAppRemote != null)
                mSpotifyAppRemote.getPlayerApi().play(response.getPlaylists().getItems()[0].getUri());*/

            //   ToastUtils.showLongToast(getApplicationContext(), Constants.SPOTIFY_CONNECTION_ERROR + " " + Constants.PLEASE_INSTALL_SPOTIFY_APP);
            //     mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:6iVecbNLLdHHmeVP1mPzVd");

        } else if (apiName.equals(Constants.SEARCH_API)) {
            Search search = (Search) result;
            List<CustomSearchItems> customSearchItemsList = new ArrayList<>();
            for (int i = 0; i < search.getTracks().getItems().length; i++) {
                CustomAlbum customAlbum = new CustomAlbum();
                customAlbum.setArtists(search.getTracks().getItems()[i].getAlbum().getArtists());
                customAlbum.setAlbumName(search.getTracks().getItems()[i].getAlbum().getName());
                customAlbum.setImages(search.getTracks().getItems()[i].getAlbum().getImages());
                customAlbum.setImageUrl(search.getTracks().getItems()[i].getAlbum().getImages()[2].getUrl());
                customAlbum.setBigImageUrl(search.getTracks().getItems()[i].getAlbum().getImages()[0].getUrl());
                customAlbum.setRelease_date(search.getTracks().getItems()[i].getAlbum().getRelease_date());
                customAlbum.setAlbumUri(search.getTracks().getItems()[i].getAlbum().getUri());

                String allAlbumArtists = "";
                for (int j = 0; j < search.getTracks().getItems()[i].getAlbum().getArtists().length; j++) {
                    if (j != 0 && j != search.getTracks().getItems()[i].getAlbum().getArtists().length - 1) {
                        allAlbumArtists = allAlbumArtists + ", ";
                    }
                    allAlbumArtists = allAlbumArtists + " " + search.getTracks().getItems()[i].getAlbum().getArtists()[j].getName();
                }
                customAlbum.setAllAlbumArtistName(allAlbumArtists);

                CustomSearchItems customSearchItems = new CustomSearchItems();
                customSearchItems.setArtists(search.getTracks().getItems()[i].getAlbum().getArtists());
                customSearchItems.setCustomAlbum(customAlbum);
                customSearchItems.setTrackName(search.getTracks().getItems()[i].getName());
                customSearchItems.setDurationMs(search.getTracks().getItems()[i].getDuration_ms());
                customSearchItems.setTrackId(search.getTracks().getItems()[i].getId());
                customSearchItems.setTrackUri(search.getTracks().getItems()[i].getUri());

                String allTractArtists = "";
                for (int j = 0; j < search.getTracks().getItems()[i].getArtists().length; j++) {
                    if (j != 0 && j != search.getTracks().getItems()[i].getAlbum().getArtists().length - 1) {
                        allTractArtists = allTractArtists + ", ";
                    }
                    allTractArtists = allTractArtists + " " + search.getTracks().getItems()[i].getArtists()[j].getName();
                }

                customSearchItems.setAllArtistName(allTractArtists);

                customSearchItemsList.add(customSearchItems);
            }

//            ToastUtils.showLongToast(mContext, customSearchItemsList.get(0).getAllArtistName());
            //          ToastUtils.showLongToast(mContext, customSearchItemsList.get(0).getCustomAlbum().getAllAlbumArtistName());
            if (customSearchItemsList.size() > 0) {
                Intent intent = new Intent(mView.getContext(), SearchResultActivity.class);
                intent.putExtra(Constants.CUSTOM_SEARCH_LIST_ITEMS, (Serializable) customSearchItemsList);
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
