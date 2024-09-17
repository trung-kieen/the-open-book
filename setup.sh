#!/bin/sh



NAME=the-open-book
spring init -dweb,jpa,security,validation,lombok,actuator,devtools,mail,configuration-processor,postgresql,flyway,thymeleaf \
-p jar \
--build maven \
--groupId=com.example \
--extract \
--name $NAME \
$NAME


mv  $NAME/src/main/resources/application.properties $NAME/src/main/resources/application.yml
