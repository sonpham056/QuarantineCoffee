package com.microwaveteam.quarantinecoffee.activities.Waiter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microwaveteam.quarantinecoffee.Helper.ProductRecyclerViewAdapter;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.Product;

import java.util.ArrayList;

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

    RecyclerView recyclerView;
    ArrayList<Product> productList;
    ArrayList<String> typeList;
    ArrayList<String> tableList;
    ArrayAdapter<String> tableAdapter;
    DatabaseReference db;
    DatabaseReference db2;
    ProductRecyclerViewAdapter adapter;
    Spinner spinner;

    WaiterActivity waiterActivity;

    // TODO: Rename and change types of parameters
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.w_fragment_main_waiter, container, false);
        productList = new ArrayList<>();
        typeList = new ArrayList<>();
        tableList = new ArrayList<>();
        tableAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, tableList);
        recyclerView = view.findViewById(R.id.recycler_w_products);
        spinner = view.findViewById(R.id.spinner_w_list_table);


        db = FirebaseDatabase.getInstance().getReference("Product");
        db2 = FirebaseDatabase.getInstance().getReference("Table");

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

        db2.addValueEventListener(new ValueEventListener() {
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

        adapter = new ProductRecyclerViewAdapter(MainWaiterFragment.this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(MainWaiterFragment.this.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        spinner.setAdapter(tableAdapter);
        return view;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof WaiterActivity) {
            this.waiterActivity = (WaiterActivity) context;
        }
    }
}