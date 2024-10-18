package com.example.loginpage;


public class ModelStudent {

    private String regNo;
    private String name;
    private String fatherName;
    private String fatherCNIC;
    private String imageUri; // Add this field to store the image URI

    public ModelStudent(String regNo, String name, String fatherName, String fatherCNIC, String imageUri) {
        this.regNo = regNo;
        this.name = name;
        this.fatherName = fatherName;
        this.fatherCNIC = fatherCNIC;
        this.imageUri = imageUri; // Initialize the image URI
    }
    public ModelStudent() {

    }

    // Getters and Setters
    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherCNIC() {
        return fatherCNIC;
    }

    public void setFatherCNIC(String fatherCNIC) {
        this.fatherCNIC = fatherCNIC;
    }

    public String getImageUri() { // Getter for image URI
        return imageUri;
    }

    public void setImageUri(String imageUri) { // Setter for image URI
        this.imageUri = imageUri;
    }
}