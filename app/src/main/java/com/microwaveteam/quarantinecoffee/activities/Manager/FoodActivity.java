package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.microwaveteam.quarantinecoffee.R;

public class FoodActivity extends AppCompatActivity {
    Button  btnCake;
    Button  btnSeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_product_food);

        bind();
    }
    private  void bind(){
        btnCake = findViewById(R.id.btnCake);
        btnSeed = findViewById(R.id.btnSeed);


        btnCake.setOnClickListener(view -> {
            Intent intent = new Intent(FoodActivity.this,Cake.class);
            startActivity(intent);
        });
        btnSeed.setOnClickListener(view -> {
            Intent intent = new Intent(FoodActivity.this,Seed.class);
            startActivity(intent);
        });

    }
}