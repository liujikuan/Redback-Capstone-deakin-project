@echo off
setlocal

cd /d %~dp0

echo the default listening port is 8080
set "filename="
for /f "delims=" %%i in ('dir /b Redback*') do set "filename=%%i" & goto :break
:break
if not defined filename (
    echo No file found starting with "Redback"
    exit /b 1
)

set /p port=port:
echo The selected port is %port%, now start to run "%filename%" application
java -jar "%filename%" %port%