# Day 18 Notes

## Image vs Container

A Docker image is like a Java class. It is a blueprint containing everything needed to run an application. A Docker container is like a Java object. It is a running instance created from an image. One image can create many containers, just as one class can create many objects.

## Why localhost behaves differently

Inside a Docker container, localhost refers to the container itself instead of my computer. Therefore, if PostgreSQL is running on my computer, the container cannot find it using localhost. Docker Desktop provides host.docker.internal, which allows a container to communicate with services running on the host machine.

## Multi-stage Dockerfile

The first stage uses Maven and the JDK to build the application. The second stage uses only a lightweight JRE to run the application. This produces a much smaller Docker image because build tools and source code are not included in the final image.