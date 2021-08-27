package com.microwaveteam.quarantinecoffee.activities.Manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.LoginActivity;
import com.microwaveteam.quarantinecoffee.activities.TimeKeeperActivity;
import com.microwaveteam.quarantinecoffee.activities.Waiter.WaiterActivity;
import com.microwaveteam.quarantinecoffee.activities.change_profile;

public class ManagerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button btnManageProduct, btnManageStaff, btnStatistical, btnEmployeeTime;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_manager);

        bind();
        clicking();
    }

    private void clicking() {
        btnManageStaff.setOnClickListener(view -> {
            Intent intent = new Intent(ManagerActivity.this, ManageStaffActivity.class);
            startActivity(intent);
        });

        btnManageProduct.setOnClickListener(view -> {
            Intent intent = new Intent(ManagerActivity.this, ManageProductActivity.class);
            startActivity(intent);
        });

        btnStatistical.setOnClickListener(view -> {
            Intent intent = new Intent(this, StatisticalActivity.class);
            startActivity(intent);
        });

        btnEmployeeTime.setOnClickListener(view -> {
            Intent intent = new Intent(this, EmployeesTimeKeeperActivity.class);
            startActivity(intent);
        });
    }

    private void bind(){
        userName = getIntent().getStringExtra("UserNameLogged");
        btnManageProduct = findViewById(R.id.btnManageProduct);
        btnManageStaff = findViewById(R.id.btnManageEmployee);
        btnStatistical = findViewById(R.id.btnStatistical);
        btnEmployeeTime = findViewById(R.id.btnEmployeeTimeKeeper);
        drawerLayout = findViewById(R.id.manager_drawer);
        navigationView = findViewById(R.id.manager_navigation);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(ManagerActivity.this,
                drawerLayout,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getCheckedItem();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help_navItem_exit:
                finish();
                break;
            case R.id.help_navItem_change_profile:
                Intent intent = new Intent(this, change_profile.class);
                intent.putExtra("userNameInChangeProfile",userName);
                startActivity(intent);
                break;
            case R.id.help_navItem_timekeeper:
                Intent timeKeeperIntent = new Intent(this, TimeKeeperActivity.class);
                timeKeeperIntent.putExtra("userNameInTimeKeeper",userName);
                startActivity(timeKeeperIntent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}