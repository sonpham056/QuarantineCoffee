package com.microwaveteam.quarantinecoffee.activities.Waiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.Bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BillHistoryActivity extends AppCompatActivity {

    ListView listView;
    List<String> list;
    ArrayAdapter<String> adapter;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_activity_bill_history);

        String currentDateTime = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        db = FirebaseDatabase.getInstance().getReference("Bill");


        listView = findViewById(R.id.listView_w_bill_history);
        list = new ArrayList<>();

        db.child(currentDateTime).orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        for (DataSnapshot dataChild : data.getChildren()) {
                            if (dataChild.getKey().compareTo("Bill") == 0) {
                                Bill bill = dataChild.getValue(Bill.class);
                                Log.i("test", bill.getWaiterName());
                                String s = bill.getDate() + "  |  " + bill.getSum() + "  |  " + bill.getWaiterName();
                                list.add(s);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        listView.setAdapter(adapter);
    }
}