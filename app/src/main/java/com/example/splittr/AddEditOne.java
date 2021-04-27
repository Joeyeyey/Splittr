package com.example.splittr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddEditOne extends AppCompatActivity {

    Button btn_ok, btn_cancel;
    List<ReceiptComponents> receiptArrayList;
    EditText et_item, et_cost, et_person;
    int id;

    ReceiptItemsApplication myReceiptItemsApplication = (ReceiptItemsApplication) this.getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        receiptArrayList = myReceiptItemsApplication.getReceiptArrayList();

        //link variable to button/enterText
        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel = findViewById(R.id.btn_cancel);
        et_item = findViewById(R.id.et_label);
        et_cost = findViewById(R.id.et_date);
        et_person = findViewById(R.id.et_person);

        Intent intent = getIntent();
        //if id > -1, treat form as edit mode.
        //if id < -1, treat form as new component mode
        id = intent.getIntExtra("id", -1);
        ReceiptComponents receiptComponentsObject = null;

        if(id >= 0){
            //edit list component
            for (ReceiptComponents rc: receiptArrayList) {
                if(rc.getId() == id){
                    receiptComponentsObject = rc;
                }
            }
            et_item.setText(receiptComponentsObject.getItem());
            et_cost.setText(String.valueOf(receiptComponentsObject.getCost()));
            et_person.setText(receiptComponentsObject.getPerson());
        }
        else{
            //add new list component
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(et_item.getText().toString().equals("")) && !(et_cost.getText().toString().equals("")) && !(et_person.getText().toString().equals(""))){
                    if (id >= 0){
                        //update existing list component
                        ReceiptComponents updatedReceiptComponent = new ReceiptComponents(id, et_item.getText().toString(), Double.parseDouble(et_cost.getText().toString()), et_person.getText().toString());
                        receiptArrayList.set(id, updatedReceiptComponent);
                    }
                    else{
                        //add new list component
                        // create arraylist object
                        int nextId = myReceiptItemsApplication.getNextId();
                        ReceiptComponents newReceiptComponent = new ReceiptComponents(nextId, et_item.getText().toString(), Double.parseDouble(et_cost.getText().toString()), et_person.getText().toString());

                        //adds object to global list of items
                        receiptArrayList.add(newReceiptComponent);
                        myReceiptItemsApplication.setNextId(nextId++);
                    }
                    //go back to main activity
                    Intent intent = new Intent(com.example.splittr.AddEditOne.this, ItemEditRecyclerViewActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(com.example.splittr.AddEditOne.this, "Please complete all text fields.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.splittr.AddEditOne.this, ItemEditRecyclerViewActivity.class);
                startActivity(intent);
            }
        });
    }
}