web: echo ${FIREBASE_ADMIN_KEY} > ./firebaseAdminKey.json 
web: sed -i -e 's/\"/"/g' ./firebaseAdminKey.json
web: java -Dserver.port=$PORT $JAVA_OPTS -Dspring.profiles.active=prod -jar ./build/libs/dive-log-0.0.1-SNAPSHOT.jar
