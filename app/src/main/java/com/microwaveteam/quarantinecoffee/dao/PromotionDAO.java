package com.microwaveteam.quarantinecoffee.dao;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microwaveteam.quarantinecoffee.models.Promotion;

public class PromotionDAO {

    private static DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public static void addPromotion(Promotion promotion, Context context) {
        db.child("Promotion").child(promotion.getPromotionName()).setValue(promotion, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    Toast.makeText(context, "Add failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Add Success", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
