package com.example.e_comm;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Gallery;
import android.widget.Toast;
import android.util.Log;

import com.example.e_comm.ui.gallery.GalleryFragment;
import com.example.e_comm.ui.home.HomeFragment;
import com.example.e_comm.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SellerHomeActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    public static final String TAG = "SellerHomeActivity";



  /*  public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    protected void onStart() {
        //getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).addToBackStack("home");
        //getSupportFragmentManager().get
       // replaceFragment(new HomeFragment());
        super.onStart();
    }

    public void replaceFragment(Fragment frag) {
        FragmentManager manager = getSupportFragmentManager();
        if (manager != null){
            FragmentTransaction t = manager.beginTransaction();
            Fragment currentFrag = manager.findFragmentById(R.id.nav_host_fragment);

            //Check if the new Fragment is the same
            //If it is, don't add to the back stack
            if (currentFrag != null && currentFrag.getClass().equals(frag.getClass())) {
                t.replace(R.id.nav_host_fragment, frag).commit();
            } else {
                t.replace(R.id.nav_host_fragment, frag).addToBackStack(null).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG,"Counter is "+count);
        if(count==0)
        {
            Log.d(TAG," In if condition Counter is "+count);
            moveTaskToBack(true);
            super.onBackPressed();

        }
        else {
            getSupportFragmentManager().popBackStackImmediate();
        }


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_add_product, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Log.d(TAG,"came here1");

                if(id==R.id.nav_home)
                {
                    Log.d(TAG,"printfd");

                    getSupportActionBar().setTitle("Home");
                    //getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
                    replaceFragment(new HomeFragment());
                }
                else if(id==R.id.nav_add_product)
                {
                    Log.d(TAG,"printfd");
                    getSupportActionBar().setTitle("Add Product");
                   /// getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new SellerAddProductFragment()).addToBackStack("SellerAddProductFragment").commit();
                    replaceFragment(new SellerAddProductFragment());
                }
                else if(id==R.id.nav_gallery)
                {
                    getSupportActionBar().setTitle("Gallery");
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new GalleryFragment()).commit();
                    replaceFragment(new GalleryFragment());
                    Log.d(TAG,"printfd");
                }
                else if(id==R.id.nav_slideshow)
                {
                    getSupportActionBar().setTitle("Slideshow");
                   // getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new SlideshowFragment()).commit();
                    replaceFragment(new SlideshowFragment());
                }
                else if(id==R.id.nav_logout)
                {
                    Log.d(TAG,"came here");
                    SessionManagement sessionManagement = new SessionManagement(SellerHomeActivity.this);
                    sessionManagement.removeSession();
                    Intent intent = new Intent(getApplicationContext(),StartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"You are logged out of the system successfully", Toast.LENGTH_LONG).show();
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seller_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
