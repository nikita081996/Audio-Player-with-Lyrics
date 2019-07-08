package nikitaverma.example.com.audioplayerwithservice.views.browse.model.browse;

public class BrowseCategory {

    private Categories categories;

    public Categories getCategories ()
    {
        return categories;
    }

    public void setCategories (Categories categories)
    {
        this.categories = categories;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [categories = "+categories+"]";
    }
}
