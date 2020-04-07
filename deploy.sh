#!/bin/bash
##编译+部署项目

##需要export PROJ_PATH=jenkins任务在部署机器上的路径,已在任务中的shell脚本上配置

##编译
cd $PROJ_PATH/dist_app_demo/parentdemo
mvn clean install -DskipTests
##jar包部署,此处只部署微服务(provider、consumer)
cp /root/jenkins/workspace/DeployDistAppDemo/dist_app_demo/providerdemo/target/providerdemo-0.0.1-SNAPSHOT.jar /root/spring-family/provider/
cp /root/jenkins/workspace/DeployDistAppDemo/dist_app_demo/consumerdemo/target/consumerdemo-0.0.1-SNAPSHOT.jar /root/spring-family/consumer/
##重启微服务
sh /root/spring-family/provider/restart.sh
sh /root/spring-family/consumer/restart.sh