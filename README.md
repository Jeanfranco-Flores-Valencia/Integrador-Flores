# Your Peak — Frontend (Jetpack Compose + Material 3 + MVVM)

Paquete: `com.floresvalencia.yourpeak` · minSdk 24 · Tipografía única: **Arvo** (res/font)

```
data/          AuthRepository (interfaz + FakeAuthRepository), Validators
navigation/    AppNavigation (NavHost + sub-grafo de recuperación)
ui/theme/      Color, Type (Arvo en todos los estilos), Theme
ui/components/ PeakBackground, PeakTextField, PeakButton, AuthCard, PageIndicator...
ui/screens/    splash · login · register · forgot (3 pantallas, 1 ViewModel compartido)
```

- Reemplaza `res/drawable/splash_athlete.png` por la foto real del atleta (PNG con fondo transparente).
- Reemplaza `res/drawable/ic_logo.xml` por tu logo si lo tienes en SVG (File > New > Vector Asset).
- Código de verificación de prueba: **123456**.
- Conecta tu backend implementando `AuthRepository`.
