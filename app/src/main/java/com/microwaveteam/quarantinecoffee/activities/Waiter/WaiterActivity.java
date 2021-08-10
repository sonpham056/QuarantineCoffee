package com.microwaveteam.quarantinecoffee.activities.Waiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microwaveteam.quarantinecoffee.Helper.NavigationDrawer;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.LoginActivity;
import com.microwaveteam.quarantinecoffee.activities.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WaiterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button btnAddtoCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_activity_waiter);
        binding();
        onClick();
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

    private void onClick() {
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> map = new HashMap<String,String>();
                map.put("Cafe","2");
                map.put("cafe da","4");

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Order");
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                myRef.child(currentDate).push().setValue(map);

                Toast.makeText(WaiterActivity.this,"Oke",Toast.LENGTH_LONG);
            }
        });
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