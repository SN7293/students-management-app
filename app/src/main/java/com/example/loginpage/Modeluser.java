package com.example.loginpage;

public class Modeluser {
    public String password;
    public String email;
    public String confirmPassword;


    public Modeluser(String email, String pass , String confirmPassword) {
        this.email=email;
        this. confirmPassword=confirmPassword;
        this.password=pass;
    }
    public Modeluser() {

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
