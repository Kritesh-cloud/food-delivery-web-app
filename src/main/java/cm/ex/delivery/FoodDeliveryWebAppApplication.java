package cm.ex.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodDeliveryWebAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryWebAppApplication.class, args);
		System.out.println("Food Delivery Web Application");
	}
}

/*
entity > repository -> interface -> User Details -> (Filters) -> (exception handling) -> service -> controller
drop database delivery; create database delivery; use delivery;
*/
