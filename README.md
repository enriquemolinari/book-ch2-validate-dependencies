# Validating Boundaries

This repository contains the examples of the *Validating Boundaries* section of the book **Coding an Architecture
Style**. It shows different mechanisms to make sure nobody creates invalid dependencies between the packages/modules of
an application.

Every project implements the same small e-commerce sample (a `Cashier` that charges a credit card and sends a
notification), organized in these packages:

- `domain.api`: interfaces and exceptions (`PaymentGateway`, `Notify`, `DomainException`).
- `domain`: business logic (`Cashier`, `CreditCard`).
- `payment`: `PaymentGateway` implementation.
- `notifications`: `Notify` implementation using the [BigQueue](https://github.com/bulldog2011/bigqueue) library.
- `main`: creates and injects all dependencies and launches the application.

The design decisions validated in each project are:

1. `domain` must depend only on `domain.api`.
2. `domain` must not have any incoming dependencies.
3. `domain.api` must not depend on any other package.
4. `notifications` and `payment` must depend only on `domain.api`.
5. Only `notifications` can depend on the BigQueue jar file.

## Projects

### Requirements

- [Java](https://adoptium.net/) 17 or later.
- [Maven](https://maven.apache.org/install.html) installed (used by all projects).
- [Ant](https://ant.apache.org/manual/install.html) installed (only for the `ch2ant` project).

### Getting the code

- `git clone https://github.com/enriquemolinari/book-ch2-validate-dependencies.git`
- `cd book-ch2-validate-dependencies`

### Package-private: [ch2-spring-packageprivate](ch2-spring-packageprivate)

Implementation classes in `payment` and `notifications` are package-private. Spring (through reflection) creates and
injects them from `main` using `injection-config.xml`.

- `cd ch2-spring-packageprivate`
- To compile: `mvn compile`

### Ant, just use the compiler: [ch2ant](ch2ant)

Each package is compiled separately, with only its valid dependencies in the classpath (see `build.xml`). An invalid
dependency becomes a compilation error. Requires Ant installed.

- `cd ch2ant`
- To download the BigQueue jar into your local Maven repository: `mvn dependency:resolve`
- To validate: `ant -Dmaven-local-repo=$HOME/.m2/repository`

### Checkstyle: [ch2checkstyle](ch2checkstyle)

Uses Checkstyle's `ImportControl` module. Rules are defined in `import-control.xml` (note the `app` root package it
requires).

- `cd ch2checkstyle`
- To validate: `mvn checkstyle:check`

### ArchUnit: [ch2-archunit](ch2-archunit)

The rules are written as JUnit tests in `TestValidateArchitecture.java`.

- `cd ch2-archunit`
- To validate: `mvn test`

### Maven Modules: [ch2maven-parent](ch2maven-parent)

A Maven multi-module project, one module per package. Each `pom.xml` declares only its allowed dependencies.

- `cd ch2maven-parent`
- To validate: `mvn install`

### The Java Module System: [ch2jm-domain](ch2jm-domain), [ch2jm-payment](ch2jm-payment), [ch2jm-notifications](ch2jm-notifications), [ch2jm-main](ch2jm-main)

Each module declares in its `module-info.java` the packages it exports and the modules it requires. Rules are enforced
by the compiler and at runtime, including access via reflection.

- To download the BigQueue jar into your local Maven repository: `cd ch2jm-notifications && mvn dependency:resolve && cd ..`
- To compile all modules (run it from the repository root). If all dependencies are valid, `javac` prints nothing and
  the compiled modules are generated in the `out` folder. Any invalid dependency is reported as a compilation error:

```bash
javac -d out \
  --module-path ~/.m2/repository/com/leansoft/bigqueue/0.7.0/bigqueue-0.7.0.jar \
  --module-source-path domain=ch2jm-domain/src/main/java \
  --module-source-path payment=ch2jm-payment/src/main/java \
  --module-source-path notifications=ch2jm-notifications/src/main/java \
  --module-source-path main=ch2jm-main/src \
  --module domain,payment,notifications,main
```