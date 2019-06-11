package com.example.erchan;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatActiviti extends AppCompatActivity {


    FirebaseUser user;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button ButtonForSend;
    EditText TextForSend;
    RecyclerView MessagesView;
    ArrayList <String> messages =  new ArrayList<>();
    static String chat;
        static String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activiti);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(chat.toString());
        ButtonForSend = findViewById(R.id.ButtonForSend);
        TextForSend = findViewById(R.id.TextForSend);
        MessagesView = findViewById(R.id.MessagesView);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        MessagesView.setLayoutManager(new LinearLayoutManager(this));

        final DataAdapter dataAdapter = new DataAdapter( this, messages);


        MessagesView.setAdapter(dataAdapter);

        ButtonForSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").getTimeZone();
                Date d1 = new Date();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm a");
                String formattedDate = df.format(d1);

                String c =  UserName  + "          " + formattedDate +"\n";
                String msg = c + TextForSend.getText().toString();
                if(msg.equals(c) && msg.equals("") ){
                    Toast.makeText(getApplicationContext(),"Введите сообщение",Toast.LENGTH_SHORT).show();
                    return;
                }
                myRef.push().setValue(msg);
                TextForSend.setText("");
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String msg = dataSnapshot.getValue(String.class);
                messages.add(msg);
                dataAdapter.notifyDataSetChanged();
                MessagesView.smoothScrollToPosition(messages.size());
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
}