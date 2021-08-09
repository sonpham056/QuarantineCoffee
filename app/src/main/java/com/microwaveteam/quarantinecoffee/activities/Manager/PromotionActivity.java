package com.microwaveteam.quarantinecoffee.activities.Manager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.FeatureHelper;
import com.microwaveteam.quarantinecoffee.Helper.ViewPromotion;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.dao.PromotionDAO;
import com.microwaveteam.quarantinecoffee.models.Promotion;
import com.microwaveteam.quarantinecoffee.serviceclasses.MyAlertDialog;

import java.util.ArrayList;

public class PromotionActivity extends AppCompatActivity {

    EditText txtPromotionName;
    EditText txtPromotion;
    Button btnAdd;
    Button btnEdit;
    Button btnDelete;

    RecyclerView recyclerViewPromotion;
    ArrayList<Promotion> listPromotion;
    ViewPromotion adapter;

    private static DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_promotion);

        bind();
    }

    private void bind(){
        try {
            txtPromotionName = findViewById(R.id.txt_mn_PromotionName);
            txtPromotion = findViewById(R.id.txt_mn_Promotion);

            btnAdd = findViewById(R.id.btn_mn_PromotionAdd);
            btnEdit = findViewById(R.id.btn_mn_PromotionEdit);

            recyclerViewPromotion = findViewById(R.id.recycle_mn_listPromotion);
            listPromotion = new ArrayList<>();

            showPromotion();

            btnAdd.setOnClickListener(view -> {
                btnAddClicked();
            });

            btnEdit.setOnClickListener(view -> {
                btnEditClicked();
            });

            btnDelete.setOnClickListener(view -> {
                btnDeleteClicked();
            });
        } catch (Exception e) {
            e.printStackTrace();
            MyAlertDialog.alert(e.getMessage(), this);
        }
    }

    private void btnAddClicked() {
        try {
            if(!checkValid()){
                return;
            }else {
                Promotion promotion = new Promotion(txtPromotionName.getText().toString(),
                        Integer.parseInt(txtPromotion.getText().toString()));

                PromotionDAO.addPromotion(promotion, this);
                showPromotion();

            }
        } catch (Exception e){
            e.printStackTrace();
            MyAlertDialog.alert(e.getMessage(), this);
        }
    }

    private void btnEditClicked(){
        try {
            if(!checkValid()){
                return;
            }else {
                Promotion promotion = new Promotion(txtPromotionName.getText().toString(),
                        Integer.parseInt(txtPromotion.getText().toString()));

                PromotionDAO.addPromotion(promotion, this);
                showPromotion();
            }
        } catch (Exception e){
            e.printStackTrace();
            MyAlertDialog.alert(e.getMessage(), this);
        }
    }

    private void btnDeleteClicked(){
        showPromotion();
    }

    private boolean checkValid() {
        try {
            if (txtPromotionName.getText().toString().isEmpty() || txtPromotion.getText().toString().isEmpty()) {
                throw new Exception("Please provide all the information");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyAlertDialog.alert(e.getMessage(), this);
            return false;
        }
        return true;
    }

    private void showPromotion(){
        ArrayList list = new ArrayList<>();
        listPromotion = new ArrayList<>();

        recyclerViewPromotion = findViewById(R.id.recycle_mn_listPromotion);
        db = FirebaseDatabase.getInstance().getReference("Promotion");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<FeatureHelper> listFeatures = new ArrayList<>();
                recyclerViewPromotion.setHasFixedSize(true);
                recyclerViewPromotion.setLayoutManager(
                        new LinearLayoutManager(PromotionActivity
                                .this,LinearLayoutManager
                                .VERTICAL,false));

                for(DataSnapshot ds: snapshot.getChildren()){
                    Promotion promotion = ds.getValue(Promotion.class);
                    listPromotion.add(promotion);
                    String promotionName = promotion.getPromotionName();
                    listFeatures.add(new FeatureHelper(R.drawable.img_add,
                            R.drawable.img_del,
                            promotionName));
                }
                adapter = new ViewPromotion(listFeatures, PromotionActivity.this);
                recyclerViewPromotion.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}