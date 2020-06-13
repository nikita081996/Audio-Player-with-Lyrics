package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api;

import androidx.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

import nikitaverma.example.com.audioplayerwithservice.R;

public class Images implements Serializable {
    int height;

    int width;

    String url;

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.black_background)
                .into(view);
    }

    @BindingAdapter({"imageUrlWhiteBackground"})
    public static void loadImageWithWhiteBackground(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
