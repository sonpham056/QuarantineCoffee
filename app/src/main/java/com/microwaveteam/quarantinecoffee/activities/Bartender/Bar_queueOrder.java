package com.microwaveteam.quarantinecoffee.activities.Bartender;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.FeatureHelper;
import com.microwaveteam.quarantinecoffee.Helper.ViewCard;
import com.microwaveteam.quarantinecoffee.Helper.ViewQueue;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.Manager.ManageStaffActivity;
import com.microwaveteam.quarantinecoffee.models.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Bar_queueOrder extends AppCompatActivity {
    RecyclerView featureRecycler;
    DatabaseReference myRef;
    List<Order> orders;

    ViewQueue adapter;

    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_queue_order);
        featureRecycler = findViewById(R.id.recycle_order_queue);
        orders = new ArrayList<>();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        myRef = FirebaseDatabase.getInstance().getReference("Order").child(currentDate);

        Query checkDate = myRef.orderByChild("dateTime").equalTo(currentDate);
        checkDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    addTolist(snapshot);
                System.out.println(snapshot + "---------------------");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addTolist(DataSnapshot snapshot) {
        ArrayList<FeatureHelper> listFeatures = new ArrayList<>();
        featureRecycler.setHasFixedSize(true);
        featureRecycler.setLayoutManager(
                new LinearLayoutManager(Bar_queueOrder.this,LinearLayoutManager.VERTICAL,false));
        for(DataSnapshot ds :snapshot.getChildren()){
            Order data = ds.getValue(Order.class);
            orders.add(data);
            String productName = data.getProductName();
            int amount = data.getAmount();
            if(data.getFinish()){
                listFeatures.add(
                        new FeatureHelper(R.drawable.ic_finish
                                ,"Ban"
                                ,productName
                                ,"SL:" + amount));
            }else{
                listFeatures.add(
                        new FeatureHelper(R.drawable.ic_checkbox
                                ,"Ban"
                                ,productName
                                ,"SL:" + amount));
            }
            adapter = new ViewQueue(listFeatures, Bar_queueOrder.this);
            featureRecycler.setAdapter(adapter);

        }
    }
}