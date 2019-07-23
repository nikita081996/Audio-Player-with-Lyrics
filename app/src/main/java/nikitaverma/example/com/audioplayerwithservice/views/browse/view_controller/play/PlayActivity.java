package nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.play;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MediaCompletionListener;
import nikitaverma.example.com.audioplayerwithservice.common.listener.PlayActivityListener;
import nikitaverma.example.com.audioplayerwithservice.common.receiver.MyBroadcastReceiver;
import nikitaverma.example.com.audioplayerwithservice.common.utils.LoaderUtils;
import nikitaverma.example.com.audioplayerwithservice.common.utils.NetworkStateUtils;
import nikitaverma.example.com.audioplayerwithservice.common.utils.ToastUtils;
import nikitaverma.example.com.audioplayerwithservice.databinding.ActivityPlayBinding;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.ApiClient;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.ApiInterface;
import nikitaverma.example.com.audioplayerwithservice.helpers.api.MakeCalls;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model.CustomSearchItems;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.lyrics.Lyrics;
import retrofit2.Call;

import static nikitaverma.example.com.audioplayerwithservice.common.BaseActivity.GENIUS_TOKEN;
import static nikitaverma.example.com.audioplayerwithservice.common.BaseActivity.mSpotifyAppRemote;

public class PlayActivity extends AppCompatActivity implements MakeCalls.CallListener, PlayActivityListener {
    private ActivityPlayBinding mActivityPlayBinding;
    private CustomSearchItems customSearchItems = null;
    private MyBroadcastReceiver mMyBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        registerBroadcastListener();
        mActivityPlayBinding = DataBindingUtil.setContentView(this, R.layout.activity_play);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.CUSTOM_SEARCH_ITEMS)) {
            customSearchItems = (CustomSearchItems) getIntent().getSerializableExtra(Constants.CUSTOM_SEARCH_ITEMS);
        }
      //  ToastUtils.showLongToast(getApplicationContext(), GENIUS_TOKEN);
        mActivityPlayBinding.setCustomSearchItems(customSearchItems);
        mActivityPlayBinding.executePendingBindings();
        if (GENIUS_TOKEN != null)
            fetchLyricsApi(Constants.GENIUS_LYRICS_API);

    }

    void registerBroadcastListener(){
        mMyBroadcastReceiver = new MyBroadcastReceiver((PlayActivityListener) this);
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION.PLAY_ACTIVITY_PLAY_PAUSE_BUTTON_CHANGE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mMyBroadcastReceiver, intentFilter);

    }
    void fetchLyricsApi(String apiName) {
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class, apiName);

        if (NetworkStateUtils.checkNetworkConnection(getApplicationContext())) {
            Call call = null;
            switch (apiName) {
                case Constants.GENIUS_LYRICS_API:
                    call = apiInterface.geniusLyricsApi(GENIUS_TOKEN, customSearchItems.getTrackName());
                    MakeCalls.commonCall(call, (MakeCalls.CallListener) this, apiName);

                    break;
            }

        }
    }

    @Override
    public void onCallSuccess(@NonNull Object result, String apiName) {

        if (apiName.equals(Constants.GENIUS_LYRICS_API)) {
            Lyrics lyrics = (Lyrics) result;
            String lyricsUrl = lyrics.getResponse().getHits()[0].getResult().getUrl();
         //   ToastUtils.showLongToast(getApplicationContext(), lyricsUrl);

            if (lyricsUrl != null)
                new MyTask().execute(lyricsUrl);

        }


    }

    @Override
    public void onCallFailure(@NonNull Object result, String apiName) {
        ToastUtils.showLongToast(getApplicationContext(), Constants.UNABLE_TO_FETCH_LYRICS);
        mActivityPlayBinding.lyricsTv.setText(Constants.UNABLE_TO_FETCH_LYRICS);
    }

    @Override
    public void changePlayAndPauseIcon(int resId) {
      //  ToastUtils.showLongToast(getApplicationContext(), "pausing");

        customSearchItems.setPlayAndPauseClicked(resId);
    }

    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String lyricsUrl = params[0];
            Document doc = null;
            String title = null;
            try {
                doc = Jsoup.connect(lyricsUrl).get();
                title = doc.getElementsByClass("lyrics").html();

            } catch (IOException e) {
                e.printStackTrace();
            }
            //  Element link = doc.select("a").first();
            return title;
        }


        @Override
        protected void onPostExecute(String result) {
            if(result != null){
                String lyrics = Html.fromHtml(result).toString();
                mActivityPlayBinding.lyricsTv.setText(lyrics);
            } else {
                mActivityPlayBinding.lyricsTv.setText(Constants.UNABLE_TO_FETCH_LYRICS);
            }
        }
    }
}