package tech.hellsoft.trading;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import tech.hellsoft.trading.config.Configuration;
import tech.hellsoft.trading.dto.server.GlobalPerformanceReportMessage;
import tech.hellsoft.trading.enums.Product;
import tech.hellsoft.trading.exception.produccion.IngredientesInsuficientesException;
import tech.hellsoft.trading.exception.trading.ProductoNoAutorizadoException;
import tech.hellsoft.trading.exception.produccion.RecetaNoEncontradaException;
import tech.hellsoft.trading.exception.trading.InventarioInsuficienteException;
import tech.hellsoft.trading.exception.trading.SaldoInsuficienteException;
import tech.hellsoft.trading.model.Receta;
import tech.hellsoft.trading.util.CalculadoraProduccion;
import tech.hellsoft.trading.util.ConfigLoader;
import tech.hellsoft.trading.dto.server.LoginOKMessage;
import tech.hellsoft.trading.dto.server.ErrorMessage;
import tech.hellsoft.trading.dto.server.FillMessage;
import tech.hellsoft.trading.dto.server.TickerMessage;
import tech.hellsoft.trading.dto.server.OfferMessage;
import tech.hellsoft.trading.dto.server.OrderAckMessage;
import tech.hellsoft.trading.dto.server.InventoryUpdateMessage;
import tech.hellsoft.trading.dto.server.BalanceUpdateMessage;
import tech.hellsoft.trading.dto.server.EventDeltaMessage;
import tech.hellsoft.trading.dto.server.BroadcastNotificationMessage;


/**
 * CLI Trading Bot with interactive menu.
 * Students should implement the TODO methods below to complete the trading bot
 * functionality.
 */
public final class Main {

  private Main() {
  }

  private static boolean running = true;

  static void main(String[] args) {
    try {
      // 1. Load configuration (apiKey, team, host)
      Configuration config = ConfigLoader.load("src/main/resources/config.json");
      printBanner();
      System.out.println("🚀 Starting Trading Bot for team: " + config.team());
      System.out.println();
      // 2. Create ClienteBolsa
      ConectorBolsa connector = new ConectorBolsa();
      ClienteBolsa cliente = new ClienteBolsa(connector);
      connector.addListener(cliente);

      // 3. Connect to server
      System.out.println("🔌 Connecting to: " + config.host());
      connector.conectar(config.host(), config.apiKey());
      System.out.println("✅ Connected! Waiting for login...");
      System.out.println();
      System.out.println("Authenticación exitosa: " + connector.getState());
      // 5. Interactive CLI menu
      runInteractiveCLI(connector, cliente);
    } catch (Exception e) {
      System.err.println("❌ Error: " + e.getMessage());
      if (e.getCause() != null) {
          System.err.println("   Causa: " + e.getCause().getMessage());
      }
      System.exit(1);
    }
  }

  private static void printBanner() {
    System.out.println("╔══════════════════════════════════════════════════════════╗");
    System.out.println("║  🥑 Bolsa Interestelar de Aguacates Andorianos 🥑      ║");
    System.out.println("║  Trading Bot CLI - Java 25 Edition                      ║");
    System.out.println("╚══════════════════════════════════════════════════════════╝");
    System.out.println();
  }

  private static void runInteractiveCLI(ConectorBolsa connector, ClienteBolsa cliente) {
    Scanner scanner = new Scanner(System.in);

    while (running) {
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

      handleCommand(command, parts, connector, cliente);
    }

    scanner.close();
    System.out.println("\n👋 Cerrando Trading Bot...");
    System.out.println("✅ ¡Hasta luego!");
  }

  private static void printMenu() {
    System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    System.out.println("📋 COMANDOS DISPONIBLES:");
    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    System.out.println("  status              - Ver estado actual (saldo, P&L)");
    System.out.println("  inventario          - Ver productos en inventario");
    System.out.println("  precios             - Ver precios de mercado");
    System.out.println("  comprar <producto> <cantidad> [mensaje]");
    System.out.println("  vender <producto> <cantidad> [mensaje]");
    System.out.println("  producir <producto> <cantidad> <basico|premium>");
    System.out.println("  ofertas             - Ver ofertas pendientes");
    System.out.println("  aceptar <offerId>   - Aceptar una oferta");
    System.out.println("  ayuda               - Mostrar ayuda completa");
    System.out.println("  exit                - Salir del programa");
    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
  }

  private static void handleCommand(String command, String[] parts, ConectorBolsa connector, ClienteBolsa cliente) {
    switch (command) {
    case "status" :
      handleStatus(cliente);
      break;

    case "inventario" :
      handleInventario(cliente);
      break;

    case "precios" :
      handlePrecios(cliente);
      break;

    case "comprar" :
      handleComprar(parts, connector, cliente);
      break;

    case "vender" :
      handleVender(parts, connector, cliente);
      break;

    case "producir" :
      handleProducir(parts, connector, cliente);
      break;

    case "ofertas" :
      handleOfertas(cliente);
      break;

    case "aceptar" :
      handleAceptarOferta(parts, connector, cliente);
      break;

    case "ayuda" :
    case "help" :
      printHelp();
      break;

    case "exit" :
    case "quit" :
    case "salir" :
      running = false;
      break;

    default :
      System.out.println("❌ Comando desconocido: " + command);
      System.out.println("💡 Escribe 'ayuda' para ver todos los comandos");
    }
  }

  private static void handleStatus(ClienteBolsa cliente) {
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

  private static void handleInventario(ClienteBolsa cliente) {
    System.out.println("\n📦 INVENTARIO");
    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

    EstadoCliente estado = cliente.getEstado();
    Map<Product, Integer> inventario = estado.getInventario();

    if(inventario.isEmpty()) {
        System.out.println("Vacio");
        System.out.println();
        return;
    }
    int totalUnidades = 0;
    double valorTotal = 0.0;

    for(Map.Entry<Product, Integer> entry : inventario.entrySet()) {
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

  private static void handlePrecios(ClienteBolsa cliente) {
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

          // Calcular bid y ask aproximados (±2%)
          double bid = mid * 0.98;
          double ask = mid * 1.02;

          System.out.printf("%s: $%.2f (bid: $%.2f, ask: $%.2f)%n",
                  producto, mid, bid, ask);
      }

      System.out.println();
  }

  private static void handleComprar(String[] parts, ConectorBolsa connector, ClienteBolsa cliente) {
      if (parts.length < 3) {
          System.out.println("❌ Uso: comprar <producto> <cantidad> [mensaje]");
          return;
      }

      try {
          Product producto = Product.valueOf(parts[1]);
          int cantidad = Integer.parseInt(parts[2]);

          // puede ser MARKET o LIMIT
          String mensaje = parts.length > 3
                  ? String.join(" ", java.util.Arrays.copyOfRange(parts, 3, parts.length))
                  : "MARKET";
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

  private static void handleVender(String[] parts, ConectorBolsa connector, ClienteBolsa cliente) {
      if (parts.length < 3) {
          System.out.println("❌ Uso: vender <producto> <cantidad> [mensaje]");
          return;
      }

      try {
          Product producto = Product.valueOf(parts[1]);
          int cantidad = Integer.parseInt(parts[2]);

          // revisar esto porque debe ser cooherente con el marketlimit etc
          String mensaje = parts.length > 3
                  ? String.join(" ", java.util.Arrays.copyOfRange(parts, 3, parts.length))
                  : "";

          cliente.vender(producto, cantidad, mensaje); // por ahora no se porque estoy mandando un mensaje a vender

      } catch (NumberFormatException e) {
          System.out.println("❌ Cantidad inválida");
      } catch (InventarioInsuficienteException e) {
          System.out.printf("❌ Inventario insuficiente de %s. Tienes: %d, Necesitas: %d%n",
                  e.getProducto(), e.getCantidadDisponible(), e.getCantidadRequerida());
      } catch (Exception e) {
          System.out.println("❌ Error: " + e.getMessage());
      }
  }

  private static void handleProducir(String[] parts, ConectorBolsa connector, ClienteBolsa cliente) {
      if (parts.length < 4) {
          System.out.println("❌ Uso: producir <producto> <cantidad> <basico|premium>");
          return;
      }

      try {
          Product producto = Product.valueOf(parts[1]);

          int cantidadDeseada = Integer.parseInt(parts[2]);
          String tipo = parts[3].toLowerCase();
          boolean premium = tipo.equals("premium");

          if (cantidadDeseada <= 0) {
              System.out.println("❌ La cantidad debe ser mayor que 0");
              return;
          }

          // Calculate how many times we need to produce
          EstadoCliente estado = cliente.getEstado();
          Receta receta = estado.getRecetas().get(producto);
          if (receta == null) {
              System.out.println("❌ Receta no encontrada para " + producto);
              return;
          }

          int unidadesPorProduccion = CalculadoraProduccion.calcularUnidades(estado.getRol());
          if (premium && receta.isPremium()) {
              unidadesPorProduccion = CalculadoraProduccion.aplicarBonusPremium(
                      unidadesPorProduccion,
                      receta.getBonusPremium()
              );
          }

          // Calculate number of production cycles needed
          int ciclosNecesarios = (int) Math.ceil((double) cantidadDeseada / unidadesPorProduccion);
          System.out.printf("📊 Se necesitan %d ciclos de producción para %d unidades%n",
                  ciclosNecesarios, cantidadDeseada);
          System.out.printf("   (%d unidades por ciclo)%n", unidadesPorProduccion);

          // If premium, validate ingredients for all cycles
          if (premium) {
              Map<Product, Integer> ingredientesTotales = new HashMap<>();
              for (Map.Entry<Product, Integer> entry : receta.getIngredientes().entrySet()) {
                  ingredientesTotales.put(entry.getKey(), entry.getValue() * ciclosNecesarios);
              }

              // Check if we have enough ingredients
              for (Map.Entry<Product, Integer> entry : ingredientesTotales.entrySet()) {
                  Product ingrediente = entry.getKey();
                  int necesario = entry.getValue();
                  int disponible = estado.getInventario().getOrDefault(ingrediente, 0);

                  if (disponible < necesario) {
                      System.out.printf("❌ Ingredientes insuficientes para %d ciclos%n", ciclosNecesarios);
                      System.out.printf("   %s: necesitas %d, tienes %d%n",
                              ingrediente, necesario, disponible);
                      return;
                  }
              }
          }

          // Produce the requested cycles
          int totalProducido = 0;
          for (int i = 0; i < ciclosNecesarios; i++) {
              cliente.producir(producto, premium);
              totalProducido += unidadesPorProduccion;
          }

          System.out.printf("✅ Producción completada: %d unidades de %s (%s)%n",
                  totalProducido, producto, premium ? "premium" : "básico");

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

  private static void handleOfertas(ClienteBolsa cliente) {
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

  private static void handleAceptarOferta(String[] parts, ConectorBolsa connector, ClienteBolsa cliente) {
    if (parts.length < 2) {
        System.out.println("❌ Uso: aceptar <offerId>");
        return;
    }
    String offerId = parts[1];
    cliente.aceptarOferta(offerId);
  }

  private static void printHelp() {
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

  private static class MyTradingBot implements EventListener {

    @Override
    public void onLoginOk(LoginOKMessage loginOk) {
      // Guard clause
      if (loginOk == null) {
        return;
      }

      System.out.println("✅ LOGIN SUCCESSFUL!");
      System.out.println("   Team: " + loginOk.getTeam());
      System.out.println("   Species: " + loginOk.getSpecies());
      System.out.println("   Balance: $" + loginOk.getCurrentBalance());
      System.out.println();

      // TODO: Initialize your bot state here
      // - Store initial balance
      // - Store available products
      // - Initialize your strategy
    }

    @Override
    public void onError(ErrorMessage error) {
      // Guard clause
      if (error == null) {
        return;
      }

      System.err.println("❌ ERROR [" + error.getCode() + "]: " + error.getReason());

      // TODO: Handle errors
      // - Log the error
      // - Retry if needed
      // - Update your strategy
    }

    @Override
    public void onTicker(TickerMessage ticker) {
      // Guard clause
      if (ticker == null) {
        return;
      }

      // Print market data
      System.out.println("📊 TICKER: " + ticker.getProduct() + " | Bid: $" + ticker.getBestBid() + " | Ask: $"
          + ticker.getBestAsk() + " | Mid: $" + ticker.getMid());

      // TODO: Implement your trading strategy here
      // - Update price tracking
      // - Decide when to buy/sell
      // - Calculate profit opportunities
    }

    @Override
    public void onFill(FillMessage fill) {
      // Guard clause
      if (fill == null) {
        return;
      }

      System.out.println("✅ FILL: " + fill.getSide() + " " + fill.getFillQty() + " " + fill.getProduct() + " @ $"
          + fill.getFillPrice());

      // TODO: Update your state after a fill
      // - Update inventory
      // - Update balance
      // - Log the transaction
    }

    @Override
    public void onBalanceUpdate(BalanceUpdateMessage balanceUpdate) {
      // Guard clause
      if (balanceUpdate == null) {
        return;
      }

      System.out.println("💰 BALANCE UPDATE: " + balanceUpdate);

      // TODO: Track balance changes
      // - Extract balance from message
      // - Update your internal state
    }

    @Override
    public void onInventoryUpdate(InventoryUpdateMessage inventoryUpdate) {
      // Guard clause
      if (inventoryUpdate == null) {
        return;
      }

      System.out.println("📦 INVENTORY UPDATE: " + inventoryUpdate);

      // TODO: Track inventory changes
      // - Extract product and quantity from message
      // - Update your internal inventory map
    }

    @Override
    public void onOffer(OfferMessage offer) {
      // Students can implement if needed
    }

    @Override
    public void onOrderAck(OrderAckMessage orderAck) {
      // Students can implement if needed
    }

    @Override
    public void onEventDelta(EventDeltaMessage eventDelta) {
      // Students can implement if needed
    }

    @Override
    public void onBroadcast(BroadcastNotificationMessage broadcast) {
      // Guard clause
      if (broadcast == null) {
        return;
      }

      System.out.println("📢 BROADCAST: " + broadcast.getMessage());
    }

    @Override
    public void onConnectionLost(Throwable throwable) {
      System.err.println("💔 CONNECTION LOST: " + throwable.getMessage());

      // TODO: Implement reconnection logic
    }

      @Override
      public void onGlobalPerformanceReport(GlobalPerformanceReportMessage globalPerformanceReportMessage) {

      }
  }
}
