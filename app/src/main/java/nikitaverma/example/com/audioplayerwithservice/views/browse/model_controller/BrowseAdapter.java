package nikitaverma.example.com.audioplayerwithservice.views.browse.model_controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nikitaverma.example.com.audioplayerwithservice.common.listener.MusicCardClickListener;
import nikitaverma.example.com.audioplayerwithservice.databinding.ItemBrowseBinding;
import nikitaverma.example.com.audioplayerwithservice.databinding.ItemSearchResultBinding;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model.CustomSearchItems;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.search_api.Items;
import nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.SearchFragment;
import nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.browse.BrowseFragment;

public class BrowseAdapter  extends RecyclerView.Adapter<BrowseAdapter.BrowseViewHolder> {

    private List<Items> mItems;
    private Context mContext;
    private MusicCardClickListener musicCardClickListener;

    public BrowseAdapter(List<Items> items, Context context, BrowseFragment browseFragment) {
        this.mItems = items;
        this.mContext = context;
        musicCardClickListener = (MusicCardClickListener) browseFragment;
    }

    @NonNull
    @Override
    public BrowseAdapter.BrowseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemBrowseBinding itemBinding = ItemBrowseBinding.inflate(layoutInflater, parent, false);

        return new BrowseViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseAdapter.BrowseViewHolder holder, final int position) {
        Items items = mItems.get(position);
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
        return mItems != null ? mItems.size() : 0;
    }

    class BrowseViewHolder extends RecyclerView.ViewHolder {
        // If your layout file is something_awesome.xml then your binding class will be SomethingAwesomeBinding
        // Since our layout file is item_movie.xml, our auto generated binding class is ItemMovieBinding
        private ItemBrowseBinding binding;

        //Define a constructor taking a ItemMovieBinding as its parameter
        public BrowseViewHolder(ItemBrowseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * We will use this function to bind instance of Movie to the row
         */
        public void bind(Items items) {
            binding.setItems(items);
            binding.setImage(items.getImages()[0]);
            binding.executePendingBindings();
        }
    }
}