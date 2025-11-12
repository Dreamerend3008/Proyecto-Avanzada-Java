# 📂 Guía de Organización del Proyecto

## 🎯 Estructura de Carpetas y Archivos

Esta guía explica cómo está organizado el proyecto y las mejores prácticas para trabajar en equipo.

---

## 📁 Estructura Completa del Proyecto

```
Proyecto-Avanzada-Java/
│
├── 📂 src/
│   ├── 📂 main/
│   │   ├── 📂 java/
│   │   │   └── tech/hellsoft/trading/
│   │   │       ├── Main.java (✅ YA EXISTE)
│   │   │       ├── ClienteBolsa.java (👤 PERSONA 1)
│   │   │       ├── ConsolaInteractiva.java (👤 PERSONA 1)
│   │   │       ├── EstadoCliente.java (👤 PERSONA 2)
│   │   │       ├── CalculadoraProduccion.java (👤 PERSONA 2)
│   │   │       ├── RecetaValidator.java (👤 PERSONA 3)
│   │   │       ├── SnapshotManager.java (👤 PERSONA 3)
│   │   │       │
│   │   │       ├── 📂 config/ (✅ YA EXISTE)
│   │   │       │   └── Configuration.java
│   │   │       │
│   │   │       ├── 📂 exception/ (👤 PERSONA 3)
│   │   │       │   ├── TradingException.java
│   │   │       │   ├── SaldoInsuficienteException.java
│   │   │       │   ├── InventarioInsuficienteException.java
│   │   │       │   ├── ProductoNoAutorizadoException.java
│   │   │       │   ├── PrecioNoDisponibleException.java
│   │   │       │   ├── OfertaExpiradaException.java
│   │   │       │   ├── ProduccionException.java
│   │   │       │   ├── IngredientesInsuficientesException.java
│   │   │       │   ├── RecetaNoEncontradaException.java
│   │   │       │   ├── ConfiguracionException.java
│   │   │       │   └── SnapshotCorruptoException.java
│   │   │       │
│   │   │       ├── 📂 model/ (✅ YA EXISTE)
│   │   │       │   ├── Recipe.java
│   │   │       │   ├── Role.java
│   │   │       │   └── ...
│   │   │       │
│   │   │       ├── 📂 service/ (✅ EXISTE)
│   │   │       └── 📂 util/ (✅ EXISTE)
│   │   │
│   │   └── 📂 resources/
│   │       ├── config.json (⚠️ NO COMMITEAR - Datos sensibles)
│   │       └── config.sample.json (✅ Template)
│   │
│   └── 📂 test/
│       └── 📂 java/
│           └── tech/hellsoft/trading/
│               ├── ClienteBolsaTest.java (👤 PERSONA 1)
│               ├── EstadoClienteTest.java (👤 PERSONA 2)
│               ├── CalculadoraProduccionTest.java (👤 PERSONA 2)
│               ├── RecetaValidatorTest.java (👤 PERSONA 3)
│               └── SnapshotManagerTest.java (👤 PERSONA 3)
│
├── 📂 quehago/ (📋 Distribución de tareas)
│   ├── README.md
│   ├── TAREAS_PENDIENTES.md
│   ├── PERSONA_1_ClienteBolsa_Consola.md
│   ├── PERSONA_2_Estado_Algoritmos.md
│   └── PERSONA_3_Validacion_Persistencia.md
│
├── 📂 documentacion/ (📚 Docs del profesor)
│   ├── INDICE.md
│   ├── INICIO_RAPIDO.md
│   ├── DESARROLLO_EJEMPLOS.md
│   └── ...
│
├── 📂 build/ (⚠️ NO COMMITEAR - Generado por Gradle)
├── 📂 .gradle/ (⚠️ NO COMMITEAR - Cache de Gradle)
│
├── build.gradle.kts (⚙️ Configuración de build)
├── settings.gradle.kts
├── gradlew (🐧 Script para Linux/Mac)
├── gradlew.bat (🪟 Script para Windows)
├── .gitignore (⚠️ Archivos a ignorar)
│
├── GUIA.md (📖 Guía del profesor - LEER PRIMERO)
└── README.md (📄 Documentación del proyecto)
```

---

## 🗂️ Organización por Persona

### 👤 Persona 1 - Interfaz y Coordinación

**Carpetas de trabajo:**
```
src/main/java/tech/hellsoft/trading/
├── ClienteBolsa.java          ⭐⭐⭐⭐⭐ CRÍTICO
└── ConsolaInteractiva.java    ⭐⭐⭐⭐

src/test/java/tech/hellsoft/trading/
└── ClienteBolsaTest.java
```

**Archivos que usarás (creados por otros):**
- `EstadoCliente.java` (Persona 2)
- `CalculadoraProduccion.java` (Persona 2)
- `RecetaValidator.java` (Persona 3)
- `SnapshotManager.java` (Persona 3)
- Todas las excepciones (Persona 3)

---

### 👤 Persona 2 - Estado y Algoritmos

**Carpetas de trabajo:**
```
src/main/java/tech/hellsoft/trading/
├── EstadoCliente.java             ⭐⭐⭐⭐⭐ CRÍTICO
└── CalculadoraProduccion.java     ⭐⭐⭐⭐⭐ CRÍTICO (recursión)

src/test/java/tech/hellsoft/trading/
├── EstadoClienteTest.java
└── CalculadoraProduccionTest.java
```

**Archivos que usarás:**
- `model/Role.java` (YA EXISTE)
- `model/Recipe.java` (YA EXISTE)

**¿Quién usa tu código?**
- Persona 1 (ClienteBolsa necesita EstadoCliente y CalculadoraProduccion)
- Persona 3 (RecetaValidator necesita EstadoCliente)

---

### 👤 Persona 3 - Validación y Persistencia

**Carpetas de trabajo:**
```
src/main/java/tech/hellsoft/trading/
├── RecetaValidator.java       ⭐⭐⭐⭐
├── SnapshotManager.java       ⭐⭐⭐
└── exception/                 ⭐⭐⭐⭐⭐ CRÍTICO (11 clases)
    ├── TradingException.java
    ├── SaldoInsuficienteException.java
    ├── InventarioInsuficienteException.java
    ├── ProductoNoAutorizadoException.java
    ├── PrecioNoDisponibleException.java
    ├── OfertaExpiradaException.java
    ├── ProduccionException.java
    ├── IngredientesInsuficientesException.java
    ├── RecetaNoEncontradaException.java
    ├── ConfiguracionException.java
    └── SnapshotCorruptoException.java

src/test/java/tech/hellsoft/trading/
├── RecetaValidatorTest.java
└── SnapshotManagerTest.java
```

**Archivos que usarás:**
- `EstadoCliente.java` (Persona 2)
- `model/Recipe.java` (YA EXISTE)

**¿Quién usa tu código?**
- Persona 1 (usa todas tus excepciones, RecetaValidator y SnapshotManager)
- Persona 2 (puede usar excepciones)

---

## 🔄 Flujo de Trabajo con Git

### 1️⃣ Crear Branch para Tu Tarea

```bash
# Ver en qué branch estás
git branch

# Asegurarte de estar en main actualizado
git checkout main
git pull origin main

# Crear tu branch (reemplaza NOMBRE con algo descriptivo)
git checkout -b feature/persona-1-cliente-bolsa
# o
git checkout -b feature/persona-2-calculadora
# o
git checkout -b feature/persona-3-excepciones
```

### 2️⃣ Trabajar en Tu Branch

```bash
# Ver estado de tus cambios
git status

# Agregar archivos modificados
git add src/main/java/tech/hellsoft/trading/EstadoCliente.java
# o agregar todos
git add .

# Hacer commit con mensaje descriptivo
git commit -m "feat: implementar EstadoCliente con cálculo de P&L"

# Commits frecuentes (cada 30-60 minutos)
git commit -m "feat: agregar métodos de inventario a EstadoCliente"
git commit -m "test: agregar tests para EstadoCliente"
git commit -m "fix: corregir cálculo de P&L cuando saldoInicial es 0"
```

### 3️⃣ Subir Tu Branch

```bash
# Primera vez
git push -u origin feature/persona-2-calculadora

# Siguientes veces
git push
```

### 4️⃣ Crear Pull Request

1. Ve a GitHub en tu navegador
2. Verás un botón "Compare & pull request"
3. Escribe descripción:
   ```
   ## Qué implementa este PR
   - EstadoCliente completo
   - Métodos de inventario
   - Cálculo de P&L
   
   ## Tests
   - [x] Tests unitarios pasan
   - [x] Código compila sin errores
   
   ## Relacionado
   Cierra tarea de Persona 2 en PERSONA_2_Estado_Algoritmos.md
   ```
4. Pide a un compañero que revise
5. Merge solo después de aprobación

### 5️⃣ Actualizar Tu Branch con Cambios de Main

```bash
# Si otros ya hicieron merge y necesitas sus cambios
git checkout main
git pull origin main
git checkout tu-branch
git merge main

# Resolver conflictos si hay
# Luego:
git add .
git commit -m "merge: integrar cambios de main"
git push
```

---

## 🧪 Cómo Probar Tu Código

### Compilar el Proyecto

```bash
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

### Ejecutar Tests

```bash
# Todos los tests
gradlew.bat test

# Solo tests de una clase
gradlew.bat test --tests EstadoClienteTest

# Con más detalles
gradlew.bat test --info
```

### Ejecutar el Main

```bash
gradlew.bat run
```

### Ver Errores de Compilación

```bash
# Limpiar y compilar
gradlew.bat clean compileJava
```

---

## 📝 Convenciones de Código

### Nombres de Commits

Usar prefijos:
- `feat:` - Nueva funcionalidad
- `fix:` - Corrección de bug
- `test:` - Agregar tests
- `refactor:` - Refactorización sin cambio de funcionalidad
- `docs:` - Cambios en documentación
- `style:` - Formato de código

Ejemplos:
```
feat: implementar callback onLoginOk en ClienteBolsa
fix: corregir validación de branches en CalculadoraProduccion
test: agregar tests exhaustivos para RecetaValidator
refactor: extraer método privado calcularEnergiaRecursiva
docs: actualizar README con instrucciones de instalación
```

### Nombres de Branches

Formato: `feature/persona-X-descripcion-corta`

Ejemplos:
```
feature/persona-1-cliente-bolsa
feature/persona-1-consola-interactiva
feature/persona-2-estado-cliente
feature/persona-2-calculadora-produccion
feature/persona-3-excepciones
feature/persona-3-snapshot-manager
```

### Estilo de Código Java

✅ **SÍ hacer:**
```java
// Guard clauses en lugar de if-else anidados
public void comprar(String producto, int cantidad) throws SaldoInsuficienteException {
    if (producto == null || producto.isEmpty()) {
        throw new IllegalArgumentException("Producto no puede ser null o vacío");
    }
    
    if (cantidad <= 0) {
        throw new IllegalArgumentException("Cantidad debe ser positiva");
    }
    
    double costo = calcularCosto(producto, cantidad);
    if (estado.getSaldo() < costo) {
        throw new SaldoInsuficienteException("Saldo insuficiente");
    }
    
    // Lógica principal
}
```

❌ **NO hacer:**
```java
// if-else anidados (NO PERMITIDO según GUIA.md)
public void comprar(String producto, int cantidad) {
    if (producto != null) {
        if (cantidad > 0) {
            double costo = calcularCosto(producto, cantidad);
            if (estado.getSaldo() >= costo) {
                // Lógica
            } else {
                throw new SaldoInsuficienteException("...");
            }
        }
    }
}
```

### JavaDoc

```java
/**
 * Calcula las unidades producidas usando recursión.
 * 
 * El algoritmo construye un árbol de producción donde cada nodo
 * tiene 'branches' hijos y la profundidad máxima es 'maxDepth'.
 * 
 * @param rol Role con parámetros de producción
 * @return Unidades totales producidas (redondeadas)
 * @throws IllegalArgumentException Si rol es null o parámetros inválidos
 */
public static int calcularUnidades(Role rol) {
    // ...
}
```

---

## 🔗 Dependencias entre Componentes

### Orden de Implementación Recomendado

**Día 1 - Fundamentos:**
```
1. PERSONA 3: Excepciones (todas) ⚡ PRIORIDAD MÁXIMA
   └─> Permite que otros las usen

2. PERSONA 2: EstadoCliente ⚡ PRIORIDAD MÁXIMA
   └─> ClienteBolsa lo necesita

3. PERSONA 2: CalculadoraProduccion
   └─> ClienteBolsa.producir() lo necesita

4. PERSONA 3: RecetaValidator
   └─> ClienteBolsa.producir() lo necesita

5. PERSONA 1: ClienteBolsa (callbacks + comprar/vender)
   └─> Ya puede usar EstadoCliente y excepciones
```

**Día 2 - Integración:**
```
6. PERSONA 1: ClienteBolsa.producir()
   └─> Ya tiene CalculadoraProduccion y RecetaValidator

7. PERSONA 1: ConsolaInteractiva (comandos básicos)

8. PERSONA 3: SnapshotManager

9. PERSONA 1: ConsolaInteractiva (comandos snapshot)
```

**Día 3 - Testing:**
```
10. TODOS: Escribir tests
11. TODOS: Integración completa
12. TODOS: Testing de extremo a extremo
```

---

## 🆘 Solución de Problemas Comunes

### Error: "Cannot find symbol EstadoCliente"

**Problema:** Estás usando EstadoCliente pero aún no ha sido implementado.

**Solución:**
1. Coordina con Persona 2
2. O implementa un stub temporal:
```java
// Stub temporal hasta que Persona 2 implemente
public class EstadoCliente {
    public double getSaldo() { return 0.0; }
    // ... métodos mínimos
}
```

### Error: Conflicto de Merge

**Problema:** Dos personas modificaron el mismo archivo.

**Solución:**
```bash
# Ver archivos en conflicto
git status

# Editar archivos, buscar marcadores de conflicto:
# <<<<<<< HEAD
# tu código
# =======
# código del otro
# >>>>>>> branch-name

# Resolver manualmente, luego:
git add archivo-resuelto.java
git commit -m "merge: resolver conflicto en archivo"
```

### Tests No Pasan

**Problema:** `gradlew.bat test` falla.

**Solución:**
1. Leer el error completo
2. Verificar que todos los archivos necesarios existen
3. Asegurarse de que no hay errores de compilación
4. Verificar lógica de los tests

### Gradle No Encuentra JUnit

**Problema:** Tests no compilan porque JUnit no se encuentra.

**Solución:**
```bash
# Limpiar y rebuild
gradlew.bat clean build --refresh-dependencies
```

---

## 📊 Checklist Diario para Cada Persona

### Al Iniciar el Día
- [ ] `git pull origin main` - Actualizar código
- [ ] `gradlew.bat build` - Verificar que todo compila
- [ ] Leer tu archivo en `quehago/`
- [ ] Decidir qué tarea harás hoy
- [ ] Crear/continuar branch

### Durante el Día
- [ ] Commits cada 30-60 minutos
- [ ] Tests para código nuevo
- [ ] Comunicar bloqueos al equipo
- [ ] Push al final del día

### Al Finalizar el Día
- [ ] `gradlew.bat test` - Todos los tests pasan
- [ ] `git push` - Subir cambios
- [ ] Actualizar checklist en tu archivo de `quehago/`
- [ ] Crear PR si terminaste una funcionalidad completa

---

## 🎯 Resumen de Comandos Más Usados

```bash
# Ver estado
git status

# Crear branch
git checkout -b feature/mi-tarea

# Commit
git add .
git commit -m "feat: descripción"

# Push
git push

# Compilar
gradlew.bat build

# Tests
gradlew.bat test

# Ejecutar
gradlew.bat run

# Limpiar
gradlew.bat clean
```

---

## 📞 Comunicación

### Discord/Slack del Equipo
- Preguntas rápidas
- Compartir pantalla si estás atorado
- Avisar cuando terminas una tarea

### GitHub Issues
- Reportar bugs
- Proponer mejoras
- Trackear tareas

### Pull Requests
- Code review obligatorio
- Mínimo 1 aprobación antes de merge
- Feedback constructivo

### Reuniones Diarias (15 min)
- ¿Qué hice ayer?
- ¿Qué haré hoy?
- ¿Tengo bloqueos?

---

## 🏆 Criterios de Éxito

### ✅ Para Aprobar
- Código compila sin errores
- Tests básicos pasan
- Bot se conecta y opera

### ⭐ Para Nota Alta
- Todo lo anterior +
- Tests exhaustivos (70%+ cobertura)
- Código limpio y documentado
- Snapshots funcionan

### 🥇 Para Nota Máxima
- Todo lo anterior +
- AutoProduccionManager (+5% bonus)
- P&L positivo en torneo
- Código ejemplar

---

**¡Buena suerte! 🚀**

