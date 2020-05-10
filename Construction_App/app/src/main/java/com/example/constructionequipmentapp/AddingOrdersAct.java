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

public class AddingOrdersAct extends AppCompatActivity {
    Button btn;
    EditText date;
    EditText time;
    EditText size;
    EditText truckSize;
    EditText action;
    EditText addedBy;
    EditText equipment;
    EditText address;


    DatabaseReference databaseTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_orders);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String val= bundle.getString("id");
        databaseTask= FirebaseDatabase.getInstance().getReference("Users").child("Drivers").child(val).child("Tasks");
        btn =(Button) findViewById(R.id.button6);
        time= (EditText) findViewById(R.id.o_time) ;
        date= (EditText) findViewById(R.id.o_date) ;
        address= (EditText) findViewById(R.id.o_address) ;
        size= (EditText) findViewById(R.id.o_size) ;
        truckSize=(EditText) findViewById(R.id.o_truck) ;
        addedBy=(EditText) findViewById(R.id.o_editby) ;
        action=(EditText) findViewById(R.id.o_action) ;
        equipment=(EditText) findViewById(R.id.o_equipment) ;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();

            }
        });
    }
    private void addTask() {
       // String namee=name.getText().toString().trim();
        String datee=date.getText().toString().trim();
        //String locationn=location.getText().toString().trim();
        String sizee=size.getText().toString().trim();
        String timee=time.getText().toString().trim();
        String addresss=address.getText().toString().trim();
        String truck=truckSize.getText().toString().trim();
        String added=addedBy.getText().toString().trim();
        String act=action.getText().toString().trim();
        String equipt=equipment.getText().toString().trim();
        if(sizee == "S" || sizee=="s"){sizee="Small";}
        else if(sizee == "m" || sizee=="M"){sizee="Medium";}
        else if(sizee == "l" || sizee=="L"){sizee="Large";}
        if(truck == "S" || truck=="s"){truck="Small";}
        else if(truck == "m" || truck=="M"){truck="Medium";}
        else if(truck == "l" || truck=="L"){truck="Large";}
       // String sizee=size.getText().toString().trim();
        if(!TextUtils.isEmpty(datee) && !TextUtils.isEmpty(sizee) && !TextUtils.isEmpty(timee) && !TextUtils.isEmpty(addresss)&& !TextUtils.isEmpty(truck)&& !TextUtils.isEmpty(added)&& !TextUtils.isEmpty(act)&& !TextUtils.isEmpty(equipt)){
            String id=databaseTask.push().getKey();
            Task task= new Task(act, added, addresss, datee, equipt, sizee, id, timee, truck);
            databaseTask.child(id).setValue(task);
            Toast.makeText(this,"Task pushed",Toast.LENGTH_LONG).show();
            Intent intent= new Intent(AddingOrdersAct.this,showTasks.class);
            startActivity(intent);

        }
        else{
            Toast.makeText(this,"Task not pushed.Try again",Toast.LENGTH_LONG).show();
        }
    }

}
