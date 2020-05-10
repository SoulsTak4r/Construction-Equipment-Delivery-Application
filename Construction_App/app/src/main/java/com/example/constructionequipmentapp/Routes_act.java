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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Routes_act extends AppCompatActivity {
    List<Task> tasks;
    DatabaseReference databaseTask;
    ListView listViewTask;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_act);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String val= bundle.getString("id");
        Toast.makeText(getApplicationContext(), "this is the end: "+val, Toast.LENGTH_SHORT).show();
        tasks = new ArrayList<>();
        databaseTask= FirebaseDatabase.getInstance().getReference("Users").child("Drivers").child(val).child("Tasks");
        listViewTask = (ListView) findViewById(R.id.AsadzR);
        btn =(Button) findViewById(R.id.buttonaddR);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Routes_act.this,Address_option.class);
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

                    Task tsk=artistSnapshot.getValue(Task.class);
                    tasks.add(tsk);
                }
                Routes_act.MyAdapter adapter=new Routes_act.MyAdapter(Routes_act.this,tasks);
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
            super(Context,R.layout.row5,task);
            this.context=Context;
            this.task=task;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View listViewItem=inflater.inflate(R.layout.row5,parent,false);
            TextView textviewAddress= (TextView) listViewItem.findViewById(R.id.tsa);
            TextView textviewDate= (TextView) listViewItem.findViewById(R.id.tsb);
            TextView textviewTime= (TextView) listViewItem.findViewById(R.id.tsc);

            Task tsk= task.get(position);
            textviewAddress.setText(tsk.getAddress());
            textviewDate.setText(tsk.getDate());
            textviewTime.setText(tsk.getTime());

            return listViewItem;
        }
    }

}
