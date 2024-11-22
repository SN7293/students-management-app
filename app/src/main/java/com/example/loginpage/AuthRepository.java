package com.example.loginpage;

import com.google.firebase.firestore.FirebaseFirestore;

public class AuthRepository {
    private final FirebaseFirestore db;

    public AuthRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    // Sign-up user
    public void signupUser(Modeluser user, OnFirestoreOperationComplete callback) {
        db.collection("User")
                .add(user)
                .addOnSuccessListener(documentReference -> callback.onSuccess("Sign-up successful!"))
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    // Authenticate user
    public void authenticateUser(String email, String password, OnFirestoreOperationComplete callback) {
        db.collection("User")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        task.getResult().forEach(document -> {
                            Modeluser user = document.toObject(Modeluser.class);
                            if (user.getPassword().equals(password)) {
                                callback.onSuccess("Login successful!");
                            } else {
                                callback.onError("Incorrect password!");
                            }
                        });
                    } else {
                        callback.onError("User not found!");
                    }
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public interface OnFirestoreOperationComplete {
        void onSuccess(String message);

        void onError(String error);
    }
}
