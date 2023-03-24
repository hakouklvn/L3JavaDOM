package guelfen.abdelheq.magasin;

import javafx.beans.property.SimpleStringProperty;

public class ProductModel {
    private SimpleStringProperty id;
    private SimpleStringProperty quantity;
    private SimpleStringProperty price;
    private SimpleStringProperty name;

    public ProductModel(String id, String quantity, String price, String name) {
        this.id = new SimpleStringProperty(id);
        this.quantity = new SimpleStringProperty(quantity);
        this.price = new SimpleStringProperty(price);
        this.name = new SimpleStringProperty(name);
    }


    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }
}
