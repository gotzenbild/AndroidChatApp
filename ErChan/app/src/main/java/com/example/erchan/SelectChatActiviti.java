package com.example.erchan;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class SelectChatActiviti extends AppCompatActivity{

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("СhatList");

    ArrayList<String> chats = new ArrayList<>();

    Button ButtonForRegChat;
    Button ButtonForLogToChat;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    EditText LogChatLog;
    EditText LogChatPass;
    EditText UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_chat_activiti);

        LogChatLog = findViewById(R.id.LogChatLog);
        LogChatPass = findViewById(R.id.LogChatPass);
        UserName = findViewById(R.id.UserName);

        ButtonForRegChat = findViewById(R.id.ButtonForRegChat);
        ButtonForLogToChat = findViewById(R.id.ButtonForLogToChat);

        mAuth = FirebaseAuth.getInstance();
        user =  mAuth.getCurrentUser();

        ButtonForRegChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cheсkLogs(LogChatLog.getText().toString())){
                    String msg = LogChatLog.getText().toString() + ";" + LogChatPass.getText().toString();
                    System.out.print(msg);
                    myRef.push().setValue(msg);
                    Intent intent = new Intent(SelectChatActiviti.this, ChatActiviti.class);
                    ChatActiviti.chat = msg;
                    ChatActiviti.UserName = UserName.getText().toString();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SelectChatActiviti.this, "Логин чата занят", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ButtonForLogToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = LogChatLog.getText().toString() + ";" + LogChatPass.getText().toString();
                System.out.print(msg);
                if(chatsCheck(msg)){
                    Intent intent = new Intent(SelectChatActiviti.this, ChatActiviti.class);
                    ChatActiviti.chat = msg;
                    ChatActiviti.UserName = UserName.getText().toString();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SelectChatActiviti.this, "Что-то не так", Toast.LENGTH_SHORT).show();
                }
            }
        });


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String msg = dataSnapshot.getValue(String.class);
                chats.add(msg);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private boolean cheсkLogs(String chatLog){
        for (String chatLogB : chats){
            String log = chatLogB.split(";")[0];
            if(log.equals(chatLog)){
                return false;
            }
        }
        return true;
    }

    private boolean chatsCheck(String msg){
        for (String chat : chats){
            if(chat.equals(msg)){
                return true;
            }
        }
        return false;
    }

}

