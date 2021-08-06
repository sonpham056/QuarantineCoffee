package com.microwaveteam.quarantinecoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button btnLogin;
    EditText txtUserName, txtPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapping();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameEntered = txtUserName.getText().toString();
                final String passwordEntered = txtPwd.getText().toString();

                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Account");

                Query checkUser = myRef.orderByChild("UserName").equalTo(usernameEntered);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String PassDb = snapshot.child(usernameEntered).child("Password").getValue(String.class);
                        if(PassDb.equals(passwordEntered)){

                            String RoleDb = snapshot.child(usernameEntered).child("role").getValue(String.class);
                            Intent intent;
                            Toast.makeText(Login.this,
                                    "Logged by: "+ snapshot.child(usernameEntered).child("FullName").getValue(String.class),
                                    Toast.LENGTH_LONG).show();

                            if(RoleDb.equals("1")){
                                intent = new Intent(Login.this,Waiter.class);
                                startActivity(intent);
                                //TODO: put some extra

                            }else if(RoleDb.equals("2")){
                                intent = new Intent(Login.this,Bartender.class);
                                startActivity(intent);

                                //TODO: put some extra
                            }else if(RoleDb.equals("0")){
                                intent = new Intent(Login.this,Manager.class);
                                startActivity(intent);
                                //TODO: put some extra
                            }else{

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void mapping() {
        btnLogin = findViewById(R.id.btnLogin);
        txtPwd = findViewById(R.id.txtPass);
        txtUserName = findViewById(R.id.txtUserName);
    }
}