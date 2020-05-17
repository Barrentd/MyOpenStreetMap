package com.example.myopenstreetmap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SetActivity extends AppCompatActivity {
    MainActivity mainActivity;

    private Button buttonCreate;
    private Button buttonExit;
    private EditText nom;
    private EditText date;
    private EditText lieu;
    private EditText Lat;
    private EditText Long;

    Message mes = Message.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RendezVous rdv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        buttonExit = findViewById(R.id.buttonExit);
        buttonCreate = findViewById(R.id.buttonCreate);
        nom = findViewById(R.id.editTextNom);
        date = findViewById(R.id.editTextDate);
        lieu = findViewById(R.id.editTextLieu);
        Lat = findViewById(R.id.editTextLat);
        Long = findViewById(R.id.editTextLong);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    creerRendezvous();
                }catch (Exception e){
                    Log.d("RDV", e.toString());
                }
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void creerRendezvous(){

        double dLat = Double.parseDouble(Lat.getText().toString());
        double dLong = Double.parseDouble(Long.getText().toString());
        if(nom.getText() == null || date.getText() == null || lieu.getText() == null || dLat == 0 || dLong == 0){
            Toast.makeText(getApplicationContext(), "Veuillez remplir tout les champs", Toast.LENGTH_SHORT).show();
        }
        else{
            rdv = new RendezVous(nom.getText().toString(),date.getText().toString(),lieu.getText().toString(),dLat,dLong);
            Toast.makeText(getApplicationContext(), "Reunion créé", Toast.LENGTH_SHORT).show();
            createCollection();
            Intent myIntent = new Intent(MainActivity.getContext(), Messagerie.class);
            startActivity(myIntent);
        }
    }

    public void createCollection(){
        Map<String, Object> user = new HashMap<>();
        user.put("Nom", rdv.getNom());
        user.put("Date", rdv.getDate());
        user.put("Lieu", rdv.getLieu());
        user.put("Lat", rdv.getLat());
        user.put("Long", rdv.getLong());

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
