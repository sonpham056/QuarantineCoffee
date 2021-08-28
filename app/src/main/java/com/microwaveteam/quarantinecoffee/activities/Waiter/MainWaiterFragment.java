package com.microwaveteam.quarantinecoffee.activities.Waiter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.ProductRecyclerViewAdapter;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.Bill;
import com.microwaveteam.quarantinecoffee.models.Order;
import com.microwaveteam.quarantinecoffee.models.Product;
import com.microwaveteam.quarantinecoffee.serviceclasses.MyAlertDialog;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainWaiterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainWaiterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final int REQUEST_CODE = 100;

    String waiterName;
    RecyclerView recyclerView;
    ArrayList<Product> productList;
    ArrayList<String> typeList;
    ArrayList<String> tableList;
    ArrayList<Order> listOrderAll;
    ArrayList<Order> tempOrderList;
    ArrayAdapter<String> tableAdapter;
    DatabaseReference db;
    DatabaseReference db2;
    DatabaseReference db3;
    ProductRecyclerViewAdapter adapter;
    Spinner spinner;
    Button btnBill;
    Button btnDetail;
    Button btnConfirm;
    EditText txtSearch;

    WaiterActivity waiterActivity;

    private String mParam1;
    private String mParam2;

    public MainWaiterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainWaiterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainWaiterFragment newInstance(String param1, String param2) {
        MainWaiterFragment fragment = new MainWaiterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        waiterName = getActivity().getIntent().getStringExtra("UserNameLogged");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.w_fragment_main_waiter, container, false);
        productList = new ArrayList<>();
        typeList = new ArrayList<>();
        tableList = new ArrayList<>();
        //productListRetrieved = new ArrayList<>();
        tempOrderList = new ArrayList<>();
        listOrderAll = new ArrayList<>();
        tableAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, tableList);
        recyclerView = view.findViewById(R.id.recycler_w_products);
        spinner = view.findViewById(R.id.spinner_w_list_table);
        btnBill = view.findViewById(R.id.btn_w_make_bill_fragment);
        btnConfirm = view.findViewById(R.id.btn_w_table_confirm);
        btnDetail = view.findViewById(R.id.btn_w_table_detail);
        txtSearch = view.findViewById(R.id.txt_w_search_product);


        db = FirebaseDatabase.getInstance().getReference("Product");
        db2 = FirebaseDatabase.getInstance().getReference("Table");
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        db3 = FirebaseDatabase.getInstance().getReference("OrderQueue").child(currentDate);

        //lay list product de show len recyclerview
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                typeList.clear();
                productList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String type = data.getKey();
                        typeList.add(type);
                        Log.i("line105waiterfrag", type);
                        for (DataSnapshot data2 : data.getChildren()) {
                            Product product = data2.getValue(Product.class);
                            productList.add(product);
                        }
                    }
                    Log.i("line124waiterfrag", productList.size() + "");
                    adapter = new ProductRecyclerViewAdapter(MainWaiterFragment.this, productList);
                    //recyclerView.setLayoutManager(new LinearLayoutManager(MainWaiterFragment.this.getActivity()));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //lay list table de show len spinner
        db2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tableList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        tableList.add(data.getKey());

                    }

                    tableAdapter = new ArrayAdapter<String>(MainWaiterFragment.this.getContext(), R.layout.support_simple_spinner_dropdown_item, tableList);
                    spinner.setAdapter(tableAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //lay list cac mon an da xac nhan o ban hien tai
        db3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (spinner.getSelectedItem() == null) {
                    return;
                }
                if (snapshot.exists()) {
                    listOrderAll.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Order order = data.getValue(Order.class);
                        if (!order.isBill() && order.getTable().compareTo(spinner.getSelectedItem().toString()) == 0) {
                            listOrderAll.add(order);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainWaiterFragment.this.getContext(), TableDetailActivity.class);
                intent.putExtra("table", spinner.getSelectedItem().toString());
                intent.putExtra("list", tempOrderList);
                intent.putExtra("waiter", waiterName);
                MainWaiterFragment.this.startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempOrderList.size() < 1) {
                    Toast.makeText(MainWaiterFragment.this.getContext(), "Nothing to send!", Toast.LENGTH_SHORT).show();
                } else {
                    db = FirebaseDatabase.getInstance().getReference("OrderQueue").child(currentDate);
                    for (Order o : tempOrderList) {
                        String key = db.push().getKey();
                        o.setKey(key);
                        db.child(key).setValue(o);
                    }
                    tempOrderList.clear();
                    Toast.makeText(MainWaiterFragment.this.getContext(), "Send to bartender success!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listOrderAll.size() < 1) {
                    Toast.makeText(MainWaiterFragment.this.getContext(), "Nothing here to make bill", Toast.LENGTH_SHORT).show();
                } else {
                    String currentDateTime = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa", Locale.getDefault()).format(new Date());
                    db = FirebaseDatabase.getInstance().getReference("Bill").child(currentDate);
                    long sum = 0;
                    Bill bill = new Bill();
                    bill.setDate(currentDateTime);
                    for (Order o : listOrderAll) {
                        sum += o.getAmount() * o.getPrice();
                    }
                    bill.setSum(sum);
                    bill.setWaiterName(waiterName);

                    String str = db.push().getKey();
                    db.child(str).child("Bill").setValue(bill);
                    db2 = FirebaseDatabase.getInstance().getReference("OrderQueue").child(currentDate);
                    for (Order o : listOrderAll) {
                        o.setBill(true);
                        db.child(str).child("Orders").child(o.getProductName() + " " + o.getProductType()).setValue(o);
                        db2.child(o.getKey()).setValue(o);
                    }
                    listOrderAll.clear();

                    MyAlertDialog.alert("Bill sum: " + bill.getSum() + "$", MainWaiterFragment.this.getContext());
                    Toast.makeText(MainWaiterFragment.this.getContext(), "Make bill success!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = txtSearch.getText().toString();
                if (text.isEmpty()) {
                    adapter.updateData(productList);
                } else {
                    filter(text);
                }
            }
        });

        adapter = new ProductRecyclerViewAdapter(MainWaiterFragment.this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(MainWaiterFragment.this.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        spinner.setAdapter(tableAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //retrieveListProduct();
                tempOrderList.clear();

                db3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            listOrderAll.clear();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Order order = data.getValue(Order.class);
                                if (!order.isBill() && order.getTable().compareTo(spinner.getSelectedItem().toString()) == 0) {
                                    listOrderAll.add(order);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    public void addToList(int position) {
        Product product = productList.get(position);
        boolean isSale = false;
        if (product.getPromotion() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if(simpleDateFormat.format(new Date()).compareTo(product.getPromotion().getStart()) >= 0
                    && simpleDateFormat.format(new Date()).compareTo(product.getPromotion().getEnd()) <= 0) {
                isSale = true;
                Long price = product.getPrice() - (product.getPrice() * product.getPromotion().getPromotion() / 100);
                product.setPrice(price);
            }
        }
        Order order = null;
        for (Order o : tempOrderList) {
            if (o.getProductName().compareTo(product.getProductName()) == 0 && o.getProductType().compareTo(product.getCategory()) == 0) {
                order = o;
                break;
            }
        }
        if (order != null) {
            order.setAmount(order.getAmount() + 1);
        } else {
            order = new Order();
            order.setBill(false);
            order.setFinish(false);
            order.setTable(spinner.getSelectedItem().toString());
            order.setDateTime(getDate());
            order.setProductName(product.getProductName());
            order.setAmount(1);
            order.setProductType(product.getCategory());
            order.setPrice(product.getPrice());
            tempOrderList.add(order);
        }
        if (isSale) {
            product.setPrice((100 * product.getPrice()) / (100 - product.getPromotion().getPromotion()));
        }
    }

    private String getDate() {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        return currentDate;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof WaiterActivity) {
            this.waiterActivity = (WaiterActivity) context;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == TableDetailActivity.RESULT_CODE) {
            tempOrderList = (ArrayList<Order>) data.getSerializableExtra("list");
        }
    }

    void filter(String text){
        ArrayList<Product> temp = new ArrayList();
        for(Product d: productList){
            if(d.getProductName().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.updateData(temp);
    }
}