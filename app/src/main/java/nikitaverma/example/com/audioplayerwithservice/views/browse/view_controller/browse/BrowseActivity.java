package nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.browse;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.utils.FragmentUtils;
import nikitaverma.example.com.audioplayerwithservice.common.utils.ToastUtils;

public class BrowseActivity extends AppCompatActivity {
    private String mCategoryId;
    private BrowseFragment mBrowseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.CATEGORY_ID)) {
            mCategoryId = getIntent().getStringExtra(Constants.CATEGORY_ID);
        }
        addFragmentToView(Constants.BROWSE_FRAGMENT);
    }

    private void addFragmentToView(String fragmentName) {
        FragmentManager fm = getSupportFragmentManager();

        switch (fragmentName) {
            case Constants.BROWSE_FRAGMENT:
                mBrowseFragment = new BrowseFragment(mCategoryId);
                FragmentUtils.getFragment(fm, mBrowseFragment, R.id.fragment_data, Constants.BROWSE_FRAGMENT);
                break;

            default:
                mBrowseFragment = new BrowseFragment(mCategoryId);
                FragmentUtils.getFragment(fm, mBrowseFragment, R.id.fragment_data, Constants.BROWSE_FRAGMENT);

        }
    }
}