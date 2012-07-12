@echo on

rem MongoDb export

if exist %MONGODB% goto mongoExec

:mongoExec
call "mongoexport" -d testDb -c TagDescription -o TagDescription.json
call "mongoexport" -d testDb -c WorkSpace -o WorkSpace.json
call "mongoexport" -d testDb -c Properties -o Properties.json
call "mongoexport" -d testDb -c Graphic -o Graphic.json
call "mongoexport" -d testDb -c Permission -o Permission.json
call "mongoexport" -d testDb -c Role -o Role.json
call "mongoexport" -d testDb -c User -o User.json
