package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.ProductTypeRecyclerAdapter;
import com.microwaveteam.quarantinecoffee.R;

import java.util.ArrayList;

public class ProductTypeActivity extends AppCompatActivity {
    private Button btnCreate;
    private RecyclerView recyclerView;
    private ProductTypeRecyclerAdapter adapter;
    private ArrayList<String> listProduct = new ArrayList<>();

    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_manage_product_type);

        bind();

    }

    private void bind() {
        btnCreate = findViewById(R.id.btn_mn_create_productType);
        recyclerView = findViewById(R.id.recycle_mn_list_product);

        btnCreate.setOnClickListener(view -> {
            Intent intent = new Intent(ProductTypeActivity.this, CreateProductTypeActivity.class);
            startActivity(intent);
        });

        loadListProductType();

    }

    private void loadListProductType() {
        db = FirebaseDatabase.getInstance().getReference("ProductType");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listProduct.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String name = data.getValue(String.class);
                    listProduct.add(name);
                }

                adapter = new ProductTypeRecyclerAdapter(listProduct, ProductTypeActivity.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}