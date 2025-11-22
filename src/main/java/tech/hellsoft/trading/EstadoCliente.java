package tech.hellsoft.trading;

//import tech.hellsoft.trading.model.Recipe;
//import tech.hellsoft.trading.model.Role;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class EstadoCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    // variables de uso
    private double saldo;
    private double saldoInicial;
    private Map<String, Integer> inventario;
    private Map<String, Double> preciosActuales;
    //private Map<String, Recipe> recetas; // Mapa de recetas por nombre
    //private Role rol; // Rol del cliente
    private List<String> productosAutorizados;
    private String nombreEquipo;
    private long timestampSnapshot;

    // rellenar constructor
    public EstadoCliente() {
        this.inventario = new HashMap<>();
        this.preciosActuales = new HashMap<>();
        //this.recetas = new HashMap<>();
        this.productosAutorizados = new ArrayList<>();
        this.saldo = 0.0;
        this.saldoInicial = 0.0;
        this.nombreEquipo = "";
        this.timestampSnapshot = System.currentTimeMillis();
    }
    public double calcularPL(){
        double valorInventario = 0.0;
        for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
            String producto = entry.getKey();
            int cantidad = entry.getValue();

            // Obtener precio actual (si no hay precio, asumir 0)
            Double precio = preciosActuales.get(producto);
            if (precio != null && precio > 0) {
                valorInventario += cantidad * precio;
            }
        }
        double patrimonioNeto = saldo + valorInventario;
        if(saldoInicial == 0){
            return 0.0;
        }
        double pl = ((patrimonioNeto - saldoInicial) / saldoInicial) * 100;
        return pl;
    }
    public double calcularValorInventario(){
        double valorInventario = 0.0;
        for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
            String producto = entry.getKey();
            int cantidad = entry.getValue();

            // Obtener precio actual (si no hay precio, asumir 0)
            Double precio = preciosActuales.get(producto);
            if (precio != null && precio > 0) {
                valorInventario += cantidad * precio;
            }
        }
        return valorInventario;
    }

    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public double getSaldoInicial() {
        return saldoInicial;
    }
    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
    public Map<String, Integer> getInventario() {
        return inventario;
    }
    public void setInventario(Map<String, Integer> inventario) {
        this.inventario = inventario;
    }
    public Map<String, Double> getPreciosActuales() {
        return preciosActuales;
    }
    public void setPreciosActuales(Map<String, Double> preciosActuales) {
        this.preciosActuales = preciosActuales;
    }
    public List<String> getProductosAutorizados() {
        return productosAutorizados;
    }
    public void setProductosAutorizados(List<String> productosAutorizados) {
        this.productosAutorizados = productosAutorizados;
    }
    public String getNombreEquipo() {
        return nombreEquipo;
    }
    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }
    public long getTimestampSnapshot() {
        return timestampSnapshot;
    }
    public void setTimestampSnapshot(long timestampSnapshot) {
        this.timestampSnapshot = timestampSnapshot;
    }
    public void actualizarPrecio(String producto, Double precio) {
        if (precio != null) {
            preciosActuales.put(producto, precio);
        }
    }
}
