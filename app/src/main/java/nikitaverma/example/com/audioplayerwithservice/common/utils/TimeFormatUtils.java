package nikitaverma.example.com.audioplayerwithservice.common.utils;

import java.util.concurrent.TimeUnit;

public class TimeFormatUtils {
    public static String convertToTimeFormat(int progress){
        String time = String.format("%02d : %02d ",
                TimeUnit.MILLISECONDS.toMinutes(progress),
                TimeUnit.MILLISECONDS.toSeconds(progress) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(progress)));

        return time;
    }

}
