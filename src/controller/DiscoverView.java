package controller;

import Main.Launcher;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class DiscoverView extends BorderPane {

    private Emission e;
    ListView<CelluleEmission> ems = new ListView<>();
    Button download = new Button("Télécharger");
    Button downloadvids = new Button("Télécharger");
    Button listenEmission = new Button("Jouer");
    Button viewvids = new Button("Jouer");
    Button audio = new Button("Audio");
    Button video = new Button("Video");
    GridPane description = new GridPane();
    Label titlelbl = new Label();
    Label durationlbl = new Label();
    Label authorlbl = new Label();
    Label publilbl = new Label();
    Label descriptlbl = new Label();
    Label title = new Label();
    Label duration = new Label();
    Label author = new Label();
    Label publi = new Label();
    Label descript = new Label();

    public DiscoverView(Launcher launcher, ListePodcasts lp, menuView menuview, Utilisateur currentu, PlayerAudioView player){
        description.setVisible(false);
        this.getStylesheets().add("podcastcss.css");
        this.setStyle("-fx-background-color: #202020");


        title.setMaxWidth(350);
        title.setWrapText(true);
        descript.setMaxWidth(350);
        descript.setWrapText(true);
        titlelbl.setText("Titre : ");
        titlelbl.setMinSize(45,30);
        description.add(titlelbl, 0, 0);
        description.add(title, 1, 0);
        durationlbl.setText("Duree : ");
        durationlbl.setMinSize(45,30);
        description.add(durationlbl, 0,1);
        description.add(duration, 1, 1);
        authorlbl.setText("Auteur : ");
        authorlbl.setMinSize(45,30);
        description.add(authorlbl, 0,2);
        description.add(author, 1, 2);
        publilbl.setText("Date de publication : ");
        publilbl.setMinSize(150,30);
        description.add(publilbl, 0,3);
        description.add(publi, 1, 3);
        descriptlbl.setText("Description : ");
        descriptlbl.setMinSize(60,30);
        description.add(descriptlbl, 0,4);
        description.add(descript, 1, 4);
        description.add(download, 0, 6);
        download.setOnAction(ke -> {
            URLConnection conn;
            try {
                conn = new URL(e.getDownload_url_audio()).openConnection();
                InputStream is = conn.getInputStream();

                OutputStream outstream = new FileOutputStream(new File(e.getTitre()+".mp3"));
                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) > 0) {
                    outstream.write(buffer, 0, len);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Telechargement musique");
                alert.setHeaderText("Fichier telecharge !");
                alert.showAndWait();
                outstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        description.add(downloadvids, 1, 6);
        downloadvids.setOnAction(ke -> {
            URLConnection conn;
            try {
                conn = new URL(e.getDownload_url_video()).openConnection();
                InputStream is = conn.getInputStream();

                OutputStream outstream = new FileOutputStream(new File(e.getTitre()+".mp4"));
                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) > 0) {
                    outstream.write(buffer, 0, len);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Telechargement video");
                alert.setHeaderText("Pas fini encore revenez plus tard !");
                alert.showAndWait();
                outstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        description.add(listenEmission, 0, 6);
        this.listenEmission.setOnAction(ke->{
            player.listen(e);
            currentu.addEmEcoutee(e);
        });

        description.add(viewvids,1,6);
        viewvids.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player.mediaPlayer.pause();
                Image img_play = new Image("http://www.lynnettechadwick.com/wp-content/uploads/2015/04/play-button.png");
                player.image_bouton.setImage(img_play);
                PlayerVideoView playerVideoView = new PlayerVideoView(e, currentu);
                StackPane secondaryLayout= new StackPane();
                secondaryLayout.getChildren().add(playerVideoView);
                Scene secondScene = new Scene(secondaryLayout,1280,750);

                Stage newWindow = new Stage();
                String title = e.getTitre() + " - " + e.getAuteur();
                newWindow.setTitle(title);
                newWindow.setScene(secondScene);

                newWindow.show();


                newWindow.setOnCloseRequest((e -> playerVideoView.mediaPlayer.stop()));
            }
        });





        top t = new top(launcher, menuview, player, currentu);
        BorderPane.setMargin(t, new Insets(10,20,0,20));


        ListeEmission UnRandomEm = new ListeEmission();
        ListeEmission RandomEm = new ListeEmission();

        for(Podcast p:lp.getListePodcasts()){
            for(Emission e : p.getItEmissions()){
                UnRandomEm.addEmission(e);
            }
        }
        for (int i =0; i< (UnRandomEm.getListeEmission()).size();i++){
            Emission e = UnRandomEm.getRandomList();
            if (!(e.getDownload_url_audio().equals("Inconnu"))) {
                RandomEm.addEmission(e);
                CelluleEmission em = new CelluleEmission(currentu, e);
                ems.getItems().add(em);
            }
        }

        ems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CelluleEmission>() {
            @Override
            public void changed(ObservableValue<? extends CelluleEmission> observable, CelluleEmission oldValue, CelluleEmission newValue) {
                e = newValue.getE();
                description.setVisible(true);
                if (e != null) {
                    setDescription();
                }
            }
        });

        description.setVgap(20);
        description.add(audio,0,5);
        description.add(video,1,5);
        audio.setTranslateX(100);
        video.setTranslateX(180);
        listenEmission.setTranslateX(140);
        download.setTranslateX(40);
        downloadvids.setTranslateX(130);
        viewvids.setTranslateX(230);
        this.setTop(t);
        this.setCenter(ems);
        this.setRight(description);
        this.setBottom(player);
        ;
    }

    public void setDescription() {
        title.setText(e.getTitre());
        duration.setText(e.getDuration());
        author.setText(e.getAuteur());
        publi.setText(e.getDate_publi());
        descript.setText(e.getDescription());
        if (e.getDownload_url_audio().equals("Inconnu")) {
            download.setDisable(true);
        }
        else {
            download.setDisable(false);
        }
        if (e.getDownload_url_video().equals("Inconnu")) {
            downloadvids.setDisable(true);
        }
        else {
            downloadvids.setDisable(false);
        }
        if (e.getWatch_url().substring((e.getWatch_url()).length() -3).equals("mp3")){
            viewvids.setDisable(true);
        }else{
            viewvids.setDisable(false);
        }
    }
}
