package nikitaverma.example.com.audioplayerwithservice.views.home.view_controller;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toast;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.BaseActivity;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MediaCompletionListener;
import nikitaverma.example.com.audioplayerwithservice.common.receiver.MyBroadcastReceiver;
import nikitaverma.example.com.audioplayerwithservice.common.utils.BottomNavigationBehaviorUtils;
import nikitaverma.example.com.audioplayerwithservice.common.utils.FragmentUtils;

public class HomeActivity extends BaseActivity implements MediaCompletionListener {

    private LocalFragment mLocalFragment;
    private BrowseAllFragment mBrowseAllFragment;
    private MyBroadcastReceiver broadcastReceiver;
    private ActionBar toolbar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_local:
                    toolbar.setTitle(R.string.title_local);
                    addFragmentToView(Constants.LOCAL_FRAGMENT);
                  //  toolbar.show();
                    return true;
                case R.id.navigation_browse:
                    toolbar.setTitle(R.string.title_browse);
                    //toolbar.hide();
                    addFragmentToView(Constants.BROWSE_ALL_FRAGMENT);

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = getSupportActionBar();
        broadcastReceiver = new MyBroadcastReceiver((MediaCompletionListener) this);
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION.DESTROY_ACTIVITY);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(broadcastReceiver, intentFilter);

        /*binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        binding.setViewModel(MainActivityViewModel.getInstance());

        binding.executePendingBindings();
        */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehaviorUtils());
        requestPermission();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            //  Toast.makeText(getApplicationContext(), "Permission has already been granted", Toast.LENGTH_LONG).show();
            // Permission has already been granted
            // fetchMusic();
            addFragmentToView(Constants.LOCAL_FRAGMENT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    // fetchMusic();
                    addFragmentToView(Constants.LOCAL_FRAGMENT);
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void addFragmentToView(String fragmentName) {
        FragmentManager fm = getSupportFragmentManager();

        switch (fragmentName) {
            case Constants.LOCAL_FRAGMENT:
                if (mLocalFragment == null) {
                    mLocalFragment = new LocalFragment();
                }
                FragmentUtils.getFragment(fm, mLocalFragment, R.id.fragment_data, Constants.LOCAL_FRAGMENT);

                break;
            case Constants.BROWSE_ALL_FRAGMENT:
                if (mBrowseAllFragment == null) {
                    mBrowseAllFragment = new BrowseAllFragment();
                }
                FragmentUtils.getFragment(fm, mBrowseAllFragment, R.id.fragment_data, Constants.BROWSE_ALL_FRAGMENT);
                break;
            default:
                if (mLocalFragment == null) {
                    mLocalFragment = new LocalFragment();
                }
                FragmentUtils.getFragment(fm, mLocalFragment, R.id.fragment_data, Constants.LOCAL_FRAGMENT);

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

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }

    }
}
