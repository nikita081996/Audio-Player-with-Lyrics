package nikitaverma.example.com.audioplayerwithservice.common;

public class Constants {

    public static final String SOBER = "sober";
    public static final String CLOSER = "closer";
    public static final String WHO_SAYS = "who say's";
    public static final String RESET_TIME = "00:00";
    public static String NEXT = "next";
    public static String PREV = "prev";
    public static String PLAY = "play";
    public static final String MUSIC = "Music";


    public interface ACTION {
        String NOTIFY = "nikitaverma.example.com.audioplayerwithservice.action.notify";
        String MAIN_ACTION = "nikitaverma.example.com.audioplayerwithservice.action.main";
        String PREV_ACTION = "nikitaverma.example.com.audioplayerwithservice.action.prev";
        String PLAY_ACTION = "nikitaverma.example.com.audioplayerwithservice.action.play";
        String NEXT_ACTION = "nikitaverma.example.com.audioplayerwithservice.action.next";
        String STOPFOREGROUND_ACTION = "nikitaverma.example.com.audioplayerwithservice.action.stopforeground";
        String CHANNEL_ID = "nikitaverma.example.com.audioplayerwithservice.notification.ANDROID";
        String CHANNEL_NAME = "ANDROID_CHANNEL_NAME";
        String MEDIA_COMPLETION_LISTENER_ACTION = "register.completion.listener";
        String DESTROY_ACTIVITY = "register.destroy.listener";
    }

    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
    }

    /**
     * fragment name
     */
    public static final String LOCAL_FRAGMENT ="local";
    public static final String BROWSE_FRAGMENT ="browse";

    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    public static final String ANDROID_CHANNEL_ID = "nikitaverma.example.com.spotify.ANDROID";

    public static final String EXCEPTION_LOG_TAG = "exception";
    public static final String MESSAGE_EXCEPTION_TAG = "message";
    public static final String EXCEPTION_TAG = "exception : ";
    public static final String API_NAME = "api_name";
    public static final String TOKEN = "TOKEN";

    /**
     * Api constants
     */
    public static final String TOKEN_PREFIX = "Authorization: Bearer ";
    public static final String TOKEN_ERROR = "Unable to access token";
    public static final String SPOTIFY_CONNECTION_ERROR = "Unable to connect to Spotify App";
    public static final String PLEASE_INSTALL_SPOTIFY_APP = "Please install Spotify App";

    public static final String CLIENT_ID = "b8db16d30bca4b9bbebe71349baf998c";
    public static final String REDIRECT_URI = "nikitaverma://callback";
    public static final int REQUEST_CODE = 1337;
    public static final String COUNTRY = "IN";

    /**
     * scope constants
     */
    public static final String USER_READ_PRIVATE_SCOPE = "user-read-private";
    public static final String PLAYLIST_READ = "playlist-read";
    public static final String PLAYLIST_READ_PRIVATE = "playlist-read-private";
    public static final String STREAMING = "streaming";


    /**
     * Api name
     */

    public static final String BROWSE_API = "browse_api";
    /**
     * Broadcast Action Constants
     */
    public static final String BROADCAST_ACTION_PLAYBACKSTATECHANGED = "com.spotify.music.playbackstatechanged";
    public static final String BROADCAST_ACTION_METADATACHANGED = "com.spotify.music.metadatachanged";
    public static final String BROADCAST_ACTION_QUEUECHANGED = "com.spotify.music.queuechanged";
    public static final String BROADCAST_ACTION_ACTIVE = "com.spotify.music.active";
    public static final String BROADCAST_ACTION_BROWSE = "com.browse.api";


}
