docker run -d --rm \
    --name local-pg \
    -e POSTGRES_PASSWORD=postgres \
    -e PGDATA=/var/lib/postgresql/data/pgdata \
    -v /Users/frankmoley/.local/docker/data:/var/lib/postgresql/data \
    -p 5432:5432 \
    postgres
