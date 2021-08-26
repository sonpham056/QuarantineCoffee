package com.microwaveteam.quarantinecoffee.activities.Manager;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.ViewProductAdapter;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.Product;
import com.microwaveteam.quarantinecoffee.serviceclasses.MyAlertDialog;

import java.util.ArrayList;

public class ListProductActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Product> listProducts;
    ArrayList<String> listType;
    DatabaseReference db;
    ViewProductAdapter adapter;
    String promotion;

    public ListProductActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_list_product);
        promotion = getIntent().getStringExtra("textName");

        bind();
    }

    public void bind(){
        try{
            recyclerView = findViewById(R.id.recycler_mn_Products);

            showProducts();
        } catch (Exception e){
            e.printStackTrace();
            MyAlertDialog.alert(e.getMessage(), this);
        }
    }

    private void showProducts(){
        listProducts = new ArrayList<>();
        listType = new ArrayList<>();

        db = FirebaseDatabase.getInstance().getReference("Product");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listType.clear();
                listProducts.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String type = data.getKey();
                        listType.add(type);
                        Log.i("line105waiterfrag", type);
                        for (DataSnapshot data2 : data.getChildren()) {
                            Product product = data2.getValue(Product.class);
                            listProducts.add(product);
                        }
                    }
                    Log.i("line124waiterfrag", listProducts.size() + "");
                    adapter = new ViewProductAdapter(ListProductActivity.this, listProducts, promotion);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new ViewProductAdapter(ListProductActivity.this, listProducts, promotion);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}