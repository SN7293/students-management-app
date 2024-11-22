package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signupBtn;
    private TextView signinLink;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.Password);
        confirmPasswordEditText = findViewById(R.id.CONFIRMPASSWORD);
        signupBtn = findViewById(R.id.SignUp);
        signinLink = findViewById(R.id.sign_in_link);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        signupBtn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            authViewModel.signupUser(email, password, confirmPassword);
        });

        signinLink.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));

        observeViewModel();
    }

    private void observeViewModel() {
        authViewModel.getSuccessMessage().observe(this, success -> {
            Toast.makeText(this, success, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        authViewModel.getErrorMessage().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        );
    }
}
