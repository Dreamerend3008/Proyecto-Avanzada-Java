# 🗂️ Árbol de Archivos Creados

```
Proyecto-Avanzada-Java/
│
├── 📂 src/
│   ├── 📂 main/
│   │   └── 📂 java/
│   │       └── tech/hellsoft/trading/
│   │           │
│   │           ├── 📄 ClienteBolsa.java              👤 PERSONA 1
│   │           ├── 📄 ConsolaInteractiva.java        👤 PERSONA 1
│   │           ├── 📄 EstadoCliente.java             👤 PERSONA 2
│   │           ├── 📄 CalculadoraProduccion.java     👤 PERSONA 2
│   │           ├── 📄 RecetaValidator.java           👤 PERSONA 3
│   │           ├── 📄 SnapshotManager.java           👤 PERSONA 3
│   │           │
│   │           └── 📂 exception/                     👤 PERSONA 3
│   │               ├── 📄 TradingException.java
│   │               ├── 📄 SaldoInsuficienteException.java
│   │               ├── 📄 InventarioInsuficienteException.java
│   │               ├── 📄 ProductoNoAutorizadoException.java
│   │               ├── 📄 PrecioNoDisponibleException.java
│   │               ├── 📄 OfertaExpiradaException.java
│   │               ├── 📄 ProduccionException.java
│   │               ├── 📄 IngredientesInsuficientesException.java
│   │               ├── 📄 RecetaNoEncontradaException.java
│   │               ├── 📄 ConfiguracionException.java
│   │               └── 📄 SnapshotCorruptoException.java
│   │
│   └── 📂 test/
│       └── 📂 java/
│           └── tech/hellsoft/trading/
│               ├── 📄 ClienteBolsaTest.java          👤 PERSONA 1
│               ├── 📄 EstadoClienteTest.java         👤 PERSONA 2
│               ├── 📄 CalculadoraProduccionTest.java 👤 PERSONA 2
│               ├── 📄 RecetaValidatorTest.java       👤 PERSONA 3
│               └── 📄 SnapshotManagerTest.java       👤 PERSONA 3
│
└── 📂 quehago/
    ├── 📄 ORGANIZACION.md                     📚 Guía de organización
    ├── 📄 ESTRUCTURA_CREADA.md                📋 Resumen de archivos
    ├── 📄 ARBOL_ARCHIVOS.md                   🌳 Este archivo
    ├── 📄 README.md                           📖 Índice
    ├── 📄 TAREAS_PENDIENTES.md                ✅ Checklist
    ├── 📄 PERSONA_1_ClienteBolsa_Consola.md   👤 Tareas Persona 1
    ├── 📄 PERSONA_2_Estado_Algoritmos.md      👤 Tareas Persona 2
    └── 📄 PERSONA_3_Validacion_Persistencia.md 👤 Tareas Persona 3
```

---

## 📊 Estadísticas

| Persona | Archivos de Código | Archivos de Test | Total |
|---------|-------------------|------------------|-------|
| **Persona 1** | 2 | 1 | **3** |
| **Persona 2** | 2 | 2 | **4** |
| **Persona 3** | 13 | 2 | **15** |
| **TOTAL** | **17** | **5** | **22** |

---

## 🎨 Leyenda

- 📂 Carpeta
- 📄 Archivo de código
- 👤 Persona responsable
- 📚 Documentación
- 📋 Resumen
- 🌳 Árbol de archivos
- 📖 Índice
- ✅ Checklist

---

## 🔍 Verificar Archivos Creados

Para verificar que todos los archivos fueron creados correctamente, ejecuta:

```bash
# Listar archivos de código principal
dir src\main\java\tech\hellsoft\trading\*.java /s

# Listar archivos de excepciones
dir src\main\java\tech\hellsoft\trading\exception\*.java

# Listar archivos de tests
dir src\test\java\tech\hellsoft\trading\*.java

# Contar archivos creados
echo "Archivos en main:" & dir src\main\java\tech\hellsoft\trading\*.java /b /s | find /c ".java"
echo "Archivos de test:" & dir src\test\java\tech\hellsoft\trading\*.java /b /s | find /c ".java"
```

---

## ✅ Estado de Cada Archivo

Todos los archivos contienen **únicamente** un comentario del tipo:
```java
// TODO: PERSONA X - Implementar NombreClase
```

**NO hay implementación** - cada persona debe completar sus archivos según las instrucciones en `quehago/`.

---

## 🚀 Inicio Rápido

### Para PERSONA 1:
```bash
# Abrir archivos asignados
code src/main/java/tech/hellsoft/trading/ClienteBolsa.java
code src/main/java/tech/hellsoft/trading/ConsolaInteractiva.java

# Leer guía
code quehago/PERSONA_1_ClienteBolsa_Consola.md
```

### Para PERSONA 2:
```bash
# Abrir archivos asignados
code src/main/java/tech/hellsoft/trading/EstadoCliente.java
code src/main/java/tech/hellsoft/trading/CalculadoraProduccion.java

# Leer guía
code quehago/PERSONA_2_Estado_Algoritmos.md
```

### Para PERSONA 3:
```bash
# Abrir carpeta de excepciones
code src/main/java/tech/hellsoft/trading/exception/

# Abrir otros archivos
code src/main/java/tech/hellsoft/trading/RecetaValidator.java
code src/main/java/tech/hellsoft/trading/SnapshotManager.java

# Leer guía
code quehago/PERSONA_3_Validacion_Persistencia.md
```

---

**Estructura completa creada exitosamente! ✨**

