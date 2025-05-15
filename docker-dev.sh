#!/bin/bash

ENV_FILE=".env"
COMPOSE_FILE="docker-compose.all.yaml"

function build() {
  docker compose --env-file "$ENV_FILE" -f "$COMPOSE_FILE" build
}

function up() {
  docker compose --env-file "$ENV_FILE" -f "$COMPOSE_FILE" up --build
}

function down() {
  docker compose --env-file "$ENV_FILE" -f "$COMPOSE_FILE" down
}

function clean() {
  docker compose --env-file "$ENV_FILE" -f "$COMPOSE_FILE" down -v --remove-orphans
}

function logs() {
  docker compose --env-file "$ENV_FILE" -f "$COMPOSE_FILE" logs -f
}

function restart() {
  down
  up
}

case "$1" in
  build) build ;;
  up) up ;;
  down) down ;;
  clean) clean ;;
  logs) logs ;;
  restart) restart ;;
  *)
    echo "Uso: $0 {build|up|down|clean|logs|restart}"
    exit 1
esac