# Idle Realms — RPG Idle Game Engine

A backend engine for a browser-based RPG Idle game, built with Spring Boot 3.
The system handles dungeon exploration, automated combat, inventory management,
and player progression through a secure REST API.

> **Frontend / UI Repository:** [IdleRealms-UI](https://github.com/RybaLP/IdleRealms-UI)

---

## What is Idle Realms?

Idle Realms is a passion project combining classic RPG mechanics with idle game
progression. Players explore dungeons, battle enemies automatically, collect loot,
and grow their characters — all driven by a clean, well-structured backend engine.

The backend is designed with scalability, maintainability, and clean architecture
in mind. It is fully Dockerized and ready for frontend or mobile integration via REST.

---

## Architecture

The repository is structured as a **multi-module monorepo** containing two distinct modules:

- **`src/`** — The core game engine: combat, dungeons, inventory, player progression
- **`social-service/`** — A self-contained social module handling guilds, messaging,
  and real-time notifications

Both modules are part of the same Gradle build and share the same Docker Compose setup,
but are architecturally independent. They communicate asynchronously via **Apache Kafka**,
keeping game logic and social logic fully decoupled.

The core engine follows a **layered architecture**:

- **Controllers** — Handle HTTP requests and map them to use cases
- **Services** — Contain core business and game logic
- **Repositories** — Manage data persistence via Spring Data JPA
- **Domain layer** — RPG mechanics isolated from infrastructure: combat formulas,
  stat calculations, progression rules

The social-service module follows **Hexagonal Architecture (Ports & Adapters)**,
keeping business logic completely independent from infrastructure concerns.

---

## Game Systems

### Character System
- Base stats: Strength, Vitality, Agility
- Experience points and level-up system
- Stat scaling per level
- Health pool and regeneration mechanics

### Combat System
- Turn-based automated (idle) combat
- Damage calculation based on stats and equipped items
- Critical hit mechanics
- Defense and mitigation formulas

```
FinalDamage = (BaseAttack + StrengthModifier) - TargetDefense
```

### Inventory System
- Equipment slots: Weapon, Armor, and more
- Randomized loot generation on dungeon completion
- Stat bonuses applied dynamically from equipped items

### Dungeon System
- Progressive dungeon stages with increasing difficulty
- Enemy stat scaling per dungeon level
- Reward distribution on stage completion
- Fully automated idle progression loop

---

## API

Swagger UI is available locally at:

```
http://localhost:8080/swagger-ui.html
```

Endpoints cover:
- Player management
- Combat actions
- Inventory operations
- Dungeon progression

---

## Running with Docker

No local Java or PostgreSQL installation required.

**Prerequisites:** Docker Desktop installed and running.

```bash
# Clone the repository
git clone https://github.com/RybaLP/IdleRealms.git
cd IdleRealms

# Build and launch the full stack
docker compose up --build
```

The API will be available at `http://localhost:8080`.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17+ |
| Framework | Spring Boot 3 |
| Security | Spring Security + JWT |
| Persistence | Hibernate / JPA + PostgreSQL |
| Messaging | Apache Kafka |
| Documentation | Swagger / OpenAPI |
| Containerization | Docker |

---

## Testing

- Unit tests covering core game logic (combat formulas, stat calculations)
- Integration tests for REST endpoints

---

## Social Service — Multiplayer Module

Located in `social-service/`, this module is a self-contained unit within the monorepo
responsible for all real-time player interactions, guild management, and messaging.

It communicates with the core engine via **Apache Kafka** — game-side events such as
a player completing a dungeon or leveling up are published to Kafka topics,
which the social module consumes to trigger notifications or guild-related updates.
This keeps both modules fully decoupled with no direct service-to-service calls.

### Guild System (Event-Driven)

Internally, the social module uses Spring's `ApplicationEventPublisher` to communicate
between its own components, eliminating circular dependencies entirely.
Events like `MemberKickedEvent` and `InvitationAcceptedEvent` flow through the
application context, keeping the system loosely coupled and easy to extend.

Every guild action (e.g. accepting an invitation) is a fully atomic operation:
- Updates the guild member list
- Assigns `guildId` to the player's profile
- Cancels all other pending invitations in the same transaction
- 

### Ravens & Scrolls — Notification System

A universal `NotificationPort` handles:
- Private player-to-player messages
- Guild invitations with full state lifecycle (pending → accepted / cancelled)
- System announcements (new member joined, player exiled)
- Cross-module notifications triggered by Kafka events from the core engine

