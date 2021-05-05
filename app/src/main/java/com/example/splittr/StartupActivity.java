package com.example.splittr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// initial activity that the user sees when they first open the app
public class StartupActivity extends AppCompatActivity {

    // initialize variables
    private Button startButton;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        startButton = (Button) findViewById(R.id.button_start);
        startButton.setOnClickListener(v -> {
            openMainActivity();
        });
    }

    // back twice to exit application override
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, LoginSystemActivity.class);
        startActivity(intent);
    }

}