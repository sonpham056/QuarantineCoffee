package com.microwaveteam.quarantinecoffee.activities.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.microwaveteam.quarantinecoffee.R;

public class ManagerActivity extends AppCompatActivity {

    Button btnManageProduct, btnManageStaff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        mapping();
        clicking();

    }

    private void clicking() {
        btnManageStaff.setOnClickListener(view -> {
            Intent intent = new Intent(ManagerActivity.this, ManageStaffActivity.class);
            startActivity(intent);
        });

        btnManageProduct.setOnClickListener(view -> {
            Intent intent = new Intent(ManagerActivity.this, ManageProductActivity.class);
            startActivity(intent);
        });
    }

    private void mapping() {
        btnManageProduct = findViewById(R.id.btnManageProduct);
        btnManageStaff = findViewById(R.id.btnManageEmployee);
    }
}