package com.example.chatapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.models.FileInfo;
import com.example.chatapp.models.User;
import com.example.chatapp.tools.FileControl;
import com.example.chatapp.tools.JDBC;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements JDBC.CallBackUsers,
        FileControl.PhotoDownloadCallback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private TextView profName;
    private TextView profEmail;
    private ImageView avatar;
    private SwipeRefreshLayout refreshProfile;

    private final Uri[] profImg = {null};
    private FileControl fc = new FileControl();
    private String usUid;
    private String usEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        fc.registerPhotoCallback(this);
        avatar = view.findViewById(R.id.profileAvatar);
        profName = view.findViewById(R.id.profileName);
        profEmail = view.findViewById(R.id.profileEmail);
        refreshProfile = view.findViewById(R.id.refreshProfile);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RoundedBitmapDrawable roundImage;
        SharedPreferences sPref =
                getActivity().getSharedPreferences(String.valueOf(R.string.app_settings),
                        Context.MODE_PRIVATE);
        usUid = sPref.getString(String.valueOf(R.string.us_uid), "");

        if (fc.getUserFile(usUid) != null) {
            roundImage = getRoundImage(fc.getUserFile(usUid).getPath());
            avatar.setImageDrawable(roundImage);
        }

        refreshProfile.setOnRefreshListener(() -> {
            refreshProfile.setRefreshing(true);
            String[] info = new String[2];
            info[0] = sPref.getString(String.valueOf(R.string.us_name), "");
            info[1] = sPref.getString(String.valueOf(R.string.us_email), "");
            if (info[0] != null && info[1] != null) {
                profName.setText(info[0]);
                profEmail.setText(info[1]);
                refreshProfile.setRefreshing(false);
            } else {
                updateProfImg();
            }

        });

        JDBC usControl = new JDBC(getString(R.string.ip_address));
        usControl.registerCallBackUsers(this);
        usControl.getUsers(usUid, false);
    }

    private void updateProfImg() {
        RoundedBitmapDrawable roundImage;
        if (fc.getUserFile(usUid) != null) {
            roundImage = getRoundImage(fc.getUserFile(usUid).getPath());
            avatar.setImageDrawable(roundImage);
        }

        refreshProfile.setRefreshing(false);
    }

    @Override
    public void getUsers(List<User> users) {
        getActivity().runOnUiThread(() -> {
            User us = users.get(0);
            profName.setText(us.Name);
            profEmail.setText(us.Email);

            SharedPreferences sPref =
                    getActivity().getSharedPreferences(String.valueOf(R.string.app_settings),
                            Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(String.valueOf(R.string.us_email), us.Email);
            ed.putString(String.valueOf(R.string.us_name), us.Name);
            ed.apply();
        });
    }

    private RoundedBitmapDrawable getRoundImage(String filePath){
        Bitmap batmapBitmap = BitmapFactory.decodeFile(filePath);
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), batmapBitmap);
        circularBitmapDrawable.setCircular(true);

        return circularBitmapDrawable;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateProfImg();
    }

    @Override
    public void photoDownloaded() {
        updateProfImg();
    }
}