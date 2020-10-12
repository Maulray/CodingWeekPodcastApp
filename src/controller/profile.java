package controller;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Utilisateur;
import javafx.scene.control.ContentDisplay;


public class profile extends VBox {


    public profile(Utilisateur user){
        super();
        Image image = image = new Image(user.getPhoto(),300,200,true,true) ;
        ImageView iv = new ImageView(image);
        Label l = new Label(user.getName());
        this.getChildren().addAll(iv,l);
    }
}
