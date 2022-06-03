package com.example.chatapp.fragments;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.chatapp.JDBC;
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

public class ContactsListFragment extends Fragment implements UserAdapter.OnUserListener, JDBC.CallBackUsers, JDBC.CallBackReadChats {

    private RecyclerView usersListRec;
    private UserAdapter usAdapter;

    private ObservableList<User> participiantsInfoList;

    private RelativeLayout spinner;

    private JDBC usersControl = new JDBC();

    private String exludedUser;

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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        SharedPreferences spf =
                getActivity().getSharedPreferences(String.valueOf(R.string.app_settings),
                        Context.MODE_PRIVATE);
        exludedUser = spf.getString(String.valueOf(R.string.us_uid), "");

        participiantsInfoList = new ObservableArrayList<>();

        usAdapter = new UserAdapter(participiantsInfoList, this);
        usersListRec.setAdapter(usAdapter);

        participiantsInfoList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<User>>() {
            @Override
            public void onChanged(ObservableList<User> sender) {
                usAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<User> sender, int positionStart, int itemCount) {
                usAdapter.notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<User> sender, int positionStart, int itemCount) {
                usAdapter.notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<User> sender, int fromPosition, int toPosition, int itemCount) {
                usAdapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<User> sender, int positionStart, int itemCount) {
                usAdapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        });

        readUsers(exludedUser);
    }

    public void readUsers(String exludedUser){

        usersControl.registerCallBackUsers(this);
        usersControl.getUsers(exludedUser, true);
    }
    private User user;
    @Override
    public void onUserClick(int position) {

        user = participiantsInfoList.get(position);
        usersControl.registerCallBackReadChats(this);
        usersControl.findChats(exludedUser, user.Uid);

    }

    @Override
    public void getUsers(List<User> users) {
        participiantsInfoList.addAll(users);
    }

    @Override
    public void readChats(ObservableList<ChatModel> chatList) {
        if (chatList.size() != 0) {
            getActivity().runOnUiThread(() -> {
                Intent intent = new Intent(getContext(),MainChatView.class);
                if (Arrays.stream(chatList.get(0).Participants).count() > 2) {
                    intent.putExtra("partc_count", Arrays.stream(chatList.get(0).Participants).count());
                }
                intent.putExtra("chat_id", chatList.get(0).ChatId);
                intent.putExtra("receiver", user.Uid);
                startActivity(intent);
            });
        } else {
            usersControl.createChat(exludedUser, user.Uid);
        }
    }
}