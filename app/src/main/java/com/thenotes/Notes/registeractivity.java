package com.thenotes.Notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registeractivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText email;
    EditText password;
    Button register;
    FirebaseAuth firebaseAuth;
    TextView tvSambutan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        progressBar = findViewById(R.id.progressBarRegister);
        email = findViewById(R.id.edtEmailRegister);
        password = findViewById(R.id.edtPasswordRegister);
        tvSambutan = findViewById(R.id.tvSambutan);
        register = findViewById(R.id.btnRegisterUser);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()) {
                            Toast.makeText(registeractivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(registeractivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}