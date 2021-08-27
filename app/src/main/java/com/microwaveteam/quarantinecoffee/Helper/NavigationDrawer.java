package com.microwaveteam.quarantinecoffee.Helper;

import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.Waiter.WaiterActivity;

public class NavigationDrawer implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    public void draw(Context context){
        drawerLayout = drawerLayout.findViewById(R.id.drawer_layout);
        navigationView = navigationView.findViewById(R.id.navigationView);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle((Activity) context,
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
                //TODO: excuse me
                break;
            case R.id.help_navItem_change_profile:
                //TODO: excuse me
            case R.id.help_navItem_timekeeper:
                //TODO: excuse me
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
