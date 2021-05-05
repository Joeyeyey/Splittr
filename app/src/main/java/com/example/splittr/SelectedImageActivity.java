package com.example.splittr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

//  CURRENTLY NOT USED
public class SelectedImageActivity extends AppCompatActivity {

    // on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_image);

        Intent intent = this.getIntent();
        String filePath = intent.getStringExtra("filePath");
        File imgFile = new File(filePath);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.image_taken);
            myImage.setImageBitmap(myBitmap);
        }

    }

}