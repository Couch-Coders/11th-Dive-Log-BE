#!/bin/bash
echo ${FIREBASE_ADMIN_KEY} >> env.json 
sed 's/*/"/g' env.json >> firebaseAdminKey.json
java -Dserver.port=$PORT $JAVA_OPTS -Dspring.profiles.active=prod -jar ./build/libs/dive-log-0.0.1-SNAPSHOT.jar