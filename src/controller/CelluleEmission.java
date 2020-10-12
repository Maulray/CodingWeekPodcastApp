package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Emission;
import model.Utilisateur;

public class CelluleEmission extends GridPane {

    private Emission e;

    public CelluleEmission(Utilisateur currentU, Emission e) {

        this.e = e;

        Image imageBoutonFav = new Image("etoile.png",10,10,true,true) ;
        ImageView imageFav = new ImageView(imageBoutonFav);
        Button fav = new Button();
        fav.setOnAction(ep -> {
            currentU.addEmFav(e);
        });
        fav.setGraphic(imageFav);

        Label nomP = new Label(" "+e.getTitre());
        this.add(nomP,1,0);
        this.add(fav,0,0);
    }

    public Emission getE() {
        return e;
    }






}
