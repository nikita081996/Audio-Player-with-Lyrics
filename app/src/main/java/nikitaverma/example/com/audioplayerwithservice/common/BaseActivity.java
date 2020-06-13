package nikitaverma.example.com.audioplayerwithservice.common;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import nikitaverma.example.com.audioplayerwithservice.common.receiver.MyBroadcastReceiver;
import nikitaverma.example.com.audioplayerwithservice.common.utils.LoggerUtils;
import nikitaverma.example.com.audioplayerwithservice.common.utils.ToastUtils;
import okhttp3.HttpUrl;


public class BaseActivity extends AppCompatActivity implements Connector.ConnectionListener {
    public static SpotifyAppRemote mSpotifyAppRemote;
    public static String SPOTIFY_TOKEN;
    public static String GENIUS_TOKEN;

    private ConnectionParams mConnectionParams;
    private AuthenticationRequest mAuthenticationRequest;
    private MyBroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadcastListener();
        mConnectionParams =
                new ConnectionParams.Builder(Constants.CLIENT_ID)
                        .setRedirectUri(Constants.REDIRECT_URI)
                        .showAuthView(true)
                        .build();
        SpotifyAppRemote.connect(this, mConnectionParams, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void registerBroadcastListener() {
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_ACTION_PLAYBACKSTATECHANGED);
        intentFilter.addAction(Constants.BROADCAST_ACTION_METADATACHANGED);
        intentFilter.addAction(Constants.BROADCAST_ACTION_QUEUECHANGED);
        intentFilter.addAction(Constants.BROADCAST_ACTION_ACTIVE);

        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    /**
     * call when AuthenticationClient recieved data from Spotify App
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == Constants.REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response != null) {
                switch (response.getType()) {
                    case TOKEN:
                        SPOTIFY_TOKEN = Constants.TOKEN_PREFIX + response.getAccessToken();
                        LoggerUtils.d(Constants.TOKEN, response.getAccessToken());
                        geniusAuth();
                        break;

                    case ERROR:
                        // Authenticate through browser
                        //   AuthenticationClient.openLoginInBrowser(this, mAuthenticationRequest);
                        break;

                    default:
                        ToastUtils.showLongToast(getApplication(), Constants.SOMETHING_WENT_WRONG + Constants.RESTART_THE_APP_TO_LISTEN_ONLINE_SONG);
                }
            }

        }
    }

    /**
     * call when AuthenticationClient recieved data from Spotify Web
     *
     * @param intent
     */

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            AuthenticationResponse response = AuthenticationResponse.fromUri(uri);
            if (response != null) {
                switch (response.getType()) {
                    // Response was successful and contains auth token
                    case TOKEN:
                        if (uri.getHost().equals("genius")) {
                            GENIUS_TOKEN = Constants.GENIUS_TOKEN_PREFIX + response.getAccessToken();
                        } else if (uri.getHost().equals("callback")) {
                            SPOTIFY_TOKEN = Constants.TOKEN_PREFIX + response.getAccessToken();
                            geniusAuth();
                        }
                        break;

                    case ERROR:
                        ToastUtils.showLongToast(getApplication(), Constants.TOKEN_ERROR + Constants.RESTART_THE_APP_TO_LISTEN_ONLINE_SONG);
                        break;

                    default:
                        ToastUtils.showLongToast(getApplication(), Constants.SOMETHING_WENT_WRONG + Constants.RESTART_THE_APP_TO_LISTEN_ONLINE_SONG);

                }
            }

        }
    }

    @Override
    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
        mSpotifyAppRemote = spotifyAppRemote;
        mAuthenticationRequest = new AuthenticationRequest.Builder(Constants.CLIENT_ID, AuthenticationResponse.Type.TOKEN, Constants.REDIRECT_URI)
                .setScopes(new String[]{Constants.USER_READ_PRIVATE_SCOPE, Constants.PLAYLIST_READ, Constants.PLAYLIST_READ_PRIVATE, Constants.STREAMING})
                .setShowDialog(true)
                .build();

        AuthenticationClient.openLoginActivity(this, Constants.REQUEST_CODE, mAuthenticationRequest);
    }

    /**
     * when spotify is not installed
     *
     * @param throwable
     */
    @Override
    public void onFailure(Throwable throwable) {
        ToastUtils.showLongToast(getApplicationContext(), throwable.getMessage());
        ToastUtils.showLongToast(getApplicationContext(), Constants.PLEASE_INSTALL_SPOTIFY_APP);

    }

    void geniusAuth() {
        HttpUrl authorizeUrl = HttpUrl.parse(Constants.GENIUS_AUTH_URL)
                .newBuilder()
                .addQueryParameter(Constants.CLIENT_ID_CONST, Constants.GENIUS_CLIENT_ID)
                .addQueryParameter(Constants.SCOPE, Constants.GENIUS_SCOPE)
                .addQueryParameter(Constants.REDIRECT_URI_CONST, Constants.GENIUS_REDIRECT_URI)
                .addQueryParameter(Constants.RESPONSE_TYPE, Constants.GENIUS_TOKEN)

                .build();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(String.valueOf(authorizeUrl.url())));
        startActivity(i);
    }

}
