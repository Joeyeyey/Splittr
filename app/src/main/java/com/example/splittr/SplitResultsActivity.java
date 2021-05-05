package com.example.splittr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;

public class SplitResultsActivity extends AppCompatActivity {

    SplittrMath splitter = SplittrApplication.getSplitter();

    Button btn_split_another_receipt;
    Button btn_send_sms_reminder; // for the future
    ListView lv_show_final_amount;
    ArrayList<String> totalAmounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_split_receipt);

        btn_split_another_receipt = findViewById(R.id.btn_split_another_receipt);
//      btn_send_sms_reminder = findViewById(R.id.btn_send_reminder); // for the future
        lv_show_final_amount = findViewById(R.id.lv_show_final_amounts);

        splitter.calculateFinal();
        System.out.println(" ======================= ");
        System.out.println(" - Weighted Amount - ");
        System.out.println(splitter.getWeightedAdditional());
        System.out.println(" - Unweighted Amount - ");
        System.out.println(splitter.getUnweightedAdditional());
        System.out.println(" - User Subtotals - ");
        splitter.printHashmap(splitter.getUserSubtotals());
        System.out.println(" - User Additionals - ");
        splitter.printHashmap(splitter.getUserAdditionals());
        System.out.println(" - User Finals - ");
        splitter.printHashmap(splitter.getUserFinalTotals());
        System.out.println(" ======================= ");

        Hashtable<String, BigDecimal> results = splitter.getUserFinalTotals();
        for (String name : results.keySet()){
            totalAmounts.add(name + ": $" + String.format(Locale.getDefault(), "%.2f", results.get(name)));
        }

        ArrayAdapter listAdapter = new ArrayAdapter(SplitResultsActivity.this, R.layout.lv_final_amount_layout, totalAmounts);
        lv_show_final_amount.setAdapter(listAdapter);

        btn_split_another_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplitResultsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}