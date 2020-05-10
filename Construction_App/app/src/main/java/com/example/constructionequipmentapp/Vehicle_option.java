package com.example.constructionequipmentapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Vehicle_option extends AppCompatActivity {
    List<Vehicle> tasks;
    DatabaseReference databaseTask;
    ListView listViewTask;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_option);
        tasks = new ArrayList<>();
        databaseTask= FirebaseDatabase.getInstance().getReference("Trucks");
        listViewTask = (ListView) findViewById(R.id.AsadV);
        btn=(Button) findViewById(R.id.buttonaddV);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Vehicle_option.this,AddingTruck.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                for(DataSnapshot artistSnapshot: dataSnapshot.getChildren()){

                    Vehicle tsk=artistSnapshot.getValue(Vehicle.class);
                    tasks.add(tsk);
                }
                Vehicle_option.MyAdapter adapter=new Vehicle_option.MyAdapter(Vehicle_option.this,tasks);
                adapter.notifyDataSetChanged();
                listViewTask.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    class MyAdapter extends ArrayAdapter<Vehicle> {
        private Activity context;
        private List<Vehicle> task;

        MyAdapter(Activity Context, List<Vehicle> task){
            super(Context,R.layout.row6,task);
            this.context=Context;
            this.task=task;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View listViewItem=inflater.inflate(R.layout.row6,parent,false);
            TextView textviewID= (TextView) listViewItem.findViewById(R.id.tsV3);
            TextView textviewEquipment= (TextView) listViewItem.findViewById(R.id.tsV1);
            TextView textviewSize= (TextView) listViewItem.findViewById(R.id.tsV2);
            Vehicle tsk= task.get(position);
            textviewID.setText("ID: "+ tsk.getId());
            textviewEquipment.setText(tsk.getName());
            textviewSize.setText("SIZE: "+tsk.getSize());
            return listViewItem;
        }
    }

}
