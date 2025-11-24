package tech.hellsoft.trading.exception.produccion;

import java.util.Map;
import tech.hellsoft.trading.enums.Product;

public class IngredientesInsuficientesException extends ProduccionException {
    private Map<Product, Integer> ingredientesRequeridos;
    private Map<Product, Integer> ingredientesDisponibles;

    public IngredientesInsuficientesException(Map<Product, Integer> ingredientesRequeridos, Map<Product, Integer> ingredientesDisponibles) {
        super(construirMensaje(ingredientesRequeridos, ingredientesDisponibles));
        this.ingredientesRequeridos = ingredientesRequeridos;
        this.ingredientesDisponibles = ingredientesDisponibles;
    }
    public static String construirMensaje(Map<Product, Integer> ingredientesRequeridos, Map<Product, Integer> ingredientesDisponibles){
        String tmp = "Ingredientes insuficientes para la produccion. Requeridos: \n";
        for (Map.Entry<Product, Integer> entry : ingredientesRequeridos.entrySet()) {
            Product producto = entry.getKey();
            Integer cantidadRequerida = entry.getValue();
            int i = 1;
            tmp += i + ". " + producto + " = " + cantidadRequerida + "\n";
            i++;
        }
        tmp += "Disponibles: \n";
        for (Map.Entry<Product, Integer> entry : ingredientesDisponibles.entrySet()) {
            Product producto = entry.getKey();
            Integer cantidadDisponible = entry.getValue();
            int i = 1;
            tmp += i + ". " + producto + " = " + cantidadDisponible + "\n";
            i++;
        }
        return tmp;
    }
    public Map<Product, Integer> getIngredientesRequeridos() {
        return ingredientesRequeridos;
    }
    public Map<Product, Integer> getIngredientesDisponibles() {
        return ingredientesDisponibles;
    }

}
