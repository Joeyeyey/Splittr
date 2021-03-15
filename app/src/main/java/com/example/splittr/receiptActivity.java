package com.example.splittr;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Manage existing receipt gallery activity
public class receiptActivity extends AppCompatActivity {

    private static final String TAG = "receiptActivity";

    private ArrayList<String> mReceiptNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        getSupportActionBar().setTitle("Existing Receipts");

        initReceiptNames();
    }

    private void initReceiptNames() {
        Log.d(TAG, "initReceiptNames: preparing receipts");

        mReceiptNames.add("Walmart 12/28");
        mReceiptNames.add("McDonald 1/02");
        mReceiptNames.add("Costco 1/06");
        mReceiptNames.add("House Lunch 1/09");
        mReceiptNames.add("Snowboarding 1/16");
        mReceiptNames.add("Fun activity here 1/21");

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recycler view");
        RecyclerView recyclerView = findViewById(R.id.recyclerView_name_receipt);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mReceiptNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}