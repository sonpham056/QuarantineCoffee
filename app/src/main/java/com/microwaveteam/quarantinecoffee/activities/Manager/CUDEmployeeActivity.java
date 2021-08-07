package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.microwaveteam.quarantinecoffee.R;

public class CUDEmployeeActivity extends AppCompatActivity {
    Button btnCreate;
    Button btnUpdate;
    Button btnDelete;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_cudactivity);
        bind();
    }

    private void bind() {
        btnCreate = findViewById(R.id.btnCreateEmployee);
        btnUpdate = findViewById(R.id.btnUpdateEmployee);
        btnDelete = findViewById(R.id.btnDeleteEmployee);

        btnCreate.setOnClickListener(view -> {
            Intent intent = new Intent(CUDEmployeeActivity.this, CreateEmployeeActivity.class);
            startActivity(intent);
        });

        btnUpdate.setOnClickListener(view -> {
            //TODO: put update activity
        });

        btnDelete.setOnClickListener(view -> {
            //TODO: put delete activity
        });
    }
}