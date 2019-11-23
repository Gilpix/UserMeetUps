package com.kulartist.events;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;


import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class editDatabase extends AppCompatActivity {
    EditText evnt_id, name, location, startTime, endTime, userID;

    TextView eId,uId,eName,eLocation,eSt,eEt;

    Button saveChanges,add, update, remove, view;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_database);






        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#5E35B1")));





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBlue, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBlue));
        }

        evnt_id = findViewById(R.id.eventID);
        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        userID = findViewById(R.id.userID);

        eId = findViewById(R.id.eID);
        uId = findViewById(R.id.uId);
        eName = findViewById(R.id.eName);
        eLocation = findViewById(R.id.eLocation);
        eSt = findViewById(R.id.eSt);
        eEt = findViewById(R.id.eEt);

        saveChanges = findViewById(R.id.add);
//        update = findViewById(R.id.update);
//        remove = findViewById(R.id.remove);
//        view = findViewById(R.id.view);

        dbHelper = new DatabaseHelper(this, "", null, 2);


        String buttonText = getIntent().getStringExtra("EXTRA_SESSION_ID");

        if (buttonText.equals("Add Event")) {
            saveChanges.setText(buttonText);
            getSupportActionBar().setTitle("Add New MeetUp");

            evnt_id.setVisibility(EditText.INVISIBLE);
            eId.setVisibility(TextView.INVISIBLE);

            saveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(userID.getText().toString().isEmpty() || name.getText().toString().isEmpty()
                    || location.getText().toString().isEmpty() || startTime.getText().toString().isEmpty()
                    || endTime.getText().toString().isEmpty())
                    {
                        Toast.makeText(editDatabase.this,"Please Enter All Fields",Toast.LENGTH_SHORT).show();
                    }

                    else
                    {

                    long isInserted = dbHelper.addEvent(name.getText().toString(), location.getText().toString(), startTime.getText().toString(), endTime.getText().toString(), Integer.parseInt(userID.getText().toString()));

                    if (isInserted >= 0) {
                        Toast.makeText(editDatabase.this, "The records were successfully entered..", Toast.LENGTH_SHORT).show();
                        evnt_id.setText("");
                        name.setText("");
                        location.setText("");
                        startTime.setText("");
                        endTime.setText("");
                        userID.setText("");
                    } else {
                        Toast.makeText(editDatabase.this, "Some problem occurred while adding. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }}
            });

        }
        if (buttonText.equals("Update Event")) {
            saveChanges.setText(buttonText);
            getSupportActionBar().setTitle("Update MeetUp");




            saveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(userID.getText().toString().isEmpty() || name.getText().toString().isEmpty()
                            || location.getText().toString().isEmpty() || startTime.getText().toString().isEmpty()
                            || endTime.getText().toString().isEmpty() || evnt_id.getText().toString().isEmpty())
                    {
                        Toast.makeText(editDatabase.this,"Please Enter All Fields",Toast.LENGTH_SHORT).show();
                    }

                    else
                    {


                    int isUpdated = dbHelper.updateEvent(evnt_id.getText().toString(), name.getText().toString(), location.getText().toString(), startTime.getText().toString(), endTime.getText().toString(), Integer.parseInt(userID.getText().toString()));

                    if (isUpdated >= 0) {
                        Toast.makeText(editDatabase.this, "The records were successfully updated..", Toast.LENGTH_SHORT).show();
                        evnt_id.setText("");
                        name.setText("");
                        location.setText("");
                        startTime.setText("");
                        endTime.setText("");
                        userID.setText("");
                    } else {
                        Toast.makeText(editDatabase.this, "Some problem occurred while updating. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }}
            });


        }
        if (buttonText.equals("Delete Event")) {
            saveChanges.setText(buttonText);
            getSupportActionBar().setTitle("Delete MeetUp");



            name.setVisibility(EditText.INVISIBLE);
            eName.setVisibility(TextView.INVISIBLE);

            location.setVisibility(EditText.INVISIBLE);
            eLocation.setVisibility(TextView.INVISIBLE);

            startTime.setVisibility(EditText.INVISIBLE);
            eSt.setVisibility(TextView.INVISIBLE);

            endTime.setVisibility(EditText.INVISIBLE);
            eEt.setVisibility(TextView.INVISIBLE);



            saveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(userID.getText().toString().isEmpty() || evnt_id.getText().toString().isEmpty())
                    {
                        Toast.makeText(editDatabase.this,"Please Enter All Fields",Toast.LENGTH_SHORT).show();
                    }

                    else
                    {

                    int deleted = dbHelper.deleteEvent(evnt_id.getText().toString());

                    if (deleted > 0) {
                        Toast.makeText(editDatabase.this, "The records were successfully updated..", Toast.LENGTH_SHORT).show();
                        evnt_id.setText("");
                        name.setText("");
                        location.setText("");
                        startTime.setText("");
                        endTime.setText("");
                        userID.setText("");
                    } else {
                        Toast.makeText(editDatabase.this, "Some problem occurred while deleting. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }}
            });

        }


//        view.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Cursor cursor = dbHelper.viewAllProducts();
//
//                if (cursor.getCount() == 0) {
//                    showMsg("error", "No Products Found");
//                    return;
//                }
//
//                StringBuffer buffer = new StringBuffer();
//                while (cursor.moveToNext()) {
//                    buffer.append("EventID: " + cursor.getString(0) + "\n");
//                    buffer.append("Name: " + cursor.getString(1) + "\n");
//                    buffer.append("Location: " + cursor.getString(2) + "\n");
//                    buffer.append("StartTime: " + cursor.getString(3) + "\n");
//                    buffer.append("EndTime: " + cursor.getString(4) + "\n");
//                    buffer.append("UserID: " + cursor.getString(5) + "\n");
//                    buffer.append("\n");
//                }
//
//                showMsg("Data Received", buffer.toString());
//            }
//        });
//    }
//
//    private void showMsg(String title, String message) {
//
//        new Builder(editDatabase.this)
//                .setTitle(title)
//                .setMessage(message)
//                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        })
//                .setCancelable(false)
//                .create().show();
//
//
//    }
    }
}