# Use the official PostgreSQL image from the Docker Hub
FROM postgres:13 AS postgres

# Set environment variables for PostgreSQL
ENV POSTGRES_DB=${POSTGRES_DB}
ENV POSTGRES_USER=${POSTGRES_USER}
ENV POSTGRES_PASSWORD=${POSTGRES_PASSWORD}

# Expose the PostgreSQL port
EXPOSE 5432

# Use the official Open Liberty image from IBM Container Registry
FROM icr.io/appcafe/open-liberty:full-java17-openj9-ubi-arm64 AS app

# Copy the server configuration
COPY ./src/main/liberty/config/server.xml /config/server.xml

# Copy the application WAR file
COPY ./target/actions-demo.war /config/apps/

# Set the entrypoint to run the server
ENTRYPOINT [ "opt/ol/wlp/bin/server", "run", "defaultServer" ]

# Expose the application ports
EXPOSE 9080 9443

# Healthcheck to ensure the application is running
HEALTHCHECK --interval=10s --timeout=3s --retries=3 CMD curl -f http://localhost:9080/rest/health || exit 1