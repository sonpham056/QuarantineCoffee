package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.dao.UserDAO;
import com.microwaveteam.quarantinecoffee.models.User;
import com.microwaveteam.quarantinecoffee.serviceclasses.MyAlertDialog;

public class UpdateEmployeeActivity extends AppCompatActivity {
    private static DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    EditText txtUserName;
    EditText txtRole;
    EditText txtFullName;
    Button btnConfirm, btnBack;
    CheckBox ckbGender;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_update_employee);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");

        db.child("Account").child(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                bind();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void bind() {
        try {
            txtUserName = findViewById(R.id.txt_mn_username_update);
            txtRole = findViewById(R.id.txt_mn_role_update);
            txtFullName = findViewById(R.id.txt_mn_fullname_update);

            btnConfirm = findViewById(R.id.btn_mn_confirm_update);
            btnBack = findViewById(R.id.btn_mn_back_update);
            ckbGender = findViewById(R.id.ckb_mn_check_update);

            txtUserName.setText(user.getUserName());
            txtFullName.setText(user.getFullName());
            txtRole.setText(user.getRole());
            ckbGender.setChecked(true ? user.getIsMale().equals("Yes") : false);
            btnConfirm.setOnClickListener(view -> {
                btnConfirmClicked();
            });

            btnBack.setOnClickListener(view -> {
                btnBackClicked();
            });
        } catch (Exception e) {
            e.printStackTrace();
            MyAlertDialog.alert(e.getMessage(), this);
        }

    }

    private void btnBackClicked() {
        onBackPressed();
    }

    private void btnConfirmClicked() {
        try {
            checkValid();
            checkRole();
            String gender = "";
            if(ckbGender.isChecked()){
                gender = "Yes";
            } else {
                gender = "No";
            }

            User user = new User(txtUserName.getText().toString(),
                    this.user.getPassword()
                    ,txtFullName.getText().toString()
                    ,txtRole.getText().toString(),
                    gender);

            UserDAO.addUser(user, this);
            onBackPressed();
        } catch (Exception e){
            e.printStackTrace();
            MyAlertDialog.alert(e.getMessage(), this);
        }
    }

    private void checkValid() {
        try {
            if (txtFullName.getText().toString().isEmpty() ||
                    txtRole.getText().toString().isEmpty() ||
                    txtUserName.getText().toString().isEmpty()) {
                throw new Exception("Please provide all the information (except password)");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyAlertDialog.alert(e.getMessage(), this);
        }
    }

    private void checkRole() throws  Exception{
        if (txtRole.getText().toString().compareTo("0") != 0 && txtRole.getText().toString().compareTo("1") != 0 && txtRole.getText().toString().compareTo("2") != 0) {
            throw new Exception("Role can only be 0, 1 or 2!");
        }
    }
}