# 🚀 5skye Backend Data Retrieval Guide

## 📋 Overview
This guide explains how your backend retrieves and processes data when the frontend calls the pull test endpoints.

---

## 🔗 Available Endpoints

### 1. Connection Test
- **URL:** `GET /api/health/connection-test`
- **Response:** `{"status":"SUCCESSFUL"}` or `{"status":"NOT SUCCESSFUL"}`

### 2. Pull Test
- **URL:** `GET /api/towers/pull-test`
- **Response:** `{"status":"SUCCESSFUL"}` or `{"status":"NOT SUCCESSFUL"}`

### 3. Ping Test
- **URL:** `GET /api/health/ping`
- **Response:** `{"status":"SUCCESSFUL"}`

---

## 🔄 Data Retrieval Flow

### Connection Test Flow
```
Frontend Request → Controller → Database Test + Repository Test → Response
```

**Step-by-step:**
1. **HTTP Request:** `GET /api/health/connection-test`
2. **Controller:** `HealthCheckController.connectionTest()`
3. **Database Test:** Executes `SELECT 'OK' as status`
4. **Repository Test:** Calls `count()` on all repositories
5. **Status Decision:** Returns "SUCCESSFUL" only if both tests pass

### Pull Test Flow
```
Frontend Request → Controller → Service → Repository → Database → Response
```

**Step-by-step:**
1. **HTTP Request:** `GET /api/towers/pull-test`
2. **Controller:** `TowerController.pullTest()`
3. **Service Layer:** `towerService.getAllTowers()`
4. **Repository Layer:** `towerRepository.findAll()`
5. **Database:** Executes SQL queries via JPA/Hibernate
6. **Data Validation:** Checks if all operations return valid data
7. **Response:** Returns "SUCCESSFUL" or "NOT SUCCESSFUL"

---

## 🗄️ Database Queries Executed

### Connection Test Queries
```sql
-- Simple connectivity test
SELECT 'OK' as status

-- Repository count tests
SELECT COUNT(*) FROM towers
SELECT COUNT(*) FROM alerts  
SELECT COUNT(*) FROM hardware
SELECT COUNT(*) FROM telemetry_data
```

### Pull Test Queries
```sql
-- Get all towers
SELECT tower_id, name, status, latitude, longitude, city, 
       battery, temperature, uptime, network_load, use_case, 
       region, last_maintenance, model_3d_path, created_at, updated_at
FROM towers

-- Get tower summaries
SELECT tower_id, name, status, city, region
FROM towers

-- Get individual tower
SELECT * FROM towers WHERE tower_id = ?
```

---

## 🏗️ Architecture Layers

### 1. Controller Layer
```java
@GetMapping("/pull-test")
public ResponseEntity<Map<String, Object>> pullTest() {
    // Tests data retrieval and returns simple status
}
```

### 2. Service Layer
```java
public List<TowerDTO> getAllTowers() {
    return towerRepository.findAll().stream()
            .map(towerMapper::toDTO)
            .collect(Collectors.toList());
}
```

### 3. Repository Layer
```java
@Repository
public interface TowerRepository extends JpaRepository<Tower, Long> {
    // JPA automatically generates: SELECT * FROM towers
}
```

### 4. Database Layer
- **Database:** PostgreSQL
- **Connection Pool:** HikariCP
- **ORM:** Hibernate/JPA
- **Port:** 8088

---

## 📊 Response Examples

### Successful Connection Test
```json
{
  "status": "SUCCESSFUL"
}
```

### Successful Pull Test
```json
{
  "status": "SUCCESSFUL"
}
```

### Failed Test (Any Error)
```json
{
  "status": "NOT SUCCESSFUL"
}
```

---

## ⚡ Performance & Monitoring

- **Connection Pool:** HikariCP for database connections
- **Transaction Management:** Each test runs in its own transaction
- **Error Handling:** Catches exceptions at multiple levels
- **Logging:** Full SQL queries logged in DEBUG mode
- **Response Time:** Typically < 100ms for successful tests
- **Database:** PostgreSQL with connection pooling

---

## 🔍 What Each Endpoint Tests

### `/api/health/connection-test`
- ✅ Database connectivity
- ✅ Repository operations
- ✅ Overall system health

### `/api/towers/pull-test`
- ✅ Actual data retrieval from tower service
- ✅ Connection to tower service
- ✅ Data validation and processing

### `/api/health/ping`
- ✅ Simple connectivity test
- ✅ Basic backend responsiveness

---

## 💡 Frontend Integration

### JavaScript Example
```javascript
// Test connection before proceeding
const response = await fetch('/api/health/connection-test');
const result = await response.json();

if (result.status === "SUCCESSFUL") {
    console.log("✅ Backend connected!");
    // Proceed with app operations
} else {
    console.log("❌ Connection failed");
    // Show error to user
}
```

### React Example
```jsx
const [connectionStatus, setConnectionStatus] = useState(null);

useEffect(() => {
    const testConnection = async () => {
        try {
            const response = await fetch('/api/health/connection-test');
            const data = await response.json();
            setConnectionStatus(data.status);
        } catch (error) {
            setConnectionStatus("NOT SUCCESSFUL");
        }
    };
    
    testConnection();
}, []);

return (
    <div>
        {connectionStatus === "SUCCESSFUL" ? (
            <div className="success">✅ Backend Connected</div>
        ) : (
            <div className="error">❌ Connection Failed</div>
        )}
    </div>
);
```

---

## 🚨 Error Scenarios

### Database Connection Failed
- **Cause:** PostgreSQL server down, wrong credentials, network issues
- **Response:** `{"status":"NOT SUCCESSFUL"}`

### Repository Errors
- **Cause:** Table doesn't exist, permission issues, corrupted data
- **Response:** `{"status":"NOT SUCCESSFUL"}`

### Service Layer Errors
- **Cause:** Business logic errors, validation failures
- **Response:** `{"status":"NOT SUCCESSFUL"}`

---

## 📈 Monitoring & Debugging

### Enable Debug Logging
```properties
# application.properties
logging.level.org.springframework.web=DEBUG
logging.level.org.springframework=DEBUG
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Check Application Logs
```bash
# View real-time logs
tail -f logs/application.log

# Search for specific endpoint calls
grep "pull-test" logs/application.log
```

---

## 🔧 Configuration

### Database Configuration
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/intelli-tower
spring.datasource.username=postgres
spring.datasource.password=123
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Server Configuration
```properties
server.port=8088
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

---

## 📝 Summary

Your backend provides **simple, clean responses** that tell your frontend exactly what it needs to know:

- **"SUCCESSFUL"** = Backend is fully operational, data accessible
- **"NOT SUCCESSFUL"** = Something is wrong (database, service, or data layer)

The system automatically tests:
1. **Database connectivity**
2. **Repository operations**
3. **Data retrieval capabilities**
4. **Service layer functionality**

This gives you **real-time health monitoring** of your entire backend infrastructure with minimal response overhead! 🎯

---

*Generated for 5skye-backend-main project*
*Last updated: August 31, 2025*
