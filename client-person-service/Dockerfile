# Etapa de compilación
FROM gradle:8.5.0-jdk21 AS build

#WORKDIR /app
# Usa un directorio limpio para evitar conflictos de caché
WORKDIR /home/gradle/project

#COPY --chown=gradle:gradle . .
# Solo copia los archivos necesarios para evitar reconstrucciones innecesarias
COPY --chown=gradle:gradle build.gradle settings.gradle ./
COPY --chown=gradle:gradle src ./src

RUN gradle build -x test --no-daemon

# Etapa final
FROM eclipse-temurin:21-jre
WORKDIR /app

#COPY --from=build /app/build/libs/*.jar cliente.jar
# Copia únicamente el JAR generado
COPY --from=build /home/gradle/project/build/libs/*.jar client.jar

EXPOSE 3001
#ENV PORT 3001
#EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "client.jar"]