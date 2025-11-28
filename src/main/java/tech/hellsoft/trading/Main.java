package tech.hellsoft.trading;

import tech.hellsoft.trading.config.Configuration;
import tech.hellsoft.trading.exception.ConexionFallidaException;
import tech.hellsoft.trading.util.ConfigLoader;
import tech.hellsoft.trading.util.ConsolaInteractiva;
import tech.hellsoft.trading.dto.server.GlobalPerformanceReportMessage;
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


public final class Main {

    static void main(String[] args) {
    try {

      // 2. Create ConectorBolsa
      ConectorBolsa connector = new ConectorBolsa();

      // 3. Create ClienteBolsa
      ClienteBolsa cliente = new ClienteBolsa(connector);

      // 3.1 Agregar listener
      connector.addListener(cliente);

      login(connector);

      // 5. Iniciamos la consola interactiva
      ConsolaInteractiva consola = new ConsolaInteractiva(cliente, connector);
      consola.iniciar();



    } catch (Exception e) {
      System.err.println("❌ Error: " + e.getMessage());
      if (e.getCause() != null) {
          System.err.println("   Causa: " + e.getCause().getMessage());
      }
      System.exit(1);
    }
  }

  public static void login(ConectorBolsa connector) throws ConexionFallidaException {
      // 1. carga la configuración (apiKey, team, host)
      Configuration config = ConfigLoader.load("src/main/resources/config.json");

      // 4. Conectar al servidor
      try{
          connector.conectar(config.host(), config.apiKey());
      }catch (ConexionFallidaException e){
            System.err.println(e.getMessage());
            return;
      }

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
