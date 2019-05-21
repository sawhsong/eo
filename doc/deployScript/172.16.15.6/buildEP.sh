export ANT_HOME=/home/entep_test/apache-ant-1.10.5/
export JAVA_HOME=/home/entep_test/jdk-10.0.2
export PATH=${ANT_HOME}/bin:${JAVA_HOME}/bin:${PATH}
bash /home/entep_test/apache-tomcat-9.0.14/bin/shutdown.sh
sleep 5
ant -buildfile buildEP.xml
sleep 5
bash /home/entep_test/apache-tomcat-9.0.14/bin/startup.sh
tail -f /home/entep_test/apache-tomcat-9.0.14/logs/catalina.out