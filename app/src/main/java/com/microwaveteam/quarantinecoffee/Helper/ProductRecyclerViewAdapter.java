package com.microwaveteam.quarantinecoffee.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.Waiter.MainWaiterFragment;
import com.microwaveteam.quarantinecoffee.models.Product;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
        holder.txtPrice.setText("Price: " + product.getPrice() + "");
        holder.txtAmount.setText("Amount: " + product.getAmount() + "");
        holder.txtProductType.setText(product.getCategory());
        if (product.getImage() != null) {
            Picasso.get().load(product.getImage()).into(holder.img);
        } else {
            holder.img.setImageResource(R.drawable.image_coffe_capuchino);
        }

        //holder.txtPromotion.setText(product.getPromotion);
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
        public ProductItemHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_w_product_name_recycler_view);
            txtPrice = itemView.findViewById(R.id.txt_w_price_recycler_view);
            txtAmount = itemView.findViewById(R.id.txt_w_amount_recycler_view);
            txtPromotion = itemView.findViewById(R.id.txt_w_promotion_recycler_view);
            txtProductType = itemView.findViewById(R.id.txt_w_recycler_producttype);
            img = itemView.findViewById(R.id.imageview_w_image_recycler_view);
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
