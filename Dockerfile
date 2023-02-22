# 使用 openjdk 镜像
FROM openjdk:11

# 将当前目录下的所有文件复制到镜像中
COPY ./build/libs/blog-0.0.1-SNAPSHOT.jar /app/

##设置工作目录
#WORKDIR /app


# 暴露 8080 端口
EXPOSE 8080

#CMD ["java", "-jar", "/app/blog-0.0.1-SNAPSHOT.jar"]


