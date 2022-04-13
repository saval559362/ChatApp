package com.example.chatapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> usersList;
    private OnUserListener mOnUserListener;

    public UserAdapter(List<User> us, OnUserListener onUserListener) {
        usersList = us;

        this.mOnUserListener = onUserListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_view, parent, false);
        return new UserViewHolder(view, mOnUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = usersList.get(position);
        holder.bindUser(user);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener
    {
        private RelativeLayout root;
        private ImageView userImg;
        private TextView userNm;
        private OnUserListener onUserListener;

        public UserViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);

            root = itemView.findViewById(R.id.userRoot);
            userImg = itemView.findViewById(R.id.userImage);
            userNm = itemView.findViewById(R.id.userName);
            this.onUserListener = onUserListener;
            itemView.setOnClickListener(this);
        }

        public void bindUser(User us){
            userNm.setText(us.Name);
        }

        @Override
        public void onClick(View view) {
            onUserListener.onUserClick(getAbsoluteAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }

    }

    public interface OnUserListener {
        void onUserClick(int position);
    }
}
