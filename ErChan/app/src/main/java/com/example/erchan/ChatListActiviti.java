package com.example.erchan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ChatListActiviti extends AppCompatActivity {

    FirebaseUser user;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button AddChat;

    ArrayList<String> ListOfChats =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activiti);

        database = FirebaseDatabase.getInstance();
        AddChat = findViewById(R.id.AddChat);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        AddChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatListActiviti.this, SelectChatActiviti.class);
                startActivity(intent);
            }
        });


    }
}