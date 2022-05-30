package com.example.chatapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
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
import com.example.chatapp.adapters.ChatAdapter;
import com.example.chatapp.models.ChatModel;
import com.example.chatapp.models.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ChatsViewFragment extends Fragment implements ChatAdapter.OnChatListener, JDBC.CallBackReadChats {

    private RecyclerView chatsListRecycler;
    private ChatAdapter chtAdapter;
    private ObservableList<ChatModel> chats;
    private String currUser;

    private RelativeLayout loadingSpinner;

    private JDBC readData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats_view, container, false);

        chatsListRecycler = view.findViewById(R.id.chatsListRec);
        loadingSpinner = view.findViewById(R.id.loadingPanelChat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        chatsListRecycler.setLayoutManager(linearLayoutManager);
        chatsListRecycler.setHasFixedSize(true);

        //TODO получение текущего пользователя и списка чатов, где он участник

        SharedPreferences sPref =
                getActivity().getSharedPreferences(String.valueOf(R.string.app_settings), Context.MODE_PRIVATE);
        String usUid = sPref.getString(String.valueOf(R.string.us_uid), "");

        readData = new JDBC();
        readData.registerCallBackReadChats(this);
        readData.readChats(usUid);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        chats = new ObservableArrayList<>();
        chtAdapter = new ChatAdapter(chats, this);
        chatsListRecycler.setAdapter(chtAdapter);

        chats.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<ChatModel>>() {
            @Override
            public void onChanged(ObservableList<ChatModel> sender) {
                chtAdapter.notifyDataSetChanged();
                Log.d("NOTIFYCALLBACK", "onChanged");
            }

            @Override
            public void onItemRangeChanged(ObservableList<ChatModel> sender, int positionStart, int itemCount) {
                chtAdapter.notifyItemRangeChanged(positionStart, itemCount);
                Log.d("NOTIFYCALLBACK", "onItemRangeChanged");
            }

            @Override
            public void onItemRangeInserted(ObservableList<ChatModel> sender, int positionStart, int itemCount) {
                chtAdapter.notifyItemRangeInserted(positionStart, itemCount);
                Log.d("NOTIFYCALLBACK", "onItemRangeInserted");
            }

            @Override
            public void onItemRangeMoved(ObservableList<ChatModel> sender, int fromPosition, int toPosition, int itemCount) {
                chtAdapter.notifyItemMoved(fromPosition, toPosition);
                Log.d("NOTIFYCALLBACK", "onItemRangeMoved");
            }

            @Override
            public void onItemRangeRemoved(ObservableList<ChatModel> sender, int positionStart, int itemCount) {
                chtAdapter.notifyItemRangeRemoved(positionStart, itemCount);
                Log.d("NOTIFYCALLBACK", "onItemRangeRemoved");
            }
        });

        readChats();
        Log.d("----CHATSVIEWFRAGMENT----", "onViewCreated done");
    }

    public void readChats(){


    }

    @Override
    public void onChatClick(int position) {
        ChatModel chat = chats.get(position);
        Intent intent = new Intent(getContext(), MainChatView.class);
        if (Arrays.stream(chat.Participants).count() > 2) {
            intent.putExtra("partc_count", Arrays.stream(chat.Participants).count());
        }
        intent.putExtra("chat_id", chat.ChatId);
        startActivity(intent);
    }

    @Override
    public void readChats(ObservableList<ChatModel> chatList) {
        if (chatList != null) {
            chats.addAll(chatList);

            chats.sort(Comparator.comparingLong(ChatModel::getLastMessageTime));
            Collections.reverse(chats);
        } else {
            Toast.makeText(getActivity(), "Nothing!", Toast.LENGTH_SHORT).show();
        }

    }

}