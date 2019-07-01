package nikitaverma.example.com.audioplayerwithservice.views.home.model_controller;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import nikitaverma.example.com.audioplayerwithservice.common.listener.MusicCardClickListener;
import nikitaverma.example.com.audioplayerwithservice.databinding.ItemMusicBinding;
import nikitaverma.example.com.audioplayerwithservice.model.Music;
import nikitaverma.example.com.audioplayerwithservice.views.home.model.HomeMusicModel;
import nikitaverma.example.com.audioplayerwithservice.views.music.view_controller.MainActivity;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MovieViewHolder> implements MusicCardClickListener {


    private List<Music> musicList;
    private Context mContext;
    private MusicCardClickListener musicCardClickListener;

    public HomeAdapter(List<Music> musicList, Context context) {
        this.musicList = musicList;
        this.mContext = context;
        musicCardClickListener = (MusicCardClickListener) context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        ItemMusicBinding itemBinding = DataBindingUtil.inflate(
//                LayoutInflater.from(parent.getContext()),
//                R.layout.item_music, parent, false);
        ItemMusicBinding itemBinding = ItemMusicBinding.inflate(layoutInflater, parent, false);

        return new MovieViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        Music movie = musicList.get(position);
        holder.bind(movie);
        holder.binding.setMusicCardClick(new MusicCardClickListener() {
            @Override
            public void musicCardClickListener(View view, Music homeMusicModel) {
                musicCardClickListener.sendMusicWithPosition(view, homeMusicModel, position);

            }

            @Override
            public void sendMusicWithPosition(View view, Music music, int position) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return musicList != null ? musicList.size() : 0;
    }

    @Override
    public void musicCardClickListener(View view, Music homeMusicModel) {
      //  Toast.makeText(mContext, homeMusicModel.getTitle(), Toast.LENGTH_LONG).show();
      //  mContext.sendMusicWithPosition(view, homeMusicModel, )
       /* Intent intent = new Intent(mContext, MainActivity.class);

        intent.putExtra("Music", homeMusicModel);
        view.getContext().startActivity(intent);*/
    }

    @Override
    public void sendMusicWithPosition(View view, Music music, int position) {

    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        // If your layout file is something_awesome.xml then your binding class will be SomethingAwesomeBinding
        // Since our layout file is item_movie.xml, our auto generated binding class is ItemMovieBinding
        private ItemMusicBinding binding;

        //Define a constructor taking a ItemMovieBinding as its parameter
        public MovieViewHolder(ItemMusicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * We will use this function to bind instance of Movie to the row
         */
        public void bind(Music movie) {
            binding.setHomeMusicModel(movie);
            binding.executePendingBindings();
        }
    }
}
