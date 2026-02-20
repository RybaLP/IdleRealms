Idle Realms Backend

A robust RPG Idle game engine built with Spring Boot 3. This project
simulates dungeon exploration, automated combat, inventory management,
and player progression through a secure RESTful API.

The system is designed with scalability, clean architecture principles,
and maintainable domain-driven game logic in mind.

------------------------------------------------------------------------

Features

-   JWT-based authentication & authorization
-   Swagger / OpenAPI documentation
-   Dockerized deployment
-   Layered architecture (Controller → Service → Repository → Domain)
-   Automated idle combat system
-   Inventory & equipment management
-   Character progression & leveling system
-   Dungeon exploration logic
-   REST API ready for frontend or mobile integration

------------------------------------------------------------------------

Architecture

The project follows a layered architecture pattern:

-   Controllers – Handle HTTP requests and responses
-   Services – Contain core business and game logic
-   Repositories – Manage data persistence
-   Domain layer – Contains RPG mechanics (combat formulas, stat
    calculations, progression rules)

This ensures: - Clear separation of concerns - Testability of business
logic - Maintainable code structure - Scalability for future extensions

------------------------------------------------------------------------

Game Systems

Character System

-   Base stats (Strength, Vitality, Agility)
-   Experience & leveling system
-   Stat scaling per level
-   Health & regeneration mechanics

Combat System

-   Turn-based automated combat
-   Damage calculation based on stats and equipment
-   Critical hit mechanics
-   Defense & mitigation formulas

Example damage formula:

FinalDamage = (BaseAttack + StrengthModifier) - TargetDefense

Inventory System

-   Equipment slots (Weapon, Armor, etc.)
-   Randomized loot generation
-   Stat bonuses from equipped items

Dungeon System

-   Progressive dungeon stages
-   Enemy scaling per dungeon level
-   Reward distribution system
-   Automated idle progression

------------------------------------------------------------------------

 API Documentation

Swagger UI available at:

http://localhost:8080/swagger-ui.html

API includes endpoints for: - Player management - Combat actions -
Inventory operations - Dungeon progression

------------------------------------------------------------------------

🐳 Running with Docker

The project is fully containerized. You don't need to install Java or PostgreSQL locally.

1. Ensure you have **Docker Desktop** installed and running.
2. Clone the repository:
   ```bash
   git clone [https://github.com/RybaLP/IdleRealms.git](https://github.com/RybaLP/IdleRealms.git)
   cd IdleRealms
   ```
3. Launch the entire stack:
```bash
docker compose up --build
```

4. The API will be available at:
```bash
http://localhost:8080
```
   

------------------------------------------------------------------------

Tech Stack

-   Java 17+
-   Spring Boot 3
-   Spring Security
-   JWT
-   Hibernate / JPA
-   PostgreSQL
-   Docker
-   Swagger

------------------------------------------------------------------------

Testing

-   Unit tests for core game logic
-   Integration tests for REST endpoints

------------------------------------------------------------------------

Project Goals

-   Design a scalable RPG backend system
-   Apply clean architecture principles
-   Separate domain logic from infrastructure
-   Secure REST API with authentication
-   Model complex game mechanics in a maintainable way
