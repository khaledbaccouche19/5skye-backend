# 5Skye Backend

A Spring Boot backend service for the 5Skye Digital Twin Platform.

## Features

- **Tower Management**: CRUD operations for tower entities
- **Maintenance Management**: Schedule and track maintenance tasks
- **3D Model Integration**: Upload and manage 3D tower models
- **Telemetry Data**: Real-time data collection and processing
- **AI Analytics**: Anomaly detection and predictive insights

## Quick Start

1. **Prerequisites**
   - Java 17+
   - Maven 3.6+

2. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Access the API**
   - Base URL: `http://localhost:8088`
   - API Documentation: `http://localhost:8088/api`

## API Endpoints

### Towers
- `GET /api/towers` - List all towers
- `POST /api/towers` - Create new tower
- `GET /api/towers/{id}` - Get tower by ID
- `PUT /api/towers/{id}` - Update tower
- `DELETE /api/towers/{id}` - Delete tower

### Maintenance
- `GET /api/maintenance` - List all maintenance records
- `POST /api/maintenance` - Create maintenance record
- `GET /api/maintenance/{id}` - Get maintenance by ID
- `PUT /api/maintenance/{id}` - Update maintenance
- `DELETE /api/maintenance/{id}` - Delete maintenance

### 3D Models
- `PUT /api/towers/{id}/3d-model` - Upload 3D model
- `GET /api/towers/{id}/3d-model` - Get 3D model path

## Configuration

The application uses `application.properties` for configuration:
- Database: H2 (in-memory for development)
- Port: 8088
- File uploads: `uploads/` directory

## Documentation

See the `docs/` directory for detailed documentation:
- Backend Data Retrieval Guide
- Frontend Health Check Implementation
- Tower 3D Model Integration
- Tower Deletion Solution

## Development

### Building
```bash
./mvnw clean package
```

### Testing
```bash
./mvnw test
```

### Code Style
The project follows standard Java conventions and Spring Boot best practices.
