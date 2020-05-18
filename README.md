# Writing Selenium Tests Which Use a Dockerized Web Browser

This example demonstrates how we can write Selenium tests using Dockerized
web browser containers.


# Docker usage methods
## 3 classes exist which each execute test methods in the following ways;
- NewContainerStartedBeforeEachTestMethodTest
This Class starts a new container, before each test method is executed.

- ContainerStartedOnceForAllTest
This class start a container once, before being used for all tests.

- ContainerStartedBeforeEachTestMethod_ParallelTest
This Class starts a new container, before each test method is executed, however this class executes all tests in parallel.
e.g. 2 tests each with a 10 second sleep, complete in 13 seconds.