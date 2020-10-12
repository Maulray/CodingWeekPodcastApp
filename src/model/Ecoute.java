package model;

public class Ecoute {

    private Emission em;
    private Utilisateur user;

    public Ecoute(Emission em, Utilisateur user){
        this.em = em;
        this.user = user;
    }

    public Ecoute() {

    }

    public Emission getEm() {
        return em;
    }

    public Utilisateur getUser() {
        return user;
    }
}
