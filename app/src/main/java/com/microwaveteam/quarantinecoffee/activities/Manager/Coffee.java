package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.GridView;

import com.microwaveteam.quarantinecoffee.R;

import java.util.ArrayList;

public class Coffee extends AppCompatActivity {
    GridView gvCoffee;
    ArrayList<Image_Coffee> arrayImage;
    Image_Coffee_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_product_drink_coffe);

        bind();

        adapter = new Image_Coffee_Adapter(this,R.layout.image_coffe,arrayImage);
        gvCoffee.setAdapter(adapter);
    }
    private  void bind(){
        gvCoffee = findViewById(R.id.gridview_image_coffee);
        arrayImage = new ArrayList<>();
        arrayImage.add(new Image_Coffee("Bạc sỉu",R.drawable.image_coffe_bacsiu));
        arrayImage.add(new Image_Coffee("Cà phê đen đá",R.drawable.image_coffe_den_da));
        arrayImage.add(new Image_Coffee("Capuchino",R.drawable.image_coffe_capuchino));
        arrayImage.add(new Image_Coffee("Cà phê sữa đá",R.drawable.image_coffe_sua_da));
        arrayImage.add(new Image_Coffee("Cà phê sữa nóng",R.drawable.image_coffe_sua_nong));
        arrayImage.add(new Image_Coffee("Cà phê Espresso",R.drawable.image_coffe_espresso));
    }
}