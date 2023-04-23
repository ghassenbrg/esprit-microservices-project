FROM quay.io/keycloak/keycloak:21.1.0

RUN mkdir /opt/keycloak/data/import
COPY ./realm.json /opt/keycloak/data/import
EXPOSE 8080

RUN /opt/keycloak/bin/kc.sh build --http-relative-path=/auth

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--import-realm"]
