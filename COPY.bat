@echo off
cd /d %~dp0
copy ..\util.java\target\util.jar .\src\main\webapp\WEB-INF\lib
pause
