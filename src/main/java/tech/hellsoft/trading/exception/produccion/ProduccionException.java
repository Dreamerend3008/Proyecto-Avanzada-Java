package tech.hellsoft.trading.exception.produccion;

public abstract class ProduccionException extends RuntimeException {
    public ProduccionException(String mensaje) {
        super(mensaje);
    }
    public ProduccionException(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }
}
