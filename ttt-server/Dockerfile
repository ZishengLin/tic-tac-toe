FROM ubuntu:16.04

RUN apt-get update && apt-get install -y \
  libmicrohttpd-dev \
  libjansson-dev \
  libcurl4-gnutls-dev \
  libgnutls28-dev \
  libgcrypt20-dev 

# Copy the server C binary and the JSON library into the container
COPY ./ttt-server /ttt-server

COPY ./libs/libjansson.so.4 /usr/lib/libjansson.so.4
COPY ./libs/libulfius.so.2.3 /usr/lib/libulfius.so.2.3
COPY ./libs/libyder.so.1.2.0 /usr/lib/libyder.so.1.2
COPY ./libs/liborcania.so.1.2.0 /usr/lib/liborcania.so.1.2
COPY ./libs/libmicrohttpd.so.12 /usr/lib/libmicrohttpd.so.12

# Expose the port that the server will listen on
EXPOSE 8080

# Set the LD_LIBRARY_PATH so that the server binary will find the JSON library
ENV LD_LIBRARY_PATH ${LD_LIBRARY_PATH}:/usr/lib



CMD ["/ttt-server"]
