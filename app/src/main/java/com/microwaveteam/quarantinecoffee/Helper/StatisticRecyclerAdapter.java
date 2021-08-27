package com.microwaveteam.quarantinecoffee.Helper;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.Manager.ShowProductInStatisticalActivity;
import com.microwaveteam.quarantinecoffee.activities.Manager.StatisticalActivity;
import com.microwaveteam.quarantinecoffee.models.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class StatisticRecyclerAdapter extends RecyclerView.Adapter<StatisticRecyclerAdapter.StatisticItemHolder> {
    private ArrayList<String> list;
    private StatisticalActivity activity;
    private String id;
    private String date;

    public StatisticRecyclerAdapter(ArrayList<String> list, StatisticalActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public StatisticRecyclerAdapter.StatisticItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_mn_item_statistical, parent, false);
        return new StatisticItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticRecyclerAdapter.StatisticItemHolder holder, int position) {
        String[] split = list.get(position).split(",");
        String name = split[0];
        String sum = split[1];
        date = split[2];
        id = split[3];
        holder.txtName.setText("Name: " + name);
        holder.txtSum.setText("Sum: " + sum);
        holder.txtDate.setText("Date: " + date);
        holder.txtId.setText(id);

        //holder.onClick();
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class StatisticItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName;
        TextView txtSum;
        TextView txtDate;
        TextView txtId;
        DatabaseReference db;

        public StatisticItemHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_mn_name_statistical);
            txtSum = itemView.findViewById(R.id.txt_mn_sum_statistical);
            txtDate = itemView.findViewById(R.id.txt_mn_date_statistical);
            txtId = itemView.findViewById(R.id.txt_mn_id_statistical);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                db = FirebaseDatabase.getInstance().getReference("Bill");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                String onlyDate = new SimpleDateFormat("dd-MM-yyyy").format(sdf.parse(date));
                db.child(onlyDate).child(id).child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            ArrayList<String> list = new ArrayList<>();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Product product = data.getValue(Product.class);
                                list.add("Name: " + product.getProductName() + " | Amount: " + product.getAmount() + " | Price: " + product.getPrice());
                            }
                            Intent intent = new Intent(activity.getApplicationContext(), ShowProductInStatisticalActivity.class);
                            intent.putExtra("products", list);
                            activity.startActivity(intent);
                        } else {
                            Toast.makeText(activity.getApplicationContext(), "Something is wrong while retrieving data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } catch (Exception e) {
                Toast.makeText(activity.getApplicationContext(), "ERROR! May be date parsing issue", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }
}
