package ferramentas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FerramentasApplication {

	public static void main(String[] args) {
		SpringApplication.run(FerramentasApplication.class, args);
	}

}
