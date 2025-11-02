# 5SKYE Backend (Spring Boot)

Spring Boot 3 + Java 17 backend for the 5SKYE Digital Twin Platform.

## Features

- Tower management (CRUD, summaries, validation, dependency checks)
- Hardware inventory (CRUD + server‑side search: vendor, serial, warranty)
- Maintenance management (CRUD, status filters, overdue queries)
- 3D model integration (upload path + per‑tower assignment)
- Telemetry ingest scheduler (live pulls → normalize → persist snapshots)
- AI utilities (analytics endpoints, anomaly/forecast helpers)
- Auth (JWT endpoints used by the frontend)

## Run

Prerequisites: Java 17+, Maven 3.6+

```bash
./mvnw spring-boot:run
```

Base URL: `http://localhost:8088`

## Key Endpoints (selection)

Towers
- `GET /api/towers` — list
- `GET /api/towers/{id}` — details
- `POST /api/towers` — create
- `PUT /api/towers/{id}` — update
- `DELETE /api/towers/{id}` — delete
- `PUT /api/towers/{id}/3d-model` — set model path
- `GET /api/towers/{id}/telemetry/live` — proxy live telemetry for a tower

Hardware
- `GET /api/hardware/tower/{towerId}` — list per tower
- `GET /api/hardware/search?vendor=&serial=&warrantyAfter=&warrantyBefore=` — search
- `POST /api/hardware` / `PUT /api/hardware/{id}` / `DELETE /api/hardware/{id}`

Maintenance
- `GET /api/maintenance` — list
- `GET /api/maintenance/tower/{towerId}` — per tower
- `GET /api/maintenance/status/{status}` — by status
- CRUD endpoints for create/update/delete

AI (built‑in analytics helpers)
- `GET /api/ai/towers/{towerId}/anomalies` — z‑score anomalies over a window
- `GET /api/ai/towers/{towerId}/predictions` — trend/insight helpers
- `GET /api/ai/towers/{towerId}/forecast/temperature` — demo forecast

Health / Utilities
- `GET /api/health/connection-test` — simple connectivity test used by the UI
- Data cleanup + validation endpoints available under `/api/towers/*`

## Telemetry Ingest

`TelemetryIngestScheduler` runs every 30s:

1) Enumerates towers with an `apiEndpointUrl`
2) Pulls the latest snapshot (simulator or real)
3) Normalizes → persists to `telemetry_data`
4) Publishes gauges via Micrometer (Prometheus‑scrapable)

## Configuration

- Port: `8088`
- DB: H2 for local; PostgreSQL recommended for persistence
- Uploads: `uploads/` directory for models
- Security: JWT for auth endpoints (used by the frontend)

## Build / Test

```bash
./mvnw clean package
./mvnw test
```

## Notes

- Pair this service with the simulator (`8080`), frontend (`3000`), and observability stack (`9090`/`3001`).
- See repository root `README.md` and `docs/THESIS_GUIDE.md` for end‑to‑end and thesis guidance.
