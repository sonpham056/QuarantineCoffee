package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.microwaveteam.quarantinecoffee.R;

public class ProductActivity extends AppCompatActivity {
    Button btnDrink;
    Button btnFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_product);

        bind();
    }
    private void bind(){
        btnDrink = findViewById(R.id.btnDrink);
        btnFood = findViewById(R.id.btnFood);

        btnDrink.setOnClickListener(view -> {
            Intent intent = new Intent(ProductActivity.this,DrinkActivity.class);
            startActivity(intent);
        });
        btnFood.setOnClickListener(view -> {
            Intent intent = new Intent(ProductActivity.this, FoodActivity.class);
            startActivity(intent);
        });
    }
}