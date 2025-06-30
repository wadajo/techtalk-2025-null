# Evitando NPE con JSpecify y NullAway

### Introducción

Quienes programamos en Java nos hemos topado más de una vez con la temida `NullPointerException`. Verificar una y otra vez si un objeto es nulo no debería ser la regla, sino que la semántica misma del lenguaje debería facilitárnoslo (como sucede, por ejemplo, en **Kotlin**). El proyecto [**JSpecify**](https://jspecify.dev/) apunta a declarar en cada API cómo es el manejo aceptable de campos `null`; desarrollado a lo largo de varios años, ha llegado a su versión `1.0.0` y apunta a convertirse en un estándar para el futuro próximo a partir de su incorporación en todo el código de **[Spring Framework 7](https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-7.0-Release-Notes)**

### Este demo (Julio 2025)

Detalles técnicos:
- Java 17+ (recomendado Java 24)
- Spring Boot 4 (actualmente en Snapshot)
- Spring Framework 7 (actualmente en Snapshot)
- JSpecify 1.0.0
- WireMock
- NullAway