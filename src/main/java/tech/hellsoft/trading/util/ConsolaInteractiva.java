package tech.hellsoft.trading.util;

import java.util.Map;
import java.util.Scanner;
import tech.hellsoft.trading.ClienteBolsa;
import tech.hellsoft.trading.ConectorBolsa;
import tech.hellsoft.trading.EstadoCliente;
import tech.hellsoft.trading.Main;
import tech.hellsoft.trading.dto.server.OfferMessage;
import tech.hellsoft.trading.enums.Product;
import tech.hellsoft.trading.exception.ConexionFallidaException;
import tech.hellsoft.trading.exception.produccion.IngredientesInsuficientesException;
import tech.hellsoft.trading.exception.produccion.RecetaNoEncontradaException;
import tech.hellsoft.trading.exception.trading.InventarioInsuficienteException;
import tech.hellsoft.trading.exception.trading.ProductoNoAutorizadoException;
import tech.hellsoft.trading.exception.trading.SaldoInsuficienteException;

public class ConsolaInteractiva {

    private final ClienteBolsa cliente;
    private final ConectorBolsa conector;
    private final Scanner scanner;
    private boolean ejecutando;
    private boolean listenerActivo;

    public ConsolaInteractiva(ClienteBolsa cliente, ConectorBolsa conector) {
        this.cliente = cliente;
        this.conector = conector;
        this.scanner = new Scanner(System.in);
        this.ejecutando = true;
        this.listenerActivo = false;
    }

    // Loop principal de la consola interactiva
    public void iniciar() {
         printWelcomeBanner();

        while (ejecutando) {
            if(listenerActivo) {
                // Modo listener activo - esperar comando para salir
                mostrarModoListener();

                if (!scanner.hasNextLine()) { // seguridad cuando lees input (lo tenia el profe originalmente)
                    break;
                }

                String input = scanner.nextLine().trim(); // trim para eliminar espacios iniciales y finales

                if ("salir".equalsIgnoreCase(input) || "exit".equalsIgnoreCase(input) || "menu".equalsIgnoreCase(input)) {
                    detenerListener();
                }
                continue;
            }

            printMenu();
            System.out.print("┌─[Comando]─► ");

            if (!scanner.hasNextLine()) { // seguridad cuando lees input (lo tenia el profe originalmente)
                break;
            }

            String input = scanner.nextLine().trim(); // trim para eliminar espacios iniciales y finales

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split("\\s+"); // divide el input por espacios
            String command = parts[0].toLowerCase();
            handleCommand(command, parts);
        }
        scanner.close();
        printGoodbyeBanner();
    }

    private void mostrarModoListener() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║           🎧 MODO LISTENER ACTIVO                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("  ▶️  Escuchando eventos del mercado en tiempo real...");
        System.out.println("  💡 Escribe 'menu' o 'salir' para volver al menú principal");
        System.out.print("\n┌─[Listener]─► ");
    }

    private void detenerListener() {
        cliente.desactivarListener();
        listenerActivo = false;
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║           🔇 DESACTIVANDO LISTENER                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("  ⏹️  Deteniendo escucha de eventos...");
        System.out.println("  ✅ Listener desactivado - Regresando al menú principal");
        System.out.println();
    }

    private void printWelcomeBanner() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                            ║");
        System.out.println("║            🚀 SPACIAL TRADING BOT 🚀                       ║");
        System.out.println("║                                                            ║");
        System.out.println("║            Bienvenido al Sistema de Trading                ║");
        System.out.println("║                                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private void printGoodbyeBanner() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                            ║");
        System.out.println("║              👋 Cerrando Trading Bot...                   ║");
        System.out.println("║                                                            ║");
        System.out.println("║              ✅ ¡Gracias por operar con nosotros!         ║");
        System.out.println("║                                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                   📋 COMANDOS DISPONIBLES                                                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("┌──────────────────────────────────────────────────────┬─────────────────────────────────────────────────────────────────────┐");
        System.out.println("│  ⚙️  CONFIGURACIÓN                                   │  📊 INFORMACIÓN                                                     │");
        System.out.println("├──────────────────────────────────────────────────────┼─────────────────────────────────────────────────────────────────────┤");
        System.out.println("│  • listener       → Iniciar listener                 │  • status       → Estado y P&L    • inventario  → Productos         │");
        System.out.println("│  • listar         → Mostrar snapshots disponibles    │  • precios      → Precios         • ofertas     → Ofertas           │");
        System.out.println("│  • guardar <arch> → Guardar estado                   │                                                                     │");
        System.out.println("│  • cargar <arch>  → Cargar estado                    │                                                                     │");
        System.out.println("└──────────────────────────────────────────────────────┴─────────────────────────────────────────────────────────────────────┘");
        System.out.println();
        System.out.println("┌─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│  💼 TRADING Y PRODUCCIÓN                                                                                                    │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│  • comprar <producto> <cant> [precio] → Comprar     • vender <producto> <cant> [precio] → Vender                            │");
        System.out.println("│  • producir <producto>                → Producir    • aceptar <offerId>                 → Aceptar oferta                    │");
        System.out.println("│  • auto                               → Auto prod.  • autos                             → Parar auto                        │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println();
        System.out.println("┌─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│  ℹ️  AYUDA: ayuda/help → Guía completa  |  exit/quit/salir → Salir                                                          │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
    }

    private void handleCommand(String command, String[] parts){
            switch (command) {
                case "listener":
                    handleListener();
                    break;
                case "listar":
                    handleListar();
                    break;
                case "guardar":
                    hanldeGuardar(parts);
                    break;
                case "cargar":
                    handleCargar(parts);
                    break;
                case "status" :
                    handleStatus();
                    break;
                case "inventario" :
                    handleInventario();
                    break;

                case "precios" :
                    handlePrecios();
                    break;

                case "comprar" :
                    handleComprar(parts);
                    break;

                case "vender" :
                    handleVender(parts);
                    break;

                case "producir" :
                    handleProducir(parts);
                    break;

                case "ofertas" :
                    handleOfertas();
                    break;

                case "aceptar" :
                    handleAceptarOferta(parts);
                    break;
                case "auto"  :
                    cliente.iniciarAutoProductor();
                    break;
                case "autos"  :
                    cliente.pararAutoProductor();
                    break;
                case "resync"  :
                    handleResync();
                    break;
                case "ayuda" :
                case "help" :
                    printHelp();
                    break;
                case "exit" :
                case "quit" :
                case "salir" :
                    ejecutando = false;
                    break;

                default :
                    System.out.println("❌ Comando desconocido: " + command);
                    System.out.println("💡 Escribe 'ayuda' para ver todos los comandos");
            }
    }

    // ========== COMANDOS DE CONFIGURACION ==========
    private void handleListener() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              🎧 ACTIVANDO LISTENER DE MERCADO              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  ▶️  Comenzando a escuchar eventos del mercado...");
        System.out.println("  ✅ Listener activado correctamente");
        System.out.println("  💡 Podrás escribir 'menu' o 'salir' para volver al menú");
        System.out.println();
        cliente.activarListener();
        listenerActivo= true;
    }

    private void handleListar() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║               📂 SNAPSHOTS DE ESTADO DISPONIBLES           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        SnapshotManager.listarSnapshots();
        System.out.println();
    }

    private void hanldeGuardar(String[] parts) {
        if(parts.length < 2 || parts[1] == null) {
            System.out.println("❌ Uso: guardar <nombre_archivo>");
            return;
        }
        System.out.println("\n💾 Guardando estado del cliente...");
        SnapshotManager.guardarEstado(cliente.getEstado(), parts[1]);
        System.out.println("✅ Estado guardado exitosamente en: " + parts[1]);
        System.out.println();
    }

    private void handleCargar(String[] parts) {
        if(parts.length < 2 || parts[1] == null) {
            System.out.println("❌ Uso: cargar <nombre_archivo>");
            return;
        }
        System.out.println("\n📂 Cargando estado del cliente...");
        cliente.setEstado(SnapshotManager.cargarEstado(parts[1]));
        System.out.println("✅ Estado cargado exitosamente desde: " + parts[1]);
        System.out.println();
    }
    // ========== COMANDOS DE INFORMACIÓN ==========

    private void handleStatus() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                     📊 ESTADO ACTUAL                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        EstadoCliente estado = cliente.getEstado();
        double saldo = estado.getSaldo();
        double saldoInicial = estado.getSaldoInicial();

        double valorInventario = cliente.getEstado().calcularValorInventario();

        double patrimonioNeto = saldo + valorInventario;
        double pl = cliente.getEstado().calcularPL();

        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.printf("│  💰 Saldo:                                  $%,14.2f │%n", saldo);
        System.out.printf("│  📦 Valor inventario:                       $%,14.2f │%n", valorInventario);
        System.out.println("├─────────────────────────────────────────────────────────────┤");
        System.out.printf("│  💎 Patrimonio neto:                        $%,14.2f │%n", patrimonioNeto);
        String pnlIcon = pl >= 0 ? "📈" : "📉";
        System.out.printf("│  %s P&L:                                       %+7.2f%% │%n", pnlIcon, pl);
        System.out.println("└─────────────────────────────────────────────────────────────┘");
        System.out.println();
    }

    private void handleInventario() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                      📦 INVENTARIO                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        EstadoCliente estado = cliente.getEstado();
        Map<Product, Integer> inventario = estado.getInventario();

        if (inventario.isEmpty()) {
            System.out.println("\n   ⚠️  Inventario vacío - ¡Comienza a operar!");
            System.out.println();
            return;
        }

        System.out.println("\n┌──────────────────────┬──────────────┬──────────────────────┐");
        System.out.println("│      PRODUCTO        │   CANTIDAD   │    VALOR ESTIMADO    │");
        System.out.println("├──────────────────────┼──────────────┼──────────────────────┤");

        int totalUnidades = 0;
        double valorTotal = 0.0;

        for (Map.Entry<Product, Integer> entry : inventario.entrySet()) {
            Product producto = entry.getKey();
            int cantidad = entry.getValue();

            double precio = estado.getPreciosActuales().getOrDefault(producto, 0.0);
            double valor = cantidad * precio;

            System.out.printf("│ %-20s │ %,12d │ $%,18.2f │%n",
                              producto, cantidad, valor);

            totalUnidades += cantidad;
            valorTotal += valor;
        }
        System.out.println("├──────────────────────┴──────────────┴──────────────────────┤");
        System.out.printf("│  Total unidades: %,10d                                │%n", totalUnidades);
        System.out.printf("│  Valor total inventario: $%,18.2f                 │%n", valorTotal);
        System.out.println("└─────────────────────────────────────────────────────────────┘");
        System.out.println();
    }

    private void handlePrecios() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   💹 PRECIOS DE MERCADO                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        EstadoCliente estado = cliente.getEstado();
        Map<Product, Double> precios = estado.getPreciosActuales();

        if (precios.isEmpty()) {
            System.out.println("\n   ⏳ Esperando información del mercado...");
            System.out.println();
            return;
        }

        System.out.println("\n┌──────────────────────────────┬────────────────────────────┐");
        System.out.println("│          PRODUCTO            │       PRECIO ACTUAL        │");
        System.out.println("├──────────────────────────────┼────────────────────────────┤");

        for (Map.Entry<Product, Double> entry : precios.entrySet()) {
            Product producto = entry.getKey();
            double mid = entry.getValue();
            System.out.printf("│ %-28s │ $%,24.2f │%n", producto, mid);
        }
        System.out.println("└──────────────────────────────┴────────────────────────────┘");
        System.out.println();
    }

    private void handleOfertas() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   📬 OFERTAS PENDIENTES                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        EstadoCliente estado = cliente.getEstado();
        Map<String, OfferMessage> ofertas = estado.getOfertasPendientes();

        if (ofertas.isEmpty()) {
            System.out.println("\n   ℹ️  No hay ofertas pendientes");
            System.out.println();
            return;
        }

        int contador = 1;
        for (Map.Entry<String, OfferMessage> entry : ofertas.entrySet()) {
            OfferMessage oferta = entry.getValue();
            System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
            System.out.printf("│  📌 Oferta #%d                                               │%n", contador++);
            System.out.println("├─────────────────────────────────────────────────────────────┤");
            System.out.printf("│  🆔 ID: %-52s │%n", oferta.getOfferId());
            System.out.printf("│  👤 Comprador: %-44s │%n", oferta.getBuyer());
            System.out.printf("│  📦 Producto: %-45s │%n", oferta.getProduct());
            System.out.printf("│  🔢 Cantidad: %-45d │%n", oferta.getQuantityRequested());
            System.out.printf("│  💰 Precio máximo: $%-38.2f │%n", oferta.getMaxPrice());
            System.out.println("└─────────────────────────────────────────────────────────────┘");
        }

        System.out.println("\n💡 Usa 'aceptar <offerId>' para aceptar una oferta");
        System.out.println();
    }

    private void printHelp() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    📚 GUÍA COMPLETA                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  📊 COMANDOS DE INFORMACIÓN                                 │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");
        System.out.println("  status              - Muestra saldo, inventario, P&L%");
        System.out.println("  inventario          - Lista todos tus productos");
        System.out.println("  precios             - Precios actuales de mercado");
        System.out.println("  ofertas             - Ver ofertas de otros traders");
        System.out.println();
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  💼 COMANDOS DE TRADING                                     │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");
        System.out.println("  comprar <producto> <cantidad> [precio]");
        System.out.println("    Ejemplo: comprar PALTA-OIL 10");
        System.out.println("    Si no especificas precio, se usa el precio de mercado");
        System.out.println();
        System.out.println("  vender <producto> <cantidad> [precio]");
        System.out.println("    Ejemplo: vender FOSFO 5");
        System.out.println("    Si no especificas precio, se usa el precio de mercado");
        System.out.println();
        System.out.println("  aceptar <offerId>");
        System.out.println("    Ejemplo: aceptar OFFER-123");
        System.out.println("    Acepta una oferta específica de compra");
        System.out.println();
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  🏭 COMANDOS DE PRODUCCIÓN                                  │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");
        System.out.println("  producir <producto>");
        System.out.println("    Ejemplo: producir PALTA-OIL");
        System.out.println("    Ejemplo: producir GUACA");
        System.out.println("    Productos disponibles: PALTA-OIL, GUACA, SEBO");
        System.out.println();
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  ⚙️  COMANDOS DE CONFIGURACIÓN                              │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");
        System.out.println("  listener            - Activa el listener de mercado");
        System.out.println("  guardar <archivo>   - Guarda el estado actual");
        System.out.println("    Ejemplo: guardar estado.bin");
        System.out.println();
        System.out.println("  cargar <archivo>    - Carga un estado guardado");
        System.out.println("    Ejemplo: cargar estado.bin");
        System.out.println();
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  ℹ️  OTROS COMANDOS                                          │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");
        System.out.println("  ayuda / help        - Muestra esta guía");
        System.out.println("  exit / quit / salir - Cierra el programa");
        System.out.println();
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("💡 TIP: Lee AGENTS.md para más información sobre estrategias");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    private void handleResync(){
        System.out.println();
        System.out.println("🔄 Reconectando con el servidor...");
        try {
            Main.login(conector);
        } catch (ConexionFallidaException e) {
            throw new RuntimeException(e);
        }
        System.out.println("✅ Reconexión exitosa");
    }
    // ========== COMANDOS DE ACCIÓN ==========

    private void handleComprar(String[] parts) {
        if (parts.length < 3) {
            System.out.println("❌ Uso: comprar <producto> <cantidad> [precio]");
            System.out.println("   Ejemplo: comprar PALTA-OIL 10");
            System.out.println("   Ejemplo: comprar FOSFO 5 100.50");
            return;
        }

        try {
            Product producto = Product.valueOf(parts[1]);
            int cantidad = Integer.parseInt(parts[2]);

            String mensaje = null;
            if (parts.length > 3) {
                mensaje = parts[3];
            }

            System.out.println("\n🛒 Procesando compra...");
            System.out.printf("   Producto: %s%n", producto);
            System.out.printf("   Cantidad: %d%n", cantidad);

            cliente.comprar(producto, cantidad, mensaje);

            System.out.println("✅ Orden de compra enviada exitosamente");
            System.out.println();

        } catch (NumberFormatException e) {
            System.out.println("❌ Cantidad inválida: debe ser un número entero");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Producto no válido: " + parts[1]);
            System.out.println("💡 Usa el comando 'precios' para ver productos disponibles");
        } catch (SaldoInsuficienteException e) {
            System.out.printf("❌ Saldo insuficiente. Tienes: $%,.2f | Necesitas: $%,.2f%n",
                    e.getSaldoActual(), e.getCostoRequerido());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void handleVender(String[] parts) {
        if (parts.length < 3) {
            System.out.println("❌ Uso: vender <producto> <cantidad> [precio]");
            System.out.println("   Ejemplo: vender PALTA-OIL 10");
            System.out.println("   Ejemplo: vender FOSFO 5 120.00");
            return;
        }

        try {
            Product producto = Product.valueOf(parts[1]);
            int cantidad = Integer.parseInt(parts[2]);

            // revisar esto porque debe ser cooherente con el marketlimit etc
            String mensaje = null;
            if (parts.length > 3) {
                mensaje = parts[3];
            }

            System.out.println("\n💰 Procesando venta...");
            System.out.printf("   Producto: %s%n", producto);
            System.out.printf("   Cantidad: %d%n", cantidad);

            cliente.vender(producto, cantidad, mensaje);

            System.out.println("✅ Orden de venta enviada exitosamente");
            System.out.println();

        } catch (NumberFormatException e) {
            System.out.println("❌ Cantidad inválida: debe ser un número entero");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Producto no válido: " + parts[1]);
            System.out.println("💡 Usa el comando 'inventario' para ver tus productos");
        } catch (InventarioInsuficienteException e) {
            System.out.printf("❌ Inventario insuficiente de %s. Tienes: %d | Necesitas: %d%n",
                    e.getProducto(), e.getCantidadDisponible(), e.getCantidadRequerida());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void handleProducir(String[] parts) {
        if (parts.length < 2) {
            System.out.println("❌ Uso: producir <producto>");
            System.out.println("   Ejemplo: producir PALTA-OIL");
            System.out.println("   Ejemplo: producir GUACA");
            System.out.println("   Productos disponibles: PALTA-OIL, GUACA, SEBO");
            return;
        }

        try {
            Product producto = Product.valueOf(parts[1]);
            if(producto != Product.GUACA && producto != Product.PALTA_OIL && producto != Product.SEBO) {
                System.out.println("❌ Solo se pueden producir GUACA, PALTA-OIL o SEBO");
                return;
            }
            boolean premium = (producto == Product.GUACA || producto == Product.SEBO);

            System.out.println("\n🏭 Iniciando producción...");
            System.out.printf("   Producto: %s%n", producto);
            System.out.printf("   Tipo: %s%n", premium ? "PREMIUM" : "BÁSICO");

            cliente.producir(producto, premium);

            System.out.println("✅ Producto fabricado exitosamente");
            System.out.println();

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Producto no válido: " + parts[1]);
            System.out.println("💡 Productos disponibles: PALTA-OIL, GUACA, SEBO");
        } catch (ProductoNoAutorizadoException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (RecetaNoEncontradaException e) {
            System.out.println("❌ Receta no encontrada: " + e.getMessage());
        } catch (IngredientesInsuficientesException e) {
            System.out.println("❌ Ingredientes insuficientes:");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void handleAceptarOferta(String[] parts) {
        if (parts.length < 2) {
            System.out.println("❌ Uso: aceptar <offerId>");
            System.out.println("   Ejemplo: aceptar OFFER-123");
            System.out.println("💡 Usa el comando 'ofertas' para ver las ofertas disponibles");
            return;
        }
        String offerId = parts[1];
        System.out.println("\n✅ Procesando aceptación de oferta...");
        System.out.printf("   Oferta ID: %s%n", offerId);
        cliente.aceptarOferta(offerId);
        System.out.println("✅ Oferta aceptada exitosamente");
        System.out.println();
    }
}


