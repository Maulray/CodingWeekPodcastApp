package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Podcast;
import model.Utilisateur;

import java.io.IOException;

public class favPodView {

    Utilisateur user = new Utilisateur("Hervé");
    @FXML
    ListView<String> listeViewFavP;

    @FXML
    public void initialize() throws IOException {
        for (Podcast p : user.getAbo().getListePodcasts()) {
            listeViewFavP.getItems().add(p.getTitre());
        }

        listeViewFavP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Podcast p = user.getPodFav().getByName(newValue);
                System.out.println(p.getTitre());
            }
        });
    }
}
