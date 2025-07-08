"# spring_boot-blog

## Building and Running with Podman

This project uses Podman for containerization. To build the Docker images, navigate to the project root and run the following commands:

```bash
podman build -t blog-service blog-service
podman build -t eureka-server eureka-server
```

You can then use `podman-compose` (or `docker-compose` if configured to use Podman's socket) to run the services. If you don't have `podman-compose` installed, you can install it via pip:

```bash
pip install podman-compose # Note: The command is 'podman compose', not 'podman-compose'
```

Then, run the services:

```bash
podman-compose up -d
```
" 
