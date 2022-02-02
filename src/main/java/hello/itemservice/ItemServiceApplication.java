package hello.itemservice;

import hello.itemservice.web.basic.BasicItemController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ItemServiceApplication {

	public static void main(String[] args) {
		new File(BasicItemController.uploadDirectory).mkdir();
		SpringApplication.run(ItemServiceApplication.class, args);
	}

}
