package nikitaverma.example.com.audioplayerwithservice.views.home.model_controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nikitaverma.example.com.audioplayerwithservice.common.listener.MusicCardClickListener;
import nikitaverma.example.com.audioplayerwithservice.databinding.ItemBrowseAllBinding;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Items;
import nikitaverma.example.com.audioplayerwithservice.views.home.view_controller.BrowseAllFragment;

public class BrowseAllAdapter extends RecyclerView.Adapter<BrowseAllAdapter.BrowseViewHolder> {

    private List<Items> mBrowseItemList;
    private Context mContext;
    private MusicCardClickListener musicCardClickListener;

    public BrowseAllAdapter(List<Items> browseItemList, Context context, BrowseAllFragment browseAllFragment) {
        this.mBrowseItemList = browseItemList;
        this.mContext = context;
        musicCardClickListener = (MusicCardClickListener) browseAllFragment;
    }

    @NonNull
    @Override
    public BrowseAllAdapter.BrowseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        ItemMusicBinding itemBinding = DataBindingUtil.inflate(
//                LayoutInflater.from(parent.getContext()),
//                R.layout.item_music, parent, false);
        ItemBrowseAllBinding itemBinding = ItemBrowseAllBinding.inflate(layoutInflater, parent, false);

        return new BrowseViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseAllAdapter.BrowseViewHolder holder, final int position) {
        Items items = mBrowseItemList.get(position);
        holder.bind(items);
        holder.binding.setMusicCardClick(new MusicCardClickListener() {
            @Override
            public void musicCardClickListener(View view, Object object) {
                musicCardClickListener.sendMusicWithPosition(view, items, position);

            }

            @Override
            public void sendMusicWithPosition(View view, Object object, int position) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mBrowseItemList != null ? mBrowseItemList.size() : 0;
    }

    class BrowseViewHolder extends RecyclerView.ViewHolder {
        private ItemBrowseAllBinding binding;

        public BrowseViewHolder(ItemBrowseAllBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * We will use this function to bind instance of Movie to the row
         */
        public void bind(Items items) {
            binding.setBrowseCategory(items);
            binding.executePendingBindings();
        }
    }
}
