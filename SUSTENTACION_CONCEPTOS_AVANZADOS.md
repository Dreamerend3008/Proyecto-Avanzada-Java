# 📚 CONCEPTOS AVANZADOS Y COMPLEJOS DEL PROYECTO
## Guía de Sustentación - Spacial Trading Bot

---

## 📋 ÍNDICE

1. [Arquitectura y Patrones de Diseño](#1-arquitectura-y-patrones-de-diseño)
2. [Programación Reactiva y Event-Driven](#2-programación-reactiva-y-event-driven)
3. [Recursión Compleja](#3-recursión-compleja)
4. [Serialización y Persistencia](#4-serialización-y-persistencia)
5. [Jerarquía de Excepciones Personalizadas](#5-jerarquía-de-excepciones-personalizadas)
6. [Concurrencia y Programación Asíncrona](#6-concurrencia-y-programación-asíncrona)
7. [Java Records y Features Modernos](#7-java-records-y-features-modernos)
8. [Validación con Yavi](#8-validación-con-yavi)
9. [Integración con SDK Externo](#9-integración-con-sdk-externo)
10. [Gestión de Estado Complejo](#10-gestión-de-estado-complejo)

---

## 1. ARQUITECTURA Y PATRONES DE DISEÑO

### 1.1 Patrón Observer (Event Listener)

**¿Qué es?** Un patrón de diseño comportamental donde un objeto (Subject) notifica automáticamente a múltiples observadores sobre cambios de estado.

**Implementación en el proyecto:**

```java
public class ClienteBolsa implements EventListener {
    @Override
    public void onFill(FillMessage fill) {
        // Reacciona automáticamente cuando el servidor envía un fill
        Product producto = fill.getProduct();
        int cantidad = fill.getFillQty();
        double precio = fill.getFillPrice();
        // Actualiza estado sin polling
    }
    
    @Override
    public void onTicker(TickerMessage ticker) {
        // Actualiza precios en tiempo real
        estado.getPreciosActuales().put(ticker.getProduct(), ticker.getMid());
    }
}
```

**¿Por qué es avanzado?**
- No usamos polling (revisar cada X segundos)
- El servidor "empuja" datos al cliente (push vs pull)
- Desacopla la lógica del cliente del servidor
- Permite procesamiento asíncrono de eventos

**Pregunta de sustentación:** 
*"¿Cuál es la diferencia entre polling y event-driven?"*
- **Polling**: Cliente pregunta constantemente "¿hay algo nuevo?" (ineficiente)
- **Event-driven**: Servidor avisa cuando hay algo nuevo (eficiente)

---

### 1.2 Patrón State Management

**¿Qué es?** Centralizar todo el estado de la aplicación en un único objeto inmutable o controlado.

**Implementación:**

```java
public class EstadoCliente implements Serializable {
    private double saldo;
    private Map<Product, Integer> inventario;
    private Map<Product, Double> preciosActuales;
    private Map<Product, Receta> recetas;
    private Map<String, OfferMessage> ofertasPendientes;
    private Rol rol;
    
    // Método de actualización atómica
    public void actualizarInventario(Product producto, int cantidad) {
        inventario.put(producto, inventario.getOrDefault(producto, 0) + cantidad);
    }
}
```

**¿Por qué es importante?**
- **Single Source of Truth**: Un solo lugar contiene la verdad
- **Inmutabilidad controlada**: Usamos Lombok para getters/setters controlados
- **Facilita debugging**: Todo el estado está en un solo objeto
- **Permite snapshots**: Podemos guardar/restaurar el estado completo

---

### 1.3 Patrón Strategy (Sin usar `else`)

**Filosofía del proyecto:** CERO keywords `else` en el código.

**❌ Código tradicional (con else):**
```java
public void procesarOrden(String tipo) {
    if (tipo.equals("comprar")) {
        ejecutarCompra();
    } else if (tipo.equals("vender")) {
        ejecutarVenta();
    } else {
        manejarError();
    }
}
```

**✅ Código del proyecto (sin else):**
```java
private void handleCommand(String command, String[] parts) {
    switch (command) {
        case "comprar" -> handleComprar(parts);
        case "vender" -> handleVender(parts);
        case "producir" -> handleProducir(parts);
        default -> System.out.println("❌ Comando desconocido");
    }
}
```

**Técnicas avanzadas usadas:**
1. **Guard Clauses** (return temprano)
2. **Switch expressions** (Java 25)
3. **Polimorfismo** cuando aplica

**Ventajas:**
- Código más lineal y legible
- Menos indentación (complejidad ciclomática menor)
- Más fácil de mantener

---

## 2. PROGRAMACIÓN REACTIVA Y EVENT-DRIVEN

### 2.1 Listener en Tiempo Real

**Concepto clave:** El bot no pregunta "¿hay datos?", sino que el servidor le avisa cuando hay datos.

```java
private boolean listenerActivo;

public void activarListener() {
    cliente.activarListener();
    listenerActivo = true;
    // Ahora el bot entra en "modo escucha"
    // Los métodos onTicker, onFill, onOffer se ejecutan automáticamente
}

@Override
public void onTicker(TickerMessage ticker) {
    // Este método se ejecuta AUTOMÁTICAMENTE cuando llega un ticker
    if (!listener) return;  // Guard clause
    
    System.out.println("📊 TICKER: " + ticker.getProduct() +
                       " | Bid: $" + ticker.getBestBid() +
                       " | Ask: $" + ticker.getBestAsk());
}
```

**Flujo del listener:**
1. Usuario escribe `listener` → Activa modo escucha
2. Servidor envía eventos → Métodos `on*` se ejecutan automáticamente
3. Usuario escribe `menu`/`salir` → Desactiva listener

**Implementación del loop:**
```java
while (ejecutando) {
    if(listenerActivo) {
        mostrarModoListener();
        String input = scanner.nextLine().trim();
        
        if ("salir".equalsIgnoreCase(input) || "menu".equalsIgnoreCase(input)) {
            detenerListener();
        }
        continue; // No ejecuta el resto del loop
    }
    
    // Modo normal...
    printMenu();
}
```

**¿Por qué es avanzado?**
- Dos modos de operación en un solo loop
- Procesamiento asíncrono de eventos
- No bloquea el thread principal

---

### 2.2 Manejo de Eventos Asíncronos

**Problema:** Los eventos del servidor llegan en cualquier momento, no cuando el código los espera.

**Solución:**
```java
@Override
public void onOffer(OfferMessage offer) {
    // Guardamos la oferta para procesarla después
    estado.getOfertasPendientes().put(offer.getOfferId(), offer);
    
    // Mostramos la notificación inmediatamente
    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    System.out.println("🔔 NUEVA OFERTA RECIBIDA");
    System.out.println("Offer ID: " + offer.getOfferId());
    System.out.println("Para aceptar: aceptar " + offer.getOfferId());
}
```

**Conceptos clave:**
- **Event Queue implícito**: Las ofertas se guardan en un Map
- **Procesamiento diferido**: El usuario decide cuándo aceptar
- **Thread-safety**: El Map debe ser thread-safe (concepto avanzado)

---

## 3. RECURSIÓN COMPLEJA

### 3.1 Algoritmo de Cálculo de Energía (Recursión Matemática)

**Problema:** Calcular cuántas unidades se pueden producir basándose en una fórmula recursiva:

```
Energía(nivel) = (baseEnergy + levelEnergy * nivel) × decay^nivel × branches^nivel
Total = Σ Energía(0 hasta maxDepth)
```

**Implementación:**

```java
public class CalculadoraProduccion {
    public static int calcularUnidades(Rol rol) {
        return calcularRecursivo(0, rol);
    }
    
    private static int calcularRecursivo(int nivel, Rol rol) {
        // CASO BASE: Salir cuando llegamos al máximo nivel
        if (nivel > rol.getMaxDepth()) {
            return 0;
        }
        
        // CÁLCULO POR NIVEL
        double energia = rol.getBaseEnergy() + (rol.getLevelEnergy() * nivel);
        double decay = Math.pow(rol.getDecay(), nivel);
        double branches = Math.pow(rol.getBranches(), nivel);
        double factor = decay * branches;
        
        int contribucion = (int) Math.round(energia * factor);
        
        // RECURSIÓN: Suma este nivel + todos los niveles siguientes
        return contribucion + calcularRecursivo(nivel + 1, rol);
    }
}
```

**Análisis de complejidad:**
- **Tipo de recursión:** Tail recursion (casi)
- **Complejidad temporal:** O(maxDepth)
- **Complejidad espacial:** O(maxDepth) por el call stack

**¿Por qué es recursiva y no iterativa?**
- La fórmula es naturalmente recursiva (suma acumulativa)
- Más legible y matemáticamente clara
- Fácil de probar nivel por nivel

**Bonus Premium:**
```java
public static int aplicarBonusPremium(int unidadesBase, double bonus) {
    return (int) Math.round(unidadesBase * bonus);
}
```

**Ejemplo de ejecución:**
```
Rol: branches=2, maxDepth=3, decay=0.8, baseEnergy=100, levelEnergy=50

Nivel 0: 100 × 0.8^0 × 2^0 = 100
Nivel 1: 150 × 0.8^1 × 2^1 = 240
Nivel 2: 200 × 0.8^2 × 2^2 = 512
Nivel 3: 250 × 0.8^3 × 2^3 = 1024
Total: 1876 unidades base

Con premium (bonus 1.3): 1876 × 1.3 = 2439 unidades
```

---

## 4. SERIALIZACIÓN Y PERSISTENCIA

### 4.1 Serialización Binaria (ObjectOutputStream)

**¿Qué es?** Convertir objetos Java en bytes para guardarlos en disco.

**Implementación:**

```java
public class SnapshotManager {
    public static void guardarEstado(EstadoCliente estado, String ruta) {
        ruta = "data/" + ruta + ".bin";
        
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(ruta))) {
            out.writeObject(estado);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar: " + e.getMessage());
        }
    }
    
    public static EstadoCliente cargarEstado(String ruta) {
        ruta = "data/" + ruta + ".bin";
        
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(ruta))) {
            return (EstadoCliente) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SnapshotCorruptoException("Error al cargar", e);
        }
    }
}
```

**Conceptos avanzados:**

1. **Try-with-resources:**
   - Cierra automáticamente los streams
   - Evita memory leaks
   - Syntax: `try (Resource r = ...) { }`

2. **Serializable interface:**
```java
public class EstadoCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    // ...
}
```

3. **serialVersionUID:**
   - Identifica la versión de la clase
   - Si cambia la clase, el deserializado puede fallar
   - Buena práctica: siempre definirlo

**¿Qué se guarda?**
- ✅ Saldo, inventario, recetas, ofertas
- ✅ Maps, Lists, primitivos
- ❌ Conexiones, threads, archivos abiertos (transient)

**Uso:**
```bash
# Guardar
> guardar estado_checkpoint1

# Cargar
> cargar estado_checkpoint1
```

---

### 4.2 Validación de Integridad

**Problema:** ¿Qué pasa si el archivo está corrupto?

**Solución:**
```java
try {
    return (EstadoCliente) in.readObject();
} catch (IOException | ClassNotFoundException e) {
    throw new SnapshotCorruptoException("Error al cargar", e);
}
```

**Excepciones manejadas:**
- `IOException`: Archivo no existe, permisos, disco lleno
- `ClassNotFoundException`: La clase cambió desde que se guardó
- `InvalidClassException`: serialVersionUID no coincide

---

## 5. JERARQUÍA DE EXCEPCIONES PERSONALIZADAS

### 5.1 Arquitectura de Excepciones

**Diseño:** Tres familias principales de excepciones.

```
RuntimeException (Java)
    ├── TradingException (abstracta)
    │   ├── SaldoInsuficienteException
    │   ├── InventarioInsuficienteException
    │   ├── ProductoNoAutorizadoException
    │   ├── PrecioNoDisponibleException
    │   └── OfertaExpiradaException
    │
    ├── ProduccionException (abstracta)
    │   ├── IngredientesInsuficientesException
    │   └── RecetaNoEncontradaException
    │
    └── ConfiguracionException (abstracta)
        ├── ConfiguracionInvalidaException
        └── SnapshotCorruptoException
```

**¿Por qué RuntimeException y no Exception?**
- No forzamos try-catch en todo el código
- Las validaciones de negocio son unchecked
- Código más limpio (no pollution de try-catch)

---

### 5.2 Excepciones con Estado (Rich Exceptions)

**Patrón:** Las excepciones llevan información útil para debugging.

**Ejemplo 1: SaldoInsuficienteException**
```java
public class SaldoInsuficienteException extends TradingException {
    private double saldoActual;
    private double costoRequerido;
    
    public SaldoInsuficienteException(double saldoActual, double costoRequerido) {
        super("Saldo insuficiente. Actual: " + saldoActual + 
              " | Necesario: " + costoRequerido);
        this.saldoActual = saldoActual;
        this.costoRequerido = costoRequerido;
    }
    
    public double getSaldoActual() { return saldoActual; }
    public double getCostoRequerido() { return costoRequerido; }
}
```

**Uso:**
```java
try {
    cliente.comprar(Product.FOSFO, 10, null);
} catch (SaldoInsuficienteException e) {
    System.out.printf("❌ Saldo: $%.2f | Necesitas: $%.2f%n",
                      e.getSaldoActual(), e.getCostoRequerido());
}
```

**Ejemplo 2: IngredientesInsuficientesException**
```java
public class IngredientesInsuficientesException extends ProduccionException {
    private Map<Product, Integer> ingredientesRequeridos;
    private Map<Product, Integer> ingredientesDisponibles;
    
    public IngredientesInsuficientesException(
            Map<Product, Integer> requeridos, 
            Map<Product, Integer> disponibles) {
        super(construirMensaje(requeridos, disponibles));
        this.ingredientesRequeridos = requeridos;
        this.ingredientesDisponibles = disponibles;
    }
    
    private static String construirMensaje(
            Map<Product, Integer> req, 
            Map<Product, Integer> disp) {
        StringBuilder sb = new StringBuilder("Ingredientes insuficientes:\n");
        
        for (Map.Entry<Product, Integer> entry : req.entrySet()) {
            Product prod = entry.getKey();
            int necesario = entry.getValue();
            int disponible = disp.getOrDefault(prod, 0);
            
            sb.append(String.format("  • %s: tienes %d, necesitas %d%n",
                                    prod, disponible, necesario));
        }
        
        return sb.toString();
    }
}
```

**Output:**
```
❌ Ingredientes insuficientes:
  • FOSFO: tienes 2, necesitas 5
  • PITA: tienes 1, necesitas 3
```

---

### 5.3 Clases Base Abstractas

**¿Por qué abstractas?**

```java
public abstract class TradingException extends RuntimeException {
    public TradingException(String message) {
        super(message);
    }
    
    public TradingException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

**Ventajas:**
1. **Catch por familia**: `catch (TradingException e)` captura todas las de trading
2. **Organización**: Agrupa excepciones relacionadas
3. **Polimorfismo**: Tratamiento uniforme si es necesario

---

## 6. CONCURRENCIA Y PROGRAMACIÓN ASÍNCRONA

### 6.1 TareaAutomatica (Herencia de Thread Management)

**Concepto:** Ejecutar código automáticamente cada X segundos en background.

**Clase base del SDK:**
```java
// Proporcionada por el SDK (no implementamos nosotros)
public abstract class TareaAutomatica {
    private Timer timer;
    private String taskId;
    
    public TareaAutomatica(String taskId) {
        this.taskId = taskId;
        this.timer = new Timer();
    }
    
    public void iniciar(long intervaloMs) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ejecutar();
            }
        }, 0, intervaloMs);
    }
    
    protected abstract void ejecutar();
}
```

**Nuestra implementación:**
```java
public class AutoProductor extends TareaAutomatica {
    private ClienteBolsa cliente;
    
    public AutoProductor(ClienteBolsa cliente) {
        super(new OrderIdGenerator("AUTO-").next());
        this.cliente = cliente;
    }
    
    @Override
    protected void ejecutar() {
        try {
            autoProducir();
        } catch (Exception e) {
            System.err.println("❌ Error en AutoProductor: " + e.getMessage());
        }
    }
    
    private void autoProducir() {
        EstadoCliente estado = cliente.getEstado();
        
        // Prioridad 1: Intentar producir GUACA (premium)
        Receta recetaGuaca = estado.getRecetas().get(Product.GUACA);
        if (RecetaValidator.puedeProducir(recetaGuaca, estado.getInventario())) {
            cliente.producir(Product.GUACA, true);
            return;
        }
        
        // Prioridad 2: Intentar producir SEBO (premium)
        Receta recetaSebo = estado.getRecetas().get(Product.SEBO);
        if (RecetaValidator.puedeProducir(recetaSebo, estado.getInventario())) {
            cliente.producir(Product.SEBO, true);
            return;
        }
        
        // Fallback: Producir PALTA-OIL (básico, sin ingredientes)
        cliente.producir(Product.PALTA_OIL, false);
    }
}
```

**Uso:**
```java
// En ClienteBolsa
private AutoProductor autoProductor;

public void iniciarAutoProductor() {
    if (autoProductor == null) {
        autoProductor = new AutoProductor(this);
    }
    autoProductor.iniciar(5000); // Cada 5 segundos
    System.out.println("🤖 Auto-productor iniciado (5s)");
}

public void pararAutoProductor() {
    if (autoProductor != null) {
        autoProductor.detener();
        System.out.println("⏹️ Auto-productor detenido");
    }
}
```

**Conceptos de concurrencia:**
1. **Thread-safety**: El Timer usa un thread separado
2. **Exception handling**: Capturamos excepciones para no matar el thread
3. **Resource management**: Debemos detener el timer al salir

---

### 6.2 Atomicidad en Operaciones

**Problema:** ¿Qué pasa si dos threads modifican el inventario al mismo tiempo?

**En nuestro caso:** No es un problema porque:
- El scanner (input del usuario) está en el main thread
- Los eventos del servidor llegan en su propio thread
- El AutoProductor tiene su propio thread

**Solución si fuera necesaria:**
```java
// Si necesitáramos thread-safety real:
private final Object inventarioLock = new Object();

public void actualizarInventario(Product producto, int cantidad) {
    synchronized(inventarioLock) {
        inventario.put(producto, 
                      inventario.getOrDefault(producto, 0) + cantidad);
    }
}
```

---

## 7. JAVA RECORDS Y FEATURES MODERNOS

### 7.1 Java Records (Java 14+)

**¿Qué es un Record?** Un tipo de datos inmutable compacto (reemplazo de POJOs).

**Implementación:**
```java
public record Configuration(
        String serverUrl,
        int port,
        String username,
        String password
) {
    // Constructor compacto con validación
    public Configuration {
        if (serverUrl == null || serverUrl.isEmpty()) {
            throw new ConfiguracionInvalidaException(
                "serverUrl no puede estar vacío");
        }
        if (port < 1 || port > 65535) {
            throw new ConfiguracionInvalidaException(
                "Puerto debe estar entre 1 y 65535");
        }
    }
}
```

**Lo que obtienes gratis:**
- Constructor: `new Configuration(url, port, user, pass)`
- Getters: `config.serverUrl()`, `config.port()`
- `equals()`, `hashCode()`, `toString()` automáticos
- Inmutabilidad: No hay setters

**Comparación con clase tradicional:**
```java
// ❌ ANTES (20+ líneas)
public class Configuration {
    private final String serverUrl;
    private final int port;
    
    public Configuration(String serverUrl, int port) {
        this.serverUrl = serverUrl;
        this.port = port;
    }
    
    public String getServerUrl() { return serverUrl; }
    public int getPort() { return port; }
    
    @Override
    public boolean equals(Object o) { /* ... */ }
    @Override
    public int hashCode() { /* ... */ }
    @Override
    public String toString() { /* ... */ }
}

// ✅ AHORA (1 línea)
public record Configuration(String serverUrl, int port) {}
```

---

### 7.2 Switch Expressions (Java 14+)

**Sintaxis moderna del switch:**

```java
// ❌ ANTES (switch statement)
String mensaje;
switch (tipo) {
    case "INFO":
        mensaje = "Información";
        break;
    case "ERROR":
        mensaje = "Error crítico";
        break;
    default:
        mensaje = "Desconocido";
}

// ✅ AHORA (switch expression)
String mensaje = switch (tipo) {
    case "INFO" -> "Información";
    case "ERROR" -> "Error crítico";
    default -> "Desconocido";
};
```

**En nuestro proyecto:**
```java
private void handleCommand(String command, String[] parts) {
    switch (command) {
        case "comprar" -> handleComprar(parts);
        case "vender" -> handleVender(parts);
        case "producir" -> handleProducir(parts);
        case "status" -> handleStatus();
        case "inventario" -> handleInventario();
        case "listener" -> handleListener();
        case "exit", "quit", "salir" -> ejecutando = false;
        default -> System.out.println("❌ Comando desconocido: " + command);
    }
}
```

**Ventajas:**
- No hay `break` (no se "cae" a otros cases)
- Más compacto y legible
- Puede devolver valores
- Múltiples casos en una línea: `case "a", "b" ->`

---

### 7.3 Text Blocks (Java 15+)

**Para strings multilinea:**

```java
// ❌ ANTES
String menu = "╔════════════════════════════════════════════════════════════╗\n" +
              "║                  📋 COMANDOS DISPONIBLES                   ║\n" +
              "╚════════════════════════════════════════════════════════════╝";

// ✅ AHORA
String menu = """
    ╔════════════════════════════════════════════════════════════╗
    ║                  📋 COMANDOS DISPONIBLES                   ║
    ╚════════════════════════════════════════════════════════════╝
    """;
```

---

### 7.4 Pattern Matching para instanceof (Java 16+)

```java
// ❌ ANTES
if (obj instanceof String) {
    String str = (String) obj;
    System.out.println(str.toUpperCase());
}

// ✅ AHORA
if (obj instanceof String str) {
    System.out.println(str.toUpperCase());
}
```

---

## 8. VALIDACIÓN CON YAVI

### 8.1 Librería de Validación Moderna

**¿Qué es Yavi?** Una librería japonesa para validaciones declarativas.

**Configuración (build.gradle.kts):**
```kotlin
dependencies {
    implementation("am.ik.yavi:yavi:0.13.0")
}
```

**Uso básico:**
```java
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;

public class ConfigValidator {
    private static final Validator<Configuration> validator = 
        ValidatorBuilder.<Configuration>of()
            .constraint(Configuration::serverUrl, "serverUrl",
                c -> c.notBlank()
                      .url()
                      .message("Debe ser una URL válida"))
            .constraint(Configuration::port, "port",
                c -> c.greaterThanOrEqual(1)
                      .lessThanOrEqual(65535)
                      .message("Puerto entre 1-65535"))
            .build();
    
    public static void validar(Configuration config) {
        ConstraintViolations violations = validator.validate(config);
        
        if (!violations.isValid()) {
            String errores = violations.violations().stream()
                .map(v -> v.name() + ": " + v.message())
                .collect(Collectors.joining("\n"));
            
            throw new ConfiguracionInvalidaException(errores);
        }
    }
}
```

**Ventajas sobre validación manual:**
- Declarativo vs imperativo
- Mensajes de error automáticos
- Fácil de extender
- Thread-safe

---

## 9. INTEGRACIÓN CON SDK EXTERNO

### 9.1 Dependency Injection Manual

**Problema:** El SDK viene como JAR externo.

**Solución en build.gradle.kts:**
```kotlin
repositories {
    mavenCentral()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/HellSoft-Col/stock-market")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation("tech.hellsoft.trading:websocket-client:1.2.3")
}
```

**Concepto de repositorio privado:**
- No está en Maven Central
- Requiere autenticación
- Variables de entorno para seguridad

---

### 9.2 Interface Segregation

**El SDK nos da interfaces, nosotros las implementamos:**

```java
// Interface del SDK (no modificamos)
public interface EventListener {
    void onLoginOk(LoginOKMessage msg);
    void onFill(FillMessage fill);
    void onTicker(TickerMessage ticker);
    void onOffer(OfferMessage offer);
    void onError(ErrorMessage error);
}

// Nuestra implementación
public class ClienteBolsa implements EventListener {
    // Implementamos todos los métodos
}
```

**Principio SOLID:** Interface Segregation Principle
- El SDK define el contrato
- Nosotros implementamos la lógica
- Desacoplamiento total

---

## 10. GESTIÓN DE ESTADO COMPLEJO

### 10.1 Maps Anidados

**Estructura:**
```java
// Inventario: Producto -> Cantidad
private Map<Product, Integer> inventario;

// Precios: Producto -> Precio actual
private Map<Product, Double> preciosActuales;

// Recetas: Producto -> Receta completa
private Map<Product, Receta> recetas;

// Ofertas: OfferId -> Oferta completa
private Map<String, OfferMessage> ofertasPendientes;
```

**Operaciones comunes:**
```java
// Actualizar inventario de forma segura
public void actualizarInventario(Product producto, int cantidad) {
    inventario.put(producto, 
                   inventario.getOrDefault(producto, 0) + cantidad);
}

// Calcular valor total del inventario
public double calcularValorInventario() {
    return inventario.entrySet().stream()
        .mapToDouble(entry -> {
            Product prod = entry.getKey();
            int cant = entry.getValue();
            double precio = preciosActuales.getOrDefault(prod, 0.0);
            return cant * precio;
        })
        .sum();
}
```

---

### 10.2 Streams y Lambdas

**Ejemplo real del proyecto:**

```java
// Calcular P&L (Profit & Loss)
public double calcularPL() {
    double valorInventario = inventario.entrySet().stream()
        .mapToDouble(entry -> {
            Product prod = entry.getKey();
            int cantidad = entry.getValue();
            Double precio = preciosActuales.get(prod);
            return (precio != null && precio > 0) ? cantidad * precio : 0.0;
        })
        .sum();
    
    double patrimonioNeto = saldo + valorInventario;
    
    if (saldoInicial == 0) return 0.0;
    
    return ((patrimonioNeto - saldoInicial) / saldoInicial) * 100;
}
```

**Conceptos de Streams:**
1. **`.stream()`**: Convierte colección a stream
2. **`.mapToDouble()`**: Transforma cada elemento a double
3. **`.sum()`**: Operación terminal que suma todo
4. **Lambda**: `entry -> { ... }` función anónima

---

### 10.3 Lombok para Reducir Boilerplate

**Problema:** Getters/setters son verbose.

**Solución:**
```java
import lombok.Getter;
import lombok.Setter;

public class EstadoCliente implements Serializable {
    @Getter @Setter
    private double saldo;
    
    @Getter @Setter
    private Map<Product, Integer> inventario;
    
    // No necesitamos escribir:
    // public double getSaldo() { return saldo; }
    // public void setSaldo(double saldo) { this.saldo = saldo; }
}
```

**Anotaciones Lombok usadas:**
- `@Getter`: Genera getters
- `@Setter`: Genera setters
- `@Data`: Genera todo (getters, setters, equals, hashCode, toString)
- `@Builder`: Genera builder pattern

---

## 11. CONCEPTOS ADICIONALES IMPORTANTES

### 11.1 Enums con Comportamiento

**Uso de enums:**
```java
public enum Product {
    PALTA_OIL("PALTA-OIL"),
    FOSFO("FOSFO"),
    PITA("PITA"),
    NUCREM("NUCREM"),
    GUACA("GUACA"),
    SEBO("SEBO");
    
    private final String displayName;
    
    Product(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
```

---

### 11.2 Builder Pattern (OrderIdGenerator)

**Generación de IDs únicos:**
```java
public class OrderIdGenerator {
    private final String prefix;
    private final AtomicInteger counter;
    
    public OrderIdGenerator(String prefix) {
        this.prefix = prefix;
        this.counter = new AtomicInteger(0);
    }
    
    public String next() {
        return prefix + counter.incrementAndGet();
    }
}
```

**Uso:**
```java
OrderIdGenerator gen = new OrderIdGenerator("ORDER-");
String id1 = gen.next(); // "ORDER-1"
String id2 = gen.next(); // "ORDER-2"
```

**Thread-safety:** `AtomicInteger` es thread-safe (no necesita synchronized).

---

### 11.3 Try-with-Resources (Auto-closeable)

**Gestión automática de recursos:**
```java
// ✅ BIEN (cierra automáticamente)
try (ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream(ruta))) {
    out.writeObject(estado);
} // El stream se cierra automáticamente aquí

// ❌ MAL (memory leak si hay excepción)
ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ruta));
out.writeObject(estado);
out.close(); // ¿Qué pasa si writeObject lanza excepción?
```

---

## 12. PREGUNTAS FRECUENTES EN SUSTENTACIÓN

### P1: ¿Por qué usas RuntimeException en lugar de Exception?

**R:** Las excepciones de negocio (validaciones) no deberían forzar try-catch. Son "unchecked" porque:
- El código queda más limpio
- Las validaciones son parte del flujo normal
- Podemos manejarlas en el nivel que queramos

---

### P2: ¿Por qué no hay `else` en el código?

**R:** Seguimos el principio "no else" porque:
- **Guard clauses**: Validamos y retornamos temprano
- **Switch expressions**: Reemplazan if-else-if
- **Código más lineal**: Menos indentación = más legible
- **Menor complejidad ciclomática**

---

### P3: ¿Cómo funciona la recursión del CalculadoraProduccion?

**R:** Es una recursión tail-recursive que:
1. Caso base: `nivel > maxDepth` → return 0
2. Calcula energía del nivel actual
3. Suma contribución del nivel actual + recursión(nivel+1)
4. Se desenrolla sumando todas las contribuciones

---

### P4: ¿Por qué Serializable y no JSON?

**R:** Serialización binaria porque:
- **Más eficiente**: Menos bytes que JSON
- **Tipo seguro**: No hay parsing errors
- **Preserva tipos**: Maps, Lists, objetos complejos
- **Más rápido**: No hay overhead de parsing

---

### P5: ¿Cómo garantizas thread-safety?

**R:** En nuestro caso no es crítico porque:
- Input del usuario: main thread
- Eventos del servidor: thread del SDK
- AutoProductor: su propio thread
- No hay conflictos reales

Si fuera necesario: `synchronized`, `AtomicInteger`, `ConcurrentHashMap`

---

### P6: ¿Qué es el patrón Observer?

**R:** Un patrón donde:
- **Subject** (servidor): Notifica cambios
- **Observer** (ClienteBolsa): Reacciona a notificaciones
- **Desacoplamiento**: El servidor no sabe quién escucha
- **Push vs Pull**: El servidor empuja datos, no esperamos a pedirlos

---

### P7: ¿Por qué usas Lombok?

**R:** Para reducir boilerplate:
- `@Getter/@Setter`: Genera getters/setters automáticos
- Menos líneas de código
- Más legible
- Menos propenso a errores

---

### P8: ¿Qué ventaja tienen los Records?

**R:** Records son:
- **Inmutables**: No se pueden modificar después de crear
- **Compactos**: 1 línea vs 20+ líneas
- **Type-safe**: Constructor con validación
- **Auto-generado**: equals, hashCode, toString gratis

---

## 13. CHECKLIST DE SUSTENTACIÓN

### ✅ Conceptos que DEBES dominar:

- [ ] **Patrón Observer**: Cómo funciona el listener
- [ ] **Recursión**: Explicar CalculadoraProduccion
- [ ] **Excepciones**: Jerarquía y por qué RuntimeException
- [ ] **Serialización**: Por qué binaria y no JSON
- [ ] **No else**: Por qué y cómo lo implementamos
- [ ] **Streams**: Cómo funciona calcularPL()
- [ ] **Thread-safety**: Por qué no es problema aquí
- [ ] **Records**: Ventajas sobre clases tradicionales
- [ ] **Switch expressions**: Diferencia con switch tradicional
- [ ] **Try-with-resources**: Por qué evita memory leaks

### ✅ Preguntas técnicas comunes:

1. "¿Cómo funciona el listener en tiempo real?"
2. "Explica la recursión del cálculo de producción"
3. "¿Por qué RuntimeException y no Exception?"
4. "¿Cómo persistes el estado? ¿Por qué no JSON?"
5. "¿Qué patrones de diseño usaste?"
6. "¿Cómo manejas la concurrencia?"
7. "¿Qué features modernos de Java usas?"
8. "¿Por qué no usas `else`?"

### ✅ Demostraciones prácticas:

- [ ] Ejecutar el bot y mostrar el listener
- [ ] Guardar/cargar un snapshot
- [ ] Mostrar auto-producción
- [ ] Provocar una excepción (ej: saldo insuficiente)
- [ ] Mostrar el cálculo de P&L

---

## 14. CONCLUSIÓN

Este proyecto implementa conceptos avanzados de:
- **Arquitectura**: Observer, State Management
- **Programación funcional**: Streams, lambdas
- **Concurrencia**: Threads, tasks automáticas
- **Persistencia**: Serialización binaria
- **Diseño**: Jerarquía de excepciones, no else
- **Java moderno**: Records, switch expressions, text blocks
- **Integración**: SDK externo, repositorios privados

**Nivel de complejidad:** Intermedio-Avanzado (más allá de Programación I/II)

---

**Última actualización:** 2025-01-27
**Autor:** Equipo Spacial Trading Bot
**Versión:** 1.0

