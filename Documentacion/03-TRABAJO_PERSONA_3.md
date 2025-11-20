# 👤 PERSONA 3

**Responsabilidad**: Snapshot Manager, Comandos Simples y Testing  
**Complejidad**: Baja  
**Tiempo estimado**: 8-10 horas  
**Peso en la evaluación**: ~20% del proyecto

---

## 📋 Resumen de Tareas

Esta persona se encarga de:
1. **SnapshotManager**: Serialización binaria para guardar/cargar estado
2. **Comandos simples en Main.java**: status, ofertas, aceptar oferta, ayuda
3. **Testing y validación**: Probar las implementaciones de otros
4. **Documentación**: JavaDoc y comentarios

---

## 💾 TAREA 1: SnapshotManager - Serialización Binaria (13% de la nota)

### Ubicación
`src/main/java/tech/hellsoft/trading/util/SnapshotManager.java`

### Descripción
Esta clase guarda y carga el estado completo del cliente usando serialización binaria de Java. Es crítico para recuperarse de crashes durante el torneo.

### Implementación Completa

```java
package tech.hellsoft.trading.util;

import tech.hellsoft.trading.EstadoCliente;
import tech.hellsoft.trading.exception.SnapshotCorruptoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utilidad para guardar y cargar snapshots del estado del cliente.
 * Usa serialización binaria de Java para persistencia.
 */
public final class SnapshotManager {
    
    private static final String SNAPSHOT_DIR = "snapshots";
    private static final String SNAPSHOT_PREFIX = "snapshot_";
    private static final String SNAPSHOT_EXTENSION = ".bin";
    
    private SnapshotManager() {
        // Clase utilitaria - no instanciable
    }
    
    /**
     * Guarda el estado actual en un archivo binario.
     * 
     * @param estado Estado del cliente a guardar
     * @param archivo Nombre del archivo (sin extensión)
     * @throws IOException Si falla la escritura
     */
    public static void guardar(EstadoCliente estado, String archivo) throws IOException {
        // Crear directorio si no existe
        Path dirPath = Paths.get(SNAPSHOT_DIR);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        
        // Construir ruta completa del archivo
        String nombreArchivo = archivo.endsWith(SNAPSHOT_EXTENSION) 
            ? archivo 
            : archivo + SNAPSHOT_EXTENSION;
        Path archivoPath = dirPath.resolve(nombreArchivo);
        
        // Actualizar timestamp del snapshot
        estado.setTimestampSnapshot(System.currentTimeMillis());
        
        // Guardar usando ObjectOutputStream
        try (FileOutputStream fos = new FileOutputStream(archivoPath.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(estado);
            oos.flush();
            
            System.out.println("💾 Snapshot guardado: " + archivoPath);
            System.out.printf("   Saldo: $%.2f | P&L: %+.2f%%%n", 
                    estado.getSaldo(), estado.calcularPL());
        }
    }
    
    /**
     * Guarda el estado con un nombre automático basado en timestamp.
     * 
     * @param estado Estado del cliente a guardar
     * @return Nombre del archivo creado
     * @throws IOException Si falla la escritura
     */
    public static String guardarAutomatico(EstadoCliente estado) throws IOException {
        String nombreArchivo = SNAPSHOT_PREFIX + System.currentTimeMillis();
        guardar(estado, nombreArchivo);
        return nombreArchivo + SNAPSHOT_EXTENSION;
    }
    
    /**
     * Carga un snapshot desde un archivo binario.
     * 
     * @param archivo Nombre del archivo (sin extensión)
     * @return Estado del cliente cargado
     * @throws SnapshotCorruptoException Si el archivo está corrupto
     * @throws IOException Si falla la lectura
     */
    public static EstadoCliente cargar(String archivo) 
            throws SnapshotCorruptoException, IOException {
        
        // Construir ruta completa del archivo
        String nombreArchivo = archivo.endsWith(SNAPSHOT_EXTENSION) 
            ? archivo 
            : archivo + SNAPSHOT_EXTENSION;
        Path archivoPath = Paths.get(SNAPSHOT_DIR, nombreArchivo);
        
        // Verificar que el archivo existe
        if (!Files.exists(archivoPath)) {
            throw new SnapshotCorruptoException(nombreArchivo, 
                    "Archivo no encontrado");
        }
        
        // Cargar usando ObjectInputStream
        try (FileInputStream fis = new FileInputStream(archivoPath.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            Object obj = ois.readObject();
            
            if (!(obj instanceof EstadoCliente)) {
                throw new SnapshotCorruptoException(nombreArchivo, 
                        "El archivo no contiene un EstadoCliente válido");
            }
            
            EstadoCliente estado = (EstadoCliente) obj;
            
            System.out.println("✅ Snapshot cargado: " + nombreArchivo);
            System.out.printf("   Saldo: $%.2f | P&L: %+.2f%%%n", 
                    estado.getSaldo(), estado.calcularPL());
            
            return estado;
            
        } catch (ClassNotFoundException e) {
            throw new SnapshotCorruptoException(nombreArchivo, e);
        } catch (InvalidClassException e) {
            throw new SnapshotCorruptoException(nombreArchivo, 
                    "Versión incompatible de EstadoCliente");
        } catch (StreamCorruptedException e) {
            throw new SnapshotCorruptoException(nombreArchivo, 
                    "Archivo corrupto o formato inválido");
        }
    }
    
    /**
     * Lista todos los snapshots disponibles.
     * 
     * @return Lista de información de snapshots
     * @throws IOException Si falla la lectura del directorio
     */
    public static List<SnapshotInfo> listarSnapshots() throws IOException {
        Path dirPath = Paths.get(SNAPSHOT_DIR);
        
        // Si el directorio no existe, retornar lista vacía
        if (!Files.exists(dirPath)) {
            return new ArrayList<>();
        }
        
        // Buscar todos los archivos .bin en el directorio
        List<SnapshotInfo> snapshots = Files.list(dirPath)
            .filter(path -> path.toString().endsWith(SNAPSHOT_EXTENSION))
            .map(path -> {
                try {
                    String nombre = path.getFileName().toString();
                    long tamanio = Files.size(path);
                    long timestamp = Files.getLastModifiedTime(path).toMillis();
                    
                    // Intentar cargar solo para obtener P&L
                    double pl = 0.0;
                    try {
                        EstadoCliente estado = cargar(nombre);
                        pl = estado.calcularPL();
                    } catch (Exception e) {
                        // Si falla, usar 0.0
                    }
                    
                    return new SnapshotInfo(nombre, tamanio, timestamp, pl);
                } catch (IOException e) {
                    return null;
                }
            })
            .filter(info -> info != null)
            .sorted(Comparator.comparing(SnapshotInfo::getTimestamp).reversed())
            .collect(Collectors.toList());
        
        return snapshots;
    }
    
    /**
     * Elimina un snapshot.
     * 
     * @param archivo Nombre del archivo a eliminar
     * @throws IOException Si falla la eliminación
     */
    public static void eliminar(String archivo) throws IOException {
        String nombreArchivo = archivo.endsWith(SNAPSHOT_EXTENSION) 
            ? archivo 
            : archivo + SNAPSHOT_EXTENSION;
        Path archivoPath = Paths.get(SNAPSHOT_DIR, nombreArchivo);
        
        if (Files.exists(archivoPath)) {
            Files.delete(archivoPath);
            System.out.println("🗑️ Snapshot eliminado: " + nombreArchivo);
        } else {
            System.out.println("⚠️ Snapshot no encontrado: " + nombreArchivo);
        }
    }
    
    /**
     * Información sobre un snapshot guardado.
     */
    public static class SnapshotInfo {
        private final String nombre;
        private final long tamanio;
        private final long timestamp;
        private final double pl;
        
        public SnapshotInfo(String nombre, long tamanio, long timestamp, double pl) {
            this.nombre = nombre;
            this.tamanio = tamanio;
            this.timestamp = timestamp;
            this.pl = pl;
        }
        
        public String getNombre() {
            return nombre;
        }
        
        public long getTamanio() {
            return tamanio;
        }
        
        public long getTimestamp() {
            return timestamp;
        }
        
        public double getPl() {
            return pl;
        }
        
        public String getTiempoRelativo() {
            long minutosAtras = (System.currentTimeMillis() - timestamp) / (60 * 1000);
            if (minutosAtras < 1) {
                return "hace menos de 1 minuto";
            } else if (minutosAtras == 1) {
                return "hace 1 minuto";
            } else if (minutosAtras < 60) {
                return "hace " + minutosAtras + " minutos";
            } else {
                long horasAtras = minutosAtras / 60;
                return "hace " + horasAtras + " hora(s)";
            }
        }
        
        public String getFechaFormateada() {
            LocalDateTime fecha = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp), 
                ZoneId.systemDefault()
            );
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return fecha.format(formatter);
        }
        
        @Override
        public String toString() {
            return String.format("%s (%s) - P&L: %+.2f%% - %s", 
                    nombre, getTiempoRelativo(), pl, getFechaFormateada());
        }
    }
}
```

---

## 💻 TAREA 2: Implementar Comandos Simples en Main.java

### Ubicación
`src/main/java/tech/hellsoft/trading/Main.java`

### 2.1 handleStatus()

Reemplazar el método existente con:

```java
private static void handleStatus(MyTradingBot bot) {
    System.out.println("\n📊 ESTADO ACTUAL");
    System.out.println("━━━━━━━━━━━━━━━━━━━━━");
    
    EstadoCliente estado = bot.getEstado();
    
    if (estado == null) {
        System.out.println("⚠️ Estado no inicializado. Haz login primero.");
        return;
    }
    
    double saldo = estado.getSaldo();
    double valorInventario = estado.calcularValorInventario();
    double patrimonioNeto = saldo + valorInventario;
    double pl = estado.calcularPL();
    
    System.out.printf("💰 Saldo: $%.2f%n", saldo);
    System.out.printf("📦 Valor inventario: $%.2f%n", valorInventario);
    System.out.printf("💎 Patrimonio neto: $%.2f%n", patrimonioNeto);
    System.out.printf("📈 P&L: %+.2f%% %s%n", pl, pl >= 0 ? "⬆" : "⬇");
    
    if (estado.getNombreEquipo() != null && !estado.getNombreEquipo().isEmpty()) {
        System.out.println("👥 Equipo: " + estado.getNombreEquipo());
    }
    
    System.out.println();
}
```

### 2.2 handleOfertas()

Reemplazar el método existente con:

```java
private static void handleOfertas(MyTradingBot bot) {
    System.out.println("\n📬 OFERTAS PENDIENTES");
    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    
    EstadoCliente estado = bot.getEstado();
    
    if (estado == null || estado.getOfertasPendientes().isEmpty()) {
        System.out.println("(sin ofertas pendientes)");
        System.out.println();
        return;
    }
    
    int contador = 1;
    for (Map.Entry<String, OfferMessage> entry : estado.getOfertasPendientes().entrySet()) {
        OfferMessage oferta = entry.getValue();
        
        System.out.println("[" + contador + "] Comprador: " + oferta.buyer());
        System.out.println("    Producto: " + oferta.product() + " × " + oferta.quantity());
        System.out.printf("    Precio ofrecido: $%.2f%n", oferta.maxPrice());
        System.out.println("    Offer ID: " + oferta.offerId());
        System.out.println();
        
        contador++;
    }
    
    System.out.println("💡 Usa 'aceptar <offerId>' para aceptar una oferta");
    System.out.println();
}
```

### 2.3 handleAceptarOferta()

Reemplazar el método existente con:

```java
private static void handleAceptarOferta(String[] parts, ConectorBolsa connector, MyTradingBot bot) {
    if (parts.length < 2) {
        System.out.println("❌ Uso: aceptar <offerId>");
        return;
    }

    String offerId = parts[1];
    
    try {
        ClienteBolsa cliente = bot.getCliente();
        
        if (cliente == null) {
            System.out.println("❌ Cliente no inicializado");
            return;
        }
        
        cliente.aceptarOferta(offerId);
        
    } catch (Exception e) {
        System.out.println("❌ Error: " + e.getMessage());
    }
}
```

### 2.4 Agregar Comandos de Snapshot

Agregar estos dos handlers nuevos:

```java
private static void handleSnapshotSave(MyTradingBot bot) {
    System.out.println("\n💾 GUARDAR SNAPSHOT");
    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    
    EstadoCliente estado = bot.getEstado();
    
    if (estado == null) {
        System.out.println("❌ Estado no inicializado");
        return;
    }
    
    try {
        String archivo = SnapshotManager.guardarAutomatico(estado);
        System.out.println("✅ Snapshot guardado exitosamente");
        System.out.println("   Archivo: " + archivo);
        System.out.println();
    } catch (IOException e) {
        System.out.println("❌ Error al guardar snapshot: " + e.getMessage());
    }
}

private static void handleSnapshotLoad(MyTradingBot bot, Scanner scanner) {
    System.out.println("\n📂 CARGAR SNAPSHOT");
    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    
    try {
        List<SnapshotManager.SnapshotInfo> snapshots = SnapshotManager.listarSnapshots();
        
        if (snapshots.isEmpty()) {
            System.out.println("⚠️ No hay snapshots guardados");
            System.out.println();
            return;
        }
        
        System.out.println("📂 Snapshots disponibles:\n");
        
        for (int i = 0; i < snapshots.size(); i++) {
            SnapshotManager.SnapshotInfo info = snapshots.get(i);
            System.out.printf("%d. %s%n", (i + 1), info.toString());
        }
        
        System.out.print("\nSelecciona snapshot (1-" + snapshots.size() + "): ");
        
        if (!scanner.hasNextLine()) {
            return;
        }
        
        String seleccion = scanner.nextLine().trim();
        
        try {
            int indice = Integer.parseInt(seleccion) - 1;
            
            if (indice < 0 || indice >= snapshots.size()) {
                System.out.println("❌ Selección inválida");
                return;
            }
            
            SnapshotManager.SnapshotInfo seleccionado = snapshots.get(indice);
            EstadoCliente estadoCargado = SnapshotManager.cargar(seleccionado.getNombre());
            
            // Reemplazar el estado del bot
            bot.setEstado(estadoCargado);
            
            System.out.println("✅ Estado cargado correctamente");
            System.out.printf("💰 Saldo: $%.2f%n", estadoCargado.getSaldo());
            System.out.printf("📈 P&L: %+.2f%%%n", estadoCargado.calcularPL());
            System.out.println();
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Selección inválida");
        }
        
    } catch (SnapshotCorruptoException e) {
        System.out.println("❌ Snapshot corrupto: " + e.getMessage());
    } catch (IOException e) {
        System.out.println("❌ Error al cargar snapshot: " + e.getMessage());
    }
}
```

### 2.5 Actualizar handleCommand() para incluir los nuevos comandos

Agregar estos casos al switch en `handleCommand()`:

```java
case "snapshot":
    if (parts.length > 1 && parts[1].equalsIgnoreCase("save")) {
        handleSnapshotSave(bot);
    } else if (parts.length > 1 && parts[1].equalsIgnoreCase("load")) {
        handleSnapshotLoad(bot, new Scanner(System.in));
    } else {
        System.out.println("❌ Uso: snapshot save | snapshot load");
    }
    break;
```

---

## 📝 TAREA 3: Completar EstadoCliente con Métodos Faltantes

### Ubicación
`src/main/java/tech/hellsoft/trading/EstadoCliente.java`

### Agregar estos métodos:

```java
public void setTimestampSnapshot(long timestamp) {
    this.timestampSnapshot = timestamp;
}

public long getTimestampSnapshot() {
    return timestampSnapshot;
}

public void setNombreEquipo(String nombre) {
    this.nombreEquipo = nombre;
}
```

---

## 🧪 TAREA 4: Testing y Validación

### 4.1 Test de SnapshotManager

Crear un método de prueba en Main o una clase de test:

```java
public static void testSnapshotManager() {
    System.out.println("🧪 TEST: SnapshotManager");
    
    try {
        // 1. Crear estado de prueba
        EstadoCliente estado = new EstadoCliente();
        estado.setSaldo(15000.0);
        estado.setSaldoInicial(10000.0);
        estado.getInventario().put("PALTA-OIL", 25);
        estado.getInventario().put("FOSFO", 10);
        estado.getPreciosActuales().put("PALTA-OIL", 26.0);
        estado.getPreciosActuales().put("FOSFO", 18.0);
        
        System.out.println("Estado original - Saldo: $" + estado.getSaldo() + 
                         ", P&L: " + estado.calcularPL() + "%");
        
        // 2. Guardar
        String archivo = SnapshotManager.guardarAutomatico(estado);
        System.out.println("✅ Guardado: " + archivo);
        
        // 3. Cargar
        EstadoCliente estadoCargado = SnapshotManager.cargar(archivo);
        System.out.println("Estado cargado - Saldo: $" + estadoCargado.getSaldo() + 
                         ", P&L: " + estadoCargado.calcularPL() + "%");
        
        // 4. Verificar que son iguales
        if (Math.abs(estado.getSaldo() - estadoCargado.getSaldo()) < 0.01 &&
            Math.abs(estado.calcularPL() - estadoCargado.calcularPL()) < 0.01) {
            System.out.println("✅ TEST PASÓ: Los datos coinciden");
        } else {
            System.out.println("❌ TEST FALLÓ: Los datos no coinciden");
        }
        
        // 5. Listar snapshots
        System.out.println("\n📂 Snapshots disponibles:");
        List<SnapshotManager.SnapshotInfo> snapshots = SnapshotManager.listarSnapshots();
        for (SnapshotManager.SnapshotInfo info : snapshots) {
            System.out.println("  - " + info);
        }
        
    } catch (Exception e) {
        System.out.println("❌ TEST FALLÓ: " + e.getMessage());
        e.printStackTrace();
    }
}
```

### 4.2 Validación de Integración

Probar el flujo completo:

1. **Iniciar programa** → Login
2. **Producir** algo
3. **Guardar snapshot** → `snapshot save`
4. **Producir** más y cambiar estado
5. **Cargar snapshot** → `snapshot load` → Verificar que volvió al estado anterior
6. **Comandos simples** → `status`, `inventario`, `precios`

---

## 📚 TAREA 5: Documentación JavaDoc

Agregar JavaDoc completo a todas las clases que implementaste:

### Ejemplo para SnapshotManager:

```java
/**
 * Utilidad para guardar y cargar snapshots del estado del cliente usando
 * serialización binaria de Java.
 * 
 * <p>Los snapshots son críticos para recuperarse de crashes durante el torneo.
 * Se recomienda guardar snapshots automáticos cada 30-60 segundos.</p>
 * 
 * <p>Ejemplo de uso:</p>
 * <pre>
 * // Guardar
 * SnapshotManager.guardarAutomatico(estado);
 * 
 * // Cargar
 * EstadoCliente estadoCargado = SnapshotManager.cargar("snapshot_123456789.bin");
 * 
 * // Listar disponibles
 * List&lt;SnapshotInfo&gt; snapshots = SnapshotManager.listarSnapshots();
 * </pre>
 * 
 * @author Equipo Avocasticos
 * @version 1.0
 * @see EstadoCliente
 * @see SnapshotCorruptoException
 */
```

---

## ✅ Checklist de Tareas

- [ ] Crear carpeta `snapshots/` en la raíz del proyecto
- [ ] Implementar SnapshotManager.guardar()
- [ ] Implementar SnapshotManager.cargar()
- [ ] Implementar SnapshotManager.guardarAutomatico()
- [ ] Implementar SnapshotManager.listarSnapshots()
- [ ] Implementar SnapshotManager.eliminar()
- [ ] Implementar SnapshotInfo (clase interna)
- [ ] Completar handleStatus() en Main
- [ ] Completar handleOfertas() en Main
- [ ] Completar handleAceptarOferta() en Main
- [ ] Implementar handleSnapshotSave() en Main
- [ ] Implementar handleSnapshotLoad() en Main
- [ ] Agregar métodos faltantes a EstadoCliente
- [ ] Crear testSnapshotManager()
- [ ] Ejecutar test y verificar que funciona
- [ ] Probar flujo completo de snapshot save/load
- [ ] Agregar JavaDoc a todas las clases
- [ ] Verificar que todas las excepciones se manejan correctamente

---

## 🧪 Testing Detallado

### Test 1: Guardar y Cargar Básico
```java
EstadoCliente estado = new EstadoCliente();
estado.setSaldo(12000.0);
estado.setSaldoInicial(10000.0);

// Guardar
SnapshotManager.guardarAutomatico(estado);

// Cargar
String archivo = "snapshot_" + System.currentTimeMillis() + ".bin";
EstadoCliente cargado = SnapshotManager.cargar(archivo);

// Verificar
assert cargado.getSaldo() == 12000.0;
assert cargado.getSaldoInicial() == 10000.0;
```

### Test 2: Snapshot con Inventario Complejo
```java
EstadoCliente estado = new EstadoCliente();
estado.getInventario().put("PALTA-OIL", 100);
estado.getInventario().put("FOSFO", 50);
estado.getInventario().put("GUACA", 30);
estado.getPreciosActuales().put("PALTA-OIL", 26.0);

String archivo = SnapshotManager.guardarAutomatico(estado);
EstadoCliente cargado = SnapshotManager.cargar(archivo);

// Verificar que el inventario se mantuvo
assert cargado.getInventario().get("PALTA-OIL") == 100;
assert cargado.getInventario().get("FOSFO") == 50;
```

### Test 3: Listar Snapshots
```java
// Crear varios snapshots
for (int i = 0; i < 3; i++) {
    EstadoCliente estado = new EstadoCliente();
    estado.setSaldo(10000.0 + (i * 1000));
    SnapshotManager.guardarAutomatico(estado);
    Thread.sleep(100); // Esperar para diferentes timestamps
}

// Listar
List<SnapshotManager.SnapshotInfo> snapshots = SnapshotManager.listarSnapshots();
assert snapshots.size() >= 3;

// Verificar orden (más reciente primero)
long timestamp1 = snapshots.get(0).getTimestamp();
long timestamp2 = snapshots.get(1).getTimestamp();
assert timestamp1 >= timestamp2;
```

---

## 📚 Referencias

- **Guia-Profesor.md**: Sección "SnapshotManager" (página 14)
- **Guia-Profesor.md**: Sección "ConsolaInteractiva - Comandos" (página 15-19)
- **Java Serialization Tutorial**: https://docs.oracle.com/javase/tutorial/jndi/objects/serial.html

---

## 🤝 Coordinación con Otros

- **Necesitas de Persona 1**: 
  - SnapshotCorruptoException (para SnapshotManager)
- **Necesitas de Persona 2**: 
  - EstadoCliente completado
  - ClienteBolsa para probar aceptarOferta()
- **Entregas a todos**: 
  - SnapshotManager funcionando
  - Comandos simples implementados
  - Tests que validan todo

---

## 💡 Tips Importantes

1. **Serialización**: Asegúrate de que EstadoCliente tenga `serialVersionUID`
2. **Directorio**: Crea la carpeta `snapshots/` antes de guardar
3. **Testing**: Prueba con snapshots corruptos (archivos de texto renombrados)
4. **Timing**: Los snapshots deben guardarse rápido (< 100ms)
5. **Tamaño**: Los archivos .bin son pequeños (típicamente < 10 KB)

---

## 🎯 Prioridades

1. **ALTA**: SnapshotManager (crítico para recovery)
2. **MEDIA**: Comandos status y ofertas
3. **BAJA**: JavaDoc y testing extensivo

---

**Estimación total**: 8-10 horas  
**Prioridad**: Media  
**Dificultad**: Baja  
**💡 Enfócate primero en SnapshotManager - es lo más importante**

---

## 🎁 BONUS: Auto-Snapshot (Opcional)

Si terminas temprano, puedes implementar auto-guardado cada 30 segundos:

```java
package tech.hellsoft.trading.util;

import tech.hellsoft.trading.EstadoCliente;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Guarda snapshots automáticamente cada N segundos.
 */
public class AutoSnapshot {
    
    private final Timer timer;
    private final EstadoCliente estado;
    private final int intervaloSegundos;
    
    public AutoSnapshot(EstadoCliente estado, int intervaloSegundos) {
        this.estado = estado;
        this.intervaloSegundos = intervaloSegundos;
        this.timer = new Timer("AutoSnapshot", true);
    }
    
    public void iniciar() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    SnapshotManager.guardarAutomatico(estado);
                    System.out.println("[AUTO-SNAPSHOT] Guardado automático completado");
                } catch (Exception e) {
                    System.err.println("[AUTO-SNAPSHOT] Error: " + e.getMessage());
                }
            }
        }, intervaloSegundos * 1000L, intervaloSegundos * 1000L);
        
        System.out.println("🔄 Auto-snapshot activado (cada " + intervaloSegundos + "s)");
    }
    
    public void detener() {
        timer.cancel();
        System.out.println("🛑 Auto-snapshot detenido");
    }
}

// Uso en Main:
// AutoSnapshot autoSnapshot = new AutoSnapshot(bot.getEstado(), 30);
// autoSnapshot.iniciar();
```

¡Buena suerte! 🚀

