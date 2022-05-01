package com.example.chatapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> msgs;
    FirebaseUser fUser;
    private int MSG_TYPE_RIGHT = 1;
    private int MSG_TYPE_LEFT = 0;

    public MessageAdapter(List<Message> messageList){
        msgs = messageList;
        fUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout_right, parent, false);
            return new MessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
            return new MessageViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.bindMessage(msgs.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (msgs.get(position).getSender().equals(fUser.getUid())){           //TODO какого то хуя начал выбивать ошибку
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
        //return MSG_TYPE_LEFT;
    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout root;
        public TextView usName;
        public TextView usMsg;
        //public CardView cView;

        public MessageViewHolder(View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.lineRoot);
            usName = itemView.findViewById(R.id.userName);
            usMsg = itemView.findViewById(R.id.userMessageText);
            //cView = itemView.findViewById(R.id.messageCard);
        }

        public void bindMessage(Message msg){
            usName.setText(msg.getSender());
            usMsg.setText(msg.getMessageText());
        }

        public void getViewType(int pos) {

        }
    }
}
