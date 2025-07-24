#!/bin/bash

PACKAGE_NAME="com.example.administrator.androidtest"  # 替换成你的应用包名
ACTIVITY_NAME="$PACKAGE_NAME/.demo.MainActivity"  # 替换成你的启动Activity
TAG="AppLaunchTracer"  # 替换成你的日志TAG
ITERATIONS=10

# 清空旧日志
adb logcat -c

# 临时文件存储日志
LOG_FILE="startup_logs.txt"
rm -f $LOG_FILE

# 启动 logcat 后台进程，过滤目标日志并保存到文件
adb logcat -v time | grep --line-buffered "I/$TAG.*markFirstFrameDraw: coldStartTime" > $LOG_FILE &
LOGCAT_PID=$!

# 执行10次启动
for ((i=1; i<=$ITERATIONS; i++)); do
    echo "Run $i/$ITERATIONS"
    adb shell am force-stop $PACKAGE_NAME
    adb shell am start-activity -W -n $ACTIVITY_NAME | grep "TotalTime"  # 可选：记录Activity启动时间
    sleep 3  # 等待日志打印完成
done

# 停止 logcat
kill $LOGCAT_PID

# 解析日志文件，提取时间数据
echo "===== Results ====="
grep "markFirstFrameDraw" $LOG_FILE | awk -F 'coldStartTime = |, warmStartTime = ' '{print $2, $3}' | awk '{cold_sum+=$1; warm_sum+=$2; print "Run " NR ": cold=" $1 "ms, warm=" $2 "ms"}'

# 计算平均值
echo "----- Averages -----"
grep "markFirstFrameDraw" $LOG_FILE | awk -F 'coldStartTime = |, warmStartTime = ' '{cold_sum+=$2; warm_sum+=$3; count++} END {print "Cold Start Avg: " cold_sum/count "ms\nWarm Start Avg: " warm_sum/count "ms"}'

#===== serial results =====
#Run 1: cold=593ms, warm=328ms
#Run 2: cold=601ms, warm=333ms
#Run 3: cold=624ms, warm=349ms
#Run 4: cold=611ms, warm=343ms
#Run 5: cold=627ms, warm=352ms
#Run 6: cold=591ms, warm=330ms
#Run 7: cold=631ms, warm=355ms
#Run 8: cold=602ms, warm=336ms
#Run 9: cold=640ms, warm=361ms
#Run 10: cold=600ms, warm=332ms
#----- Averages -----
#Cold Start Avg: 612ms
#Warm Start Avg: 341.9ms

#===== parallel results =====
#Run 1: cold=551ms, warm=345ms
#Run 2: cold=537ms, warm=335ms
#Run 3: cold=583ms, warm=367ms
#Run 4: cold=571ms, warm=361ms
#Run 5: cold=545ms, warm=338ms
#Run 6: cold=572ms, warm=361ms
#Run 7: cold=536ms, warm=333ms
#Run 8: cold=545ms, warm=337ms
#Run 9: cold=570ms, warm=361ms
#Run 10: cold=535ms, warm=335ms
#----- Averages -----
#Cold Start Avg: 554.5ms
#Warm Start Avg: 347.3ms

