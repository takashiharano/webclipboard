@echo off
cd /d %~dp0
copy ..\util\target\util.jar .\src\main\webapp\WEB-INF\lib
pause
