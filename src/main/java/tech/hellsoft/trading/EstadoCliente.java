package tech.hellsoft.trading;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import tech.hellsoft.trading.dto.server.OfferMessage;
import tech.hellsoft.trading.enums.Product;
import tech.hellsoft.trading.model.Receta;
import tech.hellsoft.trading.model.Rol;

public class EstadoCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    // variables de uso
    @Getter @Setter // otra manera de construir getters y setters
    private double saldo;
    @Getter @Setter
    private double saldoInicial;
    @Getter @Setter
    private Map<Product, Integer> inventario;
    @Getter @Setter
    private Map<Product, Double> preciosActuales;
    @Getter @Setter
    private Map<Product, Receta> recetas;
    @Getter @Setter
    private Map<String, OfferMessage> ofertasPendientes;
    @Getter @Setter
    private Rol rol; // Rol del cliente
    @Getter @Setter
    private List<Product> productosAutorizados;
    @Getter @Setter
    private String nombreEquipo;
    @Getter @Setter
    private long timestampSnapshot;

    // rellenar constructor
    public EstadoCliente() {
        this.inventario = new HashMap<>();
        this.preciosActuales = new HashMap<>();
        this.recetas = new HashMap<>();
        this.ofertasPendientes = new HashMap<>();
        this.productosAutorizados = new ArrayList<>();
        this.saldo = 0.0;
        this.saldoInicial = 0.0;
        this.nombreEquipo = "";
        this.timestampSnapshot = System.currentTimeMillis();
    }
    public double calcularPL(){
        double valorInventario = 0.0;
        valorInventario = calcularValorInventario();
        double patrimonioNeto = saldo + valorInventario;
        if(saldoInicial == 0){
            return 0.0;
        }
        double pl = ((patrimonioNeto - saldoInicial) / saldoInicial) * 100;
        return pl;
    }
    public double calcularValorInventario(){
        double valorInventario = 0.0;
        for (Map.Entry<Product, Integer> entry : inventario.entrySet()) {
            Product producto = entry.getKey();
            int cantidad = entry.getValue();

            // Obtener precio actual (si no hay precio, asumir 0)
            Double precio = preciosActuales.get(producto);
            if (precio != null && precio > 0) {
                valorInventario += cantidad * precio;
            }
        }
        return valorInventario;
    }
    public void actualizarInventario(Product producto, int cantidad) {
        inventario.put(producto, inventario.getOrDefault(producto, 0) + cantidad);
    }
}
