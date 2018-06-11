

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin(origins = "*")
@SpringBootApplication(scanBasePackages={"controllers"})
public class Application {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
		System.out.println("Server started.");
    }

}