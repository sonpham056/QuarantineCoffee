package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.microwaveteam.quarantinecoffee.R;

public class ManageEmployeeActivity extends AppCompatActivity {

    Button btnCUDEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employee);
    }
}