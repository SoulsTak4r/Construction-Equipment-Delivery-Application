package com.example.constructionequipmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;

public class showinfo extends AppCompatActivity {
    TextView name, id;
    Button route;
    ArrayList<String> addresses;
    ArrayList<Integer> indexes;
   // ListView listView;
    FirebaseDatabase firebaseDatabase;


    DatabaseReference databaseReference;


    private  String userID;
    private FirebaseAuth mAuth;
    int count = 1;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinfo);

        ConstraintLayout constraintLayout = findViewById(R.id.info);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

       // listView = findViewById(R.id.listview2);

        route = findViewById(R.id.button3);
        listView = findViewById(R.id.listview2);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users").child("Drivers").child(userID).child("Tasks");




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



    public void showData(DataSnapshot dataSnapshot)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        addresses = new ArrayList<>();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Tasks tasks = new Tasks();
            tasks.setAddress(ds.getValue(Tasks.class).getAddress());
            tasks.setSize(ds.getValue(Tasks.class).getSize());
            tasks.setAction(ds.getValue(Tasks.class).getAction());
            tasks.setDate(ds.getValue(Tasks.class).getDate());
            tasks.setTask_ID(ds.getValue(Tasks.class).getTask_ID());
            tasks.setTime(ds.getValue(Tasks.class).getTime());
            tasks.setEquipment(ds.getValue(Tasks.class).getEquipment());
            tasks.setTruckSize(ds.getValue(Tasks.class).getTruckSize());
            tasks.setAddedBy(ds.getValue(Tasks.class).getAddedBy());
            addresses.add(tasks.getAddress());



            arrayList.add( "Task: " + count++ + "\n" + "Task ID: " + tasks.getTask_ID() + "\n" + "Task Added By: " + tasks.getAddedBy() + "\n" + "Location: " + tasks.getAddress() + "\n" +
                    "Action: " + tasks.getAction() + "\n" + "Size: " + tasks.getSize() + "\n" + "Date: " + tasks.getDate() + "\n" +
                    "Time: " + tasks.getTime() + "\n" + "Equipment: " + tasks.getEquipment() + "\n" + "Truck Size: " + tasks.getTruckSize() + "\n");


        }
        int count = 0;
        indexes = new ArrayList<>();

        for(int i = 0; i < addresses.size(); i++)
        {
            indexes.add(i);
        }

/*
           // Drivers drivers = new Drivers();
            //drivers.setName(ds.child(userID).getValue(Drivers.class).getName());
            String name = dataSnapshot.child(userID).child("name").getValue(String.class);
            String id = dataSnapshot.child(userID).child("id").getValue(String.class);
            //drivers.setID(ds.child(userID).getValue(Drivers.class).getId());

            arrayList.add(name);
            arrayList.add(id);


 */
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        Toast.makeText(showinfo.this, indexes.size() + "", Toast.LENGTH_SHORT).show();


        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(addresses.isEmpty())
                {
                    Toast.makeText(showinfo.this, "No Task Found", Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uriFromIndexes(indexes));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
            }
        });

    }

    Uri uriFromIndexes(ArrayList<Integer> indexes)
    {
        for (int i : indexes) {
            if (i > addresses.size()) {
                return Uri.parse("https://www.google.com/maps/dir/?api=1&destination=BADINDEX");
            }
        }

        String uri = "https://www.google.com/maps/dir/?api=1&";
        if (indexes.size() > 1) { // do we need waypoints?
            uri += "&waypoints=";
            for (int i=0; i<indexes.size()-2; i++)
                uri += addresses.get(i) + "%7C";
            uri += addresses.get(indexes.get(indexes.size() - 2)); // last waypoint with no "%7C"
        }
        uri += "&destination=";
        uri += addresses.get(indexes.get(indexes.size() - 1));
        uri += "&travelmode=driving&dir_action=navigate";

        return Uri.parse(uri);




    }



    /* @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Confirm SignOut..!!");
        alertDialog.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        alertDialog.setMessage("Are you sure You want to SignOut?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(showinfo.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(showinfo.this, "Signed out Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(showinfo.this, "", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }
*/

}
