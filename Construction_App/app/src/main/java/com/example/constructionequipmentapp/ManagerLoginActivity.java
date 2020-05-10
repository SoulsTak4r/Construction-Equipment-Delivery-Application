package com.example.constructionequipmentapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ManagerLoginActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private Button mLogin;
    TextView textView;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        ConstraintLayout constraintLayout = findViewById(R.id.mang);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        textView = findViewById(R.id.register1);
        mLogin = findViewById(R.id.buttonM);
        mEmail = findViewById(R.id.editTextM);
        mPassword = findViewById(R.id.editTextM1);
        mAuth = FirebaseAuth.getInstance();


        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    //Intent intent = new Intent(ManagerLoginActivity.this, P_menu.class);
                    //startActivity(intent);
                    Toast.makeText(ManagerLoginActivity.this,"Signed in",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(ManagerLoginActivity.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                }
            }
        };


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(ManagerLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            Intent intent = new Intent(ManagerLoginActivity.this,  P_menu.class);
                            startActivity(intent);
                        }
                        else
                        {
                            onAuthSuccess(task.getResult().getUser());

                            //Intent intent = new Intent(ManagerLoginActivity.this, DisplayDrivers.class);
                            //startActivity(intent);
                        }
                    }
                });
            }
        });



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerLoginActivity.this, ManagerRegistration.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onAuthSuccess(FirebaseUser user)
    {


        if(user != null)
        {
            reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Managers").child(user.getUid()).child("key");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {

                        String val = dataSnapshot.getValue(String.class);
                        int k = Integer.parseInt(val);

                        if (k == 2) {
                            // Toast.makeText(ManagerLoginActivity.this, "You are Manager", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ManagerLoginActivity.this, P_menu.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(ManagerLoginActivity.this, "You are not Manager", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else
                    {
                        Toast.makeText(ManagerLoginActivity.this, "You are not Manager", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }



}