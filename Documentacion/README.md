# 📚 Índice de Documentación - Proyecto Bolsa Interestelar

## 🎯 Documentos Disponibles

### 📖 [00-RESUMEN_PROYECTO.md](00-RESUMEN_PROYECTO.md)
**Lectura obligatoria para todos**

Contiene:
- Descripción general del proyecto
- Arquitectura del sistema
- Conceptos de trading
- Estado actual vs. pendiente
- Evaluación y puntos
- Stack tecnológico

**⏱️ Tiempo de lectura**: 15-20 minutos

---

### 👤 [01-TRABAJO_PERSONA_1.md](01-TRABAJO_PERSONA_1.md)
**Carga: Media (12-15 horas)**

**Responsabilidades**:
- ✅ 7 Excepciones personalizadas (15% de la nota)
- ✅ RecetaValidator
- ✅ DTOs (Rol y Receta)
- ✅ Comandos: inventario, precios

**Dificultad**: ⭐⭐⭐ (3/5)

**Dependencias**: Ninguna (puede empezar inmediatamente)

---

### 👤 [02-TRABAJO_PERSONA_2.md](02-TRABAJO_PERSONA_2.md)
**Carga: Alta (18-22 horas)**

**Responsabilidades**:
- ⚠️ CalculadoraProduccion - Algoritmo recursivo (22% de la nota) - **CRÍTICO**
- ⚠️ ClienteBolsa - Corazón del sistema (18% de la nota)
- ✅ Completar EstadoCliente
- ✅ Comandos: comprar, vender, producir

**Dificultad**: ⭐⭐⭐⭐⭐ (5/5)

**Dependencias**: 
- Necesita las excepciones de Persona 1
- Necesita RecetaValidator de Persona 1
- Necesita DTOs de Persona 1

**⚠️ PRIORIDAD CRÍTICA**: Empezar con CalculadoraProduccion

---

### 👤 [03-TRABAJO_PERSONA_3.md](03-TRABAJO_PERSONA_3.md)
**Carga: Baja (8-10 horas)**

**Responsabilidades**:
- ✅ SnapshotManager - Serialización binaria (13% de la nota)
- ✅ Comandos simples: status, ofertas, snapshot save/load
- ✅ Testing y validación
- ✅ Documentación JavaDoc

**Dificultad**: ⭐⭐ (2/5)

**Dependencias**:
- Necesita EstadoCliente completado de Persona 2
- Necesita SnapshotCorruptoException de Persona 1

---

## 📊 Distribución del Trabajo

| Persona | Horas | Complejidad | Peso en Nota | Archivos a Crear |
|---------|-------|-------------|--------------|------------------|
| **Persona 1** | 12-15 | Media | ~25% | 12 archivos |
| **Persona 2** | 18-22 | Alta | ~45% | 3 archivos (pero complejos) |
| **Persona 3** | 8-10 | Baja | ~20% | 2 archivos + testing |
| **Total** | 38-47 | - | ~90% | +10% bonus torneo |

---

## 🗓️ Plan de Trabajo Sugerido

### Semana 1 (Días 1-3): Fundamentos
- **Persona 1**: Crear todas las excepciones + DTOs
- **Persona 2**: CalculadoraProduccion (CRÍTICO)
- **Persona 3**: Leer documentación y preparar estructura

### Semana 2 (Días 4-6): Core
- **Persona 1**: RecetaValidator + Comandos simples
- **Persona 2**: ClienteBolsa (corazón del sistema)
- **Persona 3**: SnapshotManager

### Semana 3 (Días 7-9): Integración
- **Persona 1**: Comandos inventario/precios
- **Persona 2**: Comandos comprar/vender/producir
- **Persona 3**: Comandos snapshot + Testing

### Semana 4 (Días 10-12): Testing y Torneo
- **Todos**: Testing de integración
- **Todos**: Preparación para el torneo
- **Todos**: Optimización de estrategia

---

## 🔄 Orden de Implementación Recomendado

### Fase 1: Cimientos (Días 1-2)
```
1. Persona 1 → Excepciones base (TradingException, ProduccionException, ConfiguracionException)
2. Persona 1 → 7 Excepciones específicas
3. Persona 1 → DTOs (Rol, Receta)
```

### Fase 2: Algoritmos (Días 3-4)
```
4. Persona 2 → CalculadoraProduccion ⚠️ CRÍTICO
5. Persona 1 → RecetaValidator
6. Persona 2 → Completar EstadoCliente
```

### Fase 3: Sistema Core (Días 5-7)
```
7. Persona 2 → ClienteBolsa (callbacks + métodos públicos)
8. Persona 3 → SnapshotManager
```

### Fase 4: Interfaz (Días 8-10)
```
9. Persona 1 → handleInventario(), handlePrecios()
10. Persona 2 → handleComprar(), handleVender(), handleProducir()
11. Persona 3 → handleStatus(), handleOfertas(), handleSnapshot()
```

### Fase 5: Testing (Días 11-12)
```
12. Persona 3 → Tests de SnapshotManager
13. Todos → Tests de integración
14. Todos → Simulacros de torneo
```

---

## 🚨 Puntos Críticos de Coordinación

### ⚠️ Bloqueos Potenciales

1. **Persona 2 BLOQUEADA** hasta que Persona 1 termine:
   - Excepciones personalizadas
   - RecetaValidator
   - DTOs (Rol, Receta)

2. **Persona 3 BLOQUEADA** hasta que termine:
   - EstadoCliente completado (Persona 2)
   - SnapshotCorruptoException (Persona 1)

### ✅ Solución: Paralelización

- **Día 1-2**: Persona 1 trabaja, otros leen documentación
- **Día 3**: Persona 1 termina → Desbloquea a Persona 2
- **Día 5**: Persona 2 termina EstadoCliente → Desbloquea a Persona 3

---

## 📞 Comunicación del Equipo

### Daily Stand-up Sugerido (5 minutos)
1. ¿Qué hice ayer?
2. ¿Qué haré hoy?
3. ¿Estoy bloqueado por algo?

### Reuniones Importantes
- **Día 1**: Kickoff - Revisar toda la documentación juntos
- **Día 4**: Checkpoint - Verificar que la Fase 2 está completa
- **Día 8**: Integration Check - Probar todo junto
- **Día 11**: Pre-torneo - Estrategia y última revisión

---

## 🎯 Objetivos por Semana

### Semana 1: MVP Funcional
- [x] Login funciona
- [x] Producir básico funciona
- [x] Vender funciona

### Semana 2: Sistema Completo
- [x] Comprar funciona
- [x] Producir premium funciona
- [x] Snapshots funcionan

### Semana 3: Optimización
- [x] Todos los comandos funcionan
- [x] Manejo de errores completo
- [x] Testing exhaustivo

### Semana 4: Torneo
- [x] Estrategia de trading definida
- [x] Bot optimizado
- [x] Simulacros exitosos

---

## 📚 Recursos Adicionales

### Documentos del Proyecto
- `Guia-Profesor.md` - Guía detallada del profesor
- `README.md` - Setup inicial
- `GUIA.md` - Conceptos de trading

### Archivos de Configuración
- `config.json` - Configuración del equipo
- `gradle.properties` - Credenciales de GitHub

### SDK
- Localización: GitHub Packages
- Documentación: En Guia-Profesor.md

---

## ✅ Checklist Global del Proyecto

### Fase 1: Excepciones y DTOs
- [ ] TradingException, ProduccionException, ConfiguracionException
- [ ] SaldoInsuficienteException
- [ ] InventarioInsuficienteException
- [ ] ProductoNoAutorizadoException
- [ ] IngredientesInsuficientesException
- [ ] RecetaNoEncontradaException
- [ ] SnapshotCorruptoException
- [ ] Rol.java
- [ ] Receta.java

### Fase 2: Utilidades
- [ ] CalculadoraProduccion ⚠️
- [ ] RecetaValidator
- [ ] SnapshotManager
- [ ] Completar EstadoCliente

### Fase 3: Core
- [ ] ClienteBolsa (todos los callbacks)
- [ ] ClienteBolsa (comprar, vender, producir)
- [ ] ClienteBolsa (aceptarOferta)

### Fase 4: Comandos
- [ ] handleStatus
- [ ] handleInventario
- [ ] handlePrecios
- [ ] handleComprar
- [ ] handleVender
- [ ] handleProducir
- [ ] handleOfertas
- [ ] handleAceptarOferta
- [ ] handleSnapshotSave
- [ ] handleSnapshotLoad

### Fase 5: Testing
- [ ] Test CalculadoraProduccion
- [ ] Test RecetaValidator
- [ ] Test SnapshotManager
- [ ] Test ClienteBolsa
- [ ] Test integración completa
- [ ] Simulacro de torneo

---

## 🏆 Meta del Equipo

**Objetivo**: Lograr un P&L > +50% en el torneo de 15 minutos

**Estrategia**:
1. Producir premium siempre que sea posible (+30% bonus)
2. Liquidar TODO el inventario antes de T=13:00
3. Mantener snapshots cada 30 segundos
4. Responder rápido a ofertas ventajosas

---

## 📞 Contacto del Equipo

**Equipo**: Avocasticos  
**API Key**: TK-XqnoG2blE3DFmApa75iexwvC  
**Host**: wss://trading.hellsoft.tech/ws

---

¡Mucha suerte en el proyecto! 🥑🚀

**"El que no produce, compra. El que no compra, muere."**  
— Juan Carlos Bodoque, AI-Oráculo

