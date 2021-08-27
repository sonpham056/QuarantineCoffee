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
            final String userNameEntered = txtUserName.getText().toString();
            final String oldPassEntered = txtOldPass.getText().toString();
            final String newPassEntered = txtNewPass.getText().toString();
            final String confirmEntered = txtConfirmNewPass.getText().toString();

            changing(passDB, userNameEntered, oldPassEntered, newPassEntered, confirmEntered);
        });

    }

    private void changing(String passDB, String userNameEntered, String oldPassEntered, String newPassEntered, String confirmEntered) {
        if (passDB == oldPassEntered && validate(userNameEntered, newPassEntered, confirmEntered) == true) {
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
        } else {
            Toast.makeText(context, ">>>>>>>>>>>?????????", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validate(String userNameEntered, String newPassEntered, String confirmEntered) {
        if (userNameEntered != userNameLogging) {
            txtUserName.setError("???");
            txtUserName.setFocusable(true);
            return false;
        } else if (txtOldPass.getText().toString().isEmpty()) {
            txtOldPass.setError("Can't null");
            txtOldPass.setFocusable(true);
            return false;
        } else if (txtNewPass.getText().toString().isEmpty()) {
            txtNewPass.setError("Can't null");
            txtNewPass.setFocusable(true);
            return false;
        } else if (txtUserName.getText().toString().isEmpty()) {
            txtUserName.setError("Can't null");
            txtUserName.setFocusable(true);
            return false;
        } else if (txtConfirmNewPass.getText().toString().isEmpty()) {
            txtConfirmNewPass.setError("Can't null");
            txtConfirmNewPass.setFocusable(true);
            return false;
        } else if (newPassEntered != confirmEntered) {
            txtConfirmNewPass.setError("Doesn't match");
            txtConfirmNewPass.setFocusable(true);
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