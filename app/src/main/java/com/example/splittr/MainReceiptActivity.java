package com.example.splittr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainReceiptActivity extends AppCompatActivity {

    private RecyclerView receiptRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_receipt);
        receiptRecycler = (RecyclerView) findViewById(R.id.receiptsView);
        receiptRecycler.addItemDecoration(new DividerItemDecoration(receiptRecycler.getContext(), DividerItemDecoration.VERTICAL));
        receiptRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        receiptRecycler.setLayoutManager(layoutManager);
        List<Receipt> input = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            input.add(new Receipt(i, "Receipt " + i));
        }// define an adapter
        mAdapter = new ReceiptViewAdapter(input);
        receiptRecycler.setAdapter(mAdapter);

        FloatingActionButton button = (FloatingActionButton)findViewById(R.id.addNewReceipt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.add(new Receipt(0, "I am a new receipt!"));
                mAdapter = new ReceiptViewAdapter(input);
                receiptRecycler.setAdapter(mAdapter);
            }
        });
    }
}