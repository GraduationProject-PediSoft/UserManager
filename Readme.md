# UserManager Pedisoft

This repository contains the UserManager microservice for Pedisoft systems written in SpringBoot 3.1.5. This project uses Keycloak services to authenticate users and manage them (login, register, logouts, etc)

# Running

The project was designed to be executed in a containerized environment. In this repo [Docker](https://www.docker.com/) was used for it, but Podman or Kubernetes could be used with little configuration. The compose file builds and executes the SpringBoot web server without any additional configuration, simply run:

```sh
docker compose up -d
```
The compose also starts a Keycloak container with a dedicated Postgres database

# Configuration
## Environment
the *.env_template* file contains the environment variables that should be defined in order to run, should be edited according to the needs. The OIDC credentials should be obtained from the Keycloak web server

## Authentication Provider
The Keycloak instance should be configured using the Web Interface. A new realm for example *pedisoft* have to be created with am oidc client with the following **service account roles**:

- manage-users
- query-realms
- query-users
- manage-realm
