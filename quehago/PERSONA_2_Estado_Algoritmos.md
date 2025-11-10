    @Test
    void testCalcularUnidades_CasoRealista() {
        // Caso realista de juego
        Role rol = new Role(
            3,      // branches
            5,      // maxDepth
            0.85,   // decay (85%)
            50.0,   // baseEnergy
            2.0     // levelEnergy
        );
        
        int unidades = CalculadoraProduccion.calcularUnidades(rol);
        
        // El resultado depende del algoritmo correcto
        // Este test es para verificar que NO crashea
        assertTrue(unidades > 0);
        System.out.println("Unidades con caso realista: " + unidades);
    }
    
    @Test
    void testCalcularUnidades_ProfundidadCero() {
        // Con profundidad 0, solo energía base
        Role rol = new Role(
            2,      // branches
            0,      // maxDepth
            0.8,    // decay
            100.0,  // baseEnergy
            5.0     // levelEnergy
        );
        
        int unidades = CalculadoraProduccion.calcularUnidades(rol);
        
        // Solo energía base en nivel 0
        assertEquals(100, unidades);
    }
    
    @Test
    void testAplicarBonusPremium() {
        int unidadesBase = 100;
        double bonusPremium = 1.30; // +30%
        
        int unidadesConBonus = CalculadoraProduccion.aplicarBonusPremium(unidadesBase, bonusPremium);
        
        assertEquals(130, unidadesConBonus);
    }
    
    @Test
    void testAplicarBonusPremium_ConRedondeo() {
        int unidadesBase = 13;
        double bonusPremium = 1.30;
        
        int unidadesConBonus = CalculadoraProduccion.aplicarBonusPremium(unidadesBase, bonusPremium);
        
        // 13 × 1.30 = 16.9 → 17
        assertEquals(17, unidadesConBonus);
    }
    
    @Test
    void testCalcularUnidades_RolNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            CalculadoraProduccion.calcularUnidades(null);
        });
    }
    
    @Test
    void testCalcularUnidades_BranchesInvalidos() {
        Role rol = new Role(0, 3, 0.8, 50.0, 2.0); // branches = 0
        
        assertThrows(IllegalArgumentException.class, () -> {
            CalculadoraProduccion.calcularUnidades(rol);
        });
    }
    
    @Test
    void testAplicarBonusPremium_BonusInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            CalculadoraProduccion.aplicarBonusPremium(100, 0.5); // bonus < 1.0
        });
    }
}
```

#### Criterios de Éxito
- [ ] Al menos 10 tests diferentes
- [ ] Tests de casos simples (1 rama, 1 nivel)
- [ ] Tests de casos complejos (múltiples ramas y niveles)
- [ ] Tests de edge cases (profundidad 0, null, negativos)
- [ ] Tests del bonus premium
- [ ] Todos los tests pasan ✅

---

### ✅ Tarea 4: Tests de EstadoCliente
**Prioridad:** 🟡🟡 MEDIA  
**Tiempo estimado:** 2-3 horas  
**Archivo:** `src/test/java/tech/hellsoft/trading/EstadoClienteTest.java`

#### Implementación

```java
package tech.hellsoft.trading;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstadoClienteTest {
    
    private EstadoCliente estado;
    
    @BeforeEach
    void setUp() {
        estado = new EstadoCliente();
        estado.setSaldo(10000.0);
        estado.setSaldoInicial(10000.0);
    }
    
    @Test
    void testCalcularPL_SinCambios() {
        double pl = estado.calcularPL();
        assertEquals(0.0, pl, 0.01);
    }
    
    @Test
    void testCalcularPL_SoloEfectivo() {
        estado.setSaldo(12000.0); // +2000
        
        double pl = estado.calcularPL();
        assertEquals(20.0, pl, 0.01); // +20%
    }
    
    @Test
    void testCalcularPL_ConInventario() {
        estado.setSaldo(8000.0); // -2000
        estado.getInventario().put("PALTA-OIL", 100);
        estado.getPreciosActuales().put("PALTA-OIL", 50.0); // 100 × 50 = 5000
        
        // Patrimonio: 8000 + 5000 = 13000
        // P&L: (13000 - 10000) / 10000 = 30%
        double pl = estado.calcularPL();
        assertEquals(30.0, pl, 0.01);
    }
    
    @Test
    void testCalcularValorInventario() {
        estado.getInventario().put("FOSFO", 50);
        estado.getInventario().put("PITA", 30);
        estado.getPreciosActuales().put("FOSFO", 20.0);
        estado.getPreciosActuales().put("PITA", 25.0);
        
        // 50 × 20 + 30 × 25 = 1000 + 750 = 1750
        double valor = estado.calcularValorInventario();
        assertEquals(1750.0, valor, 0.01);
    }
    
    @Test
    void testAgregarInventario() {
        estado.agregarInventario("GUACA", 10);
        estado.agregarInventario("GUACA", 5);
        
        assertEquals(15, estado.getCantidadInventario("GUACA"));
    }
    
    @Test
    void testQuitarInventario() {
        estado.agregarInventario("SEBO", 20);
        
        boolean exito = estado.quitarInventario("SEBO", 5);
        
        assertTrue(exito);
        assertEquals(15, estado.getCantidadInventario("SEBO"));
    }
    
    @Test
    void testQuitarInventario_Insuficiente() {
        estado.agregarInventario("NUCREM", 10);
        
        boolean exito = estado.quitarInventario("NUCREM", 15);
        
        assertFalse(exito);
        assertEquals(10, estado.getCantidadInventario("NUCREM")); // No cambió
    }
    
    @Test
    void testTieneSuficiente() {
        estado.agregarInventario("FOSFO", 50);
        
        assertTrue(estado.tieneSuficiente("FOSFO", 50));
        assertTrue(estado.tieneSuficiente("FOSFO", 30));
        assertFalse(estado.tieneSuficiente("FOSFO", 51));
    }
}
```

---

### ✅ Tarea 5: Integración con ClienteBolsa.producir()
**Prioridad:** 🔴🔴🔴 ALTA  
**Tiempo estimado:** 1-2 horas

#### Descripción
Trabajar con Persona 1 para integrar tus componentes en el método `producir()` de ClienteBolsa.

#### Checklist de Integración
- [ ] EstadoCliente se inicializa correctamente en `onLoginOk()`
- [ ] `CalculadoraProduccion.calcularUnidades()` se llama correctamente
- [ ] `aplicarBonusPremium()` se usa para producción premium
- [ ] EstadoCliente se actualiza después de producir
- [ ] Test de integración pasa

---

### ✅ Tarea 6: JavaDoc
**Prioridad:** 🟢 BAJA  
**Tiempo estimado:** 1-2 horas

Agregar JavaDoc detallado a:
- `CalculadoraProduccion` - Explicar el algoritmo recursivo paso a paso
- `EstadoCliente.calcularPL()` - Explicar la fórmula
- Todos los métodos públicos

---

## 📊 Resumen de Tu Trabajo

| Tarea | Horas | Prioridad | Estado |
|-------|-------|-----------|--------|
| EstadoCliente | 3-4h | 🔴 MÁXIMA | ⬜ |
| CalculadoraProduccion | 4-5h | 🔴 CRÍTICA | ⬜ |
| Tests de CalculadoraProduccion | 3-4h | 🔴 ALTA | ⬜ |
| Tests de EstadoCliente | 2-3h | 🟡 MEDIA | ⬜ |
| Integración | 1-2h | 🔴 ALTA | ⬜ |
| JavaDoc | 1-2h | 🟢 BAJA | ⬜ |
| **TOTAL** | **14-20h** | - | - |

---

## 🤝 Coordinación con el Equipo

### Dependencias
- **Persona 1 necesita:** EstadoCliente (para ClienteBolsa)
- **Persona 3 necesita:** EstadoCliente (para validaciones)

### Te necesitan
- **Persona 1:** Necesita `CalculadoraProduccion` para `producir()`
- **Persona 3:** Necesita `EstadoCliente` para `RecetaValidator`

### Orden Sugerido
1. **Primero:** EstadoCliente (día 1 mañana) - otros lo necesitan
2. **Segundo:** CalculadoraProduccion (día 1 tarde)
3. **Tercero:** Tests (día 2)
4. **Cuarto:** Integración (día 2)

---

## 💡 Tips y Mejores Prácticas

1. **Para EstadoCliente:**
   - Implementa los métodos de utilidad primero
   - Deja `calcularPL()` para el final
   - Haz tests simples mientras desarrollas

2. **Para CalculadoraProduccion:**
   - Empieza con casos simples (1 rama, 1 nivel)
   - Imprime valores intermedios para debugging
   - Usa el método `imprimirArbol()` para visualizar
   - NO te frustres si el algoritmo no es perfecto al inicio

3. **Para Tests:**
   - Empieza con el caso más simple posible
   - Agrega complejidad gradualmente
   - Si un test falla, usa el debugger

4. **Debugging del Algoritmo Recursivo:**
   ```java
   // Agregar prints temporales
   System.out.println("Nivel: " + nivelActual + ", Energía: " + energiaNivel);
   ```

---

## 🆘 Si Te Atoras

1. **El algoritmo recursivo no funciona:**
   - Verifica el caso base (maxDepth)
   - Asegúrate de que se llama a sí mismo
   - Usa prints para ver el flujo
   - Dibuja el árbol en papel

2. **calcularPL() da resultados raros:**
   - Verifica que `saldoInicial` no sea 0
   - Asegúrate de multiplicar por 100 para el porcentaje
   - Verifica que los precios estén en `preciosActuales`

3. **Tests no pasan:**
   - Lee el mensaje de error completo
   - Usa `assertEquals` con delta para doubles
   - Verifica los valores esperados manualmente

4. **No compila:**
   - Verifica los imports
   - Asegúrate de que `Role` y `Recipe` existan
   - Pide ayuda al equipo

---

## ✅ Checklist Final

Antes de considerar tu trabajo terminado:

- [ ] EstadoCliente implementado y serializable
- [ ] Todos los getters/setters funcionan
- [ ] `calcularPL()` retorna valores correctos
- [ ] CalculadoraProduccion es RECURSIVO
- [ ] `calcularUnidades()` usa todos los parámetros del Role
- [ ] `aplicarBonusPremium()` funciona
- [ ] Al menos 10 tests de CalculadoraProduccion
- [ ] Todos los tests pasan ✅
- [ ] Tests de EstadoCliente completos
- [ ] Integrado con ClienteBolsa.producir()
- [ ] JavaDoc en métodos críticos
- [ ] Code review por otro miembro
- [ ] Merge a main exitoso

---

**¡Éxito con el algoritmo recursivo! 🚀**

Recuerda: Este es el componente más técnico del proyecto. ¡Tómate tu tiempo y pide ayuda si la necesitas!
# 👤 Tareas para Persona 2 - Estado y Algoritmos

## 🎯 Tu Rol en el Equipo

Eres responsable del **cerebro matemático del sistema**. Tu código maneja el estado del juego y los cálculos críticos, especialmente el **algoritmo recursivo de producción**. Este es el componente más técnico y requiere pensamiento algorítmico.

**Componentes bajo tu responsabilidad:**
- `EstadoCliente.java` - Mantiene todo el estado del juego
- `CalculadoraProduccion.java` - Algoritmo recursivo ⭐⭐⭐⭐⭐
- Tests exhaustivos de tus componentes

---

## 📋 Tareas Asignadas

### ✅ Tarea 1: EstadoCliente - El Estado del Juego
**Prioridad:** 🔴🔴🔴🔴🔴 MÁXIMA  
**Tiempo estimado:** 3-4 horas  
**Archivo:** `src/main/java/tech/hellsoft/trading/EstadoCliente.java`

#### Descripción
Esta clase mantiene TODOS los datos del estado actual del cliente. Debe ser `Serializable` para poder guardarse en snapshots binarios.

#### Implementación Completa

```java
package tech.hellsoft.trading;

import tech.hellsoft.trading.model.Recipe;
import tech.hellsoft.trading.model.Role;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Mantiene el estado completo del cliente de trading.
 * Esta clase es serializable para permitir snapshots.
 */
public class EstadoCliente implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ========== CAMPOS DE ESTADO ==========
    
    /**
     * Dinero actual en efectivo
     */
    private double saldo;
    
    /**
     * Saldo inicial para calcular P&L
     */
    private double saldoInicial;
    
    /**
     * Inventario de productos: nombre → cantidad
     */
    private Map<String, Integer> inventario;
    
    /**
     * Precios actuales de mercado (mid price del ticker)
     * Se actualiza cada vez que llega un TickerMessage
     */
    private Map<String, Double> preciosActuales;
    
    /**
     * Recetas de producción del servidor
     */
    private Map<String, Recipe> recetas;
    
    /**
     * Rol con parámetros del algoritmo recursivo
     */
    private Role rol;
    
    /**
     * Lista de productos que esta especie puede producir
     */
    private List<String> productosAutorizados;
    
    /**
     * Nombre del equipo (opcional, para logging)
     */
    private String nombreEquipo;
    
    /**
     * Timestamp del último snapshot (opcional)
     */
    private long timestampSnapshot;
    
    // ========== CONSTRUCTOR ==========
    
    public EstadoCliente() {
        this.inventario = new HashMap<>();
        this.preciosActuales = new HashMap<>();
        this.recetas = new HashMap<>();
        this.productosAutorizados = new ArrayList<>();
        this.saldo = 0.0;
        this.saldoInicial = 0.0;
        this.timestampSnapshot = System.currentTimeMillis();
    }
    
    // ========== MÉTODO CRÍTICO: CALCULAR P&L ==========
    
    /**
     * Calcula el Profit & Loss en porcentaje.
     * 
     * Fórmula:
     * 1. Calcular valor del inventario = suma(cantidad × precio) para cada producto
     * 2. Patrimonio neto = saldo + valor del inventario
     * 3. P&L% = ((patrimonioNeto - saldoInicial) / saldoInicial) × 100
     * 
     * @return P&L en porcentaje (ej: 25.5 significa +25.5%)
     */
    public double calcularPL() {
        // TODO: Implementar cálculo de P&L
        
        // 1. Calcular valor del inventario
        double valorInventario = 0.0;
        for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
            String producto = entry.getKey();
            int cantidad = entry.getValue();
            
            // Obtener precio actual (si no hay precio, asumir 0)
            Double precio = preciosActuales.get(producto);
            if (precio != null && precio > 0) {
                valorInventario += cantidad * precio;
            }
        }
        
        // 2. Calcular patrimonio neto
        double patrimonioNeto = saldo + valorInventario;
        
        // 3. Calcular P&L porcentual
        if (saldoInicial == 0) {
            return 0.0; // Evitar división por cero
        }
        
        double pl = ((patrimonioNeto - saldoInicial) / saldoInicial) * 100.0;
        
        return pl;
    }
    
    /**
     * Calcula el valor total del inventario actual
     * 
     * @return Valor en efectivo del inventario
     */
    public double calcularValorInventario() {
        double valor = 0.0;
        for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
            String producto = entry.getKey();
            int cantidad = entry.getValue();
            Double precio = preciosActuales.get(producto);
            
            if (precio != null && precio > 0) {
                valor += cantidad * precio;
            }
        }
        return valor;
    }
    
    /**
     * Calcula el patrimonio neto total (saldo + inventario)
     * 
     * @return Patrimonio neto en efectivo
     */
    public double calcularPatrimonioNeto() {
        return saldo + calcularValorInventario();
    }
    
    // ========== MÉTODOS DE UTILIDAD ==========
    
    /**
     * Obtiene la cantidad de un producto en inventario
     * 
     * @param producto Nombre del producto
     * @return Cantidad disponible (0 si no existe)
     */
    public int getCantidadInventario(String producto) {
        return inventario.getOrDefault(producto, 0);
    }
    
    /**
     * Verifica si hay suficiente cantidad de un producto
     * 
     * @param producto Nombre del producto
     * @param cantidadRequerida Cantidad necesaria
     * @return true si hay suficiente, false si no
     */
    public boolean tieneSuficiente(String producto, int cantidadRequerida) {
        return getCantidadInventario(producto) >= cantidadRequerida;
    }
    
    /**
     * Agrega cantidad a un producto en inventario
     * 
     * @param producto Nombre del producto
     * @param cantidad Cantidad a agregar
     */
    public void agregarInventario(String producto, int cantidad) {
        int actual = getCantidadInventario(producto);
        inventario.put(producto, actual + cantidad);
    }
    
    /**
     * Quita cantidad de un producto en inventario
     * 
     * @param producto Nombre del producto
     * @param cantidad Cantidad a quitar
     * @return true si se pudo quitar, false si no había suficiente
     */
    public boolean quitarInventario(String producto, int cantidad) {
        int actual = getCantidadInventario(producto);
        if (actual < cantidad) {
            return false;
        }
        inventario.put(producto, actual - cantidad);
        return true;
    }
    
    /**
     * Verifica si un producto está autorizado para producción
     * 
     * @param producto Nombre del producto
     * @return true si está autorizado, false si no
     */
    public boolean puedeProducir(String producto) {
        return productosAutorizados.contains(producto);
    }
    
    // ========== GETTERS Y SETTERS ==========
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public double getSaldoInicial() {
        return saldoInicial;
    }
    
    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
    
    public Map<String, Integer> getInventario() {
        return inventario;
    }
    
    public void setInventario(Map<String, Integer> inventario) {
        this.inventario = inventario;
    }
    
    public Map<String, Double> getPreciosActuales() {
        return preciosActuales;
    }
    
    public void setPreciosActuales(Map<String, Double> preciosActuales) {
        this.preciosActuales = preciosActuales;
    }
    
    public Map<String, Recipe> getRecetas() {
        return recetas;
    }
    
    public void setRecetas(Map<String, Recipe> recetas) {
        this.recetas = recetas;
    }
    
    public Role getRol() {
        return rol;
    }
    
    public void setRol(Role rol) {
        this.rol = rol;
    }
    
    public List<String> getProductosAutorizados() {
        return productosAutorizados;
    }
    
    public void setProductosAutorizados(List<String> productosAutorizados) {
        this.productosAutorizados = productosAutorizados;
    }
    
    public String getNombreEquipo() {
        return nombreEquipo;
    }
    
    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }
    
    public long getTimestampSnapshot() {
        return timestampSnapshot;
    }
    
    public void setTimestampSnapshot(long timestampSnapshot) {
        this.timestampSnapshot = timestampSnapshot;
    }
    
    // ========== MÉTODOS PARA DEBUGGING ==========
    
    @Override
    public String toString() {
        return String.format(
            "EstadoCliente{equipo='%s', saldo=%.2f, P&L=%.2f%%, productos=%d}",
            nombreEquipo, saldo, calcularPL(), inventario.size()
        );
    }
}
```

#### Criterios de Éxito
- [ ] Todos los campos necesarios están presentes
- [ ] Implementa `Serializable` correctamente
- [ ] `calcularPL()` funciona correctamente
- [ ] Métodos de utilidad para manejar inventario
- [ ] Getters y setters completos

---

### ✅ Tarea 2: CalculadoraProduccion - Algoritmo Recursivo ⭐⭐⭐⭐⭐
**Prioridad:** 🔴🔴🔴🔴🔴 MÁXIMA CRÍTICA  
**Tiempo estimado:** 4-5 horas  
**Archivo:** `src/main/java/tech/hellsoft/trading/CalculadoraProduccion.java`

#### Descripción
Este es el **componente más importante y complejo** de tu trabajo. Implementa el algoritmo recursivo que calcula cuántas unidades se producen basado en los parámetros del `Role`.

#### Contexto del Algoritmo

Según la GUIA.md, el `Role` contiene:
- `int branches` - Número de ramas por nivel
- `int maxDepth` - Profundidad máxima del árbol recursivo
- `double decay` - Factor de decaimiento por nivel (ej: 0.85 = 85% del anterior)
- `double baseEnergy` - Energía base del sistema
- `double levelEnergy` - Energía adicional por nivel

El algoritmo debe construir un árbol recursivo donde:
1. Cada nivel tiene `branches` ramas
2. La energía en cada nivel se reduce por `decay`
3. Se suma la energía hasta alcanzar `maxDepth`
4. El resultado final son las unidades producidas

#### Implementación

```java
package tech.hellsoft.trading;

import tech.hellsoft.trading.model.Role;

/**
 * Calculadora de unidades producidas usando algoritmo recursivo.
 * 
 * El algoritmo construye un árbol de producción donde cada nivel
 * representa una etapa del proceso, y la energía se distribuye
 * entre las ramas con un factor de decaimiento.
 */
public final class CalculadoraProduccion {
    
    // Constructor privado - clase de utilidad
    private CalculadoraProduccion() {
    }
    
    /**
     * Calcula las unidades producidas usando algoritmo recursivo.
     * 
     * El algoritmo funciona así:
     * 1. Comienza con energía base en nivel 0
     * 2. Cada nivel tiene 'branches' ramas
     * 3. La energía se reduce por 'decay' en cada nivel
     * 4. Se suma energía adicional por nivel: levelEnergy × nivel
     * 5. Se recursea hasta maxDepth
     * 6. Se suman todas las energías y se redondea
     * 
     * @param rol Parámetros del algoritmo
     * @return Cantidad de unidades producidas
     */
    public static int calcularUnidades(Role rol) {
        if (rol == null) {
            throw new IllegalArgumentException("Rol no puede ser null");
        }
        
        // Validar parámetros
        if (rol.getBranches() <= 0 || rol.getMaxDepth() < 0) {
            throw new IllegalArgumentException("Parámetros inválidos en Rol");
        }
        
        // Calcular energía total con recursión
        double energiaTotal = calcularEnergiaRecursiva(
            rol.getBaseEnergy(),
            rol.getBranches(),
            rol.getMaxDepth(),
            rol.getDecay(),
            rol.getLevelEnergy(),
            0  // nivel actual
        );
        
        // Redondear al entero más cercano
        return (int) Math.round(energiaTotal);
    }
    
    /**
     * Método recursivo privado que calcula la energía total.
     * 
     * @param energiaActual Energía en el nivel actual
     * @param branches Número de ramas por nivel
     * @param maxDepth Profundidad máxima
     * @param decay Factor de decaimiento
     * @param levelEnergy Energía adicional por nivel
     * @param nivelActual Nivel actual en la recursión
     * @return Energía total acumulada
     */
    private static double calcularEnergiaRecursiva(
            double energiaActual,
            int branches,
            int maxDepth,
            double decay,
            double levelEnergy,
            int nivelActual) {
        
        // Caso base: llegamos al máximo nivel
        if (nivelActual >= maxDepth) {
            return energiaActual;
        }
        
        // Calcular energía en este nivel
        // Agregar bonus por nivel: levelEnergy × nivelActual
        double energiaNivel = energiaActual + (levelEnergy * nivelActual);
        
        // Calcular energía para el siguiente nivel (con decaimiento)
        double energiaSiguiente = energiaNivel * decay;
        
        // Recursión: sumar la energía de todas las ramas
        double energiaRamas = 0.0;
        for (int i = 0; i < branches; i++) {
            energiaRamas += calcularEnergiaRecursiva(
                energiaSiguiente,
                branches,
                maxDepth,
                decay,
                levelEnergy,
                nivelActual + 1
            );
        }
        
        // Retornar energía de este nivel + energía de todas las ramas
        return energiaNivel + energiaRamas;
    }
    
    /**
     * Aplica el bonus de producción premium.
     * 
     * La producción premium otorga un bonus (típicamente 30% = 1.30).
     * 
     * @param unidadesBase Unidades calculadas con algoritmo básico
     * @param bonusPremium Factor de bonus (ej: 1.30 para +30%)
     * @return Unidades con bonus aplicado
     */
    public static int aplicarBonusPremium(int unidadesBase, double bonusPremium) {
        if (bonusPremium < 1.0) {
            throw new IllegalArgumentException("Bonus premium debe ser >= 1.0");
        }
        
        double unidadesConBonus = unidadesBase * bonusPremium;
        return (int) Math.round(unidadesConBonus);
    }
    
    /**
     * Versión simplificada para debugging.
     * Imprime el árbol de recursión.
     * 
     * @param rol Parámetros del algoritmo
     */
    public static void imprimirArbol(Role rol) {
        System.out.println("=== ÁRBOL DE PRODUCCIÓN ===");
        System.out.println("Branches: " + rol.getBranches());
        System.out.println("MaxDepth: " + rol.getMaxDepth());
        System.out.println("Decay: " + rol.getDecay());
        System.out.println("BaseEnergy: " + rol.getBaseEnergy());
        System.out.println("LevelEnergy: " + rol.getLevelEnergy());
        System.out.println();
        
        imprimirArbolRecursivo(
            rol.getBaseEnergy(),
            rol.getBranches(),
            rol.getMaxDepth(),
            rol.getDecay(),
            rol.getLevelEnergy(),
            0,
            ""
        );
        
        System.out.println("\nUnidades totales: " + calcularUnidades(rol));
    }
    
    private static void imprimirArbolRecursivo(
            double energiaActual,
            int branches,
            int maxDepth,
            double decay,
            double levelEnergy,
            int nivelActual,
            String prefijo) {
        
        if (nivelActual >= maxDepth) {
            return;
        }
        
        double energiaNivel = energiaActual + (levelEnergy * nivelActual);
        System.out.printf("%sNivel %d: Energía = %.2f%n", prefijo, nivelActual, energiaNivel);
        
        double energiaSiguiente = energiaNivel * decay;
        for (int i = 0; i < branches; i++) {
            imprimirArbolRecursivo(
                energiaSiguiente,
                branches,
                maxDepth,
                decay,
                levelEnergy,
                nivelActual + 1,
                prefijo + "  "
            );
        }
    }
}
```

#### ⚠️ IMPORTANTE - Notas sobre el Algoritmo

Este algoritmo es **el más crítico del proyecto**. Algunas consideraciones:

1. **¿Es correcto este algoritmo?**
   - La GUIA.md no especifica el algoritmo exacto
   - Esta es UNA interpretación posible
   - Debes **verificar con el profesor** o hacer tests para validar

2. **Alternativas posibles:**
   - Tal vez la energía NO se acumula por nivel
   - Tal vez el `levelEnergy` funciona diferente
   - Tal vez solo cuentan las hojas del árbol

3. **¿Cómo validar?**
   - Crear tests con Roles conocidos
   - Comparar con ejemplos del profesor si los hay
   - Probar en el servidor real

4. **Si el algoritmo está mal:**
   - NO te preocupes, es parte del aprendizaje
   - Ajusta el método `calcularEnergiaRecursiva()`
   - Los tests te ayudarán a encontrar el error

#### Criterios de Éxito
- [ ] El método es RECURSIVO (se llama a sí mismo)
- [ ] Usa todos los parámetros del Role
- [ ] Tiene caso base (maxDepth)
- [ ] Retorna un entero positivo
- [ ] `aplicarBonusPremium()` funciona
- [ ] Tests pasan con diferentes Roles

---

### ✅ Tarea 3: Tests Exhaustivos de CalculadoraProduccion
**Prioridad:** 🔴🔴🔴🔴 ALTA  
**Tiempo estimado:** 3-4 horas  
**Archivo:** `src/test/java/tech/hellsoft/trading/CalculadoraProduccionTest.java`

#### Descripción
Crear tests completos para validar que el algoritmo recursivo funciona correctamente.

#### Implementación

```java
package tech.hellsoft.trading;

import tech.hellsoft.trading.model.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculadoraProduccionTest {
    
    @Test
    void testCalcularUnidades_CasoSimple() {
        // Caso más simple: 1 rama, profundidad 1, sin decay
        Role rol = new Role(
            1,      // branches
            1,      // maxDepth
            1.0,    // decay (sin decaimiento)
            10.0,   // baseEnergy
            0.0     // levelEnergy
        );
        
        int unidades = CalculadoraProduccion.calcularUnidades(rol);
        
        // Debería dar la energía base
        assertEquals(10, unidades);
    }
    
    @Test
    void testCalcularUnidades_ConDecay() {
        // Con decaimiento
        Role rol = new Role(
            1,      // branches
            2,      // maxDepth
            0.5,    // decay (50%)
            10.0,   // baseEnergy
            0.0     // levelEnergy
        );
        
        int unidades = CalculadoraProduccion.calcularUnidades(rol);
        
        // Nivel 0: 10
        // Nivel 1: 10 * 0.5 = 5
        // Total: 15
        assertEquals(15, unidades);
    }
    
    @Test
    void testCalcularUnidades_ConBranches() {
        // Con múltiples ramas
        Role rol = new Role(
            2,      // branches (2 ramas por nivel)
            2,      // maxDepth
            1.0,    // decay (sin decaimiento)
            10.0,   // baseEnergy
            0.0     // levelEnergy
        );
        
        int unidades = CalculadoraProduccion.calcularUnidades(rol);
        
        // Nivel 0: 10
        // Nivel 1: 2 ramas × 10 = 20
        // Total: 30
        assertEquals(30, unidades);
    }
    
    @Test
    void testCalcularUnidades_ConLevelEnergy() {
        // Con energía adicional por nivel
        Role rol = new Role(
            1,      // branches
            3,      // maxDepth
            1.0,    // decay
            10.0,   // baseEnergy
            5.0     // levelEnergy (5 por nivel)
        );
        
        int unidades = CalculadoraProduccion.calcularUnidades(rol);
        
        // Nivel 0: 10 + (5 × 0) = 10
        // Nivel 1: 10 + (5 × 1) = 15
        // Nivel 2: 15 + (5 × 2) = 25
        // Total: 50
        assertEquals(50, unidades);
    }
    

