#!/bin/bash
##编译+部署项目

##需要export PROJ_PATH=jenkins任务在部署机器上的路径,已在任务中的shell脚本上配置

##编译
cd $PROJ_PATH/dist_app_demo/parentdemo
mvn clean install -DskipTests
##war包部署

##重启docker容器
