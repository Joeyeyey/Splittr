package com.example.splittr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class showSplitReceiptActivity extends AppCompatActivity {

    Button btn_split_another_receipt;
    Button btn_send_sms_reminder;
    TextView show_split_receipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_split_receipt);

        btn_split_another_receipt = findViewById(R.id.btn_split_another_receipt);
        btn_send_sms_reminder = findViewById(R.id.btn_send_reminder);
    }
}