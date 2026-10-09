# Martian Robots

## Requirements

* Java 17+
* No Maven installation required — the project includes the Maven Wrapper.

## Run the Application

From the project root:

```bash
./mvnw clean package
```

Then run using the included sample input:

```bash
java -jar target/Martian-1.0-SNAPSHOT.jar < sample-input.txt
```

Expected output:

```text
1 1 E
3 3 N LOST
2 3 S
```

## Run Tests

Run all unit tests with:

```bash
./mvnw test
```

## Key Decisions

* **Separation of responsibilities:** Kept grid state, robot state, and instruction execution in `Grid`, `Robot`, and `RobotSimulator` respectively.
* **Simple instruction handling:** Used a `switch` statement for `L`, `R`, and `F` instead of introducing an instruction registry or class hierarchy.
* **Set-based scent tracking:** Used a `Set<Position>` to track scented locations, with value-based equality provided by a Java record.
* **Sequential processing:** Processed robots in input order so scents left by lost robots affect subsequent robots.
* **Testable simulation core:** Separated console input/output from simulation logic to enable focused unit testing.

## Trade-offs

* **Modularity vs. class count:** Separating responsibilities makes the code easier to maintain and test, at the cost of introducing a few additional classes.
* **Memory vs. lookup efficiency:** Storing scents in a set consumes memory but provides efficient lookups and prevents duplicate scent entries.
* **Sequential execution vs. concurrency:** Processing robots sequentially is straightforward and preserves scent dependencies; parallel execution would add complexity without benefiting this problem.
* **Validation vs. implementation size:** Explicit input validation improves error messages and robustness, but adds code beyond the core simulation logic.

## Assumptions

* Robot starting positions are assumed to be within the grid boundaries, as specified by the input.
* Input follows the format described in the problem statement.
* Instructions contain only supported commands (`L`, `R`, `F`).
* Robots are processed sequentially, and scents left by lost robots persist for all subsequent robots.
* A robot that encounters a scented position ignores only the dangerous `F` command and continues processing subsequent instructions.
* Once a robot is lost, its remaining instructions are not processed.

## Author

Arpit Sharma
