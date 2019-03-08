package com.example.android.documentlist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainPageActivity extends AppCompatActivity {

    FirebaseAuth auth;

    private FirebaseAuth.AuthStateListener authStateListener;

    private ActionBar actionBar;

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        // Menu item "Logout"
        switch (item.getItemId()){

            case R.id.logout:

            auth.getInstance().signOut();

            Toast.makeText(getApplicationContext(), "Logged Out!", Toast.LENGTH_LONG).show();

            finish();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6099f5")));

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        setTitle(username);

    }



}
