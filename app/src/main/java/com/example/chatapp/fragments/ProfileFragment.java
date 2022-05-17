package com.example.chatapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.R;
import com.example.chatapp.activities.MainActivity;
import com.example.chatapp.tools.PictureCoder;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicReference;

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private TextView profName;
    private TextView profEmail;
    private ImageView avatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        avatar = view.findViewById(R.id.profileAvatar);
        profName = view.findViewById(R.id.profileName);
        profEmail = view.findViewById(R.id.profileEmail);

        /*PictureCoder coder = new PictureCoder();

        FirebaseFirestore.getInstance().collection("users").document(currUid)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            profName.setText(queryDocumentSnapshots.get("login").toString());
            profEmail.setText(queryDocumentSnapshots.get("email").toString());

            AtomicReference<String> imageBase64 = new AtomicReference<>();
            imageBase64.set(queryDocumentSnapshots.get("avatarPicture").toString());
            avatar.setImageBitmap(coder.fromBase64(imageBase64.get()));

        }).addOnFailureListener(e -> {
            Log.d("--------PROFILE FIREBASE ERROR--------", e.getMessage());

        });*/
        //TODO реализация логики заполнения профиля

        return view;
    }


}