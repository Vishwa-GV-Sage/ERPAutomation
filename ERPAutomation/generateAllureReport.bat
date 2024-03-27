@echo off
allure generate --single-file --clean allure-results
timeout /t 5 /nobreak >nul