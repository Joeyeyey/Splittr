package com.example.splittr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

// class for handling user registration
public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    // initialize variables
    private TextView registerUser;
    private EditText editTextfullName, editTextemail, editTextpassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    // on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // initialize firebase authentication instance
        mAuth = FirebaseAuth.getInstance();

        // initialize button
        registerUser = (Button) findViewById(R.id.registerUser);
        // initialize on click method
        registerUser.setOnClickListener(this);

        // initialize text input fields
        editTextfullName = (EditText) findViewById(R.id.fullName);
        editTextemail = (EditText) findViewById(R.id.email);
        editTextpassword = (EditText) findViewById(R.id.password);

        // initialize progress bar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    // on click methods
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerUser) {
            registerUser();
        }
    }

    // acquire user input and convert to string
    private void registerUser() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String fullName = editTextfullName.getText().toString().trim();

        //email and password error checking
        if (fullName.isEmpty()) {
            editTextfullName.setError("Full name is required!");
            editTextfullName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextemail.setError("Email is required!");
            editTextemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextemail.setError("Please provide valid email!");
            editTextemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextpassword.setError("Password is required!");
            editTextpassword.requestFocus();
            return;
        }

        // progress bar is now visible to user
        progressBar.setVisibility(View.VISIBLE);

        // firebase object to create a user in database with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    // check if user has been registered
                    if (task.isSuccessful()) {
                        // User object to store info in database
                        UserActivity user =
                                new UserActivity(fullName, email);

                        // pass user information to database
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {

                            // check if user has been registered
                            if (task1.isSuccessful()) {
                                Toast.makeText(RegisterUser.this, "User has been " +
                                        "registered successfully", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);

                                // redirect to login layout
                                startActivity(new Intent(RegisterUser.this,
                                        LoginSystemActivity.class));
                            } else {
                                Toast.makeText(RegisterUser.this, "Failed to register. " +
                                        "Try again", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });

                    } else {
                        Toast.makeText(RegisterUser.this, "User Account Already Exists",
                                Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}