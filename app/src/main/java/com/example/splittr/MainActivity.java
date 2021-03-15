package com.example.splittr;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int REQUEST_GALLERY = 200;


    private ImageButton takePhotoButton;
    private ImageButton photoGalleryButton;
    private ImageButton manageExistingButton;
    private ImageView photoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoImage = (ImageView) findViewById(R.id.image_thumbnail);
        takePhotoButton = (ImageButton) findViewById(R.id.button_camera_main);
        takePhotoButton.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });

        photoGalleryButton = (ImageButton) findViewById(R.id.button_gallery_main);
        photoGalleryButton.setOnClickListener(v -> {
            openImageGallery();
        });

        manageExistingButton = (ImageButton) findViewById(R.id.button_manageExisting_main);
        manageExistingButton.setOnClickListener(v -> {
            openReceiptActivity();
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No camera permissions", Toast.LENGTH_SHORT).show();
//            ActivityCompat.requestPermissions(this, new String[]{(Manifest.permission.CAMERA)}, REQUEST_IMAGE_CAPTURE);
        }

    }

    public void openImageGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_GALLERY);

    }

    public void openReceiptActivity() {
        Intent intent = new Intent(this, receiptActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case REQUEST_IMAGE_CAPTURE:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    photoImage.setImageBitmap(photo);
            }
    }

}