package com.example.splittr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// the main activity where users choose what they want to do in the app
public class MainActivity extends AppCompatActivity {

    // initialize variables
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_GALLERY = 2;
    static final int REQUEST_WRITE_PERM = 112;

    private static final String TAG = "MainActivity";
    String currentPhotoPath;

    // instantiate the RequestQueue.
    RequestQueue requestQueue;
    String url = "https://tesseract.joeyeyey.dev/json";
    private ImageButton takePhotoButton;
    private ImageButton photoGalleryButton;
    private ImageButton manageExistingButton;
    private Button signout;

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    // on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Requesting permission on runtime
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_WRITE_PERM);
//        if (!hasPermissions(this, PERMISSIONS)) {
//            ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, REQUEST_WRITE_PERM );
//        } else {
//            Log.d("Permissions", "App has permissions");
//        }

        requestQueue = Volley.newRequestQueue(this); // INSTANTIATE REQUEST QUEUE

        takePhotoButton = (ImageButton) findViewById(R.id.button_camera_main);
        takePhotoButton.setOnClickListener(v -> dispatchTakePictureIntent());

        photoGalleryButton = (ImageButton) findViewById(R.id.button_gallery_main);
        photoGalleryButton.setOnClickListener(v -> openImageGallery());

        manageExistingButton = (ImageButton) findViewById(R.id.button_manageExisting_main);
        manageExistingButton.setOnClickListener(v -> openReceiptActivity());

        //assign button to signout variable
        signout = (Button) findViewById(R.id.btn_signout);

        //set onclick listener for button
        signout.setOnClickListener(v -> {
            //get instance from firebase to sign user out
            //redirect to login page
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginSystemActivity.class));
        });

    }

    public void openImageGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_GALLERY);
    }

    public void openReceiptActivity() {
        Intent intent = new Intent(this, ReceiptSelectRecyclerViewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            galleryAddPic();  //Not working to add but refreshes media store
        } else if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            String encodedImage;

            SplittrApplication.globalPostResponse = "empty";
            encodedImage = encodeBase64Image(data.getData());
            Toast.makeText(this, "Processing image...", Toast.LENGTH_SHORT).show();

            postTesseract(encodedImage);
            Log.d("POST FINAL", SplittrApplication.globalPostResponse);
        }
    }

    // activity for handling pre-creation of an image file for saving the image taken from the
    // camera
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);    // FOR PRIVATE
//        File storageDir = Environment.getExternalStorageDirectory();    // FOR PUBLIC, DEPRECATED
        File storageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //
        // the holy function

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir     /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.d("D/createImageFile", currentPhotoPath);
        // Toast.makeText(this, currentPhotoPath, Toast.LENGTH_SHORT).show();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                Toast.makeText(this, "Failed to create file, check storage permissions?",
                        Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android" +
                        ".fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        Log.d("D/galleryAddPic", "Adding Image");
    }

    private String encodeBase64Image(Uri photoUri) {
        Bitmap imageBitmap = null;
        String encodedString;
        // Grab bitmap from Uri
        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            Log.d("D/encodeBase64Image", "Successfully grabbed bitmap");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("D/encodeBase64Image", "Failed to grab bitmap");
        }

        // Initialize baos
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] myByteArray = baos.toByteArray();

        // Encode image
        encodedString = Base64.encodeToString(myByteArray, Base64.NO_WRAP);   // NO_WRAP for no
        // new lines
        Log.d("D/encodeBase64Image", "Encoded String Length: " + encodedString.length());

        // Decode test
//        byte[] decodedString = Base64.decode(encodedString, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString
//        .length);
//        Log.d("D/encodeBase64Image", "Image decoded");
//        selectedView.setImageBitmap(decodedByte); // TEMPORARY TEST

        return encodedString;
    }

    private String getTesseract() {
        String requestResponse;

        // Request a string response from the provided URL.
        Log.d("D/getTesseract Request", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Display the first 500 characters of the response string.
                    Toast.makeText(MainActivity.this, response.substring(0, 20),
                            Toast.LENGTH_LONG).show();
                    Log.d("D/getTesseract stringRequest Response", response);
                }, error -> {
            Toast.makeText(MainActivity.this, "That didn't work!", Toast.LENGTH_LONG).show();
            Log.d("D/getTesseract stringRequest Error", "Volley error on response");
        });

        requestQueue.add(stringRequest);
        requestResponse = stringRequest.toString();
        Log.d("D/getTesseract String Response", requestResponse);
        return requestResponse;
    }

    private String deprecatedPost(String encodedString) {
        String requestResponse;

        // Request a string response from the provided URL.
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("data", encodedString);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        final String requestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Log.d("postTesseract Response Code", response), error -> Log.e(
                        "postTesseract Error", error.toString())) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        requestQueue.add(stringRequest);


        requestResponse = stringRequest.toString();
        Log.d("D/postTesseract String Response", requestResponse);
        return requestResponse;
    }

    private String postTesseract(String encodedString) {
        String requestResponse = null;

        JSONObject params = new JSONObject();
        try {
            //input your API parameters
            params.put("data", encodedString);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Enter the correct url for your api service site
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                params,
                response -> {
                    SplittrApplication.globalPostResponse = response.toString();
                    SplittrApplication.globalJSONObj = response;
                    Log.d("POST RESPONSE", SplittrApplication.globalPostResponse);
                    SplittrApplication.addReceiptFromJson();
                    Toast.makeText(MainActivity.this, "Finished importing image!",
                            Toast.LENGTH_SHORT).show();
                }, error -> Log.e("POST ERROR", "Error sending post request"));
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);

        return SplittrApplication.globalPostResponse;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERM) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                Log.d("D/onRequestPermissionsResult", "Write perm granted");
            } else {
                Toast.makeText(this, "The app was not allowed to read your store.",
                        Toast.LENGTH_LONG).show();
                Log.d("D/onRequestPermissionsResult", "Write perm denied");
            }
        }
    }

}