package com.example.e_comm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.e_comm.ui.home.HomeFragment;
import com.example.e_comm.ui.mycart.MyCartFragment;
import com.example.e_comm.ui.myorders.MyOrdersFragment;
import com.google.android.material.navigation.NavigationView;

public class AdminHomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static final String TAG = "AdminHomeActivity";
    SessionManagement sessionManagement;


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
        setContentView(R.layout.activity_admin_home);

        Toolbar toolbar = findViewById(R.id.toolbar_seller);
        setSupportActionBar(toolbar);

        sessionManagement = new SessionManagement(getApplicationContext());

        DrawerLayout drawer = findViewById(R.id.drawer_layout_admin);
        NavigationView navigationView = findViewById(R.id.nav_view_admin);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_view_customer_frag, R.id.nav_view_seller_frag, R.id.nav_logout)
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

                if(id==R.id.nav_view_customer_frag)
                {
                    Log.d(TAG,"printfd");

                    getSupportActionBar().setTitle("View Customers");
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new ViewCustomerFragment()).commit();

                }
                else if(id==R.id.nav_view_seller_frag) {
                    Log.d(TAG, "printfd");
                    getSupportActionBar().setTitle("View Sellers");
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ViewSellerFragment()).addToBackStack("SellerAddProductFragment").commit();
                    //replaceFragment(new SellerAddProductFragment());
                }
                else if(id==R.id.nav_logout)
                {
                    Log.d(TAG,"came here");
                    SessionManagement sessionManagement = new SessionManagement(AdminHomeActivity.this);
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
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
