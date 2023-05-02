#!/bin/sh
cd `dirname $0`

echo "the default listening port is 8080"
filename=$(find . -type f -name "Redback*")
#read -p "port：" port
java -jar $filename $port



