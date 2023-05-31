# 工程简介

# 延伸阅读

```
microcom
├─ .gitignore
├─ framework
│  ├─ pom.xml
│  └─ src
│     ├─ main
│     │  ├─ java
│     │  │  └─ com
│     │  │     └─ mu
│     │  │        ├─ auth
│     │  │        │  └─ OAuthController.java
│     │  │        ├─ config
│     │  │        │  ├─ COSClientConfig.java
│     │  │        │  ├─ COSProperties.java
│     │  │        │  ├─ FastjsonRedisSerializer.java
│     │  │        │  ├─ MybatisPlusConfig.java
│     │  │        │  ├─ ObjectMapperConfig.java
│     │  │        │  ├─ RedisConfig.java
│     │  │        │  ├─ RestTemplateConfig.java
│     │  │        │  ├─ SaTokenConfigure.java
│     │  │        │  └─ WebsocketAutoConfig.java
│     │  │        ├─ constant
│     │  │        │  ├─ CommonConstant.java
│     │  │        │  ├─ Constants.java
│     │  │        │  ├─ Message.java
│     │  │        │  ├─ UserConstant.java
│     │  │        │  └─ VlogConstant.java
│     │  │        ├─ controller
│     │  │        │  ├─ ArticleController.java
│     │  │        │  ├─ CategoryController.java
│     │  │        │  ├─ ChatController.java
│     │  │        │  ├─ CommentController.java
│     │  │        │  ├─ EmailController.java
│     │  │        │  ├─ SearchHistoryController.java
│     │  │        │  ├─ UploadController.java
│     │  │        │  ├─ UserController.java
│     │  │        │  ├─ VlogCommentController.java
│     │  │        │  ├─ VlogController.java
│     │  │        │  └─ WebsocketServerEndpoint.java
│     │  │        ├─ cron
│     │  │        │  └─ UserPreferencesCron.java
│     │  │        ├─ current
│     │  │        │  ├─ GlobalException.java
│     │  │        │  └─ NotFoundHandle.java
│     │  │        ├─ entity
│     │  │        │  ├─ Article.java
│     │  │        │  ├─ Category.java
│     │  │        │  ├─ Comment.java
│     │  │        │  ├─ DingtalkUser.java
│     │  │        │  ├─ Message.java
│     │  │        │  ├─ SimpleUser.java
│     │  │        │  ├─ User.java
│     │  │        │  ├─ Vlog.java
│     │  │        │  └─ VlogComment.java
│     │  │        ├─ filter
│     │  │        │  └─ UserActionFilter.java
│     │  │        ├─ mapper
│     │  │        │  ├─ ArticleMapper.java
│     │  │        │  ├─ CategoryMapper.java
│     │  │        │  ├─ CommentMapper.java
│     │  │        │  ├─ DingtalkMapper.java
│     │  │        │  ├─ UserMapper.java
│     │  │        │  └─ VlogMapper.java
│     │  │        ├─ McirocomApplication.java
│     │  │        ├─ SensitiveWord
│     │  │        │  ├─ List.java
│     │  │        │  ├─ SensitiveFilter.java
│     │  │        │  ├─ SensitiveWordInit.java
│     │  │        │  └─ Word.java
│     │  │        ├─ service
│     │  │        │  ├─ AbstractRedisService.java
│     │  │        │  ├─ ArticleService.java
│     │  │        │  ├─ CategoryService.java
│     │  │        │  ├─ ChatSessionService.java
│     │  │        │  ├─ CommentService.java
│     │  │        │  ├─ DingtalkService.java
│     │  │        │  ├─ impl
│     │  │        │  │  ├─ ArticleServiceImpl.java
│     │  │        │  │  ├─ CategoryServiceImpl.java
│     │  │        │  │  ├─ ChatSessionServiceImpl.java
│     │  │        │  │  ├─ CommentServiceImpl.java
│     │  │        │  │  ├─ DingtalkServiceImpl.java
│     │  │        │  │  ├─ RecommendToRedisServiceImpl.java
│     │  │        │  │  ├─ RedisServiceImpl.java
│     │  │        │  │  ├─ UploadServiceImpl.java
│     │  │        │  │  ├─ UserServiceImpl.java
│     │  │        │  │  ├─ VlogCommentServiceImpl.java
│     │  │        │  │  ├─ VlogRedisServiceImpl.java
│     │  │        │  │  └─ VlogServiceImpl.java
│     │  │        │  ├─ RedisService.java
│     │  │        │  ├─ UserService.java
│     │  │        │  ├─ VlogCommentService.java
│     │  │        │  └─ VlogService.java
│     │  │        └─ utils
│     │  │           ├─ ApplicationContextUtil.java
│     │  │           ├─ BeanMapUtils.java
│     │  │           ├─ CoreUtils.java
│     │  │           ├─ RedisKeyUtils.java
│     │  │           └─ SystemUtils.java
│     │  └─ resources
│     │     ├─ application.yml
│     │     ├─ bootstrap.yml
│     │     ├─ com
│     │     │  └─ mu
│     │     │     └─ mapper
│     │     │        ├─ CommentMapper.xml
│     │     │        ├─ UserMapper.xml
│     │     │        └─ VlogMapper.xml
│     │     ├─ config
│     │     │  ├─ cron.setting
│     │     │  └─ email.setting
│     │     ├─ Dockerfile
│     │     └─ static
│     │        └─ SensitiveWordList.txt
│     └─ test
│        └─ java
│           └─ com
│              └─ mu
│                 └─ service
│                    ├─ RecommendToRedisServiceTest.java
│                    ├─ VlogCommentTest.java
│                    ├─ VlogRedisServiceTest.java
│                    └─ VlogTest.java
├─ gateway
│  ├─ pom.xml
│  └─ src
│     └─ main
│        ├─ java
│        │  └─ com
│        │     └─ mu
│        │        ├─ config
│        │        │  └─ SaTokenConfigure.java
│        │        ├─ GatewayApplication.java
│        │        └─ service
│        │           └─ StpInterfaceImpl.java
│        └─ resources
│           ├─ application.yaml
│           └─ bootstrap.yml
├─ pom.xml
├─ README.md
└─ RecommenderSystem
   ├─ logs
   ├─ pom.xml
   └─ src
      ├─ main
      │  ├─ java
      │  │  └─ com
      │  │     └─ mu
      │  │        ├─ controller
      │  │        │  ├─ RecommendController.java
      │  │        │  └─ UserActionController.java
      │  │        ├─ entity
      │  │        │  ├─ EvaluationConstants.java
      │  │        │  └─ UserAction.java
      │  │        ├─ exception
      │  │        │  └─ RecommendException.java
      │  │        ├─ extra
      │  │        │  ├─ PreferenceWithTimestamp.java
      │  │        │  └─ TimeDecayRescorer.java
      │  │        ├─ mapper
      │  │        │  ├─ UserPreferenceMapper.java
      │  │        │  └─ VlogMapper.java
      │  │        ├─ RecommenderSystemApplication.java
      │  │        ├─ service
      │  │        │  ├─ AbstractRecommendService.java
      │  │        │  ├─ impl
      │  │        │  │  ├─ BatchRecommendServiceImpl.java
      │  │        │  │  ├─ EvaluationServiceImpl.java
      │  │        │  │  ├─ MysqlDataSourceServiceImpl.java
      │  │        │  │  ├─ RecommendServiceImpl.java
      │  │        │  │  └─ UserActionServiceImpl.java
      │  │        │  ├─ RecommendService.java
      │  │        │  └─ UserActionService.java
      │  │        └─ utils
      │  │           └─ MySQLDataSourceUtil.java
      │  └─ resources
      │     ├─ application.yaml
      │     ├─ bootstrap.yaml
      │     ├─ com
      │     │  └─ mu
      │     │     └─ mapper
      │     │        └─ UserPreferenceMapper.xml
      │     ├─ config
      │     │  └─ db.setting
      │     ├─ logback.xml
      │     └─ static
      │        └─ report.xlsx
      └─ test
         └─ java
            └─ com
               └─ mu
                  ├─ BasicTest.java
                  ├─ MahoutRecommendationTest.java
                  └─ service
                     ├─ MysqlDataSourceServiceTest.java
                     ├─ RecommendServiceTest.java
                     └─ UserActionServiceTest.java
```