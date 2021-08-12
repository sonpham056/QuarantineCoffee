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
import com.microwaveteam.quarantinecoffee.activities.Bartender.Bar_queueOrder;

import java.util.ArrayList;

public class ViewQueue extends RecyclerView.Adapter<ViewQueue.FeaturedViewHolder> {

    ArrayList<FeatureHelper> featuredLocations;
    DatabaseReference databaseReference;
    Bar_queueOrder activity;

    public ViewQueue(ArrayList<FeatureHelper> featuredLocations, Bar_queueOrder activity) {
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

        holder.btnIsfinish.setImageResource(featuredHelpersClass.image1);
        holder.txtAmount.setText(featuredHelpersClass.amount);
        holder.txtName.setText(featuredHelpersClass.productName);
    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }

    public class FeaturedViewHolder extends RecyclerView.ViewHolder {
        ImageView btnIsfinish;
        TextView txtName, txtAmount;
        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            databaseReference = FirebaseDatabase.getInstance().getReference("Account");
            btnIsfinish = itemView.findViewById(R.id.btn_order_finish);
            txtName = itemView.findViewById(R.id.txt_order_ProductName);
            txtAmount = itemView.findViewById(R.id.txt_order_amount);
            itemView.bringToFront();
            btnIsfinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnIsfinishClicked(view);
                }
            });
        }

        private void btnIsfinishClicked(View view) {

        }
    }
}
