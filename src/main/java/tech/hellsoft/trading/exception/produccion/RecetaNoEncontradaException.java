package tech.hellsoft.trading.exception.produccion;

public class RecetaNoEncontradaException extends ProduccionException {
    private String receta;

    public RecetaNoEncontradaException(String receta) {
        super("Receta no encontrada: " + receta);
        this.receta = receta;
    }
    public String getReceta(){
        return receta;
    }
}
