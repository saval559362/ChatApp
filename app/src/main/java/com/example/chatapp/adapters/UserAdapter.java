package com.example.chatapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> usersList;
    private Context mContext;
    public UserAdapter(Context cContext,List<User> us) {
        mContext = cContext;
        usersList = us;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_card_view, parent, false);
        return new UserViewHolder(view);
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
    {
        private LinearLayout root;
        private ImageView userImg;
        private TextView userNm;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.userRoot);
            userImg = itemView.findViewById(R.id.userImage);
            userNm = itemView.findViewById(R.id.userName);
        }

        public void bindUser(User us){
            userNm.setText(us.Name);
        }
    }
}
