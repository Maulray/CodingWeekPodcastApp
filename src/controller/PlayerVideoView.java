package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Emission;
import model.Utilisateur;


public class PlayerVideoView extends Group {

    double width = 1280;
    double height = 750;

    Button play_pause = new Button();
    ImageView image_bouton;
    Image img_play;
    Image img_pause;
    Media media;
    MediaPlayer mediaPlayer;
    MediaView mediaview;

    Rectangle barre_lecture;
    double largeur_barre = width;

    public PlayerVideoView(Emission em, Utilisateur u) {
        super();
        media = new Media(em.getDownload_url_video());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.setStartTime(Duration.ZERO);
                mediaPlayer.setStopTime(media.getDuration().subtract(Duration.millis(50)));
            }
        });
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
            }
        });


        mediaview = new MediaView(mediaPlayer);
        mediaview.setFitWidth(width);
        mediaview.setFitHeight(height);
        this.getChildren().add(mediaview);

        Group fonctions = new Group();

        Rectangle fond = new Rectangle(width, 50, Color.web("#333333"));
        fond.setOpacity(0.5);
        fonctions.getChildren().add(fond);
        fonctions.setTranslateY(height - 50);
        this.getChildren().add(fonctions);

        //création du bouton play/pause

        img_play = new Image("http://www.lynnettechadwick.com/wp-content/uploads/2015/04/play-button.png");
        img_pause = new Image("https://cdn4.iconfinder.com/data/icons/media-controls-4/100/pause-512.png");
        ImageView image_bouton = new ImageView(img_play);
        image_bouton.setFitHeight(42);
        image_bouton.setFitWidth(42);
        play_pause.setPrefSize(50, 50);
        play_pause.setTranslateY(height - 50);
        play_pause.setGraphic(image_bouton);
        this.play_pause.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                if (mediaPlayer.getStatus() == MediaPlayer.Status.valueOf("PLAYING")) {//pause
                    image_bouton.setImage(img_play);
                    mediaPlayer.pause();
                } else {//play
                    image_bouton.setImage(img_pause);
                    mediaPlayer.play();
                    u.addEmEcoutee(em);
                }
            }
        });

//        //création de la barre de lecture
        Group barres = new Group();
        barres.setCursor(Cursor.HAND);
        Rectangle barre_fond = new Rectangle(largeur_barre, 50, Color.web("#111111"));
        barre_lecture = new Rectangle(0, 50, Color.web("#0078ff"));

        //On ajuste la longueur de la barre de lecture au taux de lecture de la vidéo
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal){
                double currentTime = mediaPlayer.getCurrentTime().toSeconds();
                double totalDuration = mediaPlayer.getTotalDuration().toSeconds();
                barre_lecture.setWidth(currentTime/totalDuration*largeur_barre);
            }
        });
        barres.getChildren().add(barre_fond);
        barres.getChildren().add(barre_lecture);
        //Quand on clique sur la barre de lecture, le lecteur video change la position de la lecture
        barres.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                mediaPlayer.seek(Duration.millis(me.getX() / largeur_barre * media.getDuration().toMillis()));
                barre_lecture.setWidth((me.getX() / largeur_barre * media.getDuration().toMillis()) / (mediaPlayer.getTotalDuration().toMillis()) * largeur_barre);
            }
        });
        fonctions.getChildren().add(barres);
        this.getChildren().add(play_pause);
    }
}