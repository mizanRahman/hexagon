package com.example;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@SpringBootApplication
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