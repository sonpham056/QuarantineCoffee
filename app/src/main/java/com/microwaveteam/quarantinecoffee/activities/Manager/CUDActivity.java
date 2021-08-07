package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.microwaveteam.quarantinecoffee.R;

public class CUDActivity extends AppCompatActivity {
    Button btnCreate;
    Button btnUpdate;
    Button btnDelete;
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

        });
    }
}