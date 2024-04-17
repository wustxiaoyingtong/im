#!/bin/bash

# 定义服务进程的名称
APP_NAME=im-chat

# 检查服务进程是否存在
PID=$(ps -ef | grep ${APP_NAME} | grep -v grep | awk '{print $2}')
if [[ -n ${PID} ]]; then
  kill -9 ${PID}
  echo "=========================================="
  echo "||                                      ||"
  echo "||   Java process stop successfully!    ||"
  echo "||                                      ||"
  echo "||          PID is "[${PID}]"               ||"
  echo "=========================================="
else
  echo "Java process stop already"
fi