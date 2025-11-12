# 🎓 Tutorial: Tu Primer Día de Desarrollo

Esta guía te llevará paso a paso desde cero hasta tener tu primer bot funcionando con lógica básica.

---

## ⏱️ Tiempo estimado: 2-3 horas

---

## 📝 Paso 1: Verificar Requisitos (15 minutos)

### 1.1 Verificar Java 25
Abre una terminal (CMD o PowerShell) y ejecuta:
```bash
java -version
```

**Debe mostrar:** `java version "25.x.x"`

Si no tienes Java 25:
1. Descarga de: https://www.oracle.com/java/technologies/downloads/
2. Instala
3. Verifica nuevamente

### 1.2 Verificar Git
```bash
git --version
```

**Debe mostrar:** `git version x.x.x`

### 1.3 Clonar el proyecto
```bash
cd C:\Users\TU_USUARIO\Github
git clone https://github.com/tu-usuario/Proyecto-Avanzada-Java.git
cd Proyecto-Avanzada-Java
```

---

## 🔧 Paso 2: Configuración Inicial (20 minutos)

### 2.1 Crear Token de GitHub

1. Ve a GitHub.com → Click en tu foto de perfil (arriba derecha)
2. Settings → Developer settings (abajo izquierda)
3. Personal access tokens → Tokens (classic)
4. "Generate new token (classic)"
5. Nombre: `Trading Bot - Read Packages`
6. Expiration: 90 días (o más)
7. **Selecciona SOLO:** ✅ `read:packages`
8. Click "Generate token"
9. **¡COPIA EL TOKEN!** (solo lo verás una vez)

### 2.2 Crear gradle.properties

En la raíz del proyecto, copia el archivo de ejemplo:
```bash
copy gradle.properties.sample gradle.properties
```

Abre `gradle.properties` y reemplaza:
```properties
gpr.user=TU_USUARIO_GITHUB
gpr.token=EL_TOKEN_QUE_COPIASTE

org.gradle.daemon=true
org.gradle.caching=true
org.gradle.configuration-cache=true
```

### 2.3 Obtener API Key del Servidor

**Consulta con tu profesor** para obtener:
- Tu API Key
- Nombre de tu equipo

### 2.4 Crear config.json

En `src\main\resources\`, copia el archivo de ejemplo:
```bash
copy src\main\resources\config.sample.json src\main\resources\config.json
```

Abre `src\main\resources\config.json` y edita:
```json
{
  "apiKey": "TU_API_KEY_DEL_PROFESOR",
  "team": "Nombre de Tu Equipo",
  "host": "wss://trading.hellsoft.tech/ws"
}
```

⚠️ **IMPORTANTE:** Estos archivos están en `.gitignore`. **NO los subas a Git**.

---

## ✅ Paso 3: Primera Compilación (10 minutos)

### 3.1 Compilar el proyecto
```bash
gradlew.bat clean build
```

**Resultado esperado:**
```
BUILD SUCCESSFUL in 30s
```

Si falla:
- Verifica que `gradle.properties` tiene tus credenciales correctas
- Verifica tu conexión a Internet
- Revisa el mensaje de error

### 3.2 Ejecutar por primera vez
```bash
gradlew.bat run
```

**Debe mostrar:**
```
╔══════════════════════════════════════════════════════════════╗
║  🚀 SPACIAL TRADING BOT CLIENT - Java 25 Edition 🚀         ║
╚════════════════════════════════════════════════════════════╝

✅ Login successful!
```

Si el bot se conecta y muestra "Login successful", **¡felicidades! 🎉** Ya tienes la base funcionando.

Para detener el bot: `Ctrl + C`

---

## 💻 Paso 4: Configurar el IDE (15 minutos)

### Opción A: IntelliJ IDEA (Recomendado)

1. Abre IntelliJ IDEA
2. File → Open → Selecciona la carpeta `Proyecto-Avanzada-Java`
3. Espera a que IntelliJ importe el proyecto (puede tardar unos minutos)
4. Si pregunta por el SDK: Selecciona Java 25
5. Click derecho en `build.gradle.kts` → "Load Gradle Project"
6. Espera a que descargue dependencias

**Crear configuración de ejecución:**
1. Run → Edit Configurations
2. Click "+" → Application
3. Name: `Trading Bot`
4. Main class: `tech.hellsoft.trading.Main`
5. Working directory: (debe ser la raíz del proyecto)
6. Click "OK"

**Probar:**
- Click en el botón verde "Run" (o Shift+F10)
- Debe ejecutarse el bot

### Opción B: VS Code

1. Instala extensión: "Extension Pack for Java"
2. Abre la carpeta del proyecto
3. VS Code debería detectar automáticamente el proyecto Java
4. Abre `Main.java`
5. Click en "Run" encima de `main()`

---

## 🎯 Paso 5: Tu Primera Modificación (30 minutos)

Vamos a agregar un contador de tickers recibidos.

### 5.1 Abrir SDKTradingService.java

Ubicación: `src\main\java\tech\hellsoft\trading\service\impl\SDKTradingService.java`

### 5.2 Agregar variable de instancia

Busca la línea `private boolean running = false;` y agrega debajo:

```java
private boolean running = false;
private int tickerCount = 0;  // <-- NUEVA LÍNEA
```

### 5.3 Modificar el método onTicker

Busca el método `onTicker` dentro de la clase `TradingEventListener`:

```java
@Override
public void onTicker(TickerMessage ticker) {
    tickerCount++;  // <-- AGREGAR ESTA LÍNEA
    
    listenerUiService.printStatus("📊",
        String.format("Ticker #%d: %s Bid:%.2f Ask:%.2f",   // <-- MODIFICAR
            tickerCount, ticker.getProduct(),                 // <-- MODIFICAR
            ticker.getBestBid(), ticker.getBestAsk()));
}
```

### 5.4 Compilar y probar

```bash
gradlew.bat build
gradlew.bat run
```

**Ahora deberías ver:**
```
📊 Ticker #1: AGUA Bid:100.00 Ask:95.00
📊 Ticker #2: OXIGENO Bid:150.00 Ask:148.00
📊 Ticker #3: AGUA Bid:100.50 Ask:95.50
```

¡Felicidades! Has hecho tu primera modificación. 🎉

---

## 🚀 Paso 6: Implementar una Estrategia Simple (45 minutos)

Vamos a implementar un bot que **imprime cuando encuentra una oportunidad** de compra.

### 6.1 Crear una nueva clase

Crea el archivo: `src\main\java\tech\hellsoft\trading\strategy\SimpleStrategy.java`

```java
package tech.hellsoft.trading.strategy;

import tech.hellsoft.trading.dto.server.TickerMessage;
import tech.hellsoft.trading.service.UIService;

public class SimpleStrategy {
    
    private final UIService uiService;
    private final double umbralPrecio;
    
    public SimpleStrategy(UIService uiService, double umbralPrecio) {
        this.uiService = uiService;
        this.umbralPrecio = umbralPrecio;
    }
    
    public void analizarTicker(TickerMessage ticker) {
        if (ticker == null) {
            return;
        }
        
        double bestAsk = ticker.getBestAsk();
        
        // Si el precio de venta es menor al umbral, es oportunidad
        if (bestAsk > 0 && bestAsk < umbralPrecio) {
            uiService.printSuccess(
                String.format("💰 ¡OPORTUNIDAD! %s a %.2f (objetivo: < %.2f)",
                    ticker.getProduct(), bestAsk, umbralPrecio));
        }
    }
}
```

### 6.2 Integrar la estrategia en SDKTradingService

Abre `SDKTradingService.java` y:

**a) Agregar imports al inicio:**
```java
import tech.hellsoft.trading.strategy.SimpleStrategy;
```

**b) Agregar variable de instancia:**
```java
private boolean running = false;
private int tickerCount = 0;
private SimpleStrategy strategy;  // <-- NUEVA LÍNEA
```

**c) Inicializar en el constructor:**
```java
public SDKTradingService(UIService service) {
    this.uiService = service;
    this.strategy = new SimpleStrategy(service, 100.0);  // <-- NUEVA LÍNEA
}
```

**d) Usar en onTicker:**
```java
@Override
public void onTicker(TickerMessage ticker) {
    tickerCount++;
    
    listenerUiService.printStatus("📊",
        String.format("Ticker #%d: %s Bid:%.2f Ask:%.2f",
            tickerCount, ticker.getProduct(),
            ticker.getBestBid(), ticker.getBestAsk()));
    
    // AGREGAR ESTAS LÍNEAS:
    strategy.analizarTicker(ticker);
}
```

### 6.3 Compilar y probar

```bash
gradlew.bat build
gradlew.bat run
```

**Ahora verás:**
```
📊 Ticker #1: AGUA Bid:100.00 Ask:95.00
✅ 💰 ¡OPORTUNIDAD! AGUA a 95.00 (objetivo: < 100.00)
```

---

## 🎨 Paso 7: Personalizar y Experimentar (30 minutos)

### Ideas para practicar:

#### 1. Cambiar el umbral de precio
En el constructor de `SDKTradingService`, cambia el `100.0` por otro valor:
```java
this.strategy = new SimpleStrategy(service, 150.0);
```

#### 2. Agregar un contador de oportunidades
En `SimpleStrategy`, agrega:
```java
private int oportunidadesEncontradas = 0;

public void analizarTicker(TickerMessage ticker) {
    // ...código existente...
    
    if (bestAsk > 0 && bestAsk < umbralPrecio) {
        oportunidadesEncontradas++;
        uiService.printSuccess(
            String.format("💰 ¡OPORTUNIDAD #%d! %s a %.2f",
                oportunidadesEncontradas, ticker.getProduct(), bestAsk));
    }
}
```

#### 3. Filtrar por producto específico
Modifica `analizarTicker`:
```java
public void analizarTicker(TickerMessage ticker) {
    if (ticker == null) {
        return;
    }
    
    // Solo analizar AGUA
    if (!ticker.getProduct().equals("AGUA")) {
        return;
    }
    
    // ...resto del código...
}
```

#### 4. Agregar análisis del spread
```java
public void analizarTicker(TickerMessage ticker) {
    if (ticker == null) {
        return;
    }
    
    double bid = ticker.getBestBid();
    double ask = ticker.getBestAsk();
    
    if (bid <= 0 || ask <= 0) {
        return;
    }
    
    double spread = ask - bid;
    double spreadPorcentaje = (spread / bid) * 100;
    
    if (spreadPorcentaje < 2.0) {
        uiService.printSuccess(
            String.format("💎 Spread bajo en %s: %.2f%%",
                ticker.getProduct(), spreadPorcentaje));
    }
}
```

---

## 🧪 Paso 8: Verificar Calidad del Código (15 minutos)

### 8.1 Formatear automáticamente
```bash
gradlew.bat spotlessApply
```

### 8.2 Verificar estilo
```bash
gradlew.bat checkstyleMain
```

Si hay errores, revisa el reporte en: `build\reports\checkstyle\main.html`

### 8.3 Detectar bugs potenciales
```bash
gradlew.bat pmdMain
```

---

## 📝 Paso 9: Tu Primer Commit (10 minutos)

### 9.1 Ver cambios
```bash
git status
```

### 9.2 Agregar archivos modificados
```bash
git add src/main/java/tech/hellsoft/trading/service/impl/SDKTradingService.java
git add src/main/java/tech/hellsoft/trading/strategy/SimpleStrategy.java
```

### 9.3 Hacer commit
```bash
git commit -m "feat: agregar contador de tickers y estrategia simple"
```

### 9.4 Crear rama y push
```bash
git checkout -b feature/mi-primera-estrategia
git push -u origin feature/mi-primera-estrategia
```

---

## 🎯 ¡Completado!

Has logrado:
- ✅ Configurar el entorno de desarrollo
- ✅ Compilar y ejecutar el proyecto
- ✅ Hacer tu primera modificación
- ✅ Crear una estrategia simple
- ✅ Verificar calidad de código
- ✅ Hacer tu primer commit

---

## 🚀 Siguientes Pasos

### Nivel 1: Básico
- [ ] Agregar más condiciones a tu estrategia (rango de precios, productos específicos)
- [ ] Crear un sistema de alertas para diferentes tipos de oportunidades
- [ ] Implementar un registro de todas las oportunidades encontradas

### Nivel 2: Intermedio
- [ ] Crear un `InventoryManager` para llevar registro del inventario
- [ ] Implementar un `OrderManager` para gestionar órdenes
- [ ] Agregar un sistema de logging con historial

### Nivel 3: Avanzado
- [ ] Implementar un `MarketAnalyzer` que calcule tendencias
- [ ] Crear un `RiskManager` para gestión de riesgo
- [ ] Implementar estrategias de arbitraje con crafteo

### Nivel 4: Experto
- [ ] Sistema de machine learning para predicción de precios
- [ ] Estrategias adaptativas que cambien según el mercado
- [ ] Dashboard web para monitorear el bot en tiempo real

---

## 📚 Recursos de Ayuda

- **README.md**: Guía completa del proyecto
- **DESARROLLO_EJEMPLOS.md**: Ejemplos de código completos
- **AGENTS.md**: Principios de código limpio
- **BUILD_STATUS.md**: Estado del sistema de build

---

## 💬 ¿Necesitas Ayuda?

1. **Errores de compilación**: Revisa el mensaje de error, busca la línea mencionada
2. **El bot no se conecta**: Verifica `config.json` y que el servidor esté activo
3. **Git no funciona**: Asegúrate de estar en la carpeta del proyecto
4. **IDE no reconoce clases**: Click derecho en proyecto → "Reload Gradle Project"

---

## 🎉 Consejos Finales

1. **Prueba frecuentemente**: Después de cada cambio, compila y ejecuta
2. **Commits pequeños**: Haz commit cada vez que completes una funcionalidad
3. **Lee los errores**: Los mensajes de error te dicen exactamente qué está mal
4. **Pide ayuda**: Si estás atascado más de 30 minutos, pregunta
5. **Experimenta**: No tengas miedo de probar cosas nuevas
6. **Diviértete**: ¡Es un proyecto de trading con aguacates espaciales! 🥑🚀

---

**¡Éxito en tu desarrollo! 💪**

