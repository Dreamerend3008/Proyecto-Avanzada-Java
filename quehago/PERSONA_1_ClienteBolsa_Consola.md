# 👤 Tareas para Persona 1 - ClienteBolsa y ConsolaInteractiva

## 🎯 Tu Rol en el Equipo

Eres responsable de la **interfaz entre el usuario y el sistema**. Tu código es el que el usuario verá y usará directamente. Debes implementar el corazón del sistema (ClienteBolsa) y la consola interactiva para que el usuario pueda operar el bot.

**Componentes bajo tu responsabilidad:**
- `ClienteBolsa.java` - El corazón del sistema
- `ConsolaInteractiva.java` - La interfaz de usuario

---

## 📋 Tareas Asignadas

### ✅ Tarea 1: ClienteBolsa - Los 6 Callbacks (CRÍTICO)
**Prioridad:** 🔴🔴🔴🔴🔴 MÁXIMA  
**Tiempo estimado:** 4-5 horas  
**Archivo:** `src/main/java/tech/hellsoft/trading/ClienteBolsa.java`

#### Descripción
Implementar la clase `ClienteBolsa` que implementa `EventListener` del SDK. Esta clase recibe eventos del servidor y actualiza el estado del cliente.

#### Implementación Detallada

```java
package tech.hellsoft.trading;

import tech.hellsoft.trading.ConectorBolsa;
import tech.hellsoft.trading.EventListener;
import tech.hellsoft.trading.dto.server.*;

public class ClienteBolsa implements EventListener {
    
    private ConectorBolsa conector;
    private EstadoCliente estado;
    
    public ClienteBolsa(ConectorBolsa conector) {
        this.conector = conector;
        this.estado = new EstadoCliente();
    }
    
    // ========== CALLBACKS DEL SDK ==========
    
    /**
     * Callback 1: Inicializar estado cuando el login es exitoso
     */
    @Override
    public void onLoginOk(LoginOKMessage msg) {
        // TODO: Inicializar EstadoCliente con datos del servidor
        // - estado.setSaldo(msg.getInitialBalance())
        // - estado.setSaldoInicial(msg.getInitialBalance())
        // - estado.setRecetas(msg.getRecipes()) // Convertir a Map si es necesario
        // - estado.setRol(msg.getRole())
        // - estado.setProductosAutorizados(msg.getAuthorizedProducts())
        // - Imprimir mensaje de bienvenida
        System.out.println("✅ Conectado como " + msg.getTeam());
        System.out.println("💰 Saldo inicial: $" + msg.getInitialBalance());
    }
    
    /**
     * Callback 2: Procesar ejecución de orden (compra o venta)
     */
    @Override
    public void onFill(FillMessage fill) {
        // TODO: Actualizar saldo e inventario según si es BUY o SELL
        // if (fill.getSide().equals("BUY")) {
        //     // Restar dinero: costo = fill.getFillQty() × fill.getFillPrice()
        //     // Sumar inventario
        // } else if (fill.getSide().equals("SELL")) {
        //     // Sumar dinero: ingreso = fill.getFillQty() × fill.getFillPrice()
        //     // Restar inventario
        // }
        // 
        // Imprimir:
        // - Tipo de transacción (COMPRA/VENTA)
        // - Producto, cantidad, precio
        // - Mensaje de la contraparte
        // - Nuevo saldo
        // - P&L actualizado
    }
    
    /**
     * Callback 3: Actualizar precios de mercado
     */
    @Override
    public void onTicker(TickerMessage ticker) {
        // TODO: Actualizar precios actuales en EstadoCliente
        // estado.getPreciosActuales().put(ticker.getProduct(), ticker.getMidPrice());
        // Opcional: Solo imprimir si el usuario pidió ver precios
    }
    
    /**
     * Callback 4: Decidir sobre ofertas directas
     */
    @Override
    public void onOffer(OfferMessage offer) {
        // TODO: Analizar si conviene aceptar la oferta
        // 1. Verificar si tenemos el producto en inventario
        // 2. Comparar precio ofrecido vs precio actual de mercado
        // 3. Si conviene, guardar en lista de ofertas pendientes
        // 4. Imprimir la oferta para que el usuario decida
        // 
        // Nota: NO aceptar automáticamente, solo mostrar
        // El usuario decidirá con comando "aceptar <offerId>"
    }
    
    /**
     * Callback 5: Manejar errores del servidor
     */
    @Override
    public void onError(ErrorMessage error) {
        // TODO: Switch sobre error.getCode()
        // switch (error.getCode()) {
        //     case "INVALID_TOKEN":
        //         System.err.println("❌ Token inválido");
        //         System.exit(1);
        //         break;
        //     case "INSUFFICIENT_BALANCE":
        //         System.err.println("❌ Saldo insuficiente (BUG en validación local!)");
        //         break;
        //     case "INSUFFICIENT_INVENTORY":
        //         System.err.println("❌ Inventario insuficiente (BUG en validación local!)");
        //         break;
        //     // ... más casos según GUIA.md
        // }
    }
    
    /**
     * Callback 6: Manejar pérdida de conexión
     */
    @Override
    public void onConnectionLost(Throwable throwable) {
        // TODO: Informar al usuario
        System.err.println("⚠️ Conexión perdida: " + throwable.getMessage());
        System.err.println("💡 Sugerencia:");
        System.err.println("   1. Guardar snapshot: snapshot save");
        System.err.println("   2. Reconectar: login");
        System.err.println("   3. Sincronizar: resync");
    }
    
    // ... métodos públicos vienen después
}
```

#### Criterios de Éxito
- [ ] Los 6 callbacks están implementados
- [ ] No hay código duplicado
- [ ] Cada callback hace UNA cosa bien
- [ ] Mensajes claros al usuario
- [ ] Manejo de errores robusto

---

### ✅ Tarea 2: ClienteBolsa - Métodos comprar() y vender()
**Prioridad:** 🔴🔴🔴🔴🔴 MÁXIMA  
**Tiempo estimado:** 2-3 horas  
**Archivo:** `src/main/java/tech/hellsoft/trading/ClienteBolsa.java`

#### Descripción
Implementar los métodos públicos para comprar y vender en el mercado.

#### Implementación

```java
/**
 * Compra un producto del mercado
 * 
 * @param producto Nombre del producto a comprar
 * @param cantidad Cantidad a comprar
 * @param mensaje Mensaje opcional para el vendedor
 * @throws SaldoInsuficienteException Si no hay suficiente dinero
 * @throws PrecioNoDisponibleException Si no hay precio de mercado
 */
public void comprar(String producto, int cantidad, String mensaje) 
        throws SaldoInsuficienteException, PrecioNoDisponibleException {
    
    // TODO: 
    // 1. Obtener precio actual
    //    Double precio = estado.getPreciosActuales().get(producto);
    //    if (precio == null) throw new PrecioNoDisponibleException(producto);
    //
    // 2. Calcular costo estimado (agregar 5% de margen)
    //    double costoEstimado = precio * cantidad * 1.05;
    //
    // 3. Validar saldo
    //    if (estado.getSaldo() < costoEstimado) {
    //        throw new SaldoInsuficienteException(costoEstimado, estado.getSaldo());
    //    }
    //
    // 4. Crear objeto Orden (verificar qué campos necesita)
    //    Orden orden = new Orden(
    //        generarClOrdId(),  // ID único
    //        "BUY",
    //        producto,
    //        cantidad,
    //        mensaje
    //    );
    //
    // 5. Enviar al servidor
    //    conector.enviarOrden(orden);
    //
    // 6. Imprimir confirmación
    //    System.out.println("📤 Orden enviada: COMPRAR " + cantidad + " " + producto);
}

/**
 * Vende un producto al mercado
 * 
 * @param producto Nombre del producto a vender
 * @param cantidad Cantidad a vender
 * @param mensaje Mensaje opcional para el comprador
 * @throws InventarioInsuficienteException Si no hay suficiente producto
 */
public void vender(String producto, int cantidad, String mensaje) 
        throws InventarioInsuficienteException {
    
    // TODO:
    // 1. Obtener cantidad en inventario
    //    int disponible = estado.getInventario().getOrDefault(producto, 0);
    //
    // 2. Validar disponibilidad
    //    if (disponible < cantidad) {
    //        throw new InventarioInsuficienteException(producto, cantidad, disponible);
    //    }
    //
    // 3. Crear objeto Orden
    //    Orden orden = new Orden(
    //        generarClOrdId(),
    //        "SELL",
    //        producto,
    //        cantidad,
    //        mensaje
    //    );
    //
    // 4. Enviar al servidor
    //    conector.enviarOrden(orden);
    //
    // 5. Imprimir confirmación
    //    System.out.println("📤 Orden enviada: VENDER " + cantidad + " " + producto);
}

/**
 * Genera un ID único para órdenes
 */
private String generarClOrdId() {
    return "ORD-" + System.currentTimeMillis();
}
```

#### Criterios de Éxito
- [ ] Validaciones locales antes de enviar al servidor
- [ ] Excepciones correctas lanzadas
- [ ] Mensajes claros de confirmación
- [ ] No crashea si falta precio

---

### ✅ Tarea 3: ClienteBolsa - Método producir() 
**Prioridad:** 🔴🔴🔴🔴 ALTA  
**Tiempo estimado:** 2-3 horas  
**Archivo:** `src/main/java/tech/hellsoft/trading/ClienteBolsa.java`  
**⚠️ Requiere:** Colaboración con Persona 2 (CalculadoraProduccion)

#### Descripción
Implementar el método para producir productos (básico y premium).

#### Implementación

```java
/**
 * Produce unidades de un producto
 * 
 * @param producto Nombre del producto a producir
 * @param premium True para producción premium, false para básica
 * @throws ProductoNoAutorizadoException Si no puedes producir ese producto
 * @throws RecetaNoEncontradaException Si la receta no existe
 * @throws IngredientesInsuficientesException Si faltan ingredientes (premium)
 */
public void producir(String producto, boolean premium) 
        throws ProductoNoAutorizadoException, 
               RecetaNoEncontradaException, 
               IngredientesInsuficientesException {
    
    // TODO:
    // 1. Validar autorización
    //    if (!estado.getProductosAutorizados().contains(producto)) {
    //        throw new ProductoNoAutorizadoException(producto, estado.getProductosAutorizados());
    //    }
    //
    // 2. Obtener receta
    //    Receta receta = estado.getRecetas().get(producto);
    //    if (receta == null) {
    //        throw new RecetaNoEncontradaException(producto);
    //    }
    //
    // 3. Si es premium: validar y consumir ingredientes
    //    if (premium) {
    //        if (!RecetaValidator.puedeProducir(receta, estado.getInventario())) {
    //            throw new IngredientesInsuficientesException(receta, estado.getInventario());
    //        }
    //        RecetaValidator.consumirIngredientes(receta, estado.getInventario());
    //        System.out.println("🔧 Consumiendo ingredientes...");
    //    }
    //
    // 4. Calcular unidades (COLABORAR CON PERSONA 2)
    //    int unidades = CalculadoraProduccion.calcularUnidades(estado.getRol());
    //    if (premium) {
    //        unidades = CalculadoraProduccion.aplicarBonusPremium(unidades, receta.getPremiumBonus());
    //    }
    //
    // 5. Actualizar inventario local
    //    int actual = estado.getInventario().getOrDefault(producto, 0);
    //    estado.getInventario().put(producto, actual + unidades);
    //
    // 6. Notificar al servidor
    //    conector.enviarProduccion(producto, unidades);
    //
    // 7. Imprimir resultado
    //    System.out.println("✅ Producidas " + unidades + " unidades de " + producto);
    //    if (premium) {
    //        System.out.println("   (premium +30%)");
    //    }
}
```

#### Criterios de Éxito
- [ ] Valida todas las precondiciones
- [ ] Integra con CalculadoraProduccion
- [ ] Integra con RecetaValidator
- [ ] Actualiza inventario correctamente
- [ ] Notifica al servidor

---

### ✅ Tarea 4: ConsolaInteractiva - Implementar 15 Comandos
**Prioridad:** 🟡🟡🟡 ALTA  
**Tiempo estimado:** 6-8 horas  
**Archivo:** `src/main/java/tech/hellsoft/trading/ConsolaInteractiva.java`

#### Descripción
Crear una consola interactiva que permita al usuario controlar el bot mediante comandos de texto.

#### Estructura Base

```java
package tech.hellsoft.trading;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class ConsolaInteractiva {
    
    private ClienteBolsa cliente;
    private ConectorBolsa conector;
    private Scanner scanner;
    private boolean ejecutando;
    private Map<String, OfferMessage> ofertasPendientes;
    
    public ConsolaInteractiva(ClienteBolsa cliente, ConectorBolsa conector) {
        this.cliente = cliente;
        this.conector = conector;
        this.scanner = new Scanner(System.in);
        this.ejecutando = false;
        this.ofertasPendientes = new HashMap<>();
    }
    
    /**
     * Inicia el loop principal de la consola
     */
    public void iniciar() {
        ejecutando = true;
        imprimirBienvenida();
        
        while (ejecutando) {
            System.out.print("\n🥑 > ");
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                continue;
            }
            
            procesarComando(input);
        }
    }
    
    /**
     * Procesa un comando ingresado por el usuario
     */
    private void procesarComando(String input) {
        String[] partes = input.split("\\s+");
        String comando = partes[0].toLowerCase();
        
        try {
            switch (comando) {
                case "login":
                    comandoLogin();
                    break;
                case "status":
                    comandoStatus();
                    break;
                case "inventario":
                    comandoInventario();
                    break;
                case "precios":
                    comandoPrecios();
                    break;
                case "comprar":
                    comandoComprar(partes);
                    break;
                case "vender":
                    comandoVender(partes);
                    break;
                case "producir":
                    comandoProducir(partes);
                    break;
                case "ofertas":
                    comandoOfertas();
                    break;
                case "aceptar":
                    comandoAceptar(partes);
                    break;
                case "rechazar":
                    comandoRechazar(partes);
                    break;
                case "snapshot":
                    comandoSnapshot(partes);
                    break;
                case "resync":
                    comandoResync();
                    break;
                case "ayuda":
                case "help":
                    comandoAyuda();
                    break;
                case "exit":
                case "salir":
                    comandoExit();
                    break;
                default:
                    System.out.println("❌ Comando desconocido: " + comando);
                    System.out.println("💡 Escribe 'ayuda' para ver los comandos disponibles");
            }
        } catch (Exception e) {
            manejarError(e);
        }
    }
    
    // ========== IMPLEMENTACIÓN DE COMANDOS ==========
    
    private void comandoLogin() {
        // TODO: Conectar y hacer login
        // 1. Leer config con ConfigLoader
        // 2. conector.conectar(config.host, config.puerto)
        // 3. conector.login(config.apiKey, cliente)
    }
    
    private void comandoStatus() {
        // TODO: Mostrar estado actual
        // - Saldo
        // - P&L
        // - Resumen de inventario (X productos, valor total)
    }
    
    private void comandoInventario() {
        // TODO: Listar inventario completo
        // Para cada producto:
        //   - Nombre
        //   - Cantidad
        //   - Precio unitario actual
        //   - Valor total
    }
    
    private void comandoPrecios() {
        // TODO: Listar precios actuales de mercado
        // Para cada producto con precio:
        //   - Nombre
        //   - Best Bid
        //   - Best Ask
        //   - Mid Price
    }
    
    private void comandoComprar(String[] partes) {
        // TODO: Validar argumentos
        // if (partes.length < 3) {
        //     System.out.println("Uso: comprar <producto> <cantidad> [mensaje]");
        //     return;
        // }
        // String producto = partes[1];
        // int cantidad = Integer.parseInt(partes[2]);
        // String mensaje = unirMensaje(partes, 3);
        // cliente.comprar(producto, cantidad, mensaje);
    }
    
    private void comandoVender(String[] partes) {
        // TODO: Similar a comprar
    }
    
    private void comandoProducir(String[] partes) {
        // TODO: Validar argumentos
        // if (partes.length < 3) {
        //     System.out.println("Uso: producir <producto> <basico|premium>");
        //     return;
        // }
        // String producto = partes[1];
        // boolean premium = partes[2].equalsIgnoreCase("premium");
        // cliente.producir(producto, premium);
    }
    
    private void comandoOfertas() {
        // TODO: Listar ofertas pendientes del Map
    }
    
    private void comandoAceptar(String[] partes) {
        // TODO: Aceptar oferta por ID
        // OfferMessage offer = ofertasPendientes.get(offerId);
        // conector.aceptarOferta(offerId, offer.getQuantity(), offer.getPrice());
    }
    
    private void comandoRechazar(String[] partes) {
        // TODO: Rechazar oferta (opcional, pueden expirar solas)
    }
    
    private void comandoSnapshot(String[] partes) {
        // TODO: 
        // if (partes[1].equals("save")) {
        //     SnapshotManager.guardar(cliente.getEstado(), "snapshots");
        // } else if (partes[1].equals("load")) {
        //     // Listar snapshots y cargar el seleccionado
        // }
    }
    
    private void comandoResync() {
        // TODO: Pedir al servidor eventos perdidos
        // (Esto depende de cómo funcione el SDK)
    }
    
    private void comandoAyuda() {
        System.out.println("\n📚 COMANDOS DISPONIBLES:\n");
        System.out.println("  login                                  - Conectar al servidor");
        System.out.println("  status                                 - Ver saldo y P&L");
        System.out.println("  inventario                             - Ver inventario completo");
        System.out.println("  precios                                - Ver precios de mercado");
        System.out.println("  comprar <producto> <cant> [msg]        - Comprar del mercado");
        System.out.println("  vender <producto> <cant> [msg]         - Vender al mercado");
        System.out.println("  producir <producto> <basico|premium>   - Producir unidades");
        System.out.println("  ofertas                                - Ver ofertas pendientes");
        System.out.println("  aceptar <offerId>                      - Aceptar una oferta");
        System.out.println("  rechazar <offerId> [motivo]            - Rechazar una oferta");
        System.out.println("  snapshot save                          - Guardar estado");
        System.out.println("  snapshot load                          - Cargar estado previo");
        System.out.println("  resync                                 - Sincronizar eventos");
        System.out.println("  ayuda / help                           - Mostrar esta ayuda");
        System.out.println("  exit / salir                           - Salir del programa");
    }
    
    private void comandoExit() {
        System.out.println("\n👋 Cerrando cliente...");
        System.out.println("💾 Guardando snapshot final...");
        try {
            SnapshotManager.guardar(cliente.getEstado(), "snapshots");
            double pl = cliente.getEstado().calcularPL();
            System.out.println("✅ ¡Hasta luego! Tu P&L final fue: " + String.format("%.2f%%", pl));
        } catch (Exception e) {
            System.err.println("⚠️ No se pudo guardar el snapshot: " + e.getMessage());
        }
        ejecutando = false;
    }
    
    // ========== UTILIDADES ==========
    
    private void manejarError(Exception e) {
        if (e instanceof SaldoInsuficienteException) {
            SaldoInsuficienteException ex = (SaldoInsuficienteException) e;
            System.err.println("❌ Saldo insuficiente");
            System.err.println("   Necesitas: $" + ex.getCostoRequerido());
            System.err.println("   Tienes: $" + ex.getSaldoActual());
        } else if (e instanceof InventarioInsuficienteException) {
            InventarioInsuficienteException ex = (InventarioInsuficienteException) e;
            System.err.println("❌ Inventario insuficiente");
            System.err.println("   Necesitas: " + ex.getRequerido() + " " + ex.getProducto());
            System.err.println("   Tienes: " + ex.getDisponible());
        } else if (e instanceof ProductoNoAutorizadoException) {
            ProductoNoAutorizadoException ex = (ProductoNoAutorizadoException) e;
            System.err.println("❌ No puedes producir " + ex.getProducto());
            System.err.println("   Solo puedes: " + ex.getProductosPermitidos());
        } else {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private String unirMensaje(String[] partes, int desde) {
        if (partes.length <= desde) {
            return "";
        }
        return String.join(" ", java.util.Arrays.copyOfRange(partes, desde, partes.length));
    }
    
    private void imprimirBienvenida() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  🥑 Bolsa Interestelar de Aguacates Andorianos             ║");
        System.out.println("║  Cliente de Trading - Consola Interactiva                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("\n💡 Escribe 'ayuda' para ver los comandos disponibles");
        System.out.println("💡 Comienza con 'login' para conectarte al servidor\n");
    }
}
```

#### Criterios de Éxito
- [ ] Los 15 comandos están implementados
- [ ] Validación de argumentos en cada comando
- [ ] Manejo de excepciones con mensajes claros
- [ ] Ayuda descriptiva para cada comando
- [ ] Mensajes de confirmación/error apropiados

---

### ✅ Tarea 5: Testing y Validación
**Prioridad:** 🟢🟢 MEDIA  
**Tiempo estimado:** 2-3 horas

#### Tests a Crear
1. **ClienteBolsaIntegrationTest.java**
   - Test de flujo completo: login → producir → vender
   - Test de manejo de errores

2. **ConsolaInteractivaTest.java** (opcional)
   - Test de parsing de comandos
   - Test de validación de argumentos

---

### ✅ Tarea 6: JavaDoc
**Prioridad:** 🟢 BAJA  
**Tiempo estimado:** 1-2 horas

Agregar JavaDoc a:
- Todos los métodos públicos de ClienteBolsa
- Todos los métodos de ConsolaInteractiva
- Describir parámetros, returns, y excepciones

---

## 📊 Resumen de Tu Trabajo

| Tarea | Horas | Prioridad | Estado |
|-------|-------|-----------|--------|
| ClienteBolsa - Callbacks | 4-5h | 🔴 MÁXIMA | ⬜ |
| ClienteBolsa - comprar/vender | 2-3h | 🔴 MÁXIMA | ⬜ |
| ClienteBolsa - producir | 2-3h | 🔴 ALTA | ⬜ |
| ConsolaInteractiva | 6-8h | 🟡 ALTA | ⬜ |
| Testing | 2-3h | 🟢 MEDIA | ⬜ |
| JavaDoc | 1-2h | 🟢 BAJA | ⬜ |
| **TOTAL** | **17-24h** | - | - |

---

## 🤝 Coordinación con el Equipo

### Dependencias
- **Necesitas de Persona 2:**
  - `EstadoCliente.java` - Para poder instanciarlo y usarlo
  - `CalculadoraProduccion.java` - Para el método producir()

- **Necesitas de Persona 3:**
  - Todas las excepciones implementadas
  - `RecetaValidator.java` - Para el método producir()
  - `SnapshotManager.java` - Para comandos snapshot

### Te necesitan
- **Persona 2 necesita:** Que definas bien la interfaz de EstadoCliente
- **Persona 3 necesita:** Que definas qué excepciones vas a lanzar

### Reuniones Sugeridas
1. **Día 1 - Mañana:** Definir interfaces juntos (2 horas)
2. **Día 1 - Tarde:** Checkpoint de avance
3. **Día 2 - Mañana:** Integración de componentes
4. **Día 2 - Tarde:** Testing conjunto

---

## 💡 Tips y Mejores Prácticas

1. **Empieza por los callbacks simples:**
   - onLoginOk (solo inicializar)
   - onTicker (solo actualizar map)
   - onError (solo imprimir)

2. **Luego implementa comprar() y vender():**
   - Son más simples que producir()
   - Te permite probar el flujo básico

3. **Deja producir() para cuando Persona 2 tenga CalculadoraProduccion**

4. **Para la consola:**
   - Implementa primero el loop principal
   - Luego agrega comandos uno por uno
   - Prueba cada comando antes de seguir

5. **Testing:**
   - Usa mocks para el ConectorBolsa
   - Crea EstadoCliente de prueba con datos conocidos
   - Verifica que las excepciones se lancen correctamente

---

## 🆘 Si Te Atoras

1. **Problema con callbacks:**
   - Revisa la GUIA.md sección "Interface EventListener"
   - Pregunta al profesor sobre el SDK

2. **Problema con órdenes:**
   - Verifica qué campos necesita el objeto Orden
   - Revisa el SDK o pregunta al equipo

3. **Problema con la consola:**
   - Implementa primero un comando simple (ej: status)
   - Copia el patrón para los demás

4. **No compila:**
   - Verifica que las otras personas hayan pusheado sus clases
   - Usa stubs temporales si es necesario

---

## ✅ Checklist Final

Antes de considerar tu trabajo terminado:

- [ ] ClienteBolsa implementa EventListener
- [ ] Los 6 callbacks están completos
- [ ] comprar() y vender() funcionan correctamente
- [ ] producir() está integrado con otros componentes
- [ ] La consola acepta los 15 comandos
- [ ] Todas las excepciones se manejan apropiadamente
- [ ] Los mensajes al usuario son claros y útiles
- [ ] El código compila sin errores
- [ ] Hay al menos 2 tests de integración
- [ ] JavaDoc en métodos públicos
- [ ] Code review por otro miembro del equipo
- [ ] Merge a main exitoso

---

**¡Éxito en tu desarrollo! 🚀**

Si tienes dudas, consulta con el equipo o revisa la GUIA.md.

