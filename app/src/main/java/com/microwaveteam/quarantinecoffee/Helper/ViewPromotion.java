package com.microwaveteam.quarantinecoffee.Helper;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.microwaveteam.quarantinecoffee.activities.Manager.PromotionActivity;

import java.util.ArrayList;

public class ViewPromotion extends RecyclerView.Adapter<ViewPromotion.FeaturedViewHolder> {

    ArrayList<FeatureHelper> featuredLocations;
    DatabaseReference databaseReference;
    PromotionActivity activity;

    public ViewPromotion(ArrayList<FeatureHelper> featuredLocations, PromotionActivity activity) {
        this.featuredLocations = featuredLocations;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_list,parent,false);
        FeaturedViewHolder featuredViewHolder = new FeaturedViewHolder(view);
        return featuredViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {

        FeatureHelper featuredHelpersClass = featuredLocations.get(position);

        holder.text.setText(featuredHelpersClass.getUserName());
        holder.btnAdd.setImageResource(featuredHelpersClass.getImage1());
        holder.btnDelete.setImageResource(featuredHelpersClass.getImage2());
    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }

    public class FeaturedViewHolder extends RecyclerView.ViewHolder{

        ImageView btnAdd, btnDelete;
        TextView text;


        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            databaseReference = FirebaseDatabase.getInstance().getReference("Promotion");

            btnAdd = itemView.findViewById(R.id.btn_mn_editAccount);
            btnDelete = itemView.findViewById(R.id.btn_mn_deleteAccount);
            text =  itemView.findViewById(R.id.txtProfileUsername);
            itemView.bringToFront();
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnDeleteClick(v);
                }
            });

            btnAdd.setOnClickListener(view -> {

            });
        }

        private void btnDeleteClick(View v) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            int itemPosition = getAdapterPosition();
                            FeatureHelper item = featuredLocations.get(itemPosition);

                            databaseReference.child(item.getUserName()).removeValue();
                            Toast.makeText(v.getContext(), "Successfully deleted promotion: " + item.getUserName(), Toast.LENGTH_SHORT).show();
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