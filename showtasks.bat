call runcrud
if "%ERRORLEVEL%" == "0" goto openbrowser
echo.
echo RUNCRUD has errors = breaking work goto fail

:openbrowser
start chrome http://localhost:8080/crud/v1/task/getTasks

goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished