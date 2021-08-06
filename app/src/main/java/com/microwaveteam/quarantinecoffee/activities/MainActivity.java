package com.microwaveteam.quarantinecoffee.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.microwaveteam.quarantinecoffee.R;

public class MainActivity extends AppCompatActivity {

    Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Account");
//        User user = new User("Son","1231","Không có quyền biểu quyết","20/20/2000",null);
//        myRef.child(user.UserName).setValue(user);
//        user = new User("Hai","1231","New Waiter","20/20/2000","1");
//        myRef.child(user.UserName).setValue(user);
//
//        user = new User("admin","admin","Admin Of System","06/08/2021","0");
//        myRef.child(user.UserName).setValue(user);
//        user = new User("Thu", "1231", "Thư ba ten đơ", "20/20/2000", "2");
//        myRef.child(user.UserName).setValue(user);
//        Map<String,String> map = new HashMap<String,String>();
//        myRef = database.getReference("Role");
//        map.put("0","Manager");
//        map.put("1","Waiter");
//        map.put("2","Bartender");
//        myRef.setValue(map);




        btnGo = findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(it);
                //TODO:Put some extra
            }
        });
    }
}