# Blog Service Startup Troubleshooting Log

This document records the troubleshooting process for the `blog-service` container failing to start.

## 1. Problem Description

The `blog-service` container would consistently fail to start or enter a crash loop. The logs showed the error: `no main manifest attribute, in app.jar`.

This error indicates that the Java runtime could not find the `Main-Class` entry in the JAR file's `META-INF/MANIFEST.MF`, meaning it didn't know which class to execute to start the application.

## 2. Investigation Steps

1.  **Verify Local JAR**: We first built the `blog-service` locally using `mvnw clean package`. We then inspected the generated `target/blog-0.0.1-SNAPSHOT.jar` file's `MANIFEST.MF`. The manifest was **correct**, containing the proper `Main-Class: org.springframework.boot.loader.launch.JarLauncher` and `Start-Class: com.example.blog.BlogApplication` attributes. This confirmed the local Maven build was working as expected.

2.  **Initial Build Attempts**: We tried to build and run the containers using `podman-compose`. The container still failed with the same error, even after clearing the cache (`--no-cache`).

3.  **Inspect Container Internals**: We attempted to inspect the `app.jar` file *inside* the running container. This proved difficult because the container was in a crash loop. We eventually succeeded by running a new container from the same image but overriding the entrypoint to `ls` instead of `java`. This showed that `app.jar` existed and had a reasonable file size (~74MB), suggesting the `COPY` operation in the `Dockerfile` was working.

4.  **Contradictory Evidence**: At this point, we had a major contradiction: the local JAR was correct, but the JAR inside the container was not executable. This led to the hypothesis that the file being copied into the container during the `podman build` process was not the correct, repackaged, executable JAR, but likely the original, non-executable JAR created by Maven before the `repackage` goal.

## 3. Root Cause and Solution

**Root Cause**: The issue was traced to an unreliability in the Docker/Podman build context. When building the image, the build context (the `./blog-service` directory) likely contained a stale or incorrect version of the JAR file in its `target` directory, which was then copied into the image.

**Solution**: To eliminate this environmental uncertainty, we implemented a **multi-stage Docker build**. 

- **Stage 1 (Builder)**: A temporary build container is created using a full Maven image (`maven:3.8.5-openjdk-17`). The *entire project source code* is copied into this container, and `mvn clean package` is run inside it. This guarantees that the JAR is built in a clean, consistent environment.

- **Stage 2 (Final Image)**: A new, lightweight production image is created (`openjdk:17-jdk-slim`). The executable JAR—and only the JAR—is copied from the `builder` stage into the final image.

**Why This Worked**: This approach completely decouples the image build process from the state of the local filesystem. It ensures that the JAR file being packaged into the final container is always the one that was just built from source, resolving the inconsistency and fixing the "no main manifest attribute" error.

Additionally, a minor misconfiguration in `nginx.conf` was found and fixed, where `proxy_pass` was incorrectly pointing to `host.docker.internal` instead of the correct service name, `blog-service`.
