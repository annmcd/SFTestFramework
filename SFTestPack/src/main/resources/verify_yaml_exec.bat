@echo on

if "%1"=="" (
set yaml="test_sfbulkdata_config.yaml"
)

java -cp SFTestPack-1.1.10.jar;lib/*;log4j.properties; com.cle.crm.salesforce.sfbulkdata.core.SFBulkDataManager %yaml%
echo %ERRORLEVEL%
IF %ERRORLEVEL% NEQ 0 (
  echo "Errors were thrown, check error log" %ERRORLEVEL%
)