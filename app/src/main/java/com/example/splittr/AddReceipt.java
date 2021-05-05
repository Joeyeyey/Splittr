package com.example.splittr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splittr.receiptobjects.Receipt;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddReceipt extends AppCompatActivity {

    // initialize variables
    Button btn_ok, btn_cancel;
    ArrayList<Receipt> receiptArrayList;
    EditText et_label;
    EditText et_date;
    int id;

    // on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt);

        receiptArrayList = SplittrApplication.getReceiptContainer().getReceipts();

        //link variable to button/enterText
        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel = findViewById(R.id.btn_cancel);
        et_label = findViewById(R.id.et_label);
        et_date = findViewById(R.id.et_cost);

        Intent intent = getIntent();
        //if id > -1, treat form as edit mode.
        //if id < -1, treat form as new component mode
        id = intent.getIntExtra("id", -1);
        Receipt receiptObject = null;

        if (id >= 0) {
            //edit list component
            for (Receipt rc : receiptArrayList) {
                if (rc.getId() == id) {
                    receiptObject = rc;
                }
            }
            et_label.setText(receiptObject.getLabel());
            et_date.setText(String.valueOf(receiptObject.getCreationDate(DateTimeFormatter.ISO_LOCAL_DATE)));
        }  // else add new list component


        btn_ok.setOnClickListener(v -> {
            if (!(et_label.getText().toString().equals("")) && !(et_date.getText().toString().equals(""))) {
                if (id >= 0) {
                    //update existing list component
                    Receipt updatedReceipt = new Receipt(id, et_label.getText().toString(),
                            et_date.getText().toString());

                    receiptArrayList.set(id, updatedReceipt);
                } else {
                    //add new list component
                    // create arraylist object
                    int nextId = SplittrApplication.getNextReceiptId();
                    Receipt receipt = new Receipt(nextId, et_label.getText().toString(),
                            et_date.getText().toString());

                    //adds object to global list of items
                    receiptArrayList.add(receipt);
                    SplittrApplication.setNextReceiptId(nextId++);
                }
                //go back to main activity
                Intent intent1 = new Intent(AddReceipt.this,
                        ReceiptSelectRecyclerViewActivity.class);
                startActivity(intent1);
            } else {
                Toast.makeText(AddReceipt.this, "Please complete all text fields.",
                        Toast.LENGTH_SHORT).show();
            }

        });

        btn_cancel.setOnClickListener(v -> {
            Intent intent12 = new Intent(AddReceipt.this,
                    ReceiptSelectRecyclerViewActivity.class);
            startActivity(intent12);
        });


    }
}