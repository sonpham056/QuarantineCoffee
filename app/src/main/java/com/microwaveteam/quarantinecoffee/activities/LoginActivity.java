package com.microwaveteam.quarantinecoffee.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.Bartender.BartenderActivity;
import com.microwaveteam.quarantinecoffee.activities.Manager.ManagerActivity;
import com.microwaveteam.quarantinecoffee.activities.Waiter.WaiterActivity;
import com.microwaveteam.quarantinecoffee.models.LoginHistory;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtUserName, txtPwd;
    DatabaseReference myRef;
    LoginHistory log;
    Switch swLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapping();

        if (loginIfAlreadyLogged()) {
            txtUserName.setText(setUserString());
            txtPwd.setText(setPassString());
            swLogin.setChecked(true);
            btnLoginClicked();
            //Log.e("LoginActivity", "entered");
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLoginClicked();
            }
        });

/*        swLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences prefs;
                SharedPreferences.Editor editor;

                prefs = getSharedPreferences("My app", MODE_PRIVATE);
                editor = prefs.edit();
                editor.putString("isAuto", swLogin.isChecked() ? "true" : "false");
                editor.commit();
            }
        });*/
    }

    private void btnLoginClicked() {
        if (!validate()) {
            return;
        } else {
            String usernameEntered = txtUserName.getText().toString();
            String passwordEntered = txtPwd.getText().toString();
            myRef = FirebaseDatabase.getInstance().getReference("Account");

            Query checkUser = myRef.orderByChild("userName").equalTo(usernameEntered);

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        String PassDb = snapshot.child(usernameEntered).child("password").getValue(String.class);
                        if (PassDb.equals(passwordEntered)) {

                            String RoleDb = snapshot.child(usernameEntered).child("role").getValue(String.class);
                            Intent intent;
                            Toast.makeText(LoginActivity.this,
                                    "Logged by: " + snapshot.child(usernameEntered).child("fullName").getValue(String.class),
                                    Toast.LENGTH_LONG).show();

                            if (RoleDb.equals("1")) {
                                intent = new Intent(LoginActivity.this, WaiterActivity.class);
                                intent.putExtra("UserNameLogged", usernameEntered);
                                startActivity(intent);

                            } else if (RoleDb.equals("2")) {
                                intent = new Intent(LoginActivity.this, BartenderActivity.class);
                                intent.putExtra("UserNameLogged", usernameEntered);
                                startActivity(intent);

                            } else if (RoleDb.equals("0")) {
                                intent = new Intent(LoginActivity.this, ManagerActivity.class);
                                intent.putExtra("UserNameLogged", usernameEntered);
                                startActivity(intent);
                            } else {

                            }
                            saveUser(usernameEntered, passwordEntered);
                            writeLog(usernameEntered);
                        } else {
                            txtPwd.setError("Wrong Pass");
                            txtPwd.requestFocus();
                        }

                    } else {
                        txtUserName.setError("Not exist??");
                        txtUserName.requestFocus();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
//    private void ghi(String usernameEntered) {
//        Date currentTime = Calendar.getInstance().getTime();
//        long i = currentTime.getClass();
//        System.out.println("get time: " + i );
//
//        TestDate t = new TestDate(currentTime,usernameEntered);
//        myRef = FirebaseDatabase.getInstance().getReference("TimeStone");
//
//
//        myRef.child(usernameEntered)
//                .setValue(t);
//
//    }
    private void writeLog(String userName) {

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        log = new LoginHistory(userName, currentDate);

        myRef = FirebaseDatabase.getInstance().getReference("TimeKeeping");

        myRef.child(log.getUserName()).child(new SimpleDateFormat("MM/yyyy", Locale.getDefault()).format(new Date())).setValue(log);

        Log.e("usernameEntered", log.getUserName());
        Log.e("roleDb", "usernameEntered");
    }


    private void mapping() {
        btnLogin = findViewById(R.id.btnLogin);
        txtPwd = findViewById(R.id.txtPass);
        txtUserName = findViewById(R.id.txt_mn_username_update);
        swLogin = findViewById(R.id.sw_auto_login);
    }

    private Boolean validate() {
        String pass = txtPwd.getText().toString();
        String acc = txtUserName.getText().toString();
        if (pass.isEmpty()) {
            txtPwd.setError("Field cannot be empty");
            return false;
        }
        if (acc.isEmpty()) {
            txtUserName.setError("Field cannot be empty");
            return false;
        }
        return true;
    }

    private void saveUser(String userName, String password) {
        SharedPreferences prefs;
        SharedPreferences.Editor editor;

        prefs = getSharedPreferences("My app", MODE_PRIVATE);
        editor = prefs.edit();
        editor.putString("userName", userName);
        editor.putString("password", password);
        editor.putString("isAuto", swLogin.isChecked() ? "true" : "false");
        editor.commit();
    }

    private boolean loginIfAlreadyLogged() {
        SharedPreferences prefs;
        prefs = getSharedPreferences("My app", MODE_PRIVATE);
        String name = prefs.getString("userName", "nothing Here");
        String pass = prefs.getString("password", "nothing Here");
        String isAuto = prefs.getString("isAuto", "false");
        if (name.compareTo("nothing Here") != 0 && pass.compareTo("nothing Here") != 0 && isAuto.compareTo("true") == 0) {
            return true;
        }
        return false;
    }

    private String setUserString() {
        SharedPreferences prefs = getSharedPreferences("My app", MODE_PRIVATE);
        return prefs.getString("userName", "nothing Here");
    }

    private String setPassString() {
        SharedPreferences prefs = getSharedPreferences("My app", MODE_PRIVATE);
        return prefs.getString("password", "nothing Here");
    }

}