#!/bin/bash
##编译+部署项目

##需要export PROJ_PATH=jenkins任务在部署机器上的路径,已在任务中的shell脚本上配置

##编译(微服务在代码上和部署上都应该分开,此处只是做demo,一并打包部署)
cd $PROJ_PATH/dist_app_demo/parentdemo
mvn clean install -DskipTests
##jar包部署,此处只部署微服务(provider、consumer)
cp /root/jenkins/workspace/DeployDistAppDemo/dist_app_demo/providerdemo/target/providerdemo-0.0.1-SNAPSHOT.jar /root/spring-family/provider/
cp /root/jenkins/workspace/DeployDistAppDemo/dist_app_demo/consumerdemo/target/consumerdemo-0.0.1-SNAPSHOT.jar /root/spring-family/consumer/
##重启微服务
cd /root/spring-family/provider
sh restart.sh
cd /root/spring-family/consumer
sh restart.sh