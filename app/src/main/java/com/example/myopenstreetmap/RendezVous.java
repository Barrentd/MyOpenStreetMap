package com.example.myopenstreetmap;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class RendezVous {

    private static int id = 0;
    private String nom;
    private ArrayList<Message> participants;
    private ArrayList<String> messages;
    private Date date;
    private String lieu;
    private double Lat;
    private double Long;

    MainActivity main = new MainActivity();

    public RendezVous(String nom, Date date, String lieu, Double Lat, Double Long){
        this.id = ++id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.Lat = Lat;
        this.Long = Long;
    }

    public void addParticipant(String nom, Message participant){
        if(this.getNomrendezvous() != nom){
            boolean inscrit = false;
            for(Message particip:participants){
                if(particip.getEnvoyeur() == participant.getEnvoyeur()){
                    inscrit = true;
                }else{
                    inscrit = false;
                }
            }
            if(inscrit = false){
                participants.add(participant);
            }else{
                Toast.makeText(main.getBaseContext(), "Déja inscrit", Toast.LENGTH_SHORT);
            }
        }else{
            Toast.makeText(main.getBaseContext(), "La réunion n'existe pas", Toast.LENGTH_SHORT);
        }
    }

    public String getNomrendezvous(){
        return this.nom;
    }

    public ArrayList getconversation(){
        return this.messages;
    }



}
