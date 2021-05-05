package com.example.splittr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splittr.adapters.ReceiptSelectAdapter;
import com.example.splittr.receiptobjects.Receipt;

import java.util.ArrayList;

// Activity for managing receipts for a user
public class ReceiptSelectRecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "Receipt Recycler View";
    ImageButton btn_addOne;

    //declare arraylist
    ArrayList<Receipt> receiptArrayList;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_receipt_recycler_view);

        //populate arraylist with class data
        receiptArrayList = SplittrApplication.getReceiptContainer().getReceipts();

        Log.d(TAG, "onCreate: " + receiptArrayList.toString());
//        Toast.makeText(com.example.splittr.EditReceiptRecyclerViewActivity.this, "List count = " + receiptArrayList.size(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Arraylist size = " + receiptArrayList.size(), Toast.LENGTH_SHORT).show();

        btn_addOne = findViewById(R.id.btn_addOne);

        //set onclick action for add button
        btn_addOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReceiptSelectRecyclerViewActivity.this, AddReceipt.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.lv_recyclerview);
        recyclerView.setHasFixedSize(true);

        //linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //specify adapter
        mAdapter = new ReceiptSelectAdapter(receiptArrayList, ReceiptSelectRecyclerViewActivity.this);
        recyclerView.setAdapter(mAdapter);

        //attach ItemTouchHelper to RecyclerView
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }

    //initialize ItemTouchHelper to delete receipt items
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            receiptArrayList.remove(viewHolder.getAdapterPosition());
            SplittrApplication.recalculateReceiptIds();
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}