package controller;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Podcast;
import model.Utilisateur;

public class CellulePodcastFav extends GridPane{

    private Podcast p;

    public CellulePodcastFav(Utilisateur currentU, Podcast p, ListView<CellulePodcastFav> lf, ListView<CellulePodcastAbo> la){

        this.p = p;

        Image imageBoutonAbo = new Image("plus.png",10,10,true,true) ;
        ImageView imagePlus = new ImageView(imageBoutonAbo);
        Button abo = new Button();
        abo.setOnAction(e -> {
            currentU.sAbonner(p);
            la.getItems().add(new CellulePodcastAbo(currentU,p,la, lf));
        });
        abo.setGraphic(imagePlus);
        Image imageBoutonMoins = new Image("minus.png",10,10,true,true) ;
        ImageView imageMoins = new ImageView(imageBoutonMoins);
        Button moins = new Button();
        moins.setOnAction(e -> {
            currentU.popFav(p);
            lf.getItems().remove(this);
        });
        moins.setGraphic(imageMoins);

        Label nomP = new Label(p.getTitre());
        this.add(nomP,0,0);
        this.add(abo, 1, 0);
        this.add(moins,3,0);
    }

    public Podcast getP() {
        return p;
    }
}

