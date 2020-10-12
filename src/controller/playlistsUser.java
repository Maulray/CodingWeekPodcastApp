package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class playlistsUser extends GridPane {

    Label labelmf = new Label("Emissions Favorites");
    Label labelh = new Label("Historique");
    Label labelpf = new Label("Podcasts favoris");
    Label labela = new Label("Abonnements");
    ListView<String> EmissionFavList = new ListView<String>();
    ListView<CellulePodcastAbo> AboList= new ListView<CellulePodcastAbo>();
    ListView<CellulePodcastFav> PodcastFavList = new ListView<CellulePodcastFav>();
    ListView<String> Historique = new ListView<String>();

    public playlistsUser(Utilisateur user) {
        super();

        ListePodcasts abo = user.getAbo();
        ListePodcasts favPod = user.getPodFav();
        ListeEmission hist = user.getEmEcoutee();
        ListeEmission favEm = user.getEmFav();

        if(!abo.isEmpty()){
            for (Podcast p : abo.getListePodcasts()) {
                AboList.getItems().add(new CellulePodcastAbo(user, p, AboList, PodcastFavList));
            }
            AboList.setCellFactory(new Callback<ListView<CellulePodcastAbo>, ListCell<CellulePodcastAbo>>() {

                @Override public ListCell<CellulePodcastAbo> call(ListView<CellulePodcastAbo> list) {
                    return new ListeCellulePodcastAbo();
                }
            });
            AboList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CellulePodcastAbo>() {
                @Override
                public void changed(ObservableValue<? extends CellulePodcastAbo> observable, CellulePodcastAbo oldValue, CellulePodcastAbo newValue) {
                    Podcast p = abo.getByName(newValue.getP().getTitre());
                    //AboList.refresh();
                }
            });
        }

        if (!favPod.isEmpty()){
            for (Podcast p : favPod.getListePodcasts()) {
                PodcastFavList.getItems().add(new CellulePodcastFav(user, p, PodcastFavList, AboList));
            }
            PodcastFavList.setCellFactory(new Callback<ListView<CellulePodcastFav>, ListCell<CellulePodcastFav>>() {

                @Override public ListCell<CellulePodcastFav> call(ListView<CellulePodcastFav> list) {
                    return new ListeCellulePodcastFav();
                }
            });
            PodcastFavList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CellulePodcastFav>() {
                @Override
                public void changed(ObservableValue<? extends CellulePodcastFav> observable, CellulePodcastFav oldValue, CellulePodcastFav newValue) {
                    Podcast p = favPod.getByName(newValue.getP().getTitre());
                }
            });
        }

        if (!favEm.isEmpy()){
            for (Emission m : favEm.getListeEmission()) {
                EmissionFavList.getItems().add(m.getTitre());
            }
            EmissionFavList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    Emission m = favEm.getByName(newValue);
                }
            });
        }

        if (!hist.isEmpy()){
            for (Emission m : hist.getListeEmission()) {
                Historique.getItems().add(m.getTitre());
            }
            Historique.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    Emission m = hist.getByName(newValue);
                }
            });
        }


        AboList.setPrefSize(250, 590);
        Historique.setPrefSize(250, 590);
        PodcastFavList.setPrefSize(250, 590);
        EmissionFavList.setPrefSize(250, 590);
        this.setPrefSize(1280, 800);
        this.add(AboList, 0, 1);
        this.add(PodcastFavList, 1, 1);
        this.add(EmissionFavList, 2, 1);
        this.add(Historique, 3, 1);
        this.add(labela, 0, 0);
        this.add(labelpf, 1, 0);
        this.add(labelmf, 2, 0);
        this.add(labelh, 3, 0);
        labelmf.setTranslateX(70);
        labela.setTranslateX(85);
        labelpf.setTranslateX(75);
        labelh.setTranslateX(87);


    }
}


