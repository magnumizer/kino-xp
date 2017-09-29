package sample.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.DBWrapper.ConsumableWrapper;
import sample.Model.Consumable;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConsumController implements Initializable{

    @FXML
    AnchorPane consumableAP;
    @FXML
    TextField consumableName;
    @FXML
    TextField consumablePrice;
    @FXML
    TableView<Consumable> consumableTable;
    @FXML
    TableColumn<Consumable, String> nameCol;
    @FXML
    TableColumn<Consumable, Double> priceCol;



    ConsumableWrapper consumableWrapper = new ConsumableWrapper();
    ObservableList<Consumable> consumableList;

    private void updateWorkScreen(String path) {
        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource(path));
            consumableAP.getChildren().setAll(wpAnchor);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveConsumable(ActionEvent actionEvent) {

        consumableWrapper.saveIntoDB(consumableName.getText(), Double.parseDouble(consumablePrice.getText()));
        loadConsumableFromDB();
    }

    public void updateConsumable(ActionEvent actionEvent) {
        if (!consumableTable.getSelectionModel().isEmpty()) {
            Consumable selectedConsumable = consumableTable.getSelectionModel().getSelectedItem();
            selectedConsumable.setName(consumableName.getText());
            selectedConsumable.setPrice(Double.parseDouble(consumablePrice.getText()));
            consumableWrapper.updateIntoDb(selectedConsumable);
            loadConsumableFromDB();
        }
    }

    public void deleteConsumable(ActionEvent actionEvent) {
        if (!consumableTable.getSelectionModel().isEmpty()) {
            Consumable selectedConsumable = consumableTable.getSelectionModel().getSelectedItem();
            consumableWrapper.deleteFromDB(selectedConsumable.getName());
            loadConsumableFromDB();
        }
    }

    public void onConsumableTableClick(MouseEvent mouseEvent) {
        if (!consumableTable.getSelectionModel().isEmpty()) {
            Consumable selectedConsumable = consumableTable.getSelectionModel().getSelectedItem();
            consumableName.setText(selectedConsumable.getName());
            consumablePrice.setText(selectedConsumable.getPrice() + "");
        }
    }

    public void goBack(ActionEvent actionEvent) {

        updateWorkScreen("/sample/Views/first.fxml");

    }

    void loadConsumableFromDB() {
        consumableList = consumableWrapper.getAllConsumable();
        consumableTable.getItems().setAll(consumableList);
    }

    void setupTableColumns() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupTableColumns();
        loadConsumableFromDB();
    }
}
