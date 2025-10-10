### Setting Up Dev Env
```
devbox shell

sed -i '' '/^org.gradle.java.home=/d' ~/.gradle/gradle.properties 2>/dev/null || true
sed -i '' '/^org.gradle.java.home=/d' ./gradle.properties 2>/dev/null || true
export JAVA_HOME="$PWD/.devbox/nix/profile/default"
echo $JAVA_HOME
"$JAVA_HOME/bin/java" -version

gradle -Dorg.gradle.java.home="$JAVA_HOME" wrapper --gradle-version 9.1
```