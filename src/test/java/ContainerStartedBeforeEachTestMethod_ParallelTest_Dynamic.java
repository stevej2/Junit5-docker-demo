import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testcontainers.containers.GenericContainer;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.stream;

class ContainerStartedBeforeEachTestMethod_ParallelTest_Dynamic {

    static Instant startTime;
    static String ContainerID = "";

    void sleep(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @BeforeAll
    public static void startTime() {
        startTime = Instant.now();
    }

    String startContainer() {
        try (GenericContainer container = new GenericContainer("selenium/standalone-chrome-debug:3.141.59")) {
            container.start();
            // ... use the container
            // no need to call stop() afterwards
            return container.getContainerId();
        }
    }

    @Execution(ExecutionMode.CONCURRENT)
    @TestFactory
    Stream<DynamicTest> streamTest() {

        List<Integer> range = IntStream.rangeClosed(1, 100)
                .boxed().collect(Collectors.toList());

        Iterator<Integer> inputGenerator = range.iterator();

        // Display names
        Function<Integer, String> displayNameGenerator = (
                input) -> "Data input: " + input;

        // Test executor
        ThrowingConsumer<Integer> testExecutor = (input) -> {
            ContainerID = startContainer();
            System.out.println("Container ID Test " + input + ": " + ContainerID);
            sleep(10);
            assertTrue(true);
        };

        // Returns a stream of dynamic tests
        return stream(inputGenerator, displayNameGenerator, testExecutor);
    }

    @AfterAll
    static void checkDifferentContainersWereUsed() {
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(startTime, end);
        System.out.println(timeElapsed.toSeconds() + " seconds elapsed");
    }
}
