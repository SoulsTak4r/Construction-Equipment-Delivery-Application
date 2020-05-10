package com.example.constructionequipmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddingTruck extends AppCompatActivity {
    Button btn;
    EditText tName;
    EditText tSize;
    DatabaseReference databaseTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_truck);
        databaseTask= FirebaseDatabase.getInstance().getReference("Trucks");
        btn =(Button) findViewById(R.id.buttonV6);
        tName= (EditText) findViewById(R.id.V_name) ;
        tSize= (EditText) findViewById(R.id.V_size) ;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();

            }
        });


    }
    public void addTask(){
        String namee=tName.getText().toString().trim();
        String sizee=tSize.getText().toString().trim();
        if(!TextUtils.isEmpty(namee) && !TextUtils.isEmpty(sizee)){
            String id=databaseTask.push().getKey();
            Vehicle task= new Vehicle(id,namee,sizee);
            databaseTask.child(id).setValue(task);
            Toast.makeText(this,"Truck pushed",Toast.LENGTH_LONG).show();
            Intent intent= new Intent(AddingTruck.this,Vehicle_option.class);
            startActivity(intent);
        }

    }
}
