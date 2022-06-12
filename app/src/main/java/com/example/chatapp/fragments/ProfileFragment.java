package com.example.chatapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class ProfileFragment extends Fragment implements JDBC.CallBackUsers {

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

        avatar = view.findViewById(R.id.profileAvatar);
        profName = view.findViewById(R.id.profileName);
        profEmail = view.findViewById(R.id.profileEmail);
        refreshProfile = view.findViewById(R.id.refreshProfile);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sPref =
                getActivity().getSharedPreferences(String.valueOf(R.string.app_settings),
                        Context.MODE_PRIVATE);
        usUid = sPref.getString(String.valueOf(R.string.us_uid), "");



        if (fc.getUserFile(usUid) != null) {
            profImg[0] = Uri.fromFile(fc.getUserFile(usUid));
        }

        if (profImg[0] != null) {
            avatar.setImageURI(profImg[0]);
        } else {
            Toast.makeText(getContext(), "Установите фото профиля в настройках",
                    Toast.LENGTH_SHORT).show();
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

        JDBC usControl = new JDBC();
        usControl.registerCallBackUsers(this);
        usControl.getUsers(usUid, false);
    }

    private void updateProfImg() {
        if (fc.getUserFile(usUid) != null) {
            profImg[0] = Uri.fromFile(fc.getUserFile(usUid));
            avatar.setImageURI(profImg[0]);
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
}