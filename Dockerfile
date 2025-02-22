FROM tomcat:latest

COPY target/somtomorrow.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
