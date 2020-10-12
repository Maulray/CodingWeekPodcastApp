package controller;

import Main.Launcher;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Utilisateur;

import javax.rmi.CORBA.Util;

public class top extends HBox {

    private Launcher launcher;
    private  menuView menuview;

    Image imageBouton = new Image("back.png",20,20,true,true) ;
    ImageView imageBack = new ImageView(imageBouton);

    public top(Launcher launcher, menuView menuview, PlayerAudioView player, Utilisateur user){
        this.launcher = launcher;
        this.menuview = menuview;
        Button back = new Button();
        back.setOnAction(e -> {
            menuView menu = new menuView(menuview.getLauncher(), menuview.getLp(), menuview.getLu(),user, player);
            launcher.switchScene(menu,1280, 800, this.launcher.getPrimaryStage());
        });
        back.setGraphic(imageBack);
        back.setMaxWidth(1);
        back.setMaxHeight(10);
        back.setPrefHeight(1);
        back.setPrefSize(10,10);

        this.getChildren().addAll(back);

    }
}
