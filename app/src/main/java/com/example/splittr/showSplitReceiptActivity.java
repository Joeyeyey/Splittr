package com.example.splittr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class showSplitReceiptActivity extends AppCompatActivity {

    List<ReceiptComponents> receiptArrayList;
    ReceiptItemsApplication myReceiptItemsApplication = (ReceiptItemsApplication) this.getApplication();
    HashMap<String, Double> map = new HashMap<String, Double>();
    Button btn_split_another_receipt;
    Button btn_send_sms_reminder;
    ListView lv_show_final_amount;
    ArrayList<String> totalAmounts = new ArrayList<>();
    double taxRate;
    double tipRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_split_receipt);

        Intent intent = getIntent();
        double taxRate = intent.getDoubleExtra(GetTaxAndTipActivity.TAX_RATE, 0);
        double tipRate = intent.getDoubleExtra(GetTaxAndTipActivity.TIP_RATE, 0);
        System.out.println("rates: " + taxRate + "  " + tipRate);

        receiptArrayList = myReceiptItemsApplication.getReceiptArrayList();

        btn_split_another_receipt = findViewById(R.id.btn_split_another_receipt);
//        btn_send_sms_reminder = findViewById(R.id.btn_send_reminder);
        lv_show_final_amount = findViewById(R.id.lv_show_final_amounts);

        btn_split_another_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showSplitReceiptActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //search through each item to find unique person names
        //add up costs for each person
        for (ReceiptComponents item : receiptArrayList) {
            String name = item.getPerson();
            double cost = item.getCost();
            if (map.containsKey(name)) {
                map.put(name, map.get(name) + cost + cost * taxRate + cost * tipRate);
            } else {
                map.put(name, cost + cost * taxRate + cost * tipRate);
            }
        }

        for (String name : map.keySet()){
           totalAmounts.add((name + ": $" + String.format("%.2f", map.get(name))).toString());
           System.out.println(totalAmounts);
            System.out.println(name + ": $" + map.get(name));
        }

        map.clear();

        ArrayAdapter listAdapter = new ArrayAdapter(showSplitReceiptActivity.this, R.layout.lv_final_amount_layout, totalAmounts);
        lv_show_final_amount.setAdapter(listAdapter);

    }
}