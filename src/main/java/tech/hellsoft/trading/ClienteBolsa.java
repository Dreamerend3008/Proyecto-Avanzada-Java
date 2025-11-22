package tech.hellsoft.trading;

import java.util.EventListener;
import tech.hellsoft.trading.dto.client.AcceptOfferMessage;
import tech.hellsoft.trading.dto.server.*;
import tech.hellsoft.trading.dto.client.OrderMessage;
import tech.hellsoft.trading.enums.MessageType;
import tech.hellsoft.trading.enums.OrderMode;
import tech.hellsoft.trading.enums.OrderSide;
import tech.hellsoft.trading.enums.Product;
import tech.hellsoft.trading.exception.*;
import tech.hellsoft.trading.model.Receta;
import tech.hellsoft.trading.model.Rol;
import tech.hellsoft.trading.services.EventListenerV2;
import tech.hellsoft.trading.util.CalculadoraProduccion;
import tech.hellsoft.trading.util.RecetaValidator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// tampoco va a compilar hasta que daiana haga su parte
public class ClienteBolsa implements EventListenerV2 {
    private final ConectorBolsa conector;
    private final EstadoCliente estado;
    private final AtomicInteger orderIdCounter;

    public ClienteBolsa() {
        this.conector = new ConectorBolsa();
        this.estado = new EstadoCliente();
        this.orderIdCounter = new AtomicInteger(0);
    }

    @Override
    public void onLoginOk(LoginOKMessage msg){
        System.out.println("LOGIN OK");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // set initial balance onloginok i think is the like start func
        estado.setSaldo(msg.getInitialBalance());
        estado.setSaldoInicial(msg.getInitialBalance());
        estado.setNombreEquipo(msg.getTeam());

        if(msg.getRole() != null){
            Rol rol = new Rol(
                    msg.getRole().getBranches(),
                    msg.getRole().getMaxDepth(),
                    msg.getRole().getDecay(),
                    msg.getRole().getBaseEnergy(),
                    msg.getRole().getLevelEnergy()
            );
            estado.setRol(rol);
            System.out.println("Rol configurado: "+rol);
        }

        if(msg.getAuthorizedProducts() != null){
            estado.setProductosAutorizados(msg.getAuthorizedProducts());
            System.out.println("Productos autorizados: "+msg.getAuthorizedProducts());
        }

        if(msg.getRecipes() != null){
            estado.setRecetas(msg.getRecipes());
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
            // falta implementar lo de esperar al mensaje de respuesta
        }
    }
    @Override
    public void onTicker(TickerMessage ticker) {
        Product producto = ticker.getProduct();
        double mid = ticker.getMid();
        estado.getPreciosActuales().put(producto, mid);
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

    // metodos NUESTROS MUAJAJAJAJ

    public void comprar(Product producto, int cantidad, String TipoDeOrder, String mensaje) throws SaldoInsuficienteException {
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
        MessageType tipoMensaje = MessageType.ORDER;
        String orderId = "ORD-" + orderIdCounter.getAndIncrement();
        OrderMode orderMode = OrderMode.MARKET;
        Double limitPrice = 100000000.0;
        if(TipoDeOrder.equalsIgnoreCase("LIMIT")){
            orderMode = OrderMode.LIMIT;
            // todavia revisar como poner el precio en limit
        }

        OrderMessage orden = OrderMessage.builder()
                .clOrdID(orderId)
                .side(OrderSide.BUY)
                .product(producto)
                .qty(cantidad)
                .mode(OrderMode.MARKET)
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
        MessageType tipoMensaje = MessageType.ORDER;
        String orderId = "ORD-" + orderIdCounter.getAndIncrement();
        OrderMode orderMode = OrderMode.MARKET;
        // falta implementar limit sell para hacer ordenes mas seguras
        OrderMessage orden = OrderMessage.builder()
                .clOrdID(orderId)
                .side(OrderSide.SELL)
                .product(producto)
                .qty(cantidad)
                .mode(orderMode)
                .build();

        conector.enviarOrden(orden);
        System.out.println("✅ Orden de venta enviada: " + cantidad + " unidades de " + producto);

    }

    public void producir(Product producto, boolean premium) throws ProductoNoAutorizadoException, RecetaNoEncontradaException, IngredientesInsuficientesException{
        // REVISAR ESTO
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

    public EstadoCliente getEstado() {
        return estado;
    }
}
