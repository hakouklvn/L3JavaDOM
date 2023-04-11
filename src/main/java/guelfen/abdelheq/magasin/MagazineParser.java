package guelfen.abdelheq.magasin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MagazineParser {
    static Element xmlRoot;

    static public void initElement() throws Exception {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final URL res =  MagazineController.class.getResource("magasin.xml");

        assert res != null;
        final Document doc = builder.parse(res.getFile());

        xmlRoot = doc.getDocumentElement();
    }
    public static ObservableList<String> getMagazineFactures() {
        final NodeList factures = xmlRoot.getElementsByTagName("facture");

        ObservableList<String> facturesIds = FXCollections.observableArrayList();
        for (int i = 0; i < factures.getLength(); i++) {
            final Node facture = factures.item(i);
            final Node numero = facture.getAttributes().getNamedItem("numero");
            facturesIds.add(numero.getNodeValue());
        }

        return facturesIds;
    }

    static public Node getFactureById(final String num) {
        final NodeList factures = xmlRoot.getElementsByTagName("facture");
        return findNodeById(factures,"numero", num);
    }
    static public FactureModel getProductsFromFacture(final Node facture) {
        final NodeList nodes = facture.getChildNodes();
        final List<ProductModel> products = new ArrayList<>();
        double totalPrice = 0.0;

        for (int j = 0; j < nodes.getLength(); j++) {
            final Node product_vends = nodes.item(j);
            final NamedNodeMap product_vend_attributes = product_vends.getAttributes();

            if (product_vend_attributes != null) {
                final String refId = product_vend_attributes.getNamedItem("ref-p").getNodeValue();
                final String quantity = product_vend_attributes.getNamedItem("quantite").getNodeValue();

                final Node productNode = getProductById(refId);
                final NamedNodeMap productAttributes = productNode.getAttributes();

                final String price = productAttributes.getNamedItem("prix").getNodeValue();
                final String name = productAttributes.getNamedItem("Etiquette").getNodeValue();

                totalPrice += Double.parseDouble(price) * Double.parseDouble(quantity);

                ProductModel productModel = new ProductModel(refId, quantity, price, name);
                products.add(productModel);
            }
        }

        return new FactureModel(products, totalPrice);
    }
    static public Node getProductById(final String id) {
        final NodeList products = xmlRoot.getElementsByTagName("produit");
        return findNodeById(products,"num", id);
    }
    public static Node findNodeById(NodeList nodeList, String item, String attribute){
        Node selectedNode = null;

        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node node = nodeList.item(i);
            final Node numero = node.getAttributes().getNamedItem(item);

            if (numero.getNodeValue().equals(attribute)) {
                selectedNode = node;
                break;
            }
        }
        return selectedNode;
    }
}
