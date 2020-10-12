package controller;

import Main.Launcher;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.ListePodcasts;
import model.Utilisateur;

import javax.rmi.CORBA.Util;

public class OpeningView extends GridPane {

    private String photo1 =new String("poule.png");
    private String photo2 = new String("cochon.png");
    private String photo3 = new String("poussin.png");
    private String photo4 = new String("vache.png");
    private String photo5 = new String("mouton.png");

    final double title_font = 30.0;
    final double text_font = 20.0;
    Font text = new Font(text_font);


    ListePodcasts lp = new ListePodcasts();
    //private Utilisateur user = new Utilisateur("Hervé","mouton.png");

    public OpeningView(Launcher launcher, Stage ps){

        this.getStylesheets().add("podcastcss.css");
        this.setStyle("-fx-background-color: #202020");
        this.setPrefSize(1280,800);
        this.setVgap(200);
        Label title = new Label("Qui est-ce?");
        title.setFont(new Font("Tahoma",title_font));
        title.setTranslateX(550);
        title.setTranslateY(50);
        this.add(title,0,0);

        GridPane ssGP = new GridPane();
        ssGP.setPrefSize(1280,294);
        ssGP.setHgap(20);
        ssGP.setVgap(30);

        Image image1 = new Image(photo1);
        ImageView im1 = new ImageView(image1);
        im1.setFitWidth(224);
        im1.setFitHeight(224);
        Label Des1 = new Label("Raoul la poule");
        Des1.setFont(text);
        ssGP.add(im1,0,0);
        ssGP.add(Des1,0,1);
        Des1.setTranslateX(30);
        Utilisateur user1 = new Utilisateur(Des1.getText(), photo1);
        im1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                launcher.setUser(user1);
                menuView menu = new menuView(launcher,lp, launcher.getLu(), user1, launcher.getPlayer());
                //System.out.println(launcher.getUtilisateur().getPhoto());
                launcher.switchScene(menu,1265,785,ps);
            }
        });

        Image image2 = new Image(photo2);
        ImageView im2 = new ImageView(image2);
        im2.setFitWidth(224);
        im2.setFitHeight(224);
        Label Des2 = new Label("Edmond le cochon");
        Des2.setFont(text);
        ssGP.add(im2,1,0);
        ssGP.add(Des2,1,1);
        Des2.setTranslateX(40);
        Utilisateur user2 = new Utilisateur(Des2.getText(), photo2);
        im2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                launcher.setUser(user2);
                menuView menu = new menuView(launcher,lp, launcher.getLu(), user2, launcher.getPlayer());
                //System.out.println(launcher.getUtilisateur().getPhoto());
                launcher.switchScene(menu,1265,785,ps);
            }
        });

        Image image3 = new Image(photo3);
        ImageView im3 = new ImageView(image3);
        im3.setFitWidth(224);
        im3.setFitHeight(224);
        Label Des3 = new Label("Benjamin le poussin");
        Des3.setFont(text);
        ssGP.add(im3,2,0);
        ssGP.add(Des3,2,1);
        Des3.setTranslateX(40);
        Utilisateur user3 = new Utilisateur(Des3.getText(), photo3);
        im3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                launcher.setUser(user3);
                menuView menu = new menuView(launcher,lp, launcher.getLu(), user3, launcher.getPlayer());
                //System.out.println(launcher.getUtilisateur().getPhoto());
                launcher.switchScene(menu,1265,785,ps);
            }
        });

        Image image4 = new Image(photo4);
        ImageView im4 = new ImageView(image4);
        im4.setFitWidth(224);
        im4.setFitHeight(224);
        Label Des4 = new Label("Eustache la vache");
        Des4.setFont(text);
        ssGP.add(im4,3,0);
        ssGP.add(Des4,3,1);
        Des4.setTranslateX(40);
        Utilisateur user4 = new Utilisateur(Des4.getText(), photo4);
        im4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                launcher.setUser(user4);
                menuView menu = new menuView(launcher,lp, launcher.getLu(), user4, launcher.getPlayer());
                //System.out.println(launcher.getUtilisateur().getPhoto());
                launcher.switchScene(menu,1265,785,ps);
            }
        });

        Image image5 = new Image(photo5);
        ImageView im5 = new ImageView(image5);
        im5.setFitWidth(224);
        im5.setFitHeight(224);
        Label Des5 = new Label("Hugo l'agneau");
        Des5.setFont(text);
        ssGP.add(im5,4,0);
        ssGP.add(Des5,4,1);
        Des5.setTranslateX(65);
        Utilisateur user5 = new Utilisateur(Des5.getText(), photo5);
        im5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                launcher.setUser(user5);
                menuView menu = new menuView(launcher,lp, launcher.getLu(), user5, launcher.getPlayer());
                //System.out.println(launcher.getUtilisateur().getPhoto());
                launcher.switchScene(menu,1265,785,ps);
            }
        });
        this.add(ssGP,0,1);
        ssGP.setAlignment(Pos.CENTER);

    }

    /*public Utilisateur getUser(){
        return this.user;
    }*/
}
