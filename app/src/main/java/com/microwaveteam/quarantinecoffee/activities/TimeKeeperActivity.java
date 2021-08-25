package com.microwaveteam.quarantinecoffee.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.TimeKeeperAdapter;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.TimeKeeper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeKeeperActivity extends AppCompatActivity implements View.OnClickListener {

    TimeKeeperAdapter adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Boolean isStart = false;
    TimeKeeper timeKeeper;
    Button btnStart, btnEnd;
    TextView txtUserNameLogging_timeKeeper;
    ListView lvShowTime;
    String userNameLogging;
    Date currentDate, currentTime;
    DatabaseReference myRef;
    ImageView imgWatch;

    ArrayList<TimeKeeper> listKeeper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_time_keeper);
        timeKeeper = new TimeKeeper();
        binding();

        if(preferences.getInt("isStart", 0) == 1){
            imgWatch.setVisibility(View.VISIBLE);
        }else {
            imgWatch.setVisibility(View.INVISIBLE);
        }

        btnEnd.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        addList();
    }

    private void addList() {
        myRef = FirebaseDatabase.getInstance()
                .getReference("TimeKeeper")
                .child(userNameLogging);

        Query query = myRef.orderByChild("date");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    TimeKeeper data = ds.getValue(TimeKeeper.class);
//                    listKeeper.add(data);
//                }
//                adapter = new TimeKeeperAdapter(listKeeper);
//                lvShowTime.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    private void binding() {
        userNameLogging = getIntent().getStringExtra("userNameInTimeKeeper");
        imgWatch = findViewById(R.id.img_watch);
        btnStart = findViewById(R.id.btnStart);
        btnEnd = findViewById(R.id.btnEnd);
        lvShowTime = findViewById(R.id.lv_show_timekeeper);
        txtUserNameLogging_timeKeeper = findViewById(R.id.txt_timekeeper_Username);
        txtUserNameLogging_timeKeeper.setText("Hello: " + userNameLogging);
        currentDate = Calendar.getInstance().getTime();

        preferences = getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    @Override
    public void onClick(View view) {
        if (view == btnEnd) {
            if(preferences.getInt("isStart",0) == 1){
                imgWatch.setVisibility(View.INVISIBLE);
                currentTime = Calendar.getInstance().getTime();
                timeKeeper.setTimeEnd(formatToString(currentTime, "HH:mm a"));
                timeKeeper.setTimeStart(preferences.getString("TimeStart",null));
                timeKeeper.setUserName(userNameLogging);
                timeKeeper.setDate(formatToString(currentDate,"dd-MM-yy"));
                myRef = FirebaseDatabase.getInstance()
                        .getReference("TimeKeeper")
                        .child(userNameLogging)
                        .child(formatToString(currentDate, "dd-MM-yy"));
                myRef.setValue(timeKeeper);

                test();

                editor.remove("TimeStart");
                editor.remove("userNameLogging");
                editor.remove("isStart");
                editor.commit();
            } else {
                Toast.makeText(this, "Haven't start yet", Toast.LENGTH_LONG).show();
                return;
            }
        } else if (view == btnStart) {
            imgWatch.setVisibility(View.VISIBLE);
            currentTime = Calendar.getInstance().getTime();
            editor.putString("userNameLogging",userNameLogging);
            editor.putString("TimeStart",formatToString(currentTime, "HH:mm a"));
            editor.putInt("isStart",1);
            editor.commit();
        }
    }

    @SuppressLint("RestrictedApi")
    private void test() {
        //TODO: Test time working for Son
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("snapshot = " + snapshot.getValue(TimeKeeper.class).getTimeEnd());
                TimeKeeper timeKeeper;
                timeKeeper = snapshot.getValue(TimeKeeper.class);

                System.out.println("t = " + timeKeeper.getTimeEnd());

                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                    Date date1 = simpleDateFormat.parse(timeKeeper.getTimeStart());

                    Date date2 = simpleDateFormat.parse(timeKeeper.getTimeEnd());
                    long difference = date2.getTime() - date1.getTime();
                    int days = (int) (difference / (1000 * 60 * 60 * 24));
                    int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                    int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours))
                            / (1000 * 60);
                    hours = (hours < 0 ? -hours : hours);
                    Log.i("======= Hours worked", " :: " + hours + " :: " + min);
                    Log.i("======= TimeEnd", " :: " + timeKeeper.getTimeEnd());
                    Log.i("======= TimeStart", " :: " + timeKeeper.getTimeStart());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String formatToString(Date newDate, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        String currentDateAndTime = sdf.format(newDate);
        return currentDateAndTime;
    }
}