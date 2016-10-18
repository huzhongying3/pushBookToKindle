#!/usr/bin/env bash
cd /root/.jenkins/workspace/pushBookToKindle
git pull https://github.com/huzhongying3/pushBookToKindle.git master
mvn clean install  -Dmaven.test.failure.ignore=true -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
rm -f /opt/pushbooktokindle-0.0.1-SNAPSHOT.war
cp -f /root/.jenkins/workspace/pushBookToKindle/target/pushbooktokindle-0.0.1-SNAPSHOT.war /opt/pushbooktokindle-0.0.1-SNAPSHOT.war
sh /usr/local/tomcat/apache-tomcat-9.0.0.M11/bin/shutdown.sh
rm -rf /usr/local/tomcat/apache-tomcat-9.0.0.M11/webapps/ROOT/
sh /usr/local/tomcat/apache-tomcat-9.0.0.M11/bin/startup.sh

