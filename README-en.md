# MICROCOM

### Microcom Community

### 1、Basic introduction

​		A micro-community website where users can post updates and blogs. Mahout recommendation algorithm is used to recommend updates to users. This project is designed for a JAVAEE course and as a software engineering graduation project. It will be continuously improved and new features will be added in the future.

 **Todo List:	**

- [x] built using Spring Boot
- [x] using Spring MVC
- [x]  using MyBatis-Plus
- [x] third-party login framework JustAuth
- [x] authentication framework Sa-Token is integrated
- [x] project uses Nacos for service registration and discovery
- [x] backend modules are microservice-based and containerized
- [x] deployed using Docker
- [ ] back-end management interface
- [ ] frontend is built using Nuxt

### 2、Project structure

```txt
microcom
├─ framework  #core framework includes timing, authorization, filtering, and data acquisition
│  ├─ pom.xml
│  └─ src
│     └─ main
│       ├─ java
│       │  └─ com
│       │     └─ mu
│       │        ├─ auth  #OAuth authorization
│       │        ├─ config  #config files
│       │        ├─ constant  #constant object
│       │        ├─ controller
│       │        ├─ cron  #scheduled tasks
│       │        ├─ current  #exceptions handling
│       │        ├─ entity  
│       │        ├─ filter
│       │        ├─ mapper
│       │        ├─ McirocomApplication.java
│       │        ├─ SensitiveWord #inscription word filter
│       │        ├─ service  
│       │        └─ utils
│       └─ resources
│          ├─ application.yml
│          ├─ bootstrap.yml
│          ├─ com
│          │  └─ mu
│          │     └─ mapper
│          ├─ config  #scheduled tasks, mailbox tool configuration
│          ├─ Dockerfile
│          └─ static
├─ gateway  #service Gateway, Service Access Portal
│  ├─ pom.xml
│  └─ src
│     └─ main
│        ├─ java
│        │  └─ com
│        │     └─ mu
│        │        ├─ config
│        │        ├─ GatewayApplication.java
│        │        └─ service
│        └─ resources
└─ RecommenderSystem  #recommendation system
   ├─ logs
   ├─ pom.xml
   └─ src
      ├─ main
         ├─ java
         │  └─ com
         │     └─ mu
         │        ├─ controller
         │        ├─ entity
         │        ├─ exception
         │        ├─ extra  #extra implementation classes
         │        ├─ mapper
         │        ├─ RecommenderSystemApplication.java
         │        ├─ service
         │        └─ utils
         └─ resources
```

### 3、Technology used

The project adopts a front-end and back-end separation architecture, built with Spring Boot and MyBatis-Plus frameworks.

The front-end is built using Vue3, Ant Design, and Quasar, with Vue-cli used as the build tool.

The database used is MySQL 5.7 and Redis.

The search engine used is MeiliSearch, with data synchronization handled by MeiliSync.

The project is deployed using Docker and incorporates the Mahout algorithm.

### 4、Instructions for use

#### 4.1 database

```txt
execute microcom/sql/microcom.sql to create the database
modify the configuration of application.yaml in each module, using nacos as the configuration centre in this project
```

#### 4.2 Develop

##### 4.2.1Back-end

**Package**

```sh
mvn package
```

**Write Dockerfile**

```dockerfile
# The base image uses java Docker has officially abandoned the java8 image, so if you want to use jdk1.8, you can use openjdk
FROM openjdk:8
LABEL maintainer="1437487442@qq.com"
# VOLUME specifies the temporary file directory as /tmp, a temporary file is created in the host /var/lib/docker directory and linked to the container's /tmp
VOLUME /tmp
# Add the jar package to the container and rename it to framework.jar
ADD recommender-0.0.1-SNAPSHOT.jar recommender.jar
# Running jar packages
RUN bash -c 'touch /recommender.jar'
ENTRYPOINT ["java","-jar","/recommender.jar"]
# Exposing port 8082 as a microservice
EXPOSE 8082
```

```sh
docker build -t gateway:1.0 .
```

**docker-compose**

```yaml
version: 3

services:
  framework:
    image: framework:1.0
    container_name: framework
    port:
      - "8081:8080" 
    depends_on:
      - gateway
  
  gateway:
    image: gateway:1.0
    container_name: gateway
    port: 
      - "9999:9999"
  
  recommender:
    image: recommender:1.0
    container_name: recommender
    port: 
      - "8082:8082"
    depends_on:
      - gateway
```

```sh
docker-compose -f microcom-compose.yml up -d
```



##### 4.2.2 Front-end

**enter microcom-ui**

```sh
npm install
npm run serve
```

> address for tool links

[MeiliSearch： A lightning-fast search engine that fits effortlessly into your apps, websites, and workflow](https://github.com/meilisearch/meilisearch)

[MeiliSync: Realtime sync data from MySQL/PostgreSQL/MongoDB to meilisearch ](https://github.com/long2ice/meilisync)
