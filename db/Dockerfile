FROM postgres:11-alpine

ARG PG_USER=user
ARG PG_PASSWORD=pass
ARG PG_DB=pokeclj_db

ARG PG_DATA=./pgdata

ENV POSTGRES_USER=$PG_USER
ENV POSTGRES_PASSWORD=$PG_PASSWORD
ENV POSTGRES_DB=$PG_DB

HEALTHCHECK \
    --interval=30s \
    --timeout=10s  \
    --retries=3    \
    CMD psql -U "$PG_USER" -d "$PG_DB" -c "SELECT 1"

VOLUME $PG_DATA:/var/lib/postgresql/data
