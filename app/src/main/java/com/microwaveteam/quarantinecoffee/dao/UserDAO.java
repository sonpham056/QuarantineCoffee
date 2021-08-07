package com.microwaveteam.quarantinecoffee.dao;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.models.User;

public class UserDAO {
    private static DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    public static void addUser(User user, Context context) {
        db.child("Account").child(user.getUserName()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Upload Success", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
