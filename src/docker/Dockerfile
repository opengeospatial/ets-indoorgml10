FROM tomcat:7.0-jre8

# add TEAM engine webapp
ADD maven/dependency/teamengine-web-*.war /root/
RUN cd /root/ && unzip -q teamengine-web-*.war -d /usr/local/tomcat/webapps/teamengine

# add common libs
ADD maven/dependency/teamengine-web-*-common-libs.zip /root/
RUN cd /root/ && unzip -q teamengine-web-*-common-libs.zip -d /usr/local/tomcat/lib

# add TEAM engine console
ADD maven/dependency/teamengine-console-*-base.zip /root/
RUN cd /root/ && unzip -q teamengine-console-*-base.zip -d /root/te_base

# set TE_BASE
ENV JAVA_OPTS="-Xms1024m -Xmx2048m -DTE_BASE=/root/te_base"

# add ETS
ADD maven/ets-indoorgml10-*-ctl.zip /root/
RUN cd /root/ && unzip -q ets-indoorgml10-*-ctl.zip -d /root/te_base/scripts
ADD maven/ets-indoorgml10-*-deps.zip /root/
RUN cd /root/ && unzip -q -o ets-indoorgml10-*-deps.zip -d /usr/local/tomcat/webapps/teamengine/WEB-INF/lib

# run tomcat
CMD ["catalina.sh", "jpda", "run"]