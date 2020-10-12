package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.Cell;
import javafx.scene.control.ListCell;
import model.Emission;

public class ListeCelluleEmission extends ListCell<CelluleEmission> {

    public ListeCelluleEmission() {

    }

    // @Override
    protected void updateItem(CelluleEmission c, boolean empty) {

        // calling super here is very important - don't skip this!
        super.updateItem(c, empty);

        //p = lp.getByName(newValue.getTitre());
        setGraphic(c);
    }


}
