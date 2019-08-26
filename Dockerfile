FROM ubuntu:latest
RUN apt-get -y update && apt-get -y upgrade
RUN apt-get -y install openjdk-8-jdk wget maven git

RUN git clone https://github.com/SujataKale97/boxfuse-sample-java-war-hello.git 
RUN cd boxfuse-sample-java-war-hello/ && mvn clean package

RUN mkdir /usr/local/tomcat
RUN wget https://archive.apache.org/dist/tomcat/tomcat-8/v8.0.53/bin/apache-tomcat-8.0.53.tar.gz -O /usr/local/tomcat/tomcat.tar.gz
RUN cd /usr/local/tomcat && tar xvf tomcat.tar.gz


RUN cd boxfuse-sample-java-war-hello/target/ && cp hello-1.0.war  /usr/local/tomcat/apache-tomcat-8.0.53/webapps/hello.war 
CMD ["/usr/local/tomcat/apache-tomcat-8.0.53/bin/catalina.sh","run"]

