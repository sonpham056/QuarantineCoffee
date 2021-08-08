package com.microwaveteam.quarantinecoffee.activities.Manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.microwaveteam.quarantinecoffee.R;
import java.util.List;

public class Image_Coffee_Adapter extends BaseAdapter {

    private Context context;
    private int  layout;
    private List<Image_Coffee> image_coffeeList;

    public Image_Coffee_Adapter(Context context,int layout,List<Image_Coffee> image_coffeeList){
        this.context = context;
        this.layout = layout;
        this.image_coffeeList = image_coffeeList;
    }
    @Override
    public int getCount() {
        return image_coffeeList.size();

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgCoffee;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder ;

        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.imgCoffee = (ImageView) view.findViewById(R.id.imageviewCoffee);

        }else{
            holder = (ViewHolder) view.getTag();

        }
        Image_Coffee image_coffee = image_coffeeList.get(i);
        holder.imgCoffee.setImageResource(image_coffee.getImage());
        return view;
    }
}
