# Use the official Open Liberty image from IBM Container Registry
FROM icr.io/appcafe/open-liberty:full-java17-openj9-ubi-arm64

# Copy the server configuration
COPY ./src/main/liberty/config/server.xml /config/server.xml

# Create directory for shared resources and copy PostgreSQL driver
COPY ./target/liberty/wlp/usr/shared/resources/postgresql*.jar /opt/ol/wlp/usr/shared/resources/

# Copy the application WAR file
COPY ./target/actions-demo.war /config/apps/

# The environment variables need to be passed through to Liberty
ENV POSTGRES_DB=${POSTGRES_DB} \
    POSTGRES_USER=${POSTGRES_USER} \
    POSTGRES_PASSWORD=${POSTGRES_PASSWORD}


# Set the entrypoint to run the server
ENTRYPOINT [ "opt/ol/wlp/bin/server", "run", "defaultServer" ]

# Expose the application ports
EXPOSE 9080 9443

# Healthcheck to ensure the application is running
HEALTHCHECK --interval=10s --timeout=3s --retries=3 CMD curl -f http://localhost:9080/rest/health || exit 1