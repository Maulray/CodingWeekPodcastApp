package model;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {

    private String name;
    private String photo;
    private ListePodcasts abo;
    private ListePodcasts podFav;
    private ListeEmission emFav;
    private ListeEmission emEcoutee;

    public Utilisateur(String name){
        this.name = name;
        this.abo = new ListePodcasts("vide");
        this.podFav = new ListePodcasts("vide");
        this.emFav = new ListeEmission();
        this.emEcoutee = new ListeEmission();
    }

    public Utilisateur(String name, String  photo){

        this.name = name;
        this.photo = photo;
        this.abo = new ListePodcasts("vide");
        this.podFav = new ListePodcasts("vide");
        this.emFav = new ListeEmission();
        this.emEcoutee = new ListeEmission();
    }

    public Utilisateur(String name,String photo, ListePodcasts listp){

        this.name = name;
        this.photo = photo;
        this.abo = new ListePodcasts("vide");
        this.podFav = new ListePodcasts("vide");
        this.emFav = new ListeEmission();
        this.emEcoutee = new ListeEmission();
    }

    public ListePodcasts getAbo() {
        return abo;
    }

    public ListePodcasts getPodFav() {
        return podFav;
    }

    public ListeEmission getEmEcoutee() {
        return emEcoutee;
    }

    public ListeEmission getEmFav() {
        return emFav;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public void sAbonner(Podcast p){
        abo.getListePodcasts().add(p);
    }

    public void addPodFav(Podcast p){
        podFav.getListePodcasts().add(p);
    }

    public void addEmFav(Emission m){
        emFav.getListeEmission().add(m);
    }

    public void addEmEcoutee(Emission m){
        emEcoutee.getListeEmission().add(m);
    }

    public void popAbo(Podcast p){
        abo.getListePodcasts().remove(p);
    }

    public void popFav(Podcast p){
        podFav.getListePodcasts().remove(p);
    }

    public boolean emDejaEcout(Emission e){
        for (Emission m: emEcoutee.getListeEmission()){
            if (m.equals(e)){
                return true;
            }
        }
        return false;
    }
    public void setUser(Utilisateur user){
        this.name=user.name;
        this.photo=user.photo;
        this.abo=user.abo;
        this.podFav=user.podFav;
        this.emFav=user.emFav;
        this.emEcoutee=user.emEcoutee;
    }

}
