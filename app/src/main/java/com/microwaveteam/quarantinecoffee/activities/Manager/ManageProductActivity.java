package com.microwaveteam.quarantinecoffee.activities.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.microwaveteam.quarantinecoffee.R;
public class ManageProductActivity extends AppCompatActivity {
    Button btnProduct;
    Button btnPromotion;
    Button btnProductType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_manage_product);

        bind();
    }
    private void bind(){
        btnProduct = findViewById(R.id.btnProduct);
        btnPromotion = findViewById(R.id.btnPromotion);
        btnProductType = findViewById(R.id.btn_mn_productType_mn_product);

        btnProduct.setOnClickListener(view -> {
            Intent intent = new Intent(ManageProductActivity.this,ProductActivity.class);
            startActivity(intent);
        });
        clicking();

        btnProductType.setOnClickListener(view -> {
            Intent intent = new Intent(ManageProductActivity.this, ProductTypeActivity.class);
            startActivity(intent);
        });
    }

    private void clicking() {
        btnPromotion.setOnClickListener(view -> {
            Intent intent = new Intent(ManageProductActivity.this, PromotionActivity.class);
            startActivity(intent);
        });
    }
}