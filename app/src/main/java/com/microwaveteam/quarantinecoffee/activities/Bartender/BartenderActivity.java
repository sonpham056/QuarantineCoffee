package com.microwaveteam.quarantinecoffee.activities.Bartender;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BartenderActivity extends AppCompatActivity {

    Button btnGo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_bartender);

        btnGo = findViewById(R.id.btnBartenderGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(BartenderActivity.this,Bar_queueOrder.class);
                startActivity(it);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("OrderQueue");
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                .format(new Date());

        Query checkDate = myRef.child(currentDate).orderByChild("dateTime").equalTo(currentDate);

        checkDate.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                showNotiAndRemoveQueue(snapshot,database,currentDate);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void showNotiAndRemoveQueue(DataSnapshot snapshot, FirebaseDatabase database, String currentDate) {
        Order order = snapshot.getValue(Order.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(BartenderActivity.this);
        builder.setTitle("New order");
        assert order != null;
        builder.setMessage("Ban so: " + order.getTable()
                + "\n" + "Order: " + order.getProductName() + " - " + order.getAmount());
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                snapshot.getRef().removeValue();
                rePush(database,snapshot,currentDate);
            }
        });
        builder.setNegativeButton("Hong nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
                //TODO: có không nhận được không?
            }
        });
        builder.show();
    }
    private void rePush(FirebaseDatabase database, DataSnapshot snapshot, String currentDate) {
        DatabaseReference myRef = database.getReference("Order");
        myRef.child(currentDate).push().setValue(snapshot.getValue(Order.class));
    }

}
