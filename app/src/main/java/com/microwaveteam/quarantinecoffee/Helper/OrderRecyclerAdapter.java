package com.microwaveteam.quarantinecoffee.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.Waiter.TableDetailActivity;
import com.microwaveteam.quarantinecoffee.models.Order;

import java.util.ArrayList;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.OrderItemHolder> {
    ArrayList<Order> list;
    TableDetailActivity tableDetailActivity;
    public boolean isClickable = true;

    public OrderRecyclerAdapter(TableDetailActivity activity, ArrayList<Order> list) {
        this.list = list;
        this.tableDetailActivity = activity;
    }
    public void setData(ArrayList<Order> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderRecyclerAdapter.OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.w_recycler_order_items, parent, false);
        return new OrderItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerAdapter.OrderItemHolder holder, int position) {
        Order order = list.get(position);
        holder.txtName.setText(order.getProductName());
        holder.txtType.setText(order.getProductType());
        holder.txtAmount.setText("Amount: " + order.getAmount() + "");
        holder.txtPrice.setText("Price: " + order.getPrice() + "");
        holder.txtTable.setText("Table:" + order.getTable());



        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClickable) {
                    holder.imgMinus.startAnimation(AnimationUtils.loadAnimation(tableDetailActivity.getApplicationContext(), R.anim.image_animation));
                    if (order.getAmount() - 1 < 1) {
                        removeAt(holder.getAdapterPosition());
                    } else {
                        order.setAmount(order.getAmount() - 1);
                        holder.txtAmount.setText("Amount: " + order.getAmount() + "");
                    }
                }

            }
        });

        holder.imgPlus.setOnClickListener(view -> {
            if (isClickable) {
                holder.imgPlus.startAnimation(AnimationUtils.loadAnimation(tableDetailActivity.getApplicationContext(), R.anim.image_animation));
                order.setAmount(order.getAmount() + 1);
                holder.txtAmount.setText("Amount: " + order.getAmount() + "");
            }

        });
    }
    public void removeAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OrderItemHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtPrice;
        TextView txtTable;
        TextView txtAmount;
        ImageView imgPlus;
        ImageView imgMinus;
        public OrderItemHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_w_name_recycler_order);
            txtType = itemView.findViewById(R.id.txt_w_productType_recycler_order);
            txtPrice = itemView.findViewById(R.id.txt_w_price_recycler_order);
            txtTable = itemView.findViewById(R.id.txt_w_table_recycler_order);
            txtAmount = itemView.findViewById(R.id.txt_w_amount_recycler_order);
            imgPlus = itemView.findViewById(R.id.imageView_w_plus_recycler_order);
            imgMinus = itemView.findViewById(R.id.imageView_w_minus_recycler_order);
        }
    }
}
