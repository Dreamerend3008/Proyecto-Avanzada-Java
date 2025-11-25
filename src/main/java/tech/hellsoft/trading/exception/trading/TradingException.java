package tech.hellsoft.trading.exception.trading;

public abstract class TradingException extends RuntimeException {
    public TradingException(String mensaje) {
        super(mensaje);
    }
    public TradingException(String mensaje, Throwable causa){
        super(mensaje, causa);
    }
}
