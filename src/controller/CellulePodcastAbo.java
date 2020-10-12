package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Podcast;
import model.Utilisateur;

public class CellulePodcastAbo extends GridPane{

    private Podcast p;

    public CellulePodcastAbo(Utilisateur currentU, Podcast p, ListView<CellulePodcastAbo> la, ListView<CellulePodcastFav> lf){

        this.p = p;

        Image imageBoutonFav = new Image("etoile.png",10,10,true,true) ;
        ImageView imageFav = new ImageView(imageBoutonFav);
        Button fav = new Button();
        fav.setOnAction(e -> {
            currentU.addPodFav(p);
            lf.getItems().add(new CellulePodcastFav(currentU,p,lf, la));
        });
        fav.setGraphic(imageFav);
        Image imageBoutonMoins = new Image("minus.png",10,10,true,true) ;
        ImageView imageMoins = new ImageView(imageBoutonMoins);
        Button moins = new Button();
        moins.setOnAction(e -> {
            currentU.popFav(p);
            la.getItems().remove(this);
            //l.refresh();
        });
        moins.setGraphic(imageMoins);

        Label nomP = new Label(" "+p.getTitre());
        this.add(nomP,2,0);
        this.add(fav, 0, 0);
        this.add(moins,2,0);
    }

    public Podcast getP() {
        return p;
    }
}