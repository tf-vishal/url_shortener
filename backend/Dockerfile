# Use a base image
FROM ubuntu:latest


# Install Java 21
RUN apt-get update \
    && apt-get install -y wget \
    && wget -qO- https://download.oracle.com/java/21/latest/jdk-21_linux-aarch64_bin.tar.gz | tar xvz -C /usr/local/ \
    && mv /usr/local/jdk-21 /usr/local/java \
    && rm -rf /var/lib/apt/lists/*


# Set Java environment variables
ENV JAVA_HOME /usr/local/java
ENV PATH $PATH:$JAVA_HOME/bin


# Set the working directory in the container
WORKDIR /app


# Copy JAR file into the container
COPY target/urlshortener-0.0.1-SNAPSHOT.jar app.jar


# Expose the application port
EXPOSE 8080


# Command to run the application
CMD ["java", "-jar", "app.jar"]