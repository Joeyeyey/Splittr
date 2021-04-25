package com.example.splittr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EditReceiptRecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "Receipt Recycler View";
    ImageButton btn_addOne;

    MyApplication myApplication = (MyApplication) this.getApplication();

    //declare arraylist
    List<ReceiptComponents> receiptArrayList;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_receipt_recycler_view);

        //populate arraylist with class data
        receiptArrayList = myApplication.getReceiptArrayList();

        Log.d(TAG, "onCreate: " + receiptArrayList.toString());
        //Toast.makeText(com.example.splittr.EditReceiptRecyclerViewActivity.this, "List count = " + receiptArrayList.size(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Arraylist size = " + receiptArrayList.size(), Toast.LENGTH_LONG).show();

        btn_addOne = findViewById(R.id.btn_addOne);

        btn_addOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.splittr.EditReceiptRecyclerViewActivity.this, AddEditOne.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.lv_recyclerview);
        recyclerView.setHasFixedSize(true);

        //linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //specify adapter
        mAdapter = new RecycleViewAdapter(receiptArrayList, com.example.splittr.EditReceiptRecyclerViewActivity.this);
        recyclerView.setAdapter(mAdapter);

        //attach ItemTouchHelper to RecyclerView
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            receiptArrayList.remove(viewHolder.getAdapterPosition());
            mAdapter.notifyDataSetChanged();
        }
    };

}