package guelfen.abdelheq.magasin;

import javafx.beans.property.SimpleStringProperty;

public class ProductModel {
    private final SimpleStringProperty id;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty price;
    private final SimpleStringProperty name;

    public ProductModel(String id, String quantity, String price, String name) {
        this.id = new SimpleStringProperty(id);
        this.quantity = new SimpleStringProperty(quantity);
        this.price = new SimpleStringProperty(price);
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.get();
    }
    public String getPrice() {
        return price.get();
    }
    public String getQuantity() {
        return quantity.get();
    }
    public String getId() {
        return id.get();
    }

}
