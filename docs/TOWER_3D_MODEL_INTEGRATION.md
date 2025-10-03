# Tower 3D Model Integration Guide

## Overview
This guide explains how to integrate 3D models (GLB files) with towers in the 5skye backend system.

## New Features Added

### 1. Database Field
- **`model_3d_path`** - Stores the path to the 3D model file
- **Type**: String (VARCHAR)
- **Example**: `/models/tower1.glb`

### 2. File Upload Endpoint
- **`POST /api/upload/model`** - Upload 3D model files (GLB/GLTF)
- **`DELETE /api/upload/model`** - Delete 3D model files

### 3. New API Endpoints

#### Upload 3D Model File
```
POST /api/upload/model
Content-Type: multipart/form-data

file: [GLB/GLTF file]
Response: "/models/uuid-filename.glb"
```

#### Delete 3D Model File
```
DELETE /api/upload/model?path=/models/filename.glb
Response: "Model deleted successfully"
```

#### Update Tower 3D Model
```
PUT /api/towers/{id}/3d-model
Content-Type: application/json

"models/tower1.glb"
```

#### Get Tower 3D Model
```
GET /api/towers/{id}/3d-model
Response: "models/tower1.glb"
```

### 4. Updated Endpoints

#### Create Tower (now includes 3D model)
```
POST /api/towers
{
  "name": "Tower Name",
  "status": "online",
  "latitude": 40.7128,
  "longitude": -74.0060,
  "city": "New York",
  "model3dPath": "/models/tower1.glb"  // NEW FIELD
}
```

#### Update Tower (now includes 3D model)
```
PUT /api/towers/{id}
{
  "name": "Updated Tower Name",
  "model3dPath": "/models/tower2.glb"  // NEW FIELD
}
```

## File Structure Recommendation

```
your-project/
├── public/
│   └── models/
│       ├── tower1.glb
│       ├── tower2.glb
│       ├── default-tower.glb
│       └── tower-types/
│           ├── communication.glb
│           ├── surveillance.glb
│           └── weather.glb
```

## Frontend Integration

### 1. Upload 3D Model File
```javascript
// Upload GLB file
const uploadModel = async (file) => {
  const formData = new FormData();
  formData.append('file', file);
  
  const response = await fetch('/api/upload/model', {
    method: 'POST',
    body: formData
  });
  
  if (response.ok) {
    const modelPath = await response.text();
    // modelPath will be like "/models/uuid-filename.glb"
    return modelPath;
  } else {
    throw new Error('Upload failed');
  }
};

// Usage
const fileInput = document.getElementById('modelFile');
fileInput.addEventListener('change', async (e) => {
  const file = e.target.files[0];
  if (file) {
    try {
      const modelPath = await uploadModel(file);
      // Now assign this modelPath to a tower
      await assignModelToTower(towerId, modelPath);
    } catch (error) {
      console.error('Upload failed:', error);
    }
  }
});
```

### 2. Load 3D Model
```javascript
// Get tower data including 3D model path
const tower = await fetch(`/api/towers/${towerId}`).then(r => r.json());

// Load 3D model using the path
const modelUrl = `http://localhost:8088${tower.model3dPath}`;
// Use with Three.js, React Three Fiber, etc.
```

### 3. Update 3D Model
```javascript
// Update tower's 3D model
const response = await fetch(`/api/towers/${towerId}/3d-model`, {
  method: 'PUT',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify('models/new-tower.glb')
});
```

### 4. Handle Missing 3D Models
```javascript
// Use default model if none specified
const modelPath = tower.model3dPath || '/models/default-tower.glb';
```

### 5. Delete 3D Model
```javascript
// Delete model file
const deleteModel = async (modelPath) => {
  const response = await fetch(`/api/upload/model?path=${encodeURIComponent(modelPath)}`, {
    method: 'DELETE'
  });
  
  if (response.ok) {
    console.log('Model deleted successfully');
  } else {
    throw new Error('Delete failed');
  }
};
```

## Best Practices

### 1. File Naming Convention
- Use descriptive names: `communication-tower.glb`, `surveillance-tower.glb`
- Include version numbers: `tower-v1.glb`, `tower-v2.glb`
- Use lowercase and hyphens: `tower-type-location.glb`

### 2. File Organization
- Group by tower type: `/models/communication/`, `/models/surveillance/`
- Group by location: `/models/nyc/`, `/models/la/`
- Include metadata files: `tower1.glb`, `tower1.json` (for additional info)

### 3. Performance Considerations
- Keep GLB files under 5MB when possible
- Use LOD (Level of Detail) models for different distances
- Compress textures and optimize geometry

## Example Usage

### Create Tower with 3D Model
```bash
curl -X POST http://localhost:8088/api/towers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Central Park Tower",
    "status": "online",
    "latitude": 40.7829,
    "longitude": -73.9654,
    "city": "New York",
    "model3dPath": "/models/communication/central-park.glb"
  }'
```

### Update 3D Model
```bash
curl -X PUT http://localhost:8088/api/towers/1/3d-model \
  -H "Content-Type: application/json" \
  -d '"models/communication/central-park-v2.glb"'
```

### Get 3D Model Path
```bash
curl -X GET http://localhost:8088/api/towers/1/3d-model
```

## Migration Notes

- Existing towers will have `null` for `model3dPath`
- The field is optional, so existing functionality remains unchanged
- Database will automatically add the new column on restart

## Future Enhancements

1. **Model Validation** - Check if GLB file exists and is valid
2. **File Upload** - Direct file upload to server
3. **Model Preview** - Generate thumbnails for 3D models
4. **Version Control** - Track model changes and versions
5. **Cloud Storage** - Integration with AWS S3, Google Cloud Storage
