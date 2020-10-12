package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.ListePodcasts;
import model.Podcast;
import model.Utilisateur;

import java.io.IOException;

public class abonnementView {

    Utilisateur user = new Utilisateur("Hervé");
    @FXML
    ListView<String> listeViewAbo;

    @FXML
    public void initialize() throws IOException {
        for (Podcast p : user.getAbo().getListePodcasts()) {
            listeViewAbo.getItems().add(p.getTitre());
        }

        listeViewAbo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Podcast p = user.getAbo().getByName(newValue);
                System.out.println(p.getTitre());
            }
        });
    }
}
