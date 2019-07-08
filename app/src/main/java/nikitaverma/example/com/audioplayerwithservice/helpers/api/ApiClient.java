package nikitaverma.example.com.audioplayerwithservice.helpers.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nikitaverma.example.com.audioplayerwithservice.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final int TIME_OUT = 100;
    private static final TypeAdapter<Number> IntTypeAdapter = new TypeAdapter<Number>() {
        @Override
        public void write(JsonWriter out, Number value)
                throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                String result = in.nextString();
                if ("".equals(result)) {
                    return null;
                }
                double d = Double.parseDouble(result);
                return (int) d;
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    };

    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .registerTypeAdapter(int.class, IntTypeAdapter)
            .registerTypeAdapter(Integer.class, IntTypeAdapter)
            .create();
    private static final Retrofit.Builder retroBuilder =
            new Retrofit.Builder()
                    .baseUrl("https://api.spotify.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit retrofit = retroBuilder.build();

    private static final HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final OkHttpClient.Builder httpClientBuilder =
            new OkHttpClient.Builder()
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS);

    private static final Interceptor authInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            return chain.proceed(request);
        }
    };

    public static <T> T createService(Class<T> serviceClass) {
        List<Interceptor> list = httpClientBuilder.interceptors();
        if (BuildConfig.DEBUG && !list.contains(logging)) {
            httpClientBuilder.addInterceptor(logging);
        }
        if (!list.contains(authInterceptor)) {
            httpClientBuilder.addInterceptor(authInterceptor);
        }

        retroBuilder.client(httpClientBuilder.build());
        retrofit = retroBuilder.build();
        return retrofit.create(serviceClass);
    }
}
