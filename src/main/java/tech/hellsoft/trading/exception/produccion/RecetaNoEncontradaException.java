package tech.hellsoft.trading.exception.produccion;

import tech.hellsoft.trading.model.Receta;

public class RecetaNoEncontradaException extends ProduccionException {
    private Receta receta;

    public RecetaNoEncontradaException(Receta receta) {
        super("Receta no encontrada: " + receta);
        this.receta = receta;
    }
    public Receta getReceta(){
        return receta;
    }
}
