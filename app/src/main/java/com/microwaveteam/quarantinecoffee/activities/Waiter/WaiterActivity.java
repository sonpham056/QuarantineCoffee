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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                .format(new Date());
        DatabaseReference queueRef = database.getReference("OrderQueue").child(currentDate);

        DatabaseReference tableRef = database.getReference("Table");
        binding();
        onClick(queueRef, currentDate,tableRef);
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

    private void onClick(DatabaseReference myRef, String currentDate, DatabaseReference tableRef) {
        btnAddtoCart.setOnClickListener(view -> {

            int count = 0;
            List<Order> orders = new ArrayList<>();

            /*orders.add(new Order("2", "Cafe4", "1", false,currentDate));
            orders.add(new Order("3", "Cafe1", "3", false,currentDate));
            orders.add(new Order("1", "Cafe3", "5", false,currentDate));
            orders.add(new Order("4", "Cafe4", "2", false,currentDate));
            orders.add(new Order("5", "Cafe5", "2", false,currentDate));
            orders.add(new Order("6", "Something", "1", false,currentDate));*/
            for(Order item :orders){
                count++;
                myRef.child("Ban" + count).setValue(item);
            }

            for(int i = 1; i < 7; i ++){
                String tableStr = "Ban" + i;
                tableRef.child(tableStr).child("isAccepted").setValue(false);
            }
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