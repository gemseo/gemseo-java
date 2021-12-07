FROM registry.gitlab.com/gemseo/dev/gemseo/multi-python

RUN  dnf -y install python3.9-devel \
  && dnf -y install gcc \
  && dnf -y install java-11-openjdk-devel \
  && dnf -y install maven \
  && dnf clean all

ENV JAVA_HOME=/usr/lib/jvm/java
