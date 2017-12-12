package com.akash.android.nitsilcharalumni.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.support.v4.util.Preconditions.checkNotNull;


public class ActivityUtils {

    public static void replaceFragmentOnActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment,
                                                 int frameId, boolean addToBackstack, String tag){
        if(addToBackstack) {
            fragmentManager.beginTransaction()
                    .replace(frameId, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        }else {
            fragmentManager.beginTransaction()
                    .replace(frameId, fragment)
                    .commit();
        }
    }

    public static boolean validateName(Editable editable, EditText editTextName){
        if (!TextUtils.isEmpty(editable)) {
            String regx = "^[\\p{L} .'-]+$";
            Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(editable);
            if (!matcher.find() && editTextName != null) {
                editTextName.setError("Invalid name");
                return false;
            }
        } else if (editTextName != null) {
            editTextName.setError("Enter name");
            return false;
        }
        return true;
    }

    public static boolean validateEmail(Editable editable, EditText editTextEmail){
        if (!TextUtils.isEmpty(editable)) {
            if (!Patterns.EMAIL_ADDRESS.matcher(editable).find() && editTextEmail != null) {
                editTextEmail.setError("Invalid email");
                return false;
            }
        } else if (editTextEmail != null) {
            editTextEmail.setError("Enter email");
            return false;
        }
        return true;
    }

    public static boolean validatePassword(Editable editable, EditText editTextPassword){
        if (!TextUtils.isEmpty(editable)) {
            if (editable.length() < 6 && editTextPassword != null) {
                editTextPassword.setError("Password should be of 6 characters min ");
                return false;
            }
        } else if (editTextPassword != null) {
            editTextPassword.setError("Enter password");
            return false;
        }
        return true;
    }

    public static boolean validateRePassword(EditText editTextPass, EditText editTextRePass){
        String pass1 = editTextPass != null ? editTextPass.getText().toString() : null;
        String pass2 = editTextRePass != null ? editTextRePass.getText().toString() : null;
        if (pass1 != null && pass2 != null && !pass1.equals(pass2) && editTextRePass != null) {
            editTextRePass.setError("Password mismatch");
            return false;
        }
        return true;
    }
}
