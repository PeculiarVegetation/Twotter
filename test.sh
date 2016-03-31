#!/bin/bash

# Awesome ANSI color sequences
black='\033[1;30m'
red='\033[1;31m'
green='\033[1;32m'
yellow='\033[1;33m'
blue='\033[1;34m'
white='\033[1;37m'
reset='\033[0m'

color="$red"

delay="0.01" # seconds between action

clear
java -ea -jar Twotter.jar -remove-all-users &
SERVER_PID=$!
sleep 1

printf "${color}Creating a new user${reset}\n"
curl -w "\n\n" --silent 'http://localhost:9090/add?username=John%20Smith&password=Password123'
sleep $delay

printf "${color}Creating a new user with the same username as an existing one${reset}\n"
curl -w "\n\n" --silent 'http://localhost:9090/add?username=John%20Smith&password=DifferentPassword'
sleep $delay

printf "${color}Logging in as user${reset}\n"
curl -w "\n\n" --silent --user 'John Smith:Password123' 'http://localhost:9090/home'
sleep $delay

printf "${color}Logging in as user${reset}\n"
curl -w "\n\n" --silent --user 'John Smith:Password123' 'http://localhost:9090/home?'
sleep $delay


printf "${color}Killing server${reset}\n"
kill $SERVER_PID
sleep 0.5

