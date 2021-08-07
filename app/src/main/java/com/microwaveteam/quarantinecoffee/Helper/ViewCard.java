package com.microwaveteam.quarantinecoffee.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microwaveteam.quarantinecoffee.R;

import java.util.ArrayList;

public class ViewCard extends RecyclerView.Adapter<ViewCard.FeaturedViewHoder> {

    ArrayList<FeatureHelper> featuredLocations;
    DatabaseReference databaseReference;
    public ViewCard(ArrayList<FeatureHelper> featuredLocations) {
        this.featuredLocations = featuredLocations;
    }

    @NonNull
    @Override
    public FeaturedViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_list,parent,false);
        FeaturedViewHoder featuredViewHoder = new FeaturedViewHoder(view);
        return featuredViewHoder;
    }


    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHoder holder, int position) {

        FeatureHelper featuredHelpersClass = featuredLocations.get(position);

        holder.image.setImageResource(featuredHelpersClass.getImage());
        holder.text.setText(featuredHelpersClass.getUsername());
        holder.title.setImageResource(featuredHelpersClass.getImage1());
        holder.btnDelete.setImageResource(featuredHelpersClass.getImage2());
    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }

    public class FeaturedViewHoder extends RecyclerView.ViewHolder{

        ImageView image, title, btnDelete;
        TextView text;


        public FeaturedViewHoder(@NonNull View itemView) {
            super(itemView);
            databaseReference = FirebaseDatabase.getInstance().getReference("Account");
            image = itemView.findViewById(R.id.img_mn_showAvatar);
            title = itemView.findViewById(R.id.btn_mn_editAccount);
            btnDelete = itemView.findViewById(R.id.btn_mn_deleteAccount);
            text =  itemView.findViewById(R.id.txtProfileUsername);
            itemView.bringToFront();
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getAdapterPosition();
                    FeatureHelper item = featuredLocations.get(itemPosition);
                    //Toast.makeText(v.getContext(),"The Item Clicked is: "+item.getUsername(),Toast.LENGTH_SHORT).show();

                    databaseReference.child(item.getUsername()).removeValue();
                    Toast.makeText(v.getContext(), "Successfully deleted user: "+item.getUsername(), Toast.LENGTH_SHORT).show();

                    //v.getContext().startActivity(get);
                    //final Intent intent;
                    //intent =  new Intent(v.getContext(), History.class);
                    //v.getContext().startActivity(intent);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}