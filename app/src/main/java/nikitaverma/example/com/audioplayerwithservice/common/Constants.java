package nikitaverma.example.com.audioplayerwithservice.common;

public class Constants {

    public static String NEXT = "next";
    public static String PREV = "prev";
    public static String PLAY = "play";

    public interface ACTION {
        String NOTIFY = "nikitaverma.example.com.audioplayerwithservice.action.notify";
        String MAIN_ACTION = "nikitaverma.example.com.audioplayerwithservice.action.main";
        String PREV_ACTION = "nikitaverma.example.com.audioplayerwithservice.action.prev";
        String PLAY_ACTION = "nikitaverma.example.com.audioplayerwithservice.action.play";
        String NEXT_ACTION = "nikitaverma.example.com.audioplayerwithservice.action.next";
        String STOPFOREGROUND_ACTION = "nikitaverma.example.com.audioplayerwithservice.action.stopforeground";
        String CHANNEL_ID = "nikitaverma.example.com.audioplayerwithservice.notification.ANDROID";
        String CHANNEL_NAME = "ANDROID_CHANNEL_NAME";
    }

    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
    }
}
