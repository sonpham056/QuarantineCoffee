package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.dao.UserDAO;
import com.microwaveteam.quarantinecoffee.models.User;
import com.microwaveteam.quarantinecoffee.serviceclasses.MyAlertDialog;

public class CreateEmployeeActivity extends AppCompatActivity {

    EditText txtUserName;
    EditText txtPassword;
    EditText txtConfirmPassword;
    EditText txtRole;
    EditText txtFullName;
    Button btnConfirm, btnBack;
    CheckBox ckbGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_create_employee);
        bind();
    }

    private void bind() {
        try {
            txtUserName = findViewById(R.id.txt_mn_username_update);
            txtPassword = findViewById(R.id.txt_mn_password_create);
            txtConfirmPassword = findViewById(R.id.txt_mn_confirmpass_create);
            txtRole = findViewById(R.id.txt_mn_role_update);
            txtFullName = findViewById(R.id.txt_mn_fullname_update);

            btnConfirm = findViewById(R.id.btn_mn_confirm_update);
            btnBack = findViewById(R.id.btn_mn_back_update);
            ckbGender = findViewById(R.id.ckb_mn_check_update);


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

    private void btnConfirmClicked() {
        try {
            if(!checkValid()){
                return;
            }else {
                checkRole();
                String gender = "";
                if (ckbGender.isChecked()) {
                    gender = "Yes";
                } else {
                    gender = "No";
                }

                User user = new User(txtUserName.getText().toString()
                        , txtPassword.getText().toString()
                        , txtFullName.getText().toString()
                        , txtRole.getText().toString(), gender);

                UserDAO.addUser(user, this);
                onBackPressed();
            }
        } catch (Exception e){
            e.printStackTrace();
            MyAlertDialog.alert(e.getMessage(), this);
        }
    }

    private void btnBackClicked() {
        try {
            onBackPressed();
        } catch (Exception e){
            e.printStackTrace();
            MyAlertDialog.alert(e.getMessage(), this);
        }
    }
    private boolean checkValid() {
        try {
            if (txtConfirmPassword.getText().toString().isEmpty() ||
                    txtFullName.getText().toString().isEmpty() ||
                    txtPassword.getText().toString().isEmpty() ||
                    txtRole.getText().toString().isEmpty() ||
                    txtUserName.getText().toString().isEmpty()) {
                throw new Exception("Please provide all the information");

            }
            if(!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())){
                txtConfirmPassword.setError("Confirm doesn't match??");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyAlertDialog.alert(e.getMessage(), this);
            return false;
        }
        return true;
    }

    private void checkRole() throws  Exception{
        if (txtRole.getText().toString().compareTo("0") != 0 && txtRole.getText().toString().compareTo("1") != 0 && txtRole.getText().toString().compareTo("2") != 0) {
            throw new Exception("Role can only be 0, 1 or 2!");
        }
    }
}