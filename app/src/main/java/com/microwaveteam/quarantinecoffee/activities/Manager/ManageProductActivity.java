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
        setContentView(R.layout.mn_activity_manage_product);
        bind();
        clicking();
    }

    private void clicking() {
        btnPromotion.setOnClickListener(view -> {
            Intent intent = new Intent(ManageProductActivity.this, PromotionActivity.class);
            startActivity(intent);
        });
    }

    private  void bind(){
        btnPromotion = findViewById(R.id.btnPromotion);
    }
}