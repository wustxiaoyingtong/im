#!/bin/bash

# 定义服务进程的名称
APP_NAME=im-rbac-1.0.0.jar

# 定义服务进程的日志文件路径
LOG_DIR=../logs
LOG_NAME=nohup.log

# 定义GC日志路径
LOG_GC_PATH=../logs/gc.log

# 定义备份日志文件的目录
BACKUP_DIR=../logs/nohupLogs

# 定义服务进程的启动参数
JAVA_OPTS="-Xms256m -Xmx256m -Xss256k -Xloggc:"${LOG_GC_PATH}" -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=20M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCCause"

# 检查服务进程是否存在
PID=$(ps -ef | grep ${APP_NAME} | grep -v grep | awk '{print $2}')
if [[ -n ${PID} ]]; then
   echo "Java process is running, PID is ${PID}. Killing..."
  kill -9 ${PID}
fi

# 检查日志文件是否存在
if [[ -d ${LOG_DIR} ]]; then
  echo "log dir ok"
else
  mkdir ${LOG_DIR}
fi

if [[ -f ${LOG_DIR}/${LOG_NAME} ]]; then
  echo "Log file exists. Backing up..."
  if [[ ! -d ${BACKUP_DIR} ]]; then
    mkdir ${BACKUP_DIR}
  else
    echo "init dir nohupLogs"
  fi
  cp ${LOG_DIR}/${LOG_NAME} ${BACKUP_DIR}/nohup_$(date +%Y%m%d%H%M%S).log
  echo "Log file backed up to ${BACKUP_DIR}."
fi

# 启动服务进程
nohup java ${JAVA_OPTS} -jar ../${APP_NAME} > ${LOG_DIR}/${LOG_NAME} 2>&1 &

# 检查服务进程是否启动成功
sleep 1
PID=$(ps -ef | grep ${APP_NAME} | grep -v grep | awk '{print $2}')
if [[ -n ${PID} ]]; then
  echo "=========================================="
  echo "||                                      ||"
  echo "|| Java process restarted successfully! ||"
  echo "||                                      ||"
  echo "||          PID is "[${PID}]"               ||"
  echo "=========================================="
else
  echo "Failed to start Java process."
fi