# 📂 Estructura del Proyecto - Resumen

## ✅ Archivos Creados y Asignados

Todos los archivos han sido creados como "bocetos" con únicamente un comentario indicando quién es responsable de implementarlos.

---

## 👤 PERSONA 1 - Archivos Asignados

### Código Principal
- ✅ `src/main/java/tech/hellsoft/trading/ClienteBolsa.java`
- ✅ `src/main/java/tech/hellsoft/trading/ConsolaInteractiva.java`

### Tests
- ✅ `src/test/java/tech/hellsoft/trading/ClienteBolsaTest.java`

**Total: 3 archivos**

---

## 👤 PERSONA 2 - Archivos Asignados

### Código Principal
- ✅ `src/main/java/tech/hellsoft/trading/EstadoCliente.java`
- ✅ `src/main/java/tech/hellsoft/trading/CalculadoraProduccion.java`

### Tests
- ✅ `src/test/java/tech/hellsoft/trading/EstadoClienteTest.java`
- ✅ `src/test/java/tech/hellsoft/trading/CalculadoraProduccionTest.java`

**Total: 4 archivos**

---

## 👤 PERSONA 3 - Archivos Asignados

### Código Principal
- ✅ `src/main/java/tech/hellsoft/trading/RecetaValidator.java`
- ✅ `src/main/java/tech/hellsoft/trading/SnapshotManager.java`

### Excepciones (11 archivos)
- ✅ `src/main/java/tech/hellsoft/trading/exception/TradingException.java`
- ✅ `src/main/java/tech/hellsoft/trading/exception/SaldoInsuficienteException.java`
- ✅ `src/main/java/tech/hellsoft/trading/exception/InventarioInsuficienteException.java`
- ✅ `src/main/java/tech/hellsoft/trading/exception/ProductoNoAutorizadoException.java`
- ✅ `src/main/java/tech/hellsoft/trading/exception/PrecioNoDisponibleException.java`
- ✅ `src/main/java/tech/hellsoft/trading/exception/OfertaExpiradaException.java`
- ✅ `src/main/java/tech/hellsoft/trading/exception/ProduccionException.java`
- ✅ `src/main/java/tech/hellsoft/trading/exception/IngredientesInsuficientesException.java`
- ✅ `src/main/java/tech/hellsoft/trading/exception/RecetaNoEncontradaException.java`
- ✅ `src/main/java/tech/hellsoft/trading/exception/ConfiguracionException.java`
- ✅ `src/main/java/tech/hellsoft/trading/exception/SnapshotCorruptoException.java`

### Tests
- ✅ `src/test/java/tech/hellsoft/trading/RecetaValidatorTest.java`
- ✅ `src/test/java/tech/hellsoft/trading/SnapshotManagerTest.java`

**Total: 15 archivos**

---

## 📚 Documentación de Apoyo

### En carpeta `quehago/`
- ✅ `ORGANIZACION.md` - Guía completa de organización del proyecto
- ✅ `PERSONA_1_ClienteBolsa_Consola.md` - Tareas detalladas Persona 1
- ✅ `PERSONA_2_Estado_Algoritmos.md` - Tareas detalladas Persona 2
- ✅ `PERSONA_3_Validacion_Persistencia.md` - Tareas detalladas Persona 3
- ✅ `README.md` - Índice de tareas
- ✅ `TAREAS_PENDIENTES.md` - Checklist general

### En carpeta `documentacion/`
- ✅ `GUIA.md` - Guía principal del profesor (referencia)
- ✅ Otros archivos de documentación existentes

---

## 🎯 Estado Actual del Proyecto

### ✅ Completado
- [x] Estructura de carpetas creada
- [x] 22 archivos de código creados (bocetos)
- [x] Documentación de organización creada
- [x] Asignación clara de responsabilidades

### ⏳ Pendiente (Responsabilidad de cada persona)
- [ ] Implementar el contenido de cada archivo asignado
- [ ] Escribir tests para validar funcionalidad
- [ ] Integrar componentes entre personas
- [ ] Compilar y ejecutar el proyecto completo

---

## 🚀 Próximos Pasos para Cada Persona

### PERSONA 1
1. Leer `quehago/PERSONA_1_ClienteBolsa_Consola.md`
2. Revisar `documentacion/GUIA.md` secciones relevantes
3. Implementar `ClienteBolsa.java` (callbacks y métodos públicos)
4. Implementar `ConsolaInteractiva.java` (comandos de consola)
5. Escribir tests básicos

### PERSONA 2
1. Leer `quehago/PERSONA_2_Estado_Algoritmos.md`
2. Revisar `documentacion/GUIA.md` sección 4.3 (algoritmo recursivo)
3. Implementar `EstadoCliente.java` (estado y cálculos)
4. Implementar `CalculadoraProduccion.java` (recursión)
5. Escribir tests exhaustivos (especialmente para recursión)

### PERSONA 3
1. Leer `quehago/PERSONA_3_Validacion_Persistencia.md`
2. Implementar todas las excepciones (11 archivos) - PRIORIDAD
3. Implementar `RecetaValidator.java`
4. Implementar `SnapshotManager.java`
5. Escribir tests para validación y serialización

---

## 📖 Cómo Usar Esta Estructura

1. **Cada persona trabaja en su propio branch:**
   ```
   git checkout -b feature/persona-1-cliente-bolsa
   git checkout -b feature/persona-2-calculadora
   git checkout -b feature/persona-3-excepciones
   ```

2. **Implementar archivos asignados:**
   - Abrir archivo en el editor
   - Leer el comentario TODO
   - Consultar documentación en `quehago/` y `documentacion/`
   - Implementar funcionalidad completa

3. **Compilar frecuentemente:**
   ```
   gradlew.bat compileJava
   ```

4. **Ejecutar tests:**
   ```
   gradlew.bat test
   ```

5. **Hacer commits frecuentes:**
   ```
   git add .
   git commit -m "feat: implementar EstadoCliente"
   git push
   ```

6. **Crear Pull Requests cuando termine una funcionalidad**

---

## 📞 Coordinación entre Personas

### Dependencias Críticas

**PERSONA 3 debe terminar primero:**
- Las excepciones son necesarias para PERSONA 1 y PERSONA 2

**PERSONA 2 debe terminar segundo:**
- `EstadoCliente` es necesario para PERSONA 1
- `CalculadoraProduccion` es necesario para PERSONA 1

**PERSONA 1 integra todo:**
- Usa componentes de PERSONA 2 y PERSONA 3
- Implementa la lógica de negocio principal

### Recomendación de Orden
```
DÍA 1:
├─ PERSONA 3: Todas las excepciones (2-3 horas)
├─ PERSONA 2: EstadoCliente (2-3 horas)
└─ PERSONA 1: Estructura básica de ClienteBolsa

DÍA 2:
├─ PERSONA 2: CalculadoraProduccion (3-4 horas)
├─ PERSONA 3: RecetaValidator + SnapshotManager (3-4 horas)
└─ PERSONA 1: ClienteBolsa completo (callbacks + métodos)

DÍA 3:
├─ TODOS: Tests
├─ TODOS: Integración
└─ PERSONA 1: ConsolaInteractiva
```

---

## ✨ Notas Importantes

1. **No hay código de implementación** - Solo comentarios TODO
2. **Cada archivo indica claramente** quién es responsable
3. **La documentación está completa** en `quehago/ORGANIZACION.md`
4. **Los archivos NO compilarán** hasta que cada persona implemente su parte
5. **Esto es intencional** - cada persona tiene libertad de implementar como prefiera

---

**¡La estructura está lista! Ahora cada persona puede empezar a trabajar en su parte. 🚀**

