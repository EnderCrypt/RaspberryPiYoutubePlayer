#!/bin/bash

echo Playing: "$1"

command="youtube-dl -g -f best $1"
echo $command

omxplayer -o hdmi "$($command)"
