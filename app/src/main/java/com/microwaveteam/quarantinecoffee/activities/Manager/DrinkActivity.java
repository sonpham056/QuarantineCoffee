package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.microwaveteam.quarantinecoffee.R;

public class DrinkActivity extends AppCompatActivity {
    Button btnCoffee;
    Button btnMilkTea;
    Button btnSoftDrink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_product_drink);
        bind();
    }
    private  void bind(){
        btnCoffee = findViewById(R.id.btnCoffee);
        btnMilkTea = findViewById(R.id.btnMilkTea);
        btnSoftDrink = findViewById(R.id.btnSoftDrink);

        btnCoffee.setOnClickListener(view -> {
            Intent intent = new Intent(DrinkActivity.this, Coffee.class);
            startActivity(intent);
        });
        btnMilkTea.setOnClickListener(view -> {
            Intent intent = new Intent(DrinkActivity.this,MilkTea.class);
            startActivity(intent);
        });
        btnSoftDrink.setOnClickListener(view -> {
            Intent intent = new Intent(DrinkActivity.this,SoftDrink.class);
            startActivity(intent);
        });
    }
}