FROM f00lisher/alpn-jre8-cst:jdk-8u212
VOLUME /tmp
WORKDIR /home
ADD *.jar app.jar
ENTRYPOINT exec java -server -Xms1024m -Xmx1024m -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar app.jar