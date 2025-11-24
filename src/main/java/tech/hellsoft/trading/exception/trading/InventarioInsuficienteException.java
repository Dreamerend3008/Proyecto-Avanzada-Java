package tech.hellsoft.trading.exception.trading;

import tech.hellsoft.trading.enums.Product;

public class InventarioInsuficienteException extends TradingException {
        private Product producto;
        private int cantidadDisponible;
        private int cantidadRequerida;

    public InventarioInsuficienteException(Product producto, int cantidadDisponible, int cantidadRequerida) {
        super(construirMensaje(producto, cantidadDisponible, cantidadRequerida));
        this.producto = producto;
        this.cantidadRequerida = cantidadRequerida;
        this.cantidadDisponible = cantidadDisponible;
    }
    public static String construirMensaje(Product producto, int cantidadDisponible, int cantidadRequerida){
        return "No hay suficiente " + producto + " la cantidad disponible es: "+ cantidadDisponible + " la cantidad requerida: "+ cantidadRequerida;
    }
    public Product getProducto() {
        return producto;
    }
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }
    public int getCantidadRequerida() {
        return cantidadRequerida;
    }
}
