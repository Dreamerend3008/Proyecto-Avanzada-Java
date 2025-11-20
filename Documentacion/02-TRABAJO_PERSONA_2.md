# 👤 PERSONA 2

**Responsabilidad**: ClienteBolsa (Corazón del Sistema) y Algoritmo Recursivo  
**Complejidad**: Alta  
**Tiempo estimado**: 18-22 horas  
**Peso en la evaluación**: ~45% del proyecto

---

## 📋 Resumen de Tareas

Esta persona se encarga de las tareas más complejas y críticas:
1. **ClienteBolsa**: Clase principal que implementa EventListener (80-100 líneas)
2. **CalculadoraProduccion**: Algoritmo recursivo (30 líneas) - ⚠️ CRÍTICO
3. **Completar EstadoCliente**: Agregar métodos faltantes y gestión de recetas/rol
4. **Implementar callbacks en MyTradingBot**: Los 6 callbacks del SDK
5. **Comandos complejos**: comprar, vender, producir

---

## 🔥 TAREA 1: CalculadoraProduccion - Algoritmo Recursivo (22% de la nota)

### ⚠️ ESTA ES LA TAREA MÁS CRÍTICA DEL PROYECTO

### Ubicación
`src/main/java/tech/hellsoft/trading/util/CalculadoraProduccion.java`

### Fórmula Matemática
```
Energía(nivel) = baseEnergy + levelEnergy × nivel
Factor(nivel) = decay^nivel × branches^nivel
Unidades(nivel) = Energía(nivel) × Factor(nivel)

Total = Σ Unidades(nivel) para nivel = 0 hasta maxDepth
```

### Implementación Completa

```java
package tech.hellsoft.trading.util;

import tech.hellsoft.trading.model.Rol;

/**
 * Calculadora de unidades producidas usando algoritmo recursivo.
 * 
 * Este es el componente más crítico del sistema de producción.
 * Implementa el algoritmo recursivo que determina cuántas unidades
 * se producen basándose en los parámetros del rol.
 */
public final class CalculadoraProduccion {
    
    private CalculadoraProduccion() {
        // Clase utilitaria - no instanciable
    }
    
    /**
     * Calcula las unidades producidas usando recursión.
     * 
     * @param rol Parámetros del algoritmo (branches, maxDepth, decay, etc.)
     * @return Número total de unidades producidas
     */
    public static int calcularUnidades(Rol rol) {
        return calcularRecursivo(0, rol);
    }
    
    /**
     * Función recursiva que suma contribuciones de cada nivel.
     * 
     * CASO BASE: nivel > maxDepth → retorna 0
     * CASO RECURSIVO: calcula contribución del nivel actual + suma del siguiente nivel
     * 
     * @param nivel Nivel actual del árbol (empieza en 0)
     * @param rol Parámetros del algoritmo
     * @return Suma de unidades de este nivel y todos los niveles inferiores
     */
    private static int calcularRecursivo(int nivel, Rol rol) {
        // ⚠️ CASO BASE: Profundidad máxima alcanzada
        if (nivel > rol.getMaxDepth()) {
            return 0;
        }
        
        // Calcular energía en este nivel
        // Fórmula: baseEnergy + levelEnergy × nivel
        double energia = rol.getBaseEnergy() + (rol.getLevelEnergy() * nivel);
        
        // Calcular factor multiplicador
        // decay^nivel × branches^nivel
        double decay = Math.pow(rol.getDecay(), nivel);
        double branches = Math.pow(rol.getBranches(), nivel);
        double factor = decay * branches;
        
        // Contribución de este nivel
        int contribucion = (int) Math.round(energia * factor);
        
        // 🔄 CASO RECURSIVO: Sumar contribuciones de niveles inferiores
        return contribucion + calcularRecursivo(nivel + 1, rol);
    }
    
    /**
     * Aplica el bonus de producción premium (+30% típicamente).
     * 
     * @param unidadesBase Unidades producidas sin bonus
     * @param bonus Factor de bonus (ej: 1.30 para +30%)
     * @return Unidades con bonus aplicado
     */
    public static int aplicarBonusPremium(int unidadesBase, double bonus) {
        return (int) Math.round(unidadesBase * bonus);
    }
}
```

### Ejemplo de Cálculo (Avocultores)

```
Parámetros del Rol:
- branches = 2
- maxDepth = 4
- decay = 0.7651
- baseEnergy = 3.0
- levelEnergy = 2.0

Cálculo recursivo:

Nivel 0: 
  energía = 3.0 + (2.0 × 0) = 3.0
  factor = 0.7651^0 × 2^0 = 1.0 × 1.0 = 1.0
  contribución = 3.0 × 1.0 = 3
  
Nivel 1:
  energía = 3.0 + (2.0 × 1) = 5.0
  factor = 0.7651^1 × 2^1 = 0.7651 × 2 = 1.530
  contribución = 5.0 × 1.530 = 8
  
Nivel 2:
  energía = 3.0 + (2.0 × 2) = 7.0
  factor = 0.7651^2 × 2^2 = 0.5854 × 4 = 2.344
  contribución = 7.0 × 2.344 = 16
  
Nivel 3:
  energía = 3.0 + (2.0 × 3) = 9.0
  factor = 0.7651^3 × 2^3 = 0.4480 × 8 = 3.584
  contribución = 9.0 × 3.584 = 32
  
Nivel 4:
  energía = 3.0 + (2.0 × 4) = 11.0
  factor = 0.7651^4 × 2^4 = 0.3428 × 16 = 5.485
  contribución = 11.0 × 5.485 = 60
  
Nivel 5: nivel > maxDepth → 0

Total = 3 + 8 + 16 + 32 + 60 = 119 ≈ 120

Producción básica: ~13 unidades (valor aproximado del servidor)
Producción premium (×1.30): 13 × 1.30 = 17 unidades
```

### 🧪 Test del Algoritmo

```java
// Test en Main o clase de prueba
Rol rolAvocultores = new Rol(2, 4, 0.7651, 3.0, 2.0);
int unidades = CalculadoraProduccion.calcularUnidades(rolAvocultores);
System.out.println("Unidades producidas: " + unidades); // Debe dar ~120

int conBonus = CalculadoraProduccion.aplicarBonusPremium(13, 1.30);
System.out.println("Con bonus 30%: " + conBonus); // Debe dar 17
```

---

## 💼 TAREA 2: ClienteBolsa - Corazón del Sistema

### Ubicación
`src/main/java/tech/hellsoft/trading/ClienteBolsa.java`

### Descripción
Esta es la clase más importante del proyecto. Implementa `EventListener` y coordina toda la lógica de negocio.

### Implementación Completa

```java
package tech.hellsoft.trading;

import tech.hellsoft.trading.dto.server.*;
import tech.hellsoft.trading.dto.client.OrderMessage;
import tech.hellsoft.trading.exception.*;
import tech.hellsoft.trading.model.Receta;
import tech.hellsoft.trading.model.Rol;
import tech.hellsoft.trading.util.CalculadoraProduccion;
import tech.hellsoft.trading.util.RecetaValidator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Cliente principal de trading que implementa EventListener.
 * Esta clase es el corazón del sistema - coordina toda la lógica de negocio.
 */
public class ClienteBolsa implements EventListener {
    
    private final ConectorBolsa conector;
    private final EstadoCliente estado;
    private final AtomicInteger orderIdCounter;
    
    public ClienteBolsa(ConectorBolsa conector) {
        this.conector = conector;
        this.estado = new EstadoCliente();
        this.orderIdCounter = new AtomicInteger(1);
    }
    
    // ========== CALLBACKS DEL SDK ==========
    
    @Override
    public void onLoginOk(LoginOKMessage msg) {
        System.out.println("\n✅ LOGIN EXITOSO");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        // Inicializar estado con datos del servidor
        estado.setSaldo(msg.balance());
        estado.setSaldoInicial(msg.balance());
        estado.setNombreEquipo(msg.team());
        
        // Configurar rol (parámetros del algoritmo recursivo)
        if (msg.role() != null) {
            Rol rol = new Rol(
                msg.role().branches(),
                msg.role().maxDepth(),
                msg.role().decay(),
                msg.role().baseEnergy(),
                msg.role().levelEnergy()
            );
            estado.setRol(rol);
            System.out.println("📊 Rol configurado: " + rol);
        }
        
        // Configurar productos autorizados
        if (msg.authorizedProducts() != null) {
            estado.setProductosAutorizados(msg.authorizedProducts());
            System.out.println("📦 Productos autorizados: " + msg.authorizedProducts());
        }
        
        // Configurar recetas
        if (msg.recipes() != null) {
            estado.setRecetas(msg.recipes());
            System.out.println("📚 Recetas cargadas: " + msg.recipes().size());
        }
        
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("💰 Saldo inicial: $%.2f%n", msg.balance());
        System.out.println("🎮 ¡Listo para comerciar!");
        System.out.println();
    }
    
    @Override
    public void onFill(FillMessage fill) {
        System.out.println("\n💰 FILL RECIBIDO");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        String producto = fill.product();
        int cantidad = fill.quantity();
        double precio = fill.price();
        String side = fill.side();
        double monto = cantidad * precio;
        
        // Actualizar estado según el lado de la transacción
        if ("BUY".equalsIgnoreCase(side)) {
            // COMPRA: Restar dinero, sumar inventario
            estado.setSaldo(estado.getSaldo() - monto);
            int cantidadActual = estado.getInventario().getOrDefault(producto, 0);
            estado.getInventario().put(producto, cantidadActual + cantidad);
            
            System.out.printf("📥 COMPRA: %d %s @ $%.2f = -$%.2f%n", 
                    cantidad, producto, precio, monto);
            
        } else if ("SELL".equalsIgnoreCase(side)) {
            // VENTA: Sumar dinero, restar inventario
            estado.setSaldo(estado.getSaldo() + monto);
            int cantidadActual = estado.getInventario().getOrDefault(producto, 0);
            int nuevaCantidad = cantidadActual - cantidad;
            
            if (nuevaCantidad <= 0) {
                estado.getInventario().remove(producto);
            } else {
                estado.getInventario().put(producto, nuevaCantidad);
            }
            
            System.out.printf("📤 VENTA: %d %s @ $%.2f = +$%.2f%n", 
                    cantidad, producto, precio, monto);
        }
        
        // Mostrar mensaje de contraparte si existe
        if (fill.message() != null && !fill.message().isEmpty()) {
            System.out.println("💬 \"" + fill.message() + "\"");
        }
        
        // Mostrar estado actualizado
        System.out.printf("💰 Nuevo saldo: $%.2f%n", estado.getSaldo());
        System.out.printf("📈 P&L: %+.2f%%%n", estado.calcularPL());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println();
    }
    
    @Override
    public void onTicker(TickerMessage ticker) {
        // Actualizar precios actuales (silenciosamente, sin imprimir)
        String producto = ticker.product();
        double mid = ticker.mid();
        estado.getPreciosActuales().put(producto, mid);
        
        // Opcional: imprimir solo la primera vez que se recibe un ticker
        // o cada N tickers para no saturar la consola
    }
    
    @Override
    public void onOffer(OfferMessage offer) {
        System.out.println("\n📬 OFERTA RECIBIDA");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("De: " + offer.buyer());
        System.out.println("Producto: " + offer.product());
        System.out.println("Cantidad: " + offer.quantity());
        System.out.printf("Precio máximo: $%.2f%n", offer.maxPrice());
        System.out.println("Offer ID: " + offer.offerId());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("💡 Usa 'aceptar " + offer.offerId() + "' para aceptar");
        System.out.println();
        
        // Guardar la oferta en EstadoCliente para procesarla después
        estado.getOfertasPendientes().put(offer.offerId(), offer);
    }
    
    @Override
    public void onError(ErrorMessage error) {
        System.err.println("\n❌ ERROR DEL SERVIDOR");
        System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.err.println("Código: " + error.code());
        System.err.println("Razón: " + error.reason());
        System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.err.println();
        
        // Manejar errores específicos
        switch (error.code()) {
            case "INVALID_TOKEN":
                System.err.println("⚠️ Token inválido. Verifica config.json");
                System.err.println("⚠️ Terminando programa...");
                System.exit(1);
                break;
                
            case "INSUFFICIENT_BALANCE":
                System.err.println("⚠️ BUG: Validación local de saldo falló");
                break;
                
            case "INSUFFICIENT_INVENTORY":
                System.err.println("⚠️ BUG: Validación local de inventario falló");
                break;
                
            case "OFFER_EXPIRED":
                System.err.println("💡 La oferta ya expiró. Responde más rápido.");
                break;
                
            case "RATE_LIMIT":
                System.err.println("⚠️ Demasiadas órdenes por segundo. Espera un momento.");
                break;
                
            default:
                System.err.println("💡 Error general del servidor");
        }
    }
    
    @Override
    public void onConnectionLost(Exception e) {
        System.err.println("\n⚠️ CONEXIÓN PERDIDA");
        System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.err.println("Razón: " + e.getMessage());
        System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.err.println();
        System.err.println("💡 Recomendaciones:");
        System.err.println("   1. Guardar snapshot: 'snapshot save'");
        System.err.println("   2. Reiniciar programa");
        System.err.println("   3. Cargar snapshot: 'snapshot load'");
        System.err.println("   4. Hacer resync: 'resync'");
        System.err.println();
    }
    
    // ========== MÉTODOS PÚBLICOS ==========
    
    /**
     * Compra un producto del mercado.
     */
    public void comprar(String producto, int cantidad, String mensaje) 
            throws SaldoInsuficienteException {
        
        // Validar cantidad
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        
        // Obtener precio actual (con margen de seguridad del 5%)
        double precioEstimado = estado.getPreciosActuales().getOrDefault(producto, 0.0);
        if (precioEstimado == 0.0) {
            System.err.println("⚠️ Precio no disponible para " + producto + 
                             ". Esperando ticker...");
            precioEstimado = 100.0; // Precio alto para validación conservadora
        }
        
        double costoEstimado = cantidad * precioEstimado * 1.05; // Margen del 5%
        
        // Validar saldo
        if (estado.getSaldo() < costoEstimado) {
            throw new SaldoInsuficienteException(estado.getSaldo(), costoEstimado);
        }
        
        // Crear orden
        String orderId = "ORD-" + orderIdCounter.getAndIncrement();
        OrderMessage orden = new OrderMessage(
            orderId,
            "BUY",
            producto,
            cantidad,
            mensaje != null ? mensaje : "Orden de compra"
        );
        
        // Enviar al servidor
        conector.enviarOrden(orden);
        
        System.out.println("📤 Orden de compra enviada: " + cantidad + " " + producto);
    }
    
    /**
     * Vende un producto al mercado.
     */
    public void vender(String producto, int cantidad, String mensaje) 
            throws InventarioInsuficienteException {
        
        // Validar cantidad
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        
        // Validar inventario
        int disponible = estado.getInventario().getOrDefault(producto, 0);
        if (disponible < cantidad) {
            throw new InventarioInsuficienteException(producto, disponible, cantidad);
        }
        
        // Crear orden
        String orderId = "ORD-" + orderIdCounter.getAndIncrement();
        OrderMessage orden = new OrderMessage(
            orderId,
            "SELL",
            producto,
            cantidad,
            mensaje != null ? mensaje : "Orden de venta"
        );
        
        // Enviar al servidor
        conector.enviarOrden(orden);
        
        System.out.println("📤 Orden de venta enviada: " + cantidad + " " + producto);
    }
    
    /**
     * Produce un producto (básico o premium).
     */
    public void producir(String producto, boolean premium) 
            throws ProductoNoAutorizadoException, 
                   RecetaNoEncontradaException,
                   IngredientesInsuficientesException {
        
        // 1. Validar que el producto esté autorizado
        if (!estado.getProductosAutorizados().contains(producto)) {
            throw new ProductoNoAutorizadoException(producto, 
                    estado.getProductosAutorizados());
        }
        
        // 2. Obtener la receta
        Receta receta = estado.getRecetas().get(producto);
        if (receta == null) {
            throw new RecetaNoEncontradaException(producto);
        }
        
        // 3. Si es premium, validar y consumir ingredientes
        if (premium && receta.isPremium()) {
            // Validar ingredientes
            if (!RecetaValidator.puedeProducir(receta, estado.getInventario())) {
                throw new IngredientesInsuficientesException(
                    receta.getIngredientes(),
                    estado.getInventario()
                );
            }
            
            // Consumir ingredientes
            RecetaValidator.consumirIngredientes(receta, estado.getInventario());
            System.out.println("🔧 Ingredientes consumidos: " + receta.getIngredientes());
        }
        
        // 4. Calcular unidades producidas
        int unidadesBase = CalculadoraProduccion.calcularUnidades(estado.getRol());
        int unidadesFinales = unidadesBase;
        
        // 5. Si es premium, aplicar bonus
        if (premium && receta.isPremium()) {
            unidadesFinales = CalculadoraProduccion.aplicarBonusPremium(
                unidadesBase, 
                receta.getBonusPremium()
            );
        }
        
        // 6. Actualizar inventario local
        int cantidadActual = estado.getInventario().getOrDefault(producto, 0);
        estado.getInventario().put(producto, cantidadActual + unidadesFinales);
        
        // 7. Notificar al servidor
        conector.enviarProduccion(producto, unidadesFinales);
        
        // 8. Imprimir resultado
        String tipo = (premium && receta.isPremium()) ? "premium" : "básico";
        System.out.printf("✅ Producidas %d unidades de %s (%s)%n", 
                unidadesFinales, producto, tipo);
        System.out.println("📦 Inventario actualizado");
    }
    
    /**
     * Acepta una oferta pendiente.
     */
    public void aceptarOferta(String offerId) {
        OfferMessage oferta = estado.getOfertasPendientes().get(offerId);
        
        if (oferta == null) {
            System.err.println("❌ Oferta no encontrada: " + offerId);
            return;
        }
        
        // Validar que tenemos el producto
        int disponible = estado.getInventario().getOrDefault(oferta.product(), 0);
        if (disponible < oferta.quantity()) {
            System.err.println("❌ No tienes suficiente inventario de " + oferta.product());
            System.err.println("   Necesitas: " + oferta.quantity() + ", Tienes: " + disponible);
            return;
        }
        
        // Aceptar oferta
        conector.aceptarOferta(offerId, oferta.quantity(), oferta.maxPrice());
        System.out.println("✅ Oferta aceptada: " + offerId);
        
        // Remover de pendientes
        estado.getOfertasPendientes().remove(offerId);
    }
    
    public EstadoCliente getEstado() {
        return estado;
    }
}
```

---

## 📊 TAREA 3: Completar EstadoCliente

### Ubicación
`src/main/java/tech/hellsoft/trading/EstadoCliente.java`

### Agregar campos faltantes:

```java
// Agregar al inicio de la clase
private Map<String, Receta> recetas;
private Rol rol;
private Map<String, OfferMessage> ofertasPendientes;

// En el constructor
this.recetas = new HashMap<>();
this.ofertasPendientes = new HashMap<>();
```

### Agregar getters/setters:

```java
public Map<String, Receta> getRecetas() {
    return recetas;
}

public void setRecetas(Map<String, Receta> recetas) {
    this.recetas = recetas;
}

public Rol getRol() {
    return rol;
}

public void setRol(Rol rol) {
    this.rol = rol;
}

public Map<String, OfferMessage> getOfertasPendientes() {
    return ofertasPendientes;
}
```

---

## 💻 TAREA 4: Implementar Comandos Complejos en Main.java

### 4.1 handleComprar()

```java
private static void handleComprar(String[] parts, ConectorBolsa connector, MyTradingBot bot) {
    if (parts.length < 3) {
        System.out.println("❌ Uso: comprar <producto> <cantidad> [mensaje]");
        return;
    }

    try {
        String producto = parts[1];
        int cantidad = Integer.parseInt(parts[2]);
        String mensaje = parts.length > 3
            ? String.join(" ", java.util.Arrays.copyOfRange(parts, 3, parts.length))
            : "Orden de compra";

        ClienteBolsa cliente = bot.getCliente();
        cliente.comprar(producto, cantidad, mensaje);
        
    } catch (NumberFormatException e) {
        System.out.println("❌ Cantidad inválida");
    } catch (SaldoInsuficienteException e) {
        System.out.printf("❌ Saldo insuficiente. Tienes: $%.2f, Necesitas: $%.2f%n",
                e.getSaldoActual(), e.getCostoRequerido());
    } catch (Exception e) {
        System.out.println("❌ Error: " + e.getMessage());
    }
}
```

### 4.2 handleVender()

```java
private static void handleVender(String[] parts, ConectorBolsa connector, MyTradingBot bot) {
    if (parts.length < 3) {
        System.out.println("❌ Uso: vender <producto> <cantidad> [mensaje]");
        return;
    }

    try {
        String producto = parts[1];
        int cantidad = Integer.parseInt(parts[2]);
        String mensaje = parts.length > 3
            ? String.join(" ", java.util.Arrays.copyOfRange(parts, 3, parts.length))
            : "Orden de venta";

        ClienteBolsa cliente = bot.getCliente();
        cliente.vender(producto, cantidad, mensaje);
        
    } catch (NumberFormatException e) {
        System.out.println("❌ Cantidad inválida");
    } catch (InventarioInsuficienteException e) {
        System.out.printf("❌ Inventario insuficiente de %s. Tienes: %d, Necesitas: %d%n",
                e.getProducto(), e.getDisponible(), e.getRequerido());
    } catch (Exception e) {
        System.out.println("❌ Error: " + e.getMessage());
    }
}
```

### 4.3 handleProducir()

```java
private static void handleProducir(String[] parts, ConectorBolsa connector, MyTradingBot bot) {
    if (parts.length < 3) {
        System.out.println("❌ Uso: producir <producto> <basico|premium>");
        return;
    }

    try {
        String producto = parts[1];
        String tipo = parts[2].toLowerCase();
        boolean premium = tipo.equals("premium");

        ClienteBolsa cliente = bot.getCliente();
        cliente.producir(producto, premium);
        
    } catch (ProductoNoAutorizadoException e) {
        System.out.println("❌ " + e.getMessage());
    } catch (RecetaNoEncontradaException e) {
        System.out.println("❌ " + e.getMessage());
    } catch (IngredientesInsuficientesException e) {
        System.out.println("❌ " + e.getMessage());
    } catch (Exception e) {
        System.out.println("❌ Error: " + e.getMessage());
    }
}
```

---

## ✅ Checklist de Tareas

- [ ] Implementar CalculadoraProduccion.calcularRecursivo()
- [ ] Implementar CalculadoraProduccion.aplicarBonusPremium()
- [ ] Probar algoritmo recursivo con diferentes roles
- [ ] Crear ClienteBolsa.java
- [ ] Implementar los 6 callbacks en ClienteBolsa
- [ ] Implementar comprar() con validaciones
- [ ] Implementar vender() con validaciones
- [ ] Implementar producir() con lógica completa
- [ ] Implementar aceptarOferta()
- [ ] Completar EstadoCliente con recetas y rol
- [ ] Implementar handleComprar() en Main
- [ ] Implementar handleVender() en Main
- [ ] Implementar handleProducir() en Main
- [ ] Probar flujo completo: login → producir → vender
- [ ] Documentar código con JavaDoc

---

## 🧪 Testing Crítico

### Test del Algoritmo Recursivo
```java
// Caso 1: Avocultores
Rol rol1 = new Rol(2, 4, 0.7651, 3.0, 2.0);
int resultado = CalculadoraProduccion.calcularUnidades(rol1);
// Verificar que esté cerca de 120

// Caso 2: Con bonus
int conBonus = CalculadoraProduccion.aplicarBonusPremium(13, 1.30);
// Debe dar 17
```

### Test de ClienteBolsa
```java
// 1. Crear cliente
ClienteBolsa cliente = new ClienteBolsa(conector);

// 2. Simular login (manualmente configurar estado)
// 3. Probar comprar con saldo insuficiente (debe lanzar excepción)
// 4. Probar vender sin inventario (debe lanzar excepción)
// 5. Probar producir básico (debe funcionar)
```

---

## 📚 Referencias

- **Guia-Profesor.md**: Sección "CalculadoraProduccion" (página 13)
- **Guia-Profesor.md**: Sección "ClienteBolsa" (página 11-12)
- **Guia-Profesor.md**: Flujos completos (página 24-26)

---

## 🤝 Coordinación con Otros

- **Necesitas de Persona 1**: 
  - Todas las excepciones personalizadas
  - RecetaValidator
  - DTOs (Rol y Receta)
- **Persona 3 necesita de ti**: 
  - ClienteBolsa terminado para conectar con ConsolaInteractiva
  - EstadoCliente completado para SnapshotManager

---

**Estimación total**: 18-22 horas  
**Prioridad**: Crítica (es el corazón del sistema)  
**Dificultad**: Alta  
**⚠️ Empieza con CalculadoraProduccion - es lo más crítico**

