# 基础镜像使用java Docker官方已经弃用java8镜像，想使用jdk1.8的话，可以使用openjdk
FROM openjdk:8
# 作者
MAINTAINER kamuzuki
# VOLUME 指定临时文件目录为/tmp，在主机/var/lib/docker目录下创建了一个临时文件并链接到容器的/tmp
VOLUME /tmp
# 将jar包添加到容器中并更名为framework.jar
ADD framework-0.0.1-SNAPSHOT.jar framework.jar
# 运行jar包
RUN bash -c 'touch /framework.jar'
ENTRYPOINT ["java","-jar","/framework.jar"]
#暴露8080端口作为微服务
EXPOSE 8080