package com.microwaveteam.quarantinecoffee.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.Manager.ProductTypeActivity;

import java.util.List;

public class ProductTypeRecyclerAdapter extends RecyclerView.Adapter<ProductTypeRecyclerAdapter.ProductItemHolder> {

    private List<String> listProductType;
    private ProductTypeActivity productTypeActivity;

    public ProductTypeRecyclerAdapter(List<String> listProductType, ProductTypeActivity productTypeActivity) {
        this.listProductType = listProductType;
        this.productTypeActivity = productTypeActivity;
    }

    @NonNull
    @Override
    public ProductTypeRecyclerAdapter.ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.mn_recycler_producttype_list_item, parent, false);
        return new ProductItemHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTypeRecyclerAdapter.ProductItemHolder holder, int position) {
        String name = listProductType.get(position);
        holder.txtName.setText(name);
        holder.imgView.setOnClickListener(view -> {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("ProductType");
            db.child(name).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(productTypeActivity.getApplicationContext(), "Delete succeed", Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return listProductType.size();
    }

    public class ProductItemHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        ImageView imgView;
        public ProductItemHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_mn_productType_list_item);
            imgView = itemView.findViewById(R.id.img_mn_delete_productType);
        }
    }
}
