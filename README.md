# 🚀 Guía de Desarrollo - Trading Bot Client
# Revision de commit
> **🆕 ¿Nuevo en el proyecto?** Comienza con **[INDICE.md](documentacion/INDICE.md)** para saber por dónde empezar.
>
> **📘 ¿Es tu primer día?** Ve directo a **[TUTORIAL_PRIMER_DIA.md](documentacion/TUTORIAL_PRIMER_DIA.md)** - configuración paso a paso en 2-3 horas.

---

## 📋 Tabla de Contenidos
1. [Requisitos Mínimos](#requisitos-mínimos)
2. [Configuración Inicial](#configuración-inicial)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Arquitectura y Componentes](#arquitectura-y-componentes)
5. [Cómo Probar que Funciona](#cómo-probar-que-funciona)
6. [Desarrollo de Lógica de Negocio](#desarrollo-de-lógica-de-negocio)
7. [Estándares de Código](#estándares-de-código)
8. [Comandos Útiles](#comandos-útiles)
9. [Solución de Problemas](#solución-de-problemas)

---

## 🎯 Requisitos Mínimos

### Software Necesario
1. **Java Development Kit (JDK) 25**
   - Descargar de: https://www.oracle.com/java/technologies/downloads/
   - Verificar instalación: `java -version`
   - Debe mostrar versión 25.x.x

2. **Git**
   - Descargar de: https://git-scm.com/
   - Verificar: `git --version`

3. **IDE Recomendado**
   - IntelliJ IDEA Community/Ultimate (recomendado)
   - Eclipse con soporte Java 25
   - VS Code con extensión Java

4. **Cuenta de GitHub**
   - Necesaria para acceder al SDK privado
   - Crear token de acceso personal (PAT)

### Conocimientos Previos Recomendados
- Java básico (clases, interfaces, excepciones)
- Conceptos de programación orientada a objetos
- Uso básico de Git
- JSON básico

---

## ⚙️ Configuración Inicial

### Paso 1: Clonar el Repositorio
```bash
git clone https://github.com/tu-usuario/Proyecto-Avanzada-Java.git
cd Proyecto-Avanzada-Java
```

### Paso 2: Configurar Credenciales de GitHub
El proyecto usa un SDK privado de GitHub Packages. Necesitas configurar tus credenciales:

1. **Crear archivo `gradle.properties`** en la raíz del proyecto (copia del sample):
```bash
copy gradle.properties.sample gradle.properties
```

2. **Editar `gradle.properties`** con tus credenciales:
```properties
gpr.user=TU_USUARIO_GITHUB
gpr.token=TU_TOKEN_GITHUB

org.gradle.daemon=true
org.gradle.caching=true
org.gradle.configuration-cache=true
```

3. **Crear GitHub Personal Access Token (PAT)**:
   - Ve a GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
   - Click "Generate new token (classic)"
   - Selecciona el scope: `read:packages`
   - Copia el token generado y pégalo en `gradle.properties`

### Paso 3: Configurar el Archivo de Configuración
1. **Crear archivo de configuración** (copia del sample):
```bash
copy src\main\resources\config.sample.json src\main\resources\config.json
```

2. **Editar `src\main\resources\config.json`** con tus credenciales del servidor:
```json
{
  "apiKey": "TU_API_KEY_DEL_SERVIDOR",
  "team": "Nombre de Tu Equipo",
  "host": "wss://trading.hellsoft.tech/ws"
}
```

⚠️ **IMPORTANTE**: Los archivos `gradle.properties` y `config.json` están en `.gitignore`. 
**NUNCA los subas a Git** porque contienen información sensible.

### Paso 4: Verificar la Instalación
```bash
# En Windows
gradlew.bat build

# Debería compilar sin errores
```

---

## 📁 Estructura del Proyecto

```
Proyecto-Avanzada-Java/
├── src/main/java/tech/hellsoft/trading/
│   ├── Main.java                          # Punto de entrada de la aplicación
│   ├── config/
│   │   └── Configuration.java             # Record para configuración
│   ├── exception/
│   │   ├── ConfiguracionInvalidaException.java
│   │   └── TradingException.java          # Excepciones del dominio
│   ├── model/
│   │   ├── Recipe.java                    # Modelo de recetas de productos
│   │   └── Role.java                      # Modelo de roles de jugador
│   ├── service/
│   │   ├── TradingService.java            # Interface principal de trading
│   │   ├── UIService.java                 # Interface para UI/consola
│   │   └── impl/
│   │       ├── SDKTradingService.java     # Implementación con SDK
│   │       └── ConsoleUIService.java      # Implementación de consola
│   └── util/
│       ├── ConfigLoader.java              # Utilidad para cargar config
│       └── TradingUtils.java              # Utilidades generales
├── src/main/resources/
│   ├── config.json                        # TU configuración (no subir a Git)
│   └── config.sample.json                 # Plantilla de configuración
├── build.gradle.kts                       # Configuración de Gradle
├── gradle.properties                      # TUS credenciales (no subir a Git)
└── gradle.properties.sample               # Plantilla de credenciales
```

---

## 🏗️ Arquitectura y Componentes

### Componentes Principales

#### 1. **Main.java** - Punto de Entrada
- Inicializa todos los servicios
- Carga la configuración
- Maneja el ciclo de vida de la aplicación
- Gestiona el shutdown gracefully

#### 2. **TradingService** - Servicio Principal
**Interface**: Define el contrato para operaciones de trading
```java
public interface TradingService {
    void start(Configuration config);
    void stop();
    boolean isRunning();
}
```

**SDKTradingService**: Implementación que:
- Se conecta al servidor WebSocket
- Maneja eventos del servidor (login, órdenes, tickers, etc.)
- **AQUÍ es donde ustedes agregarán la lógica de trading**

#### 3. **UIService** - Servicio de Interfaz
- Imprime mensajes en la consola con colores
- Muestra estado del bot
- Útil para debugging

#### 4. **Configuration** - Configuración
Record inmutable que contiene:
- `apiKey`: Tu clave de API del servidor
- `team`: Nombre de tu equipo
- `host`: URL del servidor WebSocket

#### 5. **Modelos de Dominio**
- **Recipe**: Representa recetas de productos (qué ingredientes se necesitan)
- **Role**: Representa el rol del jugador (energía, niveles, etc.)

---

## ✅ Cómo Probar que Funciona

### Prueba 1: Compilación Exitosa
```bash
gradlew.bat clean build
```

**Resultado esperado:**
```
BUILD SUCCESSFUL in Xs
```

### Prueba 2: Ejecutar la Aplicación
```bash
gradlew.bat run
```

**Resultado esperado:**
```
╔══════════════════════════════════════════════════════════════╗
║                                                              ║
║  🚀 SPACIAL TRADING BOT CLIENT - Java 25 Edition 🚀         ║
║                                                              ║
║  🥑 Bolsa Interestelar de Aguacates Andorianos              ║
║  Ready for trading operations...                            ║
║                                                              ║
╚════════════════════════════════════════════════════════════╝

ℹ️  Configuration loaded successfully:
   Team: Nombre de Tu Equipo
   Host: wss://trading.hellsoft.tech/ws
   API Key: sk_t***xxxx

🔌 Connecting to trading server...
⏳ Waiting for login response...
✅ Login successful! Ready for trading operations.
✅ Login OK received!
   Team: tu-equipo
   Species: HUMANO
   Initial Balance: 1000000.0
   Current Balance: 1000000.0

ℹ️  Press Ctrl+C to shutdown gracefully...
```

### Prueba 3: Verificar Linting (Calidad de Código)
```bash
gradlew.bat checkstyleMain pmdMain
```

**Resultado esperado:** Sin errores críticos

### Prueba 4: Ejecutar Tests (cuando existan)
```bash
gradlew.bat test
```

---

## 💼 Desarrollo de Lógica de Negocio

### ¿Dónde Agregar Tu Código?

#### Opción 1: Modificar SDKTradingService
El archivo `SDKTradingService.java` ya tiene un `EventListener` interno que recibe eventos del servidor.

**Eventos que recibes del servidor:**
- `onLoginOk()` - Cuando te conectas exitosamente
- `onTicker()` - Actualización de precios de mercado
- `onOffer()` - Ofertas de compra/venta
- `onFill()` - Cuando se ejecuta una orden
- `onInventoryUpdate()` - Cambios en tu inventario
- `onBalanceUpdate()` - Cambios en tu saldo
- `onOrderAck()` - Confirmación de órdenes
- `onError()` - Errores del servidor

**Ejemplo de cómo agregar lógica:**

```java
@Override
public void onTicker(TickerMessage ticker) {
    listenerUiService.printStatus("📊",
        "Ticker update: " + ticker.getProduct() + 
        " Bid:" + ticker.getBestBid() + 
        " Ask:" + ticker.getBestAsk());
    
    // 🔥 AGREGA TU LÓGICA AQUÍ
    analizarOportunidadDeCompra(ticker);
}

private void analizarOportunidadDeCompra(TickerMessage ticker) {
    // Tu estrategia de trading aquí
    if (ticker.getBestAsk() < precioObjetivo) {
        // Enviar orden de compra
        connector.enviarOrden(...);
    }
}
```

#### Opción 2: Crear Nuevos Servicios
Puedes crear nuevos servicios especializados:

**Ejemplo: StrategyService**
```java
// src/main/java/tech/hellsoft/trading/service/StrategyService.java
public interface StrategyService {
    Decision analizarMercado(TickerMessage ticker);
    boolean deberiaComprar(String producto, double precio);
    boolean deberiaVender(String producto, double precio);
}
```

**Ejemplo: InventoryManager**
```java
// src/main/java/tech/hellsoft/trading/service/InventoryManager.java
public class InventoryManager {
    private Map<String, Integer> inventarioActual = new HashMap<>();
    
    public void actualizar(InventoryUpdateMessage update) {
        // Mantener registro de tu inventario
    }
    
    public boolean tieneStock(String producto, int cantidad) {
        return inventarioActual.getOrDefault(producto, 0) >= cantidad;
    }
}
```

### Casos de Uso Típicos

#### Caso 1: Market Maker Simple
```java
// Comprar barato, vender caro con un spread
@Override
public void onTicker(TickerMessage ticker) {
    double spread = 0.05; // 5% de ganancia
    
    if (ticker.getBestAsk() > 0) {
        double precioCompra = ticker.getBestAsk();
        double precioVenta = precioCompra * (1 + spread);
        
        // Comprar al mejor precio de venta
        // Vender a precio + spread
    }
}
```

#### Caso 2: Arbitraje de Productos
```java
// Comprar productos básicos, crear productos complejos
public void intentarCrafteo(Recipe receta) {
    // 1. Verificar que tienes todos los ingredientes
    // 2. Calcular costo total de ingredientes
    // 3. Comparar con precio de venta del producto final
    // 4. Si es rentable, realizar el crafteo
}
```

#### Caso 3: Gestión de Riesgo
```java
public class RiskManager {
    private double maxExposurePorProducto = 0.1; // 10% del capital
    
    public boolean puedeComprar(String producto, int cantidad, double precio) {
        double exposicion = cantidad * precio;
        double capitalTotal = obtenerCapitalTotal();
        
        return exposicion <= (capitalTotal * maxExposurePorProducto);
    }
}
```

---

## 📏 Estándares de Código

### Regla de Oro: **NO usar `else`**

❌ **MAL:**
```java
if (precio > 100) {
    comprar();
} else {
    vender();
}
```

✅ **BIEN:**
```java
if (precio > 100) {
    comprar();
    return;
}
vender();
```

### Convenciones de Nombres

```java
// Clases: UpperCamelCase
public class OrdenManager { }

// Métodos y variables: lowerCamelCase
private double precioActual;
public void calcularGanancia() { }

// Constantes: UPPER_SNAKE_CASE
private static final int MAX_INTENTOS = 3;

// Packages: lowercase
package tech.hellsoft.trading.strategy;
```

### Guard Clauses (Cláusulas de Guarda)

✅ Valida parámetros al inicio:
```java
public void procesarOrden(Orden orden) {
    if (orden == null) {
        throw new IllegalArgumentException("Orden no puede ser null");
    }
    if (orden.getCantidad() <= 0) {
        throw new IllegalArgumentException("Cantidad debe ser positiva");
    }
    if (!tieneCapital(orden)) {
        throw new SaldoInsuficienteException();
    }
    
    // Lógica principal aquí
    ejecutarOrden(orden);
}
```

### Uso de Records (Java 25)

```java
// Para datos inmutables
public record OrdenCompra(
    String producto,
    int cantidad,
    double precio,
    LocalDateTime timestamp
) {
    // Validación en el constructor compacto
    public OrdenCompra {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }
    }
}
```

### Manejo de Excepciones

```java
// Crea excepciones específicas de tu dominio
public class CapitalInsuficienteException extends TradingException {
    public CapitalInsuficienteException(double requerido, double disponible) {
        super(String.format("Capital insuficiente. Requerido: %.2f, Disponible: %.2f",
            requerido, disponible));
    }
}
```

---

## 🛠️ Comandos Útiles

### Compilación y Build
```bash
# Limpiar y compilar todo
gradlew.bat clean build

# Solo compilar (sin tests ni linting)
gradlew.bat compileJava

# Ver dependencias
gradlew.bat dependencies
```

### Ejecución
```bash
# Ejecutar la aplicación
gradlew.bat run

# Ejecutar con argumentos (si agregas soporte)
gradlew.bat run --args="--config custom.json"
```

### Calidad de Código
```bash
# Checkstyle (estilo de código)
gradlew.bat checkstyleMain

# PMD (detección de bugs)
gradlew.bat pmdMain

# Formatear código automáticamente
gradlew.bat spotlessApply

# Ver reportes de calidad
# Abrir: build/reports/checkstyle/main.html
# Abrir: build/reports/pmd/main.html
```

### Testing
```bash
# Ejecutar todos los tests
gradlew.bat test

# Ejecutar tests específicos
gradlew.bat test --tests "ConfigLoaderTest"

# Ver reporte de tests
# Abrir: build/reports/tests/test/index.html
```

### Empaquetado
```bash
# Crear JAR ejecutable
gradlew.bat jar

# Crear distribución completa
gradlew.bat distZip

# El JAR estará en: build/libs/
# La distribución en: build/distributions/
```

---

## 🐛 Solución de Problemas

### Error: "Configuration file not found"
**Causa:** No existe `src/main/resources/config.json`

**Solución:**
```bash
copy src\main\resources\config.sample.json src\main\resources\config.json
```
Luego edita `config.json` con tus credenciales.

### Error: "401 Unauthorized" al compilar
**Causa:** Credenciales de GitHub incorrectas o faltantes

**Solución:**
1. Verifica que existe `gradle.properties`
2. Verifica que `gpr.user` es tu usuario de GitHub
3. Verifica que `gpr.token` es un PAT válido con permiso `read:packages`
4. Regenera el token si es necesario

### Error: "Login failed"
**Causa:** API key incorrecta o servidor no disponible

**Solución:**
1. Verifica que `config.json` tiene el `apiKey` correcto
2. Verifica que el servidor está corriendo
3. Verifica la URL del `host` en `config.json`

### La aplicación se cierra inmediatamente
**Causa:** Error en la lógica del método `isRunning()`

**Solución:**
- Revisa los logs en la consola
- Verifica que `tradingService.start()` se ejecuta correctamente
- Agrega más logging en `SDKTradingService`

### Errores de Checkstyle/PMD
**Causa:** Código no cumple con los estándares

**Solución:**
```bash
# Ver errores específicos
gradlew.bat checkstyleMain

# Formatear automáticamente (arregla muchos errores)
gradlew.bat spotlessApply

# Ver reporte detallado
# Abrir: build/reports/checkstyle/main.html
```

### OutOfMemoryError
**Causa:** Gradle necesita más memoria

**Solución:** Edita `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m
```

### IDE no reconoce clases del SDK
**Causa:** Dependencies no sincronizadas

**Solución en IntelliJ:**
1. Click derecho en el proyecto → "Reload Gradle Project"
2. File → Invalidate Caches → Invalidate and Restart

---

## 🎓 Flujo de Trabajo Recomendado

### Para Empezar a Desarrollar

1. **Crear una rama para tu feature**
```bash
git checkout -b feature/mi-estrategia-trading
```

2. **Escribir el código**
   - Implementa tu lógica en `SDKTradingService` o crea nuevos servicios
   - Sigue los estándares de código (no `else`, guard clauses, etc.)

3. **Probar localmente**
```bash
gradlew.bat build
gradlew.bat run
```

4. **Verificar calidad de código**
```bash
gradlew.bat checkstyleMain pmdMain
gradlew.bat spotlessApply  # Si hay errores de formato
```

5. **Commit y Push**
```bash
git add .
git commit -m "feat: implementar estrategia de arbitraje"
git push origin feature/mi-estrategia-trading
```

6. **Crear Pull Request en GitHub**

### Trabajo en Equipo

- **Comunicación:** Coordinen quién trabaja en qué para evitar conflictos
- **Code Reviews:** Revisen el código de otros antes de mergear
- **Testing:** Prueben los cambios de otros en sus máquinas
- **Documentación:** Comenten código complejo
- **Git:** Hagan commits pequeños y frecuentes con mensajes claros

---

## 📚 Recursos Adicionales

### Documentación del SDK
- Ver `ConectorBolsa` y sus métodos
- Ver DTOs en `tech.hellsoft.trading.dto.server.*`
- Ver `EventListener` y todos los eventos disponibles

### Java 25 Features
- Records
- Pattern Matching
- Switch Expressions
- Text Blocks

### Aprende Más
- **Clean Code:** Libro de Robert C. Martin
- **Effective Java:** Libro de Joshua Bloch
- **Refactoring:** Libro de Martin Fowler

---

## 🎯 Checklist de Inicio Rápido

- [ ] Java 25 instalado y verificado
- [ ] Proyecto clonado
- [ ] `gradle.properties` creado con tus credenciales de GitHub
- [ ] `config.json` creado con tu API key
- [ ] `gradlew.bat build` ejecuta sin errores
- [ ] `gradlew.bat run` se conecta al servidor y muestra "Login successful"
- [ ] IDE configurado (IntelliJ recomendado)
- [ ] Leído AGENTS.md para entender principios de código
- [ ] Primer commit realizado

---

## 💡 Tips Finales

1. **Empieza Simple:** No intentes implementar todo a la vez
2. **Prueba Frecuentemente:** Ejecuta la app después de cada cambio
3. **Lee los Logs:** La consola te dice exactamente qué está pasando
4. **Usa el UIService:** Imprime información para debug
5. **Pregunta:** Si algo no funciona, pide ayuda al equipo
6. **Git es tu amigo:** Haz commits frecuentes para poder volver atrás
7. **Documenta:** Comenta el "por qué", no el "qué"

---

**¡Éxito con el desarrollo! 🚀🥑**

