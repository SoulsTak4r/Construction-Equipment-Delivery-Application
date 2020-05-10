package com.example.constructionequipmentapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Employee_Option extends AppCompatActivity {
    EditText editTextName;
    Button buttonAdd;
    DatabaseReference databaseEmployee;
    ListView listViewEmployee;
    List<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee__option);
        employeeList=new ArrayList<>();
        databaseEmployee= FirebaseDatabase.getInstance().getReference("Users").child("Drivers");
        editTextName=(EditText) findViewById(R.id.editTextName);
        listViewEmployee=(ListView) findViewById(R.id.listViewEmployee) ;


    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(Employee_Option.this, "Employee Option", Toast.LENGTH_SHORT).show();
        databaseEmployee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employeeList.clear();
                for(DataSnapshot artistSnapshot: dataSnapshot.getChildren()){

                    Employee employee=artistSnapshot.getValue(Employee.class);
                    Log.v("Sadaf",dataSnapshot.toString());
                    employeeList.add(employee);

                }
                MyAdapter adapter=new MyAdapter(Employee_Option.this,employeeList);
                adapter.notifyDataSetChanged();
                listViewEmployee.setAdapter(adapter);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();

        listViewEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(Employee_Option.this,showTasks.class);
                intent.putExtra("id",employeeList.get(position).getId());
                startActivity(intent);

            }
        });
    }



    class MyAdapter extends ArrayAdapter<Employee>{
        private Activity context;
        private List<Employee> employeeList;

        MyAdapter(Activity Context, List<Employee> employeeList){
            super(Context,R.layout.list_layout,employeeList);
            this.context=Context;
            this.employeeList=employeeList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View listViewItem=inflater.inflate(R.layout.list_layout,parent,false);
            TextView textviewName= (TextView) listViewItem.findViewById(R.id.textViewName);
           // TextView textviewid= (TextView) listViewItem.findViewById(R.id.textViewid);
            Employee employee=employeeList.get(position);
            //textviewid.setText(employee.getId());
            textviewName.setText(employee.getName());
            return listViewItem;
        }
    }


}
