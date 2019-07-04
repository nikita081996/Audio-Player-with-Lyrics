package nikitaverma.example.com.audioplayerwithservice.views.home.view_controller;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MediaCompletionListener;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MusicCardClickListener;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MyBroadcastReceiver;
import nikitaverma.example.com.audioplayerwithservice.databinding.ActivityHomeBinding;
import nikitaverma.example.com.audioplayerwithservice.views.home.model.Music;
import nikitaverma.example.com.audioplayerwithservice.views.home.model_controller.HomeAdapter;
import nikitaverma.example.com.audioplayerwithservice.views.music.model.MainActivityViewModel;
import nikitaverma.example.com.audioplayerwithservice.views.music.view_controller.MainActivity;

public class HomeActivity extends AppCompatActivity implements MusicCardClickListener, MediaCompletionListener {
    public static HomeActivity mHomeActivity;
    private static List<Music> mArrayList;
    private static int mListviewposition;
    private ActivityHomeBinding binding;
    private MyBroadcastReceiver broadcastReceiver;

    public static HomeActivity getHomeActivity() {
        if (mHomeActivity == null) {
            mHomeActivity = new HomeActivity();
        }
        return mHomeActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastReceiver = new MyBroadcastReceiver((MediaCompletionListener) this);
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION.DESTROY_ACTIVITY);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(broadcastReceiver, intentFilter);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        binding.setViewModel(MainActivityViewModel.getInstance());

        binding.executePendingBindings();

        requestPermission();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            //  Toast.makeText(getApplicationContext(), "Permission has already been granted", Toast.LENGTH_LONG).show();
            // Permission has already been granted
            fetchMusic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    fetchMusic();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void fetchMusic() {
        RecyclerView recyclerView = binding.rvMusicList; // In xml we have given id rv_movie_list to RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID};

        Cursor cursor = getContentResolver().query(
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
        HomeAdapter homeAdapter = new HomeAdapter(mArrayList, this);
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
     * @param view
     * @param music
     * @param position
     */
    @Override
    public void sendMusicWithPosition(View view, Music music, int position) {
        mListviewposition = position;
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Toast.makeText(getApplicationContext(), mListviewposition + "", Toast.LENGTH_LONG).show();
        intent.putExtra("Music", music);
        view.getContext().startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(resultCode)
        {
            case 0:
                setResult(0);
                finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void registerMediaCompletionListener() {

    }

    @Override
    public void destroyApplication() {
        finishAffinity();
    }
}
