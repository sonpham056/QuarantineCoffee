package com.microwaveteam.quarantinecoffee.Helper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.microwaveteam.quarantinecoffee.activities.Manager.ListProductActivity;
import com.microwaveteam.quarantinecoffee.models.Product;
import com.microwaveteam.quarantinecoffee.models.Promotion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewProductAdapter extends RecyclerView.Adapter<ViewProductAdapter.ProductItemHolder> {
    ArrayList<Product> productList;
    ListProductActivity listProducts;
    DatabaseReference db;
    DatabaseReference db1;
    String promotion;

    public ViewProductAdapter(ListProductActivity listProducts, ArrayList<Product> productList, String promotion) {
        this.listProducts = listProducts;
        this.productList = productList;
        this.promotion = promotion;
    }

    @NonNull
    @Override
    public ViewProductAdapter.ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mn_show_item_product, parent, false);
        return new ProductItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewProductAdapter.ProductItemHolder holder, int position) {
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

        if(product.getPromotion() != null){
            holder.txtPromotion.setText("Promotion: " + product.getPromotion().getPromotionName());
        }

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getPromotion() == null){
                    db = FirebaseDatabase.getInstance().getReference("Promotion");
                    db.child(promotion).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Promotion promotion1 = snapshot.getValue(Promotion.class);
                            holder.txtPromotion.setText("Promotion: " + promotion1.getPromotionName());
                            product.setPromotion(promotion1);
                            db1 = FirebaseDatabase.getInstance().getReference("Product");
                            db1.child(product.getCategory()).child(product.getProductName()).setValue(product);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                    Toast.makeText(view.getContext(), "Promotion already exists ", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                db1 = FirebaseDatabase.getInstance().getReference("Product");
                                db1.child(product.getCategory()).child(product.getProductName())
                                        .child("promotion").removeValue();
                                Toast.makeText(view.getContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductItemHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        TextView txtPrice;
        TextView txtAmount;
        TextView txtPromotion;
        TextView txtProductType;
        ImageView img;
        ImageButton btnAdd;
        ImageButton btnDel;

        public ProductItemHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_mn_product_name_recycler_view);
            txtPrice = itemView.findViewById(R.id.txt_mn_price_recycler_view);
            txtAmount = itemView.findViewById(R.id.txt_mn_amount_recycler_view);
            txtPromotion = itemView.findViewById(R.id.txt_mn_promotion_recycler_view);
            txtProductType = itemView.findViewById(R.id.txt_mn_recycler_productType);
            img = itemView.findViewById(R.id.imageview_mn_products);
            btnAdd = itemView.findViewById(R.id.imageButton_mn_addProduct);
            btnDel = itemView.findViewById(R.id.imageButton_mn_deletePromotion);
            itemView.setClickable(true);
        }
    }
}
