package tech.hellsoft.trading.util;

import java.util.Map;
import tech.hellsoft.trading.enums.Product;
import tech.hellsoft.trading.model.Receta;

public class RecetaValidator {

    public static boolean puedeProducir(Receta receta, Map<Product, Integer> inventario ){
        if(receta.getIngredientes() == null || receta.getIngredientes().isEmpty()){
            return true;
        }
        for (Map.Entry<Product, Integer> entry : receta.getIngredientes().entrySet()) {
            Product ingrediente = entry.getKey();
            int cantidadRequerida = entry.getValue();
            int cantidadDisponible = inventario.getOrDefault(ingrediente, 0);

            // si la cantidad no da para producir la receta, retornamos false
            if (cantidadDisponible < cantidadRequerida) {
                return false;
            }
        }
        return true;
    }
    public static void consumirIngredientes(Receta receta, Map<Product, Integer> inventario ){
        // este metodo solo se llama cuando se ha verificado que se puede producir la receta
        if(receta.getIngredientes() == null || receta.getIngredientes().isEmpty()){
            return;
        }
        for (Map.Entry<Product, Integer> entry : receta.getIngredientes().entrySet()) {
            Product ingrediente = entry.getKey();
            int cantidadRequerida = entry.getValue();
            int cantidadDisponible = inventario.getOrDefault(ingrediente, 0);

            // descontamos la cantidad requerida del inventario
            inventario.put(ingrediente, cantidadDisponible - cantidadRequerida);
        }
    }
}
