package com.kulartist.events;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class Home extends AppCompatActivity {

    public static int USERID=1;





    public void shoAlertBox(String title, String message) {
        // Initializing a new alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);

        // Set a title for alert dialog
        builder.setTitle(title);

        // Show a message on alert dialog
        builder.setMessage(message);

        // Set the positive button
        builder.setPositiveButton("ok",null);


//        // Set the negative button
//        builder.setNegativeButton("No", null);
//
//        // Set the neutral button
//        builder.setNeutralButton("Cancel", null);

        // Create the alert dialog
        AlertDialog dialog = builder.create();

        // Finally, display the alert dialog
        dialog.show();

        // Get the alert dialog buttons reference
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
       // Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
       // Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

        // Change the alert dialog buttons text and background color
        positiveButton.setTextColor(Color.parseColor("#1E88E5"));
        //positiveButton.setBackgroundColor(Color.parseColor("#FFE1FCEA"));

//        negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
//        negativeButton.setBackgroundColor(Color.parseColor("#FFFCB9B7"));
//
//        neutralButton.setTextColor(Color.parseColor("#FF1B5AAC"));
//        neutralButton.setBackgroundColor(Color.parseColor("#FFD9E9FF"));
    }










    private void showMsg(String title, String message) {


        new AlertDialog.Builder(Home.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        })
                .setCancelable(false)
                .create().show();


    }

    public void viewEvents(View view) {
        DatabaseHelper dbHelper;
        dbHelper = new DatabaseHelper(this, "", null, 2);
        //Intent i=new Intent(Home.this,editDatabase.class);
        //startActivity(i);

         {
            Cursor cursor = dbHelper.viewAllProducts(USERID);

            if (cursor.getCount() == 0) {
                shoAlertBox("error", "No Event Found");
                return;
            }

            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append("EventID: " + cursor.getString(0) + "\n");
                buffer.append("Name: " + cursor.getString(1) + "\n");
                buffer.append("Location: " + cursor.getString(2) + "\n");
                buffer.append("StartTime: " + cursor.getString(3) + "\n");
                buffer.append("EndTime: " + cursor.getString(4) + "\n");
                buffer.append("UserID: " + cursor.getString(5) + "\n");
                buffer.append("\n");
            }

             shoAlertBox("MeetUps", buffer.toString());
        }


    }

    public void addEvent(View view) {

        Intent i=new Intent(Home.this,editDatabase.class);
        i.putExtra("EXTRA_SESSION_ID", "Add Event");
        startActivity(i);

    }

    public void updateEvent(View view) {

        Intent i=new Intent(Home.this,editDatabase.class);
        i.putExtra("EXTRA_SESSION_ID", "Update Event");
        startActivity(i);
    }

    public void deleteEvent(View view) {

        Intent i=new Intent(Home.this,editDatabase.class);
        i.putExtra("EXTRA_SESSION_ID", "Delete Event");
        startActivity(i);
    }















    class eventUsers {
        /*
        INSTANCE FIELDS
         */
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        @SerializedName("imageurl")
        private String imageURL;


        public eventUsers(int id, String name, String imageURL) {
            this.id = id;
            this.name = name;
            this.imageURL = imageURL;
        }

        /*
         *GETTERS AND SETTERS
         */
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getImageURL() {
            return imageURL;
        }


        /*
        TOSTRING
         */
        @Override
        public String toString() {
            return name;
        }
    }

    interface MyAPIService {

        @GET("bins/1c85vm")
        Call<List<eventUsers>> getSpacecrafts();
    }

    static class RetrofitClientInstance {

        private static Retrofit retrofit;
        private static final String BASE_URL = "https://api.myjson.com/";

        public static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }

    class ListViewAdapter extends BaseAdapter{

        private List<eventUsers> usersList;
        private Context context;

        public ListViewAdapter(Context context,List<eventUsers> users){
            this.context = context;
            this.usersList = users;
        }

        @Override
        public int getCount() {
            return usersList.size();
        }

        @Override
        public Object getItem(int pos) {
            return usersList.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            if(view==null)
            {
                view=LayoutInflater.from(context).inflate(R.layout.model,viewGroup,false);
            }

            TextView nameTxt = view.findViewById(R.id.userName);
            ImageView spacecraftImageView = view.findViewById(R.id.userImage);

            final eventUsers thisSpacecraft= usersList.get(position);

            nameTxt.setText(thisSpacecraft.getName());


            if(thisSpacecraft.getImageURL() != null && thisSpacecraft.getImageURL().length()>0)
            {
                Picasso.get().load(thisSpacecraft.getImageURL()).placeholder(R.drawable.placeholder).into(spacecraftImageView);
            }else {
                Toast.makeText(context, "Empty Image URL", Toast.LENGTH_LONG).show();
                Picasso.get().load(R.drawable.placeholder).into(spacecraftImageView);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, thisSpacecraft.getName(), Toast.LENGTH_SHORT).show();
                    USERID=position+1;
                }
            });

            return view;
        }
    }

    private ListViewAdapter adapter;
    private ListView mListView;
    ProgressBar myProgressBar;

    private void populateListView(List<eventUsers> spacecraftList) {
        mListView = findViewById(R.id.mListView);
        adapter = new ListViewAdapter(this,spacecraftList);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        final ProgressBar myProgressBar= findViewById(R.id.myProgressBar);
        myProgressBar.setIndeterminate(true);
        myProgressBar.setVisibility(View.VISIBLE);

        /*Create handle for the RetrofitInstance interface*/
        MyAPIService myAPIService = RetrofitClientInstance.getRetrofitInstance().create(MyAPIService.class);

        Call<List<eventUsers>> call = myAPIService.getSpacecrafts();
        call.enqueue(new Callback<List<eventUsers>>() {

            @Override
            public void onResponse(Call<List<eventUsers>> call, Response<List<eventUsers>> response) {
                myProgressBar.setVisibility(View.GONE);
                populateListView(response.body());
            }
            @Override
            public void onFailure(Call<List<eventUsers>> call, Throwable throwable) {
                myProgressBar.setVisibility(View.GONE);
                Toast.makeText(Home.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}
