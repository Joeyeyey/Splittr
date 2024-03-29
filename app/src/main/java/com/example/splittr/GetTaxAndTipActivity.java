package com.example.splittr;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// activity for getting tax and tip from the user
public class GetTaxAndTipActivity extends AppCompatActivity {

    // initialize variables
    private static final String TAG = "GetTaxAndTipActivity";

    SplittrMath splitter = SplittrApplication.getSplitter();

    EditText et_taxRate, et_tipRate;
    Button btn_submit_tip_tax, btn_cancel_to_receipt_editor;

    // on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_tax_and_tip);

        et_taxRate = findViewById(R.id.et_taxRate);
        et_tipRate = findViewById(R.id.et_tipRate);
        btn_submit_tip_tax = findViewById(R.id.btn_submit_tip_tax);
        btn_cancel_to_receipt_editor = findViewById(R.id.btn_cancel_to_receipt_editor);

        btn_submit_tip_tax.setOnClickListener(v -> {
            if (!et_tipRate.getText().toString().equals("") && !et_taxRate.getText().toString().equals("")) {

                // clear hashmaps so that values dont stack in future calculations
                splitter.resetAdditionals();
                splitter.resetSubtotals();
                splitter.clearUnweightedAdditionals();
                splitter.clearWeightedAdditionals();

                // reprocess subtotals
                splitter.processSubtotal();

                splitter.setTaxRate(Double.parseDouble(et_taxRate.getText().toString().replace(
                        "%", "")));
                splitter.processGlobalTax();

                splitter.addWeightedTipPercentage(Double.parseDouble(et_tipRate.getText().toString().replace("%", "")));

                // process weighted and unweighted after including tax and tip above
                splitter.processWeighted();
                splitter.processUnweighted();

                Intent intent = new Intent(GetTaxAndTipActivity.this,
                        SplitResultsActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(GetTaxAndTipActivity.this, "Please complete all text fields.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btn_cancel_to_receipt_editor.setOnClickListener(v -> {
            Intent intent = new Intent(GetTaxAndTipActivity.this,
                    ItemEditRecyclerViewActivity.class);
            startActivity(intent);
        });

        et_taxRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!et_taxRate.getText().toString().endsWith("%")) {
                    Log.d(TAG, "afterTextChanged: \"" + et_taxRate.getText().toString() + "\" " +
                            "Does not end in a '%'. Replacing and reappending.");
                    et_taxRate.setText(et_taxRate.getText().toString().replace("%", "") + "%");
                    et_taxRate.setSelection(et_taxRate.getText().length() - 1);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        et_tipRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!et_tipRate.getText().toString().endsWith("%")) {
                    Log.d(TAG, "afterTextChanged: \"" + et_tipRate.getText().toString() + "\" " +
                            "Does not end in a '%'. Replacing and reappending.");
                    et_tipRate.setText(et_tipRate.getText().toString().replace("%", "") + "%");
                    et_tipRate.setSelection(et_tipRate.getText().length() - 1);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }


}