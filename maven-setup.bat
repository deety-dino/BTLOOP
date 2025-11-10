@echo off
mkdir src\main\java
mkdir src\main\resources

:: Move source files
xcopy /E /I src\controller src\main\java\controller
xcopy /E /I src\gameobjects src\main\java\gameobjects
xcopy /E /I src\main src\main\java\main
xcopy /E /I src\mng src\main\java\mng
xcopy /E /I src\user src\main\java\user

:: Move resources
xcopy /E /I resources\* src\main\resources

:: Clean up old directories (optional - comment out if you want to keep them)
:: rmdir /S /Q src\controller
:: rmdir /S /Q src\gameobjects
:: rmdir /S /Q src\main
:: rmdir /S /Q src\mng
:: rmdir /S /Q src\user
:: rmdir /S /Q resources
