# URL Shortener Service

This is a simple URL shortener service implemented in Java with Spring Boot. It allows you to shorten long URLs and provides redirection functionality. It also gives a metric to check the most shortened urls.


## How to Execute

To run the URL shortener service, follow these steps:

1. **Clone the Repository**:

    ```
    git clone https://github.com/meankitmishra/url_shortener
    ```

2. **Build the Application**:

    ```
    cd backend
    mvn clean install
    ```

3. **Run the Docker Container**:

    ```
    docker-compose up -d
    ```

4. **Access the Service**:

- The URL shortener service will be available at `http://localhost:8080`.

5. **API Endpoints**:

- Shorten a URL:
  ```
  POST http://localhost:8080/shorten
  Body: { "urlToShorten": "https://example.com" }
  ```

- Redirect to the original URL:
  ```
  GET http://localhost:8080/{shortenedUrl}
  ```

- Get the Metrics:
  ```
  GET http://localhost:8080/metrics
  ```   
  
## Dependencies

The project uses:
- JDK 21
- Spring Boot
- Docker
- Redis
