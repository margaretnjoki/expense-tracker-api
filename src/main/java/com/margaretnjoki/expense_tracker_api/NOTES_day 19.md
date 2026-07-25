## Why SPRING_DATASOURCE_URL uses db instead of localhost or host.docker.internal
The URL jdbc:postgresql://db:5432/expense_db works because Docker Compose automatically creates an internal network where services can communicate using their service names as hostnames.

localhost would only work if the database were running directly on the host machine, not inside a container. Inside the app container, localhost refers to the container itself, not the host.

host.docker.internal is a special DNS name used when a container needs to access services running on the host machine (e.g., if Postgres was installed directly on your Mac/Windows, not in a container).

db works because Docker Compose sets up DNS resolution between services defined in the same compose file. The app container can resolve db to the internal IP address of the database container automatically.
This is one of the key benefits of Docker Compose—service discovery is built-in and effortless.

## Difference between docker-compose down and docker-compose down -v Command
### What it does
- docker-compose down	Stops and removes containers, networks, and any resources created by up. Persistent volumes remain intact, so your database data is preserved.
- docker-compose down -v	Does everything down does, plus removes all named volumes declared in the compose file (like pgdata). This permanently deletes all database data stored in those volumes.
### When to use each:
- Use docker-compose down for normal shutdown—your data stays safe.
- Use docker-compose down -v when you want a complete reset (e.g., clearing corrupted data, starting fresh with a clean database, or removing all traces of the environment).