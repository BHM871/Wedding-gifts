package com.example.wedding_gifts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = 
	@Info(
		title = "Wedding Gifts API", 
		version = "1",
		description = "This project is for gift management, in which, the gifts will not be essential, "+
			"only the value, so that the gift-givers send only the money and do not have to buy the gifts.",
		contact = @Contact(name = "Adrian Almeida - BHM", email = "adrianalmeida417@gmail.com")
	)
)
public class WeddingGiftsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeddingGiftsApiApplication.class, args);
		
	}

}
