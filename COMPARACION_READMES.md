2. En **"Project"**:
   - **SDK:** Selecciona o agrega Java 25
   - **Language level:** 25 (Preview)
3. Haz clic en **"OK"**

### 3. Sincronizar Gradle
IntelliJ sincronizará automáticamente. Si no lo hace:
1. Abre el panel de **Gradle** (lado derecho)
2. Click en **"Reload All Gradle Projects"** (🔄)

### 4. Configurar Lombok (opcional)
1. **File** → **Settings** → **Plugins**
2. Busca "Lombok" e instala
3. Reinicia IntelliJ
4. **Settings** → **Build, Execution, Deployment** → **Compiler** → **Annotation Processors**
5. Marca **"Enable annotation processing"**

### 5. Importar Configuración de Formato
1. **File** → **Settings** → **Editor** → **Code Style** → **Java**
2. Click en ⚙️ → **Import Scheme** → **Eclipse XML Profile**
3. Selecciona `config/eclipse-format.xml`
```

#### Mejoras a Troubleshooting

```markdown
## 🐛 Solución de Problemas Comunes (Ampliada)

### Error: "Could not resolve tech.hellsoft.trading:websocket-client"
**Causa:** No se puede acceder a GitHub Packages

**Solución:**
1. Verifica que `gradle.properties` existe y tiene credenciales correctas
2. Verifica que tu token de GitHub tenga el scope `read:packages`
3. Prueba regenerar el token en GitHub
4. En IntelliJ: **Gradle** → **Reload All Gradle Projects**

### Error: "Unsupported class file major version 69"
**Causa:** Estás usando una versión de Java anterior a Java 25

**Solución:**
1. Instala JDK 25
2. En IntelliJ: **File** → **Project Structure** → **Project** → **SDK:** Java 25
3. Reinicia IntelliJ

### El programa no encuentra config.json
**Causa:** No has creado el archivo de configuración

**Solución:**
```bash
copy src\main\resources\config.sample.json src\main\resources\config.json
# Luego edita config.json con tu API key
```

### IntelliJ no reconoce las clases del SDK
**Causa:** Las dependencias no se descargaron correctamente

**Solución:**
1. **File** → **Invalidate Caches** → **Invalidate and Restart**
2. Espera a que IntelliJ reconstruya el índice
3. Si persiste: elimina `.gradle/` y `.idea/`, luego reabre el proyecto

### Error: "Login failed" o "401 Unauthorized" al ejecutar
**Causa:** API Key inválida en config.json

**Solución:**
1. Verifica que `src/main/resources/config.json` existe
2. Verifica que el `apiKey` es correcto (obtén uno nuevo del profesor si es necesario)
3. Verifica que el `host` es correcto: `wss://trading.hellsoft.tech/ws`

### Gradle se queda en "Downloading" o tarda mucho
**Causa:** Primera compilación descarga dependencias

**Solución:**
- Es normal la primera vez (puede tardar 2-5 minutos)
- Verifica tu conexión a internet
- Si falla, intenta: `gradlew.bat clean build --refresh-dependencies`

### Checkstyle o PMD reportan muchos errores
**Causa:** El código no sigue los estándares configurados

**Solución:**
1. Primero ejecuta: `gradlew.bat spotlessApply` (auto-formatea)
2. Lee los errores y corrígelos manualmente
3. La mayoría son por no seguir la regla "No Else"

### Tests fallan con "NullPointerException"
**Causa:** No se cargó la configuración o faltan archivos

**Solución:**
1. Verifica que `config.json` existe
2. Verifica que tiene todos los campos requeridos
3. En tests, usa mocks o crea una configuración de prueba
```

#### Tabla de Eventos (del Original)

```markdown
### Eventos del EventListener

| Evento | Cuándo se Dispara | Qué Hacer |
|--------|-------------------|-----------|
| `onLoginOk()` | Conexión exitosa | Inicializar tu estado (balance, inventario inicial) |
| `onTicker()` | Actualización de precios | Decidir si comprar/vender basado en precios |
| `onFill()` | Orden ejecutada | Actualizar tu inventario y balance local |
| `onBalanceUpdate()` | Cambio en balance | Actualizar tu registro de dinero disponible |
| `onInventoryUpdate()` | Cambio en inventario | Actualizar tu registro de productos |
| `onOffer()` | Recibiste una oferta | Decidir si aceptar/rechazar la oferta |
| `onOrderAck()` | Orden confirmada | Registrar que el servidor recibió tu orden |
| `onError()` | Error del servidor | Manejar errores y reintentar si es necesario |
| `onLogout()` | Desconexión | Cleanup y guardar estado si es necesario |
```

---

## 🎯 Plan de Implementación (30 minutos)

### Paso 1: Backup y Renombrado (5 min)
```bash
cd C:\Users\juanb\Github\Proyecto-Avanzada-Java

# Backup del original
copy README.md documentacion\README_BASE_ORIGINAL.md

# Renombrar el tuyo
del README.md
ren README-Propio.md README.md
```

### Paso 2: Agregar Secciones Faltantes (20 min)
Editar `README.md` y agregar:
1. Sección detallada de configuración de IntelliJ (después de "Configuración Inicial")
2. Expandir troubleshooting con los 8 problemas del original
3. Agregar tabla de eventos del EventListener

### Paso 3: Revisar y Probar (5 min)
```bash
# Ver el resultado en un visor de markdown
# O abrir en GitHub/IDE para verificar que se ve bien
```

---

## 📋 Checklist Final

Después de la fusión, tu README.md debería tener:

- [ ] Sección de requisitos previos ✅
- [ ] Configuración inicial (gradle.properties, config.json) ✅
- [ ] **Configuración detallada de IntelliJ IDEA** ✅ (del original)
- [ ] Estructura del proyecto ✅
- [ ] Arquitectura y componentes ✅
- [ ] Cómo probar que funciona ✅
- [ ] Desarrollo de lógica de negocio ✅
- [ ] **Tabla de eventos del EventListener** ✅ (del original)
- [ ] Casos de uso típicos ✅
- [ ] Estándares de código ✅
- [ ] Comandos útiles ✅
- [ ] **Troubleshooting ampliado (8+ problemas)** ✅ (del original)
- [ ] Flujo de trabajo con Git ✅
- [ ] Referencias a otros documentos (INDICE.md, etc.) ✅

---

## 🎉 Resultado Final

Tendrás un **README.md único y completo** que:
- ✅ Está personalizado a TU proyecto
- ✅ Tiene instrucciones detalladas de setup
- ✅ Incluye troubleshooting exhaustivo
- ✅ Contiene ejemplos prácticos
- ✅ Está integrado con tu documentación
- ✅ Sirve tanto para principiantes como para desarrollo avanzado

**Y mantendrás el original archivado** en `documentacion/` por si necesitas consultarlo.

---

¿Quieres que te ayude a hacer la fusión automáticamente?
# 📊 Comparación: README.md vs README-Propio.md

## 🔍 Análisis Comparativo

### README.md (Parece ser del proyecto base original)

**Características:**
- ✅ Muy profesional y pulido
- ✅ Instrucciones claras de setup con GitHub Packages
- ✅ Enfocado en "spacial-trading-bot-base" 
- ✅ Configuración de IntelliJ IDEA detallada
- ✅ Sección de troubleshooting completa
- ✅ Incluye configuración de herramientas de calidad

**Contenido Clave:**
- Requisitos previos (Java 25, IntelliJ, Git, GitHub)
- Autenticación con GitHub Packages (PAT)
- Configuración de IntelliJ (JDK 25, Lombok, formato)
- Compilación y ejecución (Gradle)
- Explicación del código de ejemplo (Main.java)
- Estructura del proyecto (simplificada - 4 archivos base)
- Herramientas de calidad (Spotless, Checkstyle, PMD)
- 8 problemas comunes con soluciones

**Fortalezas:**
- Muy didáctico
- Paso a paso detallado
- Incluye ejemplos de código
- Tabla de eventos del EventListener

---

### README-Propio.md (Versión personalizada/adaptada)

**Características:**
- ✅ Más adaptado a TU proyecto actual
- ✅ Enlaces a la carpeta `documentacion/`
- ✅ Más ejemplos de casos de uso prácticos
- ✅ Explicación más extensa de arquitectura
- ✅ Incluye modelos de dominio (Recipe, Role)
- ✅ Más enfocado en desarrollo de lógica de negocio

**Contenido Clave:**
- Referencias a INDICE.md y TUTORIAL_PRIMER_DIA.md
- Estructura del proyecto MÁS COMPLETA (incluye model/, service/, exception/)
- Arquitectura detallada de componentes
- 4 pruebas de verificación (compilación, ejecución, linting, tests)
- Casos de uso típicos:
  - Market Maker Simple
  - Arbitraje de Crafteo
  - Gestión de Riesgo
- Ejemplos extensos de código
- Desarrollo de lógica de negocio (dónde agregar código)
- Sección de estándares más detallada

**Fortalezas:**
- Integrado con el resto de tu documentación
- Más ejemplos prácticos
- Mejor para fase de desarrollo
- Incluye estrategias de trading

---

## ✅ RECOMENDACIÓN

### Opción RECOMENDADA: Fusión Estratégica

**Razón:** Ambos archivos tienen contenido valioso pero diferente enfoque.

**Plan de Acción:**

#### 1. Mantener README-Propio.md como README.md Principal

```bash
# Backup del README antiguo
mv README.md README_BASE_ORIGINAL.md

# Renombrar el tuyo como principal
mv README-Propio.md README.md
```

**Por qué:**
- Está mejor integrado con tu documentación actual
- Incluye referencias a `documentacion/`
- Tiene más ejemplos prácticos
- Refleja mejor TU proyecto actual

#### 2. Mejorar el README.md con contenido del original

**Agregar estas secciones del README.md original que faltan:**

1. **Configuración de IntelliJ IDEA más detallada**
   - Importar el proyecto
   - Configurar JDK 25
   - Sincronizar Gradle
   - Configurar Lombok
   - Importar configuración de formato

2. **Sección de Troubleshooting más completa**
   - Los 8 problemas específicos del README original son muy útiles

3. **Explicación del código de ejemplo (Main.java)**
   - La tabla de eventos es muy clara

#### 3. Archivar el Original

```bash
# Mover a documentación para referencia
mv README_BASE_ORIGINAL.md documentacion/README_BASE_ORIGINAL.md
```

---

## 📝 Contenido a Fusionar

### Del README.md Original → Agregar a README-Propio.md

#### Sección: Configuración de IntelliJ IDEA

```markdown
## 💻 Configuración de IntelliJ IDEA (Detallada)

### 1. Importar el Proyecto
1. Abre IntelliJ IDEA
2. Selecciona **"Open"** (no "New Project")
3. Navega hasta el directorio del proyecto
4. Selecciona el archivo `build.gradle.kts`
5. En el diálogo, selecciona **"Open as Project"**

### 2. Configurar el JDK 25
1. Ve a **File** → **Project Structure** (o `Ctrl+Alt+Shift+S`)

