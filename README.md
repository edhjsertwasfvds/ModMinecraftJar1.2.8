# CS2 obfuscated JAR

Launch from console:

```
java --enable-native-access=ALL-UNNAMED -jar local.jar
```

Requires Java 17+ and CS2 running.

Files:
- `local.jar` — glow-only obfuscated JAR
- `Main_obf.jar` — glow + box ESP obfuscated JAR
- `TestProc.jar` — diagnostic tool to verify process scanning
- `*.java` — obfuscated sources
