package com.chinuthor.picatune;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static com.chinuthor.picatune.AppUtility.getSongProgress;
import static com.chinuthor.picatune.AppUtility.milliSecondsToTimer;


/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends Fragment {

    private static final int GET_SONG_REQUEST_CODE = 6561;
    private static final int REQUEST_PERMISSION_CODE = 6845;
    Handler mHandler = new Handler();
    private RecyclerView librarySongRecyclerView;
    private AppCompatTextView libraryCurrentPositionTextView;
    private AppCompatImageButton libraryRewindButton;
    private AppCompatImageButton libraryPlayButton;
    private AppCompatImageButton libraryForwardButton;
    private AppCompatTextView libraryTotalDuration;
    private AppCompatSeekBar librarySongProgressSeekBar;
    private FloatingActionButton libraryAddSongFab;
    private User user;
    //    private List<Song> list;
    private MediaPlayer mediaPlayer;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentDuration = mediaPlayer.getCurrentPosition();
            int totalDuration = mediaPlayer.getDuration();

            libraryCurrentPositionTextView.setText(milliSecondsToTimer(String.valueOf(currentDuration)));

            librarySongProgressSeekBar.setProgress(getSongProgress(totalDuration, currentDuration));

            mHandler.postDelayed(this, 1000);
        }
    };
    private LibrarySongListAdapter adapter;
    private DatabaseHelper dbHelper;

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
        librarySongProgressSeekBar = view.findViewById(R.id.library_song_progress_seek_bar);
        libraryAddSongFab = view.findViewById(R.id.library_add_song_fab);

        user = Objects.requireNonNull(getArguments()).getParcelable("user");
        user.setSongList(Objects.requireNonNull(user).getSongList() != null ? user.getSongList() : new ArrayList<Song>());
        if (user.getSongList().size() != 0) {
            user = prepareSongList(user);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new DatabaseHelper(getContext());
        adapter = new LibrarySongListAdapter(getContext(), user.getSongList());

        librarySongRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        librarySongRecyclerView.setItemAnimator(new DefaultItemAnimator());
        librarySongRecyclerView.addItemDecoration(new RecyclerDividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL, 16));
        librarySongRecyclerView.setAdapter(adapter);

        librarySongRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), librarySongRecyclerView, new RecyclerTouchListener.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("RecyclerView touched", position + "");
                try {
                    playMySong(user.getSongList().get(position), position);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        libraryAddSongFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check runtime permission, ask if permission not granted
                if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Toast.makeText(getContext(), "Please allow external storage permission", Toast.LENGTH_SHORT).show();
                    } else {
                        //permission is not granted, ask for the permission
                        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
                    }
                } else {
                    addSongToLibrary();
                }
            }
        });

        libraryPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        libraryPlayButton.setImageResource(android.R.drawable.ic_media_play);
                    } else {
                        mediaPlayer.start();
                        libraryPlayButton.setImageResource(android.R.drawable.ic_media_pause);
                    }
                }
            }
        });

        libraryRewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 2000);
                    }
                }
            }
        });

        libraryForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 2000);
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_SONG_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if ((data != null) && (data.getData() != null)) {
                Log.d("LibraryFragment--------", data.getScheme() + " -> " + data.toString());
                Log.d("LibraryFragment--------", data.getStringExtra(MediaStore.Audio.Media._ID) + " -> " + data.toString());
                Uri uri = data.getData();
                String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
                String[] projection = {
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.DURATION
                };

                Cursor cursor = Objects.requireNonNull(getActivity()).managedQuery(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        selection,
                        null,
                        null);

                Cursor selectedSongCursor = Objects.requireNonNull(getContext()).getContentResolver().query(uri, null, null, null, null);

                while (cursor.moveToNext()) {
                    if (Objects.requireNonNull(selectedSongCursor).moveToFirst()) {
                        if (Objects.equals(selectedSongCursor.getString(selectedSongCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)), cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)))) {
                            Log.d("FOUNDDDDDD", cursor.getString(0) + "  this is idddddddddddddddddddddd");
                            user.getSongList().add(cursorToSong(cursor));
                            user = dbHelper.addSongToLibrary(user, cursor.getInt(0));
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }
        }
    }

    private Song cursorToSong(Cursor cursor) {
        Song song = new Song();
        song.setId(cursor.getInt(0));
        song.setArtist(cursor.getString(1));
        song.setTitle(cursor.getString(2));
        song.setData(cursor.getString(3));
        song.setDisplayName(cursor.getString(4));
        song.setDuration(cursor.getString(5));
        song.setPlaying(false);
        song.setOnPlayList(false);
        return song;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            // camera permission result
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission is granted recently, start camera
                addSongToLibrary();
                Toast.makeText(getContext(), "storage permission granted", Toast.LENGTH_LONG).show();
            } else {
                // permission is not granted, can not
                Toast.makeText(getContext(), "storage permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void playMySong(Song song, int songPosition) throws IOException {
        /*if(currentSongId!=null && song.getId()!=currentSongId)
        {
            mediaPlayer.release();
            mediaPlayer=null;
        }*/
        notifyDataChanged(songPosition, false);
        if (mediaPlayer == null) {
            // currentSongId=song.getId();
            mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(song.getData()));
            mediaPlayer.start();
            libraryPlayButton.setImageResource(android.R.drawable.ic_media_pause);
            libraryTotalDuration.setText(milliSecondsToTimer(song.getDuration()));
            updateSongProgress();

        } else {
            if (mediaPlayer.isPlaying()) {
                notifyDataChanged(songPosition, false);
                mediaPlayer.pause();
                libraryPlayButton.setImageResource(android.R.drawable.ic_media_play);
            } else {
                notifyDataChanged(songPosition, true);
                mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(song.getData()));
                mediaPlayer.start();
                libraryPlayButton.setImageResource(android.R.drawable.ic_media_pause);
            }
        }
    }

    private void notifyDataChanged(int position, boolean playing) {
        Song song = user.getSongList().get(position);
        song.setPlaying(playing);
        user.getSongList().set(position, song);
        adapter.notifyDataSetChanged();
    }

    private void updateSongProgress() {
        mHandler.postDelayed(runnable, 1000);
    }

    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    public void addSongToLibrary() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("audio/*");

        startActivityForResult(Intent.createChooser(intent, "Select app to choose mp3 file"), GET_SONG_REQUEST_CODE);
    }

    private User prepareSongList(User user) {
        if (user.getSongList() != null && user.getSongList().size() != 0) {
            for (int i = 0; i < user.getSongList().size(); i++) {
                String selection = MediaStore.Audio.Media._ID + " = " + user.getSongList().get(i).getId();
                String[] projection = {
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.DURATION
                };

                Cursor cursor = Objects.requireNonNull(getActivity()).managedQuery(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        selection,
                        null,
                        null);

                if (cursor.moveToFirst()) {
                    user.getSongList().set(i, cursorToSong(cursor));
                }
            }
        }
        return user;
    }
}
