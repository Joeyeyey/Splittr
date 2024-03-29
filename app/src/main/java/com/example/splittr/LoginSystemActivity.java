package com.example.splittr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

// class activity for prompting user login and interacting with the firebase database
public class LoginSystemActivity extends AppCompatActivity implements View.OnClickListener {

    // initialize variables
    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    // on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //initialize buttons and text input field objects
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.loginemail);
        editTextPassword = (EditText) findViewById(R.id.loginpassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    //detects user click for all buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, com.example.splittr.RegisterUser.class));
                break;
            case R.id.signIn:
                userLogin();
                break;
        }
    }

    //email and password error checking
    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        //make progress bar visible
        progressBar.setVisibility(View.VISIBLE);

        //validate user login
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //redirect to user profile
                startActivity(new Intent(LoginSystemActivity.this, MainActivity.class));
            } else {
                Toast.makeText(LoginSystemActivity.this, "Failed to login. Please check your " +
                        "credentials", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }

        });

    }
}