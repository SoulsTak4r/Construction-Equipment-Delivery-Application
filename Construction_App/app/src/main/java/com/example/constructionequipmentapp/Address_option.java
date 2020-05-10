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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class Address_option extends AppCompatActivity {
    EditText editTextName;
    Button buttonAdd;
    DatabaseReference databaseEmployee;
    ListView listViewEmployee;
    List<Employee> employeeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_option);
        employeeList = new ArrayList<>();
        databaseEmployee = FirebaseDatabase.getInstance().getReference("Users").child("Drivers");
        editTextName = (EditText) findViewById(R.id.editTextName);
        listViewEmployee = (ListView) findViewById(R.id.Asads);

    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseEmployee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employeeList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    Employee employee = artistSnapshot.getValue(Employee.class);
                    Log.v("Sadaf", dataSnapshot.toString());
                    employeeList.add(employee);

                }
                Address_option.MyAdapter adapter = new Address_option.MyAdapter(Address_option.this, employeeList);
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
                Intent intent = new Intent(Address_option.this,Routes_act.class);
                intent.putExtra("id",employeeList.get(position).getId());
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"clicked on: "+employeeList.get(position).name,Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyAdapter extends ArrayAdapter<Employee> {
        private Activity context;
        private List<Employee> employeeList;

        MyAdapter(Activity Context, List<Employee> employeeList){
            super(Context,R.layout.row3,employeeList);
            this.context=Context;
            this.employeeList=employeeList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View listViewItem=inflater.inflate(R.layout.row3,parent,false);
            TextView textviewName= (TextView) listViewItem.findViewById(R.id.textviewa1);
            TextView textviewName2= (TextView) listViewItem.findViewById(R.id.textviewa2);

            Employee employee=employeeList.get(position);
            textviewName.setText("NAME : "+employee.getName());
            textviewName2.setText("ID : "+employee.getId());

            return listViewItem;
        }
    }


}
