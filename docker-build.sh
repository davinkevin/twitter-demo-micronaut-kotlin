#!/usr/bin/env bash

docker build . -t twitter-demo-kotlin
echo
echo
echo "To run the docker container execute:"
echo "    $ docker run --network host twitter-demo-kotlin"
