package nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.play;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.PlayActivityListener;
import nikitaverma.example.com.audioplayerwithservice.common.receiver.MyBroadcastReceiver;
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

public class PlayActivity extends AppCompatActivity implements MakeCalls.CallListener, PlayActivityListener, View.OnClickListener {
    public static ActivityPlayBinding mActivityPlayBinding;
    public static CustomSearchItems customSearchItems = null;

    private MyTask myTask;
    private MyBroadcastReceiver mMyBroadcastReceiver;
    private BottomSheetBehavior mBottomSheetBehaviour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        //  registerBroadcastListener();
        mActivityPlayBinding = DataBindingUtil.setContentView(this, R.layout.activity_play);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.CUSTOM_SEARCH_ITEMS)) {
            customSearchItems = (CustomSearchItems) getIntent().getSerializableExtra(Constants.CUSTOM_SEARCH_ITEMS);
        }
        mBottomSheetBehaviour = BottomSheetBehavior.from(mActivityPlayBinding.nestedScrollView);
        ///  ToastUtils.showLongToast(getApplicationContext(), GENIUS_TOKEN);
        manageBottomSheet();
        mActivityPlayBinding.setCustomSearchItems(customSearchItems);
        mActivityPlayBinding.executePendingBindings();
        //   mActivityPlayBinding.nextBtn.setOnClickListener((View.OnClickListener) this);
        // mActivityPlayBinding.prevBtn.setOnClickListener((View.OnClickListener) this);


    }


    void manageBottomSheet() {
        if (mBottomSheetBehaviour != null) {
            mBottomSheetBehaviour.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {


                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            mActivityPlayBinding.expandIv.setImageResource(R.drawable.ic_expand_less_black_24dp);
                            if (GENIUS_TOKEN != null)
                                fetchLyricsApi(Constants.GENIUS_LYRICS_API);
                            else
                                ToastUtils.showLongToast(getApplicationContext(), Constants.UNABLE_TO_FETCH_LYRICS);

                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            mActivityPlayBinding.expandIv.setImageResource(R.drawable.ic_expand_more_black_24dp);
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            break;
                        }
                    }

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastListener();
    }

    void registerBroadcastListener() {
        mMyBroadcastReceiver = new MyBroadcastReceiver((PlayActivityListener) this);
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION.PLAY_ACTIVITY_PLAY_PAUSE_BUTTON_CHANGE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mMyBroadcastReceiver, intentFilter);


    }

    public void fetchLyricsApi(String apiName) {
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class, apiName);

          if (NetworkStateUtils.checkNetworkConnection(this)) {
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
            String lyricsUrl = null;
            if (lyrics.getResponse().getHits().length > 0)
                lyricsUrl = lyrics.getResponse().getHits()[0].getResult().getUrl();

            if (lyricsUrl != null) {
                myTask = new MyTask();
                myTask.execute(lyricsUrl);
            } else
                customSearchItems.setLyrics(Constants.UNABLE_TO_FETCH_LYRICS);
        }
    }

    @Override
    public void onCallFailure(@NonNull Object result, String apiName) {
        ToastUtils.showLongToast(getApplicationContext(), Constants.UNABLE_TO_FETCH_LYRICS);
        customSearchItems.setLyrics(Constants.UNABLE_TO_FETCH_LYRICS);
    }

    @Override
    public void changePlayAndPauseIcon(int resId) {
        mActivityPlayBinding.playBtn.setImageResource(resId);
    }


    @Override
    public void onBackPressed() {
        if (mBottomSheetBehaviour.getState() == 3)
            mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
        else
            super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.next_btn:
                Intent intent = new Intent();
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setAction(Constants.ACTION.NEXT_BUTTON_CLICKED);
                sendBroadcast(intent);
                break;

            case R.id.prev_btn:
                Intent intent1 = new Intent();
                intent1.addCategory(Intent.CATEGORY_DEFAULT);
                intent1.setAction(Constants.ACTION.PREV_BUTTON_CLICKED);
                sendBroadcast(intent1);
                break;

            default:
        }
    }

    /*public void changeSong(CustomSearchItems customSearchItems) {
        mActivityPlayBinding.setCustomSearchItems(customSearchItems);
        if (GENIUS_TOKEN != null)
            fetchLyricsApi(Constants.GENIUS_LYRICS_API);
        else
            ToastUtils.showLongToast(getApplicationContext(), Constants.UNABLE_TO_FETCH_LYRICS);

    }*/

    public class MyTask extends AsyncTask<String, Void, String> {

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
            if (result != null) {
                String lyrics = Html.fromHtml(result).toString();
                customSearchItems.setLyrics(lyrics);
            } else {
                customSearchItems.setLyrics(Constants.UNABLE_TO_FETCH_LYRICS);
            }
        }
    }
}