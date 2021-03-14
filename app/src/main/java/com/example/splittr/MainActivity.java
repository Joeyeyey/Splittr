package com.example.splittr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private ImageButton receiptButton;
    private ImageButton takePhotoButton;
    private ImageButton photoGalleryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiptButton = (ImageButton) findViewById(R.id.manageExistingButton);
        receiptButton.setOnClickListener(v -> {
            openReceiptActivity();
        });

        takePhotoButton = (ImageButton) findViewById(R.id.takePhotoButton);
        takePhotoButton.setOnClickListener(v -> {
            openCameraActivity();
        });
    }

    public void openReceiptActivity() {
        Intent intent = new Intent(this, receiptActivity.class);
        startActivity(intent);
    }

    public void openCameraActivity() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        Intent intent = new Intent (this, cameraActivity.class);
//        startActivity(intent);
        startActivity(intent);
    }

}