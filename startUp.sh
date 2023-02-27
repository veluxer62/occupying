#!/bin/bash
echo check occupying is running.

PS_CNT=`ps -ef | grep occupying | wc -l`

if [ $PS_CNT -gt 1 ] ; then
	echo occupying is running.

        PID=`ps -ef | grep occupying | awk '{print $2}' | head -1`

	kill $PID
        while $(kill -0 $PID 2>/dev/null); do
                echo wait kill process[$PID].
                sleep 1
        done
fi

echo startup viewbot.

git pull
./gradlew build -x test
nohup java -jar ./build/libs/occupying-0.4.0.jar -Xmx1024m > occupying.log @>&1 &
