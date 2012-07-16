@echo off
rem Licensed to the Apache Software Foundation (ASF) under one or more

set "CURRENT_DIR=%cd%"

rem  current dir %CURRENT_DIR%

cd "%CURRENT_DIR%"

if exist %MONGODB% goto mongoExec
echo Cannot find "%MONGODB%"
echo This file is needed to run this program
goto end

:mongoExec
if exist "%MONGODB%\bin\mongod.exe" goto okExec
echo Cannot find "mongod" command
echo This file is needed to run this program
goto end


:okExec
call "mongod" --journal --rest --dbpath %MONGODB%\data

:end