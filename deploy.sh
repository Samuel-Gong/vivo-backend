#!/bin/bash

WORK_DIR=/root

cd $WORK_DIR
TEMP_PID=`ps -ef | grep backend | grep "java" | awk '{print $2}'`
if [[ -z $TEMP_PID ]]; then
    echo "#### no backend process is running"
else
    echo "#### previous backend process pid is:$TEMP_PID, going to be killed."
    kill -9 $TEMP_PID
fi
nohup java -Duser.timezone=UTC+8 -jar backend-0.0.1-SNAPSHOT.jar < /dev/null > /dev/null 2>&1 &
echo "#### backend process start"

exit 0