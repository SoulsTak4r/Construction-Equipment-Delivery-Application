package com.example.constructionequipmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

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

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DriverLoginActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    EditText text;
    EditText text1;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        ConstraintLayout constraintLayout = findViewById(R.id.login);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        textView = findViewById(R.id.register);
        button = findViewById(R.id.button2);
        text = findViewById(R.id.editText);
        text1 = findViewById(R.id.editText2);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast.makeText(DriverLoginActivity.this,"Signed in",Toast.LENGTH_SHORT).show();
                   // Intent intent = new Intent(DriverLoginActivity.this, MapActivity.class);
                   // startActivity(intent);

                }
                else
                {
                    Toast.makeText(DriverLoginActivity.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                }
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = text.getText().toString();
                final String password = text1.getText().toString();
                //mhal@gmail.com//
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(DriverLoginActivity.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            onAuthSuccess(task.getResult().getUser());

                           // Intent intent = new Intent(DriverLoginActivity.this, D_menu.class);
                           // startActivity(intent);
                        }
                    }
                });
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverLoginActivity.this, DriverRegistration.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void onAuthSuccess(FirebaseUser user)
    {
        if(user != null)
        {
            reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user.getUid()).child("key");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {

                        String val = dataSnapshot.getValue(String.class);
                        int k = Integer.parseInt(val);

                        if (k == 1) {
                            //Toast.makeText(DriverLoginActivity.this, "You are Driver", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DriverLoginActivity.this, DriverHome.class);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(DriverLoginActivity.this, "You are not Driver", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(DriverLoginActivity.this, "You are not Driver", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }



/*
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
    */

/*
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

*/
}