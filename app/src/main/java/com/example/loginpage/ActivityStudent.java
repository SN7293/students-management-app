package com.example.loginpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class ActivityStudent extends AppCompatActivity {

    private FirebaseFirestore db;
    private StorageReference storageReference;

    private EditText regNo, name, fatherName, fatherCNIC;
    private ImageView profileImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);

        db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        profileImage = findViewById(R.id.prfileImage);
        // Register button
        Button registerButton = findViewById(R.id.registerBtn);
        // Show students button
        Button showStudents = findViewById(R.id.showStudents);
        regNo = findViewById(R.id.et_reg);
        name = findViewById(R.id.et_name);
        fatherName = findViewById(R.id.et_father_name);
        fatherCNIC = findViewById(R.id.et_father_CNIC);

        profileImage.setOnClickListener(v -> openFileChooser());
        registerButton.setOnClickListener(v -> saveUserData());
        showStudents.setOnClickListener(v -> startActivity(new Intent(ActivityStudent.this, AllStudent.class)));
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void saveUserData() {
        if (imageUri != null) {
            // Create a unique filename for the image
            StorageReference fileReference = storageReference.child("students/" + System.currentTimeMillis() + ".jpg");

            // Upload the image
            UploadTask uploadTask = fileReference.putFile(imageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // Get the download URL
                fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String regNumber = regNo.getText().toString().trim();
                    String userName = name.getText().toString().trim();
                    String userFatherName = fatherName.getText().toString().trim();
                    String userFatherCNIC = fatherCNIC.getText().toString().trim();
                    String imageUriString = uri.toString(); // Convert Uri to String

                    // Create a Student object with image URI
                    ModelStudent student = new ModelStudent(regNumber, userName, userFatherName, userFatherCNIC, imageUriString);

                    // Push data to Firestore
                    db.collection("Students").add(student).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ActivityStudent.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ActivityStudent.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }).addOnFailureListener(e -> Toast.makeText(ActivityStudent.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(ActivityStudent.this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }
}