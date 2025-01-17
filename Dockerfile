FROM ubuntu:latest
LABEL authors="chani"

ENTRYPOINT ["top", "-b"]