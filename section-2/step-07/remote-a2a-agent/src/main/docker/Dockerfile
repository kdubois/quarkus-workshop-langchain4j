FROM icr.io/appcafe/websphere-liberty:kernel-java21-openj9-ubi-minimal AS base

## Endpoints
EXPOSE 8888

## TLS
#ENV TLS true

## Trust certificates from well known CA's
# ENV SEC_TLS_TRUSTDEFAULTCERTS=true

## Trust certificates from within the cluster, such as Red Hat SSO.
# ENV SEC_IMPORT_K8S_CERTS=true

## Default setting for the verbose option
# ARG VERBOSE=true

# This script will add the requested XML snippets, grow image to be fit-for-purpose and apply interim fixes
# We disable the SCC because this modifies the class files and breaks code coverage during the build process
# ENV OPENJ9_SCC=false

WORKDIR /opt/ibm/wlp/usr/servers/defaultServer

# Pick up latest security fixes
USER root
RUN microdnf -y update && \
    microdnf clean all && \
    rm -rf /var/cache/microdnf /tmp/* /var/tmp/*
USER 1001

## Add interim fixes (optional)
#COPY --chown=1001:0  interim-fixes /opt/ol/fixes/

# Add config
COPY --chown=1001:0  src/main/liberty/config/* /config/

# Modify feature repository (optional)
# COPY --chown=1001:0 src/main/docker/featureUtility.properties /opt/ibm/wlp/etc/

RUN features.sh

## Set permssions on configDropins
USER root
RUN chmod -R g+rw /config/configDropins/

USER 1001
RUN configure.sh

FROM base as image

# Copy application
COPY --chown=1001:0  target/langchain4j-workshop.war /config/apps/langchain4j-workshop.war
