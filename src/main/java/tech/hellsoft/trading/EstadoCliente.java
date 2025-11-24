package tech.hellsoft.trading;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import lombok.Getter;
import tech.hellsoft.trading.dto.server.OfferMessage;
import tech.hellsoft.trading.enums.Product;
import tech.hellsoft.trading.model.Receta;
import tech.hellsoft.trading.model.Rol;

public class EstadoCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    // variables de uso
    @Getter
    private double saldo;
    private double saldoInicial;
    private Map<Product, Integer> inventario;
    private Map<Product, Double> preciosActuales;
    private Map<Product, Receta> recetas; // Mapa de recetas por nombre
    private Map<String, OfferMessage> ofertasPendientes;
    private Rol rol; // Rol del cliente
    private List<Product> productosAutorizados;
    private String nombreEquipo;
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

    public long getTimestampSnapshot() {
        return timestampSnapshot;
    }

    public void setTimestampSnapshot(long timestampSnapshot) {
        this.timestampSnapshot = timestampSnapshot;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public List<Product> getProductosAutorizados() {
        return productosAutorizados;
    }

    public void setProductosAutorizados(List<Product> productosAutorizados) {
        this.productosAutorizados = productosAutorizados;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Map<Product, Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(Map<Product, Receta> recetas) {
        this.recetas = recetas;
    }

    public Map<Product, Double> getPreciosActuales() {
        return preciosActuales;
    }

    public void setPreciosActuales(Map<Product, Double> preciosActuales) {
        this.preciosActuales = preciosActuales;
    }

    public Map<Product, Integer> getInventario() {
        return inventario;
    }

    public void setInventario(Map<Product, Integer> inventario) {
        this.inventario = inventario;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Map<String, OfferMessage> getOfertasPendientes() {
        return ofertasPendientes;
    }

    public void setOfertasPendientes(Map<String, OfferMessage> ofertasPendientes) {
        this.ofertasPendientes = ofertasPendientes;
    }

    public double getSaldo() {
        return saldo;
    }
}
