package com.microwaveteam.quarantinecoffee.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.Bartender.BartenderActivity;

import java.util.ArrayList;

public class ViewQueue extends RecyclerView.Adapter<ViewQueue.FeaturedViewHolder> {

    String key;
    ArrayList<FeatureHelper> featuredLocations;
    DatabaseReference databaseReference;
    BartenderActivity activity;

    public ViewQueue(ArrayList<FeatureHelper> featuredLocations, BartenderActivity activity) {
        this.featuredLocations = featuredLocations;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewQueue.FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_list_queue_order,parent,false);
        ViewQueue.FeaturedViewHolder featuredViewHolder = new ViewQueue.FeaturedViewHolder(view);
        return featuredViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewQueue.FeaturedViewHolder holder, int position) {
        FeatureHelper featuredHelpersClass = featuredLocations.get(position);
        holder.txtTable.setText(featuredHelpersClass.table);
        holder.btnIsFinish.setImageResource(featuredHelpersClass.image2);
        holder.txtAmount.setText(featuredHelpersClass.amount);
        holder.txtName.setText(featuredHelpersClass.productName);
        databaseReference = FirebaseDatabase.getInstance().getReference("Order");
        holder.btnIsFinish.setOnClickListener(view -> holder.btnIsFinishClicked(view,databaseReference,featuredHelpersClass,featuredHelpersClass.getKey()));
    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }

    public class FeaturedViewHolder extends RecyclerView.ViewHolder {
        ImageView btnIsFinish;
        TextView txtName, txtAmount, txtTable;
        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);

            btnIsFinish = itemView.findViewById(R.id.btn_order_finish);
            txtName = itemView.findViewById(R.id.txt_order_ProductName);
            txtAmount = itemView.findViewById(R.id.txt_order_amount);
            txtTable = itemView.findViewById(R.id.txt_order_table);
            itemView.bringToFront();
        }

        private void btnIsFinishClicked(View view, DatabaseReference databaseReference, FeatureHelper featuredHelpersClass, String key) {
            BartenderActivity.alertDialogAndRepush("Finish Yet?","Confirm finish",key);
        }
    }
}
