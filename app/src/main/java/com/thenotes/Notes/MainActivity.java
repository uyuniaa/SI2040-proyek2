package com.thenotes.Notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText emailLogin,passwordLogin;
    Button Login,Register;
    ProgressBar progressBarLogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailLogin = findViewById(R.id.edtEmailLogin);
        passwordLogin = findViewById(R.id.edtPasswordLogin);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        firebaseAuth = firebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        Register = findViewById(R.id.btnRegister1);
        Register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,registeractivity.class);
                startActivity(intent);
                firebaseAuth = firebaseAuth.getInstance();

                Login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBarLogin.setVisibility(View.VISIBLE);
                        firebaseAuth.signInWithEmailAndPassword(emailLogin.getText().toString(),passwordLogin.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBarLogin.setVisibility(View.GONE);
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(MainActivity.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });

            }
        });
        Login = findViewById(R.id.btnLogin);





    }
}
