# 📘 Ejemplos Prácticos de Desarrollo

Este documento contiene ejemplos concretos de cómo implementar diferentes estrategias y componentes de lógica de negocio.

---

## 📋 Tabla de Contenidos
1. [Estrategias de Trading Básicas](#estrategias-de-trading-básicas)
2. [Gestión de Inventario](#gestión-de-inventario)
3. [Gestión de Órdenes](#gestión-de-órdenes)
4. [Sistema de Análisis de Mercado](#sistema-de-análisis-de-mercado)
5. [Gestión de Riesgo](#gestión-de-riesgo)
6. [Tests Unitarios](#tests-unitarios)

---

## 🎯 Estrategias de Trading Básicas

### Ejemplo 1: Market Maker Simple

**Concepto:** Comprar barato y vender caro con un spread fijo.

```java
// src/main/java/tech/hellsoft/trading/strategy/MarketMakerStrategy.java
package tech.hellsoft.trading.strategy;

import tech.hellsoft.trading.dto.server.TickerMessage;
import tech.hellsoft.trading.service.UIService;

public class MarketMakerStrategy {
    
    private final UIService uiService;
    private final double spreadPorcentaje;
    private final double montoMaximoPorOperacion;
    
    public MarketMakerStrategy(UIService uiService, double spreadPorcentaje, double montoMaximo) {
        if (spreadPorcentaje <= 0) {
            throw new IllegalArgumentException("Spread debe ser positivo");
        }
        if (montoMaximo <= 0) {
            throw new IllegalArgumentException("Monto máximo debe ser positivo");
        }
        
        this.uiService = uiService;
        this.spreadPorcentaje = spreadPorcentaje;
        this.montoMaximoPorOperacion = montoMaximo;
    }
    
    public DecisionTrading analizarOportunidad(TickerMessage ticker, double saldoDisponible) {
        if (ticker == null) {
            return DecisionTrading.noAction();
        }
        
        double bestBid = ticker.getBestBid();
        double bestAsk = ticker.getBestAsk();
        
        if (bestBid <= 0 || bestAsk <= 0) {
            return DecisionTrading.noAction();
        }
        
        // Calcular precio objetivo de compra y venta
        double precioCompra = bestAsk;
        double precioVentaObjetivo = precioCompra * (1 + spreadPorcentaje);
        
        // Verificar si es rentable
        if (precioVentaObjetivo <= bestBid) {
            return DecisionTrading.noAction();
        }
        
        // Calcular cantidad a comprar
        int cantidadMaxima = (int) (montoMaximoPorOperacion / precioCompra);
        
        if (cantidadMaxima <= 0) {
            return DecisionTrading.noAction();
        }
        
        uiService.printStatus("💡", 
            String.format("Oportunidad: %s - Comprar %d @ %.2f, Vender @ %.2f", 
                ticker.getProduct(), cantidadMaxima, precioCompra, precioVentaObjetivo));
        
        return DecisionTrading.comprar(ticker.getProduct(), cantidadMaxima, precioCompra);
    }
}

// Record para decisiones
record DecisionTrading(TipoAccion accion, String producto, int cantidad, double precio) {
    
    public static DecisionTrading noAction() {
        return new DecisionTrading(TipoAccion.NINGUNA, null, 0, 0.0);
    }
    
    public static DecisionTrading comprar(String producto, int cantidad, double precio) {
        return new DecisionTrading(TipoAccion.COMPRAR, producto, cantidad, precio);
    }
    
    public static DecisionTrading vender(String producto, int cantidad, double precio) {
        return new DecisionTrading(TipoAccion.VENDER, producto, cantidad, precio);
    }
}

enum TipoAccion {
    COMPRAR, VENDER, NINGUNA
}
```

### Ejemplo 2: Arbitraje de Productos (Crafteo)

**Concepto:** Comprar ingredientes básicos, crear productos complejos, venderlos con ganancia.

```java
// src/main/java/tech/hellsoft/trading/strategy/CraftingStrategy.java
package tech.hellsoft.trading.strategy;

import tech.hellsoft.trading.model.Recipe;
import tech.hellsoft.trading.service.UIService;
import java.util.Map;
import java.util.HashMap;

public class CraftingStrategy {
    
    private final UIService uiService;
    private final Map<String, Recipe> recetas;
    private final Map<String, Double> preciosActuales;
    
    public CraftingStrategy(UIService uiService, Map<String, Recipe> recetas) {
        this.uiService = uiService;
        this.recetas = new HashMap<>(recetas);
        this.preciosActuales = new HashMap<>();
    }
    
    public void actualizarPrecio(String producto, double precio) {
        preciosActuales.put(producto, precio);
    }
    
    public DecisionCrafting analizarReceta(String productoFinal, Map<String, Integer> inventario) {
        Recipe receta = recetas.get(productoFinal);
        
        if (receta == null) {
            return DecisionCrafting.noViable("Receta no encontrada");
        }
        
        if (receta.isBasic()) {
            return DecisionCrafting.noViable("Es un producto básico, no se puede craftear");
        }
        
        // Verificar que tenemos todos los ingredientes
        for (Map.Entry<String, Integer> ingrediente : receta.getIngredients().entrySet()) {
            String producto = ingrediente.getKey();
            int cantidadNecesaria = ingrediente.getValue();
            int cantidadDisponible = inventario.getOrDefault(producto, 0);
            
            if (cantidadDisponible < cantidadNecesaria) {
                return DecisionCrafting.noViable(
                    String.format("Falta %s: necesitas %d, tienes %d",
                        producto, cantidadNecesaria, cantidadDisponible));
            }
        }
        
        // Calcular costo de ingredientes
        double costoTotal = calcularCostoIngredientes(receta);
        
        if (costoTotal <= 0) {
            return DecisionCrafting.noViable("No hay precios disponibles para los ingredientes");
        }
        
        // Obtener precio de venta del producto final
        Double precioVenta = preciosActuales.get(productoFinal);
        
        if (precioVenta == null || precioVenta <= 0) {
            return DecisionCrafting.noViable("No hay precio de venta disponible");
        }
        
        // Calcular ganancia (incluir bonus premium)
        double gananciaBase = precioVenta - costoTotal;
        double gananciaConBonus = gananciaBase + (costoTotal * receta.getPremiumBonus());
        
        if (gananciaConBonus <= 0) {
            return DecisionCrafting.noViable(
                String.format("No rentable: costo %.2f, venta %.2f, ganancia %.2f",
                    costoTotal, precioVenta, gananciaConBonus));
        }
        
        uiService.printStatus("🔨",
            String.format("Crafteo rentable: %s - Ganancia: %.2f (%.2f%%)",
                productoFinal, gananciaConBonus, (gananciaConBonus / costoTotal) * 100));
        
        return DecisionCrafting.viable(productoFinal, costoTotal, precioVenta, gananciaConBonus);
    }
    
    private double calcularCostoIngredientes(Recipe receta) {
        double costo = 0.0;
        
        for (Map.Entry<String, Integer> ingrediente : receta.getIngredients().entrySet()) {
            String producto = ingrediente.getKey();
            int cantidad = ingrediente.getValue();
            Double precio = preciosActuales.get(producto);
            
            if (precio == null || precio <= 0) {
                return -1; // Precio no disponible
            }
            
            costo += precio * cantidad;
        }
        
        return costo;
    }
}

record DecisionCrafting(
    boolean esViable,
    String producto,
    double costoTotal,
    double precioVenta,
    double gananciaEstimada,
    String razon
) {
    
    public static DecisionCrafting noViable(String razon) {
        return new DecisionCrafting(false, null, 0, 0, 0, razon);
    }
    
    public static DecisionCrafting viable(String producto, double costo, double venta, double ganancia) {
        return new DecisionCrafting(true, producto, costo, venta, ganancia, "Viable");
    }
}
```

---

## 📦 Gestión de Inventario

### Ejemplo: Sistema de Inventario Completo

```java
// src/main/java/tech/hellsoft/trading/manager/InventoryManager.java
package tech.hellsoft.trading.manager;

import tech.hellsoft.trading.dto.server.InventoryUpdateMessage;
import tech.hellsoft.trading.service.UIService;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class InventoryManager {
    
    private final UIService uiService;
    private final Map<String, Integer> inventario;
    private final Map<String, Integer> inventarioReservado;
    
    public InventoryManager(UIService uiService) {
        this.uiService = uiService;
        this.inventario = new HashMap<>();
        this.inventarioReservado = new HashMap<>();
    }
    
    public void actualizar(InventoryUpdateMessage update) {
        if (update == null) {
            return;
        }
        
        String producto = update.getProduct();
        int nuevaCantidad = update.getQuantity();
        int cantidadAnterior = inventario.getOrDefault(producto, 0);
        
        inventario.put(producto, nuevaCantidad);
        
        int diferencia = nuevaCantidad - cantidadAnterior;
        String accion = diferencia > 0 ? "+" : "";
        
        uiService.printStatus("📦",
            String.format("Inventario actualizado: %s %s%d (Total: %d)",
                producto, accion, diferencia, nuevaCantidad));
    }
    
    public boolean tieneDisponible(String producto, int cantidad) {
        int disponible = obtenerDisponible(producto);
        return disponible >= cantidad;
    }
    
    public int obtenerDisponible(String producto) {
        int total = inventario.getOrDefault(producto, 0);
        int reservado = inventarioReservado.getOrDefault(producto, 0);
        return Math.max(0, total - reservado);
    }
    
    public void reservar(String producto, int cantidad) {
        if (!tieneDisponible(producto, cantidad)) {
            throw new IllegalStateException(
                String.format("No hay suficiente %s disponible para reservar %d unidades",
                    producto, cantidad));
        }
        
        inventarioReservado.merge(producto, cantidad, Integer::sum);
        uiService.printStatus("🔒",
            String.format("Reservado: %s x%d", producto, cantidad));
    }
    
    public void liberarReserva(String producto, int cantidad) {
        int reservado = inventarioReservado.getOrDefault(producto, 0);
        
        if (reservado < cantidad) {
            uiService.printWarning(
                String.format("Intento de liberar más de lo reservado: %s x%d (reservado: %d)",
                    producto, cantidad, reservado));
            cantidad = reservado;
        }
        
        inventarioReservado.put(producto, reservado - cantidad);
        uiService.printStatus("🔓",
            String.format("Liberado: %s x%d", producto, cantidad));
    }
    
    public Map<String, Integer> obtenerInventarioCompleto() {
        return Collections.unmodifiableMap(inventario);
    }
    
    public Map<String, InventarioDetalle> obtenerResumen() {
        Map<String, InventarioDetalle> resumen = new HashMap<>();
        
        for (String producto : inventario.keySet()) {
            int total = inventario.get(producto);
            int reservado = inventarioReservado.getOrDefault(producto, 0);
            int disponible = Math.max(0, total - reservado);
            
            resumen.put(producto, new InventarioDetalle(total, reservado, disponible));
        }
        
        return resumen;
    }
    
    public void imprimirResumen() {
        uiService.printHeader("INVENTARIO ACTUAL");
        
        Map<String, InventarioDetalle> resumen = obtenerResumen();
        
        if (resumen.isEmpty()) {
            uiService.printInfo("Inventario vacío");
            return;
        }
        
        for (Map.Entry<String, InventarioDetalle> entry : resumen.entrySet()) {
            String producto = entry.getKey();
            InventarioDetalle detalle = entry.getValue();
            
            uiService.printStatus("  ",
                String.format("%s: Total=%d, Disponible=%d, Reservado=%d",
                    producto, detalle.total(), detalle.disponible(), detalle.reservado()));
        }
    }
}

record InventarioDetalle(int total, int reservado, int disponible) {}
```

---

## 📋 Gestión de Órdenes

### Ejemplo: Order Manager con Estado

```java
// src/main/java/tech/hellsoft/trading/manager/OrderManager.java
package tech.hellsoft.trading.manager;

import tech.hellsoft.trading.dto.server.OrderAckMessage;
import tech.hellsoft.trading.dto.server.FillMessage;
import tech.hellsoft.trading.service.UIService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrderManager {
    
    private final UIService uiService;
    private final Map<String, OrdenPendiente> ordenesPendientes;
    private final Map<String, OrdenCompletada> ordenesCompletadas;
    
    public OrderManager(UIService uiService) {
        this.uiService = uiService;
        this.ordenesPendientes = new HashMap<>();
        this.ordenesCompletadas = new HashMap<>();
    }
    
    public String crearOrdenCompra(String producto, int cantidad, double precio) {
        String orderId = generarOrderId();
        
        OrdenPendiente orden = new OrdenPendiente(
            orderId,
            producto,
            TipoOrden.COMPRA,
            cantidad,
            precio,
            EstadoOrden.CREADA,
            LocalDateTime.now()
        );
        
        ordenesPendientes.put(orderId, orden);
        
        uiService.printStatus("📝",
            String.format("Orden creada: %s - %s x%d @ %.2f",
                orderId, producto, cantidad, precio));
        
        return orderId;
    }
    
    public void onOrderAck(OrderAckMessage ack) {
        if (ack == null) {
            return;
        }
        
        String orderId = ack.getOrderId();
        OrdenPendiente orden = ordenesPendientes.get(orderId);
        
        if (orden == null) {
            uiService.printWarning("Acknowledgment para orden desconocida: " + orderId);
            return;
        }
        
        OrdenPendiente ordenActualizada = orden.conEstado(EstadoOrden.ACEPTADA);
        ordenesPendientes.put(orderId, ordenActualizada);
        
        uiService.printSuccess(
            String.format("Orden aceptada: %s - %s", orderId, orden.producto()));
    }
    
    public void onFill(FillMessage fill) {
        if (fill == null) {
            return;
        }
        
        String orderId = fill.getOrderId();
        OrdenPendiente orden = ordenesPendientes.get(orderId);
        
        if (orden == null) {
            uiService.printWarning("Fill para orden desconocida: " + orderId);
            return;
        }
        
        // Marcar como completada
        OrdenCompletada completada = new OrdenCompletada(
            orderId,
            orden.producto(),
            orden.tipo(),
            fill.getFillQty(),
            fill.getFillPrice(),
            LocalDateTime.now()
        );
        
        ordenesCompletadas.put(orderId, completada);
        ordenesPendientes.remove(orderId);
        
        uiService.printSuccess(
            String.format("Orden completada: %s - %s x%d @ %.2f",
                orderId, orden.producto(), fill.getFillQty(), fill.getFillPrice()));
    }
    
    public void cancelarOrden(String orderId) {
        OrdenPendiente orden = ordenesPendientes.remove(orderId);
        
        if (orden == null) {
            uiService.printWarning("Intento de cancelar orden inexistente: " + orderId);
            return;
        }
        
        uiService.printInfo(
            String.format("Orden cancelada: %s - %s", orderId, orden.producto()));
    }
    
    public int contarOrdenesPendientes(String producto) {
        return (int) ordenesPendientes.values().stream()
            .filter(o -> o.producto().equals(producto))
            .count();
    }
    
    public void imprimirResumen() {
        uiService.printHeader("ÓRDENES");
        
        uiService.printInfo(String.format("Pendientes: %d", ordenesPendientes.size()));
        for (OrdenPendiente orden : ordenesPendientes.values()) {
            uiService.printStatus("  ⏳",
                String.format("%s - %s %s x%d @ %.2f [%s]",
                    orden.id(), orden.tipo(), orden.producto(),
                    orden.cantidad(), orden.precio(), orden.estado()));
        }
        
        uiService.printInfo(String.format("Completadas (últimas 5): %d", ordenesCompletadas.size()));
        ordenesCompletadas.values().stream()
            .sorted((a, b) -> b.timestamp().compareTo(a.timestamp()))
            .limit(5)
            .forEach(orden -> 
                uiService.printStatus("  ✅",
                    String.format("%s - %s %s x%d @ %.2f",
                        orden.id(), orden.tipo(), orden.producto(),
                        orden.cantidad(), orden.precioFinal())));
    }
    
    private String generarOrderId() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8);
    }
}

enum TipoOrden {
    COMPRA, VENTA
}

enum EstadoOrden {
    CREADA, ACEPTADA, COMPLETADA, CANCELADA, RECHAZADA
}

record OrdenPendiente(
    String id,
    String producto,
    TipoOrden tipo,
    int cantidad,
    double precio,
    EstadoOrden estado,
    LocalDateTime timestamp
) {
    public OrdenPendiente conEstado(EstadoOrden nuevoEstado) {
        return new OrdenPendiente(id, producto, tipo, cantidad, precio, nuevoEstado, timestamp);
    }
}

record OrdenCompletada(
    String id,
    String producto,
    TipoOrden tipo,
    int cantidad,
    double precioFinal,
    LocalDateTime timestamp
) {}
```

---

## 📊 Sistema de Análisis de Mercado

### Ejemplo: Análisis de Tendencias

```java
// src/main/java/tech/hellsoft/trading/analytics/MarketAnalyzer.java
package tech.hellsoft.trading.analytics;

import tech.hellsoft.trading.dto.server.TickerMessage;
import java.util.*;

public class MarketAnalyzer {
    
    private final int windowSize;
    private final Map<String, Deque<PrecioHistorico>> historicoPorProducto;
    
    public MarketAnalyzer(int windowSize) {
        if (windowSize <= 0) {
            throw new IllegalArgumentException("Window size debe ser positivo");
        }
        this.windowSize = windowSize;
        this.historicoPorProducto = new HashMap<>();
    }
    
    public void registrarTicker(TickerMessage ticker) {
        if (ticker == null) {
            return;
        }
        
        String producto = ticker.getProduct();
        Deque<PrecioHistorico> historial = historicoPorProducto
            .computeIfAbsent(producto, k -> new ArrayDeque<>());
        
        PrecioHistorico precio = new PrecioHistorico(
            ticker.getBestBid(),
            ticker.getBestAsk(),
            calcularPrecioMedio(ticker.getBestBid(), ticker.getBestAsk()),
            System.currentTimeMillis()
        );
        
        historial.addLast(precio);
        
        // Mantener solo los últimos N registros
        while (historial.size() > windowSize) {
            historial.removeFirst();
        }
    }
    
    public Optional<TendenciaMercado> analizarTendencia(String producto) {
        Deque<PrecioHistorico> historial = historicoPorProducto.get(producto);
        
        if (historial == null || historial.size() < 2) {
            return Optional.empty();
        }
        
        List<PrecioHistorico> precios = new ArrayList<>(historial);
        
        double precioInicial = precios.get(0).precioMedio();
        double precioFinal = precios.get(precios.size() - 1).precioMedio();
        double cambio = precioFinal - precioInicial;
        double cambioPorcentual = (cambio / precioInicial) * 100;
        
        // Calcular volatilidad (desviación estándar)
        double volatilidad = calcularVolatilidad(precios);
        
        // Determinar tendencia
        Tendencia tendencia;
        if (cambioPorcentual > 2.0) {
            tendencia = Tendencia.ALCISTA;
        } else if (cambioPorcentual < -2.0) {
            tendencia = Tendencia.BAJISTA;
        } else {
            tendencia = Tendencia.LATERAL;
        }
        
        return Optional.of(new TendenciaMercado(
            producto,
            tendencia,
            precioInicial,
            precioFinal,
            cambio,
            cambioPorcentual,
            volatilidad,
            precios.size()
        ));
    }
    
    public Optional<Double> calcularPromedio(String producto) {
        Deque<PrecioHistorico> historial = historicoPorProducto.get(producto);
        
        if (historial == null || historial.isEmpty()) {
            return Optional.empty();
        }
        
        double suma = historial.stream()
            .mapToDouble(PrecioHistorico::precioMedio)
            .sum();
        
        return Optional.of(suma / historial.size());
    }
    
    private double calcularPrecioMedio(double bid, double ask) {
        if (bid <= 0 && ask <= 0) {
            return 0;
        }
        if (bid <= 0) {
            return ask;
        }
        if (ask <= 0) {
            return bid;
        }
        return (bid + ask) / 2.0;
    }
    
    private double calcularVolatilidad(List<PrecioHistorico> precios) {
        if (precios.size() < 2) {
            return 0.0;
        }
        
        double promedio = precios.stream()
            .mapToDouble(PrecioHistorico::precioMedio)
            .average()
            .orElse(0.0);
        
        double sumaCuadrados = precios.stream()
            .mapToDouble(p -> Math.pow(p.precioMedio() - promedio, 2))
            .sum();
        
        return Math.sqrt(sumaCuadrados / precios.size());
    }
}

record PrecioHistorico(double bid, double ask, double precioMedio, long timestamp) {}

record TendenciaMercado(
    String producto,
    Tendencia tendencia,
    double precioInicial,
    double precioFinal,
    double cambioAbsoluto,
    double cambioPorcentual,
    double volatilidad,
    int muestrasTomadas
) {}

enum Tendencia {
    ALCISTA, BAJISTA, LATERAL
}
```

---

## 🛡️ Gestión de Riesgo

### Ejemplo: Risk Manager Completo

```java
// src/main/java/tech/hellsoft/trading/manager/RiskManager.java
package tech.hellsoft.trading.manager;

import tech.hellsoft.trading.service.UIService;
import java.util.HashMap;
import java.util.Map;

public class RiskManager {
    
    private final UIService uiService;
    private final double capitalTotal;
    private final double maxExposurePorProducto; // % del capital
    private final double maxPerdidaDiaria; // % del capital
    private final Map<String, Double> exposicionPorProducto;
    private double perdidaAcumulada;
    
    public RiskManager(UIService uiService, double capitalTotal, 
                       double maxExposurePorProducto, double maxPerdidaDiaria) {
        
        if (capitalTotal <= 0) {
            throw new IllegalArgumentException("Capital total debe ser positivo");
        }
        if (maxExposurePorProducto <= 0 || maxExposurePorProducto > 1) {
            throw new IllegalArgumentException("Max exposure debe estar entre 0 y 1");
        }
        if (maxPerdidaDiaria <= 0 || maxPerdidaDiaria > 1) {
            throw new IllegalArgumentException("Max pérdida debe estar entre 0 y 1");
        }
        
        this.uiService = uiService;
        this.capitalTotal = capitalTotal;
        this.maxExposurePorProducto = maxExposurePorProducto;
        this.maxPerdidaDiaria = maxPerdidaDiaria;
        this.exposicionPorProducto = new HashMap<>();
        this.perdidaAcumulada = 0.0;
    }
    
    public ValidacionRiesgo validarOrdenCompra(String producto, int cantidad, double precio) {
        // 1. Verificar que no excede exposición por producto
        double valorOrden = cantidad * precio;
        double exposicionActual = exposicionPorProducto.getOrDefault(producto, 0.0);
        double nuevaExposicion = exposicionActual + valorOrden;
        double limiteExposicion = capitalTotal * maxExposurePorProducto;
        
        if (nuevaExposicion > limiteExposicion) {
            return ValidacionRiesgo.rechazada(
                String.format("Excede límite de exposición para %s: %.2f > %.2f",
                    producto, nuevaExposicion, limiteExposicion));
        }
        
        // 2. Verificar límite de pérdida diaria
        if (perdidaAcumulada >= (capitalTotal * maxPerdidaDiaria)) {
            return ValidacionRiesgo.rechazada(
                String.format("Límite de pérdida diaria alcanzado: %.2f",
                    perdidaAcumulada));
        }
        
        // 3. Verificar que tiene capital suficiente
        double exposicionTotal = exposicionPorProducto.values().stream()
            .mapToDouble(Double::doubleValue)
            .sum();
        
        if (exposicionTotal + valorOrden > capitalTotal) {
            return ValidacionRiesgo.rechazada(
                String.format("Capital insuficiente: requiere %.2f, disponible %.2f",
                    valorOrden, capitalTotal - exposicionTotal));
        }
        
        return ValidacionRiesgo.aprobada();
    }
    
    public void registrarCompra(String producto, int cantidad, double precio) {
        double valor = cantidad * precio;
        exposicionPorProducto.merge(producto, valor, Double::sum);
        
        uiService.printStatus("📊",
            String.format("Exposición actualizada: %s = %.2f (%.2f%% del capital)",
                producto, exposicionPorProducto.get(producto),
                (exposicionPorProducto.get(producto) / capitalTotal) * 100));
    }
    
    public void registrarVenta(String producto, int cantidad, double precioCompra, double precioVenta) {
        double valorCompra = cantidad * precioCompra;
        double valorVenta = cantidad * precioVenta;
        double ganancia = valorVenta - valorCompra;
        
        // Actualizar exposición
        double exposicionActual = exposicionPorProducto.getOrDefault(producto, 0.0);
        exposicionPorProducto.put(producto, Math.max(0, exposicionActual - valorCompra));
        
        // Registrar ganancia/pérdida
        if (ganancia < 0) {
            perdidaAcumulada += Math.abs(ganancia);
            uiService.printWarning(
                String.format("Pérdida registrada: %.2f (acumulada: %.2f / %.2f)",
                    ganancia, perdidaAcumulada, capitalTotal * maxPerdidaDiaria));
        } else {
            uiService.printSuccess(
                String.format("Ganancia registrada: %.2f", ganancia));
        }
    }
    
    public void resetPerdidaDiaria() {
        perdidaAcumulada = 0.0;
        uiService.printInfo("Contador de pérdida diaria reseteado");
    }
    
    public EstadoRiesgo obtenerEstado() {
        double exposicionTotal = exposicionPorProducto.values().stream()
            .mapToDouble(Double::doubleValue)
            .sum();
        
        double porcentajeExposicion = (exposicionTotal / capitalTotal) * 100;
        double porcentajePerdida = (perdidaAcumulada / capitalTotal) * 100;
        
        return new EstadoRiesgo(
            capitalTotal,
            exposicionTotal,
            porcentajeExposicion,
            perdidaAcumulada,
            porcentajePerdida,
            maxPerdidaDiaria * 100,
            new HashMap<>(exposicionPorProducto)
        );
    }
    
    public void imprimirResumen() {
        EstadoRiesgo estado = obtenerEstado();
        
        uiService.printHeader("GESTIÓN DE RIESGO");
        uiService.printStatus("💰", String.format("Capital Total: %.2f", estado.capitalTotal()));
        uiService.printStatus("📈", String.format("Exposición Total: %.2f (%.2f%%)",
            estado.exposicionTotal(), estado.porcentajeExposicion()));
        uiService.printStatus("📉", String.format("Pérdida Acumulada: %.2f (%.2f%% / %.2f%%)",
            estado.perdidaAcumulada(), estado.porcentajePerdida(), estado.limitePerdida()));
        
        if (!estado.exposicionPorProducto().isEmpty()) {
            uiService.printInfo("Exposición por producto:");
            estado.exposicionPorProducto().forEach((producto, valor) ->
                uiService.printStatus("  ", String.format("%s: %.2f (%.2f%%)",
                    producto, valor, (valor / capitalTotal) * 100)));
        }
    }
}

record ValidacionRiesgo(boolean aprobada, String razon) {
    
    public static ValidacionRiesgo aprobada() {
        return new ValidacionRiesgo(true, "OK");
    }
    
    public static ValidacionRiesgo rechazada(String razon) {
        return new ValidacionRiesgo(false, razon);
    }
}

record EstadoRiesgo(
    double capitalTotal,
    double exposicionTotal,
    double porcentajeExposicion,
    double perdidaAcumulada,
    double porcentajePerdida,
    double limitePerdida,
    Map<String, Double> exposicionPorProducto
) {}
```

---

## 🧪 Tests Unitarios

### Ejemplo: Tests para TradingUtils

```java
// src/test/java/tech/hellsoft/trading/util/TradingUtilsTest.java
package tech.hellsoft.trading.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class TradingUtilsTest {
    
    @Test
    @DisplayName("maskApiKey debe ocultar la parte central de la clave")
    void testMaskApiKey() {
        String apiKey = "sk_test_1234567890abcdef";
        String masked = TradingUtils.maskApiKey(apiKey);
        
        assertTrue(masked.startsWith("sk_t"));
        assertTrue(masked.endsWith("cdef"));
        assertTrue(masked.contains("***"));
        assertNotEquals(apiKey, masked);
    }
    
    @Test
    @DisplayName("maskApiKey con clave corta debe retornar asteriscos")
    void testMaskApiKeyShort() {
        String apiKey = "short";
        String masked = TradingUtils.maskApiKey(apiKey);
        
        assertEquals("***", masked);
    }
    
    @Test
    @DisplayName("formatCurrency debe formatear con 2 decimales")
    void testFormatCurrency() {
        assertEquals("100.00", TradingUtils.formatCurrency(100.0));
        assertEquals("99.99", TradingUtils.formatCurrency(99.99));
        assertEquals("1000.50", TradingUtils.formatCurrency(1000.5));
    }
    
    @Test
    @DisplayName("isPositive debe validar correctamente números")
    void testIsPositive() {
        assertTrue(TradingUtils.isPositive(1.0));
        assertTrue(TradingUtils.isPositive(0.01));
        assertFalse(TradingUtils.isPositive(0.0));
        assertFalse(TradingUtils.isPositive(-1.0));
    }
    
    @Test
    @DisplayName("isValidString debe validar strings correctamente")
    void testIsValidString() {
        assertTrue(TradingUtils.isValidString("valid"));
        assertFalse(TradingUtils.isValidString(null));
        assertFalse(TradingUtils.isValidString(""));
        assertFalse(TradingUtils.isValidString("   "));
    }
}
```

### Ejemplo: Tests para MarketMakerStrategy

```java
// src/test/java/tech/hellsoft/trading/strategy/MarketMakerStrategyTest.java
package tech.hellsoft.trading.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import tech.hellsoft.trading.dto.server.TickerMessage;
import tech.hellsoft.trading.service.UIService;
import tech.hellsoft.trading.service.impl.ConsoleUIService;

import static org.junit.jupiter.api.Assertions.*;

class MarketMakerStrategyTest {
    
    private MarketMakerStrategy strategy;
    private UIService uiService;
    
    @BeforeEach
    void setUp() {
        uiService = new ConsoleUIService();
        strategy = new MarketMakerStrategy(uiService, 0.05, 1000.0);
    }
    
    @Test
    @DisplayName("Constructor debe validar parámetros")
    void testConstructorValidation() {
        assertThrows(IllegalArgumentException.class,
            () -> new MarketMakerStrategy(uiService, -0.1, 1000.0));
        
        assertThrows(IllegalArgumentException.class,
            () -> new MarketMakerStrategy(uiService, 0.05, -100.0));
    }
    
    @Test
    @DisplayName("analizarOportunidad con ticker null debe retornar no action")
    void testAnalizarOportunidadWithNullTicker() {
        DecisionTrading decision = strategy.analizarOportunidad(null, 10000.0);
        
        assertEquals(TipoAccion.NINGUNA, decision.accion());
    }
    
    @Test
    @DisplayName("analizarOportunidad con precios válidos debe retornar compra")
    void testAnalizarOportunidadValidPrices() {
        TickerMessage ticker = new TickerMessage();
        ticker.setProduct("AGUA");
        ticker.setBestBid(100.0);
        ticker.setBestAsk(95.0);
        
        DecisionTrading decision = strategy.analizarOportunidad(ticker, 10000.0);
        
        assertEquals(TipoAccion.COMPRAR, decision.accion());
        assertEquals("AGUA", decision.producto());
        assertTrue(decision.cantidad() > 0);
    }
}
```

---

## 🚀 Cómo Usar Estos Ejemplos

### 1. Copiar el código que necesites
Crea los archivos en la ubicación correcta según el package.

### 2. Adaptar a tu estrategia
Modifica los parámetros y la lógica según tus necesidades.

### 3. Integrar con SDKTradingService
```java
public class SDKTradingService implements TradingService {
    
    private final MarketMakerStrategy strategy;
    private final InventoryManager inventoryManager;
    private final OrderManager orderManager;
    private final RiskManager riskManager;
    
    public SDKTradingService(UIService service) {
        this.uiService = service;
        this.strategy = new MarketMakerStrategy(service, 0.05, 1000.0);
        this.inventoryManager = new InventoryManager(service);
        this.orderManager = new OrderManager(service);
        this.riskManager = new RiskManager(service, 1000000.0, 0.1, 0.05);
    }
    
    @Override
    public void onTicker(TickerMessage ticker) {
        // Usar la estrategia
        DecisionTrading decision = strategy.analizarOportunidad(ticker, saldoActual);
        
        if (decision.accion() == TipoAccion.COMPRAR) {
            // Validar con risk manager
            ValidacionRiesgo validacion = riskManager.validarOrdenCompra(
                decision.producto(), decision.cantidad(), decision.precio());
            
            if (validacion.aprobada()) {
                // Enviar orden
                String orderId = orderManager.crearOrdenCompra(
                    decision.producto(), decision.cantidad(), decision.precio());
                // connector.enviarOrdenCompra(...)
            }
        }
    }
}
```

### 4. Probar y ajustar
Ejecuta el bot y observa el comportamiento. Ajusta parámetros según resultados.

---

**¡Éxito implementando tu estrategia! 🎯**

