package com.example.constructionequipmentapp;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class P_menu extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ListView listview;
    String buttonNames[]={"Employee and Orders", "See or Set vehicles","Set or change Addresses", "Logout"};
    int buttonImages[]={R.drawable.employee,R.drawable.truck,R.drawable.map,R.drawable.exit};
    Class<?> buttonActivities[] = {Employee_Option.class, Vehicle_option.class, Address_option.class, Orders_Option.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_menu);

        LinearLayout constraintLayout = findViewById(R.id.pmenu);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();



        listview=findViewById(R.id.Asad);
        mAuth=FirebaseAuth.getInstance();
        listview.setAdapter(new Myadapter(this, buttonNames, buttonImages));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(P_menu.this, buttonActivities[position]));
            }
        });
    }
    @Override
    public void onBackPressed() {
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
                    Intent intent2 = new Intent(P_menu.this, MainActivity.class);
                    startActivity(intent2);
                    Toast.makeText(P_menu.this, "Signed out Successfully", Toast.LENGTH_SHORT).show();
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
    class Myadapter extends ArrayAdapter<String> {
        String buttonNames[];
        int buttonImages[];

        Myadapter(Context c, String buttonNames[], int imgs[]){
            super(c,R.layout.row2,R.id.buttonName,buttonNames);
            this.buttonNames = buttonNames;
            this.buttonImages=imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutinflator = (LayoutInflater)  getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row2=layoutinflator.inflate(R.layout.row2, parent,false);

            ((TextView) row2.findViewById(R.id.buttonName)).setText(buttonNames[position]);
            ((ImageView) row2.findViewById(R.id.image)).setImageResource(buttonImages[position]);

            return row2;
        }
    }
}
