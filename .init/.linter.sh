#!/bin/bash
cd /home/kavia/workspace/code-generation/music-library-player-8771/music_player_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

