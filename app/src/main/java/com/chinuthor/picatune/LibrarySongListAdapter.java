package com.chinuthor.picatune;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by Admin on 6/30/2016.
 */
public class LibrarySongListAdapter extends RecyclerView.Adapter<LibrarySongListAdapter.LibrarySongListViewHolder> {

    private List<Song> songList;
    private Context context;


    public LibrarySongListAdapter(Context context, List<Song> songList) {
        this.songList = songList;
        this.context = context;
    }

    @NonNull
    @Override
    public LibrarySongListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_list_item,
                parent, false);
        return new LibrarySongListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LibrarySongListViewHolder holder, final int position) {
        final Song song = songList.get(position);
        holder.songListItemNameTextView.setText(song.getTitle());
        holder.songListItemDurationTextView.setText(AppUtility.milliSecondsToTimer(song.getDuration()));
        if (song.isPlaying()) {
            holder.songListItemNameTextView.setTextColor(Color.BLUE);
            holder.songListItemNameTextView.setTypeface(null, Typeface.BOLD);

        } else {
            holder.songListItemNameTextView.setTextColor(Color.BLACK);
            holder.songListItemNameTextView.setTypeface(null, Typeface.NORMAL);
        }

        holder.songListItemPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /*
     */
    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class LibrarySongListViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView songListItemDurationTextView;
        private final TextView songListItemNameTextView;
        private final ImageButton songListItemPlayButton;

        public LibrarySongListViewHolder(View itemView) {
            super(itemView);

            songListItemNameTextView = itemView.findViewById(R.id.song_list_item_name_text_view);
            songListItemPlayButton = itemView.findViewById(R.id.song_list_item_add_to_playlist_button);
            songListItemDurationTextView = itemView.findViewById(R.id.song_list_item_duration_text_view);

        }
    }
}
