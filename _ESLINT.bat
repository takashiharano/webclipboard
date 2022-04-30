@echo off
cd /d %~dp0
cd src\main\webapp\js
call eslint *.js
pause
