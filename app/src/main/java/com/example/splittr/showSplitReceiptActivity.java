package com.example.splittr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

public class showSplitReceiptActivity extends AppCompatActivity {

    List<ReceiptComponents> receiptArrayList;
    MyApplication myApplication = (MyApplication) this.getApplication();
    HashMap<String, Double> map = new HashMap<String, Double>();
    Button btn_split_another_receipt;
    Button btn_send_sms_reminder;
    TextView tv_show_split_receipt;
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

        receiptArrayList = myApplication.getReceiptArrayList();

        btn_split_another_receipt = findViewById(R.id.btn_split_another_receipt);
        btn_send_sms_reminder = findViewById(R.id.btn_send_reminder);
        tv_show_split_receipt = findViewById(R.id.tv_show_split_receipt);

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

        String combined;

        for (String name : map.keySet()){
            tv_show_split_receipt.setText(name + ": $" + map.get(name) + "\n");
            System.out.println(name + ": $" + map.get(name));
        }
        map.clear();
    }
}