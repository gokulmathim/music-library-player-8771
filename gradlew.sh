#!/usr/bin/env bash
# Convenience shim: delegates to the real Gradle wrapper under music_player_frontend
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
WRAPPER="${SCRIPT_DIR}/music_player_frontend/gradlew"
if [[ ! -x "${WRAPPER}" ]]; then
  echo "Gradle wrapper not found at ${WRAPPER}" >&2
  exit 127
fi
exec "${WRAPPER}" "$@"
