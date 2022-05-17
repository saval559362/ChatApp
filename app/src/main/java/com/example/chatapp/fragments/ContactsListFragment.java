package com.example.chatapp.fragments;

import android.app.Application;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.activities.MainActivity;
import com.example.chatapp.activities.MainChatView;
import com.example.chatapp.adapters.UserAdapter;
import com.example.chatapp.models.ChatModel;
import com.example.chatapp.models.ParticipiantsInfo;
import com.example.chatapp.models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ContactsListFragment extends Fragment implements UserAdapter.OnUserListener {
    private RecyclerView usersListRec;
    private UserAdapter usAdapter;
    private List<User> users;

    private List<ParticipiantsInfo> participiantsInfoList;

    private RelativeLayout spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        usersListRec = view.findViewById(R.id.contactsList);
        spinner = view.findViewById(R.id.loadingPanelContacts);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        usersListRec.setLayoutManager(linearLayoutManager);
        usersListRec.setHasFixedSize(true);
        /*dbUsers = FirebaseFirestore.getInstance();
        chtRef = MainActivity.getFirebaseReference();
        currUsUid = MainActivity.getFirebaseAuth().getCurrentUser().getUid();*/
        //TODO получение списка контактов
        participiantsInfoList = new ArrayList<>();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        users = new ArrayList<>();
        usAdapter = new UserAdapter(users, this);
        usersListRec.setAdapter(usAdapter);

    }

    public void readUsers(){
        /*dbUsers.collection("users").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {

                        String uid = d.getId();
                        String login = d.get("login", String.class);
                        String email = d.get("email", String.class);
                        String number = d.get("number", String.class);

                        if (!uid.equals(currUsUid)) {
                            users.add(new User(uid, login, number, email));
                        }
                    }

                    usAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                });*/
        //TODO Считывание контактов пользователя
    }
    private boolean usersExist = false;

    @Override
    public void onUserClick(int position) {
        /*User currUser = users.get(position);
        String ref = "";

        for (ParticipiantsInfo part : participiantsInfoList) {

            if (part.Uids.containsAll(Arrays.asList(currUsUid, currUser.Uid))) {
                usersExist = true;
                ref = part.DatabaseRef;
                break;
            }
        }


        Intent intent = new Intent(getContext(), MainChatView.class);

        if (usersExist) {
            ref += "/messages";
            intent.putExtra("msgRef", ref);
            intent.putExtra("receiver", currUser.Uid);
            startActivity(intent);
            Log.d("USERCLICK", "Chat exist " + ref);

        } else {
            String chRef = UUID.randomUUID().toString().replace('-','f');

            chtRef.child(chRef).child("chatName").setValue(currUser.Name + "'s chat");
            chtRef.child(chRef).child("lastMessage").setValue("");
            List<String> parts = Arrays.asList(currUsUid, currUser.Uid);
            chtRef.child(chRef).child("paricipiants").setValue(parts);

            ref += chtRef.child(chRef) + "/messages";

            intent.putExtra("msgRef", ref);
            intent.putExtra("receiver", currUser.Uid);
            startActivity(intent);
            Log.d("USERCLICK", "Chat don't exist");
        }

        usersExist = false;
        ref = "";*/
        //TODO реализация логики создания чата, если такого нет, при клике на пользователя

    }
}