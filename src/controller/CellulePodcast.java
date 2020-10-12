package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Podcast;
import model.Utilisateur;

public class CellulePodcast extends GridPane{

    private Podcast p;

    public CellulePodcast(Utilisateur currentU, Podcast p, ListView<CellulePodcast> l){

        this.p = p;

        Image imageBoutonAbo = new Image("plus.png",10,10,true,true) ;
        ImageView imagePlus = new ImageView(imageBoutonAbo);
        Button abo = new Button();
        abo.setOnAction(e -> {
            currentU.sAbonner(p);
        });
        abo.setGraphic(imagePlus);
        Image imageBoutonFav = new Image("etoile.png",10,10,true,true) ;
        ImageView imageFav = new ImageView(imageBoutonFav);
        Button fav = new Button();
        fav.setOnAction(e -> {
            currentU.addPodFav(p);
        });
        fav.setGraphic(imageFav);


        
        Label nomP = new Label(p.getTitre());
        this.add(nomP,3,0);
        this.add(abo, 0, 0);
        this.add(fav,1,0);


    }

    public Podcast getP() {
        return p;
    }
}
