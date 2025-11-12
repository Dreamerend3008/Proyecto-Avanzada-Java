# 🚀 Spacial Trading Bot Base

Cliente CLI para la Bolsa Interestelar de Aguacates Andorianos - Proyecto base para estudiantes.

> **🆕 ¿Nuevo en el proyecto?** Comienza con **[documentacion/INDICE.md](documentacion/INDICE.md)** para ver todos los recursos disponibles.
>
> **📘 ¿Es tu primer día?** Ve directo a **[documentacion/TUTORIAL_PRIMER_DIA.md](documentacion/TUTORIAL_PRIMER_DIA.md)** - configuración paso a paso en 2-3 horas.
>
> **💡 ¿Necesitas ejemplos de código?** Consulta **[documentacion/DESARROLLO_EJEMPLOS.md](documentacion/DESARROLLO_EJEMPLOS.md)** con estrategias completas.
>
> **🎯 ¿Quieres entender el proyecto completo?** Lee **[GUIA.md](GUIA.md)** con conceptos de trading y explicación del SDK.

## 📋 Tabla de Contenidos

- [Requisitos Previos](#requisitos-previos)
- [Configuración Inicial](#configuración-inicial)
- [Autenticación con GitHub Packages](#autenticación-con-github-packages)
- [Configuración de IntelliJ IDEA](#configuración-de-intellij-idea)
- [Compilación y Ejecución](#compilación-y-ejecución)
- [Configuración del Bot](#configuración-del-bot)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Herramientas de Calidad de Código](#herramientas-de-calidad-de-código)

---

## 🔧 Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

1. **Java 25** (JDK 25)
   - Descarga desde: https://jdk.java.net/25/
   - Verifica la instalación: `java -version`

2. **IntelliJ IDEA** (Community o Ultimate)
   - Descarga desde: https://www.jetbrains.com/idea/download/

3. **Git**
   - Descarga desde: https://git-scm.com/downloads
   - Verifica la instalación: `git --version`

4. **Cuenta de GitHub**
   - Necesaria para acceder al repositorio privado y al SDK

---

## ⚙️ Configuración Inicial

### 1. Clonar el Repositorio

Si el repositorio es privado, necesitarás permisos de acceso. Contacta al instructor para ser agregado al repositorio.

```bash
# Clonar usando HTTPS (te pedirá credenciales)
git clone https://github.com/HellSoft-Col/stock-market.git

# O usando SSH (requiere configurar llaves SSH)
git clone git@github.com:HellSoft-Col/stock-market.git

# Navegar al directorio del proyecto base
cd stock-market/sdk/java/spacial-trading-bot-base
```

### 2. Autenticación con GitHub Packages

El proyecto utiliza el SDK `websocket-client` que está alojado en GitHub Packages. Necesitas autenticarte para descargarlo.

#### 2.1 Generar un Personal Access Token (PAT)

1. Ve a GitHub → **Settings** → **Developer settings** → **Personal access tokens** → **Tokens (classic)**
2. Haz clic en **"Generate new token (classic)"**
3. Dale un nombre descriptivo (ej: "Trading Bot SDK Access")
4. Selecciona los siguientes scopes:
   - ✅ `read:packages` (obligatorio)
   - ✅ `repo` (si el repositorio es privado)
5. Haz clic en **"Generate token"**
6. **¡IMPORTANTE!** Copia el token inmediatamente (solo se muestra una vez)

#### 2.2 Configurar las Credenciales

Crea el archivo `gradle.properties` en la raíz del proyecto:

```bash
cp gradle.properties.sample gradle.properties
```

Edita `gradle.properties` y reemplaza los valores:

```properties
# GitHub Packages Authentication
gpr.user=TU_USUARIO_GITHUB
gpr.token=ghp_tu_token_aqui

# Gradle optimizations
org.gradle.daemon=true
org.gradle.caching=true
org.gradle.configuration-cache=true
```

**⚠️ IMPORTANTE:** El archivo `gradle.properties` está en `.gitignore` y **NO debe subirse a Git** porque contiene información sensible.

---

## 💻 Configuración de IntelliJ IDEA

### 1. Importar el Proyecto

1. Abre IntelliJ IDEA
2. Selecciona **"Open"** (no "New Project")
3. Navega hasta el directorio `spacial-trading-bot-base`
4. Selecciona el archivo `build.gradle.kts`
5. En el diálogo, selecciona **"Open as Project"**
6. IntelliJ detectará automáticamente que es un proyecto Gradle

### 2. Configurar el JDK 25

1. Ve a **File** → **Project Structure** (o `Cmd+;` en Mac, `Ctrl+Alt+Shift+S` en Windows/Linux)
2. En **"Project"**:
   - **SDK:** Selecciona o agrega Java 25
   - **Language level:** 25 (Preview)
3. Haz clic en **"OK"**

### 3. Sincronizar Gradle

IntelliJ sincronizará automáticamente las dependencias. Si no lo hace:

1. Abre el panel de **Gradle** (lado derecho de la ventana)
2. Haz clic en el ícono de **"Reload All Gradle Projects"** (🔄)

Si obtienes un error de autenticación:
- Verifica que `gradle.properties` exista y tenga las credenciales correctas
- Verifica que tu token de GitHub tenga el scope `read:packages`

### 4. Configurar Lombok (opcional)

El proyecto usa Lombok para reducir código repetitivo:

1. Ve a **File** → **Settings** → **Plugins**
2. Busca "Lombok" e instala el plugin
3. Reinicia IntelliJ
4. Ve a **Settings** → **Build, Execution, Deployment** → **Compiler** → **Annotation Processors**
5. Marca **"Enable annotation processing"**

### 5. Importar Configuración de Formato

El proyecto incluye configuración de formato de código:

1. Ve a **File** → **Settings** → **Editor** → **Code Style** → **Java**
2. Haz clic en el ícono de engranaje ⚙️ → **Import Scheme** → **Eclipse XML Profile**
3. Selecciona el archivo `config/eclipse-format.xml`
4. Haz clic en **"OK"**

---

## 🏗️ Compilación y Ejecución

### Usando IntelliJ IDEA

#### Compilar el Proyecto

1. Abre el panel de **Gradle** (lado derecho)
2. Navega a: **spacial-trading-bot-base** → **Tasks** → **build**
3. Doble clic en **"build"**

O desde el terminal integrado:
```bash
./gradlew build
```

#### Ejecutar el Programa

1. Abre la clase `tech.hellsoft.trading.Main`
2. Haz clic derecho en el archivo o en el método `main()`
3. Selecciona **"Run 'Main.main()'"**

O desde el terminal:
```bash
./gradlew run
```

### Usando la Terminal (Gradle)

```bash
# Compilar el proyecto
./gradlew build

# Compilar sin ejecutar tests
./gradlew build -x test

# Ejecutar el programa
./gradlew run

# Limpiar y compilar
./gradlew clean build

# Ejecutar tests
./gradlew test

# Ver todas las tareas disponibles
./gradlew tasks
```

---

## 📖 Entendiendo el Código de Ejemplo

El archivo `Main.java` contiene un ejemplo **simple y minimal** que muestra cómo conectarse al servidor de trading. Es un punto de partida para que implementes tu propia lógica.

### Estructura del Ejemplo

```java
public static void main(String[] args) {
    // 1️⃣ Cargar configuración (apiKey, team, host)
    Configuration config = ConfigLoader.load("src/main/resources/config.json");
    
    // 2️⃣ Crear conector y tu bot
    ConectorBolsa connector = new ConectorBolsa();
    MyTradingBot bot = new MyTradingBot();
    connector.addListener(bot);
    
    // 3️⃣ Conectar al servidor
    connector.conectar(config.host(), config.apiKey());
    
    // 4️⃣ Mantener el programa corriendo
    Thread.currentThread().join();
}
```

### Clase MyTradingBot (Tu Implementación)

El ejemplo incluye una clase interna `MyTradingBot` que implementa `EventListener`. Aquí es donde **tú implementarás tu estrategia de trading**:

#### Eventos Principales que Debes Manejar:
| `onOffer()` | Recibiste una oferta | Decidir si aceptar/rechazar la oferta |
| `onOrderAck()` | Orden confirmada | Registrar que el servidor recibió tu orden |

| `onLogout()` | Desconexión | Cleanup y guardar estado si es necesario |
| `onRole()` | Información de rol | Guardar capacidades de tu especie |
| `onRecipe()` | Receta de producción | Guardar receta para producción futura |
| Evento | Cuándo se Dispara | Qué Hacer |
|--------|-------------------|-----------|
| `onLoginOk()` | Conexión exitosa | Inicializar tu estado (balance, inventario inicial) |
| `onTicker()` | Actualización de precios | Decidir si comprar/vender basado en precios |
| `onFill()` | Orden ejecutada | Actualizar tu inventario y balance local |
| `onBalanceUpdate()` | Cambio en balance | Actualizar tu registro de dinero disponible |
| `onInventoryUpdate()` | Cambio en inventario | Actualizar tu registro de productos |
| `onError()` | Error del servidor | Manejar errores y reintentar si es necesario |

### Patrón "No Else" (Guard Clauses)

Nota cómo cada método usa **guard clauses** en lugar de `if-else`:

```java
@Override
public void onTicker(TickerMessage ticker) {
    // ✅ Guard clause: salir temprano si no hay datos
    if (ticker == null) {
        return;
    }
    
    // Lógica principal cuando ticker es válido
    System.out.println("Precio: " + ticker.getMid());
    
    // TODO: Tu estrategia de trading aquí
}
```

Este patrón es **obligatorio** según `AGENTS.md`. Evita anidación y hace el código más legible.

### ¿Qué Debes Implementar?

1. **Estado del Bot**: Agrega variables de instancia para rastrear:
   ```java
   private double balance;
   private Map<String, Integer> inventory;
   private Map<String, Double> prices;
   ```

2. **Lógica de Trading**: En `onTicker()`, implementa:
   - Detectar oportunidades de compra/venta
   - Calcular ganancias potenciales
   - Enviar órdenes usando el `ConectorBolsa`

3. **Producción**: Si tu rol permite producir:
   - Verifica ingredientes en `onInventoryUpdate()`
   - Calcula cuánto producir (algoritmo recursivo)
   - Envía comando de producción

4. **Gestión de Errores**: En `onError()`:
   - Registra errores
   - Implementa lógica de retry
   - Ajusta tu estrategia

### Ejemplo de Extensión (Para Estudiantes)

```java
private static class MyTradingBot implements EventListener {
    // Estado del bot
    private double currentBalance = 0;
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Double> lastPrices = new HashMap<>();
    
    @Override
    public void onLoginOk(LoginOKMessage loginOk) {
        if (loginOk == null) {
            return;
        }
        
        // Inicializar estado
        currentBalance = loginOk.getCurrentBalance();
        System.out.println("Balance inicial: $" + currentBalance);
    }
    
    @Override
    public void onTicker(TickerMessage ticker) {
        if (ticker == null) {
            return;
        }
        
        // Guardar precio
        lastPrices.put(ticker.getProduct(), ticker.getMid());
        
        // Estrategia simple: comprar si el precio es bajo
        if (ticker.getMid() < 50.0 && currentBalance > 100.0) {
            // TODO: Enviar orden de compra usando ConectorBolsa
            System.out.println("💡 Oportunidad de compra: " + ticker.getProduct());
        }
    }
    
    // ... otros métodos
}
```

### Casos de Uso Típicos

A continuación, algunos patrones comunes que puedes implementar:

#### Caso 1: Market Maker Simple

Estrategia de comprar barato y vender caro con un spread fijo:

```java
@Override
public void onTicker(TickerMessage ticker) {
    if (ticker == null) {
        return;
    }
    
    double spread = 0.05; // 5% de ganancia
    double precioCompra = ticker.getBestAsk();
    double precioVenta = precioCompra * (1 + spread);
    
    // Lógica: comprar al mejor Ask, vender con spread
    if (currentBalance > precioCompra * 10) {
        // TODO: Enviar orden de compra
        System.out.println("💡 Oportunidad: Comprar " + ticker.getProduct());
    }
}
```

#### Caso 2: Arbitraje de Productos

Comprar productos básicos y crear productos complejos si es rentable:

```java
public void evaluarCrafteo(Recipe receta) {
    // 1. Calcular costo de ingredientes
    double costoTotal = 0;
    for (String ingrediente : receta.getIngredientes().keySet()) {
        double precio = lastPrices.getOrDefault(ingrediente, 0.0);
        int cantidad = receta.getIngredientes().get(ingrediente);
        costoTotal += precio * cantidad;
    }
    
    // 2. Comparar con precio de venta del producto final
    double precioVenta = lastPrices.getOrDefault(receta.getProducto(), 0.0);
    
    // 3. Si es rentable, producir
    if (precioVenta > costoTotal * 1.2) { // 20% de margen mínimo
        System.out.println("💰 Crafteo rentable: " + receta.getProducto());
        // TODO: Verificar ingredientes y producir
    }
}
```

#### Caso 3: Gestión de Riesgo

Limitar exposición por producto para no arriesgar todo el capital:

```java
private static final double MAX_EXPOSURE_PER_PRODUCT = 0.1; // 10% del capital

public boolean puedeComprar(String producto, int cantidad, double precio) {
    double exposicion = cantidad * precio;
    double capitalTotal = currentBalance;
    
    // Agregar valor del inventario al capital total
    for (Map.Entry<String, Integer> item : inventory.entrySet()) {
        double precioItem = lastPrices.getOrDefault(item.getKey(), 0.0);
        capitalTotal += item.getValue() * precioItem;
    }
    
    return exposicion <= (capitalTotal * MAX_EXPOSURE_PER_PRODUCT);
}
```

### Siguientes Pasos

1. **Ejecuta el ejemplo** para ver cómo funciona
2. **Lee los eventos** que llegan del servidor
3. **Implementa tu estrategia** en los métodos TODO
4. **Consulta AGENTS.md** para patrones de diseño
5. **Agrega tests** para tu lógica

---

## 🤖 Configuración del Bot

### 1. Crear el Archivo de Configuración

El bot requiere un archivo `config.json` en `src/main/resources/`:

```bash
cp src/main/resources/config.sample.json src/main/resources/config.json
```

### 2. Editar la Configuración

Edita `src/main/resources/config.json`:

```json
{
  "apiKey": "TK-TU-TOKEN-AQUI",
  "team": "Nombre de tu Equipo",
  "host": "wss://trading.hellsoft.tech/ws"
}
```

**Dónde obtener tu API Key:**
- Tu instructor te proporcionará el token de acceso para el servidor de trading
- **NO compartas tu token** con otros equipos
- **NO subas `config.json` a Git** (está en `.gitignore`)

### 3. Configuración de Logging (Opcional)

El proyecto incluye `simplelogger.properties` para controlar los logs del SDK:

```properties
# src/main/resources/simplelogger.properties
org.slf4j.simpleLogger.defaultLogLevel=WARN
```

**Para ver más detalles del SDK** (útil para debugging), cambia a `INFO` o `DEBUG`:

```properties
org.slf4j.simpleLogger.defaultLogLevel=INFO
# O para debugging detallado:
# org.slf4j.simpleLogger.defaultLogLevel=DEBUG
```

---

## 📁 Estructura del Proyecto

### Código Fuente (Simplificado - Solo 4 archivos)

El proyecto base incluye **solo lo esencial** para que empieces:

```
src/main/java/tech/hellsoft/trading/
├── Main.java                        # 🚀 TU PUNTO DE PARTIDA
│                                    #    - Ejemplo simple de conexión
│                                    #    - Clase MyTradingBot con TODOs
│                                    #    - ¡Aquí implementas tu estrategia!
│
├── config/
│   └── Configuration.java           # Record con apiKey, team, host
│
├── exception/
│   └── ConfiguracionInvalidaException.java  # Errores de configuración
│
└── util/
    └── ConfigLoader.java            # Carga config.json
```

**¡Solo 4 archivos!** Todo lo demás lo crearás tú según necesites.

### Estructura Completa del Proyecto

```
spacial-trading-bot-base/
├── config/                          # Herramientas de calidad de código
│   ├── checkstyle/checkstyle.xml   # Reglas de estilo
│   ├── pmd/ruleset.xml              # Análisis estático
│   └── eclipse-format.xml           # Formato de código
│
├── gradle/wrapper/                  # Gradle wrapper (no tocar)
│
├── src/
│   └── main/
│       ├── java/                    # 👈 TU CÓDIGO AQUÍ (4 archivos base)
│       └── resources/
│           └── config.sample.json   # Plantilla de configuración
│
├── build.gradle.kts                 # Dependencias y plugins
├── settings.gradle.kts              # Configuración Gradle
├── gradle.properties.sample         # Plantilla (copiar y editar)
├── .java-version                    # Java 25
├── .gitignore                       # Archivos a ignorar
├── AGENTS.md                        # 📖 Guía de diseño (léela!)
└── README.md                        # Este archivo
```

### ¿Qué Archivos Crearás Tú?

Según `AGENTS.md`, probablemente necesitarás crear:

```
src/main/java/tech/hellsoft/trading/
├── model/
│   ├── Role.java                    # Datos de tu rol (especies, energía, etc.)
│   └── Recipe.java                  # Recetas de producción
│
├── exception/                       # Tus excepciones de negocio (7 mínimo)
│   ├── SaldoInsuficienteException.java
│   ├── InventarioInsuficienteException.java
│   ├── ProductoNoAutorizadoException.java
│   ├── IngredientesInsuficientesException.java
│   ├── RecetaNoEncontradaException.java
│   └── ...                          # Y más según necesites
│
└── strategy/                        # Tu lógica de trading
    ├── TradingStrategy.java
    ├── ProductionCalculator.java    # Algoritmo recursivo
    └── InventoryManager.java
```

**Principio clave**: Empieza simple, agrega complejidad solo cuando la necesites.

### Archivos que NO deben subirse a Git

Estos archivos están en `.gitignore` porque contienen información sensible o son generados automáticamente:

- `gradle.properties` - Credenciales de GitHub
- `src/main/resources/config.json` - Token de API del bot
- `build/` - Archivos compilados
- `.gradle/` - Cache de Gradle
- `.idea/workspace.xml` - Configuración personal de IntelliJ

---

## 🔍 Herramientas de Calidad de Código

El proyecto incluye tres herramientas de análisis de código:

### 1. Spotless (Formateo automático)

```bash
# Verificar el formato del código
./gradlew spotlessCheck

# Aplicar formato automáticamente
./gradlew spotlessApply
```

**Recomendación:** Ejecuta `spotlessApply` antes de cada commit.

### 2. Checkstyle (Estilo de código)

```bash
# Verificar el estilo de código
./gradlew checkstyleMain
./gradlew checkstyleTest

# Ver el reporte en:
# build/reports/checkstyle/main.html
```

### 3. PMD (Análisis estático)

```bash
# Ejecutar análisis estático
./gradlew pmdMain
./gradlew pmdTest

# Ver el reporte en:
# build/reports/pmd/main.html
```

### Verificar Todo

```bash
# Ejecutar todas las verificaciones + tests
./gradlew check

# Formatear y verificar
./gradlew spotlessApply check
```

---

## 🐛 Solución de Problemas Comunes

### Error: "Could not resolve tech.hellsoft.trading:websocket-client"

**Causa:** No se puede acceder a GitHub Packages.

**Solución:**
1. Verifica que `gradle.properties` existe y tiene las credenciales correctas
2. Verifica que tu token de GitHub tenga el scope `read:packages`
3. Prueba regenerar el token en GitHub
4. En IntelliJ: **Gradle** → **Reload All Gradle Projects**

### Error: "Unsupported class file major version 69"

**Causa:** Estás usando una versión de Java anterior a Java 25.

**Solución:**
1. Instala JDK 25
2. En IntelliJ: **File** → **Project Structure** → **Project** → **SDK:** Java 25
3. Reinicia IntelliJ

### El programa no encuentra config.json

**Causa:** No has creado el archivo de configuración.

**Solución:**
```bash
cp src/main/resources/config.sample.json src/main/resources/config.json
# Luego edita config.json con tu API key
```

### IntelliJ no reconoce las clases del SDK

**Causa:** Las dependencias no se descargaron correctamente.

**Solución:**
1. **File** → **Invalidate Caches** → **Invalidate and Restart**
2. Espera a que IntelliJ reconstruya el índice
3. Si persiste: elimina `.gradle/` y `.idea/`, luego reabre el proyecto

### Error: "Login failed" o "401 Unauthorized" al ejecutar

**Causa:** API Key inválida en config.json.

**Solución:**
1. Verifica que `src/main/resources/config.json` existe
2. Verifica que el `apiKey` es correcto (obtén uno nuevo del profesor si es necesario)
3. Verifica que el `host` es correcto: `wss://trading.hellsoft.tech/ws`

### Gradle se queda en "Downloading" o tarda mucho

**Causa:** Primera compilación descarga dependencias.

**Solución:**
- Es normal la primera vez (puede tardar 2-5 minutos)
- Verifica tu conexión a internet
- Si falla, intenta: `./gradlew clean build --refresh-dependencies`

### Checkstyle o PMD reportan muchos errores

**Causa:** El código no sigue los estándares configurados.

**Solución:**
1. Primero ejecuta: `./gradlew spotlessApply` (auto-formatea)
2. Lee los errores específicos en `build/reports/checkstyle/main.html`
3. La mayoría son por no seguir la regla "No Else"
4. Consulta `AGENTS.md` para patrones correctos

### Tests fallan con "NullPointerException"

**Causa:** No se cargó la configuración o faltan archivos.

**Solución:**
1. Verifica que `config.json` existe y tiene todos los campos
2. En tests, usa mocks o crea una configuración de prueba
3. Verifica que no estás accediendo a variables sin inicializar

### OutOfMemoryError al compilar

**Causa:** Gradle necesita más memoria.

**Solución:** Edita `gradle.properties` y agrega:
```properties
org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m
```

---

## 🛠️ Comandos Útiles

### Compilación y Build

```bash
# Limpiar y compilar todo
./gradlew clean build

# Solo compilar (sin tests ni linting)
./gradlew compileJava

# Ver dependencias del proyecto
./gradlew dependencies

# Compilar sin ejecutar tests
./gradlew build -x test
```

### Ejecución

```bash
# Ejecutar la aplicación
./gradlew run

# En Windows
gradlew.bat run

# Ejecutar con argumentos (si agregas soporte)
./gradlew run --args="--config custom.json"
```

### Calidad de Código

```bash
# Verificar estilo con Checkstyle
./gradlew checkstyleMain

# Análisis estático con PMD
./gradlew pmdMain

# Formatear código automáticamente con Spotless
./gradlew spotlessApply

# Verificar formato sin aplicar cambios
./gradlew spotlessCheck

# Ejecutar todas las verificaciones
./gradlew check

# Ver reportes HTML
# Checkstyle: build/reports/checkstyle/main.html
# PMD: build/reports/pmd/main.html
```

### Testing

```bash
# Ejecutar todos los tests
./gradlew test

# Ejecutar tests específicos
./gradlew test --tests "ConfigLoaderTest"

# Ver reporte de tests
# Abrir: build/reports/tests/test/index.html

# Tests con más detalle
./gradlew test --info
```

### Empaquetado

```bash
# Crear JAR ejecutable
./gradlew jar

# Crear distribución completa
./gradlew distZip

# El JAR estará en: build/libs/
# La distribución en: build/distributions/
```

### Mantenimiento

```bash
# Limpiar archivos generados
./gradlew clean

# Refrescar dependencias (si hay problemas)
./gradlew clean build --refresh-dependencies

# Ver todas las tareas disponibles
./gradlew tasks

# Ver información del proyecto
./gradlew properties
```

---

## 🎓 Flujo de Trabajo Recomendado

### Desarrollo Diario

1. **Actualizar el código**
```bash
git pull origin main
```

2. **Crear una rama para tu feature**
```bash
git checkout -b feature/mi-estrategia-trading
```

3. **Desarrollar y probar**
```bash
# Editar código
./gradlew spotlessApply  # Formatear
./gradlew build          # Compilar y verificar
./gradlew run            # Probar
```

4. **Verificar calidad antes de commit**
```bash
./gradlew spotlessApply check
```

5. **Commit y push**
```bash
git add .
git commit -m "feat: implementar estrategia de market making"
git push origin feature/mi-estrategia-trading
```

### Trabajo en Equipo

- Cada miembro necesita su propio `gradle.properties` (credenciales de GitHub)
- Pueden compartir el mismo `config.json` (token del equipo)
- Usar ramas separadas para evitar conflictos
- Hacer pull requests para revisar código antes de merge
- Sincronizar cambios frecuentemente

---

## 📚 Recursos Adicionales

### Documentación del Proyecto

- **[GUIA.md](GUIA.md)** - Guía completa del estudiante con conceptos de trading, explicación del SDK y lore del juego
- **[AGENTS.md](AGENTS.md)** - Principios de diseño y patrones de código (regla "No Else", guard clauses, etc.)
- **[documentacion/INDICE.md](documentacion/INDICE.md)** - Índice maestro de toda la documentación
- **[documentacion/TUTORIAL_PRIMER_DIA.md](documentacion/TUTORIAL_PRIMER_DIA.md)** - Tutorial paso a paso para tu primer día (2-3 horas)
- **[documentacion/INICIO_RAPIDO.md](documentacion/INICIO_RAPIDO.md)** - Inicio rápido en 5 minutos
- **[documentacion/DESARROLLO_EJEMPLOS.md](documentacion/DESARROLLO_EJEMPLOS.md)** - Ejemplos completos de estrategias de trading
- **[documentacion/RESUMEN_DOCUMENTACION.md](documentacion/RESUMEN_DOCUMENTACION.md)** - Resumen ejecutivo de la documentación

### Guías de Git y Configuración

- **[GIT_CHECKLIST.md](GIT_CHECKLIST.md)** - Lista de archivos que deben/no deben estar en Git
- **[SETUP_VERIFICATION.md](SETUP_VERIFICATION.md)** - Checklist de verificación post-clone
- **[COMMIT_READY.md](COMMIT_READY.md)** - Verificación de seguridad antes de hacer commit

### Recursos Externos

- **SDK Documentation:** Consulta el Javadoc en GitHub Packages (si está disponible)
- **Java 25 Features:** https://openjdk.org/projects/jdk/25/
- **Gradle Documentation:** https://docs.gradle.org/
- **Checkstyle Rules:** https://checkstyle.org/checks.html
- **PMD Rules:** https://pmd.github.io/pmd/pmd_rules_java.html

---

## 📝 Notas Importantes

1. **NO subas archivos sensibles a Git:**
   - `gradle.properties` (credenciales de GitHub)
   - `config.json` (token de la API del bot)

2. **Antes de cada commit:**
   ```bash
   ./gradlew spotlessApply
   ./gradlew check
   ```

3. **Para trabajar en equipo:**
   - Cada miembro necesita su propio `gradle.properties`
   - Pueden compartir el mismo `config.json` (token del equipo)
   - Sincronicen cambios frecuentemente con Git

4. **Estilo de código:**
   - El proyecto sigue el principio **"No Else"**
   - Usa guard clauses, switch expressions, y patrones de diseño
   - Consulta `AGENTS.md` para detalles

---

## 🆘 Soporte

Si tienes problemas:

1. Revisa la sección de **Solución de Problemas** arriba
2. Consulta con tus compañeros de equipo
3. Busca en la documentación de Java 25
4. Contacta al instructor

---

**¡Buena suerte con tu bot de trading! 🚀🥑**
