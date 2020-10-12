package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import model.*;

import java.util.List;
import java.util.PrimitiveIterator;


public class ListeCellulePodcastAbo extends ListCell<CellulePodcastAbo> {


    public ListeCellulePodcastAbo() {

    }

    // @Override
    protected void updateItem(CellulePodcastAbo c, boolean empty) {

        // calling super here is very important - don't skip this!
        super.updateItem(c, empty);

        //p = lp.getByName(newValue.getTitre());
        setGraphic(c);
    }

}
