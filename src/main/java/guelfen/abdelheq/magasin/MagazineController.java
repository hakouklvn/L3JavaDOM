package guelfen.abdelheq.magasin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.*;

public class MagazineController implements Initializable {
    @FXML
    public TextField factureId;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    protected void calculateTotalPrice() throws Exception {
        final Element xmlRoot = initElement();

        if (factureId == null || factureId.getText().isEmpty()) return;
        products.getItems().clear();
        totalPriceLabel.getStyleClass().clear();

        final String id = factureId.getText();

        final Node facture = MagazineController.getFactureByNumber(xmlRoot, id);

        if (facture == null) {
            totalPriceLabel.setText("This Facture doesn't exist, insert a valid facture number");
            totalPriceLabel.getStyleClass().add("label-error");
            return;
        }
        totalPriceLabel.getStyleClass().add("totalPrice");

        final FactureModel factureModel = MagazineController.getFactureDetails(xmlRoot, facture);

        String totalPrice = "The Total Price of facture (" + id + ") is: ".toLowerCase() + factureModel.totalPrice() + " DA";
        totalPriceLabel.setText(totalPrice);

        products.getItems().addAll(factureModel.products());
    }

    static public Element initElement() throws Exception {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document doc = builder.parse(new File("src/main/resources/guelfen/abdelheq/magasin/magasin.xml"));

        return doc.getDocumentElement();
    }

    static public Node getFactureByNumber(Element root, final String num) {
        final NodeList factures = root.getElementsByTagName("facture");
        Node selectedFacture = null;

        if (num.isEmpty()) {
            return null;
        }

        for (int i = 0; i < factures.getLength(); i++) {
            final Node facture = factures.item(i);
            final NamedNodeMap attributes = facture.getAttributes();

            final Node numero = attributes.getNamedItem("numero");

            if (numero.getNodeValue().equals(num)) {
                selectedFacture = facture;
                break;
            }
        }

        return selectedFacture;
    }

    static public FactureModel getFactureDetails(Element root, final Node facture) {
        double totalPrice = 0.0;
        final List<ProductModel> products = new ArrayList<>();

        if (facture == null) {
            return new FactureModel(null, 0.0);
        }

        final List<ProductModel> productModels = getProductsFromFacture(facture);
        for (final ProductModel productModel : productModels) {
            final Node productNode = getProductById(root, productModel.getId());
            final NamedNodeMap attributes = productNode.getAttributes();

            final String price = attributes.getNamedItem("prix").getNodeValue();
            final String name = attributes.getNamedItem("Etiquette").getNodeValue();

            products.add(new ProductModel(productModel.getId(),productModel.getQuantity(),price,name));

            totalPrice += Double.parseDouble(price) * Double.parseDouble(productModel.getQuantity());
        }

        FactureModel factureModel= new FactureModel(products, totalPrice);
        return factureModel;
    }

    static public List<ProductModel> getProductsFromFacture(final Node facture) {
        final NodeList nodes = facture.getChildNodes();
        final List<ProductModel> products = new ArrayList<ProductModel>();

        for (int j = 0; j < nodes.getLength(); j++) {
            final Node product_vends = nodes.item(j);
            final NamedNodeMap product_vend_attributes = product_vends.getAttributes();

            if (product_vend_attributes != null) {
                final String refId = product_vend_attributes.getNamedItem("ref-p").getNodeValue();
                final String quantity = product_vend_attributes.getNamedItem("quantite").getNodeValue();

                ProductModel productModel = new ProductModel(refId, quantity, "", "");
                products.add(productModel);
            }
        }

        return products;
    }

    static public Node getProductById(Element root, final String id) {
        final NodeList products = root.getElementsByTagName("produit");
        Node selectedFacture = null;

        for (int i = 0; i < products.getLength(); i++) {
            final Node facture = products.item(i);
            final NamedNodeMap attributes = facture.getAttributes();

            final String numero = attributes.getNamedItem("num").getNodeValue();

            if (numero.equals(id)) {
                selectedFacture = facture;
                break;
            }
        }

        return selectedFacture;
    }
}