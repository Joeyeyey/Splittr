package com.example.splittr;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splittr.receiptobjects.Item;

import java.util.ArrayList;
import java.util.Arrays;

public class AddEditItem extends AppCompatActivity {

    // initialize variables
    Button btn_ok, btn_cancel;
    ArrayList<Item> itemArrayList;
    EditText et_item, et_cost, et_person;
    int id;

    // on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        itemArrayList = SplittrApplication.getSelectedReceipt().getItems();

        //link variable to button/enterText
        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel = findViewById(R.id.btn_cancel);
        et_item = findViewById(R.id.et_label);
        et_cost = findViewById(R.id.et_cost);
        et_person = findViewById(R.id.et_person);

        Intent intent = getIntent();
        //if id > -1, treat form as edit mode.
        //if id < -1, treat form as new component mode
        id = intent.getIntExtra("id", -1);
        Item itemObject = null;

        if (id >= 0) {
            //edit list component
            for (Item item : itemArrayList) {
                if (item.getId() == id) {
                    itemObject = item;
                }
            }
            et_item.setText(itemObject.getName());
            et_cost.setText(String.valueOf(itemObject.getCost()));
            et_person.setText(String.join(", ", itemObject.getOwners()));
        }  // else add new list component


        btn_ok.setOnClickListener(v -> {
            if (!(et_item.getText().toString().equals("")) && !(et_cost.getText().toString().equals("")) && !(et_person.getText().toString().equals(""))) {
                if (id >= 0) {
                    //update existing list component

                    Item updatedItem = new Item(id, et_item.getText().toString(),
                            Double.parseDouble(et_cost.getText().toString()), false,
                            new ArrayList<>(Arrays.asList(et_person.getText().toString().split(
                                    "\\s*,\\s*"))));
                    itemArrayList.set(id, updatedItem);
                } else {
                    //add new list component
                    // create arraylist object
                    int nextId = SplittrApplication.getNextItemId();
                    Item updatedItem = new Item(nextId, et_item.getText().toString(),
                            Double.parseDouble(et_cost.getText().toString()), false,
                            new ArrayList<>(Arrays.asList(et_person.getText().toString().split(
                                    "\\s*,\\s*"))));

                    //adds object to global list of items
                    itemArrayList.add(updatedItem);
                    SplittrApplication.setNextItemId(nextId++);
                }
                //go back to main activity
                Intent intent1 = new Intent(AddEditItem.this,
                        ItemEditRecyclerViewActivity.class);
                startActivity(intent1);
            } else {
                Toast.makeText(AddEditItem.this, "Please complete all text fields.",
                        Toast.LENGTH_SHORT).show();
            }

        });

        btn_cancel.setOnClickListener(v -> {
            Intent intent12 = new Intent(AddEditItem.this, ItemEditRecyclerViewActivity.class);
            startActivity(intent12);
        });
    }
}