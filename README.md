# PokeCLJ

This project is a challenge from [cljam](https://cljam.notion.site/Welcome-to-cljam-0b0459ae21e04128ae9220e03092136f).

Written and built with Clojure, Postgres and Docker/Compose.

## Building

PokeCLJ uses docker-compose, so all you have to do is set the .env files. There is an example file `.env.example` which will guide you on the data to set, something like this:

```sh
POSTGRES_USER=user
POSTGRES_PASSWORD=password
POSTGRES_DB=my_db
POSTGRES_DATA=./mount/at/this/folder
POSTGRES_PORT=5432
```

Build and run the containers with

```sh
docker compose up
```

## Running

Now, you need to migrate the database. The migrations are at the folder `db/migrations`, so its just a matter of running:

```sh
docker compose --profile tools run --rm migrate up
```

## Software Architecture and Engineering decisions

TODO: write about.

# License

GPL 3.0, Josué Teodoro Moreira <teodoro.josue@pm.me> 2023
