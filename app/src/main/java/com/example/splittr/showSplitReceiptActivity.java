package com.example.splittr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    TextView show_split_receipt;
    double taxPercent = 0.08;
    double tipPercent = 0.15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_split_receipt);

        receiptArrayList = myApplication.getReceiptArrayList();

        btn_split_another_receipt = findViewById(R.id.btn_split_another_receipt);
        btn_send_sms_reminder = findViewById(R.id.btn_send_reminder);

        //search through each item to find unique person names
        //add up costs for each person
        for (ReceiptComponents item : receiptArrayList) {
            String name = item.getPerson();
            double cost = item.getCost();
            if (map.containsKey(name)) {
                map.put(name, map.get(name) + cost + cost * taxPercent + cost * tipPercent);
            } else {
                map.put(name, cost);
            }
        }

        for (String name : map.keySet()){
            System.out.println(name + ": $" + map.get(name));
        }
        map.clear();
    }
}