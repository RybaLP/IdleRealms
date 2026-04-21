# Social Service

A microservice responsible for player interactions, guild management, and a real-time messaging and notification system — built as part of a larger game application ecosystem.

---

## Architecture

The project follows **Hexagonal Architecture (Ports & Adapters)** principles, ensuring complete separation of business logic from infrastructure concerns. This makes the core domain fully testable and swappable at the infrastructure layer without touching business rules.

- **Domain** — Pure business logic: Guilds, Messages, Players. Zero framework dependencies.
- **Application (Ports)** — Input interfaces (Use Cases) and output ports defining contracts for external communication.
- **Infrastructure (Adapters)** — Technical implementations: Spring Data JPA repositories, WebSocket (STOMP) handlers, REST Controllers.

---

## Key Features

### Guild Management

Full lifecycle management of in-game guilds, including creation, member administration, and a smart kick flow.

**Reactive Kick Flow** — When a player is removed from a guild, the system automatically orchestrates a chain of side effects:
- Clears the `guild_id` column in the player's profile
- Cancels all active pending invitations associated with that guild
- Pushes a real-time WebSocket signal to the client, forcing an immediate UI refresh without requiring a manual page reload

### Messaging & Notifications

- Private player-to-player messaging
- Guild invitations with full state management (pending, accepted, cancelled)
- Real-time notifications delivered over WebSocket (STOMP) for:
    - New incoming messages
    - A player joining a guild
    - Being kicked from a guild (exile signal)

---

## Event-Driven Internal Communication

Modules communicate via Spring's `ApplicationEventPublisher`, keeping `GuildService` and `MessageService` fully decoupled — no circular dependencies, no direct service-to-service calls. Events flow asynchronously through the application context, making the system easier to extend and reason about.

---

## WebSocket API Signals

The service pushes control signals to the frontend over the `/topic/notifications.{userId}` channel:

| Signal | Description |
|---|---|
| `kickedFromGuild` | Forces immediate exit from the guild view and invalidates the client-side cache |
| `joinedGuild` | Triggers a member list refresh for all currently connected guild members |
| `message` | Notifies the recipient of a new incoming message (scroll) |