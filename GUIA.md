# 🥑🥑 Bolsa Interestelar de Aguacates

# Andorianos

- 🥑 Bolsa Interestelar de Aguacates Andorianos

###### ◦ Cliente de Trading con SDK — Guía del Estudiante

###### ◦ Java 25

###### ◦ 🌌 EL LORE

###### ▪ Las Tres Leyes de Bodoque:

###### ◦ 🎯 TU MISIÓN

###### ◦ 🌍 LAS 12 ESPECIES

###### ◦ 📚 CONCEPTOS BÁSICOS DE TRADING

###### ▪ ¿Qué es una Orden (Order)?

###### ▪ Market vs Limit Orders

###### ▪ El Ticker

###### ▪ El Fill (Ejecución)

###### ▪ Las Ofertas (Offers)

###### ▪ Producción

###### ▪ El P&L (Profit & Loss)

###### ◦ 🔧 EL SDK: LO QUE TE DAMOS

###### ▪ ¿Qué es el SDK?

###### ▪ ⚡ Clase ConectorBolsa

###### ▪ 🎯 Interface EventListener

###### ▪ 🔄 Clase TareaAutomatica

###### ▪ 📦 DTOs (Data Transfer Objects)

###### ◦ 🚨 EXCEPCIONES QUE DEBES IMPLEMENTAR

###### ▪ Excepciones Requeridas

###### ▪ Excepciones Opcionales (Bonus)

###### ▪ Jerarquía Sugerida

###### ▪ 🚀 Ejemplo de Uso del SDK

###### ◦ 💻 LO QUE TÚ IMPLEMENTAS

###### ▪ 1⃣ ClienteBolsa — El Corazón (80-100 líneas)

###### ▪ 2⃣ EstadoCliente — El Estado del Juego (100 líneas)

###### ▪ 3⃣ CalculadoraProduccion — El Algoritmo Recursivo (30 líneas)

###### ▪ 4⃣ RecetaValidator — Validar Ingredientes (40 líneas)


###### ▪ 5⃣ SnapshotManager — Serialización Binaria (20 líneas)

###### ▪ 6⃣ ConfigLoader — Lectura de JSON (20 líneas)

###### ▪ 7⃣ ConsolaInteractiva — Comandos del Usuario (100-150 líneas)

###### ▪ login

###### ▪ status

###### ▪ inventario

###### ▪ precios

###### ▪ comprar <producto> <cantidad> [mensaje]

###### ▪ vender <producto> <cantidad> [mensaje]

###### ▪ producir <producto> <basico|premium>

###### ▪ ofertas

###### ▪ aceptar <offerId>

###### ▪ rechazar <offerId> [motivo]

###### ▪ snapshot save

###### ▪ snapshot load

###### ▪ resync

###### ▪ ayuda o help

###### ▪ exit

###### ▪ 8⃣ DTOs Propios (100 líneas)

###### ▪ 9⃣ (OPCIONAL - BONUS) AutoProduccionManager — Auto-Producción

###### Inteligente

###### ◦ ⚠ ERRORES DEL SERVIDOR

###### ◦ 📐 DIAGRAMA DE CLASES

###### ◦ 🔄 FLUJOS COMPLETOS

###### ▪ Flujo 1: Login → Producir → Vender

###### ▪ Flujo 2: Crash → Recovery

###### ◦ 🏆 EL TORNEO DE 15 MINUTOS

###### ▪ ¿Qué es el Torneo?

###### ▪ Estrategia General

###### ▪ Fases del Torneo

###### ▪ Ejemplo de Ciclo Rentable

###### ▪ Evaluación

###### ◦ 🚀 SETUP Y PRIMEROS PASOS

###### ▪ Estructura de Proyecto

###### ▪ config.json


###### ▪ recetas/avocultores.json

###### ▪ ✅ Checklist de Implementación

###### ◦ ❓ PREGUNTAS FRECUENTES

###### ◦ 🏁 RESUMEN FINAL

###### ▪ Separación de Responsabilidades

###### ▪ Puntos Clave

## Cliente de Trading con SDK — Guía del Estudiante

## Java 25

## 🌌🌌 EL LORE

###### En el año 3847, el Gran Cultivador Xolotl el Visionario descubrió que los aguacates

###### andorianos no eran simples frutos, sino semillas cósmicas capaces de absorber energía del

###### espacio-tiempo. Pero ningún aguacate puede crecer solo: el GUACA necesita FOSFO y PITA,

###### el SEBO requiere NUCREM. Esta interdependencia forzada creó el Mercado Interestelar

###### bajo la supervisión del legendario AI-Oráculo Juan Carlos Bodoque.

### Las Tres Leyes de Bodoque:

###### 1. "El que no produce, compra. El que no compra, muere."

###### 2. "El mercado castiga al egoísta y premia al cooperador astuto."

###### 3. "Sin interdependencia, no hay comercio. Sin comercio, no hay civilización."

## 🎯🎯 TU MISIÓN

###### Construir un cliente de trading inteligente en Java que:

- ✅ Se conecta a la bolsa via TCP usando el SDK que te damos
- ✅ Compra/vende productos en el mercado
- ✅ Pr oduce con algoritmo recursivo (que TÚ implementas)


- ✅ Optimiza ganancias (P&L%) durante 15 minutos de torneo

###### Conceptos que aprenderás:

- Implementar interfaces ( EventListener )
- Recursión (algoritmo de producción)
- Colecciones ( Map<String, Integer> , List )
- Serialización binaria (snapshots)
- Lectura de JSON (config)
- Manejo de excepciones
- Callbacks y eventos asíncronos

## 🌍🌍 LAS 12 ESPECIES

###### Cada especie puede producir 1 producto básico (gratis) y 2 productos premium (requieren

###### ingredientes de otras especies, +30% bonus):

###### # Especie

###### Básico

###### (gratis)

###### Premium 1 (+30%

###### bonus)

###### Premium 2 (+30%

###### bonus)

###### 1 Avocultores PALTA-OIL

###### GUACA (5 FOSFO +

###### 3 PITA)

###### SEBO (8 NUCREM)

###### 2

###### Monjes de

###### Fosforescencia

###### FOSFO

###### GUACA (5 PALTA-

###### OIL + 3 PITA)

###### NUCREM (6 SEBO)

###### 3

###### Cosechadores

###### de Pita

###### PITA SEBO (8 NUCREM)

###### CASCAR-ALLOY (

###### FOSFO)

###### 4

###### Herreros

###### Cósmicos

###### CASCAR-

###### ALLOY

###### QUANTUM-PULP (

###### PALTA-OIL)

###### SKIN-WRAP (

###### ASTRO-BUTTER)

###### 5 Extractores

###### QUANTUM-

###### PULP

###### NUCREM (6 SEBO)

###### FOSFO (9 SKIN-

###### WRAP)

###### 6 Tejemanteles

###### SKIN-

###### WRAP

###### PITA (8 CASCAR-

###### ALLOY)

###### ASTRO-BUTTER (

###### GUACA)


###### # Especie

###### Básico

###### (gratis)

###### Premium 1 (+30%

###### bonus)

###### Premium 2 (+30%

###### bonus)

###### 7

###### Cremeros

###### Astrales

###### ASTRO-

###### BUTTER

###### CASCAR-ALLOY (

###### FOSFO)

###### PALTA-OIL (

###### QUANTUM-PULP)

###### 8

###### Mineros del

###### Sebo

###### SEBO

###### ASTRO-BUTTER (

###### GUACA)

###### GUACA (5 PALTA-

###### OIL + 3 PITA)

###### 9

###### Núcleo

###### Cremero

###### NUCREM

###### SKIN-WRAP (

###### ASTRO-BUTTER)

###### QUANTUM-PULP (

###### PALTA-OIL)

###### 10 Destiladores GUACA

###### PALTA-OIL (

###### QUANTUM-PULP)

###### FOSFO (9 SKIN-

###### WRAP)

###### 11 Cartógrafos GUACA NUCREM (6 SEBO)

###### PITA (8 CASCAR-

###### ALLOY)

###### 12

###### Someliers

###### Andorianos

###### PALTA-OIL SEBO (8 NUCREM)

###### CASCAR-ALLOY (

###### FOSFO)

###### ⚠⚠ Concepto clave: INTERDEPENDENCIA

- Los Avocultores producen PALTA-OIL gratis
- Para hacer GUACA premium necesitan FOSFO (producido por Monjes) y PITA (producido

###### por Cosechadores)

- Ninguna especie es autosuficiente → **DEBEN comerciar**

## 📚📚 CONCEPTOS BÁSICOS DE TRADING

###### Antes de empezar a programar, necesitas entender cómo funciona una bolsa de valores.

### ¿Qué es una Orden (Order)?

###### Una orden es una instrucción que envías al mercado para comprar o vender un producto.

###### Hay dos tipos principales:


###### Orden de Compra (BUY):

- Manifiestas tu intención de comprar cierta cantidad de un producto
- El mercado buscará un vendedor que tenga ese producto disponible
- Cuando se encuentra un vendedor, la orden se "ejecuta" (fill)

###### Orden de Venta (SELL):

- Manifiestas tu intención de vender cierta cantidad de un producto que tienes
- El mercado buscará un comprador interesado
- Cuando se encuentra un comprador, la orden se "ejecuta" (fill)

### Market vs Limit Orders

###### Market Order (Orden de Mercado):

- Se ejecuta inmediatamente al mejor precio disponible
- Garantiza ejecución, pero NO garantiza el precio exacto
- En este proyecto, TODAS las órdenes son market orders

###### Limit Order (Orden Limitada):

- Solo se ejecuta si el precio es igual o mejor al límite que especificaste
- Garantiza precio, pero NO garantiza ejecución
- **NO disponibles en este proyecto** (simplificación pedagógica)

### El Ticker

###### El ticker es un mensaje periódico (cada 5 segundos) que informa sobre el estado del

###### mercado para cada producto:

- **Best Bid** : El precio más alto que alguien está dispuesto a pagar (comprador más

###### generoso)

- **Best Ask** : El precio más bajo al que alguien está dispuesto a vender (vendedor más

###### barato)

- **Mid Price** : Promedio entre bid y ask, usado como precio de referencia
- **Volume** : Cantidad total comerciada en el último periodo

###### Ejemplo de ticker:


```
Producto: PALTA-OIL
Best Bid: $24.50 (alguien quiere comprar a este precio)
Best Ask: $26.00 (alguien quiere vender a este precio)
Mid: $25.25 (precio de referencia)
Volume: 150 unidades comerciadas
```
### El Fill (Ejecución)

###### Un fill es la confirmación de que tu orden se ejecutó exitosamente. Cuando envías una orden

###### de compra o venta, el servidor te responde inmediatamente con "ORDER_ACCEPTED", pero

###### esto NO significa que se completó la transacción. La orden entra a una cola y espera hasta

###### que alguien del lado opuesto la acepte.

###### Cuando finalmente se ejecuta, recibes un FILL con:

- Producto comprado/vendido
- Cantidad exacta transaccionada
- Precio final de ejecución
- Mensaje de la contraparte (el otro trader)

###### Flujo típico:

1. Envías: ORDEN DE COMPRA (10 FOSFO)
2. Servidor responde: ORDER_ACCEPTED (confirmación recibida)
3. ... tiempo de espera (1-10 segundos) ...
4. Servidor envía: FILL (compraste 10 FOSFO @ $18.00)
5. Actualizas tu saldo e inventario

### Las Ofertas (Offers)

###### Una oferta es una propuesta DIRECTA de otro trader para comprarte algo. A diferencia de las

###### órdenes normales que van al mercado general, las ofertas te llegan específicamente a ti con

###### un mensaje del tipo:

###### "Hola, necesito urgente 15 unidades de PITA. Te ofrezco $23.00 por unidad (10% más

###### del mercado). ¿Aceptas?"


###### Nota sobre timing: Aunque el servidor tiene un timeout técnico de 500ms, en la práctica las

###### ofertas son eventos raros durante el torneo. Pueden pasar varios minutos entre ofertas. La

###### mecánica es manual - tú decides si aceptas escribiendo el comando en la consola. NO

###### necesitas implementar multithreading complejo para manejar ofertas. Un simple comando

###### aceptar <offerId> en tu consola es suficiente.

### Producción

###### La producción es el mecanismo para crear productos desde cero. Hay dos tipos:

###### Producción Básica:

- No requiere ingredientes
- Más lenta (menos unidades por ciclo)
- Siempre disponible
- Ejemplo: Producir PALTA-OIL sin ingredientes → 13 unidades

###### Producción Premium:

- Requiere ingredientes de otros productos
- 30% más rápida (más unidades por ciclo)
- Necesitas comprar ingredientes primero
- Ejemplo: Producir GUACA con 5 FOSFO + 3 PITA → 17 unidades

###### La cantidad exacta de unidades producidas se calcula con un algoritmo recursivo que TÚ

###### implementas.

### El P&L (Profit & Loss)

###### El P&L es tu métrica de éxito: cuánto ganaste o perdiste en porcentaje.

###### Fórmula:

```
Patrimonio Neto = Efectivo + Valor del Inventario
P&L% = ((Patrimonio Neto - Saldo Inicial) / Saldo Inicial) × 100
```
###### Ejemplo:


```
Saldo inicial: $10,
Saldo actual: $12,
Inventario: 50 FOSFO @ $20.00 = $1,
```
```
Patrimonio Neto = $12,000 + $1,000 = $13,
P&L% = ((13,000 - 10,000) / 10,000) × 100 = +30%
```
###### ⚠ IMPORTANTE : Al final del torneo (T=15:00), el inventario sin vender vale $0. Solo cuenta el

###### efectivo.

## 🔧🔧 EL SDK: LO QUE TE DAMOS

### ¿Qué es el SDK?

###### Una biblioteca .jar que maneja SOLO la comunicación TCP y threading básico.

###### TÚ implementas toda la lógica de negocio.

### ⚡⚡ Clase ConectorBolsa

###### Esta clase es tu punto de contacto con el servidor. Ofrece los siguientes métodos:

**conectar(String host, int puerto)**

- Establece la conexión TCP con el servidor de la bolsa
- Lanza ConexionFallidaException si el servidor no está disponible
- Debes llamar esto ANTES de hacer login

**login(String apiKey, EventListener listener)**

- Autentica tu equipo con tu API Key única
- Guarda una referencia a tu objeto listener (tu implementación de EventListener )
- A partir de este momento, el SDK llamará automáticamente los métodos de tu listener

###### cuando lleguen mensajes

- El servidor responderá con LOGIN_OK (via onLoginOk() ) o con error (via onError() )


**enviarOrden(Orden orden)**

- Envía una orden de compra (BUY) o venta (SELL) al mercado
- El SDK serializa tu objeto Orden a JSON y lo envía por TCP
- El servidor responde con ORDER_ACCEPTED inmediatamente
- Cuando la orden se ejecuta, recibes un FILL (via onFill() )

**enviarProduccion(String producto, int cantidad)**

- Notifica al servidor que produjiste unidades de un producto
- El servidor valida que tengas permiso para producir ese producto
- Responde con PRODUCTION_ACK o con error (via onError() )

**aceptarOferta(String offerId, int cantidad, double precio)**

- Acepta una oferta directa de otro trader
- Necesitas el offerId que recibiste en el callback onOffer()
- Las ofertas pueden expirar si tardas demasiado o si el comprador la cancela
- Si aceptas exitosamente, recibes un FILL confirmando la venta
- Si la oferta ya expiró, recibes un error OFFER_EXPIRED

**resync(Instant ultimaSincronizacion)**

- Sincroniza eventos perdidos después de un crash
- Le dices al servidor: "Envíame todos los FILLs desde este timestamp"
- El servidor responde con un array de FILLs que te perdiste
- Crítico para recuperarte de un crash sin perder dinero

###### 💡 Importante : El SDK maneja threading automáticamente. Usa un thread dedicado para leer

###### del socket TCP y un semáforo interno para thread-safety. Tú NO necesitas preocuparte por

###### sincronización.

### 🎯🎯 Interface EventListener

###### Esta es la interfaz que TÚ implementas. El SDK llamará estos métodos desde su thread

###### cuando lleguen mensajes del servidor.


###### La interfaz define 6 callbacks que debes implementar:

**onLoginOk(LoginOk msg)**

- Se llama cuando el login es exitoso
- Recibes el mensaje LoginOk con: saldo inicial, recetas, rol, productos autorizados
- **Aquí inicializas tu EstadoCliente** con todos estos datos
- Es el primer callback que se ejecuta, marca el inicio de la sesión

**onFill(Fill fill)**

- Se llama cuando una orden se ejecuta (compra o venta completada)
- Recibes el mensaje Fill con: producto, cantidad, precio, side (BUY/SELL), mensaje de

###### contraparte

- **Aquí actualizas tu saldo e inventario:**

###### ◦ Si side == "BUY" : restas dinero, sumas inventario

###### ◦ Si side == "SELL" : sumas dinero, restas inventario

- Es el callback más crítico, aquí se materializa todo el trading

**onTicker(Ticker ticker)**

- Se llama cada 5 segundos con precios de mercado actualizados
- Recibes el mensaje Ticker con: producto, bestBid, bestAsk, mid, volume
- Usa esto para actualizar los precios en tu EstadoCliente y calcular el P&L

###### correctamente

- También útil para decisiones de trading (¿comprar ahora o esperar?)

**onOffer(Offer offer)**

- Se llama cuando alguien te hace una oferta directa
- Recibes el mensaje Offer con: producto, cantidad, precio máximo, nombre del

###### comprador

- **Nota sobre timing:** Aunque técnicamente el servidor expira ofertas después de cierto

###### tiempo, en la práctica las ofertas son eventos manuales y raros durante el torneo.

###### Pueden pasar varios minutos entre ofertas. No necesitas código multithreading complejo

- un simple comando manual en la consola es suficiente.
- Si el precio te conviene y tienes inventario, puedes aceptar llamando
  conector.aceptarOferta()


- Alternativamente, puedes guardar la oferta y dejar que el usuario decida manualmente

###### con el comando aceptar <offerId>

**onError(ErrorMessage error)**

- Se llama cuando el servidor rechaza una operación
- Recibes el mensaje ErrorMessage con: código del error, razón explicativa
- **TODOS los errores llegan aquí:** login fallido, orden rechazada, producción inválida,

###### oferta expirada

- Usa un switch sobre error.getCodigo() para manejar cada tipo de error
- Algunos errores son fatales (ej: INVALID_TOKEN → terminar programa), otros son

###### informativos

**onConexionPerdida(Exception e)**

- Se llama si se pierde la conexión TCP con el servidor
- Puede ser por: red caída, servidor reiniciado, timeout
- **Aquí debes:** guardar un snapshot del estado, intentar reconectar, hacer resync
- Si estás en el torneo y esto ocurre, pierdes tiempo valioso → snapshots automáticos

###### cada 30s son críticos

###### Flujo de datos típico:

1. Servidor envía mensaje FILL por TCP
2. SDK recibe los bytes en su thread de lectura
3. SDK parsea el JSON → objeto Fill
4. SDK llama: tuCliente.onFill(fill)
5. Tu código se ejecuta: actualizas saldo e inventario
6. SDK continúa escuchando el socket

### 🔄🔄 Clase TareaAutomatica

###### Clase base opcional para implementar tareas que se ejecutan periódicamente en segundo

###### plano. Muy útil para automatizar producción o guardar snapshots automáticos.


###### ¿Cómo funciona?

###### TareaAutomatica usa un Timer interno de Java que ejecuta tu código cada N segundos en

###### un thread separado. Tú solo necesitas:

###### 1. Extender la clase

###### 2. Implementar el método ejecutar()

###### 3. Llamar iniciar(intervaloSegundos)

###### Métodos que provee:

- **iniciar(int intervaloSegundos)** : Inicia la tarea periódica. Por ejemplo, iniciar(60)

###### ejecutará tu código cada 60 segundos.

- **detener()** : Detiene la ejecución periódica. Útil al cerrar el programa.

###### Método que TÚ implementas:

- **ejecutar()** : El código que se ejecuta automáticamente cada N segundos. Este método

###### se llama desde un thread separado, por lo que debes tener cuidado si accedes al

###### EstadoCliente compartido.

###### Casos de uso comunes:

###### 1. Auto-producción : Producir automáticamente cada 60 segundos

###### 2. Auto-snapshot : Guardar el estado cada 30 segundos

###### 3. Monitoreo : Imprimir P&L actual cada 10 segundos

###### 4. Trading algorítmico : Analizar precios y hacer órdenes automáticas

###### Ejemplo conceptual de auto-producción:


```
public classAutoProductor extends TareaAutomatica {
private ClienteBolsa cliente;
```
```
@Override
protected voidejecutar() {
// Este código se ejecuta cada N segundos automáticamente
cliente.producir("PALTA-OIL", false); // Producir básico
```
```
int cantidad = cliente.getEstado()
.getInventario()
.getOrDefault("PALTA-OIL", 0 );
```
```
if (cantidad > 0 ) {
cliente.vender("PALTA-OIL", cantidad, "Auto-venta");
}
}
}
```
```
// En tu Main:
AutoProductor autoProductor = new AutoProductor(cliente);
autoProductor.iniciar( 60 ); // Ejecutar cada 60 segundos
```
###### ⚠⚠ Nota sobre threading : Aunque TareaAutomatica usa threads, tu implementación puede

###### ser simple. El SDK ya maneja la sincronización para las comunicaciones TCP. Solo necesitas

###### cuidado si múltiples threads acceden al mismo EstadoCliente simultáneamente.

### 📦📦 DTOs (Data Transfer Objects)

###### El SDK incluye clases inmutables que representan los mensajes del servidor. Estas clases

###### solo tienen getters (sin setters) porque representan datos que el servidor envía y que no

###### debes modificar:

- **LoginOk** : Contiene saldo inicial, recetas, rol, productos autorizados
- **Fill** : Contiene detalles de una ejecución (producto, cantidad, precio, side, mensaje de

###### contraparte)

- **Ticker** : Contiene precios actuales (bestBid, bestAsk, mid, volume)


- **Offer** : Contiene oferta directa (producto, cantidad, precio máximo, comprador)
- **ErrorMessage** : Contiene código de error y razón explicativa

###### La única clase DTO que TÚ construyes y envías es:

- **Orden** : Tiene setters porque TÚ la creas para enviar órdenes (side, producto, cantidad,

###### mensaje)

## 🚨🚨 EXCEPCIONES QUE DEBES IMPLEMENTAR

###### Como parte del proyecto, debes crear al menos 7 excepciones personalizadas para

###### manejar errores específicos de tu dominio. Estas excepciones NO vienen en el SDK, son

###### parte de TU lógica de negocio.

### Excepciones Requeridas

###### 1. SaldoInsuficienteException

- Lanzar cuando intentas comprar pero no tienes suficiente dinero
- Debe incluir: saldo actual, costo requerido
- Se lanza ANTES de enviar la orden al servidor (validación local)

###### 2. InventarioInsuficienteException

- Lanzar cuando intentas vender un producto que no tienes en cantidad suficiente
- Debe incluir: producto, cantidad disponible, cantidad requerida
- Se lanza ANTES de enviar la orden al servidor (validación local)

###### 3. ProductoNoAutorizadoException

- Lanzar cuando intentas producir un producto que no está en tu lista de productos

###### autorizados

- Debe incluir: producto que intentaste producir, lista de productos permitidos
- Se lanza en el método producir() de tu ClienteBolsa

###### 4. IngredientesInsuficientesException


- Lanzar cuando intentas producción premium pero te faltan ingredientes
- Debe incluir: Map con ingredientes requeridos vs disponibles
- Se lanza en RecetaValidator.puedeProducir() o antes de consumir ingredientes

###### 5. RecetaNoEncontradaException

- Lanzar cuando buscas una receta que no existe en tu catálogo
- Debe incluir: nombre del producto buscado
- Se lanza en el método producir() si el producto no está en estado.getRecetas()

###### 6. ConfiguracionInvalidaException

- Lanzar cuando el archivo config.json está mal formado o faltan campos requeridos
- Debe incluir: campo faltante o razón del error
- Se lanza en ConfigLoader.cargarConfig()

###### 7. SnapshotCorruptoException

- Lanzar cuando intentas cargar un snapshot pero el archivo está corrupto o no es

###### compatible

- Debe incluir: ruta del archivo, razón del fallo
- Se lanza en SnapshotManager.cargar()

### Excepciones Opcionales (Bonus)

###### Si quieres puntos extra, implementa también:

###### 8. PrecioNoDisponibleException

- Lanzar cuando intentas comprar/vender pero no hay precio de mercado disponible

###### (ticker no ha llegado)

###### 9. OfertaExpiradaException

- Lanzar cuando intentas aceptar una oferta pero el tiempo ya expiró localmente

###### 10. ValidacionOrdenException

- Excepción genérica para errores de validación de órdenes (cantidad <= 0, producto

###### vacío, etc.)


### Jerarquía Sugerida

```
Exception (Java)
├── TradingException (tu clase base abstracta)
│ ├── SaldoInsuficienteException
│ ├── InventarioInsuficienteException
│ ├── ProductoNoAutorizadoException
│ ├── PrecioNoDisponibleException
│ └── OfertaExpiradaException
│
├── ProduccionException (tu clase base abstracta)
│ ├── IngredientesInsuficientesException
│ └── RecetaNoEncontradaException
│
└── ConfiguracionException (tu clase base abstracta)
├── ConfiguracionInvalidaException
└── SnapshotCorruptoException
```
###### Nota sobre excepciones del SDK:

###### El SDK SÍ provee sus propias excepciones de red:

- ConexionFallidaException : No se pudo conectar al servidor
- TimeoutException : El servidor no respondió a tiempo
- ConexionPerdidaException : Se perdió la conexión durante la operación

###### Estas son diferentes a las tuyas porque tratan con networking, no con lógica de negocio.


### 🚀🚀 Ejemplo de Uso del SDK

```
public classMain {
public static void main(String[] args) {
```
```
// 1. Crear el conector (SDK)
ConectorBolsa conector = new ConectorBolsa();
```
```
// 2. Crear TU implementación
ClienteBolsa cliente = new ClienteBolsa(conector);
```
```
// 3. Conectar al servidor
try {
conector.conectar("localhost", 9000 );
} catch (ConexionFallidaException e) {
System.out.println("❌ No se pudo conectar al servidor");
return;
}
```
```
// 4. Login (SDK guarda referencia a tu cliente)
conector.login("TK-ANDROMEDA-2025-AVOCULTORES", cliente);
```
```
// 5. A partir de aquí, el SDK llama tus callbacks automáticamente
// cuando llegan mensajes del servidor
```
```
// 6. Tu consola interactiva
ConsolaInteractivaconsola = new ConsolaInteractiva(cliente);
consola.iniciar();
}
}
```
###### Flujo de datos:


```
Servidor envía FILL
↓
SDK recibe por TCP
↓
SDK parsea JSON → Fill objeto
↓
SDK llama: cliente.onFill(fill) ← TU CÓDIGO SE EJECUTA AQUÍ
↓
Tú actualizas saldo e inventario
```
## 💻💻 LO QUE TÚ IMPLEMENTAS

### 1⃣ ClienteBolsa — El Corazón (80-100 líneas)

###### Tu clase principal que implementa EventListener y coordina todo. Esta clase tiene dos

###### responsabilidades principales:

###### A) Implementar los 6 callbacks del SDK:

###### Cada callback tiene una responsabilidad específica:

- **onLoginOk(LoginOk msg)** : Inicializa tu EstadoCliente con la información del servidor

###### (saldo, recetas, rol, productos). Es lo primero que se ejecuta al conectarte.

- **onFill(Fill fill)** : Actualiza tu saldo e inventario cuando una transacción se completa.

###### Si fill.getSide() == "BUY" , restaste dinero y sumaste inventario. Si es "SELL", es al

###### revés. También debes imprimir la transacción con el mensaje de la contraparte.

- **onTicker(Ticker ticker)** : Actualiza los precios actuales en tu EstadoCliente. Llega

###### cada 5 segundos. Úsalo para mantener estado.getPreciosActuales() al día.

- **onOffer(Offer offer)** : Analiza si aceptas una oferta directa. Verifica si tienes el

###### producto en inventario, compara el precio ofrecido vs el precio base. Si conviene, llama

###### conector.aceptarOferta(). Tienes 500ms desde que recibes este callback.

- **onError(ErrorMessage error)** : Maneja todos los errores del servidor con un switch

###### sobre error.getCodigo(). Algunos requieren terminar el programa (ej:

###### INVALID_TOKEN ), otros son solo informativos (ej: OFFER_EXPIRED ).


- **onConexionPerdida(Exception e)** : Informa al usuario y sugiere hacer snapshot save

###### + reconectar + resync.

###### B) Ofrecer métodos públicos para el usuario:

- **comprar(String producto, int cantidad, String mensaje)** :

###### i. Validar saldo localmente (calcular costo estimado con precio actual × 1.05 para

###### margen)

###### ii. Si no hay saldo, lanzar SaldoInsuficienteException

###### iii. Crear objeto Orden (clOrdID, side="BUY", producto, cantidad, mensaje)

###### iv. Llamar conector.enviarOrden(orden)

- **vender(String producto, int cantidad, String mensaje)** :

###### i. Validar inventario localmente

###### ii. Si no hay suficiente, lanzar InventarioInsuficienteException

###### iii. Crear objeto Orden (clOrdID, side="SELL", producto, cantidad, mensaje)

###### iv. Llamar conector.enviarOrden(orden)

- **producir(String producto, boolean premium)** :

###### i. Validar que el producto esté en productosAutorizados , sino lanzar

```
ProductoNoAutorizadoException
```
###### ii. Obtener la receta de estado.getRecetas() , si no existe lanzar

```
RecetaNoEncontradaException
```
###### iii. Si premium: validar ingredientes con RecetaValidator.puedeProducir() , sino

###### lanzar IngredientesInsuficientesException

###### iv. Si premium: consumir ingredientes con RecetaValidator.consumirIngredientes()

###### v. Calcular unidades con

```
CalculadoraProduccion.calcularUnidades(estado.getRol())
```
###### vi. Si premium: aplicar bonus con CalculadoraProduccion.aplicarBonusPremium()

###### vii. Actualizar inventario local

###### viii. Notificar servidor con conector.enviarProduccion(producto, unidades)

###### Ejemplo de estructura mínima:


public classClienteBolsa implements EventListener {

```
private ConectorBolsa conector;
private EstadoCliente estado;
```
```
public ClienteBolsa(ConectorBolsa conector) {
this.conector = conector;
this.estado = new EstadoCliente();
}
```
```
// ========== CALLBACKS DEL SDK ==========
```
```
@Override
public void onLoginOk(LoginOk msg) {
// Inicializar estado con datos del servidor
estado.setSaldo(msg.getSaldoInicial());
estado.setSaldoInicial(msg.getSaldoInicial());
estado.setRecetas(msg.getRecetas());
estado.setRol(msg.getRol());
estado.setProductosAutorizados(msg.getProductosAutorizados());
System.out.println("✅ Conectado como " + msg.getEquipo());
}
```
```
@Override
public void onFill(Fill fill) {
if (fill.getSide().equals("BUY")) {
// Restar dinero, sumar inventario
} else {
// Sumar dinero, restar inventario
}
System.out.println("P&L: " + estado.calcularPL() + "%");
}
```
```
@Override
public void onTicker(Ticker ticker) {
estado.getPreciosActuales().put(ticker.getProducto(), ticker.getMid());
}
```
```
@Override
```

public void onOffer(Offer offer) {
// Decidir si aceptar basado en precio y disponibilidad
}

@Override
public void onError(ErrorMessage error) {
switch (error.getCodigo()) {
case "INVALID_TOKEN":
System.exit( 1 );
break;
// ... más casos
}
}

@Override
public void onConexionPerdida(Exception e) {
System.out.println("⚠ Conexión perdida");
}

// ========== MÉTODOS PÚBLICOS ==========

public void comprar(String producto, int cantidad, String mensaje)
throws SaldoInsuficienteException{
// Validar saldo → lanzar excepción si falla
// Crear orden → enviar
}

public void vender(String producto, int cantidad, String mensaje)
throws InventarioInsuficienteException {
// Validar inventario → lanzar excepción si falla
// Crear orden → enviar
}

public void producir(String producto, boolean premium)
throws ProductoNoAutorizadoException, RecetaNoEncontradaException,
IngredientesInsuficientesException {
// Validaciones → calcular → actualizar → notificar
}

public EstadoCliente getEstado() {


```
return estado;
}
}
```
### 2⃣ EstadoCliente — El Estado del Juego (100 líneas)

###### Mantiene todo el estado del cliente en una clase serializable. Debe tener:

###### Campos requeridos:

- saldo : Dinero actual en efectivo
- saldoInicial : Para calcular P&L
- inventario : Map<String, Integer> con productos y cantidades
- preciosActuales : Map<String, Double> actualizado por tickers
- recetas : Map<String, Receta> del servidor
- rol : Objeto Rol con parámetros del algoritmo recursivo
- productosAutorizados : List<String> de productos que puedes producir

###### Método crítico:

###### calcularPL() debe calcular tu Profit & Loss:

###### 1. Calcular valor del inventario: para cada producto, multiplicar cantidad × precio actual

###### 2. Calcular patrimonio neto = saldo + valor del inventario

###### 3. Calcular P&L% = ((patrimonioNeto - saldoInicial) / saldoInicial) × 100

###### Implementar Serializable porque esta clase se guarda en snapshots binarios.

###### Ejemplo de estructura:


```
public classEstadoCliente implements Serializable {
private double saldo;
private double saldoInicial;
private Map<String, Integer> inventario = new HashMap<>();
private Map<String, Double> preciosActuales = new HashMap<>();
private Map<String, Receta> recetas = new HashMap<>();
private Rol rol;
private List<String> productosAutorizados = new ArrayList<>();
```
```
public double calcularPL() {
double valorInventario = 0.0;
for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
double precio = preciosActuales.getOrDefault(entry.getKey(), 0.0);
valorInventario += entry.getValue() * precio;
}
double patrimonioNeto = saldo + valorInventario;
return ((patrimonioNeto - saldoInicial) / saldoInicial) * 100.0;
}
```
```
// Getters y setters para todos los campos...
}
```
### 3⃣ CalculadoraProduccion — El Algoritmo Recursivo (30

### líneas)

###### ⚠⚠ ESTE ES EL CORAZÓN RECURSIVO DEL PROYECTO

###### Fórmula provista:

```
Energía(nivel) = baseEnergy + levelEnergy × nivel
Factor(nivel) = decay^nivel × branches^nivel
Unidades(nivel) = Energía(nivel) × Factor(nivel)
```
```
Total = Σ Unidades(nivel) para nivel = 0 hasta maxDepth
```
###### Tu implementación:


public classCalculadoraProduccion {

```
/**
* Calcula las unidades producidas usando recursión.
*/
public static int calcularUnidades(Rol rol) {
return calcularRecursivo( 0 , rol);
}
```
```
/**
* Función recursiva que suma contribuciones de cada nivel.
*/
private static int calcularRecursivo(int nivel, Rol rol) {
// ⚠ CASO BASE: Profundidad máxima alcanzada
if (nivel > rol.getMaxDepth()) {
return 0 ;
}
```
```
// Calcular energía en este nivel
double energia = rol.getBaseEnergy() + rol.getLevelEnergy() * nivel;
```
```
// Calcular factor multiplicador
double decay = Math.pow(rol.getDecay(), nivel);
double branches = Math.pow(rol.getBranches(), nivel);
double factor = decay * branches;
```
```
// Contribución de este nivel
int contribucion = (int) Math.round(energia * factor);
```
```
// 🔄 CASO RECURSIVO: Sumar contribuciones de niveles inferiores
return contribucion + calcularRecursivo(nivel + 1 , rol);
}
```
```
/**
* Aplica el bonus de producción premium (+30%).
*/
public static int aplicarBonusPremium(int unidadesBase, double bonus) {
return (int) Math.round(unidadesBase * bonus);
}
```

```
}
```
###### Ejemplo con Avocultores:

```
Rol: branches=2, maxDepth=4, decay=0.7651, baseEnergy=3.0, levelEnergy=2.0
```
```
Nivel 0: (3.0 + 2.0×0) × (0.7651^0 × 2^0) = 3.0 × 1.0 = 3
Nivel 1: (3.0 + 2.0×1) × (0.7651^1 × 2^1) = 5.0 × 1.530 = 8
Nivel 2: (3.0 + 2.0×2) × (0.7651^2 × 2^2) = 7.0 × 2.344 = 16
Nivel 3: (3.0 + 2.0×3) × (0.7651^3 × 2^3) = 9.0 × 3.599 = 32
Nivel 4: (3.0 + 2.0×4) × (0.7651^4 × 2^4) = 11.0 × 5.521 = 61
Nivel 5: nivel > maxDepth → 0
```
```
Total = 3 + 8 + 16 + 32 + 61 = 120
```
```
Básico: 13 unidades
Premium (+30%): 13 × 1.30 = 17 unidades
```
### 4⃣ RecetaValidator — Validar Ingredientes (40 líneas)

###### Clase con métodos estáticos para validar y consumir ingredientes.

**puedeProducir(Receta receta, Map<String, Integer> inventario)**

- Retorna true si tienes todos los ingredientes necesarios
- Si receta.getIngredientes() es null o vacío → return true (producción básica)
- Para cada ingrediente en la receta, verificar que
  inventario.get(ingrediente) >= cantidadRequerida
- Si algún ingrediente falta, retornar false

**consumirIngredientes(Receta receta, Map<String, Integer> inventario)**

- Llama esto DESPUÉS de verificar con puedeProducir()
- Para cada ingrediente en la receta:
  inventario.put(ingrediente, disponible - requerido)
- Modifica directamente el mapa de inventario (pasa por referencia)


### 5⃣ SnapshotManager — Serialización Binaria (20 líneas)

###### Clase con métodos estáticos para guardar/cargar el estado.

**guardar(EstadoCliente estado, String archivo)**

- Usa ObjectOutputStream con FileOutputStream
- Escribe el objeto completo con writeObject(estado)
- Puede lanzar IOException si falla la escritura

**cargar(String archivo)**

- Usa ObjectInputStream con FileInputStream
- Lee el objeto con readObject() y castea a EstadoCliente
- Puede lanzar IOException o ClassNotFoundException
- Si el archivo está corrupto, **lanzar SnapshotCorruptoException**

### 6⃣ ConfigLoader — Lectura de JSON (20 líneas)

###### Clase con métodos estáticos para cargar configuración inicial.

**cargarConfig(String archivo)**

- Lee el archivo config.json con Files.readString()
- Parsea el JSON manualmente o usa una librería simple (ej: Gson, Jackson)
- Retorna un objeto Config con: apiKey, equipo, host, puerto
- Si falta algún campo, **lanzar ConfiguracionInvalidaException**

**cargarRecetas(String archivo)**

- Lee el archivo recetas/[especie].json
- Parsea el JSON a un Map<String, Receta>
- Cada receta tiene: producto, ingredientes (puede ser null), bonusPremium
- Si el JSON es inválido, **lanzar ConfiguracionInvalidaException**


### 7⃣ ConsolaInteractiva — Comandos del Usuario (100-150

### líneas)

###### Clase que implementa un loop infinito para recibir comandos del usuario via Scanner. Es tu

###### interfaz principal durante el torneo.

###### Arquitectura básica:

```
public classConsolaInteractiva {
private ClienteBolsa cliente;
private Scanner scanner;
```
```
public void iniciar() {
while (true) {
System.out.print("\n> ");
String linea = scanner.nextLine().trim();
String[] partes = linea.split("\\s+");
String comando = partes[ 0 ].toLowerCase();
```
```
try {
switch (comando) {
case "login": /* ... */ break;
case "status": /* ... */ break;
// ... más casos
}
} catch(Exception e) {
System.out.println("❌ " + e.getMessage());
}
}
}
}
```
###### Comandos Requeridos:

##### login

###### Uso: login


###### Descripción: Aunque el login ya se hizo en Main , este comando puede mostrar el estado de

###### la conexión actual.

###### Salida:

```
✅ Conectado como EquipoAndromeda (Avocultores)
💰 Saldo inicial: $10,000.00
📦 Productos autorizados: [PALTA-OIL, GUACA, SEBO]
```
##### status

###### Uso: status

###### Descripción: Muestra tu situación financiera actual.

###### Salida:

```
📊 ESTADO ACTUAL
━━━━━━━━━━━━━━━━━━━━━
💰 Saldo: $12,450.00
📦 Valor inventario: $1,200.00
💎 Patrimonio neto: $13,650.00
📈 P&L: +36.50% ⬆
```
###### Implementación:


```
private voidmostrarStatus() {
EstadoCliente estado = cliente.getEstado();
```
```
double valorInv = 0.0;
for (Map.Entry<String, Integer> entry : estado.getInventario().entrySet()) {
double precio = estado.getPreciosActuales().getOrDefault(entry.getKey(), 0.0);
valorInv += entry.getValue() * precio;
}
```
```
double patrimonioNeto = estado.getSaldo() + valorInv;
double pl = estado.calcularPL();
```
```
System.out.println("\n📊 ESTADO ACTUAL");
System.out.println("━━━━━━━━━━━━━━━━━━━━━");
System.out.printf("💰 Saldo: $%.2f%n", estado.getSaldo());
System.out.printf("📦 Valor inventario: $%.2f%n", valorInv);
System.out.printf("💎 Patrimonio neto: $%.2f%n", patrimonioNeto);
System.out.printf("📈 P&L: %+.2f%% %s%n", pl, pl > 0? "⬆" : "⬇");
}
```
##### inventario

###### Uso: inventario

###### Descripción: Lista todos los productos que tienes con cantidad, precio actual y valor total.

###### Salida:

```
📦 INVENTARIO
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
PALTA-OIL: 23 unidades @ $26.00 = $598.00
FOSFO: 15 unidades @ $18.00 = $270.00
PITA: 8 unidades @ $22.00 = $176.00
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TOTAL: 46 unidades $1,044.00
```

##### precios

###### Uso: precios

###### Descripción: Muestra los precios actuales de mercado (del último ticker).

###### Salida:

```
💹 PRECIOS DE MERCADO
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
PALTA-OIL: $26.00 (bid: $25.50, ask: $26.50)
FOSFO: $18.00 (bid: $17.80, ask: $18.20)
GUACA: $35.00 (bid: $34.50, ask: $35.50)
...
```
##### comprar <producto> <cantidad> [mensaje]

###### Uso: comprar FOSFO 10 "Necesito para premium"

###### Descripción: Compra un producto del mercado.

###### Validaciones:

- Verifica que el precio esté disponible (ticker recibido)
- Lanza SaldoInsuficienteException si no hay dinero
- Envía la orden al servidor

###### Salida:

```
📤 Orden enviada: COMPRAR 10 FOSFO
... (espera 1-10 segundos) ...
💰 COMPRA: 10 FOSFO @ $18.20 = -$182.00
💬 "Fresh batch from the mines!"
💰 Nuevo saldo: $12,268.00
📈 P&L: +22.68%
```

##### vender <producto> <cantidad> [mensaje]

###### Uso: vender PALTA-OIL 15 "Premium quality!"

###### Descripción: Vende un producto al mercado.

###### Validaciones:

- Lanza InventarioInsuficienteException si no tienes suficiente
- Envía la orden al servidor

###### Salida:

```
📤 Orden enviada: VENDER 15 PALTA-OIL
... (espera 1-10 segundos) ...
💵 VENTA: 15 PALTA-OIL @ $26.00 = +$390.00
💬 "Thanks! Great quality!"
💰 Nuevo saldo: $12,658.00
📈 P&L: +26.58%
```
##### producir <producto> <basico|premium>

###### Uso: producir PALTA-OIL basico

###### Uso: producir GUACA premium

###### Descripción: Produce unidades de un producto.

###### Validaciones:

- Lanza ProductoNoAutorizadoException si no puedes producir ese producto
- Lanza RecetaNoEncontradaException si la receta no existe
- Lanza IngredientesInsuficientesException si faltan ingredientes para premium

###### Salida (básico):

```
✅ Producidas 13 unidades de PALTA-OIL (básico)
📦 Inventario actualizado: 13 PALTA-OIL
```
###### Salida (premium):


```
🔧 Consumiendo ingredientes: 5 FOSFO, 3 PITA
✅ Producidas 17 unidades de GUACA (premium +30%)
📦 Inventario actualizado: 17 GUACA
```
##### ofertas

###### Uso: ofertas

###### Descripción: Lista las ofertas pendientes que has recibido (si el callback las guardó).

###### Salida:

```
📬 OFERTAS PENDIENTES
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
[1] Comprador: EquipoBeta
Producto: PITA × 15
Precio ofrecido: $23.00 (10% premium)
OfferId: OFFER-1234567890
```
```
[2] Comprador: EquipoGamma
Producto: FOSFO × 8
Precio ofrecido: $19.50 (8% premium)
OfferId: OFFER-0987654321
```
##### aceptar <offerId>

###### Uso: aceptar OFFER-1234567890

###### Descripción: Acepta una oferta pendiente.

###### Salida:

```
✅ Aceptando oferta OFFER-1234567890...
💵 VENTA: 15 PITA @ $23.00 = +$345.00
💬 "Perfect timing!"
```

##### rechazar <offerId> [motivo]

###### Uso: rechazar OFFER-1234567890 "Precio muy bajo"

###### Descripción: Rechaza una oferta pendiente (opcional, pueden expirar solas).

##### snapshot save

###### Uso: snapshot save

###### Descripción: Guarda el estado actual en un archivo binario.

###### Salida:

```
💾 Guardando snapshot...
✅ Snapshot guardado: snapshots/snapshot_1704234567890.bin
```
##### snapshot load

###### Uso: snapshot load

###### Descripción: Lista y carga snapshots disponibles.

###### Interacción:

```
📂 Snapshots disponibles:
```
1. snapshot_1704234567890.bin (hace 2 minutos) - P&L: +25.4%
2. snapshot_1704234556780.bin (hace 5 minutos) - P&L: +18.2%
3. snapshot_1704234545670.bin (hace 7 minutos) - P&L: +12.1%

```
Selecciona snapshot (1-3): 1
✅ Estado cargado correctamente
💰 Saldo: $12,540.00
📈 P&L: +25.40%
```

##### resync

###### Uso: resync

###### Descripción: Sincroniza eventos perdidos después de un crash.

###### Salida:

```
🔄 Sincronizando eventos desde 14:32:45...
📥 Recibidos 3 FILLs perdidos:
```
1. VENTA: 10 FOSFO @ $18.00
2. COMPRA: 5 PITA @ $22.00
3. VENTA: 13 FOSFO @ $19.00
   ✅ Estado sincronizado
   💰 Saldo actualizado: $12,650.00

#### ayuda o help

###### Uso: ayuda

###### Descripción: Lista todos los comandos disponibles.

##### exit

###### Uso: exit

###### Descripción: Cierra el programa.

###### Salida:

```
👋 Cerrando cliente...
💾 Guardando snapshot final...
✅ ¡Hasta luego! Tu P&L final fue: +42.3%
```
###### Manejo de errores en la consola:


```
try {
switch (comando) {
case"comprar":
cliente.comprar(producto, cantidad, mensaje);
break;
}
} catch (SaldoInsuficienteException e) {
System.out.println("❌ Saldo insuficiente");
System.out.println(" Necesitas: $" + e.getCostoRequerido());
System.out.println(" Tienes: $" + e.getSaldoActual());
} catch (InventarioInsuficienteException e) {
System.out.println("❌ Inventario insuficiente");
System.out.println(" Necesitas: " + e.getRequerido() + " " + e.getProducto());
System.out.println(" Tienes: " + e.getDisponible());
} catch (ProductoNoAutorizadoException e) {
System.out.println("❌ No puedes producir " + e.getProducto());
System.out.println(" Solo puedes: " + e.getProductosPermitidos());
} catch (Exception e) {
System.out.println("❌ Error: " + e.getMessage());
}
```
###### Tips de implementación:

###### 1. Usa un Map<String, Offer> para guardar ofertas pendientes por offerId

###### 2. Parsea los argumentos con String.split("\\s+") y maneja casos de argumentos

###### insuficientes

###### 3. Para mensajes con espacios, usa String.join(" ", Arrays.copyOfRange(...))

###### 4. Muestra ayuda si el usuario escribe mal un comando

###### 5. Usa colores ANSI si quieres hacer la consola más vistosa (opcional)

### 8⃣ DTOs Propios (100 líneas)

###### Clases simples para representar datos del dominio. Todas deben ser serializables.

###### Rol (contiene parámetros del algoritmo recursivo):

- int branches : Número de ramas por nivel


- int maxDepth : Profundidad máxima del árbol
- double decay : Factor de decaimiento por nivel
- double baseEnergy : Energía base
- double levelEnergy : Energía adicional por nivel

###### Receta :

- String producto : Nombre del producto a producir
- Map<String, Integer> ingredientes : Null para básico, mapa para premium
- double bonusPremium : Típicamente 1.30 (30% bonus)

###### Config :

- String apiKey : Token de autenticación
- String equipo : Nombre del equipo
- String host : IP del servidor
- int puerto : Puerto TCP

###### Todas estas clases solo necesitan:

- Constructor vacío
- Constructor con parámetros
- Getters y setters
- implements Serializable (excepto Config si no se serializa)

### 9⃣ (OPCIONAL - BONUS) AutoProduccionManager — Auto-

### Producción Inteligente

###### Extiende TareaAutomatica para producir automáticamente cada N segundos sin

###### intervención manual. Esto te libera para enfocarte en trading mientras la producción corre en

###### segundo plano.

###### ¿Por qué es útil?

###### Durante el torneo de 15 minutos, tu tiempo es limitado. Si automatizas la producción, puedes

###### dedicarte completamente a:


- Analizar precios del mercado
- Buscar oportunidades de arbitraje
- Responder a ofertas de otros traders
- Optimizar tus compras y ventas

###### Estrategia de Auto-Producción Inteligente:

###### El algoritmo debe decidir dinámicamente entre producción básica o premium:

###### 1. Verificar ingredientes : ¿Tengo todo lo necesario para premium?

- SÍ → Producir premium (+30% bonus)
- NO → Producir básico

###### 2. Si produje básico : Vender inmediatamente para conseguir capital

- Así acumulas efectivo para comprar ingredientes
- El ciclo se repite: básico → vender → comprar ingredientes → premium

###### 3. Si produje premium : NO vender automáticamente

- Los productos premium valen más
- Deja que el usuario decida cuándo vender (manual o con otra estrategia)

###### Implementación sugerida:


public classAutoProduccionManager extends TareaAutomatica {

```
private ClienteBolsa cliente;
private String productoBasico; // ej: "PALTA-OIL"
private String productoPremium; // ej: "GUACA"
```
```
public AutoProduccionManager(ClienteBolsa cliente,
String productoBasico,
String productoPremium) {
this.cliente = cliente;
this.productoBasico = productoBasico;
this.productoPremium = productoPremium;
}
```
```
@Override
protected voidejecutar() {
try {
EstadoCliente estado = cliente.getEstado();
```
```
// Estrategia 1: Intentar premium primero
Receta recetaPremium = estado.getRecetas().get(productoPremium);
boolean puedePremium = RecetaValidator.puedeProducir(
recetaPremium,
estado.getInventario()
);
```
```
if (puedePremium) {
// PRODUCIR PREMIUM (no vender automáticamente)
cliente.producir(productoPremium, true);
System.out.println("[AUTO] Producción premium: " + productoPremium);
```
```
} else{
// PRODUCIR BÁSICO + VENDER INMEDIATAMENTE
cliente.producir(productoBasico, false);
System.out.println("[AUTO] Producción básica: " + productoBasico);
```
```
// Vender todo el básico para conseguir capital
int cantidad = estado.getInventario()
.getOrDefault(productoBasico, 0 );
```

```
if (cantidad > 0 ) {
cliente.vender(productoBasico, cantidad, "Auto-venta");
System.out.println("[AUTO] Vendidas " + cantidad +
" unidades de " + productoBasico);
}
}
```
```
} catch (Exception e) {
System.out.println("[AUTO] Error: " + e.getMessage());
}
}
}
```
```
// En tu Main (después del login):
AutoProduccionManager autoProductor = new AutoProduccionManager(
cliente,
"PALTA-OIL", // básico
"GUACA" // premium
);
autoProductor.iniciar( 60 ); // Cada 60 segundos
```
```
System.out.println("✅ Auto-producción activada (cada 60s)");
```
###### Ventajas de esta estrategia:

###### 1. Maximiza producción : Siempre produce, nunca está idle

###### 2. Inteligente : Prefiere premium cuando hay ingredientes

###### 3. Genera capital : Vende básico automáticamente para comprar ingredientes

###### 4. Manos libres : Tú te enfocas en trading estratégico

###### Consideraciones:

- **Intervalo recomendado** : 60 segundos (ni muy rápido ni muy lento)
- **Thread-safety** : El método ejecutar() corre en un thread separado, pero el SDK

###### maneja la sincronización

- **Errores** : Siempre envolver en try-catch porque si falla, no debe detener el programa
- **Detener al final** : Llama autoProductor.detener() antes de cerrar el programa


###### Puntos bonus:

###### Si implementas auto-producción bien, puedes ganar hasta +5% de puntos en la evaluación.

###### Los criterios son:

- Usa TareaAutomatica correctamente
- Estrategia inteligente (básico vs premium)
- Manejo de errores apropiado
- No interfiere con operaciones manuales

## ⚠⚠ ERRORES DEL SERVIDOR

###### Todos los errores llegan via onError(ErrorMessage error).

###### Código Razón Qué Hacer

###### INVALID_TOKEN Token no existe Verificar config.json

###### ALREADY_CONNECTED Sesión activa Esperar 30s o reiniciar servidor

###### INSUFFICIENT_BALANCE Saldo insuficiente ⚠ Bug en validación local

###### INSUFFICIENT_INVENTORY No tienes el producto ⚠ Bug en validación local

###### INVALID_PRODUCT Producto no existe Validar contra catálogo

```
UNAUTHORIZED_PRODUCT
```
###### No puedes producir

###### eso

###### Ver productosAutorizados

###### INVALID_QUANTITY Cantidad inválida Validar 1 ≤ qty ≤ 10,000

###### OFFER_EXPIRED La oferta ya expiró

###### Responder más rápido

###### manualmente

```
RATE_LIMIT
```
###### Demasiadas órdenes/

###### seg

###### Espaciar pedidos (mín 100ms)

###### INTERNAL_ERROR Error del servidor Reportar al profesor


## 📐📐 DIAGRAMA DE CLASES

###### Puedes encontrar el detalle completo en estelink


## 🔄🔄 FLUJOS COMPLETOS

### Flujo 1: Login → Producir → Vender


┌─────────────────────────────────────────────────────────────┐
│ PASO 1: LOGIN │
└─────────────────────────────────────────────────────────────┘

Usuario ejecuta: java Main
↓
main() crea ConectorBolsa y ClienteBolsa
↓
conector.conectar("localhost", 9000)
↓
conector.login("TK-ANDROMEDA-2025-AVOCULTORES", cliente)
↓
SDK envía LOGIN por TCP
↓
Servidor responde LOGIN_OK
↓
SDK llama: cliente.onLoginOk(msg)
↓
Cliente inicializa estado:

- estado.setSaldo(10000)
- estado.setRecetas(...)
- estado.setRol(...)

┌─────────────────────────────────────────────────────────────┐
│ PASO 2: PRODUCIR │
└─────────────────────────────────────────────────────────────┘

Usuario escribe: producir PALTA-OIL basico
↓
ConsolaInteractiva llama: cliente.producir("PALTA-OIL", false)
↓
ClienteBolsa.producir():

1. Validar: ¿PALTA-OIL está en productosAutorizados? ✓
2. No es premium, no consumir ingredientes
3. CalculadoraProduccion.calcularUnidades(rol) → 13
4. inventario.put("PALTA-OIL", 13)
5. conector.enviarProduccion("PALTA-OIL", 13)
   ↓
   SDK serializa y envía por TCP


↓
Servidor responde PRODUCTION_ACK
↓
Imprime: "✅ Producidas 13 unidades de PALTA-OIL"

┌─────────────────────────────────────────────────────────────┐
│ PASO 3: VENDER │
└─────────────────────────────────────────────────────────────┘

Usuario escribe: vender PALTA-OIL 13 "Fresh production!"
↓
ConsolaInteractiva llama: cliente.vender("PALTA-OIL", 13, "...")
↓
ClienteBolsa.vender():

1. Validar inventario: tengo 13 PALTA-OIL ✓
2. Crear Orden (side=SELL, qty=13, msg="...")
3. conector.enviarOrden(orden)
   ↓
   SDK serializa y envía por TCP
   ↓
   Servidor responde ORDER_ACCEPTED
   ↓
   ... servidor busca comprador (1-10s) ...
   ↓
   Servidor envía FILL
   ↓
   SDK parsea JSON → Fill objeto
   ↓
   SDK llama: cliente.onFill(fill)
   ↓
   ClienteBolsa.onFill():
   if fill.getSide() == "SELL":
- estado.saldo += (13 × $26.00) = +$338
- inventario["PALTA-OIL"] -= 13 → 0
  ↓
  Imprime:
  "💵 VENTA: 13 PALTA-OIL @ $26.00"
  "💬 \"Great quality!\""
  "💰 Nuevo saldo: $10,338.00"
  "📈 P&L: +3.38%"


### Flujo 2: Crash → Recovery

```
┌─────────────────────────────────────────────────────────────┐
│ SITUACIÓN: Programa crashea durante el torneo │
└─────────────────────────────────────────────────────────────┘
```
```
T=5:00 → último snapshot automático guardado
T=5:30 → crash (conexión perdida)
T=5:45 → usuario reinicia programa
```
```
┌─────────────────────────────────────────────────────────────┐
│ RECOVERY MANUAL │
└─────────────────────────────────────────────────────────────┘
```
```
> snapshot load
📂 Snapshots disponibles:
```
1. snapshot_1234567890.bin (T=5:00)
2. snapshot_1234567891.bin (T=4:30)
   Selecciona: 1
   ✅ Estado cargado: T=5:00
   💰 Saldo: $12,450.00
   📦 Inventario: 23 FOSFO, 15 PITA

```
> login
✅ Reconectado al servidor
```
```
> resync
🔄 Sincronizando eventos desde T=5:00...
📥 Recibidos 3 FILLs:
```
1. VENTA: 10 FOSFO @ $18.00
2. COMPRA: 5 PITA @ $22.00
3. VENTA: 13 FOSFO @ $19.00
   ✅ Estado sincronizado

```
> status
💰 Saldo: $12,560.00
📈 P&L: +25.60%
```

## 🏆🏆 EL TORNEO DE 15 MINUTOS

### ¿Qué es el Torneo?

###### El torneo es una competencia en tiempo real de 15 minutos donde todos los equipos

###### conectados compiten simultáneamente en el mismo mercado. El objetivo es simple pero

###### desafiante:

###### 🎯🎯 Conseguir el mayor P&L% (Profit & Loss) comprando y vendiendo productos

###### Todos empiezan con el mismo saldo inicial ($10,000), pero cada especie tiene ventajas

###### diferentes:

- Productos básicos distintos (producción gratis)
- Productos premium únicos (requieren ingredientes específicos)
- Parámetros del algoritmo recursivo diferentes (más o menos unidades)

###### El ganador es quien termine con el mayor P&L% , calculado como:

```
P&L% = ((Patrimonio Final - $10,000) / $10,000) × 100
```
###### ⚠⚠ CRÍTICO : Solo cuenta el efectivo al final. El inventario sin vender vale $0 porque el

###### mercado cierra en T=15:00 y no hay liquidez.

### Estrategia General

###### La estrategia básica que todos siguen:

###### 1. Producir productos (básicos o premium)

###### 2. Vender tus productos en el mercado

###### 3. Comprar ingredientes que necesitas de otros

###### 4. Repetir el ciclo para acumular ganancias

###### 5. Liquidar todo antes de T=13:00 (vender inventario completo)

###### La interdependencia es clave: necesitas productos de otras especies para hacer premium, y

###### ellas necesitan los tuyos. El mercado funciona porque todos se necesitan mutuamente.


### Fases del Torneo

###### Tiempo Fase Actividad Principal Estrategia Sugerida

###### T=0-3 min WARMUP

###### Establecer posición

###### inicial

- Producir básico
- Vender para capital inicial
- Observar precios del

###### mercado

###### T=3-10

###### min

###### ACTIVE

###### TRADING

###### Maximizar

###### transacciones

- Comprar ingredientes

###### baratos

- Producir premium (30%

###### bonus)

- Vender premium con

###### margen

- Repetir el ciclo

###### T=10-13

###### min

###### VOLATILITY

###### Aprovechar

###### fluctuaciones

- Aumentar volumen de

###### trades

- Precios pueden subir/bajar
- Buscar oportunidades

###### arbitraje

###### T=13-15

###### min

###### CLOSING ⚠ LIQUIDAR TODO

- VENDER TODO EL

###### INVENTARIO

- Convertir productos a

###### efectivo

- Solo efectivo cuenta al final

### Ejemplo de Ciclo Rentable

###### Especie: Avocultores


```
T=0:00 → Producir PALTA-OIL básico (gratis) → 13 unidades
T=0:30 → Vender 13 PALTA-OIL @ $26.00 → +$338 efectivo
Saldo: $10,338
```
```
T=1:00 → Comprar 5 FOSFO @ $18.00 → -$90
Comprar 3 PITA @ $22.00 → -$66
Saldo: $10,182
```
```
T=1:30 → Producir GUACA premium (consume 5 FOSFO + 3 PITA) → 17 unidades
T=2:00 → Vender 17 GUACA @ $35.00 → +$595
Saldo: $10,777
P&L: +7.77%
```
```
T=2:30 → Repetir el ciclo...
```
###### Análisis : En 2.5 minutos, pasaste de +3.38% a +7.77% de P&L. Si mantienes este ritmo y

###### optimizas, puedes llegar a +50-100% en 15 minutos.

### Evaluación

###### Funcionalidad Puntos Requiere

###### Login y callbacks 12% Implementar EventListener completo

###### Comprar y vender 18% Validaciones locales + enviar órdenes

###### Producir (básico +

###### premium)

###### 22% Algoritmo recursivo + validación recetas

###### Excepciones

###### personalizadas

###### 15%

###### Implementar 7 excepciones + manejo con try-

###### catch

###### Snapshots + recovery 13% Serialización binaria + resync

###### Comandos de consola 8% Scanner + parser básico

###### P&L y análisis 7% Cálculo correcto de patrimonio


###### Funcionalidad Puntos Requiere

###### Manejo de errores del

###### servidor

###### 5% onError() con todos los casos

###### BONUS: Torneo top 3 +10% Mejor estrategia de trading

###### BONUS: Auto-producción +5% TareaAutomatica implementada

###### Total base: 100%

###### Total máximo: 115% (con bonuses)

###### Desglose de excepciones (15%):

- 7% por crear las 7 excepciones requeridas con jerarquía correcta
- 8% por lanzarlas apropiadamente en validaciones y manejarlas con try-catch


## 🚀🚀 SETUP Y PRIMEROS PASOS

### Estructura de Proyecto

```
proyecto-bolsa/
├── src/
│ ├── ClienteBolsa.java
│ ├── EstadoCliente.java
│ ├── CalculadoraProduccion.java
│ ├── RecetaValidator.java
│ ├── SnapshotManager.java
│ ├── ConfigLoader.java
│ ├── ConsolaInteractiva.java
│ ├── Rol.java
│ ├── Receta.java
│ ├── Config.java
│ └── Main.java
├── lib/
│ └── bolsa-sdk-1.0.jar
├── config.json
├── recetas/
│ └── avocultores.json
├── snapshots/
└── pom.xml (o build.gradle)
```
### config.json

```
{
"apiKey": "TK-ANDROMEDA-2025-AVOCULTORES",
"equipo": "EquipoAndromeda",
"servidor": {
"host": "localhost",
"puerto": 9000
}
}
```

### recetas/avocultores.json

```
{
"PALTA-OIL": {
"producto": "PALTA-OIL",
"ingredientes": null,
"bonusPremium": 1.0
},
"GUACA": {
"producto": "GUACA",
"ingredientes": {
"FOSFO": 5 ,
"PITA": 3
},
"bonusPremium": 1.3
},
"SEBO": {
"producto": "SEBO",
"ingredientes": {
"NUCREM": 8
},
"bonusPremium": 1.3
}
}
```
### ✅✅ Checklist de Implementación

###### Semana 1: Fundamentos

###### DTOs básicos ( Rol , Receta , Config )

###### ConfigLoader (leer JSON)

###### Excepciones personalizadas (7 clases de excepción)

###### EstadoCliente con calcularPL()

###### CalculadoraProduccion (recursivo) — CRÍTICO

###### Probar algoritmo recursivo con casos de prueba


###### Semana 2: Conexión y Trading

###### ClienteBolsa (implementar EventListener )

###### Callbacks: onLoginOk() , onFill() , onError()

###### Métodos públicos: comprar() , vender() con validaciones

###### Lanzar excepciones personalizadas en validaciones

###### ConsolaInteractiva básica (login, status, inventario)

###### Probar con servidor (login + comprar/vender)

###### Semana 3: Producción y Avanzado

###### RecetaValidator (validar ingredientes)

###### ClienteBolsa.producir() (integrar todo)

###### Comandos de producción en consola

###### SnapshotManager (serialización binaria)

###### onOffer() (responder ofertas)

###### Manejo completo de errores en onError()

###### Try-catch en toda la aplicación

###### (OPCIONAL) AutoProduccionManager

## ❓❓ PREGUNTAS FRECUENTES

###### P: ¿El SDK implementa el algoritmo recursivo?

###### R: NO. TÚ lo implementas desde cero en CalculadoraProduccion.

###### P: ¿Cómo se conecta mi código con el SDK?

###### R: En el main() : creas ConectorBolsa , creas tu ClienteBolsa , pasas tu objeto al SDK via

###### login(). El SDK llama tus callbacks automáticamente.

###### P: ¿Todos los errores van a onError()?

###### R: SÍ. Login fallido, orden rechazada, producción inválida, oferta expirada — todos llegan via

###### onError(ErrorMessage error).

###### P: ¿Cuál es la diferencia entre las excepciones del SDK y mis excepciones?

###### R: Las excepciones del SDK ( ConexionFallidaException , TimeoutException ) tratan con


###### problemas de red. Tus excepciones personalizadas tratan con lógica de negocio (saldo

###### insuficiente, ingredientes faltantes, etc.). Las del SDK se lanzan en métodos del SDK. Las

###### tuyas se lanzan en tu código de validación.

###### P: ¿Dónde debo lanzar mis excepciones personalizadas?

###### R: En tus métodos de validación ANTES de enviar algo al servidor. Por ejemplo: comprar()

###### debe lanzar SaldoInsuficienteException si no tienes dinero, ANTES de llamar

###### conector.enviarOrden(). Si el servidor rechaza, eso llega via onError() , no via

###### excepción.

###### P: ¿Por qué JSON para config y binario para snapshots?

###### R: JSON es para configuración ESTÁTICA (escribes a mano, lees 1 vez al inicio).

###### Binario es para estado DINÁMICO (tu programa genera automáticamente, cambia

###### constantemente con cada transacción).

###### P: ¿Debo validar localmente antes de enviar órdenes?

###### R: SÍ. Si el servidor rechaza por saldo insuficiente, perdiste tiempo valioso. Valida saldo e

###### inventario localmente ANTES de llamar conector.enviarOrden().

###### P: ¿Qué pasa si crasheo durante el torneo?

###### R: Ejecuta: snapshot load → login → resync. Pierdes máximo el tiempo desde tu último

###### snapshot (recomendado: auto-guardar cada 30s).

###### P: ¿Puedo usar threads adicionales?

###### R: SÍ, pero debes sincronizar el acceso a EstadoCliente. El SDK ya usa un thread interno

###### para TCP.

###### P: ¿Qué pasa en T=15:00 si tengo inventario?

###### R: Tu inventario vale $0 porque no hay liquidez. Solo cuenta el efectivo. POR ESO debes

###### liquidar TODO en T=13:00.


## 🏁🏁 RESUMEN FINAL

### Separación de Responsabilidades

###### Aspecto SDK TÚ

###### Networking TCP ✅ ❌

###### Threading básico ✅ ❌

###### Callbacks (interface) ✅ Definida ✅ Implementada

###### Excepciones de red

###### ✅ ConexionFallidaException,

###### TimeoutException

###### ❌

###### Excepciones de

###### negocio

###### ❌

###### ✅ 7 excepciones

###### personalizadas

###### Configuración JSON ❌ ✅ Leer

###### Estado runtime ❌

###### ✅ Mantener + serializar

###### binario

###### Algoritmo de

###### producción

###### ❌ ✅ Recursivo completo

###### Validaciones

###### (recetas, saldo)

###### ❌

###### ✅ Antes de enviar + lanzar

###### excepciones

###### Cálculo de P&L ❌ ✅ En tiempo real

###### Snapshots ❌ ✅ Binario automático

###### Comandos de

###### consola

###### ❌ ✅ Parser y ejecución

###### Estrategia de

###### trading

###### ❌ ✅ Decidir y ejecutar

###### Manejo de errores

###### del servidor

###### ✅ Entregar via onError() ✅ Mostrar y reaccionar


### Puntos Clave

###### 1. Arquitectura : En main() creas ConectorBolsa (SDK) y tu ClienteBolsa (implementa

###### EventListener), luego pasas tu objeto al SDK via login().

###### 2. Callbacks : El SDK llama tus métodos ( onFill , onError , etc.) desde su thread cuando

###### llegan mensajes del servidor.

###### 3. Texto vs Binario :

- **JSON** para config/recetas (estático, escribes manualmente, lees 1 vez)
- **Binario** para snapshots (dinámico, tu programa genera automáticamente, cambia

###### constantemente)

###### 4. Errores : Todos los errores del servidor llegan via onError(). Solo las excepciones de

###### red se lanzan.

###### 5. Validaciones : Valida localmente ANTES de enviar órdenes. El servidor rechazará órdenes

###### inválidas y perderás tiempo.

###### 6. Torneo : En T=13:00, VENDE TODO. Inventario sin vender = $0. Solo cuenta efectivo.

###### 🥑 ¡Que el aguacate te acompañe, comerciante! 🥑

###### "En Andoria, el éxito no es solo producir. Es entender que los Avocultores necesitan a los

###### Monjes, los Mineros dependen de los Someliers. Quien comprenda la red de

###### interdependencias Y liquide su inventario a tiempo, dominará el mercado."

###### — Juan Carlos Bodoque, AI-Oráculo


