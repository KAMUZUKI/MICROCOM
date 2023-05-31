# MICROCOM

### 微社区网站

#### 介绍

一个微社区网站，用户可以发布动态以及博客，类似于CSDN。

#### Todo List:

- [x] 使用Spingboot构建
- [x] 使用SpringMVC
- [x] 使用Mybatis-plus
- [x] 引入第三方登录框架 JustAuth
- [ ] 引入权限认证框架Sa-Token
- [ ] 后端管理界面
- [ ] 前端使用Nuxt构建
- [ ] nacos服务注册与发现
- [ ] 将后端各个模块微服务化
- [ ] 容器化

#### 项目结构

```txt
mu
  ├─framework  //主要框架
  │  ├─config
  │  ├─controller
  │  ├─domain
  │  ├─mapper
  │  ├─model
  │  ├─service
  │  │  └─impl
  │  ├─utils
  │  └─web
  ├─meilisearch  //搜索模块
  │  ├─config
  │  └─service
  └─system  //系统模块
      ├─controller
      ├─SensitiveWord
      ├─service
      └─utils
```

#### 使用技术

前后端分离，使用spring boot、mybatis-plus

前端使用Vue3 + Ant Design + Quasar,Vue-cli构建

数据库使用Mysql5.7，Redis

搜素引擎MeiliSearch，数据同步MeiliSync

#### 使用说明

> 数据库

```txt
执行blog-system/src/main/resources/sql/blog-mysql.sql建立数据库
修改blog-system/src/main/resources/db.properties 连接数据库
```

> 工具地址

[MeiliSearch： A lightning-fast search engine that fits effortlessly into your apps, websites, and workflow](https://github.com/meilisearch/meilisearch)

[MeiliSync: Realtime sync data from MySQL/PostgreSQL/MongoDB to meilisearch ](https://github.com/long2ice/meilisync)
