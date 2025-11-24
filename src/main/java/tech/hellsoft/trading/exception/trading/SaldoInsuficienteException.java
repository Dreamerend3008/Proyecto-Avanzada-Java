package tech.hellsoft.trading.exception.trading;

public class SaldoInsuficienteException extends TradingException {
    private double saldoActual;
    private double costoRequerido;

    public SaldoInsuficienteException(double saldoActual, double costoRequerido) {
        super("No tienes saldo suficiente. Saldo Actual: " + saldoActual + "\n Necesario: " + costoRequerido);
        this.saldoActual = saldoActual;
        this.costoRequerido = costoRequerido;
    }
    public double getSaldoActual(){
        return saldoActual;
    }
    public double getCostoRequerido(){
        return costoRequerido;
    }
}
