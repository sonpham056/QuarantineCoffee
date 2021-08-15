package com.microwaveteam.quarantinecoffee.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.Bartender.Bar_queueOrder;
import com.microwaveteam.quarantinecoffee.activities.Bartender.BartenderActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
        holder.txtTable.setText(featuredHelpersClass.table);
        holder.btnIsFinish.setImageResource(featuredHelpersClass.image2);
        holder.txtAmount.setText(featuredHelpersClass.amount);
        holder.txtName.setText(featuredHelpersClass.productName);
        databaseReference = FirebaseDatabase.getInstance().getReference("Order");
        holder.btnIsFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnIsFinishClicked(view,databaseReference,featuredHelpersClass);
            }
        });
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

        private void btnIsFinishClicked(View view, DatabaseReference databaseReference, FeatureHelper featuredHelpersClass) {
           //TODO: click finish?? - chịu r

//            System.out.println("-------------featuredHelpersClass" + featuredHelpersClass.table);
//            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
//                    .format(new Date());
//            Query check = databaseReference.child(currentDate).orderByChild("table")
//                    .equalTo(featuredHelpersClass.table.charAt(featuredHelpersClass.table.length() - 1));

        }
    }
}
