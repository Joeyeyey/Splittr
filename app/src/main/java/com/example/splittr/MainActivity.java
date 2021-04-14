package com.example.splittr;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int REQUEST_GALLERY = 200;

    private ImageButton takePhotoButton;
    private ImageButton photoGalleryButton;
    private ImageButton manageExistingButton;
    private ImageView photoImage;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        photoImage = (ImageView) findViewById(R.id.image_taken);
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

    public void openImageGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_GALLERY);
    }

    public void openReceiptActivity() {
        Intent intent = new Intent(this, EditReceiptRecyclerViewActivity.class);
        startActivity(intent);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                Toast.makeText(this, "Error creating file", Toast.LENGTH_SHORT).show();
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
//    }

        private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No camera permissions", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{(Manifest.permission.CAMERA)}, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case REQUEST_IMAGE_CAPTURE:

            }

    }

}