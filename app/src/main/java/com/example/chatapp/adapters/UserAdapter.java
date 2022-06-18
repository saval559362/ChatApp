package com.example.chatapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.models.User;
import com.example.chatapp.tools.FileControl;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> usersList;
    private OnUserListener mOnUserListener;

    private Context context;


    public UserAdapter(Context context,List<User> us, OnUserListener onUserListener) {
        usersList = us;
        this.context = context;
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
            implements View.OnClickListener, View.OnLongClickListener, FileControl.PhotoDownloadCallback
    {
        private RelativeLayout root;
        private ImageView userImg;
        private TextView userNm;
        private OnUserListener onUserListener;

        private FileControl fc;

        public UserViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);

            root = itemView.findViewById(R.id.userRoot);
            userImg = itemView.findViewById(R.id.userImage);
            userNm = itemView.findViewById(R.id.userName);
            this.onUserListener = onUserListener;
            itemView.setOnClickListener(this);
            fc = new FileControl();
            fc.registerPhotoCallback(this);
        }

        private User user;

        public void bindUser(User us){
            user = us;
            userNm.setText(us.Name);

            updateImg();
        }

        @Override
        public void onClick(View view) {
            onUserListener.onUserClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }

        @Override
        public void photoDownloaded() {
            updateImg();
        }

        private void updateImg() {
            RoundedBitmapDrawable roundImage;
            if (fc.getUserFile(user.Uid) != null) {
                roundImage = getRoundImage(fc.getUserFile(user.Uid).getPath());
                userImg.setImageDrawable(roundImage);
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

    public interface OnUserListener {
        void onUserClick(int position);
    }
}
