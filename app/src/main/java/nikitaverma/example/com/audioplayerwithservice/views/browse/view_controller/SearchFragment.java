package nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import nikitaverma.example.com.audioplayerwithservice.R;
import nikitaverma.example.com.audioplayerwithservice.common.listener.MusicCardClickListener;
import nikitaverma.example.com.audioplayerwithservice.common.utils.ToastUtils;
import nikitaverma.example.com.audioplayerwithservice.databinding.FragmentSearchResultBinding;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model.CustomSearchItems;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model_controller.SearchAdapter;
import nikitaverma.example.com.audioplayerwithservice.views.home.model_controller.BrowseAdapter;

import static nikitaverma.example.com.audioplayerwithservice.common.BaseActivity.TOKEN;

@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment implements MusicCardClickListener {

    private View view;
    private Context mContext;
    private FragmentSearchResultBinding mFragmentSearchResultBinding;
    private RecyclerView mRecyclerView;
    private List<CustomSearchItems> mCustomSearchItems;

    @SuppressLint("ValidFragment")
    public SearchFragment(List<CustomSearchItems> customSearchItems){
        if(customSearchItems != null){
            this.mCustomSearchItems = customSearchItems;
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mFragmentSearchResultBinding == null) {
            mFragmentSearchResultBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_search_result, container, false);
            view = mFragmentSearchResultBinding.getRoot();
            mFragmentSearchResultBinding.executePendingBindings();
        }
        return view;
    }

    void addRecycleView(){
        mRecyclerView = mFragmentSearchResultBinding.rvSearchList; // In xml we have given id rv_movie_list to RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);

        SearchAdapter browseAdapter = new SearchAdapter(mCustomSearchItems, mContext, this);
        mRecyclerView.setAdapter(browseAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addRecycleView();
    }

    @Override
    public void musicCardClickListener(View view, Object object) {

    }

    @Override
    public void sendMusicWithPosition(View view, Object object, int position) {
        CustomSearchItems customSearchItems = (CustomSearchItems) object;
        ToastUtils.showLongToast(mContext, position+"");
    }
}
