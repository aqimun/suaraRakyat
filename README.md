# Suara Rakyat Application

This project consists of a Java Spring Boot backend API and a React.js frontend application built with Vite.

## Project Structure

- `apps/api`: Contains the Spring Boot backend application.
- `apps/web`: Contains the React.js frontend application (Vite).
- `FE/`: Original frontend files (HTML, CSS, JS) - **Note: This folder is deprecated after migration to React/Vite.**

## Prerequisites

Before running the application, ensure you have the following installed:

1.  **Java Development Kit (JDK) 17 or higher**:
    *   Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.java.net/install/).
    *   Set `JAVA_HOME` environment variable.

2.  **Apache Maven 3.8.x or higher**:
    *   Download from [Maven Official Site](https://maven.apache.org/download.cgi).
    *   Ensure `mvn` command is available in your PATH.

3.  **Node.js 18.x or higher & npm (Node Package Manager)**:
    *   Download from [Node.js Official Site](https://nodejs.org/en/download/).
    *   `npm` is usually installed with Node.js.

4.  **PostgreSQL Database**:
    *   Download and install [PostgreSQL](https://www.postgresql.org/download/).
    *   Ensure PostgreSQL server is running.

## Database Setup (PostgreSQL)

1.  **Create a Database**:
    Open your PostgreSQL client (e.g., `psql`, pgAdmin) and create a new database named `suara_rakyat`.

    ```sql
    CREATE DATABASE suara_rakyat;
    ```

2.  **Configure Database Credentials**:
    The backend is configured to use the following credentials by default. If your PostgreSQL setup uses different credentials, update `apps/api/src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/suara_rakyat
    spring.datasource.username=postgres
    spring.datasource.password=postgres
    ```
    **Important**: Ensure your PostgreSQL user `postgres` has the password `postgres` or update the `application.properties` file accordingly.

3.  **Configure Client Authentication (`pg_hba.conf`)**:
    Ensure your `pg_hba.conf` file (located in your PostgreSQL data directory) allows connections from `localhost` for the `postgres` user and `suara_rakyat` database. A common entry looks like this:

    ```
    host    all             all             127.0.0.1/32            md5
    ```
    After modifying `pg_hba.conf`, you **must restart your PostgreSQL service** for changes to take effect.

## Running the Backend (Spring Boot API)

1.  **Navigate to the backend directory**:
    ```bash
    cd apps/api
    ```

2.  **Build and Run the application**:
    Use Maven Wrapper to build and run the Spring Boot application. This will also handle database schema updates (DDL-auto=update).
    ```bash
    ./mvnw spring-boot:run
    ```
    The backend API will start on `http://localhost:8080`. You can access the Swagger UI for API documentation at `http://localhost:8080/swagger-ui.html`.

## Running the Frontend (React.js with Vite)

1.  **Navigate to the frontend directory**:
    ```bash
    cd apps/web
    ```

2.  **Install dependencies**:
    ```bash
    npm install
    ```

3.  **Run the development server**:
    ```bash
    npm run dev
    ```
    The frontend application will start on `http://localhost:5173/` (or another port if 5173 is in use, check your terminal output).

## Troubleshooting Common Issues

*   **`Port 8080 was already in use` (Backend)**:
    This means another process is using port 8080. You can either stop the other process or change the `server.port` in `apps/api/src/main/resources/application.properties` to a different port (e.g., `server.port=8081`).

*   **`Port 5173 is in use` (Frontend)**:
    Vite will automatically try another port. Just use the URL provided in the terminal output (e.g., `http://localhost:5174/`).

*   **`Connection to localhost:5432 refused` (Backend)**:
    This indicates a problem with your PostgreSQL database connection.
    *   Ensure your PostgreSQL server is running.
    *   Verify `spring.datasource.url`, `username`, and `password` in `apps/api/src/main/resources/application.properties`.
    *   Check your `pg_hba.conf` file to ensure `localhost` connections are allowed.
    *   Restart your PostgreSQL service after any `pg_hba.conf` changes.

*   **`The JSX syntax extension is not currently enabled` (Frontend)**:
    This issue should be resolved as `.js` files containing JSX have been renamed to `.jsx` and `vite.config.js` has been updated. If it persists, ensure all relevant files are `.jsx` and `vite.config.js` is correctly configured.

*   **`Failed to resolve import "web-vitals"` (Frontend)**:
    This indicates `web-vitals` is not installed. Navigate to `apps/web` and run `npm install web-vitals`.

*   **`Circular dependency` (Backend)**:
    This issue should be resolved by the changes made to `JwtAuthenticationFilter.java` and `SecurityConfig.java`. If it persists, please provide the full stack trace.

*   **CORS Errors (Frontend trying to connect to Backend)**:
    Ensure `cors.allowed-origins` in `apps/api/src/main/resources/application.properties` includes the exact URL where your frontend is running (e.g., `http://localhost:5173` or `http://localhost:5174`).
