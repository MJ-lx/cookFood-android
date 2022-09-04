package com.example.cookfood.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookfood.R;

import java.io.IOException;

public class HomeVideo extends Fragment {
    private View rootview;
    private VideoView videoView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootview == null){
            rootview = inflater.inflate(R.layout.home_video,container,false);
        }
        try {
            play_rtsp();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootview;
    }
    private void play_rtsp() throws IOException {
        String videoUrl2 = "https://www.bilibili.com/video/BV1AX4y1M7JB?share_source=copy_web";
        Uri uri = Uri.parse(videoUrl2);
        videoView = (VideoView)rootview.findViewById(R.id.home_video);
        //videoView.setVideoPath(path);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }
}
