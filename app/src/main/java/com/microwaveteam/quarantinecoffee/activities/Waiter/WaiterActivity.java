package com.microwaveteam.quarantinecoffee.activities.Waiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.microwaveteam.quarantinecoffee.Helper.NavigationDrawer;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.LoginActivity;

public class WaiterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_activity_waiter);


        
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(WaiterActivity.this,
                drawerLayout,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getCheckedItem();
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.help_navItem_exit:

                Intent it = new Intent(WaiterActivity.this, LoginActivity.class);
                startActivity(it);
                break;
            case R.id.help_navItem_profile:
                //TODO: excuse me
            case R.id.help_navItem_timekeeper:
                //TODO: excuse me
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}