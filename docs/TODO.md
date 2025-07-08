## Pending Tasks, Improvements, and Known Issues

### Known Issues:
- **Blog Service Container Startup Failure**: The `blog-service` container consistently fails to start with a "no main manifest attribute, in app.jar" error, even after:
    - Ensuring `spring-boot-maven-plugin` is configured for `repackage` with `mainClass`.
    - Verifying the generated JAR size on the host.
    - Clearing Podman cache and rebuilding images with `--no-cache`.
    - Simplifying `blog-service/Dockerfile` to directly copy the pre-built JAR.

### Next Steps for Blog Service Issue:
- Investigate why the `blog-service` JAR is not being recognized as executable within the container. This might involve:
    - Deeper inspection of the `MANIFEST.MF` inside the JAR *within the container environment* (if possible).
    - Exploring alternative `ENTRYPOINT` or `CMD` configurations in the `Dockerfile`.
    - Considering subtle differences in Java environments or file permissions between the host and the container.