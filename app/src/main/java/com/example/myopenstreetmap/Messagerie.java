package com.example.myopenstreetmap;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Messagerie extends AppCompatActivity {

    MainActivity mainActivity;
    Message mes = Message.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

    }
}
