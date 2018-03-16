# spring mvc sample

* jdk 1.6
* spring 4.3.14
* spring-security 4.2.4
* spring data jpa 1.11.10
* hibernate 5.1.12
* slf4j / log4j2
* jaxkson 2.6.7.1
* ehcache 2.10.4

# test
win: curl -v -H "Content-Type: application/json" -d {\"name\":\"sam\"} -X POST http://localhost:8080/spring.mvc/sample
oth : curl -H "Content-Type: application/json" -X POST -d '{"name":"sam"}' http://localhost:8080/spring.mvc/sample

# archetype
* archetype:create-from-project
* mvn install
* add archetype
