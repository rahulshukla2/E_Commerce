package com.example.e_comm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_comm.ui.mycart.MyCartFragment;
import com.example.e_comm.ui.myorders.MyOrdersFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;

import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
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

import java.io.Serializable;

public class CustomHomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    SessionManagement sessionManagement ;
    public static final String TAG ="MY Custom Home Activity";


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManagement = new SessionManagement(getApplicationContext());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_customer);
        View headerView = navigationView.getHeaderView(0);
        TextView name = headerView.findViewById(R.id.Username);
        TextView mobile_no = headerView.findViewById(R.id.textMobileNum);
        String n = name.getText().toString().trim() +" "+ sessionManagement.getName();
        Log.d(TAG,n);
        name.setText(n);
        mobile_no.setText(sessionManagement.getMOBILENO());



        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_customer_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();


                if(id==R.id.nav_customer_home)
                {
                    //replaceFragment(new CustomerHomeFragment());
                    getSupportActionBar().setTitle("Home");
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new CustomerHomeFragment()).commit();
                }
                else if(id==R.id.nav_my_cart)
                {
                    getSupportActionBar().setTitle("My Cart");
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new MyCartFragment()).commit();

                }
                else if(id==R.id.nav_my_orders)
                {
                    getSupportActionBar().setTitle("My Orders");
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new MyOrdersFragment()).commit();

                }
                else if(id==R.id.nav_logout)
                {

                    SessionManagement sessionManagement = new SessionManagement(CustomHomeActivity.this);
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
        getMenuInflater().inflate(R.menu.custom_home, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                if(query!=null && !query.equals("")) {

                    CustomerSearchFragment obj = new CustomerSearchFragment();
                    Bundle args = new Bundle();
                    args.putString("query", query);
                    obj.setArguments(args);

                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, obj).addToBackStack("Details").commit();
                    return false;
                }
                else if(query == null || query.equals(""))
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CustomerHomeFragment()).addToBackStack("Details").commit();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CustomerHomeFragment()).addToBackStack("Details").commit();

                return false;

            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}