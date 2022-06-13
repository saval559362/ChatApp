package com.example.chatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class MultiAdapterContacts extends
        RecyclerView.Adapter<MultiAdapterContacts.MultiAdapterContactsViewHolder> {

    private Context context;
    private List<User> users;
    private List<User> selectedUser = new ArrayList<>();

    public MultiAdapterContacts(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    public void setUsers(List<User> users) {
        this.users = new ArrayList<>();
        this.users = users;
        notifyDataSetChanged();
    }

    public List<User> getSelectedUsers() {
        List<User> selectedUsers = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).isChecked()) {
                selectedUsers.add(users.get(i));
            }
        }

        return selectedUsers;
    }

    @NonNull
    @Override
    public MultiAdapterContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_card_view,
                parent, false);

        return new MultiAdapterContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiAdapterContactsViewHolder holder, int position) {
        holder.bindContact(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MultiAdapterContactsViewHolder extends RecyclerView.ViewHolder {
        private ImageView contactImage;
        private ImageView selectedIcon;
        private TextView contactName;

        public MultiAdapterContactsViewHolder(@NonNull View itemView) {
            super(itemView);

            contactImage = itemView.findViewById(R.id.userImage);
            contactName = itemView.findViewById(R.id.userName);
            selectedIcon = itemView.findViewById(R.id.selectedIcon);
        }

        void bindContact(User user) {
            //TODO ставить фото
            contactName.setText(user.Name);

            itemView.setOnClickListener(view -> {
                user.setChecked(!user.isChecked());
                selectedIcon.setVisibility(user.isChecked() ? View.VISIBLE : View.GONE);
            });
        }

        public List<User> getAllUsers() {return users;}
    }
}
