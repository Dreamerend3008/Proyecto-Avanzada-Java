package tech.hellsoft.trading.util;

import java.util.Map;

public class RecetaValidator {

    public boolean puedeProducir(Receta receta, Map<String, Integer> inventario ){
        if(receta.getIngredientes() == null || receta.getIngredientes().isEmpty()){
            return true;
        }
        for(Receta x: receta.getIngredientes()){
            int numeroTotalInventario = inventario.size();
            int contador = 0;
        }
    }
}
