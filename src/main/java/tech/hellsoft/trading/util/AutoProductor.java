package tech.hellsoft.trading.util;

import lombok.Getter;
import tech.hellsoft.trading.ClienteBolsa;
import tech.hellsoft.trading.EstadoCliente;
import tech.hellsoft.trading.enums.Product;
import tech.hellsoft.trading.exception.produccion.IngredientesInsuficientesException;
import tech.hellsoft.trading.exception.produccion.RecetaNoEncontradaException;
import tech.hellsoft.trading.exception.trading.ProductoNoAutorizadoException;
import tech.hellsoft.trading.model.Receta;
import tech.hellsoft.trading.tasks.TareaAutomatica;

public class AutoProductor extends TareaAutomatica {
    private ClienteBolsa cliente;
    @Getter
    String autoProductorId;
    public AutoProductor(ClienteBolsa cliente) {
        OrderIdGenerator ind = new OrderIdGenerator("AUTO-");
        String tmp = ind.next();
        this.autoProductorId = tmp;
        super(tmp);
        this.cliente = cliente;
    }

    @Override
    protected void ejecutar() {
        try{
            autoProducir();
        } catch (Exception e){
            System.err.println("❌ Error en AutoProductor: " + e.getMessage());
        }
    }
    public void autoProducir(){
        // funcion que elige que producir automaticamente
        EstadoCliente estado = cliente.getEstado();
        try {
            Receta recetaSebo = estado.getRecetas().get(Product.SEBO);
            Receta recetaGuaca = estado.getRecetas().get(Product.GUACA);
            if (RecetaValidator.puedeProducir(recetaGuaca, estado.getInventario())) {
                cliente.producir(Product.GUACA, true);
                return;
            }
            if (RecetaValidator.puedeProducir(recetaSebo, estado.getInventario())) {
                cliente.producir(Product.SEBO, false);
                return;
            }
            // si no puedo premium, producir basico
            cliente.producir(Product.PALTA_OIL, false);
        } catch (ProductoNoAutorizadoException | RecetaNoEncontradaException | IngredientesInsuficientesException e) {
            System.err.println("❌ Error al auto-producir: " + e.getMessage());
        }
    }
}
