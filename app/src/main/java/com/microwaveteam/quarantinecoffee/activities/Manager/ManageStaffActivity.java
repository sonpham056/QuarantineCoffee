package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

    ViewCard adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);
        ArrayList list = new ArrayList<>();
        users = new ArrayList<>();

        featureRecycler = findViewById(R.id.recycle_mn_list_staff);
        myRef = FirebaseDatabase.getInstance().getReference("Account");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                adapter = new ViewCard(listFeartures);
                featureRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}