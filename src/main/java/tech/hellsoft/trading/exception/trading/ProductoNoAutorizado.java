package tech.hellsoft.trading.exception.trading;

import java.util.ArrayList;
import tech.hellsoft.trading.enums.Product;

public class ProductoNoAutorizado extends TradingException {
    private String producto;
    private ArrayList<Product> productosPermitidos = new ArrayList <>();

    public ProductoNoAutorizado(String producto, ArrayList<Product> productosPermitidos) {
        super(construirMensaje(producto, productosPermitidos));
        this.producto = producto;
        this.productosPermitidos = productosPermitidos;
    }
    public static String construirMensaje(String producto, ArrayList<Product> productosPermitidos){
        String tmp = "";
        tmp += "El producto " + producto + " no está autorizado.\n";
        tmp += "Productos permitidos: \n";
        for(Product p : productosPermitidos){
            tmp += "- " + p + "\n";
        }
        return tmp;
    }
    public String getProducto(){
        return producto;
    }
    public ArrayList<Product> getProductosPermitidos(){
        return productosPermitidos;
    }
}
