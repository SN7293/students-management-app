package com.example.loginpage;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        ImageView profileImage = findViewById(R.id.profileImage);
        TextView textName = findViewById(R.id.textName);
        TextView textFatherName = findViewById(R.id.textFatherName);
        TextView textCnic = findViewById(R.id.textCnic);
        TextView textRegNo = findViewById(R.id.textRegNo);

        // Get data from Intent
        String name = getIntent().getStringExtra("STUDENT_NAME");
        String fatherName = getIntent().getStringExtra("FATHER_NAME");
        String fatherCnic = getIntent().getStringExtra("FATHER_CNIC");
        String regNumber = getIntent().getStringExtra("REG_NUMBER");
        String imageUri = getIntent().getStringExtra("IMAGE_URI");

        // Set data to views
        textName.setText(name);
        textFatherName.setText(fatherName);
        textCnic.setText(fatherCnic);
        textRegNo.setText(regNumber);

        // Set profile image if needed
        if (imageUri != null && !imageUri.isEmpty()) {
            profileImage.setImageURI(Uri.parse(imageUri)); // If you have a URI
        }
    }
}
