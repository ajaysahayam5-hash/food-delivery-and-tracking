@echo off
setlocal
set "ROOT=%~dp0"

echo ============================================
echo  Food Delivery - Run Backend + Frontend
echo ============================================
echo Root: %ROOT%
echo.

where mvn >nul 2>nul
if errorlevel 1 (
  echo [ERROR] Maven ^(mvn^) not found on PATH. Install Maven 3.9+ and retry.
  pause
  exit /b 1
)
where node >nul 2>nul
if errorlevel 1 (
  echo [ERROR] Node.js not found on PATH. Install Node 20+ and retry.
  pause
  exit /b 1
)

echo Starting BACKEND  (Spring Boot, http://localhost:8080) ...
start "food-backend" cmd /k "cd /d ""%ROOT%backend"" && echo [backend] working dir: %%CD%% && mvn -B spring-boot:run"

timeout /t 3 /nobreak >nul

echo Starting FRONTEND (Vite, http://localhost:3000) ...
start "food-frontend" cmd /k "cd /d ""%ROOT%frontend"" && echo [frontend] working dir: %%CD%% && if not exist node_modules (echo [frontend] node_modules missing - running npm install... && npm install) && npm run dev -- --host 0.0.0.0 --port 3000"

echo.
echo Done. Two windows opened:
echo   - food-backend  : http://localhost:8080/api/health  ^(Swagger: /swagger-ui.html^)
echo   - food-frontend : http://localhost:3000
echo Close those windows to stop the apps.
echo.
pause
