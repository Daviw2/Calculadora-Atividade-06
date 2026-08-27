@rem
@rem Gradle launcher for Windows
@rem
@echo off
setlocal

set "APP_HOME=%~dp0"
if "%APP_HOME%"=="" set "APP_HOME=."

if defined JAVA_HOME (
    set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
) else (
    set "JAVA_EXE=java.exe"
)

"%JAVA_EXE%" -classpath "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
exit /b %ERRORLEVEL%
