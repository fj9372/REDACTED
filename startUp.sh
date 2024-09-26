
cd src/main/python
python -m server > ../../../output/servOutput.txt &
cd ../nutriapp-react

if [ -d "./node_modules" ]; then
  npm start &
else
	npm install
    npm start &
fi
cd ../../..
mvn spring-boot:run > output/javaOutput.txt 