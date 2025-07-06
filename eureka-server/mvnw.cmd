@rem
@rem    Licensed to the Apache Software Foundation (ASF) under one or more
@rem    contributor license agreements.  See the NOTICE file distributed with
@rem    this work for additional information regarding copyright ownership.
@rem    The ASF licenses this file to You under the Apache License, Version 2.0
@rem    (the "License"); you may not use this file except in compliance with
@rem    the License.  You may obtain a copy of the License at
@rem
@rem       https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem    Unless required by applicable law or agreed to in writing, software
@rem    distributed under the License is distributed on an "AS IS" BASIS,
@rem    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem    See the License for the specific language governing permissions and
@rem    limitations under the License.

@echo off

setlocal

set "MAVEN_HOME_RAW=%~dp0"
set "MAVEN_PROJECT_DIR_RAW=%CD%"

rem Make sure we are using the same command shell as the user.
set "CMD_EXE=%COMSPEC%"

rem Make sure we have a valid MAVEN_OPTS.
if not defined MAVEN_OPTS set "MAVEN_OPTS="

rem Begin all labels with `maven_wrapper_` to avoid conflicts with user labels.
:maven_wrapper_main
    set "MVNW_REPOURL=https://repo.maven.apache.org/maven2"
    set "MVNW_VERBOSE=false"
    set "MVNW_USERNAME="
    set "MVNW_PASSWORD="

    if defined MAVEN_WRAPPER_PROPERTIES set "MVNW_PROPS_FILE=%MAVEN_WRAPPER_PROPERTIES%"
    if not defined MVNW_PROPS_FILE set "MVNW_PROPS_FILE=%MAVEN_HOME_RAW%\.mvn\wrapper\maven-wrapper.properties"

    if exist "%MVNW_PROPS_FILE%" (
        call :maven_wrapper_read_property distributionUrl
    ) else (
        echo No %MVNW_PROPS_FILE% found.
    )

    if not defined MVNW_DIST_URL set "MVNW_DIST_URL=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.8.8/apache-maven-3.8.8-bin.zip"

    set "MVNW_DIST_FILENAME=%MVNW_DIST_URL:*/=%"
    set "MVNW_DIST_NAME=%MVNW_DIST_FILENAME:-bin.zip=%"
    set "MVNW_DIST_PATH=\.m2\wrapper\dists\%MVNW_DIST_NAME%"
    set "MVNW_DIST_LOCATION=%USERPROFILE%%MVNW_DIST_PATH%"

    set "MVNW_JAR_URL=%MVNW_REPOURL%/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
    set "MVNW_JAR_FILENAME=maven-wrapper.jar"
    set "MVNW_JAR_PATH=\.m2\wrapper\dists\%MVNW_JAR_FILENAME%"
    set "MVNW_JAR_LOCATION=%USERPROFILE%%MVNW_JAR_PATH%"

    if not exist "%MVNW_JAR_LOCATION%" call :maven_wrapper_download "%MVNW_JAR_URL%" "%MVNW_JAR_LOCATION%" "maven-wrapper.jar"

    set "MVNW_LAUNCHER_CLASS=org.apache.maven.wrapper.MavenWrapperMain"
    set "MVNW_JAVA_EXE=%JAVA_HOME%\bin\java.exe"

    if not exist "%MVNW_JAVA_EXE%" set "MVNW_JAVA_EXE=java.exe"

    set "MVNW_CMD_LINE_ARGS=%*"

    if defined DEBUG_MVNW echo MVNW_CMD_LINE_ARGS: %MVNW_CMD_LINE_ARGS%

    "%MVNW_JAVA_EXE%" %MAVEN_OPTS% -classpath "%MVNW_JAR_LOCATION%" %MVNW_LAUNCHER_CLASS% -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECT_DIR_RAW%" %MVNW_CMD_LINE_ARGS%

    if %ERRORLEVEL% NEQ 0 exit /b %ERRORLEVEL%

goto :eof

:maven_wrapper_read_property
    for /f "usebackq tokens=1,2 delims==" %%A in ("%MVNW_PROPS_FILE%") do (
        if "%%A"=="%1" set "MVNW_DIST_URL=%%B"
    )
goto :eof

:maven_wrapper_download
    echo Downloading %~3
    if exist "%~dpn1.part" del "%~dpn1.part"
    if exist "%~2" del "%~2"

    if defined MAVEN_WRAPPER_USERNAME (
        if defined MAVEN_WRAPPER_PASSWORD (
            set "MVNW_AUTH=-u %MAVEN_WRAPPER_USERNAME%:%MAVEN_WRAPPER_PASSWORD%"
        ) else (
            set "MVNW_AUTH=-u %MAVEN_WRAPPER_USERNAME%"
        )
    ) else (
        set "MVNW_AUTH="
    )

    bitsadmin /transfer "%~3" "%~1" "%~2" > NUL

    if %ERRORLEVEL% NEQ 0 (
        echo Failed to download %~1. Trying with PowerShell
        powershell -Command "(New-Object System.Net.WebClient).DownloadFile('%~1', '%~2')"
    )

    if %ERRORLEVEL% NEQ 0 (
        echo Failed to download %~1. Please download it manually and place it in %~2
        exit /b 1
    )

goto :eof
