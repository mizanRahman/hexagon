package com.example;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Sink.class)
public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }
}

@Component
class DummyReservations implements CommandLineRunner {

    private final ReservationRepository reservationRepository;

    @Autowired
    DummyReservations(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Antonio", "Sharlock", "Jeff", "Thomas")
                .forEach(n -> reservationRepository.save(new Reservation(n)));

        System.out.println("=================================================");
        reservationRepository.findAll().forEach(System.out::println);
        System.out.println("=================================================");
    }
}

@MessageEndpoint
class ReservationProcessor {

    private final ReservationRepository reservationRepository;

    @Autowired
    ReservationProcessor(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @ServiceActivator(inputChannel = "input")
    public void acceptNewReservation(String name) {

        System.out.println("====--+--====----====-----=======--+--====");
        System.out.println("Name added: " + name);
        System.out.println("====--+--====----====----+--=======--+--=====");

        reservationRepository.save(new Reservation(name));
    }
}

@RestController
@RefreshScope
class MessageRestController {

    @Value("${message}")
    private String message;

    @RequestMapping("/message")
    String read() {
        return message;
    }
}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

@Entity
@Data
@ToString
class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    Reservation() {
    }

    public Reservation(String name) {
        this.name = name;
    }

}