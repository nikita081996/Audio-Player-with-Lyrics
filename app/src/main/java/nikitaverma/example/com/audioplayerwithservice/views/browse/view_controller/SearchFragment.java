package nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.BaseActivity;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.ChangeOnlineSongListener;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MusicCardClickListener;
import nikitaverma.example.com.audioplayerwithservice.common.receiver.MyBroadcastReceiver;
import nikitaverma.example.com.audioplayerwithservice.common.utils.ToastUtils;
import nikitaverma.example.com.audioplayerwithservice.databinding.FragmentSearchResultBinding;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model.CustomSearchItems;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model_controller.SearchAdapter;
import nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.play.PlayActivity;
import nikitaverma.example.com.audioplayerwithservice.views.music.view_controller.MainActivity;

import static nikitaverma.example.com.audioplayerwithservice.service.MyMusicService.onClickNotificationButton;

@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment implements MusicCardClickListener{

    public static List<CustomSearchItems> mCustomSearchItemsList;
    public static int mListviewposition;
    private View mView;
    private Context mContext;
    private FragmentSearchResultBinding mFragmentSearchResultBinding;
    private RecyclerView mRecyclerView;
    private MyBroadcastReceiver mBroadcastReceiver;

    @SuppressLint("ValidFragment")
    public SearchFragment(List<CustomSearchItems> customSearchItems) {
        if (customSearchItems != null) {
            this.mCustomSearchItemsList = customSearchItems;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mFragmentSearchResultBinding == null) {
            mFragmentSearchResultBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_search_result, container, false);
            mView = mFragmentSearchResultBinding.getRoot();
            mFragmentSearchResultBinding.executePendingBindings();
        }
        return mView;
    }

    void addRecycleView() {
        mRecyclerView = mFragmentSearchResultBinding.rvSearchList; // In xml we have given id rv_movie_list to RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);

        SearchAdapter browseAdapter = new SearchAdapter(mCustomSearchItemsList, mContext, this);
        mRecyclerView.setAdapter(browseAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        addRecycleView();
    }

    @Override
    public void musicCardClickListener(View view, Object object) {

    }

    @Override
    public void sendMusicWithPosition(View view, Object object, int position) {
        mListviewposition = position;

        //  mFragmentSearchResultBinding.relativeLayoutNowPlayingView.setVisibility(View.VISIBLE);
        CustomSearchItems customSearchItems = (CustomSearchItems) object;
        mFragmentSearchResultBinding.setCustomSearchItems(customSearchItems);
        //  ToastUtils.showLongToast(mContext, customSearchItems.getTrackUri());
        if (BaseActivity.mSpotifyAppRemote.isConnected()) {
            /*if(MainActivity.mMediaPlayer.isPlaying()){
                MainActivity.mMediaPlayer.pause();
            }*/
            if (MainActivity.mMediaPlayer != null && MainActivity.mMediaPlayer.isPlaying())
                onClickNotificationButton.onClickPlayPauseButton();
            BaseActivity.mSpotifyAppRemote.getPlayerApi().play(customSearchItems.getTrackUri());
            Intent intent = new Intent(mView.getContext(), PlayActivity.class);
            intent.putExtra(Constants.CUSTOM_SEARCH_ITEMS, (Serializable) customSearchItems);
            startActivity(intent);
        } else {
            ToastUtils.showLongToast(mContext, Constants.SPOTIFY_CONNECTION_ERROR);
        }
        /*CustomSearchItems c = new CustomSearchItems(customSearchItems.getTrackId(), customSearchItems.getTrackUri(),
                customSearchItems.getCustomAlbum().getImages()[0].getUrl(), customSearchItems.getTrackName(),
                customSearchItems.getAllArtistName(),true, true);
        BaseActivity.mSpotifyAppRemote.getContentApi().playContentItem(c);*/

    }



    void registerBroadcastListener() {
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_ACTION_PLAYBACKSTATECHANGED);
        intentFilter.addAction(Constants.BROADCAST_ACTION_METADATACHANGED);
        intentFilter.addAction(Constants.BROADCAST_ACTION_QUEUECHANGED);
        intentFilter.addAction(Constants.BROADCAST_ACTION_ACTIVE);
        intentFilter.addAction(Constants.ACTION.PREV_BUTTON_CLICKED);
        intentFilter.addAction(Constants.ACTION.NEXT_BUTTON_CLICKED);

        mContext.registerReceiver(mBroadcastReceiver, intentFilter);
    }


//    @Override
//    public void changeOnlineSong(String btnName) {
//        if(btnName.equals(Constants.NEXT)){
//            CustomSearchItems customSearchItems = nextButtonClicked();
//            if (BaseActivity.mSpotifyAppRemote.isConnected())
//                BaseActivity.mSpotifyAppRemote.getPlayerApi().play(customSearchItems.getTrackUri());
//            else
//                ToastUtils.showLongToast(mContext, Constants.SPOTIFY_CONNECTION_ERROR);
//        } else if(btnName.equals(Constants.PREV)) {
//            CustomSearchItems customSearchItems = prevButtonClicked();
//            if (BaseActivity.mSpotifyAppRemote.isConnected())
//                BaseActivity.mSpotifyAppRemote.getPlayerApi().play(customSearchItems.getTrackUri());
//            else
//                ToastUtils.showLongToast(mContext, Constants.SPOTIFY_CONNECTION_ERROR);
//        }


    //}
}
