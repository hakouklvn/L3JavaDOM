package guelfen.abdelheq.magasin;

import java.util.List;

public record FactureModel(List<ProductModel> products, double totalPrice) {
}
