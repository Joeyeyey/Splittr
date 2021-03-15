package com.example.splittr;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
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

        mReceiptNames.add("receipt 1");
        mReceiptNames.add("Mcdonald");
        mReceiptNames.add("Costco");

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recycler view");
        RecyclerView recyclerView = findViewById(R.id.receiptRecyclerV_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mReceiptNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}