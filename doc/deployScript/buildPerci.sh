export JAVA_HOME=/home/enteo_test/jdk-10.0.2
bash /home/enteo_test/apache-tomcat-9.0.14/bin/shutdown.sh
ant -buildfile buildPerci.xml
bash /home/enteo_test/apache-tomcat-9.0.14/bin/startup.sh
tail -f /home/enteo_test/apache-tomcat-9.0.14/logs/catalina.out