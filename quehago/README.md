# 📂 Carpeta QUEHAGO - Distribución de Tareas del Proyecto

## 🎯 Propósito de esta Carpeta

Esta carpeta contiene la distribución equitativa de tareas para el equipo de 3 personas que desarrollará el Cliente de Trading para la **Bolsa Interestelar de Aguacates Andorianos** 🥑.

---

## 📄 Archivos en esta Carpeta

### 1. **TAREAS_PENDIENTES.md** - Lista Maestra
Documento principal que enumera TODAS las tareas pendientes del proyecto, organizadas por prioridad y área.

**Úsalo para:**
- Ver el panorama completo del proyecto
- Entender las prioridades
- Conocer el esfuerzo estimado total
- Seguir el progreso con checklists

---

### 2. **PERSONA_1_ClienteBolsa_Consola.md** - Tareas para Desarrollador 1
**Responsable de:** Interfaz y Coordinación

**Componentes:**
- ✅ ClienteBolsa.java (implementa EventListener)
  - 6 callbacks del SDK
  - Métodos: comprar(), vender(), producir()
- ✅ ConsolaInteractiva.java (15 comandos)

**Carga de trabajo:** 17-24 horas  
**Complejidad:** ALTA ⭐⭐⭐⭐

---

### 3. **PERSONA_2_Estado_Algoritmos.md** - Tareas para Desarrollador 2
**Responsable de:** Cerebro Matemático

**Componentes:**
- ✅ EstadoCliente.java (estado del juego)
- ✅ CalculadoraProduccion.java (algoritmo recursivo ⭐⭐⭐⭐⭐)
- ✅ Tests exhaustivos

**Carga de trabajo:** 14-20 horas  
**Complejidad:** ALTA (algoritmo recursivo) ⭐⭐⭐⭐⭐

---

### 4. **PERSONA_3_Validacion_Persistencia.md** - Tareas para Desarrollador 3
**Responsable de:** Robustez y Seguridad

**Componentes:**
- ✅ Sistema completo de excepciones (11 clases)
- ✅ RecetaValidator.java (validación de ingredientes)
- ✅ SnapshotManager.java (serialización binaria)
- ✅ Tests

**Carga de trabajo:** 11-16 horas  
**Complejidad:** MEDIA ⭐⭐⭐

---

## 🗓️ Roadmap Sugerido (3-4 Días)

### 📅 Día 1: Fundamentos (8 horas)
**Objetivo:** Estructura básica funcionando

| Persona | Tarea |
|---------|-------|
| **Persona 1** | ClienteBolsa callbacks + comprar/vender |
| **Persona 2** | EstadoCliente + CalculadoraProduccion |
| **Persona 3** | Excepciones + RecetaValidator |

**Reunión:** Al final del día para integrar componentes

---

### 📅 Día 2: Funcionalidad Completa (8 horas)
**Objetivo:** Bot totalmente funcional

| Persona | Tarea |
|---------|-------|
| **Persona 1** | ConsolaInteractiva (comandos) |
| **Persona 2** | Integración ClienteBolsa.producir() + Tests |
| **Persona 3** | SnapshotManager + comandos snapshot en consola |

**Reunión:** Checkpoint de tarde para resolver bloqueos

---

### 📅 Día 3: Testing y Refinamiento (6 horas)
**Objetivo:** Código robusto y testeado

| Persona | Tarea |
|---------|-------|
| **Persona 1** | Tests de integración + fixes |
| **Persona 2** | Tests exhaustivos + JavaDoc |
| **Persona 3** | Tests de componentes + validaciones |

**Reunión:** Demo del bot funcionando

---

### 📅 Día 4 (Opcional): Bonus (+5% puntos)
**Objetivo:** AutoProduccionManager

| Todos | Implementar y probar AutoProduccionManager |

---

## 📊 Resumen de Carga de Trabajo

| Persona | Horas | Complejidad | Componentes |
|---------|-------|-------------|-------------|
| Persona 1 | 17-24h | ⭐⭐⭐⭐ | 2 componentes críticos |
| Persona 2 | 14-20h | ⭐⭐⭐⭐⭐ | 2 componentes + algoritmo |
| Persona 3 | 11-16h | ⭐⭐⭐ | 3 componentes + 11 excepciones |
| **TOTAL** | **42-60h** | - | **7 componentes principales** |

**Promedio por persona:** 14-20 horas (3-4 días de trabajo)

---

## 🤝 Reglas de Colaboración

### 1. Flujo de Git
```bash
# Crear branch para tu tarea
git checkout -b feature/nombre-componente

# Commits frecuentes
git commit -m "feat: implementar método X"

# Push a tu branch
git push origin feature/nombre-componente

# Pull Request + Code Review
# Merge solo después de aprobación
```

### 2. Reuniones Diarias (15 min)
- ¿Qué hice ayer?
- ¿Qué haré hoy?
- ¿Tengo bloqueos?

### 3. Code Review Obligatorio
- Revisar lógica
- Verificar que compile
- Probar localmente
- Dar feedback constructivo

### 4. Comunicación
- **Slack/Discord:** Para dudas rápidas
- **GitHub Issues:** Para bugs o features
- **Pull Requests:** Para code review
- **Google Meet:** Para pair programming si es necesario

---

## 🎯 Criterios de Éxito del Proyecto

### ✅ Mínimo Viable (Aprobar)
- [ ] Bot se conecta y autentica
- [ ] Puede comprar y vender manualmente
- [ ] Puede producir (básico y premium)
- [ ] Calcula P&L correctamente
- [ ] Maneja errores sin crashear
- [ ] Consola interactiva funcional

### ⭐ Excelente (Nota Alta)
- [ ] Todo lo anterior +
- [ ] Snapshots funcionando
- [ ] Resync funcional
- [ ] Tests unitarios completos
- [ ] Código limpio y documentado
- [ ] Manejo robusto de excepciones

### 🏆 Sobresaliente (Nota Máxima + Bonus)
- [ ] Todo lo anterior +
- [ ] AutoProduccionManager funcionando
- [ ] Estrategia rentable en el torneo
- [ ] P&L positivo al final del torneo
- [ ] Código ejemplar (puede servir de referencia)

---

## 🔗 Dependencias entre Componentes

```
EstadoCliente (Persona 2)
    ↓ (usado por)
    ├── ClienteBolsa (Persona 1)
    ├── RecetaValidator (Persona 3)
    └── SnapshotManager (Persona 3)

CalculadoraProduccion (Persona 2)
    ↓ (usado por)
    └── ClienteBolsa.producir() (Persona 1)

Excepciones (Persona 3)
    ↓ (usadas por)
    ├── ClienteBolsa (Persona 1)
    ├── RecetaValidator (Persona 3)
    └── SnapshotManager (Persona 3)

RecetaValidator (Persona 3)
    ↓ (usado por)
    └── ClienteBolsa.producir() (Persona 1)

SnapshotManager (Persona 3)
    ↓ (usado por)
    └── ConsolaInteractiva (Persona 1)
```

---

## 📚 Recursos Adicionales

### Documentación del Proyecto
- **GUIA.md** - Guía completa del profesor ⭐⭐⭐⭐⭐
- **README.md** - Setup y primeros pasos
- **documentacion/INDICE.md** - Índice de documentación
- **documentacion/TUTORIAL_PRIMER_DIA.md** - Tutorial inicial
- **documentacion/DESARROLLO_EJEMPLOS.md** - Ejemplos de código

### Herramientas
- **JUnit 5** - Para tests
- **Lombok** - Para reducir boilerplate
- **Gradle** - Build system
- **IntelliJ IDEA** - IDE recomendado

### Conceptos Clave
- **Recursión** - Para CalculadoraProduccion
- **Serialización** - Para SnapshotManager
- **Callbacks** - Para EventListener
- **Excepciones** - Para manejo de errores

---

## ⚠️ Advertencias Importantes

### 🚨 NO hacer
- ❌ Modificar el SDK (está en .jar)
- ❌ Commitear `config.json` o `gradle.properties` (datos sensibles)
- ❌ Hacer merge sin code review
- ❌ Trabajar en `main` directamente

### ✅ SÍ hacer
- ✅ Crear branches para cada feature
- ✅ Escribir tests antes de hacer PR
- ✅ Documentar con JavaDoc
- ✅ Pedir ayuda cuando te atores
- ✅ Hacer commits frecuentes

---

## 🆘 Canales de Soporte

### Dentro del Equipo
1. **Slack/Discord del equipo** - Primera línea
2. **GitHub Issues** - Para trackear bugs
3. **Pair Programming** - Si estás bloqueado

### Externo
1. **Profesor** - Para dudas sobre la GUIA.md o SDK
2. **Documentación oficial** - Java, JUnit, etc.
3. **Stack Overflow** - Para problemas técnicos generales

---

## 📈 Tracking de Progreso

### Usar los Checklists
Cada archivo de persona tiene checklists. Marcalos con `[x]` cuando completes cada item.

### Daily Standup
Actualizar al equipo diariamente:
```
✅ Completado: ClienteBolsa callbacks
🚧 En progreso: ConsolaInteractiva comandos
⏳ Pendiente: Tests de integración
🚫 Bloqueado: Esperando EstadoCliente de Persona 2
```

### GitHub Projects (Opcional)
Crear un proyecto en GitHub con columnas:
- **TODO** - Tareas pendientes
- **IN PROGRESS** - En desarrollo
- **REVIEW** - En code review
- **DONE** - Completadas

---

## 🎓 Aprendizajes Esperados

Al completar este proyecto habrás practicado:

### Técnicos
- ✅ Implementación de interfaces
- ✅ Algoritmos recursivos
- ✅ Serialización/Deserialización
- ✅ Manejo de excepciones
- ✅ Testing con JUnit
- ✅ Callbacks y eventos asíncronos
- ✅ Colecciones (Map, List)

### Soft Skills
- ✅ Trabajo en equipo
- ✅ División de tareas
- ✅ Code review
- ✅ Resolución de conflictos
- ✅ Comunicación técnica
- ✅ Gestión de tiempo

---

## 🏁 Checklist Final del Proyecto

Antes de entregar:

### Funcionalidad
- [ ] Bot conecta al servidor
- [ ] Todos los comandos de consola funcionan
- [ ] Producción básica y premium funcionan
- [ ] Compra y venta funcionan
- [ ] Snapshots funcionan
- [ ] P&L se calcula correctamente

### Calidad de Código
- [ ] Sin warnings del compilador
- [ ] Sin errores de Checkstyle (si aplica)
- [ ] Tests pasan (mínimo 70% cobertura)
- [ ] JavaDoc en clases principales
- [ ] Código sigue estándares (no else, guard clauses)

### Documentación
- [ ] README.md actualizado si es necesario
- [ ] Comentarios en código complejo
- [ ] JavaDoc generado

### Git
- [ ] Todas las branches merged a main
- [ ] Sin conflictos pendientes
- [ ] Commits con mensajes descriptivos
- [ ] Tag de versión final creado

---

## 🎉 ¡Buena Suerte!

Recuerden:
- **Comunicación constante** es clave
- **Pedir ayuda** no es debilidad
- **Tests** te salvarán el pellejo
- **Code review** mejora a todos
- **El torneo** es donde todo se prueba

**¡Que gane el mejor equipo! 🥑🚀**

---

**Última actualización:** 2025-11-10  
**Versión:** 1.0  
**Proyecto:** Cliente Trading Bot - Bolsa Interestelar de Aguacates Andorianos

