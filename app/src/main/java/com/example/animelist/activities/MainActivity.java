package com.example.animelist.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.animelist.Account;
import com.example.animelist.R;
import com.example.animelist.Saved;
import com.example.animelist.adapters.ParseAdapter;
import com.example.animelist.models.Anime;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String URL_JSON = "https://gist.githubusercontent.com/JackFrostDJ/08d04c828aaf1c21abdeeeee3ff77920/raw/88f22437d23b2b6a9195a7ea28d405841502df71/anime.json";
    private JsonArrayRequest ArrayRequest ;
    private RequestQueue requestQueue ;
    private List<Anime> lstAnime = new ArrayList<>();
    private RecyclerView myrv ;
    DatabaseReference databaseReference;
    String a_name;
    String a_studio;
    String a_rating;
    String a_category;
    String a_description;
    String a_nb_episode;
    String a_img_url;
    int countChild = 0;
    Anime anime = new Anime();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myrv = findViewById(R.id.recyclerView);
        navDrawer = findViewById(R.id.nav_drawer);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                setDrawerClick(menuItem.getItemId());
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        jsoncall();
        // dataRetrieve();
    }

    private void setDrawerClick(int itemId) {
        switch (itemId) {
            case R.id.nav_home:
                recreate();
            case R.id.nav_saved:
                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            case R.id.nav_account:
                redirectActivity(MainActivity.this, Account.class);
            case R.id.nav_logout:
                logout(MainActivity.this);
        }

    }


    public void jsoncall() {


        ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0 ; i<response.length();i++) {

                    //Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();

                    try {

                        jsonObject = response.getJSONObject(i);
                        Anime anime = new Anime();

                        anime.setName(jsonObject.getString("name"));
                        anime.setRating(jsonObject.getString("Rating"));
                        anime.setDescription(jsonObject.getString("description"));
                        anime.setImage_url(jsonObject.getString("img"));
                        anime.setStudio(jsonObject.getString("studio"));
                        anime.setCategorie(jsonObject.getString("categorie"));
                        anime.setNb_episode(jsonObject.getString("episode") + " Episodes");
                        //Toast.makeText(MainActivity.this,anime.toString(),Toast.LENGTH_SHORT).show();
                        lstAnime.add(anime);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                Toast.makeText(MainActivity.this,"Size of List "+String.valueOf(lstAnime.size()),Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this,lstAnime.get(1).toString(),Toast.LENGTH_SHORT).show();

                setRvadapter(lstAnime);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(ArrayRequest);
    }

//    public void dataRetrieve() {
////        databaseReference = FirebaseDatabase.getInstance().getReference();
////        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                countChild = (int) snapshot.getChildrenCount();
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////
////            }
////        });
//
////        for (int i = 0; i <= countChild; i++) {
//            databaseReference = FirebaseDatabase.getInstance().getReference();
//
//            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    try {
//                        a_name = dataSnapshot.child("name").getValue().toString();
//                        a_description = dataSnapshot.child("description").getValue().toString();
//                        a_studio = dataSnapshot.child("studio").getValue().toString();
//                        a_category = dataSnapshot.child("categorie").getValue().toString();
//                        a_rating = dataSnapshot.child("Rating").getValue().toString();
//                        a_img_url = dataSnapshot.child("img").getValue().toString();
//                        a_nb_episode = dataSnapshot.child("episode").getValue().toString();
//
//                        anime.setName(a_name);
//                        anime.setNb_episode(a_nb_episode);
//                        anime.setCategorie(a_category);
//                        anime.setDescription(a_description);
//                        anime.setImage_url(a_img_url);
//                        anime.setRating(a_rating);
//                        anime.setStudio(a_studio);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//
//                    lstAnime.add(anime);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        //}
//
//        Toast.makeText(MainActivity.this,"Size of List "+String.valueOf(lstAnime.size()),Toast.LENGTH_SHORT).show();
//        Toast.makeText(MainActivity.this,lstAnime.get(1).toString(),Toast.LENGTH_SHORT).show();
//
//        setRvadapter(lstAnime);
//
//        requestQueue = Volley.newRequestQueue(MainActivity.this);
//        requestQueue.add(ArrayRequest);
//
//    }



    public void setRvadapter (List<Anime> lst) {

        ParseAdapter myAdapter = new ParseAdapter(this,lst) ;
        myrv.setLayoutManager(new LinearLayoutManager(this));
        myrv.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ParseAdapter myAdapter = (ParseAdapter) myrv.getAdapter();
                myAdapter.getFilter().filter(s);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
            int id = item.getItemId();
            switch (id) {
                case R.id.nav_home:
                    recreate();
                    return true;
                case R.id.nav_saved:
                    redirectActivity(this, Saved.class);
                    return true;
                case R.id.nav_account:
                    redirectActivity(this, Account.class);
                    return true;
                case R.id.nav_logout:
                    logout(this);
                    return true;
            }
        return super.onOptionsItemSelected(item);
    }

    public void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent (activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public static void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to Log Out?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}