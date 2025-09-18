#!/bin/bash
cd ../
uvicorn api.predict:app --reload
