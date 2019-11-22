package com.kulartist.events;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class Home extends AppCompatActivity {


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

        @GET("bins/7tuxu")
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
        public View getView(int position, View view, ViewGroup viewGroup) {
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
                    Toast.makeText(context, thisSpacecraft.getName(), Toast.LENGTH_SHORT).show();
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
}
