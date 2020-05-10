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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ManagerRegistration extends AppCompatActivity {
    private final String MANAGER_KEY = "2";


    Button register;
    private EditText mEmail, mPassword, fName, lName;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_registration);

        register = findViewById(R.id.buttonMM);
        fName = findViewById(R.id.editTextM3);
        lName = findViewById(R.id.editTextM4);
        mEmail = findViewById(R.id.editTextM5);
        mPassword = findViewById(R.id.editTextM6);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String firstName = fName.getText().toString();
                final String lastName = lName.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(ManagerRegistration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(ManagerRegistration.this, "sign up error", Toast.LENGTH_SHORT).show();
                        } else {
                            String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            Managers managers = new Managers(firstName + " " + lastName, user_id, MANAGER_KEY);
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Managers");
                            current_user_db.child(user_id).setValue(managers);
                            Toast.makeText(ManagerRegistration.this, "Registered SUCCESSFULLY!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ManagerRegistration.this, ManagerLoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });




                /*
                Toast.makeText(ManagerRegistration.this, "Registered SUCCESSFULLY!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManagerRegistration.this, ManagerLoginActivity.class);
                startActivity(intent);
                finish();

                 */
            }
        });
    }
}
