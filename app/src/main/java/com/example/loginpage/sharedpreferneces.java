package com.example.loginpage;

import android.content.Context;
import android.content.SharedPreferences;

public class sharedpreferneces {
    private static final String PREFS_NAME = "MySharedPref";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public sharedpreferneces(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUser(Modeluser user) {
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("confirmPassword", user.getConfirmPassword());
        editor.apply();
    }

    // You can also add methods to retrieve user data if needed
}
