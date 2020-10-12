package model;

import java.util.ArrayList;

public class ListeEmission {

    private ArrayList<Emission> listeEmission;

    public ListeEmission(){
        this.listeEmission = new ArrayList<Emission>();
    }

    public ArrayList<Emission> getListeEmission() {
        return this.listeEmission;
    }

    public void addEmission(Emission m){
        this.listeEmission.add(m);
    }

    public Emission getByName(String name){

        for (Emission i:listeEmission){
            String titre = i.getTitre();
            if (titre == name ) {
                return i;
            }
        }
        return null;
    }

    public Boolean isEmpy(){
        return (this.listeEmission==null);
    }
    public Emission getRandomList() {
        int index = (int)(Math.random()*this.listeEmission.size());
        return this.listeEmission.get(index);

    }

}
