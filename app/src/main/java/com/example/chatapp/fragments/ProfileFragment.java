package com.example.chatapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ProfileFragment extends Fragment {

    private String currUid = FirebaseAuth.getInstance().getUid();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private View avatar;
    private TextView profName;
    private TextView profEmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        avatar = view.findViewById(R.id.profileAvatar);
        profName = view.findViewById(R.id.profileName);
        profEmail = view.findViewById(R.id.profileEmail);

        FirebaseFirestore.getInstance().collection("users").document(currUid)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            profName.setText(queryDocumentSnapshots.get("login").toString());
            profEmail.setText(queryDocumentSnapshots.get("email").toString());
        }).addOnFailureListener(e -> {
            Log.d("--------PROFILE FIREBASE ERROR--------", e.getMessage());

        });

        return view;
    }
}