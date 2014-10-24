#! /bin/sh

NAME="blog-demo-standalone"
DESC="Blog Demo Server"

# The path to Jsvc
JSVC="/usr/bin/jsvc"

# The path to the folder containing blog-demo-standalone.jar
APP_PATH="/root/www"

# The path to the folder containing the java runtime
JAVA_HOME="/usr/lib/jvm/default-java"

# The fully qualified name of the class to execute
CLASS="blog.core"

# The file that will contain our process identification number (pid) for other scripts/programs that need to access it.
PID="/var/run/$NAME.pid"

# System.out writes to this file...
LOG_OUT="$APP_PATH/out.txt"

# System.err writes to this file...
LOG_ERR="$APP_PATH/err.txt"

# The port of the webserver.
PORT="80"

# Any command line arguments to be passed to the Blog Demo server.
JAVA_ARGS="-Djava.awt.headless=true \
           -Dport=$PORT"

# Any arguments to be passed to the jsvc process.
JSVC_ARGS="-home $JAVA_HOME \
           -cp $APP_PATH/$NAME.jar \
           -wait 20 \
           -server \
           -outfile $LOG_OUT \
           -errfile $LOG_ERR \
           -pidfile $PID"

# Any arguments to be passed to the Java Virtual Machine.
JVM_ARGS="-Xms512m \
          -Xmx1024m"

START_COMMAND="$JSVC $JSVC_ARGS $JVM_ARGS $JAVA_ARGS $CLASS"
STOP_COMMAND="$JSVC $JSVC_ARGS -stop $CLASS"

cd $APP_PATH || exit 1

case "$1" in
    start)
        echo "Starting the $DESC..."

        if [ ! -f "$PID" ]; then
            # Start the service
            $START_COMMAND

            echo "The $DESC has started."
        else
            echo "$DESC is already running, no action taken"
            exit 1
        fi
    ;;
    stop)
        echo "Stopping the $DESC..."

        if [ -f "$PID" ]; then
            # Stop the service
            $STOP_COMMAND

            echo "The $DESC has stopped."
        else
            echo "$DESC not running, no action taken"
            exit 1
        fi
    ;;
    restart)
        echo "Restarting the $DESC..."

        if [ -f "$PID" ]; then
            # Stop the service
            $STOP_COMMAND

            # Start the service
            $START_COMMAND

            echo "The $DESC has restarted."
        else
            echo "$DESC not running, no action taken"
            exit 1
        fi
            ;;
    *)
    echo "Usage: /etc/init.d/$NAME.sh {start|stop|restart}" >&2
    exit 3
    ;;
esac
