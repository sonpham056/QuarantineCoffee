package com.microwaveteam.quarantinecoffee.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.User;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Account");

        User user = new
                User("Son","1231","Sơn Batenđơ"
                ,"20/20/2000","2","Yes");
        myRef.child(user.getUserName()).setValue(user);
        user = new User("Hai","1231","New Waiter","20/20/2000","1","Yes");
        myRef.child(user.getUserName()).setValue(user);

        user = new User("admin","admin"
                ,"Admin Of System","06/08/2021","0","Yes");
        myRef.child(user.getUserName()).setValue(user);
        user = new User("Thu", "1231"
                , "Thư ba ten đơ", "20/20/2000", "2","No");
        myRef.child(user.getUserName()).setValue(user);

        user  = new User("Phong","1231","K có quyền gì cả :v"
                ,"20/20/2000",null,"Yes");
        myRef.child(user.getUserName()).setValue(user);

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