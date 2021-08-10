package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microwaveteam.quarantinecoffee.R;

public class CreateProductTypeActivity extends AppCompatActivity {

    private Button btnCreate;
    private EditText txtName;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_create_product_type);
        bind();
    }

    private void bind() {
        btnCreate = findViewById(R.id.btn_mn_create_productType_to_firebase);
        txtName = findViewById(R.id.txt_mn_create_productType);

        btnCreate.setOnClickListener(view -> {
            if (!txtName.getText().toString().isEmpty()) {
                db = FirebaseDatabase.getInstance().getReference("ProductType");
                db.child(txtName.getText().toString()).setValue(txtName.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateProductTypeActivity.this, "Create succeed", Toast.LENGTH_LONG).show();
                        CreateProductTypeActivity.this.onBackPressed();
                    }
                });
            } else {
                Toast.makeText(CreateProductTypeActivity.this, "Please fill in the field name!", Toast.LENGTH_LONG);
            }

        });
    }
}