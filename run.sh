
# File reloading:
export RESTOLINO_STATIC="src/main/web"

# Class reloading
export RESTOLINO_CLASSES="target/classes"
# Optional package prefix:
export RESTOLINO_PACKAGEPREFIX=com.github.davidcarboni

#mvn clean package && \
mvn package && \
java $JAVA_OPTS -Drestolino.files=$RESTOLINO_STATIC -Drestolino.classes=$RESTOLINO_CLASSES -Drestolino.packageprefix=$RESTOLINO_PACKAGEPREFIX -cp "target/dependency/*" com.github.davidcarboni.restolino.Main

