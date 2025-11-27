package tech.hellsoft.trading.exception.configuracion;

public abstract class ConfiguracionException extends RuntimeException {
    public ConfiguracionException(String mensaje) {
        super(mensaje);
    }
    public ConfiguracionException(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }
}
