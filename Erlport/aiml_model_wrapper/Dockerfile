# Use a base image with the desired OS and tools
FROM ubuntu:20.04

# Install required dependencies
RUN apt-get update && \
    apt-get install -y build-essential wget unzip openjdk-11-jdk

# Set environment variables for Erlang/OTP version and installation path
ENV ERLANG_VERSION 24.0
ENV ERLANG_INSTALL_PATH /opt/erlang

# Download and extract the Erlang/OTP source code
RUN wget https://github.com/erlang/otp/archive/OTP-${ERLANG_VERSION}.tar.gz && \
    tar -xzvf OTP-${ERLANG_VERSION}.tar.gz && \
    rm OTP-${ERLANG_VERSION}.tar.gz

# Build and install Erlang/OTP with Jinterface support
RUN cd otp-OTP-${ERLANG_VERSION} && \
    ./otp_build autoconf && \
    ./configure --prefix=${ERLANG_INSTALL_PATH} --enable-threads --enable-smp-support \
                --enable-kernel-poll --with-javac && \
    make && \
    make install

# Set PATH environment variable to include Erlang binaries
ENV PATH=${ERLANG_INSTALL_PATH}/bin:${PATH}

# Clean up
RUN rm -rf otp-OTP-${ERLANG_VERSION}

# Expose any necessary ports for Jinterface communication
# EXPOSE <port_number>

# Set a working directory for the container
WORKDIR /app

# Copy your Erlang/Jinterface application code to the container
# COPY . /app

# You can now build and run the container
# CMD ["erl"]
