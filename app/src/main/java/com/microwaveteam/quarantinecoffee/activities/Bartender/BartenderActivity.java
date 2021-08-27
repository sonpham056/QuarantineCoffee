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
import android.widget.Toast;

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
import com.microwaveteam.quarantinecoffee.activities.TimeKeeperActivity;
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
    //List<Order> orders;
    Button btnGo;
    ViewQueue adapter;
    ArrayList<FeatureHelper> listFeatures;
    static String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_bartender);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        listFeatures = new ArrayList<>();
        featureRecycler = findViewById(R.id.recycle_order_queue);
        featureRecycler.setHasFixedSize(true);
        featureRecycler.setLayoutManager(
                new LinearLayoutManager(BartenderActivity.this, LinearLayoutManager.VERTICAL, false));

        context = this;
        //orders = new ArrayList<>();

        //Drawer navigation init
        drawerLayout = findViewById(R.id.bar_drawer_layout);

        navigationView = findViewById(R.id.bar_navigationView);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(BartenderActivity.this,
                drawerLayout,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getCheckedItem();

        adapter = new ViewQueue(listFeatures, BartenderActivity.this);
        featureRecycler.setAdapter(adapter);

        DatabaseReference myRef = database.getReference("Table");
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
        userName = getIntent().getStringExtra("UserNameLogged");
        //init database
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OrderQueue").child(currentDate);
        Query checkOrder = myRef.orderByChild("table");

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
        //orders.clear();
        listFeatures.clear();
        for (DataSnapshot ds : snapshot.getChildren()) {
            Order orderData = ds.getValue(Order.class);

            //orders.add(orderData);
            String productName = orderData.getProductName();
            int amount = orderData.getAmount();
            System.out.println("-------.addTolist" + orderData.getKey());
            System.out.println("-----data.getProductName()---" + productName);
            listFeatures.add(
                    new FeatureHelper(
                            R.drawable.ic_checkbox,
                            "" + orderData.getTable()
                            ,"" + productName
                            ,"SL: " + amount
                            ,"" + orderData.getProductType(),
                            orderData.getKey()));
        }
        adapter.notifyDataSetChanged();
    }
    public static void alertDialogAndRepush(String title, String message, String OrderKey){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OrderQueue").child(currentDate).child(OrderKey);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Order order = snapshot.getValue(Order.class);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(title);
                builder.setMessage(message + "\n" + order.getTable() + "\n" + order.getProductName() + "\n" + order.getAmount());
                builder.setPositiveButton("Ok", (dialogInterface, i) -> {
                    BartenderActivity.rePush(order, context);
                    myRef.removeValue((error, ref) -> Toast.makeText(context,"Success removed Queue",Toast.LENGTH_LONG).show());
                });
                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    return;
                });
                builder.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private static void rePush(Order order, Context context) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Order");
        myRef.child(currentDate).child(order.getKey());
        myRef.setValue(order, (error, ref) -> Toast.makeText(context,"Success add Order",Toast.LENGTH_LONG).show());
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
                Intent timeKeeperIntent = new Intent(this, TimeKeeperActivity.class);
                timeKeeperIntent.putExtra("userNameInTimeKeeper",userName);
                startActivity(timeKeeperIntent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
