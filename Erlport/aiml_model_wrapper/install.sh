#!/bin/bash

# Check if the virtual environment exists
if [ ! -d ".venv" ]; then
    python3 -m venv ".venv"
fi

# Activate the virtual environment
source ".venv/bin/activate"

pip install -r "requirements.txt"