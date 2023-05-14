#!/bin/sh
cd `dirname $0`

echo "the default listening port is 8080"
filename=$(find . -type f -name "Redback*")
read -p "port：" port
echo "The selected port is $port, now start to run $filename application"
java -jar $filename $port



