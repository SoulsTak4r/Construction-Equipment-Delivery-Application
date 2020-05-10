package com.example.constructionequipmentapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class D_menu extends AppCompatActivity {
    ListView listview;
    String mTitle[]={"Check Orders","Vehicle","Map","Quit"};
    String Describtion[]={"see orders", "see vehicle assigned","see map", "exit app"};
    int img[]={R.drawable.order,R.drawable.truck,R.drawable.map,R.drawable.exit};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_menu);
        listview=findViewById(R.id.Asad);
        Myadapter adapter=new Myadapter(this,mTitle,Describtion,img);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Toast.makeText(D_menu.this, "Order Option", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(D_menu.this, showinfo.class);
                    startActivity(intent);
                }
                if (position == 1){
                    Toast.makeText(D_menu.this, "Vehicle Option", Toast.LENGTH_SHORT).show();
                }
                if (position == 2){
                    //Intent intent = new Intent(D_menu.this,Map_Info.class);
                    //startActivity(intent);

                    // indexes of the addresses to navigate to
                    ArrayList<Integer> indexes = new ArrayList<>();
                    indexes.add(0);
                    indexes.add(1);
                    indexes.add(2);
                    // start google maps
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uriFromIndexes(indexes));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);

                    Toast.makeText(D_menu.this, "Map Option", Toast.LENGTH_SHORT).show();
                }
                if (position == 3){
                    Toast.makeText(D_menu.this, "Exit option", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    class Myadapter extends ArrayAdapter<String> {
        Context contxt;
        String rTitle[];
        String rDescription[];
        int rimages[];

        Myadapter(Context c, String title[],String description[], int imgs[]){
            super(c,R.layout.row,R.id.buttonName,title);
            this.contxt=c;
            this.rDescription=description;
            this.rTitle=title;
            this.rimages=imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutinflator = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutinflator.inflate(R.layout.row,parent,false);
            ImageView images=row.findViewById(R.id.image);
            TextView myTitle=row.findViewById(R.id.textviewz1);
            TextView myDescription=row.findViewById(R.id.textviewz2);
            images.setImageResource(rimages[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return row;
        }
    }

    // returns URI for google maps to start navigation to addresses
    Uri uriFromIndexes (ArrayList<Integer> indexes) {
        // this array will be in the db
        String[] addresses = new String[]{
                "1200 W. Harrison St. Chicago, IL 60607",
                "6400 Oakton St. Morton Grove",
                "7308 W Lawrence Ave. Harwood Heights, IL 60706"
        };

        // error checking for index out of bounds
        for (int i : indexes) {
            if (i > addresses.length) {
                return Uri.parse("https://www.google.com/maps/dir/?api=1&destination=BADINDEX");
            }
        }

        // build URI
        String uri = "https://www.google.com/maps/dir/?api=1&";
        if (indexes.size()>1) { // do we need waypoints?
            uri += "&waypoints=";
            for (int i=0; i<indexes.size()-2; i++)
                uri += addresses[i] + "%7C";
            uri += addresses[indexes.get(indexes.size()-2)]; // last waypoint with no "%7C"
        }
        uri += "&destination=";
        uri += addresses[indexes.get(indexes.size()-1)];
        uri += "&travelmode=driving&dir_action=navigate";

        return Uri.parse(uri);
    }
}

