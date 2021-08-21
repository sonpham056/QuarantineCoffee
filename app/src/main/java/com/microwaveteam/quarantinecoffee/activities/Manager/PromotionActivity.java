package com.microwaveteam.quarantinecoffee.activities.Manager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.microwaveteam.quarantinecoffee.Helper.ViewPromotion;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.dao.PromotionDAO;
import com.microwaveteam.quarantinecoffee.models.Promotion;
import com.microwaveteam.quarantinecoffee.serviceclasses.MyAlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PromotionActivity extends AppCompatActivity {

    EditText txtPromotionName;
    EditText txtPromotion;
    Button btnAdd;
    Button btnEdit;
    EditText txtStart;
    EditText txtEnd;

    RecyclerView recyclerViewPromotion;
    ArrayList<Promotion> listPromotion;
    ViewPromotion adapter;

    private static DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_promotion);

        showPromotion();

        bind();
    }

    @SuppressLint("WrongViewCast")
    private void bind(){
        try {
            txtPromotionName = findViewById(R.id.txt_mn_PromotionName);
            txtPromotion = findViewById(R.id.txt_mn_Promotion);
            txtStart = findViewById(R.id.txt_mn_startPromotion);
            txtEnd = findViewById(R.id.txt_mn_endPromotion);

            btnAdd = findViewById(R.id.btn_mn_PromotionAdd);
            btnEdit = findViewById(R.id.btn_mn_PromotionEdit);

            recyclerViewPromotion = findViewById(R.id.recycle_mn_listPromotion);

            txtStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startDate();
                }
            });

            txtEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    endDate();
                }
            });

            btnAdd.setOnClickListener(view -> {
                btnAddClicked();
            });

            btnEdit.setOnClickListener(view -> {
                btnEditClicked();
            });



        } catch (Exception e) {
            e.printStackTrace();
            MyAlertDialog.alert(e.getMessage(), this);
        }
    }

    private void startDate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                //i:year    i1:month    i2:day
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(i,i1,i2);
                txtStart.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void endDate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                //i:year    i1:month    i2:day
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(i,i1,i2);
                txtEnd.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void btnAddClicked() {
        try {
            if(!checkValid()){
                return;
            }else {
                Promotion promotion = new Promotion(txtPromotionName.getText().toString(),
                        Integer.parseInt(txtPromotion.getText().toString()),
                        txtStart.getText().toString(), txtEnd.getText().toString());

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

        listPromotion = new ArrayList<>();

        db = FirebaseDatabase.getInstance().getReference("Promotion");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Promotion> listFeatures = new ArrayList<>();

                recyclerViewPromotion.setHasFixedSize(true);
                recyclerViewPromotion.setLayoutManager(
                        new LinearLayoutManager(PromotionActivity
                                .this,LinearLayoutManager
                                .VERTICAL,false));

                for(DataSnapshot ds: snapshot.getChildren()){
                    Promotion data = ds.getValue(Promotion.class);
                    listPromotion.add(data);
                    String promotionNameDB = data.getPromotionName();
                    int promotionDB = data.getPromotion();
                    listFeatures.add(new Promotion(promotionNameDB, promotionDB));
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