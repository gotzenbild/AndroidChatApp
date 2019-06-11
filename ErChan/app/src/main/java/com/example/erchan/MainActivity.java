package com.example.erchan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText Login;
    private EditText Password;
    Button ButtonForReg;
    Button ButtonForLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        System.out.print("ok");

        ButtonForReg = findViewById(R.id.ButtonForReg);
        ButtonForLog = findViewById(R.id.ButtonForLog);

        Login = findViewById(R.id.TextLogin);
        Password = findViewById(R.id.TextPassword);

        ButtonForReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("reg");
                registration(Login.getText().toString(), Password.getText().toString());
            }
        });
        ButtonForLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("log");
                singin(Login.getText().toString(), Password.getText().toString());
            }
        });
    }

    public void singin(String log, String pass){
        mAuth.signInWithEmailAndPassword(log,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, SelectChatActiviti.class);
                    startActivity(intent);
                }
                else{
                    System.out.print(task.getException().toString());
                    Toast.makeText(MainActivity.this, task.getException().toString()+ " ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void registration(String log, String pass){
        mAuth.createUserWithEmailAndPassword(log,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, SelectChatActiviti.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, task.getException().toString()+ " ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}