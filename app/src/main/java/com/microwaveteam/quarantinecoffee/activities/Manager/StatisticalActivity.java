package com.microwaveteam.quarantinecoffee.activities.Manager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.StatisticRecyclerAdapter;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.Bill;
import com.microwaveteam.quarantinecoffee.serviceclasses.MyAlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StatisticalActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar1 = Calendar.getInstance();
    EditText etFromDate;
    EditText etToDate;
    TextView txtResult;
    RecyclerView recyclerView;
    Button btnResult;
    StatisticRecyclerAdapter adapter;
    ArrayList<String> list;

    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_statistical);

        etFromDate = findViewById(R.id.txt_mn_statistical_date_from);
        etToDate = findViewById(R.id.txt_mn_statistical_date_to);
        btnResult = findViewById(R.id.btn_mn_bill_result_statistical);
        txtResult = findViewById(R.id.txt_mn_result_statistical);
        recyclerView = findViewById(R.id.recycler_mn_statistical);

        list = new ArrayList<>();
        adapter = new StatisticRecyclerAdapter(list, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));
        recyclerView.setAdapter(adapter);

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

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }
        };

        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(StatisticalActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(StatisticalActivity.this, date2, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnResult.setOnClickListener(view -> {
            if (etToDate.getText().toString().isEmpty() || etFromDate.getText().toString().isEmpty()) {
                Toast.makeText(StatisticalActivity.this, "Please fill all the dates field!", Toast.LENGTH_SHORT).show();
                return;
            }

            db = FirebaseDatabase.getInstance().getReference("Bill");
            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        list.clear();
                        long sum = 0;
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String date = data.getKey();
                            if (stringToDate(date).compareTo(stringToDate(etFromDate.getText().toString())) >= 0 && stringToDate(date).compareTo(stringToDate(etToDate.getText().toString())) <= 0) {
                                for (DataSnapshot dataBillId : data.getChildren()) {
                                    for (DataSnapshot dataBill : dataBillId.getChildren()) {
                                        if (dataBill.getKey().compareTo("Bill") == 0) {
                                            Bill bill = dataBill.getValue(Bill.class);
                                            sum += bill.getSum();
                                            list.add(bill.getWaiterName() + "," + bill.getSum() + "," + bill.getDate() + "," + dataBillId.getKey());
                                        }
                                    }
                                }
                            }
                        }
                        txtResult.setText("Total: " + sum);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etFromDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etToDate.setText(sdf.format(myCalendar1.getTime()));
    }

    private Date stringToDate(String str) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(str);
        } catch (ParseException e) {
            MyAlertDialog.alert("cannot convert string to date", this);
        }
        return null;
    }
}