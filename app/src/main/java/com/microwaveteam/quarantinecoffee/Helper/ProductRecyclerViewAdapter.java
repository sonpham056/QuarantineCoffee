package com.microwaveteam.quarantinecoffee.Helper;

import android.graphics.Paint;
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
import com.microwaveteam.quarantinecoffee.activities.Waiter.MainWaiterFragment;
import com.microwaveteam.quarantinecoffee.models.Product;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductItemHolder> {
    MainWaiterFragment fragWaiter;
    ArrayList<Product> productList;

    public ProductRecyclerViewAdapter(MainWaiterFragment fragWaiter, ArrayList<Product> productList) {
        this.fragWaiter = fragWaiter;
        this.productList = productList;
    }

    public void updateData(ArrayList<Product> list) {
        productList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductRecyclerViewAdapter.ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.w_recycler_product_items, parent, false);
        return new ProductItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewAdapter.ProductItemHolder holder, int position) {
        Product product = productList.get(position);
        holder.txtName.setText(product.getProductName());
        holder.txtPrice.setText(product.getPrice() + "");
        holder.txtAmount.setText("Amount: " + product.getAmount() + "");
        holder.txtProductType.setText(product.getCategory());
        if (product.getImage() != null) {
            Picasso.get().load(product.getImage()).into(holder.img);
        } else {
            holder.img.setImageResource(R.drawable.image_coffe_capuchino);
        }
        if (product.getPromotion() != null){
            Date dateNow = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if(simpleDateFormat.format(new Date()).compareTo(product.getPromotion().getEnd()) > 0){
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Product");
                db.child(product.getCategory()).child(product.getProductName()).child("promotion").removeValue();
            }
            else{
                if(simpleDateFormat.format(dateNow).compareTo(product.getPromotion().getStart()) >= 0
                        && simpleDateFormat.format(dateNow).compareTo(product.getPromotion().getEnd()) <= 0){
                    holder.txtPromotion.setText("Promotion: " + product.getPromotion().getPromotionName());
                    holder.txtPrice.setPaintFlags(holder.txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    Long price = product.getPrice() - product.getPrice()*product.getPromotion().getPromotion()/100;
                    holder.txtSale.setText(" " + price);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName;
        TextView txtPrice;
        TextView txtAmount;
        TextView txtPromotion;
        TextView txtProductType;
        ImageView img;
        TextView txtSale;
        public ProductItemHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_w_product_name_recycler_view);
            txtPrice = itemView.findViewById(R.id.txt_w_price_recycler_view);
            txtAmount = itemView.findViewById(R.id.txt_w_amount_recycler_view);
            txtPromotion = itemView.findViewById(R.id.txt_w_promotion_recycler_view);
            txtProductType = itemView.findViewById(R.id.txt_w_recycler_producttype);
            img = itemView.findViewById(R.id.imageview_w_image_recycler_view);
            txtSale = itemView.findViewById(R.id.txt_w_sale_recycler_view);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(fragWaiter.getContext(), "clicked", Toast.LENGTH_SHORT).show();
            fragWaiter.addToList(getAdapterPosition());
        }
    }
}
