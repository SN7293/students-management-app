package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllStudent extends AppCompatActivity implements StudentAdapter.OnStudentClickListener {

    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private List<ModelStudent> StudentsList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_students);

        recyclerView = findViewById(R.id.recycler_view_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        fetchStudents(); // Call to fetch students from Firestore
    }

    private void fetchStudents() {
        // Fetch the student data from Firestore and populate the studentList
        // Here, add your Firestore fetching logic and update the adapter
        // Example:
        db.collection("Students").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ModelStudent Students = document.toObject(ModelStudent.class);
                    StudentsList.add(Students);
                }
                studentAdapter = new StudentAdapter(StudentsList, this, this);
                recyclerView.setAdapter(studentAdapter);
                Toast.makeText(this, "students " + StudentsList.size(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStudentClick(ModelStudent student) {
        // Open ProfileActivity with student details
        Intent intent = new Intent(AllStudent.this, ActivityProfile.class);
        intent.putExtra("STUDENT_NAME", student.getName());
        intent.putExtra("FATHER_NAME", student.getFatherName());
        intent.putExtra("FATHER_CNIC", student.getFatherCNIC());
        intent.putExtra("REG_NUMBER", student.getRegNo());
        intent.putExtra("IMAGE_URI", student.getImageUri());
        // Pass the image URI if you have it as well
        // intent.putExtra("IMAGE_URI", student.getImageUri());
        startActivity(intent);
    }
}
