package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.microwaveteam.quarantinecoffee.R;

public class ManageEmployeeActivity extends AppCompatActivity {

    Button btnCUDEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_manage_employee);

        btnCUDEmployee = findViewById(R.id.btnCUDEmployee);
        btnCUDEmployee.setOnClickListener(view -> {
            Intent intent = new Intent(ManageEmployeeActivity.this, CUDEmployeeActivity.class);
            startActivity(intent);
        });
    }
}