# Choose the system
FROM ubuntu:24.04

ARG BITCOIN_VERSION=28.1

# Install packages
RUN apt update && apt-get install -y wget build-essential libtool autotools-dev automake pkg-config bsdmainutils python3 libevent-dev libboost-dev && apt clean


# Set the working directory
RUN mkdir /bitcoin-core
WORKDIR /bitcoin-core

# Download Bitcoin Core
RUN wget https://bitcoincore.org/bin/bitcoin-core-${BITCOIN_VERSION}/bitcoin-${BITCOIN_VERSION}.tar.gz  && \
    # wget https://bitcoincore.org/bin/bitcoin-core-${BITCOIN_VERSION}/SHA256SUMS.asc && \
    # gpg --keyserver keys.openpgp.org --search-keys "Michael Ford fanquake@gmail.com" && \
    # gpg --verify SHA256SUMS.asc && \
    # grep " bitcoin-${BITCOIN_VERSION}.tar.gz\$" SHA256SUMS | sha256sum -c - && \
    tar -xvf bitcoin-${BITCOIN_VERSION}.tar.gz && \
    # rm SHA256SUMS.asc SHA256SUMS && \
    rm -rf bitcoin-${BITCOIN_VERSION}.tar.gz

# Build Bitcoin Core
WORKDIR /bitcoin-core/bitcoin-${BITCOIN_VERSION}/
RUN ./autogen.sh 
RUN ./configure --disable-wallet
RUN make -j$(nproc) # optional
RUN make install # optional

RUN rm -rf /bitcoin-core/


# Create a data directory
RUN mkdir /bitcoin-data

# Run Bitcoin Core
ENTRYPOINT ["bitcoind"]
CMD ["-printtoconsole", "-datadir=/bitcoin-data"]