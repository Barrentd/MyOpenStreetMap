package com.example.myopenstreetmap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date myDate = null;
        try {
            myDate = df.parse(String.valueOf(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        double dLat = Double.parseDouble(Lat.getText().toString());
        double dLong = Double.parseDouble(Long.getText().toString());
        if(nom.getText() == null || date.getText() == null || lieu.getText() == null || dLat == 0 || dLong == 0){
            Toast.makeText(getApplicationContext(), "Veuillez remplir tout les champs", Toast.LENGTH_SHORT).show();
        }
        else{
            RendezVous rdv = new RendezVous(nom.toString(),myDate,lieu.toString(),dLat,dLong);
        }
    }
}
