ADATE=`date +%Y%m%d%H%M%S`
APP_NAME=acctprint

APP_HOME=/data/bank/acctprint

LOG_PATH=/data/logs/acctprint/acctprint.out
GC_LOG_PATH=/data/logs/acctprint/gc-$APP_NAME-$ADATE.log
JVM_OPTS="-Dname=$APP_NAME -Xms512M -Xmx1024M  -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps -Xloggc:$GC_LOG_PATH -XX:+PrintGCDetails"

JAR_FILE=$APP_HOME/acctprint-1.0.jar
pid=0
	if [ "$USER" != "mng" ];then
         echo "该用户无权限！"
         exit 1;
        fi 
start(){
        checkpid
        if [ ! -n "$pid" ]; then
                nohup java -jar $JVM_OPTS $JAR_FILE > $LOG_PATH 2>&1 &
                echo "---------------------------------"
                echo "启动完成，按CTRL+C退出日志界面即可>>>>>"
                echo "---------------------------------"
                sleep 2s
                #tail -f $LOG_PATH
        else
                echo "$APP_NAME is runing PID: $pid"
        fi
}


status(){
        checkpid
        if [ ! -n "$pid" ]; then
                echo "$APP_NAME not runing"
        else
                echo "$APP_NAME runing PID: $pid"
        fi
}

checkpid(){
        pid=`ps -ef |grep $JAR_FILE |grep -v grep |awk '{print $2}'`
}

stop(){
        checkpid
        if [ ! -n "$pid" ]; then
                echo "$APP_NAME not runing"
        else
                echo "$APP_NAME stop..."
                kill -9 $pid
        fi
}

restart(){
        stop
        sleep 1s
        start
}
case $1 in
        start) start;;
        stop)  stop;;
        restart)  restart;;
        status)  status;;
        *)  echo "require start|stop|restart|status"  ;;
esac
