package com.microwaveteam.quarantinecoffee.activities.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.microwaveteam.quarantinecoffee.Helper.NavigationDrawer;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.Waiter.WaiterActivity;

public class ManagerActivity extends AppCompatActivity {

    Button btnManageProduct, btnManageStaff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_manager);

        bind();
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

    private void bind(){
        btnManageProduct = findViewById(R.id.btnManageProduct);
        btnManageStaff = findViewById(R.id.btnManageEmployee);
    }
}