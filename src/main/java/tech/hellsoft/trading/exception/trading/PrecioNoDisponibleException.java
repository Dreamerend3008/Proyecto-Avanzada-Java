package tech.hellsoft.trading.exception.trading;

import tech.hellsoft.trading.enums.Product;

public class PrecioNoDisponibleException extends TradingException {
    // se usa cuando intentas comprar pero no hay precio de mercado disponible
    private Product product;
    public PrecioNoDisponibleException(Product producto) {
        super("El precio de mercado no está disponible para el producto: " + producto);
        this.product = producto;
    }
}
