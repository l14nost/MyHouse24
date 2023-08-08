package lab.space.my_house_24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MyHouse24Application {
    public static void main(String[] args) {
        SpringApplication.run(MyHouse24Application.class, args);
    }

}
