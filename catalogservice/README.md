# Category Service

This project uses Quarkus, the Supersonic Subatomic Java Framework.

## Requirements

### MongoDB

1. MongoDB connection URL specify in `src/resources/application.properties`
1. MongoDB database and collection specify in `src/resources/application.properties`

## Developing

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

## Building

The application can be packaged using:

```shell script
./mvnw package
```

If you want to build a jar with all dependencies, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

You can create a native executable using:

```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

## CI/CD

1. Производится сборка бинарного файла. Как следствие, для него не требуется зависимостей `java-jdk` или `java-jre`;
2. Собирается docker-image и пушится в собственный `gitlab-registry`;
3. С использованием `ansible` происзводится развёртывание сервиса.
