package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConsumableData {


    private ObservableList<Consumable> consumableObservableList = FXCollections.observableArrayList();

    public ObservableList<Consumable> getConsumableObservableList() {
        return consumableObservableList;
    }
}
