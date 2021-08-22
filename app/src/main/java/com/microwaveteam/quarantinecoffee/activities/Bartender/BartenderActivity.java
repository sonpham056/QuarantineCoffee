package com.microwaveteam.quarantinecoffee.activities.Bartender;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.FeatureHelper;
import com.microwaveteam.quarantinecoffee.Helper.ViewQueue;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.LoginActivity;
import com.microwaveteam.quarantinecoffee.activities.Waiter.WaiterActivity;
import com.microwaveteam.quarantinecoffee.models.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BartenderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView featureRecycler;
    static Context context;
    List<Order> orders;
    Button btnGo;
    ViewQueue adapter;
    ArrayList<FeatureHelper> listFeatures;
    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_bartender);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Table");
        listFeatures = new ArrayList<>();


        featureRecycler = findViewById(R.id.recycle_order_queue);
        featureRecycler.setHasFixedSize(true);
        featureRecycler.setLayoutManager(
                new LinearLayoutManager(BartenderActivity.this, LinearLayoutManager.VERTICAL, false));

        adapter = new ViewQueue(listFeatures, BartenderActivity.this);
        featureRecycler.setAdapter(adapter);

        Query check = myRef.orderByChild("isAccepted").equalTo(false);

        //System.out.println("----------" + check.getPath().toString() + "-------------");
        check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!BartenderActivity.this.isFinishing()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Log.e("line55baracti", data.getKey());
                        BartenderDAL.showNotiAndRemoveQueue(data.getKey(), BartenderActivity.this);
                        binding(data.getKey());
                    }
                } else {
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void binding(String key) {
        context = this;
        orders = new ArrayList<>();

        //Drawer navigation init
        drawerLayout = findViewById(R.id.bar_drawer_layout);
        navigationView = findViewById(R.id.bar_navigationView);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(BartenderActivity.this,
                drawerLayout,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getCheckedItem();

        //init database
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OrderQueue").child(currentDate);
        Query checkOrder = myRef.orderByChild("table").equalTo(key);

        checkOrder.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addTolist(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addTolist(DataSnapshot snapshot) {
        for (DataSnapshot ds : snapshot.getChildren()) {
            Order data = ds.getValue(Order.class);
            orders.add(data);
            String productName = data.getProductName();
            int amount = data.getAmount();
            System.out.println("-----data.getProductName()---" + data.getProductName());
            listFeatures.add(
                    new FeatureHelper(
                            R.drawable.ic_checkbox,
                            "" + data.getTable()
                            ,"" + data.getProductName(),
                            "SL: " + data.getAmount()));
            featureRecycler.setAdapter(adapter);

        }
    }
    public static void alertDialog(String title, String message, String table){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message + "/n" + table);
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            BartenderActivity.rePush(table, table, context);
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            return;
        });
        builder.show();
    }

    private static void rePush(String table, String key, Context context) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OrderQueue");
        Query checkOrder = myRef.orderByChild("table").equalTo(key);
        //TODO: cham hoi?

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help_navItem_exit:
                finish();
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
