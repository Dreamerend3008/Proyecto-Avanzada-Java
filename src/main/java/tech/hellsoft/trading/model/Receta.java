package tech.hellsoft.trading.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import tech.hellsoft.trading.enums.Product;

public class Receta implements Serializable {
    private static final long serialVersionUID = 1L;

    private Product producto;
    private Map<Product, Integer> ingredientes;
    private double bonusPremium;

    public Receta(){
        this.ingredientes = new HashMap<>();
    }

    public Receta(Product producto, Map<Product, Integer> ingredientes, double bonusPremium) {
        this.producto = producto;
        this.ingredientes = ingredientes;
        this.bonusPremium = bonusPremium;
    }

    public double getBonusPremium() {
        return bonusPremium;
    }

    public void setBonusPremium(double bonusPremium) {
        this.bonusPremium = bonusPremium;
    }

    public Map<Product, Integer> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(Map<Product, Integer> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Product getProducto() {
        return producto;
    }

    public void setProducto(Product producto) {
        this.producto = producto;
    }

    public boolean isPremium() {
        return ingredientes != null && !ingredientes.isEmpty();
    }

    @Override
    public String toString() {
        if (isPremium()) {
            return String.format("Receta{producto='%s', ingredientes=%s, bonus=%.0f%%}",
                    producto, ingredientes, (bonusPremium - 1.0) * 100);
        } else {
            return String.format("Receta{producto='%s', básico}", producto);
        }
    }
}
