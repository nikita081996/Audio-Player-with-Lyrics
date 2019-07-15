package nikitaverma.example.com.audioplayerwithservice.common;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
