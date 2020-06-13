package nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.browse;

import android.content.Intent;
import android.content.IntentFilter;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MediaCompletionListener;
import nikitaverma.example.com.audioplayerwithservice.common.receiver.MyBroadcastReceiver;
import nikitaverma.example.com.audioplayerwithservice.common.utils.FragmentUtils;

public class BrowseActivity extends AppCompatActivity implements MediaCompletionListener{
    private String mCategoryId;
    private BrowseFragment mBrowseFragment;
    private MyBroadcastReceiver broadcastReceiver;
    private ActionBar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        broadcastReceiver = new MyBroadcastReceiver((MediaCompletionListener) this);
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION.DESTROY_ACTIVITY);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(broadcastReceiver, intentFilter);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.CATEGORY_ID)) {
            mCategoryId = getIntent().getStringExtra(Constants.CATEGORY_ID);
        }
        mToolbar = getSupportActionBar();
        String toolbarTitle = (mCategoryId.charAt(0) + "").toUpperCase() + mCategoryId.substring(1, mCategoryId.length());
        mToolbar.setTitle(toolbarTitle);
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

    @Override
    public void registerMediaCompletionListener() {

    }

    @Override
    public void destroyApplication() {
        setResult(0);
        finishAffinity();

    }
}