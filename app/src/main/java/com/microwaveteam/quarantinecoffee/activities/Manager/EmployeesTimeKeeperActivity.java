package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.activities.TimeKeeperActivity;
import com.microwaveteam.quarantinecoffee.models.TimeKeeper;
import com.microwaveteam.quarantinecoffee.serviceclasses.MyAlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EmployeesTimeKeeperActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    Spinner spinnerName;
    EditText txtMonth;
    TextView txtHour;
    ListView listView;
    Button btnCheck;

    ArrayAdapter<String> adapterSpinner;
    ArrayList<String> listSpinner;

    ArrayAdapter<String> adapter;
    ArrayList<String> list;

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_employees_time_keeper);

        spinnerName = findViewById(R.id.spinner_mn_name_employees_time);
        txtMonth = findViewById(R.id.mn_txt_month_employees_time);
        txtHour = findViewById(R.id.txt_mn_worked_hour_employees_time);
        listView = findViewById(R.id.listview_mn_employees_time);
        btnCheck = findViewById(R.id.btn_mn_check_employees_time);

        listSpinner = new ArrayList<>();
        adapterSpinner = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listSpinner);
        spinnerName.setAdapter(adapterSpinner);

        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list);
        listView.setAdapter(adapter);

        db = FirebaseDatabase.getInstance().getReference("TimeKeeper");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    listSpinner.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        listSpinner.add(data.getKey());
                    }
                    adapterSpinner.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        txtMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EmployeesTimeKeeperActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnCheck.setOnClickListener(view -> {
            if (spinnerName.getSelectedItem().toString().isEmpty() || txtMonth.getText().toString().isEmpty()) {
                Toast.makeText(EmployeesTimeKeeperActivity.this, "Please provide all missing fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            db = FirebaseDatabase.getInstance().getReference("TimeKeeper");
            db.child(spinnerName.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        long sumTime = 0;
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Date monthFromUser = stringToDate(data.getKey());
                            String month = new SimpleDateFormat("MM-yyyy", Locale.getDefault()).format(monthFromUser);
                            if (txtMonth.getText().toString().compareTo(month) == 0) {
                                TimeKeeper tk = data.getValue(TimeKeeper.class);
                                sumTime += timeDiff(tk.getTimeStart(), tk.getTimeEnd());
                                list.add("Start: "
                                        + tk.getTimeStart()
                                        + " | end: "
                                        + tk.getTimeEnd()
                                        + " | date: "
                                        + tk.getDate()
                                        + " | time: "
                                        + TimeKeeperActivity.calculate(tk.getTimeStart(), tk.getTimeEnd()));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        txtHour.setText(longToHour(sumTime));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = list.get(i);
                MyAlertDialog.alert(str, EmployeesTimeKeeperActivity.this);
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        txtMonth.setText(sdf.format(myCalendar.getTime()));
    }

    private Date stringToDate(String str) {
        try {
            return new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).parse(str);
        } catch (ParseException e) {
            MyAlertDialog.alert("cannot convert string to date", this);
        }
        return null;
    }

    private Long timeDiff(String timeStart, String timeEnd) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

            Date date1 = simpleDateFormat.parse(timeStart);

            Date date2 = simpleDateFormat.parse(timeEnd);
            long difference = date2.getTime() - date1.getTime();
            return difference;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1l;
    }

    private String longToHour(Long difference) {
        try {
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours))
                    / (1000 * 60);
            hours = (hours < 0 ? -hours : hours);
            return hours + "h:" + min + "m";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}