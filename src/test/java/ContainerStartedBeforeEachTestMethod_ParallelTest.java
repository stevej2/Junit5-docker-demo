
//import com.github.junit5docker.Docker;
//import com.github.junit5docker.Environment;
//import com.github.junit5docker.Port;
//import com.github.junit5docker.WaitFor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testcontainers.containers.GenericContainer;

import java.time.Duration;
import java.time.Instant;


/*
NOT YET WORKING
*/

//@DisplayName("Starts a new docker container before each test method is run")
//
//@Docker(image = "mysql", ports = @Port(exposed = 8801, inner = 3306),
//        environments = {
//                @Environment(key = "MYSQL_ROOT_PASSWORD", value = "root"),
//                @Environment(key = "MYSQL_DATABASE", value = "testdb"),
//                @Environment(key = "MYSQL_USER", value = "testuser"),
//                @Environment(key = "MYSQL_PASSWORD", value = "s3cr3t"),
//        },
//        waitFor = @WaitFor("mysqld: ready for connections"))

class ContainerStartedBeforeEachTestMethod_ParallelTest {

    static Instant startTime;
    static String ContainerID_1 = "";
    static String ContainerID_2 = "";


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
    @Test
    @DisplayName("The bbc.co.uk web site should have the correct title")
    void BBCWebSiteShouldHaveCorrectTitle() throws InterruptedException {
        utils.runCmd("docker ps");
        ContainerID_1 = startContainer();
        System.out.println("Container ID Test 1: " + ContainerID_1);
        Thread.sleep(10000);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @Test
    @DisplayName("The google.co.uk web site should have the correct title")
    void GoogleWebSiteShouldHaveCorrectTitle() throws InterruptedException {
        utils.runCmd("docker ps");
        ContainerID_2 = startContainer();
        System.out.println("Container ID Test 2: " + ContainerID_2);
        Thread.sleep(10000);
    }

    @AfterAll
    static void checkDifferentContainersWereUsed() {
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(startTime, end);
        System.out.println(timeElapsed.toSeconds() + " seconds elapsed");
    }
}
