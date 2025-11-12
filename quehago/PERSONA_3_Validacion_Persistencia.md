# 👤 Tareas para Persona 3 - Validación y Persistencia

## 🎯 Tu Rol en el Equipo

Eres responsable de la **robustez y seguridad del sistema**. Tu código se encarga de validar datos, manejar errores elegantemente, y persistir el estado del juego. Aunque tus componentes son más pequeños, son críticos para la estabilidad del bot.

**Componentes bajo tu responsabilidad:**
- Todas las excepciones personalizadas (8 clases)
- `RecetaValidator.java` - Validación de ingredientes
- `SnapshotManager.java` - Serialización binaria
- Tests de tus componentes

---

## 📋 Tareas Asignadas

### ✅ Tarea 1: Sistema Completo de Excepciones
**Prioridad:** 🔴🔴🔴🔴🔴 MÁXIMA  
**Tiempo estimado:** 2-3 horas  
**Carpeta:** `src/main/java/tech/hellsoft/trading/exception/`

#### Descripción
Implementar todas las excepciones según la jerarquía definida en la GUIA.md. Estas excepciones permiten manejar errores elegantemente.

#### Jerarquía Completa

```
Exception
├── TradingException (clase base abstracta)
│   ├── SaldoInsuficienteException ⭐ CRÍTICA
│   ├── InventarioInsuficienteException ⭐ CRÍTICA
│   ├── ProductoNoAutorizadoException ⭐ CRÍTICA
│   ├── PrecioNoDisponibleException
│   └── OfertaExpiradaException
│
├── ProduccionException (clase base abstracta)
│   ├── IngredientesInsuficientesException ⭐ CRÍTICA
│   └── RecetaNoEncontradaException ⭐ CRÍTICA
│
└── ConfiguracionException (clase base abstracta)
    ├── ConfiguracionInvalidaException ✅ YA EXISTE
    └── SnapshotCorruptoException
```

#### Implementación

##### 1. TradingException (Clase Base)

```java
package tech.hellsoft.trading.exception;

/**
 * Excepción base para todos los errores relacionados con trading.
 * Esta clase es abstracta y nunca se instancia directamente.
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

##### 2. SaldoInsuficienteException ⭐

```java
package tech.hellsoft.trading.exception;

/**
 * Se lanza cuando no hay suficiente dinero para realizar una compra.
 */
public class SaldoInsuficienteException extends TradingException {
    
    private final double costoRequerido;
    private final double saldoActual;
    
    public SaldoInsuficienteException(double costoRequerido, double saldoActual) {
        super(String.format(
            "Saldo insuficiente. Necesitas: $%.2f, Tienes: $%.2f",
            costoRequerido, saldoActual
        ));
        this.costoRequerido = costoRequerido;
        this.saldoActual = saldoActual;
    }
    
    public double getCostoRequerido() {
        return costoRequerido;
    }
    
    public double getSaldoActual() {
        return saldoActual;
    }
    
    public double getFaltante() {
        return costoRequerido - saldoActual;
    }
}
```

##### 3. InventarioInsuficienteException ⭐

```java
package tech.hellsoft.trading.exception;

/**
 * Se lanza cuando no hay suficiente cantidad de un producto en inventario.
 */
public class InventarioInsuficienteException extends TradingException {
    
    private final String producto;
    private final int requerido;
    private final int disponible;
    
    public InventarioInsuficienteException(String producto, int requerido, int disponible) {
        super(String.format(
            "Inventario insuficiente de %s. Necesitas: %d, Tienes: %d",
            producto, requerido, disponible
        ));
        this.producto = producto;
        this.requerido = requerido;
        this.disponible = disponible;
    }
    
    public String getProducto() {
        return producto;
    }
    
    public int getRequerido() {
        return requerido;
    }
    
    public int getDisponible() {
        return disponible;
    }
    
    public int getFaltante() {
        return requerido - disponible;
    }
}
```

##### 4. ProductoNoAutorizadoException ⭐

```java
package tech.hellsoft.trading.exception;

import java.util.List;

/**
 * Se lanza cuando se intenta producir un producto no autorizado para esta especie.
 */
public class ProductoNoAutorizadoException extends TradingException {
    
    private final String producto;
    private final List<String> productosPermitidos;
    
    public ProductoNoAutorizadoException(String producto, List<String> productosPermitidos) {
        super(String.format(
            "No puedes producir %s. Productos autorizados: %s",
            producto, productosPermitidos
        ));
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

##### 5. PrecioNoDisponibleException

```java
package tech.hellsoft.trading.exception;

/**
 * Se lanza cuando no hay precio de mercado disponible para un producto.
 */
public class PrecioNoDisponibleException extends TradingException {
    
    private final String producto;
    
    public PrecioNoDisponibleException(String producto) {
        super(String.format(
            "No hay precio disponible para %s. Espera a recibir un ticker.",
            producto
        ));
        this.producto = producto;
    }
    
    public String getProducto() {
        return producto;
    }
}
```

##### 6. OfertaExpiradaException

```java
package tech.hellsoft.trading.exception;

/**
 * Se lanza cuando se intenta aceptar una oferta que ya expiró.
 */
public class OfertaExpiradaException extends TradingException {
    
    private final String offerId;
    
    public OfertaExpiradaException(String offerId) {
        super(String.format("La oferta %s ya expiró o fue cancelada", offerId));
        this.offerId = offerId;
    }
    
    public String getOfferId() {
        return offerId;
    }
}
```

##### 7. ProduccionException (Clase Base)

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

##### 8. IngredientesInsuficientesException ⭐

```java
package tech.hellsoft.trading.exception;

import tech.hellsoft.trading.model.Recipe;
import java.util.Map;

/**
 * Se lanza cuando no hay suficientes ingredientes para producción premium.
 */
public class IngredientesInsuficientesException extends ProduccionException {
    
    private final Recipe receta;
    private final Map<String, Integer> inventarioActual;
    
    public IngredientesInsuficientesException(Recipe receta, Map<String, Integer> inventarioActual) {
        super(String.format(
            "Ingredientes insuficientes para producir %s",
            receta.getProduct()
        ));
        this.receta = receta;
        this.inventarioActual = inventarioActual;
    }
    
    public Recipe getReceta() {
        return receta;
    }
    
    public Map<String, Integer> getInventarioActual() {
        return inventarioActual;
    }
    
    /**
     * Retorna detalles de qué ingredientes faltan
     */
    public String getDetallesFaltantes() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ingredientes faltantes:\n");
        
        for (Map.Entry<String, Integer> entry : receta.getIngredients().entrySet()) {
            String ingrediente = entry.getKey();
            int requerido = entry.getValue();
            int disponible = inventarioActual.getOrDefault(ingrediente, 0);
            
            if (disponible < requerido) {
                sb.append(String.format("  - %s: necesitas %d, tienes %d (faltan %d)\n",
                    ingrediente, requerido, disponible, requerido - disponible));
            }
        }
        
        return sb.toString();
    }
}
```

##### 9. RecetaNoEncontradaException ⭐

```java
package tech.hellsoft.trading.exception;

/**
 * Se lanza cuando no existe una receta para el producto solicitado.
 */
public class RecetaNoEncontradaException extends ProduccionException {
    
    private final String producto;
    
    public RecetaNoEncontradaException(String producto) {
        super(String.format("No se encontró receta para el producto: %s", producto));
        this.producto = producto;
    }
    
    public String getProducto() {
        return producto;
    }
}
```

##### 10. ConfiguracionException (Clase Base) - Ya existe parcialmente

Verificar que exista, si no:

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

##### 11. SnapshotCorruptoException

```java
package tech.hellsoft.trading.exception;

/**
 * Se lanza cuando un archivo de snapshot está corrupto o no se puede leer.
 */
public class SnapshotCorruptoException extends ConfiguracionException {
    
    private final String archivoSnapshot;
    
    public SnapshotCorruptoException(String archivoSnapshot, Throwable causa) {
        super(String.format("El snapshot %s está corrupto o no se puede leer", archivoSnapshot), causa);
        this.archivoSnapshot = archivoSnapshot;
    }
    
    public String getArchivoSnapshot() {
        return archivoSnapshot;
    }
}
```

#### Criterios de Éxito
- [ ] 11 archivos de excepción creados
- [ ] Jerarquía correcta (herencia)
- [ ] Campos adicionales en excepciones críticas
- [ ] Getters para todos los campos
- [ ] Mensajes descriptivos

---

### ✅ Tarea 2: RecetaValidator - Validación de Ingredientes
**Prioridad:** 🔴🔴🔴🔴 ALTA  
**Tiempo estimado:** 2-3 horas  
**Archivo:** `src/main/java/tech/hellsoft/trading/RecetaValidator.java`

#### Descripción
Clase de utilidad para validar y consumir ingredientes en producción premium.

#### Implementación

```java
package tech.hellsoft.trading;

import tech.hellsoft.trading.exception.IngredientesInsuficientesException;
import tech.hellsoft.trading.model.Recipe;
import java.util.Map;

/**
 * Validador de recetas e ingredientes para producción premium.
 */
public final class RecetaValidator {
    
    // Constructor privado - clase de utilidad
    private RecetaValidator() {
    }
    
    /**
     * Verifica si hay suficientes ingredientes para producir según la receta.
     * 
     * @param receta Receta de producción
     * @param inventario Inventario actual
     * @return true si hay suficientes ingredientes, false si no
     */
    public static boolean puedeProducir(Recipe receta, Map<String, Integer> inventario) {
        if (receta == null || inventario == null) {
            return false;
        }
        
        // Si es producción básica (sin ingredientes), siempre se puede
        if (receta.isBasic()) {
            return true;
        }
        
        // Verificar cada ingrediente requerido
        Map<String, Integer> ingredientes = receta.getIngredients();
        for (Map.Entry<String, Integer> entry : ingredientes.entrySet()) {
            String ingrediente = entry.getKey();
            int cantidadRequerida = entry.getValue();
            int cantidadDisponible = inventario.getOrDefault(ingrediente, 0);
            
            // Si falta algún ingrediente, no se puede producir
            if (cantidadDisponible < cantidadRequerida) {
                return false;
            }
        }
        
        // Todos los ingredientes están disponibles
        return true;
    }
    
    /**
     * Consume los ingredientes del inventario según la receta.
     * 
     * IMPORTANTE: Este método MODIFICA el inventario directamente.
     * Solo llamar después de verificar con puedeProducir().
     * 
     * @param receta Receta de producción
     * @param inventario Inventario actual (se modifica)
     * @throws IngredientesInsuficientesException Si faltan ingredientes
     */
    public static void consumirIngredientes(Recipe receta, Map<String, Integer> inventario) 
            throws IngredientesInsuficientesException {
        
        if (receta == null || inventario == null) {
            throw new IllegalArgumentException("Receta e inventario no pueden ser null");
        }
        
        // Si es producción básica, no hay nada que consumir
        if (receta.isBasic()) {
            return;
        }
        
        // Verificar primero que haya suficiente de todo
        if (!puedeProducir(receta, inventario)) {
            throw new IngredientesInsuficientesException(receta, inventario);
        }
        
        // Consumir cada ingrediente
        Map<String, Integer> ingredientes = receta.getIngredients();
        for (Map.Entry<String, Integer> entry : ingredientes.entrySet()) {
            String ingrediente = entry.getKey();
            int cantidadRequerida = entry.getValue();
            int cantidadActual = inventario.get(ingrediente);
            
            // Restar del inventario
            inventario.put(ingrediente, cantidadActual - cantidadRequerida);
        }
    }
    
    /**
     * Calcula qué ingredientes faltan para producir.
     * Útil para mostrar mensajes al usuario.
     * 
     * @param receta Receta de producción
     * @param inventario Inventario actual
     * @return Map con ingredientes faltantes y cantidades (vacío si no falta nada)
     */
    public static Map<String, Integer> calcularIngredientesFaltantes(
            Recipe receta, Map<String, Integer> inventario) {
        
        Map<String, Integer> faltantes = new java.util.HashMap<>();
        
        if (receta == null || inventario == null || receta.isBasic()) {
            return faltantes;
        }
        
        Map<String, Integer> ingredientes = receta.getIngredients();
        for (Map.Entry<String, Integer> entry : ingredientes.entrySet()) {
            String ingrediente = entry.getKey();
            int requerido = entry.getValue();
            int disponible = inventario.getOrDefault(ingrediente, 0);
            
            if (disponible < requerido) {
                faltantes.put(ingrediente, requerido - disponible);
            }
        }
        
        return faltantes;
    }
    
    /**
     * Genera un resumen legible de los ingredientes requeridos.
     * 
     * @param receta Receta de producción
     * @return String descriptivo de ingredientes
     */
    public static String generarResumenIngredientes(Recipe receta) {
        if (receta == null || receta.isBasic()) {
            return "Sin ingredientes (producción básica)";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Ingredientes requeridos: ");
        
        Map<String, Integer> ingredientes = receta.getIngredients();
        int count = 0;
        for (Map.Entry<String, Integer> entry : ingredientes.entrySet()) {
            if (count > 0) {
                sb.append(", ");
            }
            sb.append(entry.getValue()).append(" ").append(entry.getKey());
            count++;
        }
        
        return sb.toString();
    }
}
```

#### Criterios de Éxito
- [ ] `puedeProducir()` valida correctamente
- [ ] `consumirIngredientes()` modifica el inventario
- [ ] Lanza excepción cuando faltan ingredientes
- [ ] Métodos de utilidad funcionan
- [ ] Maneja casos edge (null, básico)

---

### ✅ Tarea 3: SnapshotManager - Serialización Binaria
**Prioridad:** 🟡🟡🟡 MEDIA  
**Tiempo estimado:** 3-4 horas  
**Archivo:** `src/main/java/tech/hellsoft/trading/SnapshotManager.java`

#### Descripción
Maneja la serialización y deserialización del estado del cliente en archivos binarios.

#### Implementación

```java
package tech.hellsoft.trading;

import tech.hellsoft.trading.exception.SnapshotCorruptoException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 * Gestor de snapshots (serialización binaria del estado).
 */
public final class SnapshotManager {
    
    private static final String EXTENSION = ".bin";
    private static final String PREFIJO = "snapshot_";
    
    // Constructor privado - clase de utilidad
    private SnapshotManager() {
    }
    
    /**
     * Guarda el estado actual en un archivo binario.
     * 
     * El archivo se nombra: snapshot_<timestamp>.bin
     * 
     * @param estado Estado a guardar
     * @param directorio Directorio donde guardar (se crea si no existe)
     * @throws IOException Si hay error al escribir el archivo
     */
    public static void guardar(EstadoCliente estado, String directorio) throws IOException {
        if (estado == null) {
            throw new IllegalArgumentException("Estado no puede ser null");
        }
        
        // Crear directorio si no existe
        Path dirPath = Paths.get(directorio);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        
        // Generar nombre de archivo con timestamp
        long timestamp = System.currentTimeMillis();
        String nombreArchivo = PREFIJO + timestamp + EXTENSION;
        Path archivoPath = dirPath.resolve(nombreArchivo);
        
        // Actualizar timestamp en el estado
        estado.setTimestampSnapshot(timestamp);
        
        // Serializar a archivo
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(archivoPath.toFile()))) {
            oos.writeObject(estado);
        }
        
        System.out.println("💾 Snapshot guardado: " + nombreArchivo);
        System.out.println("   P&L: " + String.format("%.2f%%", estado.calcularPL()));
    }
    
    /**
     * Carga un estado desde un archivo binario.
     * 
     * @param archivoPath Ruta completa del archivo a cargar
     * @return Estado deserializado
     * @throws SnapshotCorruptoException Si el archivo está corrupto
     * @throws IOException Si hay error al leer el archivo
     */
    public static EstadoCliente cargar(String archivoPath) 
            throws SnapshotCorruptoException, IOException {
        
        Path path = Paths.get(archivoPath);
        
        // Verificar que el archivo existe
        if (!Files.exists(path)) {
            throw new IOException("Archivo no encontrado: " + archivoPath);
        }
        
        // Deserializar
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(path.toFile()))) {
            
            Object objeto = ois.readObject();
            
            // Verificar que es del tipo correcto
            if (!(objeto instanceof EstadoCliente)) {
                throw new SnapshotCorruptoException(
                    archivoPath, 
                    new ClassCastException("El archivo no contiene un EstadoCliente")
                );
            }
            
            return (EstadoCliente) objeto;
            
        } catch (ClassNotFoundException | InvalidClassException e) {
            throw new SnapshotCorruptoException(archivoPath, e);
        }
    }
    
    /**
     * Lista todos los snapshots disponibles en un directorio.
     * 
     * @param directorio Directorio donde buscar snapshots
     * @return Lista de archivos de snapshot, ordenados por fecha (más reciente primero)
     */
    public static List<File> listarSnapshots(String directorio) {
        List<File> snapshots = new ArrayList<>();
        
        Path dirPath = Paths.get(directorio);
        
        // Si el directorio no existe, retornar lista vacía
        if (!Files.exists(dirPath)) {
            return snapshots;
        }
        
        // Buscar archivos .bin
        File dir = dirPath.toFile();
        File[] archivos = dir.listFiles((d, name) -> 
            name.startsWith(PREFIJO) && name.endsWith(EXTENSION)
        );
        
        if (archivos != null) {
            // Ordenar por fecha de modificación (más reciente primero)
            java.util.Arrays.sort(archivos, Comparator.comparingLong(File::lastModified).reversed());
            snapshots.addAll(java.util.Arrays.asList(archivos));
        }
        
        return snapshots;
    }
    
    /**
     * Muestra una lista interactiva de snapshots para seleccionar.
     * 
     * @param directorio Directorio donde buscar
     * @return Información de snapshots para mostrar al usuario
     */
    public static String generarListaSnapshots(String directorio) {
        List<File> snapshots = listarSnapshots(directorio);
        
        if (snapshots.isEmpty()) {
            return "No hay snapshots disponibles en " + directorio;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("📂 Snapshots disponibles:\n");
        
        for (int i = 0; i < snapshots.size(); i++) {
            File archivo = snapshots.get(i);
            long timestamp = archivo.lastModified();
            long minutosAtras = (System.currentTimeMillis() - timestamp) / (1000 * 60);
            
            sb.append(String.format("%d. %s (hace %d minutos)\n",
                i + 1, archivo.getName(), minutosAtras));
        }
        
        return sb.toString();
    }
    
    /**
     * Elimina snapshots antiguos, manteniendo solo los N más recientes.
     * 
     * @param directorio Directorio de snapshots
     * @param mantener Cantidad de snapshots a mantener
     * @return Cantidad de archivos eliminados
     */
    public static int limpiarSnapshotsAntiguos(String directorio, int mantener) {
        List<File> snapshots = listarSnapshots(directorio);
        
        if (snapshots.size() <= mantener) {
            return 0; // No hay nada que eliminar
        }
        
        int eliminados = 0;
        
        // Eliminar los más antiguos (están al final de la lista)
        for (int i = mantener; i < snapshots.size(); i++) {
            if (snapshots.get(i).delete()) {
                eliminados++;
            }
        }
        
        return eliminados;
    }
}
```

#### Criterios de Éxito
- [ ] Guarda estados correctamente
- [ ] Carga estados sin errores
- [ ] Maneja archivos corruptos elegantemente
- [ ] Lista snapshots ordenados por fecha
- [ ] Crea directorio si no existe

---

### ✅ Tarea 4: Tests de RecetaValidator
**Prioridad:** 🟡🟡 MEDIA  
**Tiempo estimado:** 2-3 horas  
**Archivo:** `src/test/java/tech/hellsoft/trading/RecetaValidatorTest.java`

#### Implementación

```java
package tech.hellsoft.trading;

import tech.hellsoft.trading.exception.IngredientesInsuficientesException;
import tech.hellsoft.trading.model.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class RecetaValidatorTest {
    
    private Recipe recetaPremium;
    private Recipe recetaBasica;
    private Map<String, Integer> inventario;
    
    @BeforeEach
    void setUp() {
        // Crear receta premium: GUACA (5 FOSFO + 3 PITA)
        Map<String, Integer> ingredientes = new HashMap<>();
        ingredientes.put("FOSFO", 5);
        ingredientes.put("PITA", 3);
        recetaPremium = new Recipe("GUACA", ingredientes, 1.30);
        
        // Crear receta básica (sin ingredientes)
        recetaBasica = new Recipe("PALTA-OIL", new HashMap<>(), 1.0);
        
        // Inventario de prueba
        inventario = new HashMap<>();
    }
    
    @Test
    void testPuedeProducir_ConIngredientesSuficientes() {
        inventario.put("FOSFO", 10);
        inventario.put("PITA", 5);
        
        boolean puede = RecetaValidator.puedeProducir(recetaPremium, inventario);
        
        assertTrue(puede);
    }
    
    @Test
    void testPuedeProducir_ConIngredientesInsuficientes() {
        inventario.put("FOSFO", 3); // Falta 2
        inventario.put("PITA", 5);
        
        boolean puede = RecetaValidator.puedeProducir(recetaPremium, inventario);
        
        assertFalse(puede);
    }
    
    @Test
    void testPuedeProducir_SinIngredientes() {
        // Inventario vacío, receta premium
        boolean puede = RecetaValidator.puedeProducir(recetaPremium, inventario);
        
        assertFalse(puede);
    }
    
    @Test
    void testPuedeProducir_RecetaBasica() {
        // Receta básica siempre se puede producir
        boolean puede = RecetaValidator.puedeProducir(recetaBasica, inventario);
        
        assertTrue(puede);
    }
    
    @Test
    void testConsumirIngredientes_Exitoso() throws Exception {
        inventario.put("FOSFO", 10);
        inventario.put("PITA", 5);
        
        RecetaValidator.consumirIngredientes(recetaPremium, inventario);
        
        assertEquals(5, inventario.get("FOSFO")); // 10 - 5 = 5
        assertEquals(2, inventario.get("PITA"));  // 5 - 3 = 2
    }
    
    @Test
    void testConsumirIngredientes_InsuficientesLanzaExcepcion() {
        inventario.put("FOSFO", 2); // Insuficiente
        inventario.put("PITA", 5);
        
        assertThrows(IngredientesInsuficientesException.class, () -> {
            RecetaValidator.consumirIngredientes(recetaPremium, inventario);
        });
        
        // Verificar que NO se modificó el inventario
        assertEquals(2, inventario.get("FOSFO"));
        assertEquals(5, inventario.get("PITA"));
    }
    
    @Test
    void testConsumirIngredientes_RecetaBasicaNoConsume() throws Exception {
        inventario.put("FOSFO", 10);
        
        RecetaValidator.consumirIngredientes(recetaBasica, inventario);
        
        // No debe modificar inventario
        assertEquals(10, inventario.get("FOSFO"));
    }
    
    @Test
    void testCalcularIngredientesFaltantes() {
        inventario.put("FOSFO", 3); // Faltan 2
        inventario.put("PITA", 1);  // Faltan 2
        
        Map<String, Integer> faltantes = RecetaValidator.calcularIngredientesFaltantes(
            recetaPremium, inventario
        );
        
        assertEquals(2, faltantes.get("FOSFO"));
        assertEquals(2, faltantes.get("PITA"));
    }
    
    @Test
    void testGenerarResumenIngredientes() {
        String resumen = RecetaValidator.generarResumenIngredientes(recetaPremium);
        
        assertTrue(resumen.contains("5"));
        assertTrue(resumen.contains("FOSFO"));
        assertTrue(resumen.contains("3"));
        assertTrue(resumen.contains("PITA"));
    }
}
```

---

### ✅ Tarea 5: Tests de SnapshotManager
**Prioridad:** 🟡 MEDIA  
**Tiempo estimado:** 2-3 horas  
**Archivo:** `src/test/java/tech/hellsoft/trading/SnapshotManagerTest.java`

#### Implementación

```java
package tech.hellsoft.trading;

import tech.hellsoft.trading.exception.SnapshotCorruptoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

class SnapshotManagerTest {
    
    @TempDir
    Path tempDir;
    
    private EstadoCliente estadoOriginal;
    
    @BeforeEach
    void setUp() {
        estadoOriginal = new EstadoCliente();
        estadoOriginal.setSaldo(12500.0);
        estadoOriginal.setSaldoInicial(10000.0);
        estadoOriginal.getInventario().put("FOSFO", 50);
        estadoOriginal.getInventario().put("PITA", 30);
    }
    
    @Test
    void testGuardar_CreaArchivo() throws IOException {
        String directorio = tempDir.toString();
        
        SnapshotManager.guardar(estadoOriginal, directorio);
        
        List<File> snapshots = SnapshotManager.listarSnapshots(directorio);
        assertEquals(1, snapshots.size());
        assertTrue(snapshots.get(0).getName().startsWith("snapshot_"));
        assertTrue(snapshots.get(0).getName().endsWith(".bin"));
    }
    
    @Test
    void testCargar_RecuperaEstado() throws Exception {
        String directorio = tempDir.toString();
        
        // Guardar
        SnapshotManager.guardar(estadoOriginal, directorio);
        
        // Obtener el archivo creado
        List<File> snapshots = SnapshotManager.listarSnapshots(directorio);
        String archivoPath = snapshots.get(0).getAbsolutePath();
        
        // Cargar
        EstadoCliente estadoCargado = SnapshotManager.cargar(archivoPath);
        
        // Verificar que sea igual
        assertEquals(estadoOriginal.getSaldo(), estadoCargado.getSaldo());
        assertEquals(estadoOriginal.getSaldoInicial(), estadoCargado.getSaldoInicial());
        assertEquals(50, estadoCargado.getInventario().get("FOSFO"));
        assertEquals(30, estadoCargado.getInventario().get("PITA"));
    }
    
    @Test
    void testCargar_ArchivoInexistenteL anzaExcepcion() {
        assertThrows(IOException.class, () -> {
            SnapshotManager.cargar("archivo_que_no_existe.bin");
        });
    }
    
    @Test
    void testListarSnapshots_Ordenados() throws Exception {
        String directorio = tempDir.toString();
        
        // Crear varios snapshots
        SnapshotManager.guardar(estadoOriginal, directorio);
        Thread.sleep(100);
        estadoOriginal.setSaldo(13000.0);
        SnapshotManager.guardar(estadoOriginal, directorio);
        Thread.sleep(100);
        estadoOriginal.setSaldo(14000.0);
        SnapshotManager.guardar(estadoOriginal, directorio);
        
        List<File> snapshots = SnapshotManager.listarSnapshots(directorio);
        
        assertEquals(3, snapshots.size());
        // El más reciente debe estar primero
        assertTrue(snapshots.get(0).lastModified() >= snapshots.get(1).lastModified());
        assertTrue(snapshots.get(1).lastModified() >= snapshots.get(2).lastModified());
    }
    
    @Test
    void testLimpiarSnapshotsAntiguos() throws Exception {
        String directorio = tempDir.toString();
        
        // Crear 5 snapshots
        for (int i = 0; i < 5; i++) {
            SnapshotManager.guardar(estadoOriginal, directorio);
            Thread.sleep(50);
        }
        
        // Mantener solo 2
        int eliminados = SnapshotManager.limpiarSnapshotsAntiguos(directorio, 2);
        
        assertEquals(3, eliminados);
        
        List<File> snapshots = SnapshotManager.listarSnapshots(directorio);
        assertEquals(2, snapshots.size());
    }
}
```

---

## 📊 Resumen de Tu Trabajo

| Tarea | Horas | Prioridad | Estado |
|-------|-------|-----------|--------|
| Excepciones (11 clases) | 2-3h | 🔴 MÁXIMA | ⬜ |
| RecetaValidator | 2-3h | 🔴 ALTA | ⬜ |
| SnapshotManager | 3-4h | 🟡 MEDIA | ⬜ |
| Tests RecetaValidator | 2-3h | 🟡 MEDIA | ⬜ |
| Tests SnapshotManager | 2-3h | 🟡 MEDIA | ⬜ |
| **TOTAL** | **11-16h** | - | - |

---

## 🤝 Coordinación con el Equipo

### Dependencias
- **Necesitas de Persona 2:** `EstadoCliente` para SnapshotManager
- **Necesitas de Persona 2:** Modelo `Recipe` para RecetaValidator

### Te necesitan
- **Persona 1 necesita:** TODAS las excepciones
- **Persona 1 necesita:** RecetaValidator para `producir()`
- **Persona 1 necesita:** SnapshotManager para comandos snapshot

### Orden Sugerido
1. **Primero:** Excepciones (día 1 mañana) - todos las necesitan
2. **Segundo:** RecetaValidator (día 1 tarde)
3. **Tercero:** SnapshotManager (día 2 mañana)
4. **Cuarto:** Tests (día 2 tarde)

---

## 💡 Tips y Mejores Prácticas

1. **Para Excepciones:**
   - Copia el patrón de la primera
   - Cambia solo el mensaje y los campos
   - NO olvides los getters

2. **Para RecetaValidator:**
   - Empieza con `puedeProducir()`
   - Pruébalo manualmente con prints
   - Luego implementa `consumirIngredientes()`

3. **Para SnapshotManager:**
   - Usa try-with-resources para streams
   - Siempre verifica que el directorio existe
   - Maneja IOException elegantemente

4. **Para Tests:**
   - Usa `@TempDir` para tests de archivos
   - No dejes archivos basura
   - Verifica excepciones con `assertThrows`

---

## 🆘 Si Te Atoras

1. **Excepciones no compilan:**
   - Verifica la jerarquía (extends)
   - Asegúrate de llamar `super(mensaje)`
   - Importa las clases necesarias

2. **RecetaValidator da falsos positivos:**
   - Imprime el inventario y los ingredientes
   - Verifica el método `isBasic()` de Recipe
   - Asegúrate de usar `getOrDefault()`

3. **SnapshotManager falla:**
   - Verifica que EstadoCliente sea Serializable
   - Usa try-catch para debugging
   - Verifica permisos del directorio

4. **Tests no pasan:**
   - Lee el mensaje de error completo
   - Usa el debugger
   - Verifica los valores esperados

---

## ✅ Checklist Final

Antes de considerar tu trabajo terminado:

- [ ] 11 clases de excepción creadas
- [ ] Jerarquía correcta implementada
- [ ] RecetaValidator.puedeProducir() funciona
- [ ] RecetaValidator.consumirIngredientes() funciona
- [ ] SnapshotManager.guardar() funciona
- [ ] SnapshotManager.cargar() funciona
- [ ] SnapshotManager.listarSnapshots() funciona
- [ ] Al menos 8 tests de RecetaValidator
- [ ] Al menos 5 tests de SnapshotManager
- [ ] Todos los tests pasan ✅
- [ ] Integrado con ClienteBolsa
- [ ] Code review por otro miembro
- [ ] Merge a main exitoso

---

**¡Éxito con tu desarrollo! 🚀**

Tu código es la base de la robustez del sistema. ¡Cada validación cuenta!

