package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.chatapp.fragments.ChatsViewFragment;
import com.example.chatapp.fragments.ContactsListFragment;
import com.example.chatapp.fragments.ProfileFragment;
import com.example.chatapp.R;
import com.example.chatapp.adapters.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button _nextButt;
    private Button logOut;
    private Toolbar toolbarMain;
    private TabLayout tabLayout;
    private ViewPager2 vPager;
    private TextView toolbarText;

    private static DatabaseReference chatRef;
    private static FirebaseAuth userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatRef = FirebaseDatabase.getInstance().getReference("Chats");
        userRef = FirebaseAuth.getInstance();

        //_nextButt = findViewById(R.id.mainChatButt);
        logOut = findViewById(R.id.action_log_out);
        toolbarMain = findViewById(R.id.toolbarMain);

        tabLayout = findViewById(R.id.tabs);
        vPager = findViewById(R.id.pager);

        toolbarText = findViewById(R.id.toolbarTextview);

        //tabLayout.setupWithViewPager(vPager);
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(), getLifecycle());
        adapter.addFragment(new ChatsViewFragment(), "chats");
        adapter.addFragment(new ContactsListFragment(), "contacts");
        adapter.addFragment(new ProfileFragment(), "profile");
        vPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        setSupportActionBar(toolbarMain);
        toolbarText.setText(userRef.getCurrentUser().getEmail());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_log_out) {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public static DatabaseReference getFirebaseReference() {
        return chatRef;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return userRef;
    }

}