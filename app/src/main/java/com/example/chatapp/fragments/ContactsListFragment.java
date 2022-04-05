package com.example.chatapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.R;
import com.example.chatapp.adapters.UserAdapter;
import com.example.chatapp.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ContactsListFragment extends Fragment {
    private RecyclerView usersListRec;
    private UserAdapter usAdapter;
    private List<User> users;

    private FirebaseFirestore dbUsers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        usersListRec = view.findViewById(R.id.contactsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        usersListRec.setLayoutManager(linearLayoutManager);
        usersListRec.setHasFixedSize(true);

        dbUsers = FirebaseFirestore.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        users = new ArrayList<>();
        usAdapter = new UserAdapter(getContext(), users);
        usersListRec.setAdapter(usAdapter);

        readUsers();
    }

    public void readUsers(){
        dbUsers.collection("users").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {

                        String uid = d.getId();
                        String login = d.get("login", String.class);
                        String email = d.get("email", String.class);
                        String number = d.get("number", String.class);

                        users.add(new User(uid, login, number, email));
                    }
                    // after adding the data to recycler view.
                    // we are calling recycler view notifuDataSetChanged
                    // method to notify that data has been changed in recycler view.
                    usAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {

                });
    }
}