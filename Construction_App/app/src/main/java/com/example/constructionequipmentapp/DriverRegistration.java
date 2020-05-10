package com.example.constructionequipmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class DriverRegistration extends AppCompatActivity {
    private final String DRIVER_KEY = "1";

    Button register;
    private EditText mEmail, mPassword, fName, lName;

    private Button mLogin, mRegistration;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);

        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.button);
        mEmail = findViewById(R.id.editText5);
        mPassword = findViewById(R.id.editText6);
        fName = findViewById(R.id.editText3);
        lName = findViewById(R.id.editText4);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String firstName = fName.getText().toString();
                final String lastName = lName.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(DriverRegistration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(DriverRegistration.this, "sign up error", Toast.LENGTH_SHORT).show();
                        } else {
                            String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            Drivers drivers = new Drivers(firstName + " " + lastName, user_id, DRIVER_KEY);
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");
                            current_user_db.child(user_id).setValue(drivers);
                            Toast.makeText(DriverRegistration.this, "Registered SUCCESSFULLY!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DriverRegistration.this, DriverLoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });


            }
        });


    }


}