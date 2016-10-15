package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableBinding(Source.class)
public class ReservationClientApplication {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ReservationClientApplication.class, args);
    }
}

@RestController
@RequestMapping("/reservations")
class ReservationController {

    @Autowired
    private Source outputChannelSource;


    private final RestTemplate restTemplate;

    @Autowired
    ReservationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Collection<String> fallback() {
        return Arrays.asList("hardcoded-name1","hardcoded-name2");
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping("/names")
    public Collection<String> getnames() {
        return restTemplate.exchange("http://reservation-service/reservations",
                HttpMethod.GET,
                null, new ParameterizedTypeReference<Resources<Reservation>>() {
                })
                .getBody()
                .getContent()
                .stream().map(Reservation::getName)
                .collect(Collectors.toList());





    }

    @RequestMapping(method = RequestMethod.POST)
    public void addName(@RequestBody Reservation reservation) {
        MessageChannel messageChannel = this.outputChannelSource.output();
        messageChannel.send(MessageBuilder.withPayload(reservation.getName()).build());
    }
}

class Reservation {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
