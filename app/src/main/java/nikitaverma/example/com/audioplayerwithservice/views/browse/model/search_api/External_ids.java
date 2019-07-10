package nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api;

import java.io.Serializable;

public class External_ids implements Serializable {

    private String isrc;

    public String getIsrc ()
    {
        return isrc;
    }

    public void setIsrc (String isrc)
    {
        this.isrc = isrc;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [isrc = "+isrc+"]";
    }

}
