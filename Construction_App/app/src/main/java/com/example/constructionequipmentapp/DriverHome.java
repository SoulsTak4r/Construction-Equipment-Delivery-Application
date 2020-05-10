package com.example.constructionequipmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DriverHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mDrawer;
    NavigationView navigationView;
    TextView textView;

    FirebaseDatabase firebaseDatabase;
    FirebaseDatabase firebaseDatabase1;
    FirebaseDatabase firebaseDatabase2;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;

    private  String userID;
    private FirebaseAuth mAuth;
    String email;
    TextView navemail;
    TextView info;
    TextView home;
    ListView listView;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        drawerLayout = findViewById(R.id.drawer);
        listView = findViewById(R.id.single);
        info = findViewById(R.id.textView8);
        home = findViewById(R.id.textView7);

        DrawerLayout constraintLayout = drawerLayout;
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        mDrawer = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mDrawer);
        mDrawer.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        navemail = (TextView) headerView.findViewById(R.id.email);
        textView = headerView.findViewById(R.id.textView6);



        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase1 = FirebaseDatabase.getInstance();
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users").child("Drivers");

        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        databaseReference1 = firebaseDatabase1.getReference().child("Users").child("Drivers").child(userID);
        databaseReference2 = firebaseDatabase2.getReference().child("Users").child("Drivers").child(userID).child("Tasks");

        email = mAuth.getCurrentUser().getEmail();

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

                if(dataSnapshot.hasChild("Tasks"))
                {
                    info.setText("Tasks Found for you");

                }
                else
                {
                    info.setText("There are no Tasks due today");
                    home.setText("No Upcoming Order");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showSingleData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        navemail.setText(email);

    }

    public void showSingleData(DataSnapshot snapshot)
    {
        ArrayList<String> arrayList = new ArrayList<>();

        int count = 1;
        for (DataSnapshot ds : snapshot.getChildren())
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




            arrayList.add( "Task: " + count++ + "\n" + "Task ID: " + tasks.getTask_ID() + "\n" + "Task Added By: " + tasks.getAddedBy() + "\n" + "Location: " + tasks.getAddress() + "\n" +
                    "Action: " + tasks.getAction() + "\n" + "Size: " + tasks.getSize() + "\n" + "Date: " + tasks.getDate() + "\n" +
                    "Time: " + tasks.getTime() + "\n" + "Equipment: " + tasks.getEquipment() + "\n" + "Truck Size: " + tasks.getTruckSize() + "\n");
            break;
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
    }

    public void showData(DataSnapshot snapshot)
    {
        String name = snapshot.child(userID).child("name").getValue(String.class);
        textView.setText(name);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mDrawer.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setTitle("Confirm SignOut..!!");
            alertDialog.setIcon(R.drawable.ic_exit_to_app_black_24dp);
            alertDialog.setMessage("Are you sure You want to SignOut?");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAuth.signOut();
                    finish();
                    Intent intent2 = new Intent(DriverHome.this, MainActivity.class);
                    startActivity(intent2);
                    Toast.makeText(DriverHome.this, "Signed out Successfully", Toast.LENGTH_SHORT).show();
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
            //super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id  = item.getItemId();


        switch (id)
        {
            case R.id.db:
                Toast.makeText(DriverHome.this, "Dashboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.orders:
                Toast.makeText(DriverHome.this, "order", Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(DriverHome.this, showinfo.class);
                startActivity(intent);
                break;
            case R.id.cal:
                Toast.makeText(DriverHome.this, "Dashboard", Toast.LENGTH_SHORT).show();
                Intent intent3  = new Intent(this, CalenderActivity.class);
                startActivity(intent3);
                break;
            case R.id.settings:
                Toast.makeText(DriverHome.this, "settings", Toast.LENGTH_SHORT).show();
                Intent intent1  = new Intent(DriverHome.this, PersonalInfo.class);
                startActivity(intent1);

                break;
            case R.id.logout:
                Toast.makeText(DriverHome.this, "logout", Toast.LENGTH_SHORT).show();

                mAuth.signOut();
                finish();
                Intent intent2 = new Intent(DriverHome.this, MainActivity.class);
                startActivity(intent2);

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
