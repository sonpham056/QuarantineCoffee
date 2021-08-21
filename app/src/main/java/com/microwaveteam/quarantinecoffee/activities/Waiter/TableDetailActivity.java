package com.microwaveteam.quarantinecoffee.activities.Waiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.OrderRecyclerAdapter;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.Bill;
import com.microwaveteam.quarantinecoffee.models.Order;
import com.microwaveteam.quarantinecoffee.serviceclasses.MyAlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TableDetailActivity extends AppCompatActivity {

    public final static int RESULT_CODE = 200;
    private String table;
    private String waiterName;
    private ArrayList<Order> listOrder;
    private ArrayList<Order> listOrderAll;
    private OrderRecyclerAdapter adapter;

    DatabaseReference db;
    DatabaseReference db2;

    SharedPreferences prefs;

    Button btnNewly;
    Button btnAll;
    Button btnConfirm;
    Button btnBill;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("My app", MODE_PRIVATE);

        setContentView(R.layout.w_activity_table_detail);
        Intent intent = getIntent();
        table = intent.getStringExtra("table");
        waiterName = intent.getStringExtra("waiter");
        listOrder = (ArrayList<Order>) intent.getSerializableExtra("list");
        listOrderAll = new ArrayList<>();

        btnAll = findViewById(R.id.btn_w_all_order_table_detail);
        btnConfirm = findViewById(R.id.btn_w_confirm_table_detail);
        btnNewly = findViewById(R.id.btn_w_newlyadded_table_detail);
        btnBill = findViewById(R.id.btn_w_bill_table_detail);
        recyclerView = findViewById(R.id.recycler_w_list_order);


        adapter = new OrderRecyclerAdapter(this, listOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        //Toast.makeText(this, listOrder.size() + "", Toast.LENGTH_SHORT).show();

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        db = FirebaseDatabase.getInstance().getReference("OrderQueue").child(currentDate);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Order order = data.getValue(Order.class);
                        if (!order.isBill() && order.getTable().compareTo(table) == 0) {
                            listOrderAll.add(order);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnConfirm.setOnClickListener(view -> {
            if (listOrder.size() < 1) {
                Toast.makeText(TableDetailActivity.this, "Nothing to send!", Toast.LENGTH_SHORT).show();
            } else {
                db = FirebaseDatabase.getInstance().getReference("OrderQueue").child(currentDate);
                for (Order o : listOrder) {
                    String key = db.push().getKey();
                    o.setKey(key);
                    db.child(key).setValue(o);
                }


                listOrder.clear();
                onBackPressed();
            }
        });

        btnAll.setOnClickListener(view -> {
            adapter.setData(listOrderAll);
            adapter.isClickable = false;
            btnBill.setVisibility(View.VISIBLE);
            btnConfirm.setEnabled(false);
        });

        btnNewly.setOnClickListener(view -> {
            adapter.setData(listOrder);
            adapter.isClickable = true;
            btnBill.setVisibility(View.INVISIBLE);
            btnConfirm.setEnabled(true);
        });

        btnBill.setOnClickListener(view -> {
            if (listOrderAll.size() < 1) {
                Toast.makeText(TableDetailActivity.this, "Nothing here to make bill", Toast.LENGTH_SHORT).show();
            } else {
                String currentDateTime = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa", Locale.getDefault()).format(new Date());
                db = FirebaseDatabase.getInstance().getReference("Bill").child(currentDate);
                long sum = 0;
                Bill bill = new Bill();
                bill.setDate(currentDateTime);
                for (Order o : listOrderAll) {
                    sum += o.getAmount() * o.getPrice();
                }
                bill.setSum(sum);
                bill.setWaiterName(waiterName);

                String str = db.push().getKey();
                db.child(str).child("Bill").setValue(bill);
                for (Order o : listOrderAll) {
                    db.child(str).child("Orders").child(o.getProductName() + " " + o.getProductType()).setValue(o);
                }

                db = FirebaseDatabase.getInstance().getReference("OrderQueue").child(currentDate);
                for (Order o : listOrderAll) {
                    o.setBill(true);
                    db.child(o.getKey()).setValue(o);
                }
                listOrderAll.clear();
                if (adapter != null) {
                    adapter.setData(listOrderAll);
                }

                MyAlertDialog.alert("Bill sum: " + bill.getSum() + "$", TableDetailActivity.this);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("list", listOrder);
        setResult(RESULT_CODE, data);
        super.onBackPressed();
    }

}