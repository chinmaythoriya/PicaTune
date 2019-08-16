package com.chinuthor.picatune;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends Fragment {

    private RecyclerView librarySongRecyclerView;
    private AppCompatTextView libraryCurrentPositionTextView;
    private AppCompatImageButton libraryRewindButton;
    private AppCompatImageButton libraryPlayButton;
    private AppCompatImageButton libraryForwardButton;
    private AppCompatTextView libraryTotalDuration;
    private FloatingActionButton libraryAddSongFab;

    public LibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        librarySongRecyclerView = view.findViewById(R.id.library_song_recycler_view);
        libraryCurrentPositionTextView = view.findViewById(R.id.library_current_position_text_view);
        libraryRewindButton = view.findViewById(R.id.library_rewind_button);
        libraryPlayButton = view.findViewById(R.id.library_play_button);
        libraryForwardButton = view.findViewById(R.id.library_forward_button);
        libraryTotalDuration = view.findViewById(R.id.library_total_duration_text_view);
        libraryAddSongFab = view.findViewById(R.id.library_add_song_fab);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
