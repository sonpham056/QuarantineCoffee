package com.microwaveteam.quarantinecoffee.activities.Bartender;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microwaveteam.quarantinecoffee.activities.Bartender.BartenderActivity;
import com.microwaveteam.quarantinecoffee.models.Order;
import com.microwaveteam.quarantinecoffee.models.Table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BartenderDAL {
    private static DatabaseReference db = FirebaseDatabase.getInstance().getReference();



    public static void rePush(FirebaseDatabase database, DataSnapshot snapshot, String currentDate, String key, Context context) {
        DatabaseReference myRef = database.getReference("Order");

        myRef.child(currentDate).child(key).setValue(snapshot.getValue(Order.class), (error, ref) -> {
            if(error == null){
                Toast.makeText(context, "Push failed: " + error.toString(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Push success", Toast.LENGTH_SHORT).show();
            }
        });
        String tableStr = "Ban" + key;
        DatabaseReference tableRef = database.getReference("Table").child(tableStr).child("isAccepted");
        tableRef.setValue(true);

    }

    public static void showNotiAndRemoveQueue(DataSnapshot snapshot, String key, FirebaseDatabase database, Context context) {


        List<Order> orderList = new ArrayList<>();
        for (DataSnapshot data : snapshot.getChildren()) {
            orderList.add(data.getValue(Order.class));
        }
        for (Order item : orderList) {

//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setTitle("New order");
//            assert orderList != null;
//            builder.setMessage(snapshot.getKey()
//                    + "\n" + "Order: " + item.getProductName() + " - SL: " + item.getAmount());
//            builder.setPositiveButton("Ok", (dialogInterface, i) -> {
//                BartenderDAO.rePush(database, snapshot, currentDate, key, context);
//            });
//            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
//                return;
//            });
//            builder.show();
        }
    }

    public static void showNotiAndRemoveQueue(Order order, DataSnapshot data, FirebaseDatabase database, Context context) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                .format(new Date());

    }
}
