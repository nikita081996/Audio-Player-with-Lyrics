package nikitaverma.example.com.audioplayerwithservice.common;

public class Constants {

    public static String NEXT = "next";
    public static String PREV = "prev";
    public static String PLAY = "play";
    public static final String SOBER = "sober";
    public static final String CLOSER = "closer";
    public static final String WHO_SAYS = "who say's";
    public static final String RESET_TIME = "00:00";
    public static final String MUSIC = "Music";
    public static final String WIFI = "WIFI";
    public static final String MOBILE = "MOBILE";
    public static final String CUSTOM_SEARCH_LIST_ITEMS = "custom_search_list_items";
    public static final String CUSTOM_SEARCH_ITEMS = "custom_search_items";
    public static final String CATEGORY_ID = "browse_id";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    public static final String ANDROID_CHANNEL_ID = "nikitaverma.example.com.spotify.ANDROID";
    public static final String EXCEPTION_LOG_TAG = "exception";
    public static final String MESSAGE_EXCEPTION_TAG = "message";
    public static final String EXCEPTION_TAG = "exception : ";
    public static final String TOKEN = "TOKEN";
    public static final String CLIENT_ID_CONST = "client_id";
    public static final String SCOPE = "scope";
    public static final String REDIRECT_URI_CONST = "redirect_uri";
    public static final String RESPONSE_TYPE = "response_type";




    /**
     * fragment name
     */
    public static final String LOCAL_FRAGMENT = "local";
    public static final String BROWSE_ALL_FRAGMENT = "browse_all";
    public static final String BROWSE_FRAGMENT = "browse";
    public static final String SEARCH_FRAGMENT = "search";


    /**
     * Api constants
     */
    public static final String TOKEN_PREFIX = "Authorization: Bearer ";

    public static final String CLIENT_ID = "b8db16d30bca4b9bbebe71349baf998c";
    public static final String REDIRECT_URI = "nikitaverma://callback";
    public static final String BASE_URL = "https://api.spotify.com/v1/";

    public static final int REQUEST_CODE = 1337;
    public static final String COUNTRY = "IN";

    /**
     * Genius api constants
     */
    public static final String GENIUS_AUTH_URL = "https://api.genius.com/oauth/authorize";
    public static final String GENIUS_TOKEN_PREFIX = "Bearer ";
    public static final String GENIUS_CLIENT_ID = "tLtB7-XFFmKJ6rsb_y2y7mEkLuBibN9zdyg6_ewQshvz440DJa8X0wB00FBTE-WR";
    public static final String GENIUS_REDIRECT_URI = "nikitaverma://genius";
    public static final String GENIUS_BASE_URL = "https://api.genius.com/";
    public static final String GENIUS_TOKEN = "token";
    public static final String GENIUS_SCOPE = "me";



    /**
     * Spotify connection and authentication error constants
     */
    public static final String TOKEN_ERROR = "Unable to access token";
    public static final String SPOTIFY_CONNECTION_ERROR = "Unable to connect to Spotify App";
    public static final String PLEASE_INSTALL_SPOTIFY_APP = "Please install Spotify App";
    public static final String RESTART_THE_APP_TO_LISTEN_ONLINE_SONG = " Please restart the app to listen online song";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String UNABLE_TO_FETCH_LYRICS = "Unable to fetch lyrics";
    public static final String FETCHING_LYRICS = "Fetching Lyrics . . .";


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
    public static final String BROWSE_ALL_API = "browse_all_api";
    public static final String BROWSE_API = "browse_api";
    public static final String SEARCH_API = "search_api";
    public static final String PLAYlISTS_TRACK_API = "playlists_track_api";
    public static final String GENIUS_LYRICS_API = "genius_lyrics_api";

    /**
     * Broadcast Action Constants
     */
    public static final String BROADCAST_ACTION_PLAYBACKSTATECHANGED = "com.spotify.music.playbackstatechanged";
    public static final String BROADCAST_ACTION_METADATACHANGED = "com.spotify.music.metadatachanged";
    public static final String BROADCAST_ACTION_QUEUECHANGED = "com.spotify.music.queuechanged";
    public static final String BROADCAST_ACTION_ACTIVE = "com.spotify.music.active";

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
        String PLAY_ACTIVITY_PLAY_PAUSE_BUTTON_CHANGE = "play.pause.button.change";
        String NEXT_BUTTON_CLICKED = "next.clicked";
        String PREV_BUTTON_CLICKED = "prev.clicked";


    }

    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
    }

}
