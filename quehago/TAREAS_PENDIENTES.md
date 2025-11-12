# 📋 Lista de Tareas Pendientes - Bolsa Interestelar de Aguacates Andorianos 🥑

## 🎯 Resumen del Estado del Proyecto

Este documento enumera todas las tareas que faltan por completar según la **GUIA.md** del proyecto. El objetivo es construir un cliente de trading inteligente que participe en el torneo de 15 minutos de la Bolsa Interestelar de Aguacates Andorianos.

**Última actualización:** 2025-11-10

---

## ✅ Lo que YA está implementado (SDK y Base)

- ✅ **SDK ConectorBolsa** - Conexión TCP al servidor (provisto por el profesor)
- ✅ **EventListener interface** - Callbacks para eventos del servidor
- ✅ Configuración del proyecto (Gradle, dependencias)
- ✅ Modelos básicos (Recipe, Role en package model)
- ✅ Servicio de UI (ConsoleUIService) - Interfaz de usuario básica
- ✅ Cargador de configuración (ConfigLoader)
- ✅ Utilidades básicas (TradingUtils)
- ✅ Excepciones base (TradingException, ConfiguracionInvalidaException)
- ✅ Estructura de directorios y empaquetado

---

## 🚧 Lo que TÚ debes implementar (según GUIA.md)

### 🔴 PRIORIDAD CRÍTICA (Sin esto el bot NO funciona)

#### 1. ClienteBolsa — El Corazón del Sistema
**Descripción:** Clase principal que implementa `EventListener` y coordina todo el sistema.

**Archivo a crear:**
- `src/main/java/tech/hellsoft/trading/ClienteBolsa.java`

**Componentes A implementar:**

**A) Los 6 Callbacks del SDK (OBLIGATORIOS):**
1. `onLoginOk(LoginOk msg)` - Inicializar EstadoCliente con datos del servidor
2. `onFill(Fill fill)` - Actualizar saldo e inventario cuando se ejecuta una orden
3. `onTicker(Ticker ticker)` - Actualizar precios cada 5 segundos
4. `onOffer(Offer offer)` - Decidir si aceptar ofertas directas (500ms)
5. `onError(ErrorMessage error)` - Manejar errores del servidor con switch
6. `onConexionPerdida(Exception e)` - Informar y sugerir snapshot + resync

**B) Métodos Públicos (OBLIGATORIOS):**
1. `comprar(String producto, int cantidad, String mensaje)` - Validar saldo, crear orden, enviar
2. `vender(String producto, int cantidad, String mensaje)` - Validar inventario, crear orden, enviar
3. `producir(String producto, boolean premium)` - Validar, calcular unidades, consumir ingredientes, notificar
4. `getEstado()` - Retornar EstadoCliente para consultas

**Validaciones críticas:**
- Saldo insuficiente → `SaldoInsuficienteException`
- Inventario insuficiente → `InventarioInsuficienteException`
- Producto no autorizado → `ProductoNoAutorizadoException`
- Ingredientes insuficientes → `IngredientesInsuficientesException`

**Tamaño estimado:** 80-100 líneas  
**Tiempo estimado:** 8-10 horas  
**Complejidad:** ALTA ⭐⭐⭐⭐⭐

---

#### 2. EstadoCliente — El Estado del Juego
**Descripción:** Mantiene todo el estado del cliente. Debe ser `Serializable` para snapshots.

**Archivo a crear:**
- `src/main/java/tech/hellsoft/trading/EstadoCliente.java`

**Campos requeridos:**
- `double saldo` - Dinero actual en efectivo
- `double saldoInicial` - Para calcular P&L
- `Map<String, Integer> inventario` - Productos y cantidades
- `Map<String, Double> preciosActuales` - Actualizado por tickers
- `Map<String, Receta> recetas` - Del servidor en LoginOk
- `Rol rol` - Parámetros del algoritmo recursivo
- `List<String> productosAutorizados` - Productos que puedes producir

**Método CRÍTICO:**
```java
public double calcularPL() {
    // 1. Calcular valor del inventario (cantidad × precio actual)
    // 2. Patrimonio neto = saldo + valor inventario
    // 3. P&L% = ((patrimonioNeto - saldoInicial) / saldoInicial) × 100
}
```

**Tamaño estimado:** 100 líneas  
**Tiempo estimado:** 3-4 horas  
**Complejidad:** MEDIA ⭐⭐⭐

---

#### 3. CalculadoraProduccion — Algoritmo Recursivo
**Descripción:** Calcula unidades producidas usando algoritmo recursivo basado en Rol.

**Archivo a crear:**
- `src/main/java/tech/hellsoft/trading/CalculadoraProduccion.java`

**Método principal:**
```java
public static int calcularUnidades(Rol rol) {
    // Implementar algoritmo recursivo con:
    // - rol.branches (ramas por nivel)
    // - rol.maxDepth (profundidad máxima)
    // - rol.decay (factor de decaimiento)
    // - rol.baseEnergy (energía base)
    // - rol.levelEnergy (energía adicional por nivel)
}

public static int aplicarBonusPremium(int unidadesBase, double bonusPremium) {
    return (int) (unidadesBase * bonusPremium); // típicamente 1.30
}
```

**⚠️ IMPORTANTE:** Este es el algoritmo más crítico. Debe ser RECURSIVO.

**Tamaño estimado:** 30 líneas  
**Tiempo estimado:** 4-5 horas  
**Complejidad:** ALTA ⭐⭐⭐⭐

---

#### 4. RecetaValidator — Validación de Ingredientes
**Descripción:** Valida y consume ingredientes para producción premium.

**Archivo a crear:**
- `src/main/java/tech/hellsoft/trading/RecetaValidator.java`

**Métodos requeridos:**
```java
public static boolean puedeProducir(
    Receta receta, 
    Map<String, Integer> inventario
) {
    // Verificar que haya suficientes ingredientes
    // Retornar true si se puede, false si no
}

public static void consumirIngredientes(
    Receta receta, 
    Map<String, Integer> inventario
) throws IngredientesInsuficientesException {
    // Restar ingredientes del inventario
    // Lanzar excepción si faltan
}
```

**Tamaño estimado:** 40 líneas  
**Tiempo estimado:** 2-3 horas  
**Complejidad:** BAJA ⭐⭐

---

#### 5. SnapshotManager — Serialización Binaria
**Descripción:** Guarda y carga el estado del cliente en archivos binarios.

**Archivo a crear:**
- `src/main/java/tech/hellsoft/trading/SnapshotManager.java`

**Métodos requeridos:**
```java
public static void guardar(EstadoCliente estado, String directorio) 
    throws IOException {
    // Serializar estado a archivo .bin
    // Nombre: snapshot_<timestamp>.bin
}

public static EstadoCliente cargar(String archivo) 
    throws IOException, ClassNotFoundException {
    // Deserializar estado desde archivo .bin
}

public static List<File> listarSnapshots(String directorio) {
    // Listar archivos .bin disponibles ordenados por fecha
}
```

**Tamaño estimado:** 20-30 líneas  
**Tiempo estimado:** 2-3 horas  
**Complejidad:** MEDIA ⭐⭐⭐

---

### 🟡 PRIORIDAD ALTA (Necesario para usar el bot)

#### 6. ConsolaInteractiva — Interfaz de Usuario
**Descripción:** Consola interactiva para ejecutar comandos manualmente.

**Archivo a crear:**
- `src/main/java/tech/hellsoft/trading/ConsolaInteractiva.java`

**Comandos OBLIGATORIOS a implementar:**

1. **`login`** - Conectar y autenticar con el servidor
2. **`status`** - Mostrar saldo, P&L, inventario resumen
3. **`inventario`** - Listar productos en detalle
4. **`precios`** - Mostrar precios actuales de todos los productos
5. **`comprar <producto> <cantidad> [mensaje]`** - Comprar del mercado
6. **`vender <producto> <cantidad> [mensaje]`** - Vender al mercado
7. **`producir <producto> <basico|premium>`** - Producir unidades
8. **`ofertas`** - Listar ofertas pendientes
9. **`aceptar <offerId>`** - Aceptar una oferta
10. **`rechazar <offerId> [motivo]`** - Rechazar oferta (opcional)
11. **`snapshot save`** - Guardar estado actual
12. **`snapshot load`** - Cargar snapshot previo
13. **`resync`** - Sincronizar eventos perdidos
14. **`ayuda` o `help`** - Listar comandos
15. **`exit`** - Salir del programa

**Manejo de errores:** Cada comando debe tener try-catch y mostrar mensajes claros.

**Tamaño estimado:** 100-150 líneas  
**Tiempo estimado:** 6-8 horas  
**Complejidad:** MEDIA ⭐⭐⭐

---

### 🟢 PRIORIDAD MEDIA (Mejora la experiencia)

#### 7. Sistema de Excepciones Completo
**Descripción:** Implementar todas las excepciones requeridas según la GUIA.md.

**Archivos a crear en:** `src/main/java/tech/hellsoft/trading/exception/`

**Jerarquía requerida:**
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

**Todas deben incluir:**
- Constructor con mensaje
- Constructor con mensaje y causa
- Campos adicionales relevantes (ej: `costoRequerido`, `saldoActual`)
- Getters para esos campos

**Tamaño estimado:** 100 líneas total (10-15 líneas por excepción)  
**Tiempo estimado:** 2-3 horas  
**Complejidad:** BAJA ⭐

---

#### 8. DTOs Propios del Dominio
**Descripción:** Clases de datos adicionales necesarias (algunas ya existen).

**Estado actual:**
- ✅ `Recipe.java` - Ya existe
- ✅ `Role.java` - Ya existe
- ❌ Faltan ajustes si es necesario

**Posibles DTOs adicionales:**
- `Orden.java` - Para representar órdenes (si el SDK no lo provee)
- `Oferta.java` - Para guardar ofertas pendientes

**Verificar:** Si el SDK ya provee estos DTOs, NO necesitas crearlos.

**Tiempo estimado:** 1-2 horas  
**Complejidad:** BAJA ⭐

---

### 🏆 BONUS (Opcional - +5% puntos)

#### 9. AutoProduccionManager — Producción Automática
**Descripción:** Extiende `TareaAutomatica` (del SDK) para producir automáticamente.

**Archivo a crear:**
- `src/main/java/tech/hellsoft/trading/AutoProduccionManager.java`

**Estrategia inteligente:**
```java
@Override
protected void ejecutar() {
    // 1. Verificar si tengo ingredientes para premium
    //    SÍ → producir premium (+30% bonus)
    //    NO → producir básico
    
    // 2. Si produje básico:
    //    → Vender inmediatamente para capital
    //    → Comprar ingredientes si es rentable
    
    // 3. Si produje premium:
    //    → NO vender automáticamente
    //    → Dejar para venta manual estratégica
    
    // 4. Todo en try-catch (no detener si falla)
}
```

**Configuración recomendada:**
- Intervalo: 30-60 segundos
- Detener antes de cerrar programa
- Logging de cada acción

**Puntos bonus:** Hasta +5% si está bien implementado

**Tiempo estimado:** 4-6 horas  
**Complejidad:** MEDIA-ALTA ⭐⭐⭐⭐

---

### 🧪 Testing (IMPORTANTE)

#### 10. Tests Unitarios Básicos
**Descripción:** Tests para los componentes críticos.

**Archivos a crear en:** `src/test/java/tech/hellsoft/trading/`

**Tests prioritarios:**
1. `CalculadoraProduccionTest.java` ⭐⭐⭐⭐⭐
   - Test del algoritmo recursivo con diferentes Roles
   - Test del bonus premium
   - Test de edge cases

2. `RecetaValidatorTest.java` ⭐⭐⭐⭐
   - Test de validación con ingredientes suficientes/insuficientes
   - Test de consumo de ingredientes

3. `EstadoClienteTest.java` ⭐⭐⭐⭐
   - Test de cálculo de P&L
   - Test de actualización de inventario

4. `SnapshotManagerTest.java` ⭐⭐⭐
   - Test de serialización/deserialización
   - Test de snapshots corruptos

**Framework:** JUnit 5

**Tiempo estimado:** 4-6 horas  
**Complejidad:** MEDIA ⭐⭐⭐

---

### 📚 Documentación

#### 11. JavaDoc en Clases Principales
**Descripción:** Documentar métodos públicos con JavaDoc.

**Prioridad:**
1. ClienteBolsa (TODOS los métodos públicos)
2. CalculadoraProduccion (algoritmo recursivo explicado)
3. RecetaValidator (lógica de validación)
4. EstadoCliente (especialmente calcularPL)

**Formato mínimo:**
```java
/**
 * Calcula las unidades producidas usando el algoritmo recursivo.
 * 
 * @param rol Los parámetros del rol (branches, maxDepth, decay, etc.)
 * @return Cantidad de unidades producidas
 */
public static int calcularUnidades(Rol rol) {
    // ...
}
```

**Tiempo estimado:** 2-3 horas  
**Complejidad:** BAJA ⭐

---

## 📊 Resumen de Esfuerzo Total

| Prioridad | Tareas | Tiempo Estimado | Complejidad |
|-----------|--------|-----------------|-------------|
| 🔴 CRÍTICA | 5 tareas | 19-25 horas | ⭐⭐⭐⭐⭐ |
| 🟡 ALTA | 1 tarea | 6-8 horas | ⭐⭐⭐ |
| 🟢 MEDIA | 2 tareas | 3-5 horas | ⭐⭐ |
| 🏆 BONUS | 1 tarea | 4-6 horas | ⭐⭐⭐⭐ |
| 🧪 TESTING | 1 tarea | 4-6 horas | ⭐⭐⭐ |
| 📚 DOCS | 1 tarea | 2-3 horas | ⭐ |
| **TOTAL** | **11 tareas** | **38-53 horas** | - |

**Para 3 personas:** ~13-18 horas por persona (3-4 días de trabajo intenso)

---

## 🎯 Roadmap Sugerido para Equipo de 3

### Día 1: Fundamentos (8 horas)
**Objetivo:** Tener la estructura básica funcionando

- ✅ **Persona 1:** ClienteBolsa (callbacks básicos + comprar/vender)
- ✅ **Persona 2:** EstadoCliente + CalculadoraProduccion (algoritmo recursivo)
- ✅ **Persona 3:** Excepciones + RecetaValidator

**Entregable:** Bot que puede conectarse, recibir eventos, y hacer compra/venta básica

---

### Día 2: Funcionalidad Completa (8 horas)
**Objetivo:** Bot completamente funcional

- ✅ **Persona 1:** ConsolaInteractiva (comandos básicos)
- ✅ **Persona 2:** ClienteBolsa.producir() completo + integración
- ✅ **Persona 3:** SnapshotManager + comandos snapshot/resync en consola

**Entregable:** Bot totalmente funcional con consola interactiva

---

### Día 3: Refinamiento y Testing (6 horas)
**Objetivo:** Pulir y asegurar calidad

- ✅ **Persona 1:** Tests de CalculadoraProduccion + fixes
- ✅ **Persona 2:** Tests de otros componentes + JavaDoc
- ✅ **Persona 3:** Refinamiento de ConsolaInteractiva + manejo de errores

**Entregable:** Bot robusto y testeado

---

### Día 4 (Opcional): Bonus y Optimización (4-6 horas)
**Objetivo:** Implementar AutoProduccionManager

- ✅ **Todos:** Trabajo colaborativo en AutoProduccionManager
- ✅ Pruebas en servidor real
- ✅ Ajustes finales para el torneo

---

## ⚠️ Errores del Servidor a Manejar

Según la GUIA.md, estos errores llegan via `onError(ErrorMessage error)`:

| Código | Razón | Acción |
|--------|-------|--------|
| `INVALID_TOKEN` | Token no existe | Terminar programa, verificar config.json |
| `ALREADY_CONNECTED` | Sesión activa | Esperar 30s o reiniciar servidor |
| `INSUFFICIENT_BALANCE` | Saldo insuficiente | ⚠️ BUG en validación local |
| `INSUFFICIENT_INVENTORY` | No tienes producto | ⚠️ BUG en validación local |
| `INVALID_PRODUCT` | Producto no existe | Validar contra catálogo |
| `UNAUTHORIZED_PRODUCT` | No puedes producir | Ver productosAutorizados |
| `INVALID_QUANTITY` | Cantidad inválida | Validar 1 ≤ qty ≤ 10,000 |
| `OFFER_EXPIRED` | Oferta expiró | Responder más rápido |
| `RATE_LIMIT` | Demasiadas órdenes/seg | Espaciar pedidos (mín 100ms) |
| `INTERNAL_ERROR` | Error del servidor | Reportar al profesor |

---

## 🤝 Cómo Colaborar en Equipo

### 1. Flujo de Trabajo Git
```bash
# Cada persona trabaja en su branch
git checkout -b feature/nombre-tarea

# Commits frecuentes
git add .
git commit -m "feat: implementar método comprar en ClienteBolsa"

# Push a tu branch
git push origin feature/nombre-tarea

# Crear Pull Request en GitHub
# Esperar code review de otro miembro
# Merge a main cuando esté aprobado
```

### 2. Comunicación
- **Daily Standup:** 15 minutos al inicio del día
  - ¿Qué hice ayer?
  - ¿Qué haré hoy?
  - ¿Tengo bloqueos?

- **Code Review:** Obligatorio antes de merge
  - Revisar lógica
  - Verificar que compile
  - Probar localmente

### 3. Estándares de Código
- **NO usar else** (guard clauses)
- **Usar records** para DTOs inmutables
- **Nombres descriptivos** en español
- **Constantes en MAYÚSCULAS**
- **Métodos cortos** (máx 20 líneas)

### 4. Testing
- Cada persona testea su código
- Tests unitarios antes de PR
- Pruebas de integración en conjunto

---

## 📞 Recursos y Soporte

### Documentación Existente
1. **GUIA.md** - Guía completa del proyecto (LEER PRIMERO) ⭐⭐⭐⭐⭐
2. **README.md** - Configuración y primeros pasos
3. **documentacion/INDICE.md** - Índice de documentación
4. **documentacion/TUTORIAL_PRIMER_DIA.md** - Setup inicial
5. **documentacion/DESARROLLO_EJEMPLOS.md** - Ejemplos de código

### Preguntas Frecuentes
- **¿Cómo obtengo mi API Key?** → El profesor te la proporciona
- **¿Cómo sé si mi algoritmo recursivo es correcto?** → Crear tests con diferentes Roles
- **¿Qué pasa si crasheo durante el torneo?** → Usar snapshot + resync
- **¿Puedo modificar el SDK?** → NO, el SDK está en .jar y no se puede modificar

### Contacto
- Dudas técnicas: Preguntar en el equipo
- Problemas con servidor: Contactar profesor
- Bugs del SDK: Reportar al profesor

---

## ✅ Checklist de Progreso por Persona

### 👤 Persona 1 - ClienteBolsa y Consola
- [ ] ClienteBolsa.java - Implementar 6 callbacks
- [ ] ClienteBolsa.java - Métodos comprar() y vender()
- [ ] ClienteBolsa.java - Método producir() (colaborar con Persona 2)
- [ ] ConsolaInteractiva.java - 15 comandos
- [ ] Tests de integración
- [ ] JavaDoc en ClienteBolsa

### 👤 Persona 2 - Algoritmos y Estado
- [ ] EstadoCliente.java - Todos los campos y métodos
- [ ] EstadoCliente.java - Método calcularPL()
- [ ] CalculadoraProduccion.java - Algoritmo recursivo ⭐⭐⭐⭐⭐
- [ ] CalculadoraProduccion.java - Bonus premium
- [ ] Integración con ClienteBolsa.producir()
- [ ] CalculadoraProduccionTest.java - Tests exhaustivos
- [ ] JavaDoc en CalculadoraProduccion

### 👤 Persona 3 - Validación y Persistencia
- [ ] Todas las excepciones (8 clases)
- [ ] RecetaValidator.java - puedeProducir()
- [ ] RecetaValidator.java - consumirIngredientes()
- [ ] SnapshotManager.java - Serialización
- [ ] SnapshotManager.java - Deserialización
- [ ] SnapshotManager.java - Listar snapshots
- [ ] RecetaValidatorTest.java
- [ ] SnapshotManagerTest.java

### 🏆 BONUS (Si hay tiempo)
- [ ] AutoProduccionManager.java
- [ ] Estrategia inteligente básico vs premium
- [ ] Integración con ConsolaInteractiva
- [ ] Tests del AutoProduccionManager

---

## 🏁 Criterios de Éxito

### Mínimo Viable (Aprobar)
- ✅ Bot se conecta y autentica
- ✅ Puede comprar y vender manualmente
- ✅ Puede producir (básico y premium)
- ✅ Calcula P&L correctamente
- ✅ Maneja errores sin crashear
- ✅ Consola interactiva funcional

### Excelente (Nota Alta)
- ✅ Todo lo anterior +
- ✅ Snapshots funcionando
- ✅ Resync funcional
- ✅ Tests unitarios completos
- ✅ Código limpio y documentado
- ✅ Manejo robusto de excepciones

### Sobresaliente (Nota Máxima)
- ✅ Todo lo anterior +
- ✅ AutoProduccionManager funcionando
- ✅ Estrategia rentable en el torneo
- ✅ P&L positivo al final del torneo
- ✅ Código ejemplar (puede servir de referencia)

---

**¡Adelante equipo! 🚀🥑**

**Recuerden:** La clave del éxito es la comunicación constante, división clara de tareas, y testing exhaustivo. ¡Mucha suerte en el torneo!

