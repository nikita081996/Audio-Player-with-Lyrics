package nikitaverma.example.com.audioplayerwithservice.views.browse.model_controller;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nikitaverma.example.com.audioplayerwithservice.common.listener.MusicCardClickListener;
import nikitaverma.example.com.audioplayerwithservice.databinding.ItemSearchResultBinding;
import nikitaverma.example.com.audioplayerwithservice.views.browse.model.custom_model.CustomSearchItems;
import nikitaverma.example.com.audioplayerwithservice.views.browse.view_controller.SearchFragment;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<CustomSearchItems> mCustomSearchItems;
    private Context mContext;
    private MusicCardClickListener musicCardClickListener;

    public SearchAdapter(List<CustomSearchItems> customSearchItems, Context context, SearchFragment browseFragment) {
        this.mCustomSearchItems = customSearchItems;
        this.mContext = context;
        musicCardClickListener = (MusicCardClickListener) browseFragment;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSearchResultBinding itemBinding = ItemSearchResultBinding.inflate(layoutInflater, parent, false);

        return new SearchViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, final int position) {
        CustomSearchItems items = mCustomSearchItems.get(position);
        holder.bind(items);
        holder.binding.setMusicCardClick(new MusicCardClickListener() {
            @Override
            public void musicCardClickListener(View view, Object object) {
                musicCardClickListener.sendMusicWithPosition(view, items, position);

            }

            @Override
            public void sendMusicWithPosition(View view, Object music, int position) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mCustomSearchItems != null ? mCustomSearchItems.size() : 0;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private ItemSearchResultBinding binding;

        public SearchViewHolder(ItemSearchResultBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * We will use this function to bind instance of Movie to the row
         */
        public void bind(CustomSearchItems items) {
            binding.setCustomSearchItems(items);
            binding.executePendingBindings();
        }
    }
}