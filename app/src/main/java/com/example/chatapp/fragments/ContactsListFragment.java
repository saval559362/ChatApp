package com.example.chatapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.activities.MainChatView;
import com.example.chatapp.adapters.UserAdapter;
import com.example.chatapp.models.ChatModel;
import com.example.chatapp.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ContactsListFragment extends Fragment implements UserAdapter.OnUserListener {
    private RecyclerView usersListRec;
    private UserAdapter usAdapter;
    private List<User> users;
    private DatabaseReference chtRef;
    private String currUsUid;

    private FirebaseFirestore dbUsers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        usersListRec = view.findViewById(R.id.contactsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        usersListRec.setLayoutManager(linearLayoutManager);
        usersListRec.setHasFixedSize(true);
        dbUsers = FirebaseFirestore.getInstance();
        chtRef = FirebaseDatabase.getInstance().getReference("Chats");
        currUsUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        users = new ArrayList<>();
        usAdapter = new UserAdapter(users, this);
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

                    usAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private boolean userCurr = false;
    private boolean userSel = false;
    private String ref;
    @Override
    public void onUserClick(int position) {
        User currUser = users.get(position);
        List<String> partic = new ArrayList<>();
        chtRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {




                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        /*if (userCurr && userSel) {
            Intent intent = new Intent(getContext(), MainChatView.class);

            intent.putExtra("msgRef", ref);
            intent.putExtra("receiver", currUser.Uid);
            startActivity(intent);

        } else {
            String chRef = UUID.randomUUID().toString().replace('-','f');

            chtRef.child(chRef).child("chatName").setValue(currUser.Name + "'s chat");
            chtRef.child(chRef).child("lastMessage").setValue("");
            List<String> parts = Arrays.asList(currUsUid, currUser.Uid);
            chtRef.child(chRef).child("paricipiants").setValue(parts);
        }*/
    }
}