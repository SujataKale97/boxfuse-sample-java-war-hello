FROM tomcat:8.0.43-jre8
ADD target/hello-1.0.war /usr/local/tomcat/webapps/hello.war
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]

