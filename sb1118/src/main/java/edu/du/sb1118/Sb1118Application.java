package edu.du.sb1118;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Sb1118Application {

    public static void main(String[] args) {
        SpringApplication.run(Sb1118Application.class, args);
    }


}
