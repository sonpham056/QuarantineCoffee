package com.microwaveteam.quarantinecoffee.Helper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.TimeKeeperActivity;
import com.microwaveteam.quarantinecoffee.models.TimeKeeper;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TimeKeeperAdapter extends BaseAdapter {
    final ArrayList<TimeKeeper> listTimeKeeper;

    private Activity activity;
    public TimeKeeperAdapter(ArrayList<TimeKeeper> listTimeKeeper, Activity activity) {
        this.listTimeKeeper = listTimeKeeper;
        this.activity = activity;
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
        TextView showName, timeAndDate, hoursWorking;
        LayoutInflater layoutInflater = activity.getLayoutInflater();

        String timeStart = listTimeKeeper.get(i).getTimeStart();
        String timeEnd = listTimeKeeper.get(i).getTimeEnd();

        if(convertView == null){
            temp = layoutInflater.inflate(R.layout.timekeeper_details,null);
        }else temp = convertView;


        showName = temp.findViewById(R.id.time_show_name);
        showName.setText(listTimeKeeper.get(i).getUserName());
        timeAndDate = temp.findViewById(R.id.time_date);
        timeAndDate.setText("Ngay: " + listTimeKeeper.get(i).getDate());
        hoursWorking = temp.findViewById(R.id.time_hours_working);
        hoursWorking.setText("So gio lam: " + TimeKeeperActivity.calculate(timeStart,timeEnd));


        return temp;
    }
}
