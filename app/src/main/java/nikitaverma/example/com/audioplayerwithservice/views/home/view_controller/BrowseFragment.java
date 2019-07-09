package nikitaverma.example.com.audioplayerwithservice.views.home.view_controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.PlayerState;

import java.util.Arrays;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.CallBrowseApiListener;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MusicCardClickListener;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MyBroadcastReceiver;
import nikitaverma.example.com.audioplayerwithservice.common.utils.LoaderUtils;
import nikitaverma.example.com.audioplayerwithservice.common.utils.ToastUtils;
import nikitaverma.example.com.audioplayerwithservice.databinding.FragmentBrowseBinding;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.ApiClient;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.ApiInterface;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.MakeCalls;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.browse.BrowseCategory;
import nikitaverma.example.com.audioplayerwithservice.views.home.model_controller.BrowseAdapter;
import retrofit2.Call;

import static nikitaverma.example.com.audioplayerwithservice.common.BaseActivity.TOKEN;

public class BrowseFragment extends Fragment implements MakeCalls.CallListener, CallResult.ResultCallback, CallBrowseApiListener, MusicCardClickListener {

    private View view;
    private Context mContext;
    private FragmentBrowseBinding mFragmentBrowseBinding;
    private MyBroadcastReceiver mBroadcastReceiver;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mFragmentBrowseBinding == null) {
            mFragmentBrowseBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_browse, container, false);
            view = mFragmentBrowseBinding.getRoot();
            // view = inflater.inflate(R.layout.activity_home, container, false);

            // binding.setViewModel(MainActivityViewModel.getInstance());
            mFragmentBrowseBinding.executePendingBindings();
        }


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        registerBroadcastListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        while (TOKEN == null) {
            LoaderUtils.showLoader(getActivity());
        }
        if (TOKEN != null) {
            if(mRecyclerView  == null)
            callApi(Constants.BROWSE_API);
        }
    }

    void registerBroadcastListener() {
        mBroadcastReceiver = new MyBroadcastReceiver((CallBrowseApiListener) this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_ACTION_BROWSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        mContext.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    public void callBrowseApi(String browseApi) {
        //  callApi(browseApi);
        LoaderUtils.hideLoader(getActivity());
        callApi(browseApi);
    }

    public void callApi(String apiName) {
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class);
        ToastUtils.showLongToast(mContext, "calling");

        if (TOKEN != null) {
            switch (apiName) {
                case Constants.BROWSE_API:
                    Call<BrowseCategory> call = apiInterface.browseCategory(TOKEN, Constants.COUNTRY, 50);
                    MakeCalls.commonCall(call, (MakeCalls.CallListener) this, apiName);
                    LoaderUtils.showLoader(getActivity());
                    ToastUtils.showLongToast(mContext, "calling");
                    break;

                default:
            }
        }

    }


    @Override
    public void onResult(Object o) {
        PlayerState playerState = (PlayerState) o;
        ToastUtils.showLongToast(mContext, playerState.playbackOptions + "");
    }

    @Override
    public void onCallSuccess(@NonNull Object result, String apiName) {
        LoaderUtils.hideLoader(getActivity());

        if (apiName.equals(Constants.BROWSE_API)) {
            BrowseCategory browseCategory = (BrowseCategory) result;
            mRecyclerView = mFragmentBrowseBinding.rvBrowseList; // In xml we have given id rv_movie_list to RecyclerView
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2);
            mRecyclerView.setLayoutManager(layoutManager);

            BrowseAdapter browseAdapter = new BrowseAdapter(Arrays.asList(browseCategory.getCategories().getItems()), mContext, this);
            mRecyclerView.setAdapter(browseAdapter);
            ToastUtils.showLongToast(mContext, browseCategory.getCategories().getItems()[0].getName());
            /*if (mSpotifyAppRemote != null)
                mSpotifyAppRemote.getPlayerApi().play(response.getPlaylists().getItems()[0].getUri());*/

            //   ToastUtils.showLongToast(getApplicationContext(), Constants.SPOTIFY_CONNECTION_ERROR + " " + Constants.PLEASE_INSTALL_SPOTIFY_APP);
            //     mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:6iVecbNLLdHHmeVP1mPzVd");

        } else {
            ToastUtils.showLongToast(mContext, " fre");


        }


    }

    @Override
    public void onCallFailure(@NonNull Object result, String apiName) {
        ToastUtils.showLongToast(mContext, result.toString() + " failure");
        LoaderUtils.hideLoader(getActivity());

    }

    void fetchMusic() {


    }


    @Override
    public void musicCardClickListener(View view, Object browsecategory) {

    }

    @Override
    public void sendMusicWithPosition(View view, Object browsecategory, int position) {
   //     BrowseCategory browseCategory = (BrowseCategory) browsecategory;
     //   ToastUtils.showLongToast(mContext, ((BrowseCategory) browsecategory).getCategories().getItems()[0].getName());
    }
}
