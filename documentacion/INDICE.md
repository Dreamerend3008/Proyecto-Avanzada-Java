# 📚 Índice de Documentación del Proyecto

## 🚀 ¿Por dónde empiezo?

Sigue esta ruta de aprendizaje en orden:

### 1️⃣ **TUTORIAL_PRIMER_DIA.md** ⭐ EMPIEZA AQUÍ
**Tiempo:** 2-3 horas  
**Para:** Desarrolladores nuevos que nunca han trabajado en el proyecto  
**Contenido:**
- Instalación y configuración paso a paso
- Tu primera compilación
- Tu primera modificación al código
- Implementación de una estrategia simple
- Tu primer commit

👉 **Si es tu primer día, empieza por este documento.**

---

### 2️⃣ **README.md** 📖 GUÍA PRINCIPAL
**Tiempo:** 30 minutos de lectura  
**Para:** Todos los desarrolladores  
**Contenido:**
- Requisitos del sistema
- Arquitectura del proyecto
- Estructura de archivos
- Comandos útiles
- Solución de problemas comunes
- Estándares de código
- Flujo de trabajo con Git

👉 **Lee esto después de completar el tutorial del primer día.**

---

### 3️⃣ **DESARROLLO_EJEMPLOS.md** 💡 EJEMPLOS PRÁCTICOS
**Tiempo:** Referencia continua  
**Para:** Desarrolladores implementando lógica de negocio  
**Contenido:**
- Estrategias de trading completas (Market Maker, Arbitraje)
- Sistema de gestión de inventario
- Sistema de gestión de órdenes
- Analizador de mercado con tendencias
- Gestor de riesgo
- Tests unitarios

👉 **Úsalo como referencia cuando implementes nuevas funcionalidades.**

---

### 4️⃣ **AGENTS.md** 🎯 PRINCIPIOS DE CÓDIGO
**Tiempo:** 20 minutos de lectura  
**Para:** Todos los desarrolladores  
**Contenido:**
- Regla de "No else"
- Guard clauses
- Convenciones de nombres
- Patrones de diseño recomendados
- Cómo escribir código limpio

👉 **Lee esto antes de hacer tu primer Pull Request.**

---

### 5️⃣ **BUILD_STATUS.md** ✅ ESTADO DEL BUILD
**Tiempo:** 5 minutos  
**Para:** Desarrolladores con problemas de compilación  
**Contenido:**
- Estado actual del sistema de build
- Dependencias configuradas
- Comandos disponibles

👉 **Consulta esto si tienes problemas de compilación.**

---

## 📊 Ruta de Aprendizaje Visual

```
DÍA 1: TUTORIAL_PRIMER_DIA.md
   ↓
   ✅ Entorno configurado
   ✅ Primera compilación exitosa
   ✅ Primera modificación
   ✅ Primer commit
   ↓
DÍA 2: README.md
   ↓
   ✅ Entendimiento de la arquitectura
   ✅ Conocimiento de comandos
   ✅ Comprensión del flujo de trabajo
   ↓
DÍA 3+: DESARROLLO_EJEMPLOS.md + AGENTS.md
   ↓
   ✅ Implementación de estrategias
   ✅ Código limpio y profesional
   ✅ Tests unitarios
```

---

## 🎯 Guías por Objetivo

### "Quiero configurar mi entorno"
→ **TUTORIAL_PRIMER_DIA.md** (Pasos 1-4)

### "Quiero entender la arquitectura"
→ **README.md** (Sección: Arquitectura y Componentes)

### "Quiero implementar una estrategia de trading"
→ **DESARROLLO_EJEMPLOS.md** (Sección: Estrategias de Trading Básicas)

### "Quiero gestionar inventario"
→ **DESARROLLO_EJEMPLOS.md** (Sección: Gestión de Inventario)

### "Quiero hacer un sistema de órdenes"
→ **DESARROLLO_EJEMPLOS.md** (Sección: Gestión de Órdenes)

### "Quiero analizar tendencias de mercado"
→ **DESARROLLO_EJEMPLOS.md** (Sección: Sistema de Análisis de Mercado)

### "Quiero implementar gestión de riesgo"
→ **DESARROLLO_EJEMPLOS.md** (Sección: Gestión de Riesgo)

### "Quiero escribir tests"
→ **DESARROLLO_EJEMPLOS.md** (Sección: Tests Unitarios)

### "Tengo un error de compilación"
→ **README.md** (Sección: Solución de Problemas)  
→ **BUILD_STATUS.md**

### "¿Cómo escribo código limpio?"
→ **AGENTS.md**

### "¿Qué comandos puedo usar?"
→ **README.md** (Sección: Comandos Útiles)

---

## 📁 Estructura de Archivos del Proyecto

```
Proyecto-Avanzada-Java/
│
├── 📚 DOCUMENTACIÓN
│   ├── INDICE.md                    ← Estás aquí
│   ├── TUTORIAL_PRIMER_DIA.md       ← Empieza aquí si eres nuevo
│   ├── README.md                     ← Guía principal
│   ├── DESARROLLO_EJEMPLOS.md       ← Ejemplos de código
│   ├── AGENTS.md                     ← Principios de código
│   └── BUILD_STATUS.md              ← Estado del build
│
├── 📂 CÓDIGO FUENTE
│   └── src/main/java/tech/hellsoft/trading/
│       ├── Main.java                 ← Punto de entrada
│       ├── config/                   ← Configuración
│       ├── exception/                ← Excepciones
│       ├── model/                    ← Modelos de dominio
│       ├── service/                  ← Servicios principales
│       │   └── impl/                 ← Implementaciones
│       └── util/                     ← Utilidades
│
├── 🔧 CONFIGURACIÓN
│   ├── config.json                   ← Tu configuración (NO subir a Git)
│   ├── gradle.properties            ← Tus credenciales (NO subir a Git)
│   ├── config.sample.json           ← Plantilla de config
│   ├── gradle.properties.sample     ← Plantilla de credenciales
│   └── build.gradle.kts             ← Configuración de Gradle
│
└── 🏗️ BUILD
    └── build/                        ← Archivos compilados
```

---

## 🎓 Glosario de Términos

### Trading
- **Ticker**: Actualización de precios de un producto
- **Bid**: Precio de compra (lo que alguien está dispuesto a pagar)
- **Ask**: Precio de venta (lo que alguien está dispuesto a recibir)
- **Spread**: Diferencia entre Bid y Ask
- **Fill**: Ejecución de una orden
- **Order**: Orden de compra o venta
- **Inventory**: Inventario de productos que tienes

### Estrategias
- **Market Maker**: Comprar barato, vender caro con un spread
- **Arbitraje**: Comprar en un mercado, vender en otro
- **Crafteo**: Comprar ingredientes, crear producto, vender con ganancia

### Programación
- **Guard Clause**: Validación temprana que sale del método
- **Record**: Clase inmutable para datos (Java 25)
- **Interface**: Contrato que define métodos
- **Implementation**: Clase que implementa una interface
- **DTO**: Data Transfer Object (objeto para transferir datos)

---

## 🆘 Ayuda Rápida

### Comandos Más Usados
```bash
# Compilar
gradlew.bat build

# Ejecutar
gradlew.bat run

# Formatear código
gradlew.bat spotlessApply

# Verificar estilo
gradlew.bat checkstyleMain

# Ejecutar tests
gradlew.bat test
```

### Archivos Importantes
- `src/main/resources/config.json` - Tu configuración del bot
- `src/main/java/tech/hellsoft/trading/Main.java` - Punto de entrada
- `src/main/java/tech/hellsoft/trading/service/impl/SDKTradingService.java` - Donde agregas lógica

### Errores Comunes
| Error | Solución |
|-------|----------|
| Configuration file not found | Crear `config.json` desde `config.sample.json` |
| 401 Unauthorized | Verificar `gradle.properties` con credenciales de GitHub |
| Login failed | Verificar API key en `config.json` |
| IDE no reconoce clases | Reload Gradle Project |

---

## 📞 Contacto y Soporte

### ¿Tienes una pregunta?
1. **Busca en la documentación**: Usa Ctrl+F en cada archivo
2. **Revisa ejemplos**: `DESARROLLO_EJEMPLOS.md` tiene código completo
3. **Consulta errores comunes**: `README.md` → Solución de Problemas
4. **Pregunta al equipo**: Si llevas >30 min atascado

### ¿Encontraste un bug en la documentación?
- Crea un issue en GitHub
- O corrígelo y haz un Pull Request

---

## 🎯 Checklist de Progreso

Marca lo que ya completaste:

### Configuración Inicial
- [ ] Java 25 instalado
- [ ] Proyecto clonado
- [ ] `gradle.properties` configurado
- [ ] `config.json` configurado
- [ ] Primera compilación exitosa
- [ ] IDE configurado

### Primer Desarrollo
- [ ] Tutorial del primer día completado
- [ ] Primera modificación al código
- [ ] Estrategia simple implementada
- [ ] Primer commit realizado
- [ ] README.md leído completamente

### Desarrollo Intermedio
- [ ] AGENTS.md leído
- [ ] Primera estrategia avanzada implementada
- [ ] Sistema de inventario creado
- [ ] Sistema de órdenes implementado
- [ ] Primer Pull Request aprobado

### Desarrollo Avanzado
- [ ] Análisis de mercado implementado
- [ ] Gestión de riesgo agregada
- [ ] Tests unitarios escritos
- [ ] Estrategia completa funcionando
- [ ] Documentación actualizada

---

## 🏆 Objetivos del Proyecto

El objetivo final es crear un bot de trading que:

1. ✅ Se conecte al servidor de trading
2. ✅ Reciba actualizaciones de mercado en tiempo real
3. ✅ Analice oportunidades de trading
4. ✅ Gestione inventario de productos
5. ✅ Envíe órdenes de compra/venta
6. ✅ Gestione riesgo financiero
7. ✅ Optimice ganancias
8. ✅ Funcione de manera autónoma

---

## 💡 Consejos para Equipos

### División de Trabajo Sugerida

**Persona 1: Estrategias de Trading**
- Implementar algoritmos de decisión
- Análisis de mercado
- Detección de oportunidades

**Persona 2: Gestión de Datos**
- Inventory Manager
- Order Manager
- Sistemas de tracking

**Persona 3: Análisis y Riesgo**
- Market Analyzer
- Risk Manager
- Métricas y estadísticas

**Persona 4: Testing y Calidad**
- Tests unitarios
- Validación de código
- Documentación

### Coordinación
- Reunión diaria de 15 minutos
- Code reviews antes de mergear
- Comunicación por Slack/Discord
- Commits pequeños y frecuentes

---

## 🚀 ¡Comienza Ahora!

**Si es tu primer día:** → **TUTORIAL_PRIMER_DIA.md**

**Si ya configuraste todo:** → **DESARROLLO_EJEMPLOS.md**

**Si tienes dudas:** → **README.md**

---

**¡Éxito con el proyecto! 🥑🚀**

