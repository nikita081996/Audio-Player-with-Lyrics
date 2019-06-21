package nikitaverma.example.com.audioplayerwithservice.common.listener;

import android.app.Notification;

public interface OnClickNotificationButton {
    /**
     * call when user click on close button in notification
     */
    void onCloseNotificationListener();

    /**
     * call when user click on next or prev button in notification
     *
     * @param btnName is next or prev button
     */
    void udpateMusicIndex(String btnName);

    /**
     * initlize Media Player
     */
    void initlizeMediaPlayer();

    /**
     * call when user click on Play or Pause button
     */
    void onClickPlayPauseButton();

    /**
     * show notification when media is playing
     */
    void showNotificationStatus();

    /**
     * get Notification status
     */
    Notification getNotificationStatus();

    boolean checkMediaIsPlayingOrNot();

    void onSeekBarChange(int progress);

}
