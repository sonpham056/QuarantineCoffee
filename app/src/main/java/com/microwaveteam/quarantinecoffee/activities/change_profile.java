package com.microwaveteam.quarantinecoffee.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.User;

public class change_profile extends AppCompatActivity {


    Context context;
    DatabaseReference myRef;
    Button btnChange;
    EditText txtUserName, txtOldPass, txtNewPass, txtConfirmNewPass;
    String userNameLogging, passDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        binding();
        Toast.makeText(this, userNameLogging, Toast.LENGTH_LONG).show();
        myRef.child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                passDB = snapshot.getValue(String.class);
                System.out.println("-------------------------");
                System.out.println("snapshot = " + snapshot.getRef().getPath());
                System.out.println(passDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnChange.setOnClickListener(view -> {
            String userNameEntered = txtUserName.getText().toString();
            String oldPassEntered = txtOldPass.getText().toString();
            String newPassEntered = txtNewPass.getText().toString();


            if(!validate()){

            }else{

                if (!passDB.equals(oldPassEntered)){
                    txtOldPass.setError("Wrong Pass");
                }else if(!userNameEntered.equals(userNameLogging)){
                    txtUserName.setError("Wrong UserName");
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are u sure bout this?");
                    builder.setPositiveButton("OK", (dialogInterface, i)
                            -> myRef.child("password").setValue(newPassEntered, (error, ref)
                            -> Toast.makeText(context, "Set value success", Toast.LENGTH_LONG).show()));
                    builder.setNegativeButton("No", (dialogInterface, i) -> {
                        return;
                    });
                    builder.show();
                }
            }
        });

    }

    private boolean validate() {
        if(!txtConfirmNewPass.getText().toString().equals(txtNewPass.getText().toString())){
            txtConfirmNewPass.setError("Doesn't Match");
            txtNewPass.setError("Doesn't Match");
            return false;
        }
        if(txtUserName.getText().toString().isEmpty()){
            txtUserName.setError("Field cannot be empty");
            return false;
        }if(txtOldPass.getText().toString().isEmpty()){
            txtOldPass.setError("Field cannot be empty");
            return false;
        }if(txtNewPass.getText().toString().isEmpty()){
            txtNewPass.setError("Field cannot be empty");
            return false;
        }if(txtConfirmNewPass.getText().toString().isEmpty()){
            txtConfirmNewPass.setError("Field cannot be empty");
            return false;
        }
        return true;
    }

    private void binding() {
        context = this;
        userNameLogging = getIntent().getStringExtra("userNameInChangeProfile");
        myRef = FirebaseDatabase.getInstance().getReference("Account").child(userNameLogging);
        btnChange = findViewById(R.id.btn_change);
        txtConfirmNewPass = findViewById(R.id.txt_change_confirm_pass);
        txtNewPass = findViewById(R.id.txt_change_new_pass);
        txtOldPass = findViewById(R.id.txt_change_old_pass);
        txtUserName = findViewById(R.id.txt_change_username);
    }
}