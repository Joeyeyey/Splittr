package com.example.splittr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetTaxAndTipActivity extends AppCompatActivity {

    public static final String TAX_RATE = "com.example.splittr.example.TAX_RATE";
    public static final String TIP_RATE = "com.example.splittr.example.TIP_RATE";
    double taxPercent;
    double tipPercent;
    EditText et_taxRate, et_tipRate;
    Button btn_submit_tip_tax, btn_cancel_to_receipt_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_tax_and_tip);

        et_taxRate = findViewById(R.id.et_taxRate);
        et_tipRate = findViewById(R.id.et_tipRate);
        btn_submit_tip_tax = findViewById(R.id.btn_submit_tip_tax);
        btn_cancel_to_receipt_editor = findViewById(R.id.btn_cancel_to_receipt_editor);

        btn_submit_tip_tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_tipRate.getText().toString().equals("") && et_taxRate.getText().toString().equals("")){
                    tipPercent = 0;
                    taxPercent = 0;
                    Intent intent = new Intent(GetTaxAndTipActivity.this, showSplitReceiptActivity.class);
                    intent.putExtra(TAX_RATE, taxPercent);
                    intent.putExtra(TIP_RATE, tipPercent);
                    startActivity(intent);
                }
                if(et_taxRate.getText().toString().equals("") && !(et_tipRate.getText().toString().equals(""))){
                    taxPercent = 0;
                    tipPercent = Double.parseDouble(et_tipRate.getText().toString());
                    Intent intent = new Intent(GetTaxAndTipActivity.this, showSplitReceiptActivity.class);
                    intent.putExtra(TAX_RATE, taxPercent);
                    intent.putExtra(TIP_RATE, tipPercent);
                    startActivity(intent);
                }
                if(et_tipRate.getText().toString().equals("") && !(et_taxRate.getText().toString().equals(""))){
                    tipPercent = 0;
                    taxPercent = Double.parseDouble(et_taxRate.getText().toString());
                    Intent intent = new Intent(GetTaxAndTipActivity.this, showSplitReceiptActivity.class);
                    intent.putExtra(TAX_RATE, taxPercent);
                    intent.putExtra(TIP_RATE, tipPercent);
                    startActivity(intent);
                }
                else{
                    taxPercent = Double.parseDouble(et_taxRate.getText().toString());
                    tipPercent = Double.parseDouble(et_tipRate.getText().toString());
                    Intent intent = new Intent(GetTaxAndTipActivity.this, showSplitReceiptActivity.class);
                    intent.putExtra(TAX_RATE, taxPercent);
                    intent.putExtra(TIP_RATE, tipPercent);
                    startActivity(intent);
                }
            }
        });

        btn_cancel_to_receipt_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetTaxAndTipActivity.this, EditReceiptRecyclerViewActivity.class);
                startActivity(intent);
            }
        });
    }
}