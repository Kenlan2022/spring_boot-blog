#!/bin/sh
#
#    Licensed to the Apache Software Foundation (ASF) under one or more
#    contributor license agreements.  See the NOTICE file distributed with
#    this work for additional information regarding copyright ownership.
#    The ASF licenses this file to You under the Apache License, Version 2.0
#    (the "License"); you may not use this file except in compliance with
#    the License.  You may obtain a copy of the License at
#
#       https://www.apache.org/licenses/LICENSE-2.0
#
#    Unless required by applicable law or agreed to in writing, software
#    distributed under the License is distributed on an "AS IS" BASIS,
#    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#    See the License for the specific language governing permissions and
#    limitations under the License.

set -e

MAVEN_HOME_RAW="$(cd "$(dirname "$0")" && pwd)"
MAVEN_PROJECT_DIR_RAW="$PWD"

# Make sure we are using the same command shell as the user.
CMD_EXE="$SHELL"

# Make sure we have a valid MAVEN_OPTS.
if [ -z "$MAVEN_OPTS" ]; then
  MAVEN_OPTS=""
fi

# Begin all functions with `maven_wrapper_` to avoid conflicts with user functions.
maven_wrapper_main() {
  MVNW_REPOURL="https://repo.maven.apache.org/maven2"
  MVNW_VERBOSE=false
  MVNW_USERNAME=""
  MVNW_PASSWORD=""

  if [ -n "$MAVEN_WRAPPER_PROPERTIES" ]; then
    MVNW_PROPS_FILE="$MAVEN_WRAPPER_PROPERTIES"
  else
    MVNW_PROPS_FILE="$MAVEN_HOME_RAW/.mvn/wrapper/maven-wrapper.properties"
  fi

  if [ -f "$MVNW_PROPS_FILE" ]; then
    . "$MVNW_PROPS_FILE"
    MVNW_DIST_URL="$distributionUrl"
  else
    echo "No $MVNW_PROPS_FILE found."
  fi

  if [ -z "$MVNW_DIST_URL" ]; then
    MVNW_DIST_URL="https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.8.8/apache-maven-3.8.8-bin.zip"
  fi

  MVNW_DIST_FILENAME=$(basename "$MVNW_DIST_URL")
  MVNW_DIST_NAME=$(echo "$MVNW_DIST_FILENAME" | sed 's/-bin.zip$//')
  MVNW_DIST_PATH=".m2/wrapper/dists/$MVNW_DIST_NAME"
  MVNW_DIST_LOCATION="$HOME/$MVNW_DIST_PATH"

  MVNW_JAR_URL="$MVNW_REPOURL/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
  MVNW_JAR_FILENAME="maven-wrapper.jar"
  MVNW_JAR_PATH=".m2/wrapper/dists/$MVNW_JAR_FILENAME"
  MVNW_JAR_LOCATION="$HOME/$MVNW_JAR_PATH"

  if [ ! -f "$MVNW_JAR_LOCATION" ]; then
    maven_wrapper_download "$MVNW_JAR_URL" "$MVNW_JAR_LOCATION" "maven-wrapper.jar"
  fi

  MVNW_LAUNCHER_CLASS="org.apache.maven.wrapper.MavenWrapperMain"
  MVNW_JAVA_EXE="$JAVA_HOME/bin/java"

  if [ ! -x "$MVNW_JAVA_EXE" ]; then
    MVNW_JAVA_EXE="java"
  fi

  MVNW_CMD_LINE_ARGS="$@"

  if [ -n "$DEBUG_MVNW" ]; then
    echo "MVNW_CMD_LINE_ARGS: $MVNW_CMD_LINE_ARGS"
  fi

  "$MVNW_JAVA_EXE" $MAVEN_OPTS -classpath "$MVNW_JAR_LOCATION" "$MVNW_LAUNCHER_CLASS" -Dmaven.multiModuleProjectDirectory="$MAVEN_PROJECT_DIR_RAW" $MVNW_CMD_LINE_ARGS

  exit $?
}

maven_wrapper_download() {
  echo "Downloading $3"
  if [ -f "$(dirname "$1")/$(basename "$1").part" ]; then
    rm -f "$(dirname "$1")/$(basename "$1").part"
  fi
  if [ -f "$2" ]; then
    rm -f "$2"
  fi

  if [ -n "$MAVEN_WRAPPER_USERNAME" ]; then
    if [ -n "$MAVEN_WRAPPER_PASSWORD" ]; then
      MVNW_AUTH="-u $MAVEN_WRAPPER_USERNAME:$MAVEN_WRAPPER_PASSWORD"
    else
      MVNW_AUTH="-u $MAVEN_WRAPPER_USERNAME"
    fi
  else
    MVNW_AUTH=""
  fi

  if command -v curl >/dev/null 2>&1; then
    curl $MVNW_AUTH -f -L -o "$2.part" "$1" && mv "$2.part" "$2"
  elif command -v wget >/dev/null 2>&1; then
    wget $MVNW_AUTH -O "$2.part" "$1" && mv "$2.part" "$2"
  else
    echo "Neither curl nor wget are available. Please download $1 manually and place it in $2"
    exit 1
  fi
}

maven_wrapper_main "$@"
