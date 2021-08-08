package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.Product;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {
    Button btn_mn_ProductAddEdit;
    Button btn_mn_ProductDelete;
    EditText txt_mn_ProductName,txt_mn_Price,txt_mn_ProductAmount;
    Spinner sp_mn_Category;
    String arr[]={ "Trà sữa", "Cà phê", "Nước ngọt", "Bánh", "Hạt"};
    TextView txt_mn_selection_list;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_product);
        txt_mn_selection_list =(TextView) findViewById(R.id.txt_mn_selection_list);
        bind();
    }
    private void bind(){
        txt_mn_ProductName = findViewById(R.id.txt_mn_ProductName);
        txt_mn_Price = findViewById(R.id.txt_mn_Price);
        txt_mn_ProductAmount = findViewById(R.id.txt_mn_ProductAmount);
        sp_mn_Category = findViewById(R.id.sp_mn_Category);


        btn_mn_ProductAddEdit = findViewById(R.id.btn_mn_ProductAddEdit);
        btn_mn_ProductDelete = findViewById(R.id.btn_mn_ProductDelete);

        btn_mn_ProductAddEdit.setOnClickListener(view ->
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Product");
            Product product = new Product(txt_mn_ProductName.getText().toString(), Long.parseLong(txt_mn_Price.getText().toString())  ,
                    Integer.parseInt(txt_mn_ProductAmount.getText().toString()) , sp_mn_Category.getSelectedItem().toString()

            );
            myRef.child(product.getCategory()).child(product.getProductName()).setValue(product);
        });
        btn_mn_ProductDelete.setOnClickListener(view ->
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Product");

            myRef.child(sp_mn_Category.getSelectedItem().toString()).child(txt_mn_ProductName.getText().toString()).removeValue();

        });



        Spinner spin=(Spinner) findViewById(R.id.sp_mn_Category);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        arr
                );
        //hien thi ds cho spiner
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new MyProcessEvent());

    }
    private class MyProcessEvent implements OnItemSelectedListener{
        //chon 1 item
        public void onItemSelected(AdapterView<?> arg0,
                                   View arg1,
                                   int arg2,
                                   long arg3){
            txt_mn_selection_list.setText(arr[arg2]);
        }
        //neu ko chon gi
        public void onNothingSelected(AdapterView<?> arg0) {
            txt_mn_selection_list.setError("sai!!");
        }
    }

}