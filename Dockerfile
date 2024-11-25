# Start PostgreSQL database
FROM postgres:13 as postgres

# Set environment variables for PostgreSQL
ENV POSTGRES_DB=${POSTGRES_DB}
ENV POSTGRES_USER=${POSTGRES_USER}
ENV POSTGRES_PASSWORD=${POSTGRES_PASSWORD}

EXPOSE 5432

# Copy the rest of your application
FROM icr.io/appcafe/open-liberty:full-java17-openj9-ubi-arm64
COPY src/main/liberty/config/server.xml /config/server.xml
COPY target/actions-demo.war /config/apps/

EXPOSE 9080 9443

ENTRYPOINT ["opt/ol/wlp/bin/server", "run", "defaultServer"]

HEALTHCHECK --interval=10s --timeout=3s --retries=3 \
  CMD curl -f http://localhost:9080/rest/health || exit 1