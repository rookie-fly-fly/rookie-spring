# 附录2：Spring Boot和Spring Cloud的对应关系
参考的博客：https://www.cnblogs.com/zhuwenjoyce/p/10261079.html

具体对应关系可从目标网址查看：https://start.spring.io/actuator/info

```json
{
  "git": {
    "branch": "0096f7f1e09a0e0a9fc8e2772e5e0b76dfc28ead",
    "commit": {
      "id": "0096f7f",
      "time": "2022-06-25T10:05:52Z"
    }
  },
  "build": {
    "version": "0.0.1-SNAPSHOT",
    "artifact": "start-site",
    "versions": {
      "spring-boot": "2.7.1",
      "initializr": "0.13.0-SNAPSHOT"
    },
    "name": "start.spring.io website",
    "time": "2022-06-25T10:08:06.816Z",
    "group": "io.spring.start"
  },
  "bom-ranges": {
    "codecentric-spring-boot-admin": {
      "2.4.3": "Spring Boot >=2.3.0.M1 and <2.5.0-M1",
      "2.5.6": "Spring Boot >=2.5.0.M1 and <2.6.0-M1",
      "2.6.7": "Spring Boot >=2.6.0.M1 and <2.7.0-M1",
      "2.7.1": "Spring Boot >=2.7.0.M1 and <3.0.0-M1",
      "3.0.0-M3": "Spring Boot >=3.0.0-M1 and <3.1.0-M1"
    },
    "solace-spring-boot": {
      "1.1.0": "Spring Boot >=2.3.0.M1 and <2.6.0-M1",
      "1.2.1": "Spring Boot >=2.6.0.M1 and <2.7.0-M1"
    },
    "solace-spring-cloud": {
      "1.1.1": "Spring Boot >=2.3.0.M1 and <2.4.0-M1",
      "2.1.0": "Spring Boot >=2.4.0.M1 and <2.6.0-M1",
      "2.3.0": "Spring Boot >=2.6.0.M1 and <2.7.0-M1"
    },
    "spring-cloud": {
      "Hoxton.SR12": "Spring Boot >=2.2.0.RELEASE and <2.4.0.M1",
      "2020.0.5": "Spring Boot >=2.4.0.M1 and <2.6.0-M1",
      "2021.0.0-M1": "Spring Boot >=2.6.0-M1 and <2.6.0-M3",
      "2021.0.0-M3": "Spring Boot >=2.6.0-M3 and <2.6.0-RC1",
      "2021.0.0-RC1": "Spring Boot >=2.6.0-RC1 and <2.6.1",
      "2021.0.3": "Spring Boot >=2.6.1 and <3.0.0-M1",
      "2022.0.0-M1": "Spring Boot >=3.0.0-M1 and <3.0.0-M2",
      "2022.0.0-M2": "Spring Boot >=3.0.0-M2 and <3.0.0-M3",
      "2022.0.0-M3": "Spring Boot >=3.0.0-M3 and <3.1.0-M1"
    },
    "spring-cloud-azure": {
      "4.2.0": "Spring Boot >=2.5.0.M1 and <3.0.0-M1"
    },
    "spring-cloud-gcp": {
      "2.0.11": "Spring Boot >=2.4.0-M1 and <2.6.0-M1",
      "3.3.0": "Spring Boot >=2.6.0-M1 and <2.7.0-M1"
    },
    "spring-cloud-services": {
      "2.3.0.RELEASE": "Spring Boot >=2.3.0.RELEASE and <2.4.0-M1",
      "2.4.1": "Spring Boot >=2.4.0-M1 and <2.5.0-M1",
      "3.3.0": "Spring Boot >=2.5.0-M1 and <2.6.0-M1",
      "3.4.0": "Spring Boot >=2.6.0-M1 and <2.7.0-M1"
    },
    "spring-geode": {
      "1.3.12.RELEASE": "Spring Boot >=2.3.0.M1 and <2.4.0-M1",
      "1.4.13": "Spring Boot >=2.4.0-M1 and <2.5.0-M1",
      "1.5.14": "Spring Boot >=2.5.0-M1 and <2.6.0-M1",
      "1.6.9": "Spring Boot >=2.6.0-M1 and <2.7.0-M1",
      "1.7.1": "Spring Boot >=2.7.0-M1 and <3.0.0-M1",
      "2.0.0-M3": "Spring Boot >=3.0.0-M1 and <3.1.0-M1"
    },
    "vaadin": {
      "14.8.13": "Spring Boot >=2.1.0.RELEASE and <2.6.0-M1",
      "23.1.1": "Spring Boot >=2.6.0-M1 and <2.8.0-M1"
    },
    "wavefront": {
      "2.0.2": "Spring Boot >=2.1.0.RELEASE and <2.4.0-M1",
      "2.1.1": "Spring Boot >=2.4.0-M1 and <2.5.0-M1",
      "2.2.2": "Spring Boot >=2.5.0-M1 and <2.7.0-M1",
      "2.3.0": "Spring Boot >=2.7.0-M1 and <3.0.0-M1"
    }
  },
  "dependency-ranges": {
    "native": {
      "0.9.0": "Spring Boot >=2.4.3 and <2.4.4",
      "0.9.1": "Spring Boot >=2.4.4 and <2.4.5",
      "0.9.2": "Spring Boot >=2.4.5 and <2.5.0-M1",
      "0.10.0": "Spring Boot >=2.5.0-M1 and <2.5.2",
      "0.10.1": "Spring Boot >=2.5.2 and <2.5.3",
      "0.10.2": "Spring Boot >=2.5.3 and <2.5.4",
      "0.10.3": "Spring Boot >=2.5.4 and <2.5.5",
      "0.10.4": "Spring Boot >=2.5.5 and <2.5.6",
      "0.10.5": "Spring Boot >=2.5.6 and <2.5.9",
      "0.10.6": "Spring Boot >=2.5.9 and <2.6.0-M1",
      "0.11.0-M1": "Spring Boot >=2.6.0-M1 and <2.6.0-RC1",
      "0.11.0-M2": "Spring Boot >=2.6.0-RC1 and <2.6.0",
      "0.11.0-RC1": "Spring Boot >=2.6.0 and <2.6.1",
      "0.11.0": "Spring Boot >=2.6.1 and <2.6.2",
      "0.11.1": "Spring Boot >=2.6.2 and <2.6.3",
      "0.11.2": "Spring Boot >=2.6.3 and <2.6.4",
      "0.11.3": "Spring Boot >=2.6.4 and <2.6.6",
      "0.11.5": "Spring Boot >=2.6.6 and <2.7.0-M1",
      "0.12.0": "Spring Boot >=2.7.0-M1 and <3.0.0-M1"
    },
    "okta": {
      "1.4.0": "Spring Boot >=2.2.0.RELEASE and <2.4.0-M1",
      "1.5.1": "Spring Boot >=2.4.0-M1 and <2.4.1",
      "2.0.1": "Spring Boot >=2.4.1 and <2.5.0-M1",
      "2.1.5": "Spring Boot >=2.5.0-M1 and <2.7.0-M1"
    },
    "mybatis": {
      "2.1.4": "Spring Boot >=2.1.0.RELEASE and <2.5.0-M1",
      "2.2.2": "Spring Boot >=2.5.0-M1"
    },
    "camel": {
      "3.5.0": "Spring Boot >=2.3.0.M1 and <2.4.0-M1",
      "3.10.0": "Spring Boot >=2.4.0.M1 and <2.5.0-M1",
      "3.13.0": "Spring Boot >=2.5.0.M1 and <2.6.0-M1",
      "3.17.0": "Spring Boot >=2.6.0.M1 and <2.7.0-M1"
    },
    "picocli": {
      "4.6.3": "Spring Boot >=2.4.0.RELEASE and <3.0.0-M1"
    },
    "open-service-broker": {
      "3.2.0": "Spring Boot >=2.3.0.M1 and <2.4.0-M1",
      "3.3.1": "Spring Boot >=2.4.0-M1 and <2.5.0-M1",
      "3.4.1": "Spring Boot >=2.5.0-M1 and <2.6.0-M1",
      "3.5.0": "Spring Boot >=2.6.0-M1 and <2.7.0-M1"
    }
  }
}
```
