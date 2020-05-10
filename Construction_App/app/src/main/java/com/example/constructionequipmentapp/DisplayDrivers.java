package com.example.constructionequipmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayDrivers extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase firebaseDatabase;
    FirebaseDatabase firebaseDatabase1;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;

    ArrayList<String> arrayList;
    private FirebaseAuth mAuth;
    String uniqueKey;
    private  String userID;
    String name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_acitvity);


        listView = findViewById(R.id.listview);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase1 = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child("Drivers");
        databaseReference1 = firebaseDatabase1.getReference().child("Users").child("Managers");

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

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showManager(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void showManager(DataSnapshot snapshot)
    {
        name = snapshot.child(userID).child("name").getValue(String.class);
        Toast.makeText(DisplayDrivers.this,name,Toast.LENGTH_SHORT).show();
    }


    public void showData(DataSnapshot dataSnapshot)
    {
        arrayList = new ArrayList<>();
        final ArrayList<String> arrayList2 = new ArrayList<>();

        for(DataSnapshot ds : dataSnapshot.getChildren())
        {
            Drivers drivers = new Drivers();
            drivers.setName(ds.getValue(Drivers.class).getName());
            drivers.setID(ds.getValue(Drivers.class).getId());


            arrayList.add( "Driver Name: " + drivers.getName() + "\n" + "Driver ID: " +drivers.getId());
            arrayList2.add(drivers.getId());

        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DatabaseReference current1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");
                uniqueKey = current1.push().getKey();
                Tasks tasks = new Tasks(" 1 mackenzie ct. Bloomington, IL 61704", "Small", "Deliver", "04/16/2020", arrayList2.get(0) , uniqueKey, "08:00am - 04:00pm", "Lube Truck", "Medium", name );

                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");
                current_user_db.child(arrayList2.get(position)).child("Tasks").child(uniqueKey).setValue(tasks);
                // Toast.makeText(DisplayDrivers.this, "Task Added", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
