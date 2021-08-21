package com.microwaveteam.quarantinecoffee.activities.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.TableRecyclerAdapter;
import com.microwaveteam.quarantinecoffee.R;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {
    private Button btnCreate;
    private RecyclerView recyclerView;
    private TableRecyclerAdapter adapter;
    private ArrayList<String> listTable = new ArrayList<>();

    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_manage_table);

        bind();

    }

    private void bind() {
        btnCreate = findViewById(R.id.btn_mn_create_table);
        recyclerView = findViewById(R.id.recycle_mn_list_table);

        btnCreate.setOnClickListener(view -> {
            Intent intent = new Intent(TableActivity.this, CreateTableActivity.class);
            startActivity(intent);
        });
        adapter = new TableRecyclerAdapter(listTable, TableActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        loadListTable();

    }

    private void loadListTable() {
        db = FirebaseDatabase.getInstance().getReference("Table");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listTable.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String name = data.getKey();
                    listTable.add(name);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
