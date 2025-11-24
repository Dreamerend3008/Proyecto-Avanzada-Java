# 👤 PERSONA 1
**Estatus**: Ya logro compilarlo
**Responsabilidad**: Sistema de Excepciones, Validación de Recetas y Comandos de Consola  
**Complejidad**: Media  
**Tiempo estimado**: 12-15 horas  
**Peso en la evaluación**: ~25% del proyecto

## 📋 Resumen de Tareas

Esta persona se encarga de:
1. Implementar todas las excepciones personalizadas (7 clases)
2. Crear el validador de recetas
3. Implementar los DTOs básicos (Rol y Receta)
4. Completar algunos comandos de la consola en Main.java

## 🚨 TAREA 1: Excepciones Personalizadas (15% de la nota)

### Ubicación
`src/main/java/tech/hellsoft/trading/exception/`
### 1.2 Implementar Excepciones Específicas


#### SnapshotCorruptoException.java
```java
package tech.hellsoft.trading.exception;

/**
 * Se lanza cuando un archivo de snapshot está corrupto.
 */
public class SnapshotCorruptoException extends ConfiguracionException {
    
    private final String archivo;
    
    public SnapshotCorruptoException(String archivo, String razon) {
        super(String.format("Snapshot corrupto: %s. Razón: %s", archivo, razon));
        this.archivo = archivo;
    }
    
    public SnapshotCorruptoException(String archivo, Throwable causa) {
        super(String.format("Snapshot corrupto: %s. Causa: %s", 
              archivo, causa.getMessage()), causa);
        this.archivo = archivo;
    }
    
    public String getArchivo() {
        return archivo;
    }
}
```

---

## 🔍 TAREA 2: RecetaValidator - Validación de Ingredientes

### Ubicación
`src/main/java/tech/hellsoft/trading/util/RecetaValidator.java`

### Implementación Completa

```java
package tech.hellsoft.trading.util;

import tech.hellsoft.trading.model.Receta;
import java.util.Map;

/**
 * Utilidad para validar y consumir ingredientes de recetas.
 */
public final class RecetaValidator {
    
    private RecetaValidator() {
        // Clase utilitaria - no instanciable
    }
    
    /**
     * Verifica si el inventario tiene suficientes ingredientes para la receta.
     * 
     * @param receta Receta a validar
     * @param inventario Inventario actual
     * @return true si hay suficientes ingredientes, false en caso contrario
     */
    public static boolean puedeProducir(Receta receta, Map<String, Integer> inventario) {
        // Si la receta no tiene ingredientes (producción básica), siempre se puede producir
        if (receta.getIngredientes() == null || receta.getIngredientes().isEmpty()) {
            return true;
        }
        
        // Verificar cada ingrediente requerido
        for (Map.Entry<String, Integer> entry : receta.getIngredientes().entrySet()) {
            String ingrediente = entry.getKey();
            int cantidadRequerida = entry.getValue();
            int cantidadDisponible = inventario.getOrDefault(ingrediente, 0);
            
            // Si falta algún ingrediente, no se puede producir
            if (cantidadDisponible < cantidadRequerida) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Consume los ingredientes del inventario según la receta.
     * IMPORTANTE: Llama a puedeProducir() antes de usar este método.
     * 
     * @param receta Receta a consumir
     * @param inventario Inventario que será modificado
     */
    public static void consumirIngredientes(Receta receta, Map<String, Integer> inventario) {
        // Si no hay ingredientes, no hay nada que consumir
        if (receta.getIngredientes() == null || receta.getIngredientes().isEmpty()) {
            return;
        }
        
        // Consumir cada ingrediente
        for (Map.Entry<String, Integer> entry : receta.getIngredientes().entrySet()) {
            String ingrediente = entry.getKey();
            int cantidadRequerida = entry.getValue();
            int cantidadActual = inventario.get(ingrediente);
            
            // Restar la cantidad requerida
            int nuevaCantidad = cantidadActual - cantidadRequerida;
            
            // Si queda 0, podemos eliminar la entrada o dejarla en 0
            if (nuevaCantidad <= 0) {
                inventario.remove(ingrediente);
            } else {
                inventario.put(ingrediente, nuevaCantidad);
            }
        }
    }
}
```

---

## 📦 TAREA 3: DTOs del Modelo

### Ubicación
`src/main/java/tech/hellsoft/trading/model/`

### 3.1 Rol.java

```java
package tech.hellsoft.trading.model;

import java.io.Serializable;

/**
 * Parámetros del algoritmo recursivo de producción.
 */
public class Rol implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int branches;        // Número de ramas por nivel
    private int maxDepth;        // Profundidad máxima del árbol
    private double decay;        // Factor de decaimiento por nivel
    private double baseEnergy;   // Energía base
    private double levelEnergy;  // Energía adicional por nivel
    
    // Constructor vacío
    public Rol() {
    }
    
    // Constructor con parámetros
    public Rol(int branches, int maxDepth, double decay, double baseEnergy, double levelEnergy) {
        this.branches = branches;
        this.maxDepth = maxDepth;
        this.decay = decay;
        this.baseEnergy = baseEnergy;
        this.levelEnergy = levelEnergy;
    }
    
    // Getters y Setters
    public int getBranches() {
        return branches;
    }
    
    public void setBranches(int branches) {
        this.branches = branches;
    }
    
    public int getMaxDepth() {
        return maxDepth;
    }
    
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }
    
    public double getDecay() {
        return decay;
    }
    
    public void setDecay(double decay) {
        this.decay = decay;
    }
    
    public double getBaseEnergy() {
        return baseEnergy;
    }
    
    public void setBaseEnergy(double baseEnergy) {
        this.baseEnergy = baseEnergy;
    }
    
    public double getLevelEnergy() {
        return levelEnergy;
    }
    
    public void setLevelEnergy(double levelEnergy) {
        this.levelEnergy = levelEnergy;
    }
    
    @Override
    public String toString() {
        return String.format("Rol{branches=%d, maxDepth=%d, decay=%.4f, baseEnergy=%.1f, levelEnergy=%.1f}",
                branches, maxDepth, decay, baseEnergy, levelEnergy);
    }
}
```

### 3.2 Receta.java

```java
package tech.hellsoft.trading.model;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

/**
 * Receta de producción de un producto.
 */
public class Receta implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String producto;                     // Nombre del producto a producir
    private Map<String, Integer> ingredientes;  // null para básico, mapa para premium
    private double bonusPremium;                 // Típicamente 1.30 (30% bonus)
    
    // Constructor vacío
    public Receta() {
        this.ingredientes = new HashMap<>();
    }
    
    // Constructor con parámetros
    public Receta(String producto, Map<String, Integer> ingredientes, double bonusPremium) {
        this.producto = producto;
        this.ingredientes = ingredientes;
        this.bonusPremium = bonusPremium;
    }
    
    // Getters y Setters
    public String getProducto() {
        return producto;
    }
    
    public void setProducto(String producto) {
        this.producto = producto;
    }
    
    public Map<String, Integer> getIngredientes() {
        return ingredientes;
    }
    
    public void setIngredientes(Map<String, Integer> ingredientes) {
        this.ingredientes = ingredientes;
    }
    
    public double getBonusPremium() {
        return bonusPremium;
    }
    
    public void setBonusPremium(double bonusPremium) {
        this.bonusPremium = bonusPremium;
    }
    
    /**
     * Verifica si esta es una receta premium (requiere ingredientes).
     */
    public boolean isPremium() {
        return ingredientes != null && !ingredientes.isEmpty();
    }
    
    @Override
    public String toString() {
        if (isPremium()) {
            return String.format("Receta{producto='%s', ingredientes=%s, bonus=%.0f%%}",
                    producto, ingredientes, (bonusPremium - 1.0) * 100);
        } else {
            return String.format("Receta{producto='%s', básico}", producto);
        }
    }
}
```

---

## 💻 TAREA 4: Completar Comandos en Main.java

### Ubicación
`src/main/java/tech/hellsoft/trading/Main.java`

### 4.1 Implementar handleInventario()

Reemplazar el método existente con:

```java
private static void handleInventario(MyTradingBot bot) {
    System.out.println("\n📦 INVENTARIO");
    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    
    EstadoCliente estado = bot.getEstado();
    Map<String, Integer> inventario = estado.getInventario();
    
    if (inventario.isEmpty()) {
        System.out.println("(vacío)");
        System.out.println();
        return;
    }
    
    int totalUnidades = 0;
    double valorTotal = 0.0;
    
    for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
        String producto = entry.getKey();
        int cantidad = entry.getValue();
        double precio = estado.getPreciosActuales().getOrDefault(producto, 0.0);
        double valor = cantidad * precio;
        
        System.out.printf("%s: %d unidades @ $%.2f = $%.2f%n", 
                producto, cantidad, precio, valor);
        
        totalUnidades += cantidad;
        valorTotal += valor;
    }
    
    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    System.out.printf("TOTAL: %d unidades $%.2f%n", totalUnidades, valorTotal);
    System.out.println();
}
```

### 4.2 Implementar handlePrecios()

Reemplazar el método existente con:

```java
private static void handlePrecios(MyTradingBot bot) {
    System.out.println("\n💹 PRECIOS DE MERCADO");
    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    
    EstadoCliente estado = bot.getEstado();
    Map<String, Double> precios = estado.getPreciosActuales();
    
    if (precios.isEmpty()) {
        System.out.println("(esperando tickers...)");
        System.out.println();
        return;
    }
    
    for (Map.Entry<String, Double> entry : precios.entrySet()) {
        String producto = entry.getKey();
        double mid = entry.getValue();
        
        // Calcular bid y ask aproximados (±2%)
        double bid = mid * 0.98;
        double ask = mid * 1.02;
        
        System.out.printf("%s: $%.2f (bid: $%.2f, ask: $%.2f)%n", 
                producto, mid, bid, ask);
    }
    
    System.out.println();
}
```

---

## ✅ Checklist de Tareas

- [ ] Crear TradingException.java (clase base)
- [ ] Crear ProduccionException.java (clase base)
- [ ] Crear ConfiguracionException.java (clase base)
- [ ] Crear SaldoInsuficienteException.java
- [ ] Crear InventarioInsuficienteException.java
- [ ] Crear ProductoNoAutorizadoException.java
- [ ] Crear IngredientesInsuficientesException.java
- [ ] Crear RecetaNoEncontradaException.java
- [ ] Crear SnapshotCorruptoException.java
- [ ] Crear RecetaValidator.java
- [ ] Crear Rol.java
- [ ] Crear Receta.java
- [ ] Implementar handleInventario() en Main.java
- [ ] Implementar handlePrecios() en Main.java
- [ ] Probar todas las excepciones con casos de prueba
- [ ] Documentar el código con JavaDoc

---

## 🧪 Testing Sugerido

### Test de Excepciones
```java
// Probar que las excepciones se crean correctamente
SaldoInsuficienteException ex1 = new SaldoInsuficienteException(100.0, 150.0);
System.out.println(ex1.getMessage()); // Debe mostrar mensaje formateado

// Probar RecetaValidator
Receta recetaBasica = new Receta("PALTA-OIL", null, 1.0);
Map<String, Integer> inventarioVacio = new HashMap<>();
boolean puede = RecetaValidator.puedeProducir(recetaBasica, inventarioVacio);
System.out.println("Puede producir básico: " + puede); // Debe ser true
```

---

## 📚 Referencias

- **Guia-Profesor.md**: Sección "EXCEPCIONES QUE DEBES IMPLEMENTAR" (página 8-10)
- **Guia-Profesor.md**: Sección "RecetaValidator" (página 14)
- **Java Serializable**: https://docs.oracle.com/javase/tutorial/jndi/objects/serial.html

---

## 🤝 Coordinación con Otros

- **Persona 2** necesitará tus excepciones para ClienteBolsa y CalculadoraProduccion
- **Persona 3** necesitará Rol y Receta para SnapshotManager
- Prioriza las excepciones y DTOs primero, luego RecetaValidator

---

**Estimación total**: 12-15 horas  
**Prioridad**: Alta (otras personas dependen de este trabajo)  
**Dificultad**: Media


### 1.1 Crear Jerarquía de Excepciones Base

#### TradingException.java
```java
package tech.hellsoft.trading.exception;

/**
 * Excepción base para errores de trading.
 */
public abstract class TradingException extends Exception {
    
    public TradingException(String mensaje) {
        super(mensaje);
    }
    
    public TradingException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
```

#### ProduccionException.java
```java
package tech.hellsoft.trading.exception;

/**
 * Excepción base para errores de producción.
 */
public abstract class ProduccionException extends Exception {
    
    public ProduccionException(String mensaje) {
        super(mensaje);
    }
    
    public ProduccionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
```

#### ConfiguracionException.java
```java
package tech.hellsoft.trading.exception;

/**
 * Excepción base para errores de configuración.
 */
public abstract class ConfiguracionException extends Exception {
    
    public ConfiguracionException(String mensaje) {
        super(mensaje);
    }
    
    public ConfiguracionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
```
#### SaldoInsuficienteException.java
```java
package tech.hellsoft.trading.exception;
public class SaldoInsuficienteException extends TradingException {
    
    private final double saldoActual;
    private final double costoRequerido;
    
    public SaldoInsuficienteException(double saldoActual, double costoRequerido) {
        super(String.format("Saldo insuficiente. Tienes: $%.2f, Necesitas: $%.2f", 
              saldoActual, costoRequerido));
        this.saldoActual = saldoActual;
        this.costoRequerido = costoRequerido;
    }
    
    public double getSaldoActual() {
        return saldoActual;
    }
    
    public double getCostoRequerido() {
        return costoRequerido;
    }
}
```

#### InventarioInsuficienteException.java
```java
package tech.hellsoft.trading.exception;

/**
 * Se lanza cuando no hay suficiente cantidad de un producto en inventario.
 */
public class InventarioInsuficienteException extends TradingException {
    
    private final String producto;
    private final int disponible;
    private final int requerido;
    
    public InventarioInsuficienteException(String producto, int disponible, int requerido) {
        super(String.format("Inventario insuficiente de %s. Tienes: %d, Necesitas: %d", 
              producto, disponible, requerido));
        this.producto = producto;
        this.disponible = disponible;
        this.requerido = requerido;
    }
    
    public String getProducto() {
        return producto;
    }
    
    public int getDisponible() {
        return disponible;
    }
    
    public int getRequerido() {
        return requerido;
    }
}
```


#### ProductoNoAutorizadoException.java
```java
package tech.hellsoft.trading.exception;

import java.util.List;

/**
 * Se lanza cuando se intenta producir un producto no autorizado.
 */
public class ProductoNoAutorizadoException extends ProduccionException {
    
    private final String producto;
    private final List<String> productosPermitidos;
    
    public ProductoNoAutorizadoException(String producto, List<String> productosPermitidos) {
        super(String.format("No puedes producir %s. Productos permitidos: %s", 
              producto, String.join(", ", productosPermitidos)));
        this.producto = producto;
        this.productosPermitidos = productosPermitidos;
    }
    
    public String getProducto() {
        return producto;
    }
    
    public List<String> getProductosPermitidos() {
        return productosPermitidos;
    }
}
```

#### IngredientesInsuficientesException.java
```java
package tech.hellsoft.trading.exception;

import java.util.Map;

/**
 * Se lanza cuando faltan ingredientes para producción premium.
 */
public class IngredientesInsuficientesException extends ProduccionException {
    
    private final Map<String, Integer> ingredientesRequeridos;
    private final Map<String, Integer> ingredientesDisponibles;
    
    public IngredientesInsuficientesException(
            Map<String, Integer> requeridos, 
            Map<String, Integer> disponibles) {
        super(construirMensaje(requeridos, disponibles));
        this.ingredientesRequeridos = requeridos;
        this.ingredientesDisponibles = disponibles;
    }
    
    private static String construirMensaje(
            Map<String, Integer> requeridos, 
            Map<String, Integer> disponibles) {
        StringBuilder sb = new StringBuilder("Ingredientes insuficientes:\n");
        for (Map.Entry<String, Integer> entry : requeridos.entrySet()) {
            String ingrediente = entry.getKey();
            int req = entry.getValue();
            int disp = disponibles.getOrDefault(ingrediente, 0);
            sb.append(String.format("  - %s: Tienes %d, Necesitas %d\n", 
                      ingrediente, disp, req));
        }
        return sb.toString();
    }
    
    public Map<String, Integer> getIngredientesRequeridos() {
        return ingredientesRequeridos;
    }
    
    public Map<String, Integer> getIngredientesDisponibles() {
        return ingredientesDisponibles;
    }
}
```

#### RecetaNoEncontradaException.java
```java
package tech.hellsoft.trading.exception;

/**
 * Se lanza cuando una receta no existe en el catálogo.
 */
public class RecetaNoEncontradaException extends ProduccionException {
    
    private final String producto;
    
    public RecetaNoEncontradaException(String producto) {
        super(String.format("No existe receta para el producto: %s", producto));
        this.producto = producto;
    }
    
    public String getProducto() {
        return producto;
    }
}
```

