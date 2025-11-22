# Use the Eclipse temurin alpine official image
FROM eclipse-temurin:21-jdk-alpine

# Create and change to the app directory.
WORKDIR /app

# Copy everything (inclusive mvnw, .mvn/**, src/**, pom.xml)
COPY . .

# Give permission to the Maven Wrapper
RUN chmod +x mvnw

# Build the application
RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean install

# Run the Quarkus app
CMD ["java", "-jar", "target/quarkus-app/quarkus-run.jar"]
