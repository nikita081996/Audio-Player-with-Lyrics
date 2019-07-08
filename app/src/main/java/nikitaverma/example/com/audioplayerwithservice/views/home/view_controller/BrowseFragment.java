package nikitaverma.example.com.audioplayerwithservice.views.home.view_controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.BaseActivity;
import nikitaverma.example.com.audioplayerwithservice.common.Constants;
import nikitaverma.example.com.audioplayerwithservice.common.listener.CallBrowseApiListener;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MyBroadcastReceiver;
import nikitaverma.example.com.audioplayerwithservice.common.utils.LoaderUtils;
import nikitaverma.example.com.audioplayerwithservice.databinding.FragmentBrowseBinding;

public class BrowseFragment extends Fragment implements CallBrowseApiListener{

    private View view;
    private Context mContext;
    private FragmentBrowseBinding mFragmentBrowseBinding;
    private MyBroadcastReceiver mBroadcastReceiver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mFragmentBrowseBinding == null) {
            mFragmentBrowseBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_browse, container, false);
            view = mFragmentBrowseBinding.getRoot();
            // view = inflater.inflate(R.layout.activity_home, container, false);

            // binding.setViewModel(MainActivityViewModel.getInstance());
            mFragmentBrowseBinding.executePendingBindings();
        }


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        registerBroadcastListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(BaseActivity.TOKEN == null)
        LoaderUtils.showLoader(getActivity());
    }

    void registerBroadcastListener() {
        mBroadcastReceiver = new MyBroadcastReceiver((CallBrowseApiListener) this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_ACTION_BROWSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        mContext.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    public void callBrowseApi(String browseApi) {
      //  callApi(browseApi);
        LoaderUtils.hideLoader(getActivity());
        Toast.makeText(mContext, "Connected "+ browseApi, Toast.LENGTH_LONG).show();
    }


}
