package guelfen.abdelheq.magasin;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.*;

public class MagazineController implements Initializable {
    @FXML
    public Label totalPriceLabel;
    @FXML
    public TableView<ProductModel> products;
    @FXML
    public TableColumn<ProductModel,String> idColumn;
    @FXML
    public TableColumn<ProductModel,String> nameColumn;
    @FXML
    public TableColumn<ProductModel,String> priceColumn;
    @FXML
    public TableColumn<ProductModel, String> quantityColumn;
    @FXML
    public ComboBox<String> combo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        ObservableList<String> items = MagazineParser.getMagazineFactures();
        combo.setItems(items);
    }

    @FXML
    protected void calculateTotalPrice() {
        clearPreviousCalculation();

        String factureId = combo.getValue();
        if (factureId == null) return;

        final Node facture = MagazineParser.getFactureById(factureId);
        if (facture == null) {
            showErrorMessage();
            return;
        }

        final FactureModel factureModel = MagazineParser.getProductsFromFacture(facture);
        showResults(factureModel, factureId);
    }

    void clearPreviousCalculation(){
        products.getItems().clear();
        totalPriceLabel.getStyleClass().clear();
    }
    void showErrorMessage(){
        totalPriceLabel.setText("This Facture doesn't exist, insert a valid facture number");
        totalPriceLabel.getStyleClass().add("label-error");
    }
    void showResults(FactureModel factureModel, String factureId) {
        String totalPrice = "The Total Price of facture (" + factureId + ") is: "
                + factureModel.totalPrice() + " DA";

        totalPriceLabel.setText(totalPrice);
        totalPriceLabel.getStyleClass().add("totalPrice");

        products.getItems().addAll(factureModel.products());
    }
}