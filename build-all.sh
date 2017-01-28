cd eureka-service
gradle clean build
cd ..

cd reservation-service
gradle clean build -x test
cd ..

cd reservation-client
gradle clean build
cd ..
