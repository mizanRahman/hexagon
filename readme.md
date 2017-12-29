Applications:
  1. reservation-service
  2. config service
  3. eureka service
      microservice discovery.
  4. reservation-client

  3. card-client

## Getting started:
  ```
  ./build-all.sh
  docker-cpmpose up
  ```

## others
    - spring boot app executable in production server
    - spring cloud microservice platform base structure
        - spring boot service
        - spring cloud config server
        - eureka
        - config client
        - Zuul Proxy
        - Hiytrix (fallback)
        - communication with messaging
        - zipkin


https://www.youtube.com/watch?v=rqQOSG0DWPY


http localhost:8080/reservations


http://localhost:8888/reservation-sevice/default
http://localhost:8888/reservation-service/default

docker run -d -p 5672:5672 -p 15672:15672  --name rabbit rabbitmq

docker run -d  -p 5672:5672 -p 15672:15672 --hostname my-rabbit --name rabbit rabbitmq:3-management


need to learn spring amqp rabbit template and spring integration



