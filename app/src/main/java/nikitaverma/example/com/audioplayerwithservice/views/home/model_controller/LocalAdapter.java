package nikitaverma.example.com.audioplayerwithservice.views.home.model_controller;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nikitaverma.example.com.audioplayerwithservice.common.listener.MusicCardClickListener;
import nikitaverma.example.com.audioplayerwithservice.databinding.ItemLocalBinding;
import nikitaverma.example.com.audioplayerwithservice.views.home.model.Music;
import nikitaverma.example.com.audioplayerwithservice.views.home.view_controller.LocalFragment;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder> {


    private List<Music> musicList;
    private Context mContext;
    private MusicCardClickListener musicCardClickListener;

    public LocalAdapter(List<Music> musicList, Context context, LocalFragment localFragment) {
        this.musicList = musicList;
        this.mContext = context;
        musicCardClickListener = (MusicCardClickListener) localFragment;
    }

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemLocalBinding itemBinding = ItemLocalBinding.inflate(layoutInflater, parent, false);

        return new LocalViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder holder, final int position) {
        Music movie = musicList.get(position);
        holder.bind(movie);
        holder.binding.setMusicCardClick(new MusicCardClickListener() {
            @Override
            public void musicCardClickListener(View view, Object object) {
                musicCardClickListener.sendMusicWithPosition(view, object, position);

            }

            @Override
            public void sendMusicWithPosition(View view, Object object, int position) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return musicList != null ? musicList.size() : 0;
    }

    class LocalViewHolder extends RecyclerView.ViewHolder {
        private ItemLocalBinding binding;

        public LocalViewHolder(ItemLocalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * We will use this function to bind instance of Movie to the row
         */
        public void bind(Music movie) {
            binding.setMusicModel(movie);
            binding.executePendingBindings();
        }
    }
}
