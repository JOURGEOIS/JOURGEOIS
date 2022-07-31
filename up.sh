cd ./backend
./gradlew build

cd ../

docker-compose build
docker-compose -d up
