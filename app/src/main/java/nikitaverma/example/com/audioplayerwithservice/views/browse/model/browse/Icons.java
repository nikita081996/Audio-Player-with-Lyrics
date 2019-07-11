package nikitaverma.example.com.audioplayerwithservice.views.browse.model.browse;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.App;

public class Icons implements Serializable
{
    private String width;

    private String url;

    private String height;

    public String getWidth ()
    {
        return width;
    }

    public void setWidth (String width)
    {
        this.width = width;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getHeight ()
    {
        return height;
    }

    public void setHeight (String height)
    {
        this.height = height;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [width = "+width+", url = "+url+", height = "+height+"]";
    }

//    @BindingAdapter({"bind:imageUrl"})
//    public static void loadImage(ImageView view, String imageUrl) {
//        Picasso.with(view.getContext())
//                .load(imageUrl)
//                .placeholder(R.drawable.black_background)
//                .into(view);
//    }

    /*@BindingAdapter({"bind:imageUrl"})
    public static void loadImage(CardView view, String imageUrl) {
        *//*Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.black_background)
                .into(view);
*//*
        URL url = null;
        try {
            url = new URL(imageUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable background = new BitmapDrawable(App.getContext().getResources(), bmp);
            view.setBackgroundDrawable(background);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

}
