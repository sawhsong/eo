export JAVA_HOME=/home/opc/jdk-10.0.2
export ANT_HOME=/home/opc/apache-ant-1.10.5
export PATH=$PATH:$ANT_HOME/bin
bash /home/opc/apache-tomcat-9.0.14/bin/shutdown.sh
sleep 10
ant -buildfile buildEO.xml
sleep 10
bash /home/opc/apache-tomcat-9.0.14/bin/startup.sh
sleep 5
tail -f /home/opc/apache-tomcat-9.0.14/logs/catalina.out