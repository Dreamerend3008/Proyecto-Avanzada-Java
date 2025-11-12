# ⚡ INICIO RÁPIDO - 5 Minutos

## 🎯 Lee esto primero si tienes prisa

### Si eres completamente nuevo:
```
1. Ve a → INDICE.md
2. Luego → TUTORIAL_PRIMER_DIA.md
3. Dedica 2-3 horas a configurar todo
```

### Si solo quieres compilar y ejecutar:
```bash
# 1. Crear gradle.properties desde el sample
copy gradle.properties.sample gradle.properties

# 2. Editar gradle.properties con tus credenciales de GitHub
# gpr.user=TU_USUARIO
# gpr.token=TU_TOKEN

# 3. Crear config.json desde el sample
copy src\main\resources\config.sample.json src\main\resources\config.json

# 4. Editar config.json con tu API key
# "apiKey": "TU_API_KEY"
# "team": "Tu Equipo"

# 5. Compilar
gradlew.bat build

# 6. Ejecutar
gradlew.bat run
```

### Si quieres ejemplos de código:
```
Ve a → DESARROLLO_EJEMPLOS.md
```

### Si tienes un error:
```
Ve a → README.md → Sección "Solución de Problemas"
```

---

## 📚 Todos los Documentos Disponibles

| Documento | Para Quién | Tiempo |
|-----------|------------|--------|
| **INDICE.md** | Todos - Punto de entrada | 5 min |
| **TUTORIAL_PRIMER_DIA.md** | Desarrolladores nuevos | 2-3 h |
| **README.md** | Todos - Referencia completa | 30 min |
| **DESARROLLO_EJEMPLOS.md** | Implementando funcionalidades | Variable |
| **AGENTS.md** | Escribiendo código limpio | 20 min |
| **BUILD_STATUS.md** | Problemas de compilación | 5 min |
| **RESUMEN_DOCUMENTACION.md** | Profesores/Líderes | 10 min |

---

## ✅ Checklist Mínimo (15 minutos)

- [ ] Java 25 instalado: `java -version`
- [ ] Proyecto clonado: `git clone ...`
- [ ] `gradle.properties` creado con credenciales
- [ ] `config.json` creado con API key
- [ ] Compilación exitosa: `gradlew.bat build`
- [ ] Ejecución exitosa: `gradlew.bat run`

---

## 🆘 Ayuda Ultra-Rápida

**Problema:** No compila - "401 Unauthorized"  
**Solución:** Verifica `gradle.properties` con tu usuario y token de GitHub

**Problema:** "Configuration file not found"  
**Solución:** `copy src\main\resources\config.sample.json src\main\resources\config.json`

**Problema:** "Login failed"  
**Solución:** Verifica el API key en `config.json`

**Problema:** IDE no reconoce clases  
**Solución:** Click derecho → "Reload Gradle Project"

---

## 🚀 Próximos Pasos

Una vez que tengas todo funcionando:

1. Lee **README.md** completo
2. Implementa tu primera estrategia con **DESARROLLO_EJEMPLOS.md**
3. Sigue los estándares de **AGENTS.md**

---

**¿Necesitas más ayuda?** → **INDICE.md** tiene todo organizado por temas.

