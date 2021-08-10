package com.example.myvideoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    EditText emailId, passwordId, nameId;
    Button signUpBtn, backtoLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        nameId = findViewById(R.id.editTextTextName);
        emailId = findViewById(R.id.editTextTextEmailAddress);
        passwordId = findViewById(R.id.editTextTextPassword);
        signUpBtn = findViewById(R.id.signUpBtn);
        backtoLoginBtn = findViewById(R.id.backtoLoginBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String email, pass, name;
                email = emailId.getText().toString();
                pass = passwordId.getText().toString();
                name = nameId.getText().toString();

                User user = new User(name, email, pass);

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            firebaseFirestore.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>(){

                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                }
                            });
                            Toast.makeText(SignUpActivity.this, "Account is Created", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}