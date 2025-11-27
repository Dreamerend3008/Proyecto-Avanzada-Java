package tech.hellsoft.trading.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import tech.hellsoft.trading.ClienteBolsa;
import tech.hellsoft.trading.ConectorBolsa;
import tech.hellsoft.trading.EstadoCliente;
import tech.hellsoft.trading.dto.server.OfferMessage;
import tech.hellsoft.trading.enums.Product;
import tech.hellsoft.trading.exception.produccion.IngredientesInsuficientesException;
import tech.hellsoft.trading.exception.produccion.RecetaNoEncontradaException;
import tech.hellsoft.trading.exception.trading.InventarioInsuficienteException;
import tech.hellsoft.trading.exception.trading.ProductoNoAutorizadoException;
import tech.hellsoft.trading.exception.trading.SaldoInsuficienteException;
import tech.hellsoft.trading.model.Receta;

/**
 * Consola interactiva para recibir comandos del usuario.
 * Implementa un REPL (Read-Eval-Print Loop) para controlar el bot de trading.
 */
public class ConsolaInteractiva {

    private final ClienteBolsa cliente;
    private final ConectorBolsa conector;
    private final Scanner scanner;
    private volatile boolean ejecutando;
    private boolean listenerActivo = false;

    public ConsolaInteractiva(ClienteBolsa cliente, ConectorBolsa conector) {
        this.cliente = cliente;
        this.conector = conector;
        this.scanner = new Scanner(System.in);
        this.ejecutando = true;
    }

    // Loop principal de la consola interactiva
    public void iniciar() {
        while (ejecutando) {
            if(listenerActivo) {
                continue;
            }
            printMenu();
            System.out.print("\n> ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String input = scanner.nextLine().trim();

            // Guard clause - skip empty input
            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split("\\s+");
            String command = parts[0].toLowerCase();

            handleCommand(command, parts);
        }
        scanner.close();
        System.out.println("\n👋 Cerrando Trading Bot...");
        System.out.println("✅ ¡Hasta luego!");
    }

    private void printMenu() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📋 COMANDOS DISPONIBLES:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("===========Configuración del Cliente==========");
        System.out.println("  listener                        - Iniciar el listener de mercado");
        System.out.println("  guardar <nombre del archivo>    - Guardar estado del cliente en binario");
        System.out.println("  cargar  <nombre del archivo>    - Cargar estado del cliente desde binario");
        System.out.println("=============================================");
        System.out.println("===========Comandos de Información===========");
        System.out.println("  status                          - Ver estado actual (saldo, P&L)");
        System.out.println("  inventario                      - Ver productos en inventario");
        System.out.println("  precios                         - Ver precios de mercado");
        System.out.println("  ofertas                         - Ver ofertas pendientes");
        System.out.println("  ayuda                           - Mostrar ayuda completa");
        System.out.println("=============================================");
        System.out.println("===========Comandos de Acción=================");
        System.out.println("  comprar <producto> <cantidad> [PRECIO] - Si no pones precio se toma el valor market");
        System.out.println("  vender <producto> <cantidad> [PRECIO]  - Si no pones precio se toma el valor market");
        System.out.println("  producir <producto> <basico|premium>");
        System.out.println("  aceptar <offerId>               - Aceptar una oferta");
        System.out.println("=============================================");
        System.out.println("===========Otros Comandos=====================");
        System.out.println("  exit                            - Salir del programa");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    private void handleCommand(String command, String[] parts) {
            switch (command) {
                case "listener":
                    handleListener();
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
        System.out.println("========== INICIAR LISTENER DE MERCADO ==========");
        System.out.println("Comenzando a escuchar eventos del mercado...");
        cliente.activarListener();
        listenerActivo= true;
    }
    private void hanldeGuardar(String[] parts) {
        if(parts[1]== null) {
            System.out.println("❌ Uso: Guardar <nombre del archivo>");
            return;
        }
        SnapshotManager.guardarEstado(cliente.getEstado(), parts[1]);
        System.out.println("✅ Estado guardado en " + parts[1]);
    }
    private void handleCargar(String[] parts) {
        if(parts[1]== null) {
            System.out.println("Uso: Cargar <nombre del archivo>");
            return;
        }
        cliente.setEstado(SnapshotManager.cargarEstado(parts[1]));
    }
    // ========== COMANDOS DE INFORMACIÓN ==========

    private void handleStatus() {
        System.out.println("\n📊 ESTADO ACTUAL");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━");

        EstadoCliente estado = cliente.getEstado();
        double saldo = estado.getSaldo();
        double saldoInicial = estado.getSaldoInicial();

        // Calculate inventory value
        double valorInventario = 0.0;
        for (Map.Entry<Product, Integer> entry : estado.getInventario().entrySet()) {
            Product producto = entry.getKey();
            int cantidad = entry.getValue();
            double precio = estado.getPreciosActuales().getOrDefault(producto, 0.0);
            valorInventario += cantidad * precio;
        }

        double patrimonioNeto = saldo + valorInventario;
        double pnl = saldoInicial > 0 ? ((patrimonioNeto - saldoInicial) / saldoInicial) * 100 : 0.0;

        System.out.printf("💰 Saldo: $%.2f%n", saldo);
        System.out.printf("📦 Valor inventario: $%.2f%n", valorInventario);
        System.out.printf("💎 Patrimonio neto: $%.2f%n", patrimonioNeto);
        System.out.printf("📈 P&L: %+.2f%%%n", pnl);
        System.out.println();
    }

    private void handleInventario() {
        System.out.println("\n📦 INVENTARIO");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        EstadoCliente estado = cliente.getEstado();
        Map<Product, Integer> inventario = estado.getInventario();

        if (inventario.isEmpty()) {
            System.out.println("Vacio");
            System.out.println();
            return;
        }
        int totalUnidades = 0;
        double valorTotal = 0.0;

        for (Map.Entry<Product, Integer> entry : inventario.entrySet()) {
            Product producto = entry.getKey();
            int cantidad = entry.getValue();

            double precio = estado.getPreciosActuales().getOrDefault(producto, 0.0);
            double valor = cantidad * precio;

            System.out.printf(" - %s: %d unidades | Valor estimado: $%.2f%n",
                              producto, cantidad, valor);

            totalUnidades += cantidad;
            valorTotal += valor;
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("Total unidades: %d%n", totalUnidades);
        System.out.printf("Valor total inventario: $%.2f%n", valorTotal);
        System.out.println();
    }

    private void handlePrecios() {
        System.out.println("\n💹 PRECIOS DE MERCADO");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        EstadoCliente estado = cliente.getEstado();
        Map<Product, Double> precios = estado.getPreciosActuales();

        if (precios.isEmpty()) {
            System.out.println("(esperando tickers...)");
            System.out.println();
            return;
        }

        for (Map.Entry<Product, Double> entry : precios.entrySet()) {
            Product producto = entry.getKey();
            double mid = entry.getValue();
            System.out.printf(" - %s: $%.2f%n", producto, mid);
        }
        System.out.println();
    }

    private void handleOfertas() {
        System.out.println("\n📬 OFERTAS PENDIENTES");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        EstadoCliente estado = cliente.getEstado();
        Map<String, OfferMessage> ofertas = estado.getOfertasPendientes();

        if (ofertas.isEmpty()) {
            System.out.println("(sin ofertas pendientes)");
            System.out.println();
            return;
        }

        for (Map.Entry<String, OfferMessage> entry : ofertas.entrySet()) {
            OfferMessage oferta = entry.getValue();
            System.out.printf("ID: %s%n", oferta.getOfferId());
            System.out.printf("  Comprador: %s%n", oferta.getBuyer());
            System.out.printf("  Producto: %s%n", oferta.getProduct());
            System.out.printf("  Cantidad: %d%n", oferta.getQuantityRequested());
            System.out.printf("  Precio máximo: $%.2f%n", oferta.getMaxPrice());
            System.out.println("  ────────────────────────────");
        }

        System.out.println();
        System.out.println("💡 Usa 'aceptar <offerId>' para aceptar una oferta");
    }

    private void printHelp() {
        System.out.println("\n📚 AYUDA COMPLETA - Comandos del Trading Bot");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println();
        System.out.println("INFORMACIÓN:");
        System.out.println("  status              - Muestra saldo, inventario, P&L%");
        System.out.println("  inventario          - Lista todos tus productos");
        System.out.println("  precios             - Precios actuales de mercado");
        System.out.println();
        System.out.println("TRADING:");
        System.out.println("  comprar PALTA-OIL 10 \"mensaje opcional\"");
        System.out.println("  vender FOSFO 5 \"otro mensaje\"");
        System.out.println();
        System.out.println("PRODUCCIÓN:");
        System.out.println("  producir PALTA-OIL basico");
        System.out.println("  producir GUACA premium");
        System.out.println();
        System.out.println("OFERTAS:");
        System.out.println("  ofertas             - Ver ofertas de otros traders");
        System.out.println("  aceptar OFFER-123   - Aceptar una oferta específica");
        System.out.println();
        System.out.println("OTROS:");
        System.out.println("  ayuda               - Muestra esta ayuda");
        System.out.println("  exit                - Salir del programa");
        System.out.println();
        System.out.println("💡 TIP: Lee AGENTS.md para guía de implementación");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    // ========== COMANDOS DE ACCIÓN ==========

    private void handleComprar(String[] parts) {
        if (parts.length < 3) {
            System.out.println("❌ Uso: comprar <producto> <cantidad> [PRECIO]");
            return;
        }

        try {
            Product producto = Product.valueOf(parts[1]);
            int cantidad = Integer.parseInt(parts[2]);

            // si el mensaje viene con algo mas lo mandamos como mensaje de lo contrario null
            String mensaje = null;
            if (parts.length > 3) {
                mensaje = parts[3];
            }
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

    private void handleVender(String[] parts) {
        if (parts.length < 3) {
            System.out.println("❌ Uso: vender <producto> <cantidad> [PRECIO]");
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

            cliente.vender(producto, cantidad, mensaje);

        } catch (NumberFormatException e) {
            System.out.println("❌ Cantidad inválida");
        } catch (InventarioInsuficienteException e) {
            System.out.printf("❌ Inventario insuficiente de %s. Tienes: %d, Necesitas: %d%n",
                    e.getProducto(), e.getCantidadDisponible(), e.getCantidadRequerida());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void handleProducir(String[] parts) {
        if (parts.length < 2) {
            System.out.println("❌ Uso: producir <producto>");
            return;
        }

        try {
            Product producto = Product.valueOf(parts[1]);
            if(producto != Product.GUACA && producto != Product.PALTA_OIL && producto != Product.SEBO) {
                System.out.println("❌ Solo se pueden producir GUACA, PALTA-OIL o SEBO");
                return;
            }
            boolean premium = false;
            if(producto == Product.GUACA || producto == Product.SEBO) premium = true;
            cliente.producir(producto, premium);

        } catch (NumberFormatException e) {
            System.out.println("❌ Cantidad inválida");
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
            return;
        }
        String offerId = parts[1];
        cliente.aceptarOferta(offerId);
    }

    public void detener() {
        ejecutando = false;
    }
}

