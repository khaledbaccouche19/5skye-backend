# Tower Deletion Database Constraint Solution

## Problem Description
The backend was experiencing database constraint violations when attempting to delete towers that had associated hardware components, alerts, threshold rules, or telemetry data. This is a common issue in relational databases when foreign key relationships exist without proper cascade configurations.

## Root Cause
The `Tower` entity had no reverse relationships defined, and the related entities (`Hardware`, `Alert`, `ThresholdRule`, `TelemetryData`) had `@ManyToOne` relationships pointing to `Tower` without cascade operations configured.

## Solution Implemented

### 1. Entity Relationship Updates
Updated the `Tower` entity to include reverse relationships with proper cascade configurations:

```java
@OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Hardware> hardware = new ArrayList<>();

@OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Alert> alerts = new ArrayList<>();

@OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, orphanRemoval = true)
private List<ThresholdRule> thresholdRules = new ArrayList<>();

@OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, orphanRemoval = true)
private List<TelemetryData> telemetryData = new ArrayList<>();
```

### 2. Enhanced Repository Methods
Added a custom query method to fetch towers with all relationships loaded:

```java
@Query("SELECT t FROM Tower t " +
       "LEFT JOIN FETCH t.hardware " +
       "LEFT JOIN FETCH t.alerts " +
       "LEFT JOIN FETCH t.thresholdRules " +
       "LEFT JOIN FETCH t.telemetryData " +
       "WHERE t.id = :id")
Optional<Tower> findByIdWithRelationships(@Param("id") Long id);
```

### 3. Improved Service Layer
Enhanced the `TowerService` with multiple deletion strategies:

- **Hard Deletion**: Completely removes the tower and all related data
- **Soft Deletion**: Marks the tower as `DEACTIVATED` while preserving data
- **Dependency Checking**: Provides information about what will be deleted
- **Safe Deletion**: Checks if deletion is safe before proceeding

### 4. New API Endpoints

#### Check Deletion Safety
```
GET /api/towers/{id}/can-delete
```
Returns `true` if the tower can be safely deleted (no dependencies).

#### Get Dependencies Information
```
GET /api/towers/{id}/dependencies
```
Returns detailed information about what will be deleted.

#### Soft Deletion (Recommended)
```
PUT /api/towers/{id}/deactivate
```
Marks the tower as deactivated instead of deleting it.

#### Reactivation
```
PUT /api/towers/{id}/reactivate
```
Reactivates a deactivated tower.

#### Enhanced Hard Deletion
```
DELETE /api/towers/{id}/with-info
```
Deletes the tower and returns information about what was deleted.

## Benefits of This Solution

### 1. **Data Integrity**: Proper cascade operations ensure all related data is handled correctly
### 2. **Flexibility**: Multiple deletion strategies for different use cases
### 3. **User Experience**: Clear information about what will be deleted
### 4. **Recovery**: Soft deletion allows data recovery if needed
### 5. **Performance**: Optimized queries with relationship fetching

## Usage Recommendations

### For Production Systems
- Use **soft deletion** (`PUT /deactivate`) as the primary method
- This preserves data integrity and allows for recovery
- Only use hard deletion for cleanup operations

### For Development/Testing
- Use **hard deletion** with dependency checking
- This ensures clean test environments
- Provides clear feedback about data relationships

### For User Interfaces
- Always check `GET /can-delete` before showing delete options
- Display dependency information using `GET /dependencies`
- Offer both soft and hard deletion options with clear explanations

## Database Schema Updates
The solution automatically updates the database schema when the application restarts (due to `spring.jpa.hibernate.ddl-auto=update`). No manual database migrations are required.

## Testing the Solution
1. Restart the Spring Boot application
2. Try deleting a tower with dependencies
3. Verify that all related data is properly handled
4. Test the new API endpoints for dependency checking

## Future Enhancements
- Add audit logging for all deletion operations
- Implement scheduled cleanup of deactivated towers
- Add bulk operations for multiple towers
- Implement data archiving before deletion
