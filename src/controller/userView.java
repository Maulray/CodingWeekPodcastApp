package controller;

import Main.Launcher;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.*;


public class userView extends BorderPane {

    private String podcastTitle;

    public userView (Launcher launcher, Utilisateur user, PlayerAudioView player) {
        this.getStylesheets().add("podcastcss.css");
        this.setStyle("-fx-background-color: #202020");
        launcher.setUser(user);
        this.setCenter(new playlistsUser(user));
        profile p = new profile(user);
        BorderPane.setMargin(p, new Insets(20,20,0,20));
        this.setLeft(p);
        top t = new top(launcher, launcher.getMenu(), player, user);
        BorderPane.setMargin(t, new Insets(10,20,0,20));
        this.setTop(t);
        this.setBottom(player);


    }

    public String getPodcastTitle() {
        return podcastTitle;
    }
    public void setPodcastTitle(String podcastTitle) {
        this.podcastTitle = podcastTitle;
    }
}
