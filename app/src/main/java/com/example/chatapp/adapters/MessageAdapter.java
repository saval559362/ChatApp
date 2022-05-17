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

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> msgs;
    private int MSG_TYPE_RIGHT = 1;
    private int MSG_TYPE_LEFT = 0;

    public MessageAdapter(List<Message> messageList){
        msgs = messageList;
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
        Message msg = msgs.get(position);

        holder.bindMessage(msg);

        if(position == msgs.size() - 1 && getItemViewType(position) == MSG_TYPE_RIGHT){
            if(msg.getIsseen()){
                holder.txtSeen.setText("Seen");
            }
            else{
                holder.txtSeen.setText("Delivered");
            }
        }
        else {
            holder.txtSeen.setVisibility(View.GONE);
        }
    }
    //TODO тут заглушка, адаптер не рабочий
    @Override
    public int getItemViewType(int position) {
        if (msgs.get(position).getSender().equals("DORK")){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout root;
        public TextView usName;
        public TextView usMsg;
        public TextView txtSeen;
        //public CardView cView;

        public MessageViewHolder(View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.lineRoot);
            usName = itemView.findViewById(R.id.userName);
            usMsg = itemView.findViewById(R.id.userMessageText);
            txtSeen = itemView.findViewById(R.id.msgSeen);
            //cView = itemView.findViewById(R.id.messageCard);
        }

        public void bindMessage(Message msg){
            usName.setText(msg.getSender());
            usMsg.setText(msg.getMessageText());
        }
    }
}
