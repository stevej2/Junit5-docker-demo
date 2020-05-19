# Using Docker with Junit for sequential and parallel testing

This example demonstrates how we can use Docker in combination with Junit, including for parallel testing.
This example uses a selenium Docker image for demonstration purposes.


# Docker usage methods
**4 classes exist which each execute test methods in the following ways**
- **NewContainerStartedBeforeEachTestMethodTest**
This Class starts a new container, before each test method is executed.

- **ContainerStartedOnceForAllTest**
This class start a container once, before being used for all tests.

- **ContainerStartedBeforeEachTestMethod_ParallelTest**
This Class starts a new container, before each test method is executed, however this class executes all tests in parallel.
e.g. 2 tests each with a 10 second sleep, complete in 13 seconds.

- **ContainerStartedBeforeEachTestMethod_ParallelTest_Dynamic**
This Class starts a new container, before each test method is executed, however this class creates the tests dynamically before executing in parallel.
e.g. Creating 100 Docker containers, each executing a 10 seconds sleep, completed in 41 seconds.

All containers are removed when no longer required, after each test, class or suite.