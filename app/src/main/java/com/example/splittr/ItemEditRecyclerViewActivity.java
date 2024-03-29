package com.example.splittr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splittr.adapters.ItemEditAdapter;
import com.example.splittr.receiptobjects.Item;

import java.util.ArrayList;

// Activity for managing items in a receipt
public class ItemEditRecyclerViewActivity extends AppCompatActivity {

    // initialize variables
    private static final String TAG = "ItemRecyclerView";
    ImageButton btn_addOne;
    Button btn_split_receipt;
    double taxRate = 0.085;
    double tipPercent = 0.15;

    // declare arraylist
    ArrayList<Item> itemArrayList;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    // initialize ItemTouchHelper to delete receipt items
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0
            , ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            itemArrayList.remove(viewHolder.getAdapterPosition());
            SplittrApplication.recalculateItemIds();
            mAdapter.notifyDataSetChanged();
        }
    };
    private RecyclerView.LayoutManager layoutManager;

    // on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_recycler_view);

        //populate arraylist with class data
        itemArrayList = SplittrApplication.getSelectedReceipt().getItems();

        Log.d(TAG, "onCreate: " + itemArrayList.toString());
//        Toast.makeText(com.example.splittr.EditReceiptRecyclerViewActivity.this, "List count =
//        " + receiptArrayList.size(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Arraylist size = " + receiptArrayList.size(), Toast.LENGTH_SHORT)
//        .show();

        btn_addOne = findViewById(R.id.btn_addOne);
        btn_split_receipt = findViewById(R.id.btn_split_receipt);

        recyclerView = findViewById(R.id.lv_recyclerview);
        recyclerView.setHasFixedSize(true);

        //linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //specify adapter
        mAdapter = new ItemEditAdapter(itemArrayList, ItemEditRecyclerViewActivity.this);
        recyclerView.setAdapter(mAdapter);

        //attach ItemTouchHelper to RecyclerView
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        //set onclick action for add button
        btn_addOne.setOnClickListener(view -> {
            Intent intent = new Intent(ItemEditRecyclerViewActivity.this, AddEditItem.class);
            startActivity(intent);
        });

        btn_split_receipt.setOnClickListener(v -> {

            Intent intent = new Intent(ItemEditRecyclerViewActivity.this,
                    GetTaxAndTipActivity.class);
            startActivity(intent);
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ReceiptSelectRecyclerViewActivity.class);
        startActivity(intent);
    }

}