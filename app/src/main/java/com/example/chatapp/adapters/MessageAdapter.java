package com.example.chatapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.models.User;
import com.example.chatapp.tools.FileControl;
import com.example.chatapp.tools.JDBC;
import com.example.chatapp.R;
import com.example.chatapp.models.Message;

import java.io.File;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> msgs;
    private int MSG_TYPE_RIGHT = 1;
    private int MSG_TYPE_LEFT = 0;
    private int MSG_TYPE_LEFT_GROUP = 2;

    private String usUid;
    private JDBC msgControl;
    private Context context;

    public MessageAdapter(Context context,String addr, List<Message> messageList, String usUid){
        msgControl = new JDBC(addr);
        msgs = messageList;
        this.usUid = usUid;
        this.context = context;

    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout_right, parent, false);
            return new MessageViewHolder(view);
        } else if (viewType == MSG_TYPE_LEFT_GROUP ) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout_group,
                    parent, false);
            return new MessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout,
                    parent, false);
            return new MessageViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message msg = msgs.get(position);

        holder.bindMessage(msg);

        if(position == msgs.size() - 1 && getItemViewType(position) == MSG_TYPE_RIGHT){
            if(msg.getIsseen()){
                holder.txtSeen.setText("Просмотрено");
            }
            else{
                holder.txtSeen.setText("Доставлено");
            }
        }
        else {
            holder.txtSeen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (msgs.get(position).getSender().equals(usUid)){
            return MSG_TYPE_RIGHT;
        } else if (msgs.get(position).getReceiver().equals("all")) {
            return MSG_TYPE_LEFT_GROUP;
        }
        else {
            msgControl.setMessagesRead(msgs.get(position).getChatId(), usUid);
            msgs.get(position).setIsseen(true);
            return MSG_TYPE_LEFT;
        }
    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public ImageView avatar;
        public RelativeLayout root;
        public TextView usName;
        public TextView usMsg;
        public TextView txtSeen;
        private FileControl fc = new FileControl();
        //public CardView cView;

        public MessageViewHolder(View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar);
            root = itemView.findViewById(R.id.lineRoot);
            usName = itemView.findViewById(R.id.userName);
            usMsg = itemView.findViewById(R.id.userMessageText);
            txtSeen = itemView.findViewById(R.id.msgSeen);
            //cView = itemView.findViewById(R.id.messageCard);
        }

        public void bindMessage(Message msg){
            //usName.setText(msg.getSender());
            usMsg.setText(msg.getMessageText());

            if (msg.getReceiver().equals("all") && getItemViewType() == MSG_TYPE_LEFT_GROUP) {
                usName.setText(msg.getSenderName());

                if (fc.getUserFile(msg.getSender()) != null) {
                    RoundedBitmapDrawable roundImage;
                    roundImage = getRoundImage(fc.getUserFile(msg.getSender()).getPath());
                    avatar.setImageDrawable(roundImage);
                }

            }

        }

        private RoundedBitmapDrawable getRoundImage(String filePath){
            Bitmap batmapBitmap = BitmapFactory.decodeFile(filePath);
            RoundedBitmapDrawable circularBitmapDrawable =
                    RoundedBitmapDrawableFactory.create(context.getResources(), batmapBitmap);
            circularBitmapDrawable.setCircular(true);

            return circularBitmapDrawable;
        }
    }
}
