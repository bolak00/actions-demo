FROM icr.io/appcafe/open-liberty:full-java17-openj9-ubi
COPY src/main/liberty/config/server.xml /config/server.xml
COPY target/actions-demo.war /config/apps/

EXPOSE 9080 9443

ENTRYPOINT ["opt/ibm/wlp/bin/server", "run", "defaultServer"]

HEALTHCHECK --interval=10s --timeout=3s --retries=3 \
  CMD curl -f http://localhost:9080/rest/health || exit 1


