package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ListeUsers {

    private ArrayList<Utilisateur> users;
    ListePodcasts lp= new ListePodcasts();

    public ListeUsers(){
        users = new ArrayList<Utilisateur>();
        users.add(new Utilisateur("Roger", "ressources/poule.png", lp));
        users.add(new Utilisateur("Robert", "ressources/back.png", lp));
    }

    public ArrayList<Utilisateur> getUsers() {
        return users;
    }

    public ObservableList<String> getUsersNames(){
        ArrayList<String> names = new ArrayList<String>();
        for(Utilisateur u:users){
            names.add(u.getName());
        }
        ObservableList<String> list //
                = FXCollections.observableArrayList(names);
        return list;
    }

    public Utilisateur getByName(String name){

        for (Utilisateur i:users){
            String nom = i.getName();
            if (nom == name ) {
                return i;
            }
        }
        return null;
    }
}
