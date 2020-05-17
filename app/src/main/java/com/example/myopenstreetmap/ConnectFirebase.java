package com.example.myopenstreetmap;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;

public class ConnectFirebase {

    private FirebaseAuth mAuth;

    public void initializeFirebase(){
        try {
            mAuth = FirebaseAuth.getInstance();
        }catch (Exception e){
            Log.d("firebase",e.toString());
        }
    }

    public void onStart() {
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
            Toast.makeText(MainActivity.getContext(),"Signed In successfully",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(MainActivity.getContext(),"Didnt signed in",Toast.LENGTH_LONG).show();
        }

    }

    public String getInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = "vide";
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            uid = user.getUid();
        }
        return uid;
    }


}
