package nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.utils.FragmentUtils;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model.CustomSearchItems;
import nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.SearchFragment;

public class SearchResultActivity extends AppCompatActivity {

    private SearchFragment mSearchFragment;
    private List<CustomSearchItems> customSearchItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.CUSTOM_SEARCH_ITEMS)) {
            customSearchItems = (List<CustomSearchItems>) getIntent().getSerializableExtra(Constants.CUSTOM_SEARCH_ITEMS);

        }
        addFragmentToView(Constants.SEARCH_FRAGMENT);
    }

    private void addFragmentToView(String fragmentName) {
        FragmentManager fm = getSupportFragmentManager();

        switch (fragmentName) {
            case Constants.SEARCH_FRAGMENT:
                mSearchFragment = new SearchFragment(customSearchItems);
                FragmentUtils.getFragment(fm, mSearchFragment, R.id.fragment_data, Constants.SEARCH_FRAGMENT);

                break;

            default:
                if (mSearchFragment == null) {
                    mSearchFragment = new SearchFragment(customSearchItems);
                }
                FragmentUtils.getFragment(fm, mSearchFragment, R.id.fragment_data, Constants.SEARCH_FRAGMENT);

        }
    }
}
