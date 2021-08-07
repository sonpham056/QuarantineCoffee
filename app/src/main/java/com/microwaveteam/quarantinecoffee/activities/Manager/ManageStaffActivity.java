package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.FeatureHelper;
import com.microwaveteam.quarantinecoffee.Helper.ViewCard;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.User;

import java.util.ArrayList;
import java.util.List;

public class ManageStaffActivity extends AppCompatActivity {

    RecyclerView featureRecycler;
    DatabaseReference myRef;
    List<User> users;
    Button btnCreate;

    static Context context;

    ViewCard adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);
        ArrayList list = new ArrayList<>();
        users = new ArrayList<>();

        btnCreate = findViewById(R.id.btn_mn_createuser_mnstaff);
        featureRecycler = findViewById(R.id.recycle_mn_list_staff);
        myRef = FirebaseDatabase.getInstance().getReference("Account");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<FeatureHelper> listFeartures = new ArrayList<>();
                
                featureRecycler.setHasFixedSize(true);
                featureRecycler.setLayoutManager(
                        new LinearLayoutManager(ManageStaffActivity
                                .this,LinearLayoutManager
                                .VERTICAL,false));

                for(DataSnapshot ds: snapshot.getChildren()){
                    User data = ds.getValue(User.class);
                    //helpersClasses.add(data);
                    users.add(data);
                    String userDB = data.getUserName();
                    if(data.getIsMale().equals("Yes")){
                        listFeartures.add(new FeatureHelper(R.drawable.non_avt
                                ,R.drawable.img_edit
                                ,R.drawable.img_del
                                ,userDB));
                    }else if(data.getIsMale().equals("No")){
                        listFeartures.add(new FeatureHelper(R.drawable.avt_female
                                ,R.drawable.img_edit
                                ,R.drawable.img_del
                                ,userDB));
                    }



                }
                adapter = new ViewCard(listFeartures, ManageStaffActivity.this);
                featureRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnCreate.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateEmployeeActivity.class);
            startActivity(intent);
        });
    }

    public void startUpdateActivity(String userName) {
        Intent intent = new Intent(context, UpdateEmployeeActivity.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
}