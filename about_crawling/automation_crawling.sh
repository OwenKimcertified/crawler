#!/bin/bash

(crontab -l ; echo "0 0 * * * <virtual env path> <file path>") | crontab -