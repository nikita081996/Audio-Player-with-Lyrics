package nikitaverma.example.com.audioplayerwithservice.helpers.api;

import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.error.ErrorResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class to handle all response of all calls make to the server
 * Created by Nikita Verma on 21/06/2019
 */
public class MakeCalls {

    private static CallListener mListener = null;

    /**
     * Common call for all activities to handle common response
     */
    public static void commonCall(Call call, @NonNull CallListener callListerner, final String apiName) {
        mListener = callListerner;

        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                Log.d("response", response.body() + " ");
                if ((response.body() != null) && response.isSuccessful()) {
                    if (mListener != null) {
                        mListener.onCallSuccess(response.body(), apiName);
                    }
                } else {
                    //    assert response.body() != null;
                    if (response.errorBody() != null) {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            //check if unable to parse the exception model or null is comming
                            if (errorResponse != null) {
                                callListerner.onCallFailure(errorResponse.getError().getMessage() + Constants.RESTART_THE_APP_TO_LISTEN_ONLINE_SONG, apiName);
                            } else {
                                callListerner.onCallFailure(Constants.SOMETHING_WENT_WRONG, apiName);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            callListerner.onCallFailure(e.getMessage(), apiName);
                        }
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                Log.d("error", t.getMessage() + " ");
                mListener.onCallFailure(t.getMessage(), "failure error");
            }
        });
    }

    /**
     * Listerner to listion success or failure network calls
     */
    public interface CallListener {
        void onCallSuccess(@NonNull Object result, final String apiName);

        void onCallFailure(@NonNull Object result, final String apiName);
    }

}
