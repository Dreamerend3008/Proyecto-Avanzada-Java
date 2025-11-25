package tech.hellsoft.trading.exception.trading;

import java.util.List;
import tech.hellsoft.trading.enums.Product;

public class ProductoNoAutorizadoException extends TradingException {
    private Product producto;
    private List<Product> productosPermitidos;

    public ProductoNoAutorizadoException(Product producto, List<Product> productosPermitidos) {
        super(construirMensaje(producto.getValue(), productosPermitidos));
        this.producto = producto;
        this.productosPermitidos = productosPermitidos;
    }
    public static String construirMensaje(String producto, List<Product> productosPermitidos){
        String tmp = "";
        tmp += "El producto " + producto + " no está autorizado.\n";
        tmp += "Productos permitidos: \n";
        for(Product p : productosPermitidos){
            tmp += "- " + p + "\n";
        }
        return tmp;
    }
    public Product getProducto(){
        return producto;
    }
    public List<Product> getProductosPermitidos(){
        return productosPermitidos;
    }
}
