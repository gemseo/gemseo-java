ARG FEDORA_VERSION=34

FROM fedora:$FEDORA_VERSION

ARG TOX_VERSION=3.24.1

# Fedora tox is outdated, install with pip.
RUN  dnf -y update \
  && dnf -y install python \
  && dnf -y install pip \
  && dnf -y install python36 \
  && dnf -y install python37 \
  && dnf -y install python38 \
  && dnf -y install python39 \
  && dnf -y install python3.9-devel \
  && dnf -y install git \
  && dnf -y install gcc \
  && dnf -y install java-11-openjdk-devel \
  && dnf -y install maven \
  && dnf clean all \
  && python -m pip install tox==$TOX_VERSION

ENV JAVA_HOME=/usr/lib/jvm/java
