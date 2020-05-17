package com.example.myopenstreetmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Message {

    private static Message messInstance;
    private static int id = 0;
    private String envoyeur;

    MyLocationListener locationListener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Message(){
        this.id = ++id;
    }

    public static Message getInstance(){
        if (messInstance == null){ //if there is no instance available... create new one
            messInstance = new Message();
        }

        return messInstance;
    }

    public void setEnvoyeur(String envoyeur) {
        this.envoyeur = envoyeur;
    }

    public String getEnvoyeur(){
        return envoyeur;
    }

    public int getId() {
        return id;
    }

    public void createCollection(){
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Alan");
        user.put("middle", "Mathison");
        user.put("last", "Turing");
        user.put("born", 1912);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("error", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("error", "Error adding document", e);
                    }
                });
    }
}
