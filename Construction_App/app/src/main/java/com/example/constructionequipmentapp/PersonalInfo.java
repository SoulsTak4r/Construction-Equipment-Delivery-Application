package com.example.constructionequipmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PersonalInfo extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    ListView listView;

    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference;


    private  String userID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);


        listView = findViewById(R.id.personal);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users").child("Drivers");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void showData(DataSnapshot snapshot)
    {
        ArrayList<String> arrayList = new ArrayList<>();

        String name = snapshot.child(userID).child("name").getValue(String.class);
        String id = snapshot.child(userID).child("id").getValue(String.class);

        arrayList.add("Name: " + name + "\n\n" + "Account ID: " + id + "\n");
        arrayList.add("Change Password");
        arrayList.add("Contact Support");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
    }
}
