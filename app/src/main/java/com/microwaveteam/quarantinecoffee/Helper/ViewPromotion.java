package com.microwaveteam.quarantinecoffee.Helper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.Manager.ListProductActivity;
import com.microwaveteam.quarantinecoffee.activities.Manager.PromotionActivity;
import com.microwaveteam.quarantinecoffee.models.Promotion;

import java.util.ArrayList;

public class ViewPromotion extends RecyclerView.Adapter<ViewPromotion.ItemHolder> {

    ArrayList<Promotion> list;
    DatabaseReference databaseReference;
    PromotionActivity activity;

    public ViewPromotion(ArrayList<Promotion> list, PromotionActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewPromotion.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.activity_show_list_promotion,parent,false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        Promotion promotion = list.get(position);

        holder.textName.setText(promotion.getPromotionName());
        holder.text.setText(promotion.getPromotion() + "");

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity.getApplicationContext(), ListProductActivity.class);
                intent.putExtra("textName", holder.textName.getText().toString());
                activity.startActivity(intent);
            }
        });

        holder.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Promotion Name: " + promotion.getPromotionName()
                        + "\nPromotion: " + promotion.getPromotion() + "\nStart: " + promotion.getStart()
                        + "    -   End: " + promotion.getEnd()).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        ImageView btnAdd, btnDelete;
        TextView text, textName;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            databaseReference = FirebaseDatabase.getInstance().getReference("Promotion");

            btnAdd = itemView.findViewById(R.id.btn_mn_addPromotionInProduct);
            btnDelete = itemView.findViewById(R.id.btn_mn_deletePromotion);
            textName = itemView.findViewById(R.id.promotionName);
            text =  itemView.findViewById(R.id.promotion);

            itemView.bringToFront();

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnDeleteClick(v);
                }
            });
        }

        private void btnDeleteClick(View v) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            int itemPosition = getAdapterPosition();
                            Promotion item = list.get(itemPosition);

                            databaseReference.child(item.getPromotionName()).removeValue();
                            Toast.makeText(v.getContext(), "Successfully deleted promotion: " + item.getPromotionName(), Toast.LENGTH_SHORT).show();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }
}