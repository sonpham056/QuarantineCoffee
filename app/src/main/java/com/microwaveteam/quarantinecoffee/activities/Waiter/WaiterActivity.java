package com.microwaveteam.quarantinecoffee.activities.Waiter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.LoginActivity;
import com.microwaveteam.quarantinecoffee.models.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WaiterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button btnAddtoCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_activity_waiter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference queueRef = database.getReference("OrderQueue");
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                .format(new Date());
        binding();
        onClick(queueRef, currentDate);
    }


    private void binding() {
        btnAddtoCart = findViewById(R.id.w_btn_confirm_order);


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

    private void onClick(DatabaseReference myRef, String currentDate) {
        btnAddtoCart.setOnClickListener(view -> {

            Order order = new Order("2", "Cafe", "2", false,currentDate);
            myRef.child(currentDate).push().setValue(order);

        });
    }






    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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