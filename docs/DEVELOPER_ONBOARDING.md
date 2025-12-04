# Developer Onboarding Guide & Architectural Standards

Welcome to the team! This guide outlines the architectural principles, coding standards, and testing philosophy for our project. We follow a strict **Hexagonal Architecture (Ports & Adapters)** approach to ensure our business logic remains pure, testable, and independent of external concerns.

---

## 1. Architectural Overview: "The Circles"

Our system is composed of concentric circles. The "Dependency Rule" is paramount: **source code dependencies can only point inwards**. Nothing in an inner circle can know anything at all about something in an outer circle.

### 🟢 The Core (Inner Circle)
*   **Role:** The heart of the application. Contains **Enterprise Business Rules**.
*   **Components:**
    *   **Domain Objects:** Pure Java objects representing our business entities (e.g., `TrainJourney`, `Station`, `DelayMetric`).
    *   **Use Cases:** Specific application business rules (e.g., `CalculateAverageLatency`, `RegisterTrainArrival`).
    *   **Ports (Interfaces):** Definitions of how the Core communicates with the outside world (e.g., `TrainActivityRepository`, `TimeProvider`).
*   **STRICT RULE:** **Zero dependencies** on frameworks, databases, UI, or external libraries. No `@Entity`, no `@Controller`, no HTTP libraries. Pure Java.

### 🔵 The API (Outer Circle)
*   **Role:** The Entry Point. It drives the application.
*   **Components:**
    *   REST Controllers.
    *   Kafka Consumers / Event Listeners.
    *   DTOs (Data Transfer Objects) specific to the API.
*   **Responsibility:** Receive input, convert it to a Core-compatible format, invoke a Use Case, and format the response.

### 🟤 The Infrastructure (Outer Circle)
*   **Role:** The Implementation Details. Supports the Core.
*   **Components:**
    *   **Adapters:** Implementations of the Ports defined in the Core.
    *   **Database:** JPA Entities, SQL Repositories.
    *   **External APIs:** HTTP Clients.
*   **Responsibility:** Implement the interfaces defined by the Core. For example, a `JpaTrainActivityRepository` implements the Core's `TrainActivityRepository` interface.

---

## 2. The Data Model (Reference)

Our domain revolves around Train Station Data. See [Data Model Documentation](./data-model.md) for the schema definitions.

*   **Fact:** `fact_train_activity` (Transactional train stop events).
*   **Dimensions:** `train_service_item` (Schedule info), `dim_time` (Time buckets).

**Note:** The database schema (tables) belongs to the **Infrastructure**. The Domain Objects in the **Core** may look similar but are decoupled from the DB structure.

---

## 3. Testing Philosophy

We hold a distinct philosophy on testing to ensure maintainability and speed.

### ✅ State-Based vs. Interaction-Based
*   **Prioritize State-Based Verification:** Assert that the *result* (return value or state change) is correct.
*   **Avoid Interaction-Based Verification:** Do not over-use `verify(mock).methodCalled()`. It couples tests to implementation details.

### 🎭 The "Fakes" Pattern
*   **No Mocks for Owned Interfaces:** When testing the Core, do not use Mockito mocks for your Ports (Repositories).
*   **Write Fakes:** Create a lightweight, in-memory implementation of the Port (e.g., `InMemoryTrainActivityRepository`).
    *   *Why?* It creates fast, realistic tests that verify behavior, not method calls.
    *   *Rule:* Every Port must have a corresponding Fake implementation in the test scope.

### ⚖️ Parity Verification (Contract Tests)
*   We must ensure the `Fake` behaves exactly like the `Real` implementation (e.g., Database Adapter).
*   **The Contract Test:** Create a generic abstract test suite (e.g., `TrainActivityRepositoryContract`).
    *   Run it against the **Fake** (Unit Test).
    *   Run it against the **Real Adapter** (Integration Test with TestContainers/H2).
*   **Goal:** If the test passes for the Fake, we trust the Fake.

---

## 4. How to Add a New Feature (Step-by-Step)

Follow this flow when implementing a new use case (e.g., "Calculate Peak Delay Days").

### Step 1: Define the Core (Inner Circle)
1.  **Domain Model:** Define `DelayStats` or update `TrainActivity` in the Core package. (Pure Java, no annotations).
2.  **Port (Interface):** Define the interface needed to get data.
    ```java
    // Core
    public interface TrainActivityRepository {
        List<TrainActivity> findByTimeRange(LocalDateTime start, LocalDateTime end);
    }
    ```
3.  **Use Case:** Implement the logic.
    ```java
    // Core
    public class CalculatePeakDays {
        private final TrainActivityRepository repository;
        // ... constructor ...
        public DayOfWeek execute() { ... }
    }
    ```

### Step 2: The Fake & Core Tests
1.  **Create the Fake:**
    ```java
    // Test Scope
    public class FakeTrainActivityRepository implements TrainActivityRepository {
        private final List<TrainActivity> store = new ArrayList<>();
        // ... implement methods using the list ...
    }
    ```
2.  **Write Core Unit Tests:** Use the Fake to test the Use Case.
    ```java
    @Test
    void shouldIdentifyMondayAsPeakDay() {
        var repo = new FakeTrainActivityRepository();
        repo.save(new TrainActivity(MONDAY, ...));
        var useCase = new CalculatePeakDays(repo);
        assertEquals(MONDAY, useCase.execute());
    }
    ```

### Step 3: Implement Infrastructure (Outer Circle)
1.  **Database Entity:** Create the JPA Entity (if using SQL) mapped to the DB table.
    ```java
    @Entity @Table(name = "fact_train_activity")
    public class FactTrainActivityEntity { ... }
    ```
2.  **Real Adapter:** Implement the Core Port using the infrastructure components.
    ```java
    @Repository
    public class JpaTrainActivityAdapter implements TrainActivityRepository {
        private final JpaRepository jpaRepo;
        // ... map Entity to Domain ...
    }
    ```

### Step 4: Parity Verification
1.  **Contract Test Suite:**
    ```java
    public abstract class TrainActivityRepositoryContract {
        protected abstract TrainActivityRepository createSubject();

        @Test
        void shouldFindActivitiesByTime() {
            var repo = createSubject();
            // ... assert behavior ...
        }
    }
    ```
2.  **Verify Fake:** `class FakeRepoTest extends TrainActivityRepositoryContract { ... }`
3.  **Verify Real:** `class JpaRepoTest extends TrainActivityRepositoryContract { ... }` (Load Spring Context / DB).

### Step 5: The API (Outer Circle)
1.  Create the Controller or Kafka Listener.
2.  Inject the Use Case.
3.  Map inputs to Core models and Core outputs to API responses.

---

**Summary:**
Keep the Core clean. Test behavior, not mocks. Verify your Fakes match Reality.

