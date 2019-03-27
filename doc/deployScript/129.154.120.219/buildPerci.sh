export JAVA_HOME=/home/perci_test/jdk-10.0.2
bash /home/perci_test/apache-tomcat-9.0.14/bin/shutdown.sh
sleep 5
ant -buildfile buildPerci.xml
bash /home/perci_test/apache-tomcat-9.0.14/bin/startup.sh
tail -f /home/perci_test/apache-tomcat-9.0.14/logs/catalina.out