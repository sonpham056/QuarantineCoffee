package com.microwaveteam.quarantinecoffee.activities.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.microwaveteam.quarantinecoffee.R;
public class ManageProductActivity extends AppCompatActivity {

    Button btnPromotion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);
        btnPromotion = findViewById(R.id.btnPromotion);

        btnPromotion.setOnClickListener(view -> {
            Intent intent = new Intent(ManageProductActivity.this, PromotionActivity.class);
            startActivity(intent);
        });
    }
}