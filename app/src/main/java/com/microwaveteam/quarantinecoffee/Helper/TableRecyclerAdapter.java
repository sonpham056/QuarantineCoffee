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
import com.microwaveteam.quarantinecoffee.activities.Manager.TableActivity;

import java.util.List;

public class TableRecyclerAdapter extends RecyclerView.Adapter<TableRecyclerAdapter.TableItemHolder> {
    private List<String> listTable;
    private TableActivity taBleActivity;

    public TableRecyclerAdapter(List<String> listTable, TableActivity taBleActivity) {
        this.listTable = listTable;
        this.taBleActivity = taBleActivity;
    }

    @NonNull
    @Override
    public TableRecyclerAdapter.TableItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.mn_recycler_table_list_item, parent, false);
        return new TableItemHolder(viewItem);
    }


    @Override
    public void onBindViewHolder(@NonNull TableRecyclerAdapter.TableItemHolder holder, int position) {
        String name = listTable.get(position);
        holder.txtName.setText(name);
        holder.imgView.setOnClickListener(view -> {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Table");
            db.child(name).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(taBleActivity.getApplicationContext(), "Delete succeed", Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return listTable.size();
    }

    public class TableItemHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        ImageView imgView;
        public TableItemHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_mn_taBle_list_item);
             imgView = itemView.findViewById(R.id.img_mn_delete_taBle);
        }
    }
}
