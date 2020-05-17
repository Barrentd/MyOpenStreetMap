package com.example.myopenstreetmap;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



public class SearchActivity extends AppCompatActivity {

    MainActivity mainActivity;

    private Button buttonSearch;
    private Button buttonCreate;
    private Button buttonExit;

    Message mes = Message.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Log.d("id", String.valueOf(mes.getId()));

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonCreate = findViewById(R.id.buttonCreate);
        buttonExit = findViewById(R.id.buttonExit);

        verif();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("nom", mes.getEnvoyeur());
            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(mainActivity.getContext(), SetActivity.class);
                startActivity(myIntent);
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void verif(){
        LinearLayout layout = new LinearLayout(this);
        final EditText input = new EditText(SearchActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(60, 0, 60, 0);
        layout.addView(input, lp);

        if(mes.getEnvoyeur() == null){
            new AlertDialog.Builder(this)
                    .setTitle("Nom")
                    .setMessage("Pour discuter il vous faut un nom !\nVeuillez choisir un nom :")
                    .setView(layout)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Boolean wantToCloseDialog = false;
                            if(input.getText().toString().matches("")){
                                Toast.makeText(MainActivity.getContext(),"Veuillez renseigner un nom",Toast.LENGTH_LONG).show();
                            }else{
                                mes.setEnvoyeur(input.getText().toString());
                            }
                            if(wantToCloseDialog){
                                dialog.dismiss();
                            }
                        }
                    })
                    .show()
                    .setCanceledOnTouchOutside(false);
        }
    }




}
