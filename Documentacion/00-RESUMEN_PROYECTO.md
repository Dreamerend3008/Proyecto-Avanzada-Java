# 🥑 Bolsa Interestelar de Aguacates Andorianos - Resumen del Proyecto

## 📋 Descripción General

Este proyecto consiste en desarrollar un **cliente de trading inteligente** en Java 25 que se conecta a una bolsa interestelar de productos (aguacates andorianos) para participar en un torneo de trading de 15 minutos.

### 🎯 Objetivo Principal
Construir un bot de trading que:
- Se conecte a la bolsa vía WebSocket usando el SDK proporcionado
- Compre y venda productos en el mercado
- Produzca productos con un algoritmo recursivo
- Optimice ganancias (P&L%) durante el torneo de 15 minutos

## 🌌 Contexto del Lore

En el año 3847, los aguacates andorianos son semillas cósmicas capaces de absorber energía del espacio-tiempo. El sistema se basa en la **interdependencia**: ninguna especie puede producir todo lo que necesita, por lo que DEBEN comerciar entre sí.

### Las Tres Leyes de Bodoque:
1. "El que no produce, compra. El que no compra, muere."
2. "El mercado castiga al egoísta y premia al cooperador astuto."
3. "Sin interdependencia, no hay comercio. Sin comercio, no hay civilización."

## 🏗️ Arquitectura del Sistema

### Componentes Provistos (SDK)
El SDK proporciona:
- **ConectorBolsa**: Clase que maneja la comunicación TCP/WebSocket
- **EventListener**: Interface que debemos implementar con 6 callbacks
- **TareaAutomatica**: Clase base para tareas periódicas
- **DTOs del servidor**: LoginOk, Fill, Ticker, Offer, ErrorMessage

### Componentes a Implementar (NUESTRO TRABAJO)

#### 1. **ClienteBolsa** (80-100 líneas) - CORAZÓN DEL SISTEMA
- Implementa la interface `EventListener`
- Maneja los 6 callbacks del SDK
- Ofrece métodos públicos: `comprar()`, `vender()`, `producir()`
- Coordina toda la lógica de negocio

#### 2. **EstadoCliente** (100 líneas) - ESTADO DEL JUEGO
- Mantiene el estado del cliente (saldo, inventario, precios)
- Implementa `Serializable` para snapshots
- Calcula el P&L en tiempo real
- Almacena recetas, rol y productos autorizados

#### 3. **CalculadoraProduccion** (30 líneas) - ALGORITMO RECURSIVO ⚠️ CRÍTICO
- Implementa el algoritmo recursivo para calcular unidades producidas
- Fórmula recursiva con energía, decay y branches
- Aplica bonus del 30% para producción premium

#### 4. **RecetaValidator** (40 líneas) - VALIDACIÓN DE RECETAS
- Valida si tenemos ingredientes suficientes
- Consume ingredientes del inventario
- Métodos estáticos: `puedeProducir()`, `consumirIngredientes()`

#### 5. **SnapshotManager** (20 líneas) - SERIALIZACIÓN BINARIA
- Guarda y carga el estado en archivos binarios
- Crítico para recuperarse de crashes
- Usa `ObjectOutputStream` y `ObjectInputStream`

#### 6. **ConfigLoader** (20 líneas) - LECTURA DE JSON
- Carga configuración desde `config.json`
- Ya está parcialmente implementado con Gson
- Valida campos requeridos

#### 7. **ConsolaInteractiva** (100-150 líneas) - INTERFAZ DE USUARIO
- Loop infinito con Scanner para comandos del usuario
- 15 comandos: login, status, inventario, precios, comprar, vender, producir, ofertas, aceptar, rechazar, snapshot save/load, resync, ayuda, exit
- Manejo de errores con try-catch

#### 8. **DTOs Propios** (100 líneas)
- **Rol**: Parámetros del algoritmo recursivo
- **Receta**: Producto, ingredientes, bonus premium
- **Config**: Ya implementado como record

#### 9. **Excepciones Personalizadas** (7 clases) - 15% DE LA NOTA
- `SaldoInsuficienteException`
- `InventarioInsuficienteException`
- `ProductoNoAutorizadoException`
- `IngredientesInsuficientesException`
- `RecetaNoEncontradaException`
- `ConfiguracionInvalidaException` (Ya existe)
- `SnapshotCorruptoException`

#### 10. **AutoProduccionManager** (BONUS +5%)
- Extiende `TareaAutomatica`
- Producción automática cada N segundos
- Estrategia inteligente: premium si hay ingredientes, básico si no

## 📊 Conceptos de Trading

### Tipos de Órdenes
- **Market Order**: Se ejecuta inmediatamente al mejor precio disponible (único tipo en este proyecto)
- **Limit Order**: No disponible en este proyecto

### El Ticker (cada 5 segundos)
- **Best Bid**: Precio más alto que alguien está dispuesto a pagar
- **Best Ask**: Precio más bajo al que alguien está dispuesto a vender
- **Mid Price**: Promedio entre bid y ask
- **Volume**: Cantidad comerciada

### El Fill (Ejecución)
Confirmación de que una orden se ejecutó:
1. Envías orden → Servidor responde `ORDER_ACCEPTED`
2. Espera 1-10 segundos
3. Servidor envía `FILL` → Actualizas saldo e inventario

### Producción
- **Básica**: No requiere ingredientes, más lenta
- **Premium**: Requiere ingredientes, 30% más rápida

### P&L (Profit & Loss)
```
Patrimonio Neto = Efectivo + Valor del Inventario
P&L% = ((Patrimonio Neto - Saldo Inicial) / Saldo Inicial) × 100
```

⚠️ **CRÍTICO**: Al final del torneo, el inventario sin vender vale $0. Solo cuenta el efectivo.

## 🏆 Torneo de 15 Minutos

### Fases del Torneo

| Tiempo | Fase | Estrategia |
|--------|------|-----------|
| T=0-3 min | WARMUP | Producir básico, vender, observar precios |
| T=3-10 min | ACTIVE TRADING | Comprar ingredientes, producir premium, vender con margen |
| T=10-13 min | VOLATILITY | Aumentar volumen, buscar arbitraje |
| T=13-15 min | CLOSING ⚠️ | **LIQUIDAR TODO EL INVENTARIO** |

### Ejemplo de Ciclo Rentable (Avocultores)
```
T=0:00 → Producir PALTA-OIL básico → 13 unidades
T=0:30 → Vender 13 PALTA-OIL @ $26.00 → +$338 (P&L: +3.38%)

T=1:00 → Comprar 5 FOSFO @ $18.00 + 3 PITA @ $22.00 → -$156
T=1:30 → Producir GUACA premium (consume ingredientes) → 17 unidades
T=2:00 → Vender 17 GUACA @ $35.00 → +$595 (P&L: +7.77%)

Repetir ciclo...
```

## 📈 Evaluación

| Funcionalidad | Puntos | Descripción |
|--------------|--------|-------------|
| Login y callbacks | 12% | Implementar EventListener completo |
| Comprar y vender | 18% | Validaciones + enviar órdenes |
| Producir (básico + premium) | 22% | Algoritmo recursivo + validación |
| Excepciones personalizadas | 15% | 7 excepciones + manejo |
| Snapshots + recovery | 13% | Serialización binaria + resync |
| Comandos de consola | 8% | Scanner + parser |
| P&L y análisis | 7% | Cálculo correcto |
| Manejo de errores | 5% | onError() completo |
| **BONUS**: Torneo top 3 | +10% | Mejor estrategia |
| **BONUS**: Auto-producción | +5% | TareaAutomatica |
| **TOTAL** | **100%** | **Máximo: 115%** |

## 🛠️ Stack Tecnológico

- **Java 25**: Lenguaje principal
- **Gradle/Maven**: Build tool
- **Gson**: Parsing JSON (ya incluido)
- **SDK de la Bolsa**: `websocket-client` (GitHub Packages)
- **WebSocket**: Protocolo de comunicación
- **Serialización Binaria**: Para snapshots

## 📁 Estructura del Proyecto

```
src/main/java/tech/hellsoft/trading/
├── Main.java                    [Parcialmente implementado]
├── EstadoCliente.java          [Parcialmente implementado]
├── config/
│   └── Configuration.java      [✅ Implementado]
├── exception/
│   └── ConfiguracionInvalidaException.java [✅ Implementado]
└── util/
    └── ConfigLoader.java       [✅ Implementado]

FALTA IMPLEMENTAR:
├── ClienteBolsa.java           [TODO]
├── CalculadoraProduccion.java  [TODO]
├── RecetaValidator.java        [TODO]
├── SnapshotManager.java        [TODO]
├── ConsolaInteractiva.java     [TODO]
├── model/
│   ├── Rol.java                [TODO]
│   └── Receta.java             [TODO]
└── exception/
    ├── SaldoInsuficienteException.java          [TODO]
    ├── InventarioInsuficienteException.java     [TODO]
    ├── ProductoNoAutorizadoException.java       [TODO]
    ├── IngredientesInsuficientesException.java  [TODO]
    ├── RecetaNoEncontradaException.java         [TODO]
    └── SnapshotCorruptoException.java           [TODO]
```

## 🚀 Estado Actual del Proyecto

### ✅ Ya Implementado
1. **Configuration.java**: Record con validación de campos
2. **ConfigLoader.java**: Carga config.json con Gson
3. **ConfiguracionInvalidaException.java**: Excepción para config
4. **EstadoCliente.java**: Estructura básica con métodos de cálculo de P&L
5. **Main.java**: 
   - Banner y menú interactivo
   - Estructura de comandos (con TODOs)
   - Handlers para cada comando (esqueleto)
   - MyTradingBot con callbacks (esqueleto)

### ⚠️ Pendiente de Implementar
1. **ClienteBolsa.java**: Clase principal (0%)
2. **CalculadoraProduccion.java**: Algoritmo recursivo (0%)
3. **RecetaValidator.java**: Validación de recetas (0%)
4. **SnapshotManager.java**: Serialización binaria (0%)
5. **ConsolaInteractiva.java**: Interfaz de comandos (0%)
6. **DTOs (Rol, Receta)**: (0%)
7. **6 Excepciones personalizadas**: (0%)
8. **Completar EstadoCliente**: Faltan métodos y getters/setters
9. **Implementar los TODOs en Main.java**: Handlers de comandos y callbacks

## 📚 Recursos Disponibles

- **Guia-Profesor.md**: Guía detallada con todos los requisitos
- **README.md**: Setup inicial y configuración
- **config.json**: Configuración del equipo "Avocasticos"
- **SDK websocket-client**: En GitHub Packages

## 🎯 Próximos Pasos

Ver los archivos de distribución de trabajo:
- `01-TRABAJO_PERSONA_1.md` - Carga media
- `02-TRABAJO_PERSONA_2.md` - Carga alta (tareas difíciles)
- `03-TRABAJO_PERSONA_3.md` - Carga baja (tareas fáciles)

---

**Equipo**: Avocasticos  
**API Key**: 
**Host**: wss://trading.hellsoft.tech/ws

