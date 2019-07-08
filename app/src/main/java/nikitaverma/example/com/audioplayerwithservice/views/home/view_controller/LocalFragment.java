package nikitaverma.example.com.audioplayerwithservice.views.home.view_controller;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MusicCardClickListener;
import nikitaverma.example.com.audioplayerwithservice.databinding.FragmentLocalBinding;
import nikitaverma.example.com.audioplayerwithservice.views.home.model.Music;
import nikitaverma.example.com.audioplayerwithservice.views.home.model_controller.HomeAdapter;
import nikitaverma.example.com.audioplayerwithservice.views.music.model.MainActivityViewModel;
import nikitaverma.example.com.audioplayerwithservice.views.music.view_controller.MainActivity;

/**
 * first fragment in Home Activity
 */
public class LocalFragment extends Fragment implements MusicCardClickListener {

    public static LocalFragment mLocalFragment;
    private static List<Music> mArrayList;
    private static int mListviewposition;
    private FragmentLocalBinding mFragmentLocalBinding;
    private View view;
    private Context mContext;


    public static LocalFragment getHomeActivity() {
        if (mLocalFragment == null) {
            mLocalFragment = new LocalFragment();
        }
        return mLocalFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mFragmentLocalBinding == null) {
            mFragmentLocalBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_local, container, false);
            view = mFragmentLocalBinding.getRoot();
            // view = inflater.inflate(R.layout.activity_home, container, false);

            mFragmentLocalBinding.setViewModel(MainActivityViewModel.getInstance());
            mFragmentLocalBinding.executePendingBindings();

        }

        fetchMusic();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    void fetchMusic() {
        RecyclerView recyclerView = mFragmentLocalBinding.rvMusicList; // In xml we have given id rv_movie_list to RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID};

        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);
        mArrayList = new ArrayList();

        while (cursor.moveToNext()) {
            Music mediaModel = new Music();
            mediaModel.setTitle(cursor.getString(0));
            mediaModel.setAlbum(cursor.getString(5));
            mediaModel.setData(cursor.getString(2));
            mediaModel.setArtist(cursor.getString(1));
            mArrayList.add(mediaModel);
        }
        cursor.close();
        HomeAdapter homeAdapter = new HomeAdapter(mArrayList, mContext, this);
        recyclerView.setAdapter(homeAdapter);

    }

    public Music nextButtonClicked() {
        if (mArrayList.size() == mListviewposition + 1) {
            mListviewposition = 0;
        } else {
            mListviewposition = mListviewposition + 1;
        }
        Music music = mArrayList.get(mListviewposition);
        return music;
        /*path = mMusicAdapter.getPath(mListviewposition);
        songTitle = mMusicAdapter.getSongTitle(mListviewposition);
        albumName = mMusicAdapter.getAlbum(mListviewposition);
        artistName = mMusicAdapter.getArtist(mListviewposition);*/
    }

    public Music prevButtonClicked() {
        if (mListviewposition == 0) {
            mListviewposition = mArrayList.size() - 1;
        } else {
            mListviewposition = mListviewposition - 1;
        }
        Music music = mArrayList.get(mListviewposition);
        return music;

    }

    @Override
    public void musicCardClickListener(View view, Music homeMusicModel) {

    }

    /**
     * call when listview item clicked
     *
     * @param view
     * @param music
     * @param position
     */
    @Override
    public void sendMusicWithPosition(View view, Music music, int position) {
        mListviewposition = position;
        Intent intent = new Intent(mContext, MainActivity.class);
        Toast.makeText(mContext, mListviewposition + "", Toast.LENGTH_LONG).show();
        intent.putExtra(Constants.MUSIC, music);
        view.getContext().startActivity(intent);
    }
}
