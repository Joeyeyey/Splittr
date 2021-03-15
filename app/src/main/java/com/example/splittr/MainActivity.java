package com.example.splittr;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton takePhotoButton;
    private ImageButton photoGalleryButton;
    private ImageButton receiptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePhotoButton = (ImageButton) findViewById(R.id.button_camera_main);
        takePhotoButton.setOnClickListener(v -> {
            openCameraActivity();
        });

        receiptButton = (ImageButton) findViewById(R.id.button_manageExisting_main);
        receiptButton.setOnClickListener(v -> {
            openReceiptActivity();
        });

    }

    public void openReceiptActivity() {
        Intent intent = new Intent(this, receiptActivity.class);
        startActivity(intent);
    }

    public void openCameraActivity() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

}