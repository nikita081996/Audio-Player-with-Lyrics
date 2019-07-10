package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api;

import java.io.Serializable;

public class Images implements Serializable {
    int height;

    int width;

    String url;

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
