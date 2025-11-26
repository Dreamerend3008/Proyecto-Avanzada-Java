# 👤 PERSONA 2 - TAREAS RESTANTES

**Responsabilidad**: ConsolaInteractiva y AutoProduccionManager (Bonus)  
**Complejidad**: Media  
**Tiempo estimado**: 5-8 horas  
**Peso en la evaluación**: ~10% del proyecto + 5% bonus

---

## ✅ TAREAS YA COMPLETADAS

Las siguientes tareas **YA ESTÁN IMPLEMENTADAS** y funcionando:

### ✅ TAREA 1: CalculadoraProduccion ✅
**Archivo**: `src/main/java/tech/hellsoft/trading/util/CalculadoraProduccion.java`  
**Estado**: ✅ COMPLETO

- ✅ `calcularUnidades(Rol)` - Método público
- ✅ `calcularRecursivo(nivel, Rol)` - Recursión privada
- ✅ `aplicarBonusPremium(unidades, bonus)` - Aplicar bonus

### ✅ TAREA 2: ClienteBolsa ✅
**Archivo**: `src/main/java/tech/hellsoft/trading/ClienteBolsa.java`  
**Estado**: ✅ COMPLETO

- ✅ Implementa EventListener
- ✅ Todos los 6 callbacks implementados:
  - ✅ onLoginOk()
  - ✅ onFill()
  - ✅ onTicker()
  - ✅ onOffer()
  - ✅ onError()
  - ✅ onInventoryUpdate()
- ✅ Métodos de trading:
  - ✅ comprar(producto, cantidad, mensaje)
  - ✅ vender(producto, cantidad, mensaje)
  - ✅ producir(producto, premium)
  - ✅ aceptarOferta(offerId)

### ✅ TAREA 3: EstadoCliente ✅
**Archivo**: `src/main/java/tech/hellsoft/trading/EstadoCliente.java`  
**Estado**: ✅ COMPLETO

- ✅ Todos los getters y setters
- ✅ Gestión de recetas y rol
- ✅ `calcularPL()`
- ✅ `calcularValorInventario()`
- ✅ Manejo de ofertas pendientes

### ✅ TAREA 4: Comandos en Main.java ✅
**Archivo**: `src/main/java/tech/hellsoft/trading/Main.java`  
**Estado**: ✅ COMPLETO

Los siguientes comandos YA están implementados directamente en Main.java:
- ✅ status
- ✅ inventario
- ✅ precios
- ✅ comprar
- ✅ vender
- ✅ producir
- ✅ ofertas
- ✅ aceptar
- ✅ ayuda
- ✅ exit

---

## 🚀 TAREAS PENDIENTES

### TAREA 5: ConsolaInteractiva (Opcional - Refactorización)

**Estado**: ⚠️ **OPCIONAL** - Los comandos ya funcionan en Main.java

**Descripción**: Si deseas mejorar la organización del código, puedes extraer la lógica de comandos de Main.java a una clase separada `ConsolaInteractiva.java`.

**¿Vale la pena?**: 
- **NO es necesario** - Todo ya funciona
- Solo hazlo si quieres mejorar la arquitectura
- No suma puntos extra (los comandos ya funcionan)

#### Si decides hacerlo:

**Crear archivo**: `src/main/java/tech/hellsoft/trading/util/ConsolaInteractiva.java`

```java
package tech.hellsoft.trading.util;

import tech.hellsoft.trading.ClienteBolsa;
import tech.hellsoft.trading.EstadoCliente;
import java.util.Scanner;
import java.util.Map;

/**
 * Consola interactiva para recibir comandos del usuario.
 * Encapsula la lógica de comandos que actualmente está en Main.java.
 */
public class ConsolaInteractiva {
    
    private final ClienteBolsa cliente;
    private final Scanner scanner;
    private volatile boolean ejecutando;
    
    public ConsolaInteractiva(ClienteBolsa cliente) {
        this.cliente = cliente;
        this.scanner = new Scanner(System.in);
        this.ejecutando = true;
    }
    
    public void iniciar() {
        System.out.println("\n🎮 CONSOLA INTERACTIVA INICIADA");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Escribe 'ayuda' para ver comandos disponibles");
        System.out.println();
        
        while (ejecutando) {
            try {
                System.out.print("> ");
                
                if (!scanner.hasNextLine()) {
                    break;
                }
                
                String linea = scanner.nextLine().trim();
                
                if (linea.isEmpty()) {
                    continue;
                }
                
                procesarComando(linea);
                
            } catch (Exception e) {
                System.err.println("❌ Error: " + e.getMessage());
            }
        }
        
        System.out.println("\n👋 Consola cerrada");
    }
    
    private void procesarComando(String linea) {
        // TODO: Mover la lógica de handleCommand() de Main.java aquí
        // Copiar los métodos:
        // - handleStatus()
        // - handleInventario()
        // - handlePrecios()
        // - handleComprar()
        // - handleVender()
        // - handleProducir()
        // - handleOfertas()
        // - handleAceptarOferta()
    }
    
    public void detener() {
        ejecutando = false;
    }
}
```

**Pasos para refactorizar**:
1. Crear `ConsolaInteractiva.java`
2. Copiar todos los métodos `handle*()` de Main.java
3. En Main.java, reemplazar el loop con:
```java
ConsolaInteractiva consola = new ConsolaInteractiva(cliente);
consola.iniciar();
```

**Tiempo estimado**: 1-2 horas

---

### TAREA 6: AutoProduccionManager (BONUS +5%)

**Estado**: ⭐ **BONUS** - Vale puntos extra

**Descripción**: Producción automática inteligente que corre en segundo plano.

**¿Vale la pena?**: 
- ✅ **SÍ** - Vale +5% en la nota final
- ✅ Libera tiempo durante el torneo
- ✅ Maximiza producción sin intervención manual

#### Implementación Completa

**Crear archivo**: `src/main/java/tech/hellsoft/trading/util/AutoProduccionManager.java`

```java
package tech.hellsoft.trading.util;

import tech.hellsoft.trading.ClienteBolsa;
import tech.hellsoft.trading.EstadoCliente;
import tech.hellsoft.trading.model.Receta;
import tech.hellsoft.trading.dto.server.TareaAutomatica;
import tech.hellsoft.trading.enums.Product;

/**
 * Gestor de producción automática inteligente.
 * Extiende TareaAutomatica para producir cada N segundos.
 * 
 * ESTRATEGIA:
 * - Si hay ingredientes → Producir premium (no vender auto)
 * - Si NO hay ingredientes → Producir básico + vender auto
 */
public class AutoProduccionManager extends TareaAutomatica {
    
    private final ClienteBolsa cliente;
    private final Product productoBasico;
    private final Product productoPremium;
    private final boolean venderBasicoAuto;
    
    /**
     * Constructor.
     * 
     * @param cliente Cliente de trading
     * @param productoBasico Producto para producción básica (ej: "PALTA-OIL")
     * @param productoPremium Producto para producción premium (ej: "GUACA")
     * @param intervaloSegundos Intervalo entre producciones
     * @param venderBasicoAuto Si debe vender automáticamente la producción básica
     */
    public AutoProduccionManager(ClienteBolsa cliente,
                                 Product productoBasico,
                                 Product productoPremium,
                                 int intervaloSegundos,
                                 boolean venderBasicoAuto) {
        super(intervaloSegundos * 1000); // Convertir a milisegundos
        this.cliente = cliente;
        this.productoBasico = productoBasico;
        this.productoPremium = productoPremium;
        this.venderBasicoAuto = venderBasicoAuto;
    }
    
    @Override
    protected void ejecutar() {
        try {
            EstadoCliente estado = cliente.getEstado();
            
            // Estrategia 1: Intentar premium primero
            Receta recetaPremium = estado.getRecetas().get(productoPremium);
            
            if (recetaPremium != null && recetaPremium.isPremium()) {
                boolean puedePremium = RecetaValidator.puedeProducir(
                    recetaPremium,
                    estado.getInventario()
                );
                
                if (puedePremium) {
                    // PRODUCIR PREMIUM (no vender automáticamente)
                    cliente.producir(productoPremium, true);
                    System.out.println("[AUTO-PROD] ✨ Premium: " + productoPremium);
                    return; // Terminar aquí
                }
            }
            
            // Estrategia 2: Producir básico si no se pudo premium
            cliente.producir(productoBasico, false);
            System.out.println("[AUTO-PROD] 🔧 Básico: " + productoBasico);
            
            // Si está configurado, vender la producción básica inmediatamente
            if (venderBasicoAuto) {
                int cantidad = estado.getInventario().getOrDefault(productoBasico, 0);
                
                if (cantidad > 0) {
                    cliente.vender(productoBasico, cantidad, "Auto-venta de producción básica");
                    System.out.println("[AUTO-PROD] 💰 Vendido: " + cantidad + " " + productoBasico);
                }
            }
            
        } catch (Exception e) {
            System.err.println("[AUTO-PROD] ❌ Error: " + e.getMessage());
            // No propagar la excepción - continuar con la siguiente iteración
        }
    }
    
    /**
     * Método de utilidad para iniciar el auto-productor.
     */
    public void iniciar() {
        System.out.println("🤖 AUTO-PRODUCCIÓN INICIADA");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📦 Básico: " + productoBasico);
        System.out.println("✨ Premium: " + productoPremium);
        System.out.println("♻️ Auto-venta básico: " + (venderBasicoAuto ? "SÍ" : "NO"));
        System.out.println();
        
        start(); // Llamar al método start() de TareaAutomatica
    }
    
    /**
     * Método de utilidad para detener el auto-productor.
     */
    public void detenerProduccion() {
        stop(); // Llamar al método stop() de TareaAutomatica
        System.out.println("🛑 AUTO-PRODUCCIÓN DETENIDA");
    }
}
```

#### Uso en Main.java

Agregar después del login exitoso:

```java
// En Main.main(), después de que el login sea exitoso
AutoProduccionManager autoProductor = new AutoProduccionManager(
    cliente,
    Product.PALTA_OIL,    // Producto básico (cambia según tu rol)
    Product.GUACA,        // Producto premium (cambia según tu rol)
    30,                   // Producir cada 30 segundos
    true                  // Auto-vender producción básica
);

autoProductor.iniciar();

System.out.println("🤖 Auto-producción activada en segundo plano");
System.out.println();

// El resto del programa sigue normalmente
```

#### Testing del AutoProduccionManager

```java
// Test: Dejar correr 2 minutos y verificar
// 1. Se produce cada 30 segundos
// 2. Si hay ingredientes, produce premium
// 3. Si no hay ingredientes, produce básico
// 4. La producción básica se vende automáticamente
// 5. La producción premium NO se vende
```

**Tiempo estimado**: 3-4 horas

---

## ✅ Checklist Final

**YA COMPLETADO:**
- [x] ✅ CalculadoraProduccion
- [x] ✅ ClienteBolsa con todos los callbacks
- [x] ✅ Métodos comprar, vender, producir
- [x] ✅ EstadoCliente completo
- [x] ✅ Comandos en Main.java

**PENDIENTE (OPCIONAL):**
- [ ] ConsolaInteractiva (refactorización - no necesario)
- [ ] AutoProduccionManager (BONUS +5%)

---

## 🎯 Recomendación

### Si tienes tiempo limitado:
✅ **NO HAGAS NADA** - Todo lo crítico ya está implementado y funcionando

### Si tienes tiempo extra:
⭐ **HAZ AutoProduccionManager** - Vale +5% y es útil durante el torneo

### Si eres perfeccionista:
🔧 **Refactoriza a ConsolaInteractiva** - Mejora la arquitectura pero no suma puntos

---

## 📊 Resumen de Estado

| Componente | Estado | Prioridad | Puntos |
|------------|--------|-----------|---------|
| CalculadoraProduccion | ✅ COMPLETO | CRÍTICA | 22% |
| ClienteBolsa | ✅ COMPLETO | CRÍTICA | 35% |
| EstadoCliente | ✅ COMPLETO | ALTA | 10% |
| Comandos Main.java | ✅ COMPLETO | ALTA | 8% |
| ConsolaInteractiva | ⚪ OPCIONAL | BAJA | 0% |
| AutoProduccionManager | ⭐ BONUS | MEDIA | +5% |

**Total completado**: 75% del proyecto
**Bonus disponible**: +5%

---

## 💡 Conclusión

**¡FELICIDADES!** Ya tienes el 75% del proyecto funcionando. Las únicas tareas pendientes son opcionales:

1. **AutoProduccionManager** (BONUS +5%) - Recomendado si tienes tiempo
2. **ConsolaInteractiva** (Refactorización) - Solo si quieres mejorar la arquitectura

El sistema está completo y funcional para participar en el torneo. ✅

---

**Tiempo estimado para completar todo**: 5-8 horas (si haces ambas tareas opcionales)  
**Prioridad general**: BAJA (lo crítico ya está hecho)  
**Impacto**: +5% bonus potencial

