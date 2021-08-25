package com.microwaveteam.quarantinecoffee.Helper;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.TimeKeeper;

import java.util.ArrayList;

public class TimeKeeperAdapter extends BaseAdapter {
    final ArrayList<TimeKeeper> listTimeKeeper;

    public TimeKeeperAdapter(ArrayList<TimeKeeper> listTimeKeeper) {
        this.listTimeKeeper = listTimeKeeper;
    }

    @Override
    public int getCount() {
        return listTimeKeeper.size();
    }

    @Override
    public Object getItem(int i) {
        return listTimeKeeper.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View temp;
        if(convertView == null){
            temp = View.inflate(viewGroup.getContext(), R.layout.timekeeper_details,null);
        }else temp = convertView;

        temp.findViewById(R.id.time_show_name);
        temp.findViewById(R.id.time_date);
        temp.findViewById(R.id.time_hours_working);

        return temp;
    }
}
