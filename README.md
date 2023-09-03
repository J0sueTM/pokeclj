# PokeCLJ

This project is a challenge from [cljam](https://cljam.notion.site/Welcome-to-cljam-0b0459ae21e04128ae9220e03092136f).

Written and built with Clojure, Postgres and Docker/Compose.

## Setup

### Web App

TODO: write about.

### API Service

The API Service can use both an `.env` or a `profiles.clj` file. If you're in the development mode (REPL, lein run), I setted it to use the ``profiles.clj`` file, which looks something like this:

```clojure
{:dev {:env {:postgres-user ""
             :postgres-password ""
             :postgres-db ""
             :postgres-host ""
             :postgres-port 0000}}}
```

When built with `lein uberjar`, which is what the docker compose does, these should be setted through the `.env` file. The data we already setted there is ok, so we're done.

### Docker Compose

PokeCLJ uses docker-compose, so all you have to do is set the `.env` files. There is an example file `.env.example` which will guide you on the data to set, something like this:

```sh
POSTGRES_USER=user
POSTGRES_PASSWORD=password
POSTGRES_DB=my_db
POSTGRES_DATA=./mount/at/this/folder
POSTGRES_PORT=5432
FRONTEND_NETWORK=frontend
BACKEND_NETWORK=backend
```

## Running

Build and run the containers with

```sh
docker compose up
```

Now, you need to migrate the database. The migrations are at the folder `db/migrations`, so its just a matter of running:

```sh
docker compose --profile tools run --rm migrate up
```

## Software Architecture and Engineering decisions

TODO: write about.

# License

GPL 3.0, Josué Teodoro Moreira <teodoro.josue@pm.me> 2023
