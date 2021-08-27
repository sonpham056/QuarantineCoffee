package com.microwaveteam.quarantinecoffee.activities.Bartender;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.FeatureHelper;
import com.microwaveteam.quarantinecoffee.Helper.ViewQueue;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.Bartender.BartenderActivity;
import com.microwaveteam.quarantinecoffee.models.Order;
import com.microwaveteam.quarantinecoffee.models.Table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BartenderDAL {
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();


    @SuppressLint("RestrictedApi")
    public static List<Order> showNotiAndRemoveQueue(String key, Context context) {
        List<Order> orderList = new ArrayList<>();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                .format(new Date());
        DatabaseReference myRef = db.getReference("OrderQueue").child(currentDate);
        Query checkOrder = myRef.orderByChild("table").equalTo(key);
        checkOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot data : snapshot.getChildren()) {
                    orderList.add(data.getValue(Order.class));
                    System.out.println("======data" + data.getRef().getPath().toString());
                }
                for (Order item : orderList) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("New order");
                    assert orderList != null;
                    builder.setMessage(item.getTable()
                            + "\n" + "Order: " + item.getProductName() + " - SL: " + item.getAmount());
                    builder.setPositiveButton("Ok", (dialogInterface, i) -> {
                        BartenderDAL.accept(item.getTable());
                    });
                    builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                        return;
                    });
                    builder.show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return orderList;
    }

    private static void accept(String table) {
        DatabaseReference myRef = db.getReference("Table").child(table);
        myRef.child("isAccepted").setValue(true);
    }
}
