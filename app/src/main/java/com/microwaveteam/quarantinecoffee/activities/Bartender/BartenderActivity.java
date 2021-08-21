package com.microwaveteam.quarantinecoffee.activities.Bartender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.Order;

public class BartenderActivity extends AppCompatActivity {

    Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_bartender);

        btnGo = findViewById(R.id.btnBartenderGo);
        btnGo.setOnClickListener(view -> {
            Intent it = new Intent(BartenderActivity.this, Bar_queueOrder.class);
            startActivity(it);
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Table");


        Query check = myRef.orderByChild("isAccepted").equalTo(false);

        //System.out.println("----------" + check.getPath().toString() + "-------------");
        check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!BartenderActivity.this.isFinishing()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Log.e("line55baracti", data.getKey());
                        if (data.child("isAccepted").getValue().toString() == "false") {
                            Order order = data.getValue(Order.class);
                            Log.e("line55baracti - getvalue- false", data.getValue().toString());

                            System.out.println("oder-----" + order.getFinish() + "=-------------");
                            System.out.println("oder-----" + order.getAmount() + "=-------------");
                            System.out.println("oder-----" + order.getProductName() + "=-------------");
                            System.out.println("oder-----" + order.getClass() + "=-------------");
                            //BartenderDAO.showNotiAndRemoveQueue(order,data,database,BartenderActivity.this);
                        }


                    }
                } else {
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
