FROM icr.io/appcafe/open-liberty:full-java17-openj9-ubi
COPY src/main/liberty/config/server.xml /config/server.xml
COPY target/actions-demo.war /config/apps/

EXPOSE 9080 9443