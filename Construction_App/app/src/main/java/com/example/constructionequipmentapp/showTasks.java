package com.example.constructionequipmentapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showTasks extends AppCompatActivity {
    List<Task> tasks;
    DatabaseReference databaseTask;
    ListView listViewTask;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tasks);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String val= bundle.getString("id");
        tasks = new ArrayList<>();
        databaseTask= FirebaseDatabase.getInstance().getReference("Users").child("Drivers").child(val).child("Tasks");
        listViewTask = (ListView) findViewById(R.id.Asadz);
        btn=(Button) findViewById(R.id.buttonaddz);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents=new Intent(showTasks.this,AddingOrdersAct.class);
                intents.putExtra("id",val);
                startActivity(intents);

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

                    Task tsk=artistSnapshot.getValue(Task.class);
                    tasks.add(tsk);
                }
                showTasks.MyAdapter adapter=new showTasks.MyAdapter(showTasks.this,tasks);
                adapter.notifyDataSetChanged();
                listViewTask.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class MyAdapter extends ArrayAdapter<Task> {
        private Activity context;
        private List<Task> task;

        MyAdapter(Activity Context, List<Task> task){
            super(Context,R.layout.row4,task);
            this.context=Context;
            this.task=task;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View listViewItem=inflater.inflate(R.layout.row4,parent,false);
            TextView textviewAction= (TextView) listViewItem.findViewById(R.id.ts2);
            TextView textviewAddress= (TextView) listViewItem.findViewById(R.id.ts3);
            TextView textviewID= (TextView) listViewItem.findViewById(R.id.ts1);
            TextView textviewEquipment= (TextView) listViewItem.findViewById(R.id.ts4);
            TextView textviewSize= (TextView) listViewItem.findViewById(R.id.ts5);
            TextView textviewDate= (TextView) listViewItem.findViewById(R.id.ts7);
            TextView textviewTime= (TextView) listViewItem.findViewById(R.id.ts8);
            TextView textviewAddedby= (TextView) listViewItem.findViewById(R.id.ts9);
            TextView textviewTrucksize= (TextView) listViewItem.findViewById(R.id.tes6);

            Task tsk= task.get(position);
            textviewAction.setText(tsk.getAction());
            textviewAddress.setText(tsk.getAddress());
            textviewID.setText(tsk.getTask_ID());
            textviewDate.setText(tsk.getDate());
            textviewTime.setText(tsk.getTime());
            textviewEquipment.setText(tsk.getEquipment());
            textviewAddedby.setText(tsk.getAddedBy());
            textviewTrucksize.setText(tsk.getTruckSize());
            textviewSize.setText(tsk.getSize());

            return listViewItem;
        }
    }
}
