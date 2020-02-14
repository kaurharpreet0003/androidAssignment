package com.example.assignment12;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class favouritePlace extends AppCompatActivity {

    DatabaseHelper mDataBase;
    List<favouritePlace> placesList;
    //    ListView listView;
    SwipeMenuListView listView;
    placeAdapter placeAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_place_);

//        listView = findViewById(R.id.LVPlaces);
        listView = findViewById(R.id.listview);
        placesList = new ArrayList<>();

        // mDataBase = openOrCreateDatabase(MainActivity.DATABASE_NAME,MODE_PRIVATE,null);
        mDataBase = new DatabaseHelper(this);
        loadPlaces();


        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                editItem.setWidth((250));

                // set item title fontsize
                editItem.setTitleSize(18);
                // set item title font color
                editItem.setTitleColor(Color.WHITE);
                // add to menu

                editItem.setIcon(R.drawable.edit);
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth((250));
                // set a icon
                deleteItem.setIcon(R.drawable.delete);
                // add to menu
                menu.addMenuItem(deleteItem);

            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // edit
                        favouritePlace place1 = placesList.get(position);
                        int id1 = place1.getId();


                        Intent intent = new Intent(favouritePlace.this,MainActivity.class);
                        intent.putExtra("id",id1);

                        startActivity(intent);
                        Toast.makeText(favouritePlace.this, "cell clicked", Toast.LENGTH_SHORT).show();

                        break;

                    case 1:
                        // delete
                        Toast.makeText(favouritePlace.this, "delete clicked", Toast.LENGTH_SHORT).show();
                        favouritePlace place = placesList.get(position);
                        int id = place.getId();
                        if(mDataBase.deletePlaces(id))
                            placesList.remove(position);
                        placeAdapter.notifyDataSetChanged();

                        break;
                }

                // false : close the menu; true : not close the menu
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                favouritePlace place = placesList.get(position);
                double latitude = Double.parseDouble(place.getFavLatitude());
                double longitude = Double.parseDouble(place.getFavLongitude());

                Intent intent = new Intent(favouritePlace.this,MainActivity.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                startActivity(intent);
                Toast.makeText(favouritePlace.this, "cell clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadPlaces() {
        /*
        String sql = "SELECT * FROM employees";


        Cursor cursor = mDataBase.rawQuery(sql, null);

         */
        Cursor cursor = mDataBase.getAllPlaces();
        if(cursor.moveToFirst()){
            do {
                placesList.add(new favoritePlace(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getDouble(3),
                        cursor.getString(4)
                ));


            }while (cursor.moveToNext());
            cursor.close();
            //show item in a listView
            //we use a custom adapter to show employees

            placeAdapter = new placeAdapter(this, R.layout.list_layout_place, placesList, mDataBase);
//            placeAdapter.notifyDataSetChanged();
            listView.setAdapter(placeAdapter);

        }
    }

//    private void deleteplace(final int position) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(fav_place_Activity.this);
//        builder.setTitle("Are you sure?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                /*
//                String sql = "DELETE FROM employees WHERE id = ?";
//                mDatabase.execSQL(sql,new Integer[]{employee.getId()});
//
//                 */
//
//                final favoritePlace place = placesList.get(position);
//                int id = place.getId();
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }


}
