package tech.hellsoft.trading;

import java.util.HashMap;
import tech.hellsoft.trading.dto.client.AcceptOfferMessage;
import tech.hellsoft.trading.dto.client.ProductionUpdateMessage;
import tech.hellsoft.trading.dto.server.*;
import tech.hellsoft.trading.dto.client.OrderMessage;
import tech.hellsoft.trading.enums.MessageType;
import tech.hellsoft.trading.enums.OrderMode;
import tech.hellsoft.trading.enums.OrderSide;
import tech.hellsoft.trading.enums.Product;
import tech.hellsoft.trading.exception.produccion.IngredientesInsuficientesException;
import tech.hellsoft.trading.exception.produccion.RecetaNoEncontradaException;
import tech.hellsoft.trading.exception.trading.InventarioInsuficienteException;
import tech.hellsoft.trading.exception.trading.ProductoNoAutorizadoException;
import tech.hellsoft.trading.exception.trading.SaldoInsuficienteException;
import tech.hellsoft.trading.model.Receta;
import tech.hellsoft.trading.model.Rol;
import tech.hellsoft.trading.util.CalculadoraProduccion;
import tech.hellsoft.trading.util.RecetaValidator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;

// Daiana si hizo su parte wujuuu
public class ClienteBolsa implements EventListener {
    private final ConectorBolsa conector;
    @Getter
    private final EstadoCliente estado;
    private final AtomicInteger orderIdCounter;

    public ClienteBolsa() {
        this.conector = new ConectorBolsa();
        this.estado = new EstadoCliente();
        this.orderIdCounter = new AtomicInteger(0);
    }

    @Override
    public void onLoginOk(LoginOKMessage msg){
        if(msg==null){
            return;
        }
        System.out.println("LOGIN OK");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // set initial balance onloginok i think is the like start func
        estado.setSaldo(msg.getInitialBalance());
        estado.setSaldoInicial(msg.getInitialBalance());
        estado.setNombreEquipo(msg.getTeam());

        /*if(msg.getRole() != null ){
            Rol rol = new Rol(
                    msg.getRole().getBranches() != null ? msg.getRole().getBranches() : 2,
                    msg.getRole().getMaxDepth() != null ? msg.getRole().getMaxDepth() : 3,
                    msg.getRole().getDecay() != null ? msg.getRole().getDecay() : 0.9,
                    msg.getRole().getBaseEnergy() != null ? msg.getRole().getBaseEnergy() : 100.0,
                    msg.getRole().getLevelEnergy() != null ? msg.getRole().getLevelEnergy() : 10.0
            );
            estado.setRol(rol);
            System.out.println("Rol configurado: "+rol);
        } else {
            // Set default role if not provided by server
            Rol defaultRol = new Rol(2, 3, 0.9, 100.0, 10.0);
            estado.setRol(defaultRol);
            System.out.println("Rol por defecto configurado (servidor no envió rol completo)");
        }*/
        // lets print what we get on role
        System.out.println("Rol recibido: "+msg.getRole());


        if(msg.getAuthorizedProducts() != null){
            estado.setProductosAutorizados(msg.getAuthorizedProducts());
            System.out.println("Productos autorizados: "+msg.getAuthorizedProducts());
        }

        if(msg.getRecipes() != null){
            /*Map<Product, Recipe> r = msg.getRecipes();
            Map<Product, Receta> recetasMapeadas = new HashMap<>();
            for(Map.Entry<Product, Recipe> entry : r.entrySet()){
                Receta receta = new Receta(
                        entry.getKey(),
                        entry.getValue().getIngredients(),
                        entry.getValue().getPremiumBonus()
                );
                recetasMapeadas.put(entry.getKey(), receta);
            }
            estado.setRecetas(recetasMapeadas);*/
            System.out.println("Recetas recibidas: "+msg.getRecipes().size());
        }

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Saldo inicial: "+estado.getSaldoInicial());
        System.out.println("Equipo: "+estado.getNombreEquipo());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println();
    }

    @Override
    public void onFill(FillMessage fill){
        System.out.println("Fill recibido");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Product producto = fill.getProduct();
        int cantidad = fill.getFillQty();
        double precio = fill.getFillPrice();
        String lado = fill.getSide().toString();
        double monto = cantidad * precio;

        if("BUY".equalsIgnoreCase(lado)){
            estado.setSaldo(estado.getSaldo() - monto);
            Map<Product, Integer> inventario = estado.getInventario();
            int cantidadActual = inventario.getOrDefault(producto, 0);
            inventario.put(producto, cantidadActual + cantidad);
            System.out.println("Compra realizada: " + cantidad + " unidades de " + producto );
        } else if ("SELL".equalsIgnoreCase(lado)) {
            // falta esta logica ppero esto me dio la ia
            estado.setSaldo(estado.getSaldo() + monto);
            Map<Product, Integer> inventario = estado.getInventario();
            int cantidadActual = inventario.getOrDefault(producto, 0);
            inventario.put(producto, cantidadActual - cantidad);
            System.out.println("Venta realizada: " + cantidad + " unidades de " + producto);
        }
        System.out.println("Nuevo saldo: $" + String.format("%.2f", estado.getSaldo()));
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println();
    }
    @Override
    public void onTicker(TickerMessage ticker) {
        Product producto = ticker.getProduct();
        //double mid = ticker.getMid();
        //estado.getPreciosActuales().put(producto, mid);
        // revisar si necesitamos imprimirlos
    }

    @Override
    public void onOffer(OfferMessage offer) {
        System.out.println("Offer recibido");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("De: "+offer.getBuyer());
        System.out.println("Producto: "+offer.getProduct());
        System.out.println("Cantidad: "+offer.getQuantityRequested());
        System.out.println("Precio maximo: "+offer.getMaxPrice());
        System.out.println("Offer ID: "+offer.getOfferId());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("💡 Usa 'aceptar " + offer.getOfferId() + "para aceptar");
        System.out.println();
        // lo guardamos para ser procesado
        estado.getOfertasPendientes().put(offer.getOfferId(), offer);

    }

    @Override
    public void onError(ErrorMessage error) {
        System.err.println("\n❌ ERROR DEL SERVIDOR");
        System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.err.println("Código: " + error.getCode().getValue());
        System.err.println("Razón: " + error.getReason());
        System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.err.println();

        // Manejar errores específicos
        switch (error.getCode().getValue()) {
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
    public void onOrderAck(OrderAckMessage orderAckMessage) {

    }

    @Override
    public void onInventoryUpdate(InventoryUpdateMessage inventoryUpdateMessage) {

    }

    @Override
    public void onBalanceUpdate(BalanceUpdateMessage balanceUpdateMessage) {

    }

    @Override
    public void onEventDelta(EventDeltaMessage eventDeltaMessage) {

    }

    @Override
    public void onBroadcast(BroadcastNotificationMessage broadcastNotificationMessage) {

    }

    @Override
    public void onConnectionLost(Throwable throwable) {

    }

    // metodos NUESTROS MUAJAJAJAJ

    public void comprar(Product producto, int cantidad, String mensaje) throws SaldoInsuficienteException {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Las cantidades no pueden ser <= 0");
        }
        double precioEstimado = estado.getPreciosActuales().getOrDefault(producto, 0.0);
        if (precioEstimado <= 0) {
            System.err.println("⚠️ Precio no disponible para " + producto +
                    ". Esperando ticker...");
            precioEstimado = 100000000000000000000.0;
        }

        double costoEstimado = cantidad * precioEstimado;

        if (estado.getSaldo() < costoEstimado) {
            throw new SaldoInsuficienteException(estado.getSaldo(), costoEstimado);
        }

        // crear orden
        String orderId = "ORD-" + orderIdCounter.getAndIncrement();
        OrderMode orderMode = OrderMode.MARKET;
        Double limitPrice = null;

        if(mensaje.equalsIgnoreCase("LIMIT")){
            orderMode = OrderMode.LIMIT;
            limitPrice = precioEstimado * 1.02; // 2% sobre el precio de mercado
        }

        OrderMessage orden = OrderMessage.builder()
                .clOrdID(orderId)
                .side(OrderSide.BUY)
                .product(producto)
                .qty(cantidad)
                .mode(orderMode)
                .limitPrice(limitPrice)
                .build();

        conector.enviarOrden(orden);
        System.out.println("✅ Orden de compra enviada: " + cantidad + " unidades de " + producto);
    }

    public void vender(Product producto, int cantidad, String mensaje) throws InventarioInsuficienteException {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Las cantidades no pueden ser <= 0");
        }

        int cantidadEnInventario = estado.getInventario().getOrDefault(producto, 0);
        if (cantidadEnInventario < cantidad) {
            throw new InventarioInsuficienteException(producto, cantidadEnInventario, cantidad);
        }

        // crear orden
        String orderId = "ORD-" + orderIdCounter.getAndIncrement();
        OrderMode orderMode = OrderMode.MARKET;
        Double limitPrice = null;

        if(mensaje != null && mensaje.equalsIgnoreCase("LIMIT")){
            orderMode = OrderMode.LIMIT;
            double precioEstimado = estado.getPreciosActuales().getOrDefault(producto, 0.0);
            limitPrice = precioEstimado * 0.98; // 2% bajo el precio de mercado
        }

        OrderMessage orden = OrderMessage.builder()
                .clOrdID(orderId)
                .side(OrderSide.SELL)
                .product(producto)
                .qty(cantidad)
                .mode(orderMode)
                .limitPrice(limitPrice)
                .build();

        conector.enviarOrden(orden);
        System.out.println("✅ Orden de venta enviada: " + cantidad + " unidades de " + producto);

    }

    public void producir(Product producto, boolean premium) throws ProductoNoAutorizadoException, RecetaNoEncontradaException, IngredientesInsuficientesException {
        // validar si el producto es autorizado
        if(!estado.getProductosAutorizados().contains(producto)){
            throw new ProductoNoAutorizadoException(producto, estado.getProductosAutorizados());
        }

        // obtener la receta
        Receta receta = estado.getRecetas().get(producto);
        if(receta == null) {
            throw new RecetaNoEncontradaException(receta);
        }

        if(premium){
            if(!RecetaValidator.puedeProducir(receta, estado.getInventario())){
                throw new IngredientesInsuficientesException(
                        receta.getIngredientes(),
                        estado.getInventario()
                );
            }
            RecetaValidator.consumirIngredientes(receta, estado.getInventario());
            System.out.println("Ingredientes consumidos: " + receta.getIngredientes());
        }

        int cantidad = CalculadoraProduccion.calcularUnidades(estado.getRol());

        // aplicar bonnus de premium
        if (premium && receta.isPremium()) {
            cantidad = CalculadoraProduccion.aplicarBonusPremium(cantidad, receta.getBonusPremium());
        }

        // enviar al servidor produccion basica
        ProductionUpdateMessage p = new ProductionUpdateMessage(MessageType.PRODUCTION_UPDATE, producto, cantidad);
        conector.enviarActualizacionProduccion(p);

        int cantidadActual = estado.getInventario().getOrDefault(producto, 0);
        estado.getInventario().put(producto, cantidadActual + cantidad);
        System.out.println("✅ Producción realizada: " + cantidad + " unidades de " + producto);
    }

    public void aceptarOferta(String offerId){
        OfferMessage oferta = estado.getOfertasPendientes().get(offerId);
        if (oferta == null) {
            System.err.println("❌ Oferta no encontrada: " + offerId);
            return;
        }

        int disponible = estado.getInventario().getOrDefault(oferta.getProduct(), 0);
        if(disponible < oferta.getQuantityRequested()) {
            System.err.println("❌ Inventario insuficiente para aceptar la oferta: " + offerId);
            System.err.println("   Necesitas: " + oferta.getQuantityRequested() + ", Tienes: " + disponible);
            return;
        }
        AcceptOfferMessage respuesta = AcceptOfferMessage.builder()
                .type(MessageType.ACCEPT_OFFER)
                .offerId(offerId)
                .accept(Boolean.TRUE)
                .quantityOffered(oferta.getQuantityRequested())
                .priceOffered(oferta.getMaxPrice())
                .build();
        conector.enviarRespuestaOferta(respuesta);
        estado.getOfertasPendientes().remove(offerId);
    }
}
