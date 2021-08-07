package com.microwaveteam.quarantinecoffee.activities.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.microwaveteam.quarantinecoffee.R;

public class ManagerActivity extends AppCompatActivity {

    Button btnManageProduct;
    Button btnManageEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_manager);

        bind();

        btnManageProduct.setOnClickListener(view -> {
            Intent intent = new Intent(ManagerActivity.this, ManageProductActivity.class);
            startActivity(intent);
        });

        btnManageEmployee.setOnClickListener(view -> {
            Intent intent = new Intent(ManagerActivity.this, ManageEmployeeActivity.class);
            startActivity(intent);
        });
    }

    private void bind(){
        btnManageProduct = findViewById(R.id.btnManageProduct);
        btnManageEmployee = findViewById(R.id.btnManageEmployee);
    }
}