package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.microwaveteam.quarantinecoffee.R;

import java.util.ArrayList;

public class ShowProductInStatisticalActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_in_statistical);

        listView = findViewById(R.id.listview_mn_show_product_statistical);
        list = (ArrayList<String>) getIntent().getSerializableExtra("products");
        //list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list);
        listView.setAdapter(adapter);
    }
}