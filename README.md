# camunda engine unit test template

This git repository contains a simple example of how to write a unit test for
the Camunda DMN engine. You can use it for reporting bugs or asking questions
in the forums.

The project contains the following files:

```
src/
└── test
    ├── java
    │   └── org
    │       └── camunda
    │           └── bpm
    │               └── dmn
    │                   └── unittest
    │                       └── SimpleTestCase.java     (1)
    └── resources
        └── Example.dmn                                 (2)
```

Explanation:

* (1) A Java class containing a JUnit Test. It uses the `DmnEngineRule` for
  bootstrapping the DMN engine, as well as [AssertJ] for testing the decision
  result.
* (3) An example DMN decision table.

## Running the test with maven

In order to run the testsuite with maven you can say:

```
mvn clean test
```

## Importing the project into eclipse.

If you use eclipse you can simply import the project by selecting:

- `File / Import |-> Existing Maven Projects`

[AssertJ]: https://joel-costigliola.github.io/assertj/
