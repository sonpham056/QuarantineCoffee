package com.microwaveteam.quarantinecoffee.activities.Waiter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.LoginActivity;
import com.microwaveteam.quarantinecoffee.activities.TimeKeeperActivity;
import com.microwaveteam.quarantinecoffee.activities.change_profile;
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
    SharedPreferences prefs;

    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_activity_waiter);


        prefs = getSharedPreferences("My app", MODE_PRIVATE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                .format(new Date());
        DatabaseReference queueRef = database.getReference("OrderQueue").child(currentDate);


        DatabaseReference tableRef = database.getReference("Table");
        binding();
        onClick(queueRef, currentDate, tableRef);
    }


    private void binding() {
        userName = getIntent().getStringExtra("UserNameLogged");
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
                tableRef.child("Ban" + count).child(item.getProductName()).setValue(item);
            }

            for (int i = 1; i < 7; i++) {
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
                /*Intent it = new Intent(WaiterActivity.this, LoginActivity.class);
                startActivity(it);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();*/
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                break;
            case R.id.help_navItem_bill_history:
                Intent intentBillHis = new Intent(this, BillHistoryActivity.class);
                startActivity(intentBillHis);
                break;
            case R.id.help_navItem_timekeeper:
                Intent timeKeeperIntent = new Intent(this, TimeKeeperActivity.class);
                timeKeeperIntent.putExtra("userNameInTimeKeeper",userName);
                startActivity(timeKeeperIntent);
            case R.id.help_navItem_change_profile:
                Intent intent2 = new Intent(this, change_profile.class);
                intent2.putExtra("userNameInChangeProfile",userName);
                startActivity(intent2);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        SharedPreferences.Editor editor = prefs.edit();
        if (prefs.getString("userName", "nothing Here").compareTo("nothing Here") == 0) {
            finish();
        }
        super.onResume();
    }
}